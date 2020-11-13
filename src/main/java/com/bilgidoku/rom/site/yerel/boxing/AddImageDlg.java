package com.bilgidoku.rom.site.yerel.boxing;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.browse.image.DownloadCompleted;
import com.bilgidoku.rom.gwt.client.util.browse.image.DownloadCompletedHandler;
import com.bilgidoku.rom.gwt.client.util.browse.image.FileUploadDialog;
import com.bilgidoku.rom.gwt.client.util.browse.image.SearchDlg;
import com.bilgidoku.rom.gwt.client.util.browse.image.UploadFile;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class AddImageDlg extends BoxerMenu implements UploadFile {

	private final ImageCell wdtCell = new ImageCell();
	private final CellList<String> imgList = new CellList<String>(wdtCell);
	private final SingleSelectionModel<String> imgListSelModel = new SingleSelectionModel<String>();
	private final Hidden fileName = new Hidden("fn");
	private BoxerGui boxerGui;

	private final SiteButton btnFileUpload = new SiteButton("/_public/images/bar/upload.png", null, "upload image",
			null);
	private final SiteButton btnSelect = new SiteButton("/_public/images/bar/cursor_arrow.png", null, "Select", null);
	private final SiteButton btnDelete = new SiteButton("/_public/images/bar/bin.png", null, "Delete", null);
	private final SiteButton btnReload = new SiteButton("/_public/images/bar/refresh.png", null, "reload", null);
	private boolean inDelMode = false;
	protected String resourceUri;
	private final SiteButton btnSearchImage = new SiteButton("/_public/images/bar/search_.png", null,
			"search image on internet", null);



	public AddImageDlg(BoxerGui boxerGui) {

		super(Ctrl.trans.pageFilesImages(), Layer.layer1);
		this.boxerGui = boxerGui;

		imgList.setSelectionModel(imgListSelModel);

		ScrollPanel sp = new ScrollPanel(imgList);
		sp.setWidth("200px");
		sp.setHeight("400px");

		btnDelete.setVisible(false);

		FlowPanel fp = new FlowPanel();
		fp.setStyleName("gwt-StackLayoutPanelHeader");
		fp.add(btnFileUpload);
		fp.add(btnSearchImage);
		fp.add(btnReload);
		fp.add(btnSelect);
		fp.add(btnDelete);

		sp.setStyleName("gwt-StackLayoutPanelContent");

		VerticalPanel vp = new VerticalPanel();
		vp.getElement().getStyle().setBackgroundColor("white");
		vp.add(fp);
		vp.add(sp);

		this.add(vp);

		forSelectInList();
		forSelect();
		forDelete();
		forUpload();
		forReload();
		forSearch();

	}

	private void forSearch() {
		btnSearchImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {				
				SearchDlg dlg = new SearchDlg(resourceUri);
				dlg.addHandler(new DownloadCompletedHandler() {
					@Override
					public void done(DownloadCompleted event) {
						fileUploaded(null, null);
					}
				}, DownloadCompleted.TYPE);
			}
		});

	}

	private void forReload() {
		btnReload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadData();
			}
		});
	}

	private void forSelect() {
		btnSelect.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boxer.status.setStatus("Silme modunda, silmek üzere dosya seçiniz / silme butonuna tıklayınız");
				inDelMode = true;
				btnSelect.setVisible(false);
				btnDelete.setVisible(true);
			}
		});
	}

	private void forDelete() {
		btnDelete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!inDelMode)
					return;

				if (Window.confirm("Silmek istediğinizden emin misiniz?")) {
					deleteFile();
				}
			}
		});

	}

	protected void deleteFile() {
		if (imgListSelModel.getSelectedObject() == null) {
			Window.alert("her hangi bir dosya seçmediniz.");
			return;
		}

		ResourcesDao.removefile(imgListSelModel.getSelectedObject(), boxer.writing.uri, new StringResponse() {
			@Override
			public void ready(String value) {
				fileName.setValue(null);
				inDelMode = false;
				btnDelete.setVisible(false);
				btnSelect.setVisible(true);
				boxer.status.setStatus("Dosya silindi");
				writingChanged();
			}
		});
		
	}

	protected void writingChanged() {
		boxer.writingChanged(new Runnable() {
			@Override
			public void run() {
				loadData();
			}
		});
	}

	private void forSelectInList() {
		imgListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (inDelMode)
					return;

				String selected = imgListSelModel.getSelectedObject();

				if (selected != null) {
					String src = boxer.writing.uri + "/getfile.rom?fn=" + selected;
					boxerGui.imageWidgetSelected(src.replace("//", "/"));
					AddImageDlg.this.setVisible(false);
					imgListSelModel.clear();
				}
			}
		});
	}

	private void forUpload() {
		btnFileUpload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String target = "";
				if (boxer.writing.uri.equals("/")) {
					target = "/addfile.rom";
				} else {
					target = boxer.writing.uri + "/addfile.rom";
				}

				FileUploadDialog dlg = new FileUploadDialog(AddImageDlg.this, target);
				dlg.show();
			}
		});
	}

	@Override
	public void fileUploaded(String dbfs, String fileName) {
		boxer.status.setStatus("Dosya yüklendi");
		writingChanged();
	}	

	public void loadData() {
		
		resourceUri = boxer.writing.uri;
		
		if (boxer.writing.dbfs == null || boxer.writing.dbfs.length <= 0) {
			return;
		}

		List<String> uris = new ArrayList<>();

		for (int i = 0; i < boxer.writing.dbfs.length; i++) {
			if (!boxer.writing.dbfs[i].isEmpty())
				uris.add(boxer.writing.dbfs[i]);
		}

		imgList.setVisibleRange(0, uris.size());
		imgList.setRowCount(uris.size(), true);
		imgList.setRowData(0, uris);
		
	}

	private class ImageCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			String uri = "";
			if (boxer.writing.uri.equals("/"))
				uri = "/getfile.rom?fn=" + row;
			else
				uri = boxer.writing.uri
						+ "/getfile.rom?fn=" + row;

			sb.appendHtmlConstant("<img style='display: inline-block; "
					+ "height: 60px; width: 60px; margin: 0 4px 4px 0; padding: 0;' " + "src='" + uri + "'/>");

		}
	}

	public void infoChanged() {
		loadData();
	}

}
