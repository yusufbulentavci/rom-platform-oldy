package com.bilgidoku.rom.site.yerel.wgts.edit;

import java.util.Iterator;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.cmds.CommandRepo;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.NullableMap;
import com.bilgidoku.rom.shared.code.Rtn;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.tags.TagData;
import com.bilgidoku.rom.site.yerel.wgts.NavApp;
import com.bilgidoku.rom.gwt.client.util.common.SiteTree;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.DropOnTree;
import com.bilgidoku.rom.gwt.client.util.common.DropOnTreeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelWgtTree extends VerticalPanel  {

	private SiteTree appTree = new SiteTree(true);
	private SiteToolbarButton btnSaveAsWidget = new SiteToolbarButton(Ctrl.trans.saveAsWidget(),
			Ctrl.trans.saveAsWidgetDesc(), "");
	private SiteToolbarButton btnRmvNode = new SiteToolbarButton(Ctrl.trans.removeNode(), Ctrl.trans.removeNodeDesc(),
			"");
	private SiteToolbarButton btnSave = new SiteToolbarButton(Ctrl.trans.save(), Ctrl.trans.save(), "");
//	private SiteToolbarButton btnShowCode = new SiteToolbarButton("Show Code", "Show Code", "");
//	private SiteToolbarButton btnBuildCode = new SiteToolbarButton("Build Node", "Build Node", "");

	private int id_index = 0;
	private int depth = 0;
	private final NavApp caller;
	private TabWgt tab;

	public PanelWgtTree(NavApp caller, TabWgt tab, String widgetName, Wgt widget) {
		this.caller = caller;
		this.tab = tab;

		forSave(widgetName);
		forDrop();
		forRemoveNode();
		forSaveAsWidget();
		forSelect();

		try {
			this.buildTree(widget.getCodes(), null);
		} catch (RunException e) {
			Ctrl.setError(e);
		}

		Widget[] buttons = { btnSave, btnRmvNode, btnSaveAsWidget };

		this.setStyleName("site-padding");
		this.add(ClientUtil.getToolbar(buttons, 3));
		this.add(new ScrollPanel(this.appTree));
	}

	private void forSave(final String widgetName) {
		btnSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try {
					Code widgetCode = generateObject(appTree.getItem(0), widgetName);
					caller.changeWidget(widgetName, widgetCode);
				} catch (RunException e) {
					Ctrl.setError(e);
					Sistem.printStackTrace(e);
				}
			}
		});

	}

	private int getIndex() {
		this.id_index++;
		return this.id_index;
	}

	private int getTreeDepth() {
		this.depth++;
		return this.depth;
	}

	private void buildTree(Code code, TreeItem tonode) throws RunException {

		SiteTreeItem node;
		
		if (code == null)
			return;
		
		if (code.isWidget()) {
			node = new SiteTreeItem(code.tag, "/_local/images/common/widget.png", "widget!" + code.tag);
		} else {
			// on drag start set dragData
			node = new SiteTreeItem(code.tag, "/_local/images/common/node.png", "node!" + getIndex());
		}

		TagData nodeAtts = new TagData(code.tag, code);
		node.setUserObject(nodeAtts);

		if (tonode == null) {
			appTree.addItem(node);
		} else {
			tonode.addItem(node);
			if (this.getTreeDepth() < 4)
				tonode.setState(true);
		}

		if (code.children != null && code.children.size() > 0) {
			for (Iterator<Code> iterator = code.children.iterator(); iterator.hasNext();) {
				Code child = (Code) iterator.next();
				this.buildTree(child, node);
			}
		}

		if (code.isCommand()) {
			String comTag = code.tag;
			Tag tagDef = CommandRepo.one().get(comTag).getDef();
			Att[] allParams = tagDef.getParams();
			if (allParams != null) {
				for (Att at : allParams) {
					if (at.isMethod()) {

						NullableMap<String, String> tagPrms = code.params;
						String param = at.getNamed();

						if (param == null)
							continue;

						// JSONObject jo = new JSONObject();
						// jo.put("tag", "p:" + param);
						// jo.put("id", idx);

						SiteTreeItem prmNode = new SiteTreeItem("p:" + param, "/_local/images/common/node.png", "param!" + "p:" + param);
						prmNode.setUserObject(new TagData("p:" + param, code));

						node.addItem(prmNode);

						if (tagPrms.get(param) == null)
							continue;

						String rtnName = tagPrms.get(param);

						if (NavWidgets.allCode.getRoutine(rtnName) != null) {

							Rtn rtn = NavWidgets.allCode.getRoutine(rtnName);
							for (int i = 0; i < rtn.codes.size(); i++) {
								Code cd = rtn.codes.get(i);
								this.buildTree(cd, prmNode);
							}

						}
					}
				}
			}

		}

		if (code.isWidget()) {
			Wgt widget = NavWidgets.allCode.getWidget(code.tag);
			this.buildTree(widget.getCodes(), node);
		}
	}

	protected Code generateObject(TreeItem fornode, final String widgetName) throws RunException {
		if (fornode == null) {
			fornode = this.appTree.getItem(0);
		}

		TagData tagData = (TagData) fornode.getUserObject();

		Code code = tagData.getCode().clone(NavWidgets.allCode);
		code.detach();

		if (fornode.getChildCount() > 0) {
			for (int i = 0; i < fornode.getChildCount(); i++) {
				TreeItem cn = fornode.getChild(i);
				String subtag = cn.getText().trim();
				TagData childData = (TagData) cn.getUserObject();
				Code childCode = childData.getCode();

				if (subtag.startsWith("p:")) {
					Code cin = this.generateObject(cn, widgetName);
					code.addMethodParam(subtag.substring(2), cin, NavWidgets.allCode);
				} else if (subtag.startsWith("w:")) {
					code.addChild(childCode);

				} else {
					Code cin = this.generateObject(cn, widgetName);
					code.addChild(cin);
				}
			}
		}

		return code;
	}

	public void nodeTextChanged(String value) {
		TreeItem tonode = this.appTree.getSelectedItem();
		if (tonode == null)
			return;
		TagData td = (TagData) tonode.getUserObject();
		td.setNodeText(value);
	}

	public void nodeAttChanged(String name, String value) {
		TreeItem tonode = this.appTree.getSelectedItem();
		if (tonode == null)
			return;
		TagData td = (TagData) tonode.getUserObject();
		td.setAttribute(name, value);
	}

	public void nodeStyleChanged(String styleType, String name, String value) {
		TreeItem tonode = this.appTree.getSelectedItem();
		if (tonode == null)
			return;
		TagData td = (TagData) tonode.getUserObject();
		td.setStyleByType(styleType, name, value);
	}

	// private void forChangeWidget() {
	// btnChangeWidget.addClickHandler(new ClickHandler() {
	// public void onClick(ClickEvent event) {
	// try {
	// changeWidget();
	// } catch (RunException e) {
	// Sistem.printStackTrace(e);
	// Ctrl.setError(e);
	// }
	// }
	// });
	// }
	//
	// protected void changeWidget() throws RunException {
	// TreeItem widgetNode = appTree.getSelectedItem();
	// if (widgetNode == null)
	// return;
	//
	// String widgetName = ((TagData) widgetNode.getUserObject()).getTag();
	// if (NavWidgets.allCode.getWidget(widgetName) == null) {
	// Window.alert(widgetName + " could not be found");
	// return;
	// }
	// widgetNode = widgetNode.getChild(0);
	// Code widgetCode = generateObject(widgetNode, false, widgetName);
	// caller.changeWidget(widgetName, widgetCode);
	//
	// }

	private void forSaveAsWidget() {
		btnSaveAsWidget.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Code code;
				try {
					code = generateObject(appTree.getSelectedItem(), null);
				} catch (RunException e) {
					Ctrl.setError(e);
					return;
				}

				caller.showNewWidgetDlg(code);
			}
		});
	}

	private void forDrop() {
		this.appTree.addHandler(new DropOnTreeHandler() {
			public void dropOnTree(DropOnTree event) {
				// drag from widget tree or tag panel, or from tree itself
				try {
					TreeItem target = event.targetNode;
					if (target == null)
						return;

					TreeItem parent = null;
					if (target.getParentItem() == null || event.where == 0) {
						parent = target;
					} else {
						Integer ind = appTree.getItemIndex(target);
						if (ind == null)
							return;
						parent = target.getParentItem();
					}
					TagData parentData = (TagData) parent.getUserObject();

					String t = event.baseEvent.getData("text");
					String[] data = t.split("!");

					String source = data[0];
					SiteTreeItem node = null;

					if (source.equals("widget")) {

						String widgetName = data[1];

						node = new SiteTreeItem(widgetName, "/_local/images/common/widget.png", "widget!" + widgetName);

						Wgt widget = NavWidgets.allCode.getWidget(widgetName);

						Code code = Code.widget(widgetName, NavWidgets.allCode);

						TagData nodeData = new TagData(widgetName, code);

						node.setUserObject(nodeData);

						buildTree(widget.getCodes(), node);
						// putParamContainers(widget, node);

					} else if (source.equals(Code.TAG)) {
						// from tagPanel
						// int id = getIndex();
						String text = data[1];
						node = new SiteTreeItem(text, "/_local/images/common/node.png", "node!" + getIndex());

						JSONObject jo = new JSONObject();
						jo.put(Code.TAG, text);

						Code code = new Code(parentData.getCode(), jo, NavWidgets.allCode);
						node.setUserObject(new TagData(text, code));

					} else if (source.equals("node")) {
						// move on tree itself, copy node create new one
						String oldId = data[1];
						TreeItem oldItem = getItemById(oldId);

						Code code = generateObject(oldItem, null);
						TreeItem tempnode = new TreeItem();

						buildTree(code, tempnode);
						// oldItem.remove();
						node = (SiteTreeItem) tempnode.getChild(0);
						tempnode.remove();

					} else if (source.equals("command")) {
						// from command panel
						String text = data[1];
						node = new SiteTreeItem(text, "/_local/images/common/node.png", "node!" + getIndex());

						JSONObject jo = new JSONObject();
						jo.put(Code.TAG, text);

						Code code = new Code(parentData.getCode(), jo, NavWidgets.allCode);
						node.setUserObject(new TagData(text, code));

						// node.setUserObject(new TagData(text, new Code(null,
						// tabWgt.allCode, text)));
					} else {
						Ctrl.setError("coming:" + t + " return");
						return;
					}

					if (target.getParentItem() == null || event.where == 0) {
						target.addItem(node);
						target.setState(true);
						target.setVisible(true);
					} else {
						Integer ind = appTree.getItemIndex(target);
						if (ind == null)
							return;
						if (event.where == -1) {
							target.getParentItem().insertItem(ind + 1, node);
						} else {
							target.getParentItem().insertItem(ind, node);
						}
					}
				} catch (RunException e) {
					Ctrl.setError("Drop failed; Msg:" + e.getMessage());
				}
			}

		}, DropOnTree.TYPE);

	}

	private void forSelect() {
		this.appTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				
				btnSaveAsWidget.getElement().focus();
				
				TreeItem j = event.getSelectedItem();
				TagData data = (TagData) j.getUserObject();
				String tag = data.getTag().trim();

				appTree.setSelectedItem(j, false);
				btnRmvNode.setEnabled(true);

				try {
					tab.nodeSelectionChanged(tag, data);
				} catch (RunException e) {
					Ctrl.setError(e);
				}
			}
		});
	}

	private void forRemoveNode() {
		btnRmvNode.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (appTree.getSelectedItem() == null)
					return;
				appTree.getSelectedItem().remove();
			}
		});
	}

	public void nodeParamChanged(String name, String value) {
		TreeItem tonode = this.appTree.getSelectedItem();
		if (tonode == null)
			return;
		TagData td = (TagData) tonode.getUserObject();
		td.setParam(name, value);
	}

	private TreeItem getItemById(String id) {
		id = id.trim();
		for (int i = 0; i < this.appTree.getItemCount(); i++) {
			SiteTreeItem item = (SiteTreeItem) this.appTree.getItem(i);
			TreeItem n = getItemById(id, item);
			if (n != null)
				return n;
		}
		return null;
	}

	private SiteTreeItem getItemById(String id, SiteTreeItem inItem) {
		String mid = inItem.getDragData().split("!")[1];
		// TagData td = (TagData) inItem.getUserObject();
		// String mid = td.getUri().trim();
		if (id.equals(mid)) {
			return inItem;
		}
		for (int i = 0; i < inItem.getChildCount(); i++) {
			SiteTreeItem item = (SiteTreeItem) inItem.getChild(i);
			SiteTreeItem n = getItemById(id, item);
			if (n != null)
				return n;
		}
		return null;
	}

//	@Override
//	public void modify(PreviewPage page) {
//		try {
//			JSONObject objApp = new JSONObject();
//			objApp.put("codes", generateObject(null, null).store());
//			page.setAppPreview(objApp.toString());
//		} catch (RunException e) {
//			Ctrl.setError(e);
//		}
//	}

}
