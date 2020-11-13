package com.bilgidoku.rom.site.yerel.links;

import java.util.Iterator;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Links;
import com.bilgidoku.rom.gwt.araci.client.site.LinksDao;
import com.bilgidoku.rom.gwt.araci.client.site.LinksResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.common.content.HasContent;
import com.bilgidoku.rom.site.yerel.common.content.HasResource;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.site.yerel.subpanels.PnlContent;
import com.bilgidoku.rom.site.yerel.subpanels.PnlTags;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabLink extends Composite implements HasContent, HasResource, HasWidgets, HasTags {
	private final TextBox txtLinkedUri = new TextBox();
	
	private SiteToolbarButton btnSave = new SiteToolbarButton("/_local/images/common/disk.png", Ctrl.trans.save(), Ctrl.trans.save(), "");
	private ToggleButton btnMore = new ToggleButton(Ctrl.trans.more(), Ctrl.trans.more());

	private final HorizontalPanel holder = new HorizontalPanel();
	private final String uri;
	private Links link;
	
	private final VerticalPanel pnlResource= new VerticalPanel();
	private final PnlContent pnlContent = new PnlContent(this, true, true, true);
	private final PnlTags pnlTag = new PnlTags(this);

	public TabLink(final String uri) {

		this.uri = uri;
		
		LinksDao.get(Ctrl.infoLang(), uri, new LinksResponse() {
			public void ready(Links value) {
				link = value;
				pnlContent.load(value.uri, value.medium_icon, value.large_icon, value.icon, value.title[0],
						value.summary[0], value.langcodes, value.weight);
				pnlTag.loadData(value.rtags);
				// ui(uri);
				forSave(uri, value.title[0]);
				
			}
		});
		
		forMore();
		ui(uri);
		initWidget(holder);
	}

	private void forSave(final String uri, final String title) {
		btnSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				pnlContent.save(uri, link.langcodes[0]);
				pnlTag.save(uri);

				LinksDao.change(txtLinkedUri.getValue(), uri, new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(title + " " + Ctrl.trans.saved());

					}

				});
			}
		});
	}

	private void forMore() {
		btnMore.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (pnlResource.isVisible()) {
					pnlResource.setVisible(false);
				} else {
					pnlResource.setVisible(true);
				}

			}
		});
		
	}

	private void ui(final String uri) {

		pnlResource.setVisible(false);
		
		Widget[] buttons = { btnSave, btnMore };
		txtLinkedUri.setWidth("250px");

		VerticalPanel hld = new VerticalPanel();
		hld.setSpacing(5);
		hld.addStyleName("site-innerform");
		hld.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hld.add(ClientUtil.getToolbar(buttons, 3));
		hld.add(new Label(Ctrl.trans.linkedUri()));
		hld.add(txtLinkedUri);

		pnlResource.add(pnlContent);
		pnlResource.add(new HTML(Ctrl.trans.tags()));
		pnlResource.add(pnlTag);

		holder.setSpacing(5);
		holder.addStyleName("site-padding");
		holder.add(hld);
		holder.add(pnlContent);

	}


	@Override
	public void appChanged(String value) {

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

	@Override
	public void tagsChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contentChanged() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void langChanged(final String lang) {
		LinksDao.get(lang, this.uri, new LinksResponse() {
			@Override
			public void ready(Links value) {
				link = value;
				pnlContent.load(value.uri, value.medium_icon, value.large_icon, value.icon, value.title[0],
						value.summary[0], value.langcodes, value.weight);
				pnlTag.loadData(value.rtags);
			}
		});

	}

}