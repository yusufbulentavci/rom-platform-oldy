package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class SiteImageThumb extends Image {
	BigImage dlg = null;
	public SiteImageThumb(final String uri, final String title) {
		super(uri + "?romthumb=t");
		setStyleName("site-smlimg img-small");
		setSize("52px", "60px");
		addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				dlg = new BigImage(uri, title, event.getClientX(), event.getClientY());				
			}
		});
	}
	
	private class BigImage extends ActionBarDlg {

		private Image img;

		public BigImage(String imgUri, String title, int x, int y) {
			super(title, null, null);
			img = new Image(imgUri);
			img.setWidth("500px");
			run();
			setGlassEnabled(true);
			show();
			center();
//			setPopupPosition(x-500, y);
		}

		@Override
		public Widget ui() {
			return img;
		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ok() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
