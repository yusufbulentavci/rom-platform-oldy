package com.bilgidoku.rom.site.kamu.login.client;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class DlgLogin extends DialogBox {

	private String okButton;
	
	
	public DlgLogin(String title, String titleImg, String ok) {
		this.okButton = ok;
		this.setHTML(getDlgCaption(titleImg, title));
		this.setAutoHideEnabled(false);
		this.setModal(false);
		this.hide();
		this.addStyleName("site-logindlg");
	}

	public void run() {
		Button b = new Button();
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cancel();
				DlgLogin.this.hide();
			}
		});
		b.setStyleName("site-closebutton");

		VerticalPanel gp = new VerticalPanel();
		gp.setSpacing(4);
		// gp.setStyleName("site-chatdlgin");
		gp.add(ui());
		gp.add(b);

		if (okButton != null) {
			SiteButton btnOk = new SiteButton("/_public/images/bar/check.png", null, okButton);
			btnOk.setText(okButton);
			btnOk.setTitle(okButton);
			btnOk.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ok();
					// okPoints(event.getClientX(), event.getClientY());
					DlgLogin.this.hide();
				}
			});
			gp.add(btnOk);
		}

		this.setWidget(gp);

	}

	
	public abstract Widget ui();

	public static SafeHtml getDlgCaption(String img, String title) {
		if (img == null && title == null)
			return null;
		SafeHtmlBuilder bld = new SafeHtmlBuilder();
		bld.appendHtmlConstant("<div class='site-chatdlgheader'>");
		if (img != null)
			bld.appendHtmlConstant("<img src='" + img + "' style='vertical-align:top;padding-right:4px;'/>");
		bld.appendHtmlConstant(title);
		bld.appendHtmlConstant("</div>");
		return bld.toSafeHtml();
	}

	public abstract void cancel();

	public abstract void ok();
	
//	public void okPoints(int clientX, int clientY) {
//	}

}
