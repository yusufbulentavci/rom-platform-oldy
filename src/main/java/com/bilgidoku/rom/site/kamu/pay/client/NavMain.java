package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.panels.PnlContact;
import com.bilgidoku.rom.gwt.client.util.panels.TabExamResults;
import com.bilgidoku.rom.gwt.client.util.panels.TabOrders;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavMain extends Composite {

	private ContactUI payUI;
	private final String name=RomEntryPoint.com().get("cname");
	private final String cid=RomEntryPoint.com().get("cid");

	private final SiteButton btnContact=new SiteButton(pay.trans.profile(), cid);
	private final SiteButton btnIss = new SiteButton(pay.trans.issues(), pay.trans.issues());
	private final SiteButton btnOrders = new SiteButton(pay.trans.myorders(), pay.trans.myorders());
	private final SiteButton btnExams = new SiteButton(pay.trans.exams(), pay.trans.exams());
	private final SiteButton btnWebSites = new SiteButton(pay.trans.webSites(), pay.trans.webSites());

	private Widget[] btns = { btnOrders,btnContact, btnIss, btnWebSites, btnExams };

	public NavMain(ContactUI payUI) {
		

		this.payUI = payUI;

		btnOrders.setSelected();
		
		btnOrders.setSize("100%", "41px");
		btnIss.setSize("100%", "41px");
		btnContact.setSize("100%", "41px");
		btnWebSites.setSize("100%", "41px");
		btnExams.setSize("100%", "41px");

		forOrders();
		forBtnIssues();
		forContact();
		forExams();
		forWebSites();

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(10);
		vp.setWidth("185px");		
		for (int i = 0; i < btns.length; i++) {
			vp.add(btns[i]);
		}
		initWidget(vp);

	}

	private void forWebSites() {
		btnWebSites.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				selectButton(btnWebSites);
				payUI.changeMainPnl(new WelcomeUI());	
				
			}
		});
		
	}

	private void forExams() {
		btnExams.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				selectButton(btnExams);
				payUI.changeMainPnl(new TabExamResults());			
				
			}
		});
	}

	private void forOrders() {
		btnOrders.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				selectButton(btnOrders);
				payUI.changeMainPnl(new TabOrders("pay"));				
			}
		});
		
	}

//	private void forBtnChat(Button btn2) {
//		btn2.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				ContactBla desk = Xmpp.contacts.getDesk();
//				if (desk != null)
//					Xmpp.gui.showChatDlg(desk);
//			}
//		});
//	}

	private void forContact() {
		btnContact.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectButton(btnContact);
				payUI.changeMainPnl(new PnlContact(cid, name));

			}
		});

	}

	private void forBtnIssues() {
		btnIss.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectButton(btnIss);
				payUI.changeMainPnl(new PnlIssues());
			}
		});
	}

	protected void selectButton(SiteButton btn) {
		for (int i = 0; i < btns.length; i++) {
			SiteButton mtn = (SiteButton) btns[i];
			if (mtn.isSelected()) {
				mtn.removeSelected();
			}
		}
		btn.setSelected();
	}

}
