package com.bilgidoku.rom.site.yerel.wgts.edit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Widgets;
import com.bilgidoku.rom.gwt.araci.client.rom.WidgetsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.WidgetsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTree;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemDelete;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemDeleteHandler;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEdit;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEditHandler;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.CodeRepo;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.NavTreeItem;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.wgts.NavApp;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavWidgets extends Composite {

	private final SiteTree widgetTree = new SiteTree();
	private final SiteToolbarButton btnRemoveWidget = new SiteToolbarButton(Ctrl.trans.delete(),
			Ctrl.trans.removeWidgetDesc(), "");
	private final SiteToolbarButton btnEditWidget = new SiteToolbarButton(Ctrl.trans.edit(),
			Ctrl.trans.editWidgetDesc(), "");

	// this is the only one allCode! can only be changed in this panel
	public static CodeRepo allCode;
	private NavApp navWidgets;

	public NavWidgets(NavApp navWidgets) {

		this.navWidgets = navWidgets;
		getData();

		forRemove();
		forEditProperties();

		Widget[] buttons = { btnRemoveWidget, btnEditWidget };
		DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		dock.addNorth(ClientUtil.getToolbar(buttons, 4), 30);
		dock.add(new ScrollPanel(widgetTree));
		dock.setStyleName("site-panels");
		initWidget(dock);

	}

	public void getData() {
		WidgetsDao.get("/_/widgets", new WidgetsResponse() {
			@Override
			public void ready(Widgets value) {
				JSONObject widgetCode = value.codes.getValue().isObject();
				try {
					NavWidgets.allCode = new CodeRepo(new com.bilgidoku.rom.shared.json.JSONObject(widgetCode));
					populateTree();
				} catch (RunException e) {
					Ctrl.setError(e);
				}

			}
		});

	}

	private void populateTree() throws RunException {
		widgetTree.removeItems();
		CodeRepo cr = allCode;

		Set<String> setKeys = cr.widgets.keySet();

		List<String> keys = new ArrayList<String>();
		keys.addAll(setKeys);
		Collections.sort(keys);

		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Wgt widget = allCode.getWidget(key);
			TreeItem grNode = widgetTree.getItemByText(widget.getGroup());
			if (grNode == null) {
				grNode = addWidgetGroup(widgetTree, widget.getGroup());
			}
			grNode.addItem(newWidgetNode(widget.getNamed()));
		}
	}

	private void saveWidgets(final boolean isDelete, final boolean isAdd, final String grp, final String name)
			throws RunException {

		NavWidgets.allCode.refreshRtns();
		com.bilgidoku.rom.shared.json.JSONObject jo = allCode.store();

		WidgetsDao.change("/_/widgets", new Json((JSONValue) jo.ntv), "/_/widgets", new StringResponse() {
			@Override
			public void ready(String value) {
				if (isDelete)
					widgetTree.getSelectedItem().remove();
				else if (isAdd) {
					applyNewWidget(grp, name);
				} else if (grp != null) {
					widgetTree.getSelectedItem().remove();
					applyNewWidget(grp, name);
				}
				Ctrl.setStatus(Ctrl.trans.widgets() + " " + Ctrl.trans.saved());
			}
		});
	}

	private TreeItem addWidgetGroup(Tree widgetTree, String group) {
		SiteTreeItem item = newWidgetGroupNode(group);
		widgetTree.addItem(item);
		return item;
	}

	private void applyNewWidget(String grp, String name) {
		boolean foundGrp = false;
		int i = 0;
		for (i = 0; i < widgetTree.getItemCount(); i++) {
			if (widgetTree.getItemText(i).equals(grp)) {
				foundGrp = true;
				break;
			}
		}
		if (!foundGrp) {
			TreeItem p = newWidgetGroupNode(grp);
			p.addItem(newWidgetNode(name));
			widgetTree.addItem(p);
			p.setState(true);
		} else {
			TreeItem t = widgetTree.getItem(i);
			t.addItem(newWidgetNode(name));
			t.setState(true);
		}
	}

	private SiteTreeItem newWidgetNode(String title) {
		NavTreeItem node = new NavTreeItem(title, "/_local/images/common/widget.png", null, "widget!" + title, false, true, true);
		node.setUserObject(new SiteTreeItemData(title, title, false, null));
		deletable(node);
		editable(node);
		return node;
	}

	private void deletable(SiteTreeItem node) {
		node.addTreeItemDeleteHandler(new TreeItemDeleteHandler() {
			@Override
			public void deleteTreeItem(TreeItemDelete event) {
				deleteItem();
			}
		});

	}

	private void forRemove() {
		btnRemoveWidget.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteItem();
			}
		});

	}

	protected void deleteItem() {
		if (widgetTree.getSelectedItem() == null)
			return;
		boolean res = Window.confirm(Ctrl.trans.confirmDelete() + " " + widgetTree.getSelectedItemText());
		if (!res)
			return;
		try {
			deleteWidget(widgetTree.getSelectedItemText());
		} catch (RunException e) {
			Sistem.printStackTrace(e);
		}
	}

	private void editable(SiteTreeItem node) {

		node.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				editSelectedItem();
			}
		});

		node.addTreeItemEditHandler(new TreeItemEditHandler() {
			@Override
			public void editTreeItem(TreeItemEdit event) {
				editSelectedItem();
			}
		});

	}

	protected void editSelectedItem() {

		TreeItem selItem = widgetTree.getSelectedItem();
		if (selItem == null)
			return;

		SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
		String id = selNode.getUri();

		try {
			TabWgt theTab = new TabWgt(navWidgets, id, allCode.getWidget(id));
			Ctrl.openTab(id, id, theTab, Data.DESIGN_COLOR);
		} catch (RunException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private SiteTreeItem newWidgetGroupNode(String title) {
		SiteTreeItem node = new SiteTreeItem(title, "/_local/images/common/widget.png", null);
		node.setUserObject(new SiteTreeItemData(title, title, true, null));
		return node;
	}

	private void forEditProperties() {
		btnEditWidget.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (widgetTree.getSelectedItem() == null)
					return;

				String name = widgetTree.getSelectedItemText();

				try {
					DlgEditWidget dlg = new DlgEditWidget(NavWidgets.this, allCode.getWidget(name), null);
					dlg.show();
				} catch (RunException e) {
					Ctrl.setError(e);
				}

			}
		});

	}

	public void showNewWidgetDlg(Code code) {
		DlgEditWidget dlg = new DlgEditWidget(this, null, code);
		dlg.show();

	}

	public void newWidget(String widgetName, String wdtGrp, Code widgetCode, boolean runzone, Map<String, Att> paramDefs, Integer defWidth, Integer defHeight)
			throws RunException {
		Wgt widget = null;
		try {
			widget = allCode.getWidget(widgetName);
		} catch (Exception e) {
		}
		
		if (widget == null) {
			NavWidgets.allCode.addWidget(runzone, paramDefs, widgetName, wdtGrp, widgetCode, defWidth, defHeight);
			saveWidgets(false, true, wdtGrp, widgetName);
		}

	}

	public void changeProperties(String wName, String wGrp, Map<String, Att> paramDefs, boolean runzone, Integer defWidth, Integer defHeight)
			throws RunException {
		Wgt widget = NavWidgets.allCode.getWidget(wName);
		if (widget != null) {
			widget.setGroup(wGrp);
			widget.setParams(paramDefs);
			widget.setRunZone(runzone);
			widget.setDefWidth(defWidth);
			widget.setDefHeight(defHeight);
			
			NavWidgets.allCode.setWidget(wName, widget);
			saveWidgets(false, false, wGrp, wName);
		}
	}

	public void changeCode(String widgetName, Code widgetCode) throws RunException {
		Wgt widget = NavWidgets.allCode.getWidget(widgetName);
		if (widgetCode != null) {
			widget.setCodes(widgetCode);
			NavWidgets.allCode.setWidget(widgetName, widget);
			saveWidgets(false, false, null, widgetName);
		}

	}

	public void deleteWidget(String widgetName) throws RunException {
		allCode.deleteWidget(widgetName);
		saveWidgets(true, false, null, null);
	}

}
