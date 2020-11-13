package com.bilgidoku.rom.site.yerel.tags;

import java.util.Iterator;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Tags;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.common.content.HasContent;
import com.bilgidoku.rom.site.yerel.subpanels.PnlContent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabTag extends Composite implements HasContent, HasWidgets {

	private TextBox txtValue = new TextBox();

	private SiteToolbarButton btnSave = new SiteToolbarButton("/_local/images/common/disk.png", Ctrl.trans.save(), Ctrl.trans.save(),
			"");
	private ToggleButton btnMore = new ToggleButton(Ctrl.trans.more(), Ctrl.trans.more());
	private final PnlContent pnlContent = new PnlContent(this, true, false, true);

	public String uri;
	// private Tags tags;
	private final HorizontalPanel holder = new HorizontalPanel();

	protected Tags tags;

	public TabTag(String uri2) {
		this.uri = uri2;
		TagsDao.get(Ctrl.infoLang(), uri, new TagsResponse() {
			@Override
			public void ready(Tags value) {
				tags = value;
				txtValue.setValue(value.title[0]);
				pnlContent.load(value.uri, null, null, null, value.title[0], value.summary[0], value.langcodes, value.weight);
				forSave(uri, value.title[0]);

			}
		});

		forMore();
		ui();
		this.initWidget(holder);

	}

	protected void forSave(final String uri, final String title) {
		btnSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TagsDao.change(pnlContent.getSelectedLang(), txtValue.getValue(), pnlContent.txtSummary.getValue(), null,
						uri, new StringResponse() {
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
				if (pnlContent.isVisible()) {
					pnlContent.setVisible(false);
				} else {
					pnlContent.setVisible(true);
				}

			}
		});

	}

	private void ui() {

		pnlContent.setVisible(false);

		Widget[] buttons = { btnSave, btnMore };

		VerticalPanel hld = new VerticalPanel();
		hld.setSpacing(5);
		hld.addStyleName("site-innerform");
		hld.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hld.add(ClientUtil.getToolbar(buttons, 3));
		hld.add(new Label(Ctrl.trans.value()));
		hld.add(txtValue);

		holder.setSpacing(5);
		holder.addStyleName("site-padding");
		holder.add(hld);
		holder.add(pnlContent);

	}

	@Override
	public void langChanged(String lang) {

		TagsDao.get(lang, uri, new TagsResponse() {
			@Override
			public void ready(Tags value) {
				tags = value;
				txtValue.setValue(value.title[0]);
				pnlContent.txtSummary.setValue(value.summary[0]);
			}
		});

	}

	@Override
	public void contentChanged() {
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

}
