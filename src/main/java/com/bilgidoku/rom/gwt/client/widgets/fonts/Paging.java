package com.bilgidoku.rom.gwt.client.widgets.fonts;

import com.bilgidoku.rom.gwt.client.util.common.SearchCallback;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Paging extends Composite {
	
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private SiteButton btnPrev = new SiteButton("/_public/images/bar/go_left.png", "", trans.previousPage(), "");
	private SiteButton btnNext = new SiteButton("/_public/images/bar/go_right.png", "", trans.nextPage(), "");
	
	private HTML pageInfo = new HTML();
	

	public Paging(SearchCallback callback) {
		btnPrev.addStyleName("btn-sml");
		btnNext.addStyleName("btn-sml");

		forNext(callback);
		forPrev(callback);
		
		initWidget(ui());
	}

	private Widget ui() {
		HorizontalPanel f = new HorizontalPanel();
		f.setStyleName("site-padding");
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		f.add(btnPrev);
		f.add(pageInfo);
		f.add(btnNext);
		return f;

	}

	private void forPrev(final SearchCallback callback) {
		btnPrev.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {				
				callback.setOffsetBackward();
				callback.newSearch(null);
			}
		});

	}

	private void forNext(final SearchCallback callback) {
		btnNext.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				callback.setOffsetForward();				
				callback.newSearch(null);
			}
		});

	}

	public void hidePager() {
		pageInfo.setVisible(false);
		btnPrev.setVisible(false);
		btnNext.setVisible(false);
	}

	public void showPager(String string) {
		pageInfo.setHTML(string);
//		pageInfo.setVisible(true);
//		btnPrev.setVisible(true);
//		btnNext.setVisible(true);
	}

}

