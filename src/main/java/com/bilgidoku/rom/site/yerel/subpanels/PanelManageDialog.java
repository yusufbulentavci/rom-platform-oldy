package com.bilgidoku.rom.site.yerel.subpanels;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Dialogs;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.pagedlgs.PageDlg;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelManageDialog extends Composite {
	// private final static String[] rules = new String[] {
	// "Guests see, contacts write", "Guests see, users write",
	// "Contacts see, contacts write", "Contacts see, users write",
	// "Users see, users write", "Custom" };
	// private final static long[] ruleMask = new long[] {
	// CRoleMask.makeRoleMask(CRoleMask.admin, CRoleMask.candoall)
	// | CRoleMask.makeRoleMask(CRoleMask.guest, CRoleMask.canread)
	// | CRoleMask.makeRoleMask(CRoleMask.contact, CRoleMask.canreadwrite),
	// CRoleMask.makeRoleMask(CRoleMask.admin, CRoleMask.candoall)
	// | CRoleMask.makeRoleMask(CRoleMask.guest, CRoleMask.canread)
	// | CRoleMask.makeRoleMask(CRoleMask.contact, CRoleMask.canread)
	// | CRoleMask.makeRoleMask(CRoleMask.user, CRoleMask.canreadwrite),
	// CRoleMask.makeRoleMask(CRoleMask.admin, CRoleMask.candoall)
	// | CRoleMask.makeRoleMask(CRoleMask.contact, CRoleMask.canreadwrite),
	// CRoleMask.makeRoleMask(CRoleMask.admin, CRoleMask.candoall)
	// | CRoleMask.makeRoleMask(CRoleMask.contact, CRoleMask.canread)
	// | CRoleMask.makeRoleMask(CRoleMask.user, CRoleMask.canreadwrite),
	// CRoleMask.makeRoleMask(CRoleMask.admin, CRoleMask.candoall)
	// | CRoleMask.makeRoleMask(CRoleMask.user, CRoleMask.canreadwrite) };

	private final VerticalPanel pnlHolder = new VerticalPanel();
	private final VerticalPanel pnlInner = new VerticalPanel();
	// private final Anchor seeAllLink = getSeeAllLink();
	private final Button btnEnableDisableComments = new Button(Ctrl.trans.activateComment());
	private final CheckBox cbNeedsApproval = new CheckBox(Ctrl.trans.needaproval());
	private final CheckBox cbIsCafe = new CheckBox("Is cafe");

	// private final ListBox lbCommentRules = new ListBox();

	private String dialogUri = null;
	private String writingUri;
	private String writingTitle;
	private PageDlg parent;
	private boolean isCommentsActive = false;
	private boolean isCafe = false;

	public PanelManageDialog(PageDlg parent1) {
		parent = parent1;
		// for (int i = 0; i < ruleMask.length; i++) {
		// lbCommentRules.addItem(rules[i], ruleMask[i] + "");
		// }
		ui();

		pnlInner.setVisible(false);

		forEnableComments();
		forNeedsApproval();
		forCafe();

		pnlHolder.setHeight("50px");
		pnlHolder.setStyleName("site-holder");

		initWidget(pnlHolder);
	}

	private void forEnableComments() {
		btnEnableDisableComments.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!isCommentsActive) {
					createDialog();
				} else {
					deleteDialog();
				}
			}
		});
	}

	public void load(String uri, String title, String dlgUri) {
		this.writingUri = uri;
		this.writingTitle = title;
		this.dialogUri = dlgUri;

		if (dlgUri == null || dlgUri.isEmpty()) {
			return;
		}
		DialogsDao.get(dlgUri, new DialogsResponse() {
			@Override
			public void ready(Dialogs value) {
				// TODO check
				isCommentsActive = true;
				cbNeedsApproval.setValue(value.approval);
				cbIsCafe.setValue(value.cafe);
				commExists(true);
				// if (value.mask == null) {
				// PanelManageDialog.this.lbCommentRules.setSelectedIndex(5);
				// } else {
				// for (int i = 0; i < ruleMask.length; i++) {
				// if (value.mask == ruleMask[i]) {
				// PanelManageDialog.this.lbCommentRules.setSelectedIndex(i);
				// break;
				// }
				// }
				// }

			}
		});

	}

	private void forNeedsApproval() {
		cbNeedsApproval.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CheckBox cb = (CheckBox) event.getSource();
				if (cb.getValue()) {
					noNeedApproval();
				} else {
					needsApproval();
				}
			}

		});

	}

	private void forCafe() {
		cbIsCafe.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CheckBox cb = (CheckBox) event.getSource();
				DialogsDao.setcafe(cb.getValue(), dialogUri, new BooleanResponse() {
				});
			}

		});

	}

	protected void noNeedApproval() {
		if (Window.confirm(Ctrl.trans.confirmCommentApprove())) {
			DialogsDao.change(false, true, true, false, false, false, false, false, null, dialogUri,
					new DialogsResponse() {

					});
			ResourcesDao.setmask(Data.WRITING_NOGUEST_MASK, writingUri, new StringResponse() {
				@Override
				public void ready(String value) {
				}
			});
			
			
		}

	}

	protected void needsApproval() {
		if (Window.confirm(Ctrl.trans.confirmCommentNoApprove())) {
			DialogsDao.change(false, false, true, false, false, false, false, false, null, dialogUri,
					new DialogsResponse() {

					});
		}
	}

	private void ui() {
		pnlInner.getElement().setAttribute("cellpadding", "7");
		pnlInner.setVisible(false);
		// seeAllLink.setVisible(false);
		// btnEnableDisableComments.setValue(false);

		// pnlInner.add(lbCommentRules);
		pnlInner.add(cbNeedsApproval);
		pnlInner.add(cbIsCafe);
		pnlInner.add(getSeeWaitingLink());

		pnlHolder.add(btnEnableDisableComments);
		pnlHolder.add(pnlInner);
		// pnlHolder.add(seeAllLink);

	}

	// private Anchor getSeeAllLink() {
	// // open comments tab
	// Anchor commentManage = new Anchor(Ctrl.trans.seeAllComments());
	// commentManage.addClickHandler(new ClickHandler() {
	// @Override
	// public void onClick(ClickEvent event) {
	// try {
	// com.bilgidoku.rom.shared.json.JSONObject jo = new
	// com.bilgidoku.rom.shared.json.JSONObject();
	// jo.put("cmd", "focus");
	// jo.put("cls", "allcomments");
	// jo.put("url", dialogUri + "!!" + writingTitle);
	// RomWebCom.postToParent(jo);
	//
	// } catch (RunException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// });
	// return commentManage;
	// }

	private Widget getSeeWaitingLink() {
		// open comments tab
		Anchor commentManage = new Anchor(Ctrl.trans.seeAprovalWaitingComments());
		commentManage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RomEntryPoint.com().post("*wnd.focus", "uri", dialogUri + "!!" + writingTitle);
				// RomWebCom.parentFocusesItem("waitingcomments", dialogUri +
				// "!!" + writingTitle);
				// try {
				// com.bilgidoku.rom.shared.json.JSONObject jo = new
				// com.bilgidoku.rom.shared.json.JSONObject();
				// jo.put("cmd", "focus");
				// jo.put("cls", "waitingcomments");
				// jo.put("url", dialogUri + "!!" + writingTitle);
				// RomWebCom.postToParent(jo);
				//
				// } catch (RunException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

			}
		});
		return commentManage;
	}

	public String getDialogUri() {
		return dialogUri;
	}

	public Boolean getNeedsApproval() {
		return cbNeedsApproval.getValue();
	}

	private void commExists(boolean b) {
		isCommentsActive = b;
		if (b) {
			btnEnableDisableComments.setText(Ctrl.trans.disableComments());
		} else {
			btnEnableDisableComments.setText(Ctrl.trans.activateComment());
		}
		pnlInner.setVisible(b);
		// seeAllLink.setVisible(b);
	}

	public void deleteDialog() {
		if (Window.confirm(Ctrl.trans.confirmDeleteDialog())) {
			// boxer.startSpinner();
			WritingsDao.deletedialog(writingUri, new StringResponse() {
				@Override
				public void ready(String value) {
					dialogUri = null;
					commExists(false);
					cbNeedsApproval.setValue(false);
					// preview
					parent.commentsChanged(null);
				}

				@Override
				public void err(int statusCode, String statusText, Throwable exception) {
					btnEnableDisableComments.setEnabled(true);
				}
			});
		}
	}

	private void createDialog() {
		// boxer.startSpinner();
		WritingsDao.createdialog(false, true, true, false, false, false, false, false, null, writingUri,
				new StringResponse() {
					@Override
					public void ready(String value) {
						dialogUri = value;
						commExists(true);
						cbNeedsApproval.setValue(true);
						// call preview
						parent.commentsChanged(value);
					}
				});
	}

}
