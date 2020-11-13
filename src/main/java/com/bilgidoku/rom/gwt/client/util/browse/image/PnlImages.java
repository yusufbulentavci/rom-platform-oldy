package com.bilgidoku.rom.gwt.client.util.browse.image;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class PnlImages extends Composite {
	private final ImageCell patternCell = new ImageCell();
	private final CellList<String> imageList = new CellList<String>(patternCell);
	private final SingleSelectionModel<String> imageListSelModel = new SingleSelectionModel<String>();
	private String w = "150px";
	private String h = "130px";
	private BrowseCallback call;

	public PnlImages(final BrowseCallback call) {
		this.call = call;
		imageList.setSelectionModel(imageListSelModel);
		imageList.setPageSize(150);
		ScrollPanel scrImg = new ScrollPanel(imageList);
		scrImg.setSize("800px", "455px");
		scrImg.getElement().getStyle().setBackgroundColor("white");

		imageListSelModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if (call == null)
					return;
				call.selected(getSelectedObject());
			}
		});
		initWidget(scrImg);
	}

	public void scrollToSelected() {
		int counter = -1;
		for (int i = 0; i < imageList.getRowCount(); i++) {
			String row = imageList.getVisibleItem(i);
			if (imageListSelModel.isSelected(row)) {
				counter = i;
				break;
			}
		}

		if (counter > 0)
			imageList.getRowElement(counter).scrollIntoView();

	}

	public void setSelectionOnImages(String imgUri) {
		if (imgUri == null || imgUri.isEmpty())
			return;

		List<String> list = imageList.getVisibleItems();
		for (String uri : list) {
			if (uri.equals(imgUri)) {
				imageListSelModel.setSelected(uri, true);
				scrollToSelected();
				break;
			}
		}

	}

	private class ImageCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String uri, SafeHtmlBuilder sb) {
			if (uri == null) {
				return;
			}

			String listUri = uri.indexOf("?") > 0 ? uri + "&" + "romthumb=t" : uri + "?" + "romthumb=t";

			if (ClientUtil.isImage(uri))
				sb.appendHtmlConstant("<img style='width: " + w + "; height: " + h
						+ "; margin: 4px; padding: 0; border: 1px solid #d2d2d2;' src= '" + listUri + "' />");

		}

	}

	public String getSelectedObject() {
		return imageListSelModel.getSelectedObject();
	}

	private void populateImages(final String parentUri, final String fileUri) {
		// clear first
		imageList.setRowCount(0, true);
		imageList.setRowData(0, new ArrayList<String>());

		if (parentUri.startsWith("/f")) {
			FilesDao.list("tr", 0, 1000, parentUri, new FilesResponse() {
				public void array(List<Files> value) {
					ClientUtil.stopWaiting();
					if (value == null || value.size() == 0) {
						// if (call != null)
						// call.selected(null);
						return;
					}

					List<String> uris = new ArrayList<>();

					for (int i = 0; i < value.size(); i++) {
						Files files = value.get(i);
						uris.add(files.uri);
					}

					imageList.setRowCount(uris.size(), true);
					imageList.setRowData(0, uris);

					if (fileUri != null) {
						setSelectionOnImages(ClientUtil.getUriFromlastUri(fileUri));
					}
				}

			});

		} else {

			WritingsDao.get("en", parentUri, new WritingsResponse() {
				@Override
				public void ready(Writings value) {
					if (value.dbfs == null)
						return;
					int l = value.dbfs.length;
					if (l <= 0)
						return;

					List<String> uris = new ArrayList<>();

					for (int i = 0; i < l; i++) {
						uris.add(value.uri + "/getfile.rom?fn=" + value.dbfs[i]);
					}

					imageList.setRowCount(uris.size(), true);
					imageList.setRowData(0, uris);

				}
			});

		}

	}

	public void listImages(String parentUri, String fileUri) {
		populateImages(parentUri, fileUri);

	}

	public void setImageSize(String w, String h) {
		this.w = w;
		this.h = h;
	}

}
