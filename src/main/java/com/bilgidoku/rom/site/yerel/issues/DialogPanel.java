package com.bilgidoku.rom.site.yerel.issues;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Comments;
import com.bilgidoku.rom.gwt.araci.client.rom.CommentsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.CommentsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.one;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DialogPanel extends Composite {
	private final VerticalPanel dlg = new VerticalPanel();
	private final FormPanel form = new FormPanel();
	private final TextArea comm = new TextArea();
	private final Hidden hd = new Hidden();
	private final Button btnSend = new Button(Ctrl.trans.send());
	private final TabIssue parent;
	private String dialogUri;

	public DialogPanel(String uri, TabIssue tabIssue) {
		parent = tabIssue;
		this.dialogUri = uri;
		form.setAction(uri + "/comment.rom?outform=json");
		form.setMethod(FormPanel.METHOD_POST);
		hd.setName("comment");
		comm.setStyleName("site-innerform");

		if (uri != null)
			getDialog(uri);

		formUi();
		forSubmit();
		forSend();
		forKeyUpComment();

		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(dlg);
		vp.add(form);

		initWidget(vp);
	}

	private void forKeyUpComment() {
		comm.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (btnSend.isEnabled())
					return;
				btnSend.setEnabled(true);
			}
		});

	}

	private void forSend() {
		btnSend.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String str = comm.getValue();
				if (str.isEmpty())
					return;
				hd.setValue(str.replaceAll("(\r\n|\n)", "<br />"));
				form.submit();
			}
		});
	}

	private void forSubmit() {
		form.addSubmitHandler(new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				btnSend.setEnabled(false);
			}
		});
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				comm.setValue("");
				updateDialog(dialogUri);
			}
		});

	}

	public void updateDialog(String dialogUri) {
		if (dialogUri == null)
			return;
		this.dialogUri = dialogUri;
		dlg.clear();
		getDialog(dialogUri);
		form.setAction(dialogUri + "/comment.rom?outform=json");

	}

	private void getDialog(String dialogUri) {
		DialogsDao.comments(dialogUri, new CommentsResponse() {
			@Override
			public void array(List<Comments> myarr) {
				dlgUi(myarr);
			}

		});

	}

	private void formUi() {

		comm.setSize("99%", "200px");
		btnSend.setText(Ctrl.trans.send());

		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(new HTML(Ctrl.trans.comment()));
		vp.add(comm);
		vp.add(btnSend);
		vp.add(hd);

		form.add(vp);

	}

	private void dlgUi(List<Comments> myarr) {
		for (int i = 0; i < myarr.size(); i++) {
			Comments comm = myarr.get(i);
			dlg.add(getComment(comm));
		}
	}

	private Widget getComment(final Comments co) {
		final HTML comLine = new HTML(co.comment);
		SiteToolbarButton btnChange = new SiteToolbarButton("/_local/images/common/pencil.png", "", "", "");

		FlowPanel fp = new FlowPanel();
		fp.add(comLine);

		if (co.contact.equals(one.userContactId)) {
			fp.add(btnChange);
			btnChange.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final AskForValueDlg dlg = new AskForValueDlg(Ctrl.trans.desc(), co.comment, 3, 4,
							Ctrl.trans.save(), "");
					dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
						@Override
						public void onClose(CloseEvent<PopupPanel> event) {
							String text = dlg.getValue();
							if (text != null && !text.isEmpty())
								CommentsDao.change(co.lang_id, co.dialog_id, co.contact, text, co.cmd, co.mime,
										co.bymail, co.approved, co.refer_comment, co.likes, co.dislikes, co.onpage,
										co.uri, new StringResponse() {
									public void ready(String value) {
										comLine.setHTML(dlg.getValue());
									};
								});

						}
					});

				}
			});
		}

		FlexTable vp = new FlexTable();
		vp.setWidth("530px");
		vp.setCellSpacing(3);

		vp.setWidget(0, 0, parent.getUser(co.contact));
		vp.setWidget(0, 1, new HTML(ClientUtil.fmtDate(co.creation_date)));

		vp.setWidget(1, 0, fp);

		vp.setWidget(2, 0, new HTML("<hr>"));

		vp.getFlexCellFormatter().setColSpan(1, 0, 2);
		vp.getFlexCellFormatter().setColSpan(2, 0, 2);

		return vp;
	}

}
