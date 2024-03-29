package com.bilgidoku.rom.site.yerel.households;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.user.client.ui.Widget;

public class NavHouseHolds extends NavBase implements HasContainer {
	
	private boolean containersAdded = false;
	
	public NavHouseHolds() {
		super("/_local/images/common/link.png", "/_local/images/common/folder.png", "/_local/images/common/folder_key.png", true, 1);
	}

	public NavHouseHolds(boolean noToolbar, boolean showItems) {
		super("/_local/images/common/link.png", "/_local/images/common/folder.png", "/_local/images/common/folder_key.png", false, 1);
		this.listItems = showItems;
	}

	@Override
	public NavToolbarBase createToolbar() {
		NavToolbar tb = new NavToolbar(this);
		return tb;
	}

	@Override
	public void addContainers() {
		
		if (containersAdded)
			return;
		
		getTree().removeItems();		
		
		getToolbar().buttonsStates(false);
		
		ContainersDao.listing("site", "households", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					addContainer(null, con, false, false);
				}
				selectFirstContainer();
			}
		});
		containersAdded = true;
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
