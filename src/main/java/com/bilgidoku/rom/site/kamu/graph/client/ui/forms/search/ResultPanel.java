package com.bilgidoku.rom.site.kamu.graph.client.ui.forms.search;

import java.util.Arrays;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SearchCallback;
import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class ResultPanel extends Composite {
	// private final static ApplicationConstants trans =
	// GWT.create(ApplicationConstants.class);

	private ToolbarPaging pnlPaging;

	private final ImageCell patternCell = new ImageCell();
	private final CellList<ImageResp> imageList = new CellList<ImageResp>(patternCell);
	private final SingleSelectionModel<ImageResp> imageListSelModel = new SingleSelectionModel<ImageResp>();

	private SearchCallback callback;

	public ResultPanel(SearchCallback callback) {

		this.callback = callback;
		pnlPaging = new ToolbarPaging(callback);

		imageList.setSelectionModel(imageListSelModel);
		forSelect();

		ScrollPanel scrImg = new ScrollPanel(imageList);
		scrImg.setSize("670px", "455px");
		scrImg.getElement().getStyle().setBackgroundColor("white");

		VerticalPanel vp = new VerticalPanel();
		vp.add(pnlPaging);
		vp.add(scrImg);

		initWidget(vp);

	}

	private void forSelect() {
		imageListSelModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				callback.picked(imageListSelModel.getSelectedObject().uri);
			}
		});

	}

	public void showPager(String string) {
		pnlPaging.setVisible(true);

	}

	public void populate(ImageResp[] value) {
		imageList.setVisible(true);
		List<ImageResp> asList = Arrays.asList(value);
		imageList.setRowCount(asList.size(), true);
		imageList.setRowData(0, asList);

	}

	private class ImageCell extends AbstractCell<ImageResp> {
		@Override
		public void render(Context context, ImageResp row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			if (ClientUtil.isImage(row.uri))
				sb.appendHtmlConstant(
						"<img style='width: 150px; height: 130px; margin: 4px; padding: 0; border: 1px solid #d2d2d2;' src= '"
								+ row.uri + "' />");

		}

	}

	public ImageResp getSelectedObject() {
		return imageListSelModel.getSelectedObject();
	}

	public void clear() {
		imageList.setVisible(false);

	}

}
