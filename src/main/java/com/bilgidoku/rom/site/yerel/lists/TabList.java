package com.bilgidoku.rom.site.yerel.lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Lists;
import com.bilgidoku.rom.gwt.araci.client.site.ListsDao;
import com.bilgidoku.rom.gwt.araci.client.site.ListsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.common.content.HasContent;
import com.bilgidoku.rom.site.yerel.common.content.HasResource;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.site.yerel.subpanels.PnlContent;
import com.bilgidoku.rom.site.yerel.subpanels.PnlTags;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.BrowseAll;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

public class TabList extends Composite implements HasContent, HasResource, HasWidgets, HasTags {

	private final ItemCell itmCell = new ItemCell();
	private final CellList<String> itemList = new CellList<String>(itmCell);
	private final SingleSelectionModel<String> itemListSelModel = new SingleSelectionModel<String>();

	private final String uri;
	private Lists listData;

	private SiteToolbarButton btnAdd = new SiteToolbarButton("/_local/images/common/add.png", "", Ctrl.trans.addItem(),
			"liste_ogeekle.mp4");

	private SiteToolbarButton btnUp = new SiteToolbarButton("/_local/images/common/up.png", "", Ctrl.trans.moveUp(),
			"liste_asagiyukari.mp4");
	private SiteToolbarButton btnDown = new SiteToolbarButton("/_local/images/common/down.png", "",
			Ctrl.trans.moveDown(), "liste_asagiyukari.mp4");
	private SiteToolbarButton btnDelete = new SiteToolbarButton("/_local/images/common/cross.png", "",
			Ctrl.trans.delete(), "");

	private SiteButton btnSave = new SiteButton("/_local/images/common/disk.png", Ctrl.trans.save(),
			Ctrl.trans.save(), "");
	private SiteToolbarButton btnGo = new SiteToolbarButton("/_local/images/common/right.png", "", Ctrl.trans.open(),
			"");

	private SiteButton btnMore = new SiteButton("/_local/images/common/pencil.png", Ctrl.trans.more(), Ctrl.trans.more(), "");

	private final HorizontalPanel holder = new HorizontalPanel();
	// content
	private final PnlContent pnlProp = new PnlContent(this, true, true, true);
	private final PnlTags pnlTag = new PnlTags(this);
	private VerticalPanel pnlResource = new VerticalPanel();

	private List<String> centerList = new ArrayList<String>();

	public TabList(String uri1) {
		this.uri = uri1;
		initWidget(holder);
		itemList.setSelectionModel(itemListSelModel);
		ui();
		ListsDao.get(Ctrl.infoLang(), uri1, new ListsResponse() {
			public void ready(Lists value) {
				listData = value;

				pnlProp.load(value.uri, value.medium_icon, value.large_icon, value.icon, value.title[0],
						value.summary[0], value.langcodes, value.weight);

				pnlTag.loadData(value.rtags);

				initCenterList(value.content_ids);
				loadData();
			}
		});

		forUp();
		forDown();
		forDelete();
		forAdd();
		forMore();

		forSave();
		forGo();

	}

	private void forMore() {
		btnMore.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (pnlResource.isVisible()) {
					pnlResource.setVisible(false);
					// open.setText("");
				} else {
					pnlResource.setVisible(true);
				}

			}
		});

	}

	protected void initCenterList(String[] content_ids) {
		if (content_ids == null)
			return;
		for (int i = 0; i < content_ids.length; i++) {
			String val = content_ids[i].replace("\"", "");
			centerList.add(val);
		}
	}

	private void forAdd() {
		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final BrowseAll bi = new BrowseAll();
				bi.show();
				bi.center();
				bi.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (bi.selected == null || bi.selected.isEmpty())
							return;
						String item = bi.selected;
						centerList.add(item);
						loadData();
					}
				});

			}
		});

	}

	private void ui() {

		VerticalPanel tabList = new VerticalPanel();

		pnlResource.setVisible(false);

		tabList.addStyleName("site-padding");
		tabList.addStyleName("site-innerform");

		itemList.setSize("100%", "100%");

		Widget[] listButtons1 = { btnSave, btnMore };
		Widget[] listButtons2 = { btnAdd, btnUp, btnDown, btnDelete, btnGo };

		ScrollPanel sp = new ScrollPanel(itemList);
		sp.setWidth("180px");
		sp.setHeight("500px");

		tabList.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		tabList.add(ClientUtil.getToolbar(listButtons1, 3));
		tabList.add(ClientUtil.getToolbar(listButtons2, 5));
		tabList.add(sp);

		pnlResource.add(pnlProp);
		pnlResource.add(new HTML(Ctrl.trans.tags()));
		pnlResource.add(pnlTag);

		holder.addStyleName("site-holder");
		holder.addStyleName("site-padding");
		holder.setSpacing(5);
		holder.add(tabList);
		holder.add(pnlResource);

		// holder.add(hp);

	}

	private void loadData() {
		itemList.setRowCount(centerList.size(), true);
		itemList.setRowData(0, centerList);

	}

	private void forSave() {
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				save();
			}

			private void save() {

				pnlProp.save(uri, pnlProp.getSelectedLang());
				pnlTag.save(uri);

				int t = centerList.size();
				String[] sb = new String[t];
				for (int i = 0; i < t; i++) {
					sb[i] = URL.encode(centerList.get(i));
				}

				ListsDao.change(sb, uri, new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(value + " " + Ctrl.trans.saved());

					}

				});

			}

		});
	}

	private void forDelete() {
		btnDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String uri = itemListSelModel.getSelectedObject();
				centerList.remove(uri);
				loadData();
			}
		});
	}

	private void forDown() {
		btnDown.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String uri = itemListSelModel.getSelectedObject();
				if (uri == null) {
					Window.alert(Ctrl.trans.selectAnItem());
					return;
				}

				int ind = centerList.indexOf(uri);
				if ((ind + 1) > 0 && (centerList.size() >= ind)) {
					String toMove = centerList.get(ind + 1);
					centerList.set(ind + 1, centerList.get(ind));
					centerList.set(ind, toMove);
				}
				loadData();
			}
		});
	}

	private void forUp() {
		btnUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				String uri = itemListSelModel.getSelectedObject();
				if (uri == null) {
					Window.alert(Ctrl.trans.selectAnItem());
					return;
				}

				int i = centerList.indexOf(uri);
				if (i > 0) {
					String toMove = centerList.get(i);
					centerList.set(i, centerList.get(i - 1));
					centerList.set(i - 1, toMove);
				}
				loadData();

			}
		});
	}

	@Override
	public void langChanged(final String lang) {
		ListsDao.get(lang, uri, new ListsResponse() {
			@Override
			public void ready(Lists value) {
				listData = value;
				pnlProp.load(value.uri, value.medium_icon, value.large_icon, value.icon, value.title[0],
						value.summary[0], value.langcodes, value.weight);
				pnlTag.loadData(value.rtags);
			}
		});

	}

	@Override
	public void appChanged(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contentChanged() {
		// TODO Auto-generated method stub

	}

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

	private void forGo() {
		btnGo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				String uri = itemListSelModel.getSelectedObject();
				if (uri == null) {
					Window.alert(Ctrl.trans.selectAnItem());
					return;
				}

				if (uri.startsWith("/f")) {
					Ctrl.focusMedia(uri);

				}

			}
		});

	}

	private class ItemCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}

			String docName = row.substring(row.lastIndexOf("/") + 1);
			if (docName.isEmpty()) {
				docName = Ctrl.trans.homePage();
			}

			String back = "/_local/images/doc_empty.png";
			if (row.startsWith("/f")) {
				back = row;
				sb.appendHtmlConstant("<div style='width: 150px;position:relative; margin:5px;' class='gwt-RichTextToolbar'>"
						+ "<img style='display:block;margin:5px auto;width:75px;height:75px;border:5px solid #1F1F1F;' src='"
						+ back + "'></img></div>");

			} else {
				sb.appendHtmlConstant("<div style='width: 150px;position:relative;margin: 5px;' class='gwt-RichTextToolbar'>"
						+ "<img style='display:block;margin:0 auto;padding: 10px;width:50px;height:50px;' src='"
						+ back
						+ "'></img>" + "<div style='text-align:center;'>" + docName + "</div></div>");

			}

		}
	}

	@Override
	public void tagsChanged() {
		// TODO Auto-generated method stub
		
	}

}