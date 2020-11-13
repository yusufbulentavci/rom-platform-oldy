package com.bilgidoku.rom.site.yerel.mail;

import java.util.HashSet;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;
import com.bilgidoku.rom.site.yerel.mail.widgets.MailAddrs;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TabReadMail extends Composite implements TabMailView {

	private final String[] altOrder = {  "text/html", "text/plain"};
	private final MailWrap email;
	private final String mailBoxUri;
	private final NavMailBox nav;

	public TabReadMail(MailWrap email, String mailBoxName, NavMailBox nav) {
		this.email = email;
		this.mailBoxUri = mailBoxName;
		this.nav = nav;
		if (!email.isRead()) {
			//okunmamışsa 
			nav.markAsRead(email);
		}
		
		DockLayoutPanel dock = new DockLayoutPanel(Unit.EM);
		dock.addNorth(new TabMailToolbar(this, getType(), true), 2.8);

		dock.add(new ScrollPanel(ui(email)));

		initWidget(dock);
		
	}

	private HTML body;

	private VerticalPanel ui(MailWrap email) {

		FlexTable ft = new FlexTable();

		ft.setHTML(0, 0, email.getSubject());
		ft.setHTML(1, 0, Ctrl.trans.from().toUpperCase() + ":");
		ft.setWidget(1, 1, new MailAddrs(email.getFrom()));

		ft.setHTML(1, 2, DateTimeFormat.getMediumDateTimeFormat().format(email.getDate()));

		ft.setHTML(2, 0, Ctrl.trans.to().toUpperCase() + ":");
		ft.setWidget(2, 1, new MailAddrs(email.getTo()));

		if (email.getCc() != null && email.getCc().length > 0) {
			ft.setHTML(3, 0, Ctrl.trans.cc().toUpperCase() + ":");
			ft.setWidget(3, 1, new MailAddrs(email.getCc()));
		}
		if (email.getBcc() != null && email.getBcc().length > 0) {
			ft.setHTML(4, 0, Ctrl.trans.bcc() + ":");
			ft.setWidget(4, 1, new MailAddrs(email.getBcc()));
		}

		
//TODO: alternatifler doğru gösterilinceye dek bu şekilde çalışacak/bilo
		body = new HTML(email.showHtml());
//		boolean alt = email.getMime().isMultiAlternative();
//
//		if (alt) {
//			Set<String> alts = email.getMime().getAlternatives();
//			RadioButton rbPlain = new RadioButton("type", "Plain");
//			RadioButton rbHTML = new RadioButton("type", "HTML");
//			forPlain(rbPlain);
//			forHTML(rbHTML);
//
//			HorizontalPanel hp = new HorizontalPanel();
//			hp.add(rbPlain);
//			hp.add(rbHTML);
//
//			ft.setWidget(5, 1, hp);
//
//			String selAlt = getFirstAlt(alts);
//			Ctrl.setStatus(selAlt);
//			
////			if (selAlt != null && selAlt.equals("text/plain")) {				
////				rbPlain.setValue(true);
////				body = new HTML(email.showAlternative("text/plain"));				
////			} else if (selAlt != null && selAlt.equals("text/html")) {
//				rbHTML.setValue(true);
//				body = new HTML(email.showHtml());
////			} else {
////				body = new HTML("Invalid mail format, please see original!");
////			}
//			
//		} else {
//			body = new HTML(email.showHtml());
//		}

		ft.setStyleName("gwt-RichTextToolbar");
		ft.addStyleName("site-padding");
		ft.setWidth("100%");
//		ft.setHeight("50px");

		ft.getFlexCellFormatter().setWidth(1, 0, "75px");
		ft.getFlexCellFormatter().setColSpan(0, 0, 3);
		ft.getFlexCellFormatter().setStyleName(0, 0, "site-bold");

		body.setStyleName("site-padding");

		VerticalPanel holder = new VerticalPanel();
		holder.setWidth("99%");
		holder.add(ft);
		holder.add(body);

		return holder;
	}

	private String getFirstAlt(Set<String> alts) {
		if (alts == null)
			return null;
		for (String string : altOrder) {
			if (alts.contains(string))
				return string;
		}
		return null;
	}

	private void forHTML(final RadioButton rbHTML) {
		rbHTML.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (rbHTML.getValue())
					body = new HTML(email.showHtml());
//				else
//					body = new HTML(email.showAlternative("text/plain"));
			}
		});
	}

	private void forPlain(final RadioButton rbPlain) {
		rbPlain.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (rbPlain.getValue())
					body = new HTML(email.showAlternative("text/plain"));
//				else
//					body = new HTML(email.showHtml());
			}
		});
	}

	@Override
	public Set<MailWrap> getMailItems() {
		Set<MailWrap> list = new HashSet<MailWrap>();
		list.add(this.email);
		return list;
	}

	@Override
	public void markAsReaded(MailWrap email) {
		Ctrl.setStatus("mail from " + email.getFromName() + " is marked as readed");
		nav.markAsReaded(true);
	}

	@Override
	public void markAsUnReaded(MailWrap email) {
		Ctrl.setStatus("mail from " + email.getFromName() + " is marked as unreaded");
		nav.markAsReaded(false);

	}

	@Override
	public void mailDeleted(MailWrap email) {
		Ctrl.setStatus("mail from " + email.getFromName() + " is deleted");
		Ctrl.closeTab(email.getUri());
		nav.mailsDeleted();
	}

	@Override
	public void moved(MailWrap email, String target) {
		Ctrl.setStatus("mail from " + email.getFromName() + " is moved to" + target);
		nav.mailsMoved();
	}

	@Override
	public void reload() {
		// OK
	}

	@Override
	public void markAsImportant(MailWrap email) {
		nav.markAsImportant(true);
	}

	@Override
	public void markAsUnImportant(MailWrap email) {
		nav.markAsImportant(false);
	}

	@Override
	public String getType() {
		return ClientUtil.getTitleFromUri(this.mailBoxUri);
	}

}
