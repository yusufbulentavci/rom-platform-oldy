package com.bilgidoku.rom.site.yerel.mail.widgets;


import com.bilgidoku.rom.site.yerel.mail.TabMailToolbar;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class MoveToFolder extends MenuBar {
	private final MenuBar moveTo = new MenuBar(true);
	private String[] arr = {"Trash","Spam","Inbox", "My folder"};
	
	public MoveToFolder(TabMailToolbar mailToolbar) {
	    this.setAutoOpen(false);
	    //this.setWidth("49px");
	    this.setAnimationEnabled(true);	    
	    
	    initFoldera();
	}
	
	private void initFoldera() {
		
		this.addItem("Move", moveTo);
		for (int i = 0; i < arr.length; i++) {
			MenuItem mi = new MenuItem(arr[i], new Command() {
		    	@Override
				public void execute() {
					//TODO
				}
		    });
			
		    moveTo.addItem(mi);	
			
		}
		
	}


}

