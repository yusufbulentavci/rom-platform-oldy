package com.bilgidoku.rom.site.yerel.tags;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Tags;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsResponse;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.user.client.ui.Widget;

public class NavTags extends NavBase implements HasContainer {
	
	
	public NavTags() {
		super("/_local/images/common/tag.png", "/_local/images/common/tag.png", "/_local/images/common/tag.png", true, 1);
	}

	@Override
	public void addContainers() {
		
		getTree().removeItems();

		TagsDao.list(Ctrl.infoLang(), Data.TAG_ROOT, new TagsResponse() {
			@Override
			public void array(List<Tags> value) {
				for (int i = 0; i < value.size(); i++) {
					Tags con = value.get(i);
					addLeaf(null, con.uri, con.title[0]);
				}
				selectFirstContainer();
			}
		});
		
	}

	@Override
	public NavToolbarBase createToolbar() {
		NavToolbar tb = new NavToolbar(this);
		return tb;
	}

	public void clearSelection() {
		getTree().setSelectedItem(null);		
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

	public void activateChart() {
	}

}
