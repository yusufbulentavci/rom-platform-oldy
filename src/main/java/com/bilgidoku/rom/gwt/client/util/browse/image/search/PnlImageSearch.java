
package com.bilgidoku.rom.gwt.client.util.browse.image.search;

//import com.bilgidoku.rom.client.common.dragdrop.DraggableImage;
import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.service.InternetDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.browse.image.DownloadCompleted;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.ImageAnchor;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PnlImageSearch extends Composite {

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	public static int PAGESIZE = 20;
	private static final ImgDescTemplate TEMPLATE = GWT.create(ImgDescTemplate.class);
	private SearchImgToolbar pnlToolbar = new SearchImgToolbar(this);

	private ScrollPanel resultPanel = new ScrollPanel();
	public int offset = 0;
	private SearchParams sp;
	private final boolean fileUploadEnabled;
	private final String resourceUri;

	public PnlImageSearch(boolean fileUploadEnabled, String resourceUri1) {
		this.fileUploadEnabled = fileUploadEnabled;
		this.resourceUri = resourceUri1;
		pnlToolbar.hidePager();

		resultPanel.setSize("680px", "450px");

		VerticalPanel vp = new VerticalPanel();
		vp.setHeight("50px");
		vp.add(pnlToolbar);
		vp.add(resultPanel);

		initWidget(vp);

	}

	private void uiSearchResult(ImageResp[] value) {
		resultPanel.clear();
		resultPanel.add(uiImages(value));
	}

	public void newSearch(SearchParams sp) {
		offset = 0;
		this.sp = sp;
//		pnlToolbar.closeOptions();
		makeSearch();
	}

	public void makeSearch() {
		ClientUtil.startWaiting();
		clearResults();
		
		if (pnlToolbar.isPixabay()) {
			sp.phrase = sp.phrase + " site:pixabay.com"; 
		} else if (pnlToolbar.isPdp()) {
			sp.phrase = sp.phrase + " site:publicdomainpictures.net";
		}
		
		InternetDao.searchimg(sp.provider, PAGESIZE, offset, sp.phrase, sp.size, 
				sp.aspect, sp.style, sp.color, sp.face,
				new ArrayResponse<ImageResp>() {

					@Override
					public void ready(ImageResp[] value) {
						if (value == null) {
							ClientUtil.stopWaiting();
							Window.alert(trans.noBackground());
						}

						ClientUtil.stopWaiting();
						pnlToolbar.showPager("  " + ((offset + PAGESIZE) / PAGESIZE) + "." + trans.page() + " ");
						uiSearchResult(value);
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

	public FlowPanel uiImages(ImageResp[] value) {
		final FlowPanel f = new FlowPanel();
		for (int i = 0; i < value.length; i++) {
			ImageResp img = value[i];
			SimplePanel pad = new SimplePanel(getImageCell(img));
			pad.setStyleName("site-img-pad");
			SimplePanel cell = new SimplePanel(pad);
			cell.setStyleName("site-img-cell");
			f.add(cell);

		}

		return f;
	}

	private VerticalPanel getImageCell(final ImageResp imageDef) {
		SiteButton btnActualImage = new SiteButton("/_public/images/bar/expand.png", "", "", "arama-gercekboyut.mp4");
		btnActualImage.addStyleName("btn-sml");
		btnActualImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (imageDef.uri != null && !imageDef.uri.isEmpty()) {
					// Window.open(imageDef.uri, "_blank", "");
					new PreviewDlg(imageDef.uri);
					return;
				}

				if (imageDef.previewpath != null && !imageDef.previewpath.isEmpty()) {
					// Window.open(imageDef.previewpath, "_blank", "");
					new PreviewDlg(imageDef.previewpath);
					return;
				}

			}
		});

		// image
		String thumbUrl = imageDef.thumbpath;
		if (sp.provider == 5)
			thumbUrl = imageDef.previewpath;

		Image img = new Image(thumbUrl);
		img.setTitle(imageDef.title);

		if (sp.provider == 3 || sp.provider == 4) {
			String thumb = imageDef.thumbpath;
			String mediumImage = thumb.replace("_t.", "_m.");

			img = new Image(mediumImage);
			img.setTitle(imageDef.title);

		}
		img.setHeight("150px");
		String descTitle = imageDef.title;
		if (descTitle.length() > 50)
			descTitle = descTitle.substring(0, 47) + "...";

		// String fileSize = ClientUtil.bytesToKilo(imageDef.) + "KB";
		HorizontalPanel fp = new HorizontalPanel();
		fp.setSpacing(2);
		fp.add(btnActualImage);

		if (fileUploadEnabled) {
			DownloadButton download = new DownloadButton(PnlImageSearch.this, imageDef.uri, imageDef.id);
			download.addStyleName("btn-sml");
			download.setVisible(false);
			if (Provider.isPublic(sp.provider))
				download.setVisible(true);

			fp.add(download);
		}

		ImageAnchor exclam = new ImageAnchor("/_local/images/common/exclamation.png");
		exclam.setTitle(trans.tooLong(trans.files()));
		if (imageDef.width > 1900 || imageDef.height > 1900) {
			fp.add(exclam);
		}

		SimplePanel simg = new SimplePanel(img);
		simg.setStyleName("site-img");

		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vp.add(simg);
		if (imageDef.width != null && imageDef.height != null) {
			HTML divDesc = new HTML(TEMPLATE.desc("", imageDef.width, imageDef.height, descTitle, "",
					ClientUtil.getTitleFromUri(imageDef.uri)));
			divDesc.setHeight("35px");
			vp.add(divDesc);
		}
		vp.add(fp);
		return vp;
	}

	public interface ImgDescTemplate extends SafeHtmlTemplates {
		@Template("<div class=\"site-img-desc\">{1}x{2}<br>{5}</div>")
		SafeHtml desc(String type, int w, int h, String title, String fileSize, String uri);

	}

	protected class DownloadButton extends Composite {
		SiteButton download = new SiteButton("/_public/images/bar/download.png", "", "", "arama-gercekboyut.mp4");

		public DownloadButton(final PnlImageSearch tabSearchResult, final String downloadUri, final String imgId) {
			download.setTitle(trans.download());
			download.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final DlgDownloadFolders dd = new DlgDownloadFolders(resourceUri);
					dd.addCloseHandler(new CloseHandler<PopupPanel>() {
						@Override
						public void onClose(CloseEvent<PopupPanel> event) {
							
							final String containerUri = dd.selected;
							if (containerUri.startsWith("/f/")) {
								//target is files system
								FilesDao.neww("en", containerUri, null, downloadUri, null, null, null, containerUri,
										new StringResponse() {
											public void ready(String value) {
												Window.alert("download tamamlandı.");
												tabSearchResult.fireEvent(new DownloadCompleted(containerUri));
											}
										});

							} else {
								
								String fileName = downloadUri.substring(downloadUri.lastIndexOf("/") + 1);
								ResourcesDao.downloadfile(downloadUri, fileName, resourceUri, new StringResponse() {
									@Override
									public void ready(String value) {
										Window.alert("download tamamlandı.");
										tabSearchResult.fireEvent(new DownloadCompleted(resourceUri));
									}
								});
							}
							
							

						}
					});
				}
			});
			initWidget(download);
		}

	}

	private class PreviewDlg extends ActionBarDlg {
		private String imgUri;

		public PreviewDlg(String imgUri) {
			super("Preview", null, null);
			this.imgUri = imgUri;
			run();
			this.show();
			this.center();

		}

		@Override
		public Widget ui() {
			ScrollPanel sp = new ScrollPanel();
			sp.setSize("800px", "500px");
			sp.add(new HTML("<img src='" + imgUri + "'/>"));
			return sp;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
		}
	}

}
