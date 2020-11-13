package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.araci.client.service.InternetDao;
import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class PanelBackImage extends ScrollPanel {
	private final BackgroundCell patternCell = new BackgroundCell();
	private final CellList<ImageResp> imageList = new CellList<ImageResp>(patternCell);
	private final SingleSelectionModel<ImageResp> imageListSelModel = new SingleSelectionModel<ImageResp>();

	protected boolean initialLoad = false;

	private ImageResp[] p3Data = null;
	private ImageResp[] p4Data = null;
	private boolean ready3 = false;
	private boolean ready4 = false;

	public PanelBackImage(PanelBackground parentPanel) {
		forSelectList(parentPanel);
		imageList.setSelectionModel(imageListSelModel);
		this.add(imageList);
		this.setSize("155px", "145px");

		getData();

	}

	// ${ not empty palette.back_img ? 'cover' : '' }

	private void getData() {
		// phrase : 4 => background
		InternetDao.searchimg(3, null, null, "4", null, null, null, null, null, 
				new ArrayResponse<ImageResp>() {
					@Override
					public void ready(ImageResp[] value) {
						p3Data = value;
						ready3 = true;
						dataReady();
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {

					}
				});

		InternetDao.searchimg(4, null, null, "4", null, null, null, null, null,
				new ArrayResponse<ImageResp>() {
					@Override
					public void ready(ImageResp[] value) {
						p4Data = value;
						ready4 = true;
						dataReady();
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
					}
				});

	}

	protected void dataReady() {
		if (!(ready3 && ready4))
			return;

		List<ImageResp> data3 = new ArrayList<ImageResp>(Arrays.asList(p3Data));
		List<ImageResp> data4 = new ArrayList<ImageResp>(Arrays.asList(p4Data));

		data3.addAll(data4);

		imageList.setRowCount(data3.size(), true);
		imageList.setRowData(0, data3);

	}

	private void forSelectList(final PanelBackground parentPanel) {
		imageListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (initialLoad) {
					initialLoad = false;
					return;
				}
				if (imageListSelModel.getSelectedObject() == null)
					return;
				ImageResp img = imageListSelModel.getSelectedObject();
				parentPanel.backImageChanged(img.uri);
			}
		});
	}

	public void removeSelection() {
		initialLoad = true;
		imageListSelModel.clear();
	}

	public void setSelection(String backImg) {
		if (backImg == null || backImg.isEmpty())
			return;

		initialLoad = true;
		List<ImageResp> list = imageList.getVisibleItems();
		for (ImageResp img : list) {
			if (img.uri.equals(backImg)) {
				imageListSelModel.setSelected(img, true);
				break;
			}
		}

	}

	public String getSelected() {
		ImageResp img = imageListSelModel.getSelectedObject();
		if (img == null)
			return null;
		else
			return img.uri;
	}

	private class BackgroundCell extends AbstractCell<ImageResp> {
		@Override
		public void render(Context context, ImageResp row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<div style='background-color: #3F4041; display: inline-block; "
					+ "height: 60px; line-height: 16px; margin: 4px; padding: 0; "
					+ "width: 60px; background-image: url(" + row.thumbpath
					+ "); background-repeat:no-repeat; background-position:center;'></div>");
		}
	}

}
