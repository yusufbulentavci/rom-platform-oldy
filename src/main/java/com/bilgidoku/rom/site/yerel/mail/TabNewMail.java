package com.bilgidoku.rom.site.yerel.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsDao;
import com.bilgidoku.rom.gwt.client.util.browse.image.FileUploadDialog;
import com.bilgidoku.rom.gwt.client.util.browse.image.UploadFile;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.HtmlEditAreaWrapper;
import com.bilgidoku.rom.site.yerel.common.ImageAnchor;
import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;
import com.bilgidoku.rom.site.yerel.mail.core.Mime;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;

public class TabNewMail extends Composite implements UploadFile {
	
	private final FlexTable form = new FlexTable();

	private final Button btnSend = new Button(ClientUtil.imageItemHTML("/_local/images/common/send_mail.png", Ctrl.trans.send()));

	private final Button btnSave = new Button(ClientUtil.imageItemHTML("/_local/images/common/save.png", Ctrl.trans.save()));

	private final Button btnFileUpload = new Button(ClientUtil.imageItemHTML("/_local/images/common/attachment.png", Ctrl.trans.attach()));

	private TextBoxMailAddr txtTo;
	private TextBoxMailAddr txtCc;
	private TextBoxMailAddr txtBcc;

	private final TextBox txtSubject = new TextBox();

	private final HtmlEditAreaWrapper txtBody;
	private final Anchor addCc = new Anchor(Ctrl.trans.add(Ctrl.trans.cc()));
	private final Anchor addBcc = new Anchor(Ctrl.trans.add(Ctrl.trans.bcc()));
	private final Map<String, String> attachs = new HashMap<String, String>();
	private final FlowPanel attachPanel = new FlowPanel();
	protected String uri;
	private boolean saving = false;
	private Mime saved = null;

	public TabNewMail(String subject, final InternetAddress[] to, final InternetAddress[] cc, MailWrap email,
			boolean isForward, boolean isReply) {

		ContactsDao.list("", "/_/co", new ContactsResponse() {
			@Override
			public void array(List<Contacts> myarr) {
				txtTo = new TextBoxMailAddr(myarr);
				txtCc = new TextBoxMailAddr(myarr);
				txtBcc = new TextBoxMailAddr(myarr);
				initToAndCC(to, cc);
				uiForm();
			}
		});

		txtBody = HtmlEditAreaWrapper.create((email == null)?null:email.showHtml());

		txtSubject.setValue(subject);

		forSaveAsDraft();
		forAddCc();
		forAddBcc();
		forSend();
		forAttach();
		
		ScrollPanel sp = new ScrollPanel(txtBody);
		sp.getElement().getFirstChildElement().getStyle().setProperty("height", "100%");
		sp.setHeight("100%");
		
		DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		dock.addNorth(form, 196);
		dock.add(sp);

		initWidget(dock);
	}

	private void forSaveAsDraft() {
		btnSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				checkSave(new Runnable() {
					@Override
					public void run() {
						Ctrl.setStatus(Ctrl.trans.saved());
						Ctrl.reloadMailNav();
						TabNewMail.this.removeFromParent();
					}
				});

			}
		});

	}

	public TabNewMail(String subject, InternetAddress[] to, InternetAddress[] cc, MailWrap mail) {
		this(subject, to, cc, mail, false, false);
	}

	void checkSave(final Runnable run) {
		if (txtTo.getEMails() == null) {
			Window.alert(Ctrl.trans.emptyValue(Ctrl.trans.to()));
			return;			
		}
		
		if (!this.saving) {
			InternetAddress[] tos = txtTo.getEMails();
			InternetAddress[] ccs = txtCc.getEMails();
			InternetAddress[] bccs = txtBcc.getEMails();

			final Mime m = MailUtil.buildMime(txtSubject.getValue(), "text/html", txtBody.getText(), this.attachs, tos, ccs, bccs);
			if (saved != null && saved.toJson().equals(m.toJson()))
				return;

			this.saving = true;
			try {
				if (uri == null) {
					MailsDao.neww(new Json(m.toJson()), MailUtil.draftMailbox(), new StringResponse() {
						public void ready(String value) {
							uri = value;
							saved = m;
							saving = false;
							run.run();
						}

						public void beforeLeave() {
							saving = false;
						}
					});
				} else {
					MailsDao.change(new Json(m.toJson()), uri, new StringResponse() {
						public void ready(String value) {
							saved = m;
							run.run();
							saving = false;
						}

						public void beforeLeave() {
							saving = false;
						}
					});
				}				
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				saving = false;
			}
		}
	}

	private void forSend() {
		btnSend.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				checkSave(new Runnable() {
					@Override
					public void run() {
						send();
					}
				});
			}
			
			private void send() {
				MailsDao.send(null, uri, new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(Ctrl.trans.mailSentSuccess());
						Ctrl.reloadMailNav();
						TabNewMail.this.removeFromParent();
					}
				});
			}

			
		});
	}

	private void forAttach() {
		btnFileUpload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				checkSave(new Runnable() {
					@Override
					public void run() {
						FileUploadDialog dlg = new FileUploadDialog(TabNewMail.this, uri + "/addfile.rom");
						dlg.show();
					}
				});
			}
		});
	}


	private void initToAndCC(InternetAddress[] to, InternetAddress[] cc) {
		if (to != null) {
			for (int i = 0; i < to.length; i++) {
				InternetAddress ia = to[i];
				txtTo.addItem(MailUtil.getAddr(ia));
			}
		}

		if (cc != null) {
			for (int i = 0; i < cc.length; i++) {
				InternetAddress ia = to[i];
				txtCc.addItem(MailUtil.getAddr(ia));
			}
		}
	}
		
	private void showAttachs() {
		attachPanel.clear();

		for (final Entry<String, String> v : attachs.entrySet()) {
			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(3);
			hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

			ImageAnchor remove = new ImageAnchor();
			remove.changeResource("/_local/images/common/cross_small.png");
			remove.setTitle(Ctrl.trans.deleteItem());

			remove.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					TabNewMail.this.removeAttach(v.getKey());
				}
			});
			hp.add(new Label(v.getValue()));
			hp.add(remove);
			attachPanel.add(hp);
		}
	}

	protected void removeAttach(String value) {
		attachs.remove(value);
		showAttachs();
	}

	private void forAddBcc() {
		addBcc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.setHTML(2, 0, Ctrl.trans.bcc());
				form.setWidget(2, 1, txtBcc);
				addBcc.removeFromParent();
			}
		});
	}

	private void forAddCc() {
		addCc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.setHTML(2, 0, Ctrl.trans.cc());
				form.setWidget(2, 1, txtCc);
				addCc.removeFromParent();
				addBcc.removeFromParent();
				form.setWidget(3, 1, addBcc);
			}
		});
	}

	private void uiForm() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(addCc);
		hp.add(addBcc);
		txtTo.setWidth("100%");
		txtCc.setWidth("100%");
		txtBcc.setWidth("100%");
		txtSubject.setWidth("100%");

		txtBody.setSize("100%", "100%");

		form.setSize("100%", "50px");

//		form.setCellPadding(2);

		form.setBorderWidth(0);
		form.setHTML(0, 0, Ctrl.trans.to());
		form.setWidget(0, 1, txtTo);
		form.setHTML(1, 0, Ctrl.trans.cc());
		form.setWidget(1, 1, txtCc);
		form.setWidget(1, 2, addBcc);

		// form.setWidget(1, 1, hp);
		HorizontalPanel fp = new HorizontalPanel();
		fp.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		fp.add(btnFileUpload);
		fp.add(attachPanel);

		btnSend.setSize("60px", "45px");
		btnSave.setSize("60px", "45px");
		
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setSpacing(3);
		buttons.add(btnSend);
		buttons.add(btnSave);

		form.setHTML(4, 0, Ctrl.trans.subject());
		form.setWidget(4, 1, txtSubject);
		form.setHTML(5, 0, Ctrl.trans.attachments());
		form.setWidget(5, 1, fp);
		form.setWidget(6, 1, buttons);

		form.getFlexCellFormatter().setWidth(0, 0, "100px");
	}

	@Override
	public void fileUploaded(String dbfs, String fileName) {
		this.attachs.put(dbfs, fileName);
		showAttachs();

	}
}
