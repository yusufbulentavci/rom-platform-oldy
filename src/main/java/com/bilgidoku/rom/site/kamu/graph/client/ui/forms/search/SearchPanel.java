package com.bilgidoku.rom.site.kamu.graph.client.ui.forms.search;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.service.InternetDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.browse.image.search.SearchParams;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SearchCallback;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs.DlgImageSearch;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchPanel extends Composite implements SearchCallback {

	private int PAGESIZE = 20;

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private final ToolbarOptions toolbarOptions;
	private final ResultPanel resultPanel;
	public int offset = 0;
	private SearchParams params;
	private ChangeCallback caller;

	private DlgResult dlg;

	private String userDir;

	private boolean isResultDlg;

	private DlgImageSearch dlgImageSearch;

	public SearchPanel(ChangeCallback caller1, String userDir, boolean isResultDlg, DlgImageSearch dlgImageSearch) {
		caller = caller1;
		this.userDir = userDir;
		this.isResultDlg = isResultDlg;
		this.dlgImageSearch = dlgImageSearch;

		resultPanel = new ResultPanel(this);
		toolbarOptions = new ToolbarOptions(this);

		resultPanel.setSize("680px", "300px");

		VerticalPanel vp = new VerticalPanel();
		vp.setHeight("50px");
		vp.add(toolbarOptions);
		if (isResultDlg) {
			dlg = new DlgResult();
		} else {
			vp.add(resultPanel);
		}

		initWidget(vp);

	}

	public void makeSearch() {
		ClientUtil.startWaiting();
		clearResults();
		InternetDao.searchimg(params.provider, PAGESIZE, offset, params.phrase, params.size, params.aspect, params.style,
				params.color, params.face, new ArrayResponse<ImageResp>() {
					@Override
					public void ready(ImageResp[] value) {
						if (value == null) {
							ClientUtil.stopWaiting();
							Window.alert(trans.noBackground());
						}
						resultPanel.showPager("  " + ((offset + PAGESIZE) / PAGESIZE) + "." + trans.page() + " ");
						resultPanel.populate(value);
						ClientUtil.stopWaiting();

						if (!isResultDlg) {
							toolbarOptions.closeOptions();
							return;
						}

						if (!dlg.isShowing()) {
							dlg.show();
							dlg.center();
						}
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						ClientUtil.stopWaiting();
					}

				});

	}

	private void clearResults() {
		resultPanel.clear();
	}

	public void closeOptions() {
		toolbarOptions.closeOptions();
	}

	private class DlgResult extends ActionBarDlg {
		public DlgResult() {
			super("Results", null, "OK");
			run();
		}

		@Override
		public Widget ui() {
			return resultPanel;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
			ImageResp img = getSelectedObject();
			downloadImage(img.uri);

		}

	}

	public ImageResp getSelectedObject() {
		return resultPanel.getSelectedObject();
	}

	private void downloadImage(String uri) {
		caller.setStatus("İndiriliyor " + ClientUtil.getTitleFromUri(uri));
		FilesDao.neww("en", userDir, null, uri, null, null, null, userDir, new StringResponse() {
			public void ready(String value) {
				caller.newImage(value);
			}
		});
	}

	@Override
	public void newSearch(SearchParams sp) {
		if (sp != null) {
			params = sp;
			offset = 0;
		}
		makeSearch();

	}

	@Override
	public void setOffsetForward() {
		offset = offset + PAGESIZE;
	}

	@Override
	public void setOffsetBackward() {
		if (offset - PAGESIZE < 0) {
			return;
		}

		offset = offset - PAGESIZE;
	}

	@Override
	public void picked(String uri) {
		dlgImageSearch.hide();
		downloadImage(uri);
	}

}
