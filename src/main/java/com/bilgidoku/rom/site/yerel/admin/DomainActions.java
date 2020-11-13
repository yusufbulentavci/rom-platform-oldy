package com.bilgidoku.rom.site.yerel.admin;

import java.util.ArrayList;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.data.Domain;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;


public class DomainActions {
	private NavDomains nav;
	public ArrayList<Domain> domains = new ArrayList<Domain>() {		
		private static final long serialVersionUID = 1L;
		{
			add(new Domain("tepeweb.com.tr"));
			add(new Domain("bilgidoku.com.tr"));
		}
	};
	
	public DomainActions(NavDomains navWriting) {		
		this.nav = navWriting;
	}

	public void removeLeaf() {
		TreeItem toDel = nav.getSelectedItem();
		if (toDel == null || toDel.getParentItem() != null)
			return;
		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;
		//getUserObject de olabilir	
		String domainName = toDel.getText();
		for (Domain u : domains) {
			if (u.getDomainName().equals(domainName)) {
				toDel.remove();
				domains.remove(u);
			}
		}		
	}

	public void openLeaf() {
		TreeItem selItem = nav.getSelectedItem();
		if (selItem == null) {
			Window.alert("Select app to edit");
			return;
		}
		openDomain(selItem.getText());
	}

	private void openDomain(String domainName) {
//		TabDomain tw = new TabDomain();
//		Ctrl.openTab(domainName, domainName, tw, "/_local/images/common/domain(), Data.ADMIN_COLOR);		
	}

	public void newLeaf() {
		final String sName = Window.prompt("Enter Domain Name", "");
		if (sName != null && !sName.isEmpty()) {
//			OturumIciCagriDao.checkdomain(sName, new BooleanResponse(){
//				@Override
//				public void ready(Boolean value) {					
//					if (!value) {
//						Window.alert("this domain is not available, try another name");
//					} else {
//						//TODO this object may be removed
//						Domain u = new Domain(sName);
//						//domains.add(u);  
//						nav.addDomain(u);						
//					}
//				}				
//			});
		}		
	}
}
