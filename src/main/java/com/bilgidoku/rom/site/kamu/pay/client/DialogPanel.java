package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Comments;
import com.bilgidoku.rom.gwt.araci.client.rom.CommentsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DialogPanel extends Composite {
	private final VerticalPanel dlg = new VerticalPanel();
	private final FormPanel form = new FormPanel();
	private final TextArea comm = new TextArea();
	private final SiteButton btnSend = new SiteButton(pay.trans.send(), "");
	private final PnlIssue parent;
	private String dialogUri;

	public DialogPanel(String uri, PnlIssue tabIssue) {
		parent = tabIssue;
		this.dialogUri = uri;				
		form.setAction(uri + "/comment.rom?outform=json");
		form.setMethod(FormPanel.METHOD_POST);

		if (uri != null)
			getDialog(uri);

		formUi();
		forSubmit();
		forSend();
		forKeyUpComment();

		VerticalPanel vp = new VerticalPanel();
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

		comm.setName("comment");
		comm.setSize("95%", "100px");
		btnSend.setText(pay.trans.send());

		FlexTable ft = new FlexTable();
		ft.setWidth("530px");
		ft.setHTML(0, 0, pay.trans.comment());
		ft.setWidget(0, 1, comm);
		ft.setWidget(1, 1, btnSend);
		form.add(ft);

	}

	private void dlgUi(List<Comments> myarr) {
		for (int i = 0; i < myarr.size(); i++) {
			Comments comm = myarr.get(i);
			dlg.add(getComment(comm));
		}
	}

	private Widget getComment(Comments co) {

		FlexTable vp = new FlexTable();
		vp.setWidth("530px");
		vp.setCellSpacing(3);

		vp.setHTML(0, 0, co.contact);
		vp.setWidget(0, 1, new HTML(ClientUtil.fmtDate(co.creation_date)));

		vp.setWidget(1, 0, new HTML(co.comment));
		vp.setWidget(2, 0, new HTML("<hr>"));
		vp.getFlexCellFormatter().setColSpan(1, 0, 2);
		vp.getFlexCellFormatter().setColSpan(2, 0, 2);
		return vp;
	}

}
