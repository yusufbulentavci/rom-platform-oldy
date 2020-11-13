package com.bilgidoku.rom.site.yerel.apps;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Apps;
import com.bilgidoku.rom.gwt.araci.client.rom.AppsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.AppsResponse;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.user.client.ui.Widget;


public class NavApps extends NavBase implements HasContainer {
	private boolean containersAdded = false;
	public NavApps() {
		super("/_local/images/common/filter.png", "/_local/images/common/filter.png", "/_local/images/common/filter.png", true, 1);
	}
	
	public void addContainers() {
		if (containersAdded)
			return;
		getTree().removeItems();
		
		AppsDao.list("/_/apps", new AppsResponse() {
			public void array(List<Apps> value) {
				for (int i = 0; i < value.size(); i++) {
					Apps style = value.get(i);
					addLeaf(null, style.uri, style.title);
				}
				selectFirstItem();
			}
		});
		containersAdded = true;
	}
	@Override
	public NavToolbarBase createToolbar() {
		NavToolbar tb = new NavToolbar(this);
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
