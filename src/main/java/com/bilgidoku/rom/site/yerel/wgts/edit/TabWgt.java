package com.bilgidoku.rom.site.yerel.wgts.edit;

import java.util.Iterator;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.CodeRepo;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.tags.TagData;
import com.bilgidoku.rom.site.yerel.wgts.NavApp;
import com.bilgidoku.rom.site.yerel.wgts.edit.code.PnlNodeTabs;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabWgt extends Composite implements HasWidgets{

	static final String PREVIEW_KEY = "_t:";

	private PanelWgtTree wgtPanel;
	private PnlNodeTabs nodeTabs;

	private TabPanel centerTabs = new TabPanel();

	protected CodeRepo allCode;

	public NavApp caller;

	public TabWgt(NavApp caller, String widgetName, Wgt widget) {
		this.caller = caller;

		this.wgtPanel = new PanelWgtTree(caller, this, widgetName, widget);
		this.nodeTabs = new PnlNodeTabs(null);
		nodeTabs.addStyleName("site-padding");	
		
		centerTabs.add(wgtPanel, ClientUtil.getTabTitle(Ctrl.trans.hierarchy()));
		centerTabs.selectTab(0);

		HorizontalPanel bottom = new HorizontalPanel();
		bottom.add(wgtPanel);
		bottom.add(nodeTabs);

		SimpleLayoutPanel sp = new SimpleLayoutPanel();
		sp.add(new ScrollPanel(bottom));
		initWidget(sp);

	}


	public void nodeSelectionChanged(String tag, TagData data) throws RunException {
		this.nodeTabs.buildNodeTabs(tag, data.getCode());
	}


	@Override
	public void add(Widget w) {		
		wgtPanel.add(w);
	}


	@Override
	public void clear() {
		wgtPanel.clear();
		
	}


	@Override
	public Iterator<Widget> iterator() {
		return wgtPanel.iterator();
	}


	@Override
	public boolean remove(Widget w) {
		return wgtPanel.remove(w);
	}

	

}