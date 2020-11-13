package com.bilgidoku.rom.site.yerel.styles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.StylesDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteTree;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class TabStyles extends Composite implements HasWidgets {

	private SplitLayoutPanel holder = new SplitLayoutPanel();

	private String styleUri;
	private CellTable<Style> cssTable = new CellTable<Style>();
	private TabLayoutPanel leftWrapper = new TabLayoutPanel(2.1, Unit.EM);
	private SiteTree widgetTree = new SiteTree();

	// toolbar
	// private final PreviewToolbar previewTi=new PreviewToolbar(this);
	private final SiteToolbarButton btnSaveStyles = new SiteToolbarButton(Ctrl.trans.save(), Ctrl.trans.save(), "");
	private final SiteToolbarButton btnCopySelected = new SiteToolbarButton("Copy", "Copy", "");
	private final SiteToolbarButton btnPasteUnder = new SiteToolbarButton("Paste Under", "Paste Under", "");
	// private final SiteToolbarButton btnPaste = new
	// SiteToolbarButton("Paste");
	private final SiteToolbarButton btnDeleteSelected = new SiteToolbarButton("DelSel", "DelSel", "");
	private final SiteToolbarButton btnReloadWidgets = new SiteToolbarButton("Reload", "Reload", "");
	private final SiteToolbarButton btnNewClass = new SiteToolbarButton("NewClass", "NewClass", "");
	
	public HashMap<String, List<Style>> allCss = null;
	
	private static final String defaults = "defaults";

	private final MultiSelectionModel<Style> selModel = new MultiSelectionModel<Style>();

	// private PreviewFrame frame = new PreviewFrame();
	// private PreviewPage page = null;
	private final CssEntryForm form;
	private final ListDataProvider<Style> dataProvider = new ListDataProvider<Style>();
	private ListHandler<Style> sortHandler;

	// private final String styleName;

	public TabStyles(String name, String uri, JSONArray styles) {
		this.styleUri = uri;
		this.allCss = getStylesList(styles);
		this.form = new CssEntryForm(this);
		// this.styleName=name;

		cssTable.setSelectionModel(selModel);
		cssTable.setPageSize(400);
		List<Style> df = allCss.get(defaults);
		if (df != null) {
			dataProvider.setList(allCss.get(defaults));
		}
		dataProvider.addDataDisplay(cssTable);

		sortHandler = new ListHandler<Style>(dataProvider.getList());
		addColumns(sortHandler);

		buttonsState(true);

		forSaveStyles(name);
		forSelectStyleList();
		forDeleteSelected();
		forSelectFromWidgetTree();
		forCopy();
		forReloadWidgets();
		forPaste();
		forPasteUnderTargetWidget();
		forNewClass();

		ui();

		initWidget(holder);

		buildWidgetTree();

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				// preview(Data.WELCOME_APP);
			}
		});

	}

	private void forPaste() {
		// btnPaste.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// Set<Style> coming = Ctrl.paste();
		// if (coming == null || coming.size() <= 0)
		// return;
		// String widgetName = coming.iterator().next().getWidget();
		// List<Style> recentList = allCss.get(widgetName);
		// for (Style style : coming) {
		// Style s = new Style(widgetName, style.getTag(), style.getPseClass(),
		// style.getProperty(), style.getValue());
		// if (recentList == null)
		// recentList = new ArrayList<Style>();
		// recentList.add(s);
		// }
		// allCss.put(widgetName, recentList);
		// loadCellTable(widgetName);
		// Window.alert("pasted");
		// }
		// });

	}

	private void forReloadWidgets() {
		btnReloadWidgets.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				buildWidgetTree();
			}

		});

	}

	private void forNewClass() {
		btnNewClass.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final String sTitle = Window.prompt("New CSS class name", "");
				if (sTitle == null)
					return;
				addClass(sTitle);
				buildWidgetTree();
			}
		});

	}

	// protected void loadWidgets() {
	// // WidgetsDao.get("/_/widgets", new WidgetsResponse() {
	// // @Override
	// // public void ready(Widgets value) {
	// // JSONObject objWidget = value.codes.getValue().isObject();
	// // buildWidgetTree(objWidget);
	// // }
	// // });
	// }
	private void ui() {

		TabLayoutPanel centerWrapper = new TabLayoutPanel(2.1, Unit.EM);
		TabLayoutPanel formWrapper = new TabLayoutPanel(2.1, Unit.EM);

		// SplitLayoutPanel mainPanel = new SplitLayoutPanel();

		holder.addWest(leftWrapper, 200);
		holder.addEast(formWrapper, 370);
		holder.add(centerWrapper);

		// frame.setSize("99%", "95%");
		// frame.setStyleName("site-padding");

		// mainPanel.setStyleName("site-padding");
		// mainPanel.addSouth(bottom, 250);
		// mainPanel.add(frame);

		cssTable.setHeight("50px");
		cssTable.setWidth("100%");

		Widget[] SiteToolbarButtons = { btnSaveStyles, btnDeleteSelected, btnReloadWidgets, btnNewClass,
				btnCopySelected, btnPasteUnder };

		DockLayoutPanel dock = new DockLayoutPanel(Unit.EM);
		dock.addNorth(ClientUtil.getToolbar(SiteToolbarButtons, 4), 4.4);
		dock.add(new ScrollPanel(cssTable));

		centerWrapper.add(dock, ClientUtil.getTabTitle(Ctrl.trans.styles()));

		formWrapper.add(form, ClientUtil.getTabTitle("CSS Entry Form"));
		leftWrapper.add(new ScrollPanel(widgetTree), ClientUtil.getTabTitle("Classes"));

	}

	private void forDeleteSelected() {
		btnDeleteSelected.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Set<Style> selects = getSelectedSet();
				if (selects.size() > 0) {
					for (Style st : selects) {
						deleteRow(st.getWidget(), st.getTag(), st.getPseClass(), st.getProperty(), st.getValue());
					}
				}
				// if there is no data then remove key
				String widget = form.getWidgetValue();
				if (allCss.get(widget).size() <= 0)
					allCss.remove(widget);

			}
		});

	}

	private void forPasteUnderTargetWidget() {
		btnPasteUnder.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				final DlgPasteHelper dlg = new DlgPasteHelper();
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						Set<Style> coming = Ctrl.paste();
						Ctrl.clearCache();
						if (coming == null || coming.size() <= 0)
							return;
						
						if (dlg.widgetName == null)
							return;
						
						String widgetName = dlg.widgetName;
						String tagName = dlg.tagName;
						
						List<Style> recentList = allCss.get(widgetName);
						for (Style style : coming) {								
							Style s = new Style(widgetName, tagName != null && !tagName.isEmpty() ? tagName : style.getTag(), style.getPseClass(), style.getProperty(), style.getValue());
							if (recentList == null)
								recentList = new ArrayList<Style>();
							recentList.add(s);
						}
						allCss.put(widgetName, recentList);
						loadCellTable(widgetName);
						Window.alert("pasted");
						
					}
				});
				
			}
		});
	}

	private void forCopy() {
		btnCopySelected.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Ctrl.copy(getSelectedSet());
				Window.alert("copied");
			}
		});
	}

	protected Set<Style> getSelectedSet() {
		@SuppressWarnings("unchecked")
		MultiSelectionModel<Style> mu = ((MultiSelectionModel<Style>) cssTable.getSelectionModel());
		Set<Style> set = mu.getSelectedSet();
		return set;
	}

	// public void fillFrame(String html) {
	// frame.preview(html);
	// }

	private HashMap<String, List<Style>> getStylesList(JSONArray styles) {
		if (styles == null)
			return new HashMap<String, List<Style>>();

		HashMap<String, List<Style>> stylesList = new HashMap<String, List<Style>>();
		for (int i = 0; i < styles.size(); i++) {
			JSONObject jo = styles.get(i).isObject();
			String w = jo.get("widget").isString().stringValue();
			String t = jo.get("tag").isString().stringValue();
			String pse = jo.get("pseclass").isString().stringValue();
			String p = jo.get("property").isString().stringValue();
			String v = jo.get("value").isString().stringValue();
			List<Style> l;
			if (stylesList.containsKey(w)) {
				l = stylesList.get(w);
			} else {
				l = new ArrayList<Style>();
			}
			l.add(new Style(w, t, pse, p, v));
			stylesList.put(w, l);
		}
		return stylesList;
	}

	private void buildWidgetTree() {
		widgetTree.removeItems();

		// widgetTree.addItem(getTreeItem(defaults));

		//
		// for (String wn : objWidgets.keySet()) {
		// putWidget(wn, objWidgets.get(wn).isObject());
		// }
		//
		// for(String wn: WidgetBase.getWidgets().keySet()){
		// putWidget(wn, objWidgets.get(wn).isObject());
		// }

		Set<String> keys = allCss.keySet();
		List<String> sorted = ClientUtil.asSortedList(keys);
		for (String key : sorted) {
			widgetTree.addItem(getTreeItem(key));
		}

	}

	// private void putWidget(String wn, JSONObject wo) {
	// String wsn="w"+wn.substring(2);
	// addClass(wsn);
	//
	// JSONObject c=wo.get("ref").isObject();
	// JSONObject a=wo.get("attdefs").isObject();
	// // String[] views=new String[0];
	// // if(a!=null && a.get("_view")!=null){
	// // JSONObject v=a.get("_view").isObject();
	// // if(v!=null){
	// // JSONString e=v.get("enum").isString();
	// // views=e.stringValue().split(",");
	// // }
	// // }
	// // findClass(c);
	//
	// }

	// private void findClass(JSONObject c) {
	// String tag=c.get("tag").isString().stringValue();
	// if(tag.startsWith("w:") || (tag.startsWith("c:") &&
	// !tag.equals("c:foreach")))
	// return;
	//
	// JSONValue atsv = c.get("ats");
	// if(atsv!=null){
	// JSONValue cv=atsv.isObject().get("class");
	// if(cv!=null){
	// addClass(cv.isString().stringValue());
	// }
	// }
	//
	// JSONValue a = c.get("childs");
	// if (a == null)
	// return;
	// JSONArray nodes = a.isArray();
	// if(nodes==null)
	// return;
	// for (int i = 0; i < nodes.size(); i++) {
	// JSONObject nc = nodes.get(i).isObject();
	// findClass(nc);
	// }
	// }

	private void addClass(String wsn) {
		if (allCss.get(wsn) == null) {
			allCss.put(wsn, new ArrayList<Style>());
		}
	}

	private SiteTreeItem getTreeItem(String className) {
		String count = "";
		if (allCss.get(className) != null)
			count = " (" + allCss.get(className).size() + ")";

		SiteTreeItem ti = new SiteTreeItem(className + count, "/_local/images/common/widget.png");
		ti.setUserObject(new SiteTreeItemData(className, className, false, null));
		return ti;
	}

	private void forSelectStyleList() {
		selModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Set<Style> selects = selModel.getSelectedSet();
				if (selects.size() == 1) {
					Style selected = selects.iterator().next();
					if (selected != null) {
						form.setForm(selected.getWidget(), selected.getTag(), selected.getPseClass(),
								selected.getProperty(), selected.getValue());
						buttonsState(false);
					}
				}
			}
		});
	}

	private void forSelectFromWidgetTree() {
		widgetTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				String widget = widgetTree.getSelectedItemText();
				loadCellTable(widget);
				form.setForm(widget, "", "", "", "");
			}
		});
	}

	protected void loadCellTable(String widget) {
		if (allCss.get(widget) == null) {
			cssTable.setRowCount(0, true);
			return;
		}
		dataProvider.setList(allCss.get(widget));
		dataProvider.refresh();		
		sortHandler.setList(dataProvider.getList());
	}

	protected void buttonsState(boolean isNew) {
		/*
		 * if (isNew) { btnSaveStyle.setText(Ctrl.trans.addStyle()); } else {
		 * btnSaveStyle.setText(Ctrl.trans.updateStyle()); }
		 */
	}

	private void forSaveStyles(final String name) {
		btnSaveStyles.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// loop through styleListData and build JSONObject to submit
				JSONArray ja = getStylesJSONArray();
				StylesDao.change(name, new Json(ja), styleUri, new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.stopWaiting();
						Ctrl.setStatus(name + " " + Ctrl.trans.saved());
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						super.err(statusCode, statusText, exception);
						Ctrl.stopWaiting();
					}
				});
			}
		});
	}

	protected void deleteRow(String w, String t, String pse, String p, String v) {
		List<Style> recentList = allCss.get(w);
		for (int i = 0; i < recentList.size(); i++) {
			Style s = recentList.get(i);
			if (s.getWidget().equals(w) && s.getTag().equals(t) && s.getPseClass().equals(pse)
					&& s.getProperty().equals(p) && s.getValue().equals(v)) {
				recentList.remove(i);
				break;
			}
		}
		allCss.put(w, recentList);
		form.emptyForm();
		loadCellTable(w);
	}

	private void addStyleToAllCss(Style s) {
		List<Style> recentList = allCss.get(s.getWidget());
		if (recentList == null)
			recentList = new ArrayList<Style>();
		recentList.add(s);
		allCss.put(s.getWidget(), recentList);
		loadCellTable(s.getWidget());
	}

	protected void changeData(String w, String t, String pse, String p, String v) {
		if (w == null || w.isEmpty())
			w = defaults;
		if (allCss == null || allCss.size() == 0) {
			addStyleToAllCss(new Style(w, t, pse, p, v));
			return;
		}
		List<Style> l = allCss.get(w);
		if (l == null) {
			addStyleToAllCss(new Style(w, t, pse, p, v));
			return;
		}
		
		Style foundStyle = null;
		for (int i = 0; i < l.size(); i++) {
			Style s = l.get(i);
			if (s.getWidget().equals(w) && s.getTag().equals(t) && s.getPseClass().equals(pse)
					&& s.getProperty().equals(p)) {
				foundStyle = s;
				foundStyle.setValue(v);
				l.set(i, foundStyle);
				allCss.put(w, l);
				break;
			}
		}

		if (foundStyle == null) {
			addStyleToAllCss(new Style(w, t, pse, p, v));
		}
	}

	private JSONArray getStylesJSONArray() {
		JSONArray ja = new JSONArray();
		int i = 0;

		for (String widget : allCss.keySet()) {
			List<Style> l = allCss.get(widget);
			for (Iterator<Style> iterator = l.iterator(); iterator.hasNext();) {
				Style style = (Style) iterator.next();
				JSONObject jo = new JSONObject();
				jo.put("widget", new JSONString(style.getWidget()));
				jo.put("tag", new JSONString(style.getTag()));
				jo.put("pseclass", new JSONString(style.getPseClass()));
				jo.put("property", new JSONString(style.getProperty()));
				jo.put("value", new JSONString(style.getValue()));
				ja.set(i, jo);
				i++;
			}
		}
		return ja;
	}

	private void addColumns(ListHandler<Style> sortHandler) {
		TextColumn<Style> colWidget = new TextColumn<Style>() {
			@Override
			public String getValue(Style object) {
				return object.getWidget();
			}
		};
		cssTable.addColumn(colWidget, "Widget");
		// tag
		TextColumn<Style> colTag = new TextColumn<Style>() {
			@Override
			public String getValue(Style object) {
				return object.getTag();
			}
		};
		colTag.setSortable(true);
		// colTag.setDefaultSortAscending(false);
		cssTable.addColumn(colTag, "Tag");

		// pseclass
		TextColumn<Style> colPse = new TextColumn<Style>() {
			@Override
			public String getValue(Style object) {
				return object.getPseClass();
			}
		};
		cssTable.addColumn(colPse, "Pseudo");

		TextColumn<Style> colPro = new TextColumn<Style>() {
			@Override
			public String getValue(Style object) {
				return object.getProperty();
			}
		};
		cssTable.addColumn(colPro, "Prop");

		TextColumn<Style> colVal = new TextColumn<Style>() {
			@Override
			public String getValue(Style object) {
				return object.getValue();
			}
		};
		cssTable.addColumn(colVal, "Value");

		sortHandler.setComparator(colTag, new Comparator<Style>() {
			public int compare(Style o1, Style o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getTag().compareTo(o2.getTag()) : 1;
				}
				return -1;
			}
		});

		cssTable.addColumnSortHandler(sortHandler);
		cssTable.getColumnSortList().push(colTag);

	}

//	@Override
//	public void modify(PreviewPage page) {
//		page.setStylePreview(getStylesJSONArray().toString());
//	}

	@Override
	public void add(Widget w) {
		holder.add(w);

	}

	@Override
	public void clear() {
		holder.clear();

	}

	@Override
	public Iterator<Widget> iterator() {
		return holder.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return holder.remove(w);
	}
}