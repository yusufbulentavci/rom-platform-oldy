package com.bilgidoku.rom.site.yerel.mail.widgets;

import com.google.gwt.user.client.ui.MenuBar;

public class Reply extends MenuBar {
	private final MenuBar replyMenu = new MenuBar(true);
	
//	
//	public Reply(TabMailToolbar mt) {
//		this.setAutoOpen(false);
//		// this.setWidth("49px");
//		this.setAnimationEnabled(true);
//		
//		initMenu(mt);
//	}
//	
//
//	private void initMenu(final TabMailToolbar mt) {
//
//		this.addItem("Reply", replyMenu);
//
//		MenuItem mi = new MenuItem("Reply To Sender", new Command() {
//			@Override
//			public void execute() {
//				if (mt.getMailItem() == null) {
//					Window.alert("One email should be selected");
//					return;
//				}
//				mt.getMailView().newMail(mt.getMailItem().getSubject(), mt.getMailItem().getFrom(), null, mt.getMailItem());
//			}
//		});
//
//		replyMenu.addItem(mi);
//
//		mi = new MenuItem("Reply To All", new Command() {
//			@Override
//			public void execute() {
//				if (mt.getMailItem() == null) {
//					Window.alert("One email should be selected");
//					return;
//				}
//				mt.getMailView().newMail(mt.getMailItem().getSubject(), mt.getMailItem().getFrom(), mt.getMailItem().getCc(), mt.getMailItem());
//			}
//		});
//
//		replyMenu.addItem(mi);
//
//	}

}
