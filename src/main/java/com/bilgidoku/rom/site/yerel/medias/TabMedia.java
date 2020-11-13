package com.bilgidoku.rom.site.yerel.medias;

import java.util.Date;
import java.util.Iterator;

import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.common.content.HasContent;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.site.yerel.subpanels.PnlContent;
import com.bilgidoku.rom.site.yerel.subpanels.PnlTags;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabMedia extends Composite implements HasContent, HasWidgets, HasTags {

	private final SiteToolbarButton btnMore = new SiteToolbarButton(Ctrl.trans.more(), Ctrl.trans.more(), "");
	private final ResourcePanel pnlInfo = new ResourcePanel();
	private final SimplePanel leftSelect = new SimplePanel();
	private final SimplePanel topSelect = new SimplePanel();
	private final SimplePanel rightSelect = new SimplePanel();
	private final SimplePanel bottomSelect = new SimplePanel();

	private TabToolbar toolbar;
	private Image image;
	private String imgUrl;
	// private DockLayoutPanel dock = new DockLayoutPanel(Unit.EM);
	private final HorizontalPanel holderHp = new HorizontalPanel();
	private PnlContent pnlCon;
	private PnlTags pnlTag;
	private final String uri;

	private final SplitLayoutPanel selector = new SplitLayoutPanel(8) {
		public void onResize() {
			toolbar.resized();
		};
	};

	public TabMedia(String uri) {
		this.uri = uri;
		boolean isImage = ClientUtil.isImage(uri);		
		
		toolbar = new TabToolbar(this, isImage);
		btnMore.setWidth("120px");
		toolbar.add(btnMore);		
		
		forMore();
		loadData(uri, Ctrl.infoLang());

		if (isImage) {

			this.imgUrl = uri + "?" + Ctrl.getUriDateFix();
			this.image = new Image(imgUrl);
			toolbar.setImage(this.image);
			buildSelector();

			FlowPanel im = new FlowPanel();
			im.add(image);
			im.add(selector);
			
			pnlInfo.setVisible(false);
			holderHp.setSpacing(4);
			holderHp.add(toolbar);
			holderHp.add(im);
			holderHp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			holderHp.add(pnlInfo);

			initWidget(new ScrollPanel(holderHp));

		} else {
			Frame frm = new Frame(uri);
			frm.setSize("100%", "100%");

			FlowPanel fp = new FlowPanel();
			fp.add(toolbar);
			fp.add(frm);
			
			initWidget(fp);

		}

	}

	private void forMore() {
		btnMore.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pnlInfo.show();
			}
		});

	}

	private void loadData(String uri, String lang) {
		FilesDao.getdetail(lang, uri, new FilesResponse() {
			@Override
			public void ready(Files value) {
				pnlCon.load(value.uri, null, null, null, value.title[0], value.summary[0], value.langcodes, value.weight);
				pnlTag.loadData(value.rtags);
				pnlInfo.show();
			}
		});
		
	}

	private void buildSelector() {
		leftSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		leftSelect.getElement().getStyle().setOpacity(0.6);

		rightSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		rightSelect.getElement().getStyle().setOpacity(0.6);

		topSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		topSelect.getElement().getStyle().setOpacity(0.6);

		bottomSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		bottomSelect.getElement().getStyle().setOpacity(0.6);

		image.getElement().getStyle().setZIndex(0);

		selector.getElement().getStyle().setPosition(Position.ABSOLUTE);
		selector.getElement().getStyle().setTop(0, Unit.PX);
		selector.getElement().getStyle().setLeft(120, Unit.PX);

		selector.getElement().getStyle().setZIndex(5);

		selector.addNorth(topSelect, 50);
		selector.addSouth(bottomSelect, 50);
		selector.addEast(rightSelect, 50);
		selector.addWest(leftSelect, 50);
		selector.setVisible(false);
	}

	public void imageReload() {
		Date date = new Date();
		String now = DateTimeFormat.getFormat("yyyyMMddHHmmss").format(date);
		this.image.setUrl(imgUrl + "?d=" + now);
	}

	public void showSelector() {
		selector.setSize(image.getWidth() + "px", image.getHeight() + "px");
		selector.setVisible(true);
	}

	public boolean isSelectorActive() {
		return selector.isVisible();
	}

	public void hideSelector() {
		selector.setVisible(false);
		selector.setWidgetSize(topSelect, 50);
		selector.setWidgetSize(bottomSelect, 50);
		selector.setWidgetSize(rightSelect, 50);
		selector.setWidgetSize(leftSelect, 50);
	}

	public int getSelectorX() {
		return leftSelect.getOffsetWidth();
	}

	public int getSelectorY() {
		return topSelect.getOffsetHeight();
	}

	public int getSelectorXX() {
		return rightSelect.getOffsetWidth();
	}

	public int getSelectorYY() {
		return bottomSelect.getOffsetHeight();
	}

	public String getUrl() {
		String uri = image.getUrl();
		if (uri.indexOf("?") > 0)
			return uri.substring(0, uri.indexOf("?"));
		else
			return uri;
	}

	@Override
	public void add(Widget w) {
		holderHp.add(w);

	}

	@Override
	public void clear() {
		holderHp.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return holderHp.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return holderHp.remove(w);
	}

	private class ResourcePanel extends Composite implements HasContent{
		
		public ResourcePanel() {
			initWidget(ui());
		}

		public void show() {
			setVisible(true);
			
		}

		public Widget ui() {
			if (pnlCon == null)
				pnlCon = new PnlContent(TabMedia.this, true, false, false);
			
			if (pnlTag == null)
				pnlTag = new PnlTags(TabMedia.this);
			
			Button save = new Button(Ctrl.trans.save());
			save.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					pnlCon.save(uri, pnlCon.getSelectedLang());
					pnlTag.save(uri);					
				}
			});
			
			Button cancel = new Button();
			cancel.setStyleName("site-closebutton");
			cancel.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {
					ResourcePanel.this.setVisible(false);
					
				}
			});
			
			VerticalPanel vp = new VerticalPanel();
			vp.add(new HTML(ClientUtil.getHeader(Ctrl.trans.details())));
			vp.add(pnlCon);
			vp.add(new HTML(Ctrl.trans.tags()));
			vp.add(pnlTag);
			vp.setStyleName("site-innerform");
			vp.add(save);
			vp.add(cancel);
			return vp;
		}

		@Override
		public void langChanged(String lang) {
			loadData(uri, lang);
			
		}

		@Override
		public void contentChanged() {
			// TODO Auto-generated method stub
			
		}

	}

	@Override
	public void tagsChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void langChanged(String lang) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contentChanged() {
		// TODO Auto-generated method stub
		
	}



}
