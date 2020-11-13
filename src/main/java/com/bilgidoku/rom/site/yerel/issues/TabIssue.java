package com.bilgidoku.rom.site.yerel.issues;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.IntegerResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Issues;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.client.util.browse.image.FileUploadDialog;
import com.bilgidoku.rom.gwt.client.util.browse.image.UploadFile;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.one;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.site.yerel.contacts.TabContact;
import com.bilgidoku.rom.site.yerel.subpanels.PnlTags;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabIssue extends Composite implements UploadFile, HasTags {

	private final Button btnAssign = new Button(Ctrl.trans.assign());
	private final Button btnClose = new Button(Ctrl.trans.closeIssue());
	private final Button btnResolve = new Button(Ctrl.trans.resolve());

	private Issues iss;

	private final SiteToolbarButton btnFileUpload = new SiteToolbarButton("/_local/images/common/upload.png", "",
			Ctrl.trans.fileUpload(), "");
	private final DialogPanel comments;
	private final List<String[]> users;
	private final VerticalPanel attchsPanel = new VerticalPanel();
	private final ListBox lbUsers = new ListBox();
	private final SimplePanel holder = new SimplePanel();
	private final SiteToolbarButton btnChangeDesc = new SiteToolbarButton("/_local/images/common/pencil.png", "", "",
			"");
	private final SiteToolbarButton btnChangeTitle = new SiteToolbarButton("/_local/images/common/pencil.png", "", "",
			"");
	private final SiteToolbarButton btnChangeResolve = new SiteToolbarButton("/_local/images/common/pencil.png", "", "",
			"");
	private final PnlTags pnlTags = new PnlTags(this);
	
	private final PnlTags pnlOzne;
	private final PnlTags pnlNesne;
	
	private ListBox clsList = new ListBox();
	
	private HTML htmlDesc = new HTML("");

	// private final Button btnChangeOwner = new
	// Button(Ctrl.trans.changeOwner());

	public TabIssue(Issues issu, List<String[]> user) {
		// find names from contacts
		this.iss = issu;
		this.users = user;

		loadUsers(user);
		pnlTags.loadData(issu.tags);

		htmlDesc.setHTML(iss.description);

		comments = new DialogPanel(iss.dialog_uri, this);
		forAssign();
		// forChangeOwner();
		forChangeTitle();
		forClose();
		forFileUpload();
		forResolve();
		forChangeResolveDesc();
		forChangeDesc();
		forChangeTitle();

		holder.add(ui(iss));
		initWidget(holder);
		
		pnlOzne = new PnlTags(new HasTags() {
			@Override
			public void tagsChanged() {
				IssuesDao.set_oznetags(pnlOzne.getTags(), iss.uri, 
						new StringResponse() {
				});
			}
		});
		
		pnlNesne = new PnlTags(new HasTags() {
			
			@Override
			public void tagsChanged() {
				IssuesDao.set_nesnetags(pnlNesne.getTags(), iss.uri, 
						new StringResponse() {
				});
			}
		});
		
		

	}

	private boolean isEvent() {
		return this.iss.cls.equals("event");
	}

	private void forChangeResolveDesc() {
		btnChangeResolve.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				resolve(iss.resolve_desc);

			}
		});
	}

	private void forResolve() {
		btnResolve.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				resolve(null);
			}
		});

	}

	private void resolve(String resolveDesc) {
		final ResolveIssueDlg dlg = new ResolveIssueDlg(resolveDesc);
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (dlg.fixed)
					IssuesDao.resolve("", "", "", "", null, null, "", dlg.getDesc(), 1000, iss.uri,
							new StringResponse() {
								@Override
								public void ready(String value) {
									Ctrl.closeTab(iss.uri);
									Ctrl.setStatus(Ctrl.trans.resolved());
									Ctrl.reloadIssuesNav();
									// reloadIssue();
								}
							});

			}
		});

	}

	private void forChangeDesc() {
		btnChangeDesc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final AskForValueDlg dlg = new AskForValueDlg(Ctrl.trans.desc(), iss.description, 3, 4,
						Ctrl.trans.save(), "");
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (!dlg.getValue().isEmpty()) {
							IssuesDao.changedesc(dlg.getValue(), iss.uri, new StringResponse() {
								public void ready(String value) {
									htmlDesc.setHTML(dlg.getValue());
									reloadIssue(false);
								};
							});
						}
					}
				});

			}
		});

	}

	private void loadUsers(List<String[]> myarr) {
		lbUsers.addItem("---" + Ctrl.trans.users() + "---", "");
		for (int i = 0; i < myarr.size(); i++) {
			String[] u = myarr.get(i);
			lbUsers.addItem(u[0], u[1]);
		}

	}

	private void forFileUpload() {
		btnFileUpload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FileUploadDialog dlg = new FileUploadDialog(TabIssue.this, iss.uri + "/addfile.rom");
				dlg.show();
			}
		});

	}

	private void forClose() {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (iss.close_date == null) {
					boolean b = Window.confirm(Ctrl.trans.closeIssue());
					if (b) {
						IssuesDao.close(null, null, null, null, null, null, iss.uri, new StringResponse() {
							@Override
							public void ready(String value) {
								// reloadIssue();
								Ctrl.setStatus(Ctrl.trans.closed());
								Ctrl.closeTab(iss.uri);
								Ctrl.reloadIssuesNav();
							}
						});
					}
				} else {
					boolean b = Window.confirm(Ctrl.trans.reopenIssue());
					if (b) {
						IssuesDao.reopen(iss.uri, new StringResponse() {
							@Override
							public void ready(String value) {
								reloadIssue(false);
							}
						});
					}
				}
			}
		});

	}

	private void forAssign() {
		btnAssign.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lbUsers.getSelectedIndex() <= 0) {
					Window.alert(Ctrl.trans.selectUserFirst());
					return;
				}

				if (iss.assigned_to == null || iss.assigned_to.isEmpty()) {
					// first assign
					IssuesDao.assignto(lbUsers.getSelectedValue(), iss.uri, new StringResponse() {
						@Override
						public void ready(String value) {
							reloadIssue(true);
						}

					});
				} else {
					// change assignee
					if (iss.assigned_to.equals(lbUsers.getSelectedValue())) {
						Window.alert(Ctrl.trans.same(Ctrl.trans.user()));
						return;
					}

					IssuesDao.assignto(lbUsers.getSelectedValue(), iss.uri, new StringResponse() {
						@Override
						public void ready(String value) {
							reloadIssue(false);
						}

					});

				}

			}
		});
	}

	protected void reloadIssue(final boolean reloadNav) {
		// reload issue
		IssuesDao.get(iss.uri, new IssuesResponse() {
			@Override
			public void ready(Issues value) {
				iss = value;
				attchsPanel.clear();
				holder.clear();
				holder.add(ui(value));
				comments.updateDialog(value.dialog_uri);
				if (reloadNav)
					Ctrl.reloadIssuesNav();
			}
		});

	}

	@Override
	public void fileUploaded(String dbfs, String fileName) {
		reloadIssue(false);
	}

	private boolean isClosed() {
		return this.iss.close_date != null ? true : false;
	}

	private boolean isAssigned() {
		return this.iss.assign_date != null ? true : false;
	}

	private VerticalPanel ui(final Issues iss) {

		for(int i=0; i< Data.ISSUE_CLS.length; i++) {
			clsList.addItem(Data.ISSUE_CLS[i]);
			if(iss.cls!=null && iss.cls.equals(Data.ISSUE_CLS[i])) {
				clsList.setSelectedIndex(i);
			}
		}
		
		
		
		VerticalPanel holder = new VerticalPanel();
		holder.setStyleName("site-padding");
		holder.setWidth("100%");

		if (!isClosed() && !isAssigned() && !isResolved()) {
			Widget warning = ClientUtil.getWarningRed(Ctrl.trans.notYetAssigned());
			holder.add(warning);
		}

		VerticalPanel form = new VerticalPanel();
		form.setSpacing(3);
		// form.setWidth("600px");

		form.add(getIDLine());
		form.add(getTitleLine());
		form.add(getCreationLine());
		form.add(getAttachsLine());
		form.add(getAssignLine());
		form.add(getResolveLine());

		if (!isEvent()) {
			form.add(getWeightLine());
			form.add(getCloseLine());
		}

		DisclosurePanel details = new DisclosurePanel(Ctrl.trans.details());
		details.setAnimationEnabled(true);
		details.setContent(form);
		details.setOpen(true);

		DisclosurePanel desc = new DisclosurePanel(Ctrl.trans.desc());
		desc.setAnimationEnabled(true);
		desc.setContent(getDescLine());
		desc.setOpen(true);

		DisclosurePanel comms = new DisclosurePanel(Ctrl.trans.comments());
		comms.setAnimationEnabled(true);
		comms.setOpen(true);
		comms.setWidth("100%");

		if (iss.dialog_uri != null && !iss.dialog_uri.isEmpty())
			comms.setContent(comments);

		DisclosurePanel tags = new DisclosurePanel(Ctrl.trans.tags());
		tags.setAnimationEnabled(true);
		tags.setOpen(true);
		tags.setWidth("100%");
		tags.setContent(pnlTags);

		holder.add(details);
		holder.add(desc);
		holder.add(comms);
		holder.add(tags);
		return holder;
	}

	private Widget getWeightLine() {

		FlowPanel fp = new FlowPanel();
		fp.add(new IssLabel(Ctrl.trans.weight()));

		final ListBox lb = new ListBox();
		lb.addItem("low", "10");
		lb.addItem("normal", "100");
		lb.addItem("high", "1000");

		if (iss.weight <= 10)
			lb.setSelectedIndex(0);
		else if (iss.weight <= 100)
			lb.setSelectedIndex(1);
		else
			lb.setSelectedIndex(2);

		lb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int val = Integer.parseInt(lb.getSelectedValue());
				ResourcesDao.setweight(val, iss.uri, new IntegerResponse() {
					@Override
					public void ready(Integer value) {
						Ctrl.reloadIssuesNav();
					}
				});
			}
		});

		fp.add(lb);

		return fp;
	}

	private Widget getDescLine() {
		FlowPanel d = new FlowPanel();
		d.add(htmlDesc);
		if (!isClosed())
			d.add(btnChangeDesc);

		return d;
	}

	private Widget getResolveLine() {
		FlowPanel fpa = new FlowPanel();
		fpa.add(new IssLabel(Ctrl.trans.solution()));
		if (isResolved()) {
			// fpa.add(new IssLabel(Ctrl.trans.resolved()));
			fpa.add(new HTML(" (" + Ctrl.trans.dateOf(Ctrl.trans.resolve()) + ":" + ClientUtil.fmtDate(iss.resolve_date)
					+ ")<br>"));
			fpa.add(new IssLabel(""));
			fpa.add(new HTML(iss.resolve_desc));
			fpa.add(btnChangeResolve);

		} else if (!isClosed()) {
			fpa.add(btnResolve);
		}

		return fpa;
	}

	private boolean isResolved() {
		return iss.resolve_date != null ? true : false;
	}

	private Widget getCloseLine() {

		IssLabel lblClose = new IssLabel("");

		FlowPanel fpa = new FlowPanel();

		if (isClosed()) {
			lblClose.setText(Ctrl.trans.isClosed(Ctrl.trans.issue()));
			btnClose.setText(Ctrl.trans.open());

			fpa.add(lblClose);
			fpa.add(btnClose);

			fpa.add(new HTML("(" + Ctrl.trans.closeDate() + ":" + ClientUtil.fmtDate(iss.close_date) + ")"));

		} else {

			// manager can close
			if (!isResolved() && one.isManager()) {
				lblClose.setText(Ctrl.trans.isOpen(Ctrl.trans.issue()));
				btnClose.setText(Ctrl.trans.close());

				setStyle(btnClose);

				fpa.add(lblClose);
				fpa.add(btnClose);
			}
		}
		return fpa;

	}

	private Widget getAssignLine() {

		FlowPanel fpa = new FlowPanel();
		fpa.add(new IssLabel(Ctrl.trans.assigned()));

		if (isAssigned()) {
			fpa.add(getUser(iss.assigned_to));
			fpa.add(new HTML("(" + Ctrl.trans.assignDate() + ":" + ClientUtil.fmtDate(iss.assign_date) + ")"));

			fpa.add(new IssLabel(Ctrl.trans.change()));
			fpa.add(lbUsers);
			fpa.add(btnAssign);

		} else if (!isClosed()) {

			fpa.add(lbUsers);
			fpa.add(btnAssign);

		}

		return fpa;
	}

	private Widget getAttachsLine() {
		FlowPanel fp = new FlowPanel();
		fp.add(new IssLabel(Ctrl.trans.attachments()));

		if (iss.dbfs != null && iss.dbfs.length > 0) {
			// there are attchs
			for (int i = 0; i < iss.dbfs.length; i++) {
				addFileToPanel(originalDbFileName(iss.dbfs[i]), iss.uri + "/getfile.rom?fn=" + iss.dbfs[i]);
			}

			attchsPanel.setSpacing(3);
			attchsPanel.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

			fp.add(attchsPanel);
		} else {
			fp.add(new HTML(Ctrl.trans.noAttachments()));
		}
		if (!isClosed())
			fp.add(btnFileUpload);

		return fp;

	}

	private Widget getCreationLine() {

		FlowPanel fp = new FlowPanel();
		fp.add(new IssLabel(Ctrl.trans.createdBy()));
		fp.add(getUser(iss.created_by));

		fp.add(new HTML("(" + Ctrl.trans.createDate() + ":" + ClientUtil.fmtDate(iss.creation_date) + ")"));

		return fp;
	}

	private Widget getTitleLine() {
		Label lbl = new Label(iss.title);

		FlowPanel fp = new FlowPanel();
		fp.add(new IssLabel(Ctrl.trans.title()));
		fp.add(lbl);
		if (!isClosed())
			fp.add(btnChangeTitle);

		return fp;

	}

	private void forChangeTitle() {
		btnChangeTitle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final AskForValueDlg dlg = new AskForValueDlg(Ctrl.trans.title(), iss.title, 3, 5, Ctrl.trans.save(),
						"");
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (!dlg.getValue().isEmpty()) {
							IssuesDao.changetitle(dlg.getValue(), iss.uri, new StringResponse() {
								public void ready(String value) {
									reloadIssue(true);
								};
							});

						}

					}
				});

			}
		});
	}

	private Widget getIDLine() {
		HTML id = new HTML(iss.uri.substring(iss.uri.lastIndexOf("/") + 1));
		setStyle(id);

		FlowPanel fp = new FlowPanel();
		fp.add(new IssLabel("ID"));
		fp.add(id);

		return fp;
	}

	private void addFileToPanel(String title, String uri) {

		Anchor a = new Anchor(title, uri);
		a.setTarget("_blank");

		attchsPanel.add(a);
	}

	public String originalDbFileName(String name) {
		if (name.indexOf("-") > 0) {
			int start = name.lastIndexOf('-');
			String encoded = name.substring(start + 1);
			return encoded;
		} else {
			return name;
		}

	}

	public Widget getUser(String id) {
		if (id == null || id.isEmpty())
			return new HTML();

		int foundIndex = -1;
		for (int j = 0; j < users.size(); j++) {
			String[] val = users.get(j);
			if (val[1] != null && val[1].equals(id)) {
				foundIndex = j;
				break;
			}
		}

		if (foundIndex >= 0) {
			final String[] cont = users.get(foundIndex);
			Anchor ancContact = new Anchor(cont[0] + " ");
			// setStyle(ancContact);
			ancContact.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// user name, contact id, role
					// u[0], u[1], role
					Sistem.outln(cont[1]);
					TabContact tc = new TabContact(cont[1], false);
					// TabUser tu = new TabUser(cont[0], cont[1]);
					Ctrl.openTab(cont[1], cont[0], tc, Data.HOME_COLOR);

				}
			});
			return ancContact;
		} else {
			HTML h = new HTML();
			// setStyle(h);
			return h;
		}

	}

	private void setStyle(Widget w) {
		w.setStyleName("iss-box");
		w.setWidth("100px");

	}

	private class IssLabel extends Label {
		public IssLabel(String string) {
			super(string);
			this.setStyleName("iss-label");
			this.setWidth("100px");

		}
	}

	@Override
	public void tagsChanged() {
		// comma seperated
		IssuesDao.changetags(pnlTags.getTags(), this.iss.uri, new StringResponse() {
			@Override
			public void ready(String value) {
				Ctrl.setStatus(Ctrl.trans.saved());
			}
		});

	}
}