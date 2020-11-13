package com.bilgidoku.rom.site.yerel.wgts;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.wgts.edit.DragPnlCmds;
import com.bilgidoku.rom.site.yerel.wgts.edit.DragPnlTags;
import com.bilgidoku.rom.site.yerel.wgts.edit.NavWidgets;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class NavApp extends Composite implements HasContainer {

	private TabLayoutPanel tabDraggables = new TabLayoutPanel(29, Unit.PX);
	private NavWidgets widgetPanel = new NavWidgets(this);
	private DragPnlCmds cmdPanel = new DragPnlCmds(); 
	private DragPnlTags tagPanel = new DragPnlTags();	 

	public NavApp() {
		tabDraggables.add(widgetPanel, ClientUtil.getTabTitle(Ctrl.trans.widgets()));
		tabDraggables.add(tagPanel, ClientUtil.getTabTitle(Ctrl.trans.tags()));
		tabDraggables.add(cmdPanel, ClientUtil.getTabTitle(Ctrl.trans.commands()));
		tabDraggables.selectTab(0);
		
		initWidget(tabDraggables);
	}


	public void changeWidget(String widgetName, Code widgetCode) throws RunException {
		widgetPanel.changeCode(widgetName, widgetCode);
		
	}

	public void showNewWidgetDlg(Code code) {
		widgetPanel.showNewWidgetDlg(code);
		
	}

	@Override
	public void addContainers() {
		widgetPanel.getData();
		
	}

}
