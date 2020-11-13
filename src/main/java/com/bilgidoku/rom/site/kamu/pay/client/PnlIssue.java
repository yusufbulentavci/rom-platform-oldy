package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.HashMap;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.Issues;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesResponse;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PnlIssue extends Composite implements UploadFile {

	private final CheckBox close = new CheckBox();
	private Issues iss;
	private final SiteButton btnFileUpload = new SiteButton(pay.trans.fileUpload(), "");
	private final DialogPanel comments;
	private final HashMap<String, Contacts> contacts;
	private final VerticalPanel files = new VerticalPanel();
	private final SimplePanel holder = new SimplePanel();
	private final SiteButton btnChangeDesc = new SiteButton(pay.trans.changeDesc(), "");
	private final SiteButton btnChangeTitle = new SiteButton(pay.trans.changeTitle(), "");
	private HTML htmlDesc = new HTML("");

	public PnlIssue(Issues issu, HashMap<String, Contacts> hashMap) {

		// find names from contacts
		this.iss = issu;
		this.contacts = hashMap;

		// loadUsers(user);
		htmlDesc.setHTML(iss.description);

		comments = new DialogPanel(iss.dialog_uri, this);
		forClose();
		forFileUpload();
		forChangeDesc();
		forChangeTitle();
		holder.add(ui(iss));

		initWidget(holder);
	}

	private void forChangeTitle() {
		btnChangeTitle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final AskForValueDlg dlg = new AskForValueDlg(pay.trans.title(), iss.title, 1, 4, pay.trans.save());
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (!dlg.getValue().isEmpty()) {
							IssuesDao.changetitle(dlg.getValue(), iss.uri, new StringResponse() {
								public void ready(String value) {
									reloadIssue();
								};
							});
						}

					}
				});

			}
		});

	}

	private void forChangeDesc() {
		btnChangeDesc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final AskForValueDlg dlg = new AskForValueDlg(pay.trans.description(), iss.description, 3, 4, pay.trans
						.save());
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (!dlg.getValue().isEmpty()) {
							IssuesDao.changedesc(dlg.getValue(), iss.uri, new StringResponse() {
								public void ready(String value) {
									htmlDesc.setHTML(dlg.getValue());
									reloadIssue();
								};
							});
						}
					}
				});

			}
		});

	}

	// private void loadUsers(List<String[]> myarr) {
	// lbUsers.addItem("---" + pay.trans.users() + "---", "");
	// for (int i = 0; i < myarr.size(); i++) {
	// String[] u = myarr.get(i);
	// lbUsers.addItem(u[0], u[1]);
	// }
	//
	// }

	private void forFileUpload() {
		btnFileUpload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FileUploadDialog dlg = new FileUploadDialog(PnlIssue.this, iss.uri + "/addfile.rom");
				dlg.show();
			}
		});

	}

	private void forClose() {
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (close.getValue()) {
					boolean b = Window.confirm(pay.trans.closeIssue());
					if (b) {
						IssuesDao.close(null, null, null, null, null, null, iss.uri, new StringResponse() {
						});
					}
				} else {
					boolean b = Window.confirm(pay.trans.reopenIssue());
					if (b) {
						IssuesDao.reopen(iss.uri, new StringResponse() {
						});
					}
				}
			}
		});

	}


	protected void reloadIssue() {
		// reload issue
		IssuesDao.get(iss.uri, new IssuesResponse() {
			@Override
			public void ready(Issues value) {
				iss = value;
				files.clear();
				holder.clear();
				holder.add(ui(value));
				comments.updateDialog(value.dialog_uri);
			}
		});

	}

	@Override
	public void fileAdded(String dbfs, String fileName) {
		reloadIssue();
	}

	private VerticalPanel ui(final Issues iss) {

		FlexTable pnlDetails = new FlexTable();
		pnlDetails.setCellPadding(3);
		pnlDetails.setWidth("600px");

		pnlDetails.setWidget(0, 0, new Label("ID:"));
		pnlDetails.setHTML(0, 1, iss.uri.substring(iss.uri.lastIndexOf("/") + 1));

		HorizontalPanel p = new HorizontalPanel();
		p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		p.add(new HTML(iss.title + "&nbsp;&nbsp;"));
		p.add(btnChangeTitle);

		pnlDetails.setWidget(0, 2, new Label(pay.trans.title() + ":"));
		pnlDetails.setWidget(0, 3, p);

		pnlDetails.setWidget(1, 0, new Label(pay.trans.createdBy() + ":"));
		pnlDetails.setHTML(1, 1, getInfo(iss.created_by));

		pnlDetails.setWidget(1, 2, new Label(pay.trans.createDate() + ":"));
		pnlDetails.setHTML(1, 3, ClientUtil.fmtDate(iss.creation_date));

		pnlDetails.setWidget(2, 0, new Label(pay.trans.assigned() + ":"));
		if (iss.assigned_to == null || iss.assigned_to.isEmpty()) {

			pnlDetails.setHTML(2, 1, "<span style=\"color:red;font-weight:bold;\">" + pay.trans.notAssigned()
					+ "</span>");

		} else {

			pnlDetails.setHTML(2, 1, getInfo(iss.assigned_to));

			pnlDetails.setWidget(2, 2, new Label(pay.trans.assignDate() + ":"));
			pnlDetails.setHTML(2, 3, ClientUtil.fmtDate(iss.assign_date));

		}

		if (iss.close_date != null)
			close.setValue(true);

		pnlDetails.setWidget(3, 0, new Label(pay.trans.close() + ":"));
		pnlDetails.setWidget(3, 1, close);

		pnlDetails.setWidget(3, 2, new Label(pay.trans.closeDate() + ":"));
		pnlDetails.setHTML(3, 3, ClientUtil.fmtDate(iss.close_date));

		if (iss.close_date == null) {
			pnlDetails.getFlexCellFormatter().setVisible(3, 2, false);
			pnlDetails.getFlexCellFormatter().setVisible(3, 3, false);

		}
		// file attachments
		// StringBuffer attas = new StringBuffer();
		if (iss.dbfs != null)
			for (int i = 0; i < iss.dbfs.length; i++) {
				addFileToPanel(originalDbFileName(iss.dbfs[i]), iss.uri + "/getfile.rom?fn=" + iss.dbfs[i]);
			}

		files.setSpacing(3);

		HorizontalPanel fp = new HorizontalPanel();
		fp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		fp.setSpacing(3);
		fp.add(files);
		fp.add(btnFileUpload);

		pnlDetails.setWidget(4, 0, new Label(pay.trans.attachments()));
		pnlDetails.setWidget(4, 1, fp);

		pnlDetails.getFlexCellFormatter().setColSpan(4, 1, 3);

		String sDesc = iss.description;
		if (sDesc == null || sDesc.isEmpty()) {
			sDesc = "<span style=\"color:red;\">" + pay.trans.noDescription() + "</span>";
		}

		htmlDesc.setHTML(sDesc);
		htmlDesc.setSize("500px", "50px");

		FlexTable ftDesc = new FlexTable();
		ftDesc.setWidget(0, 0, new ScrollPanel(htmlDesc));
		ftDesc.setWidget(0, 1, btnChangeDesc);

		FlowPanel d = new FlowPanel();
		d.add(ftDesc);
		d.add(btnChangeDesc);

		DisclosurePanel desc = new DisclosurePanel(pay.trans.desc());
		desc.setAnimationEnabled(true);
		desc.setContent(d);
		desc.setOpen(true);

		DisclosurePanel details = new DisclosurePanel(pay.trans.details());
		details.setAnimationEnabled(true);
		details.setContent(pnlDetails);
		details.setOpen(true);

		DisclosurePanel comms = new DisclosurePanel(pay.trans.comments());
		comms.setAnimationEnabled(true);
		comms.setOpen(true);

		if (iss.dialog_uri != null && !iss.dialog_uri.isEmpty())
			comms.setContent(comments);
		else
			comms.setContent(new HTML("<span style=\"color:red;\">" + pay.trans.noComments() + "</span>"));

		VerticalPanel holder = new VerticalPanel();
		holder.setStyleName("site-padding");

		holder.add(details);
		holder.add(desc);
		holder.add(comms);
		return holder;
	}

	private String getInfo(String created_by) {
		if (created_by == null)
			return "";
		Contacts con = contacts.get(created_by);

		String s = "";
		try {
			if (con.first_name != null)
				s = con.first_name;

			if (con.last_name != null)
				s = s + " " + con.last_name;

			if (con.email != null)
				s = s + "\n (" + con.email + ")";
			
		} catch (Exception e) {
			Sistem.outln(e.getMessage());
		}

		return s;
	}

	private void addFileToPanel(String title, String uri) {
		Anchor a = new Anchor(title, uri);
		a.setTarget("_blank");
		files.add(a);
	}

	public String originalDbFileName(String name) {
		if (name.indexOf("-") > 0) {
			int start = name.lastIndexOf('-');
			String encoded = name.substring(start + 1);
			return encoded;
		} else {
			return name;
		}

		// return new String(Base64Decode.decode(encoded));

	}

}