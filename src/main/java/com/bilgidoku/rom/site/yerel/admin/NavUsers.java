package com.bilgidoku.rom.site.yerel.admin;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.user.client.ui.Widget;


public class NavUsers extends NavBase implements HasContainer {
	public NavUsers() {
		super("/_local/images/common/user.png", "/_local/images/common/users.png", "/_local/images/common/user.png", true, 1);
	}
	
	public void addContainers() {
		getTree().removeItems();
		
		InitialsDao.getusers("/_/_initials", new ArrayResponse<String>() {
			@Override
			public void array(List<String[]> myarr) {
				for (int i = 0; i < myarr.size(); i++) {
					String[] u = myarr.get(i);
					int role = 0;
					try {
						role = Integer.parseInt(u[2]);
					} catch (Exception e) {
						// TODO: handle exception
					}
					//u[0] => userName treeitem.getText()
					//u[1] => contactId data.getUri() 
					//role => data.getTitle()
					addLeaf(null, u[1], role + "", u[0], null, true);
					
//					addUser(u[0], u[1], role);
				}
				selectFirstItem();
			}
		});
	}
	
	
	
	@Override
	public NavToolbarBase createToolbar() {
		NavUserToolbar tb = new NavUserToolbar(this);
		return tb;
	}

	@Override
	public void add(Widget w) {
		getToolbar().add(w);
		
	}

	@Override
	public void clear() {
		getToolbar().clear();
		
	}

	@Override
	public Iterator<Widget> iterator() {
		return getToolbar().iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return getToolbar().remove(w);
	}

	
}
