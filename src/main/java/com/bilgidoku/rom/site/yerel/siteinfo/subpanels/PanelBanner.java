package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.pagedlgs.SiteDlg;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class PanelBanner extends ScrollPanel {
	private final BackgroundCell patternCell = new BackgroundCell();
	private final CellList<String> imageList = new CellList<String>(patternCell);
	private final SingleSelectionModel<String> patterListSelModel = new SingleSelectionModel<String>();
	private final Button btnNoBanner = new Button("no banner");
	private final SiteDlg view;
	protected boolean initialLoad = false;

	public PanelBanner(SiteDlg viewDlg) {
		view = viewDlg;
		forSelectList();
		forNoBanner();

		imageList.setSelectionModel(patterListSelModel);

		FlowPanel fp = new FlowPanel();
		fp.add(btnNoBanner);
		fp.add(imageList);
		this.add(fp);

//		getData();
		
		List<String> patternData = new ArrayList<String>();
		for (String is : Data.BANNER_IMAGES) {
			patternData.add(is);
		}
		imageList.setRowCount(Data.BANNER_IMAGES.length, true);
		imageList.setRowData(0, patternData);

	}


//	private String[] p3Data = null;
//	private String[] p4Data = null;
//	private boolean ready3 = false;
//	private boolean ready4 = false;

//	private void getData() {
//		// phrase : 2 => banner
//		RichwebDao.searchimg(3, "2", null, null, null, null, null, null, null, null, null, null,
//				new ArrayResponse<String>() {
//					@Override
//					public void ready(String[] value) {
//						p3Data = value;
//						ready3 = true;
//						dataReady();
//					}
//				});
//
//		RichwebDao.searchimg(4, "2", null, null, null, null, null, null, null, null, null, null,
//				new ArrayResponse<String>() {
//					@Override
//					public void ready(String[] value) {
//						p4Data = value;
//						ready4 = true;
//						dataReady();
//					}
//				});
//
//	}

//	protected void dataReady() {
//		if (!(ready3 && ready4))
//			return;
//
//		// merge data
//		List<String> data3 = new ArrayList<String>(Arrays.asList(p3Data));
//		List<String> data4 = new ArrayList<String>(Arrays.asList(p4Data));
//
//		data3.addAll(data4);
//
//		imageList.setRowCount(data3.size(), true);
//		imageList.setRowData(0, data3);
//
//	}

	private void forNoBanner() {
		btnNoBanner.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				patterListSelModel.clear();
				view.bannerChanged();
				view.preview();
			}
		});
	}

	private void forSelectList() {
		patterListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (initialLoad) {
					initialLoad = false;
					return;
				}
				view.bannerChanged();
			}
		});
	}

	public String getSelected() {
		if (patterListSelModel.getSelectedObject() == null)
			return null;
		String banner = patterListSelModel.getSelectedObject();
		return banner;
	}
	
	public void setSelection(String backImg) {
		if (backImg == null || backImg.isEmpty())
			return;
		initialLoad = true;
		List<String> list = imageList.getVisibleItems();
		for (String img : list) {
			if (img.equals(backImg)) {
				patterListSelModel.setSelected(img, true);
				break;
			}
		}

	}

	private class BackgroundCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<div style='background-color: #3F4041; display: inline-block; "
					+ "height: 60px; line-height: 16px; margin: 4px; padding: 0; "
					+ "width: 60px; background-image: url("
					+ row + "); background-repeat:no-repeat; background-position:center;'></div>");
		}
	}


}
