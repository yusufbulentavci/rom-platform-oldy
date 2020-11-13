package com.bilgidoku.rom.site.yerel.styles;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Styles;
import com.bilgidoku.rom.gwt.araci.client.rom.StylesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StylesResponse;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.user.client.ui.Widget;


public class NavStyles extends NavBase implements HasContainer {
	private boolean containersAdded = false;
	public NavStyles() {
		super("/_local/images/common/style.png", "/_local/images/common/style.png", "/_local/images/common/style.png", true, 1);
	}
	
	public void addContainers() {
		if (containersAdded)
			return;
		getTree().removeItems();
		
		StylesDao.list("/_/styles", new StylesResponse() {
			public void array(List<Styles> value) {
				for (int i = 0; i < value.size(); i++) {
					Styles style = value.get(i);
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
