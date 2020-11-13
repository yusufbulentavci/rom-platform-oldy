package com.bilgidoku.rom.gwt.client.util.browse.items;

import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.ecommerce.Ecommerce;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BrowseItems extends Composite {
	


	
	
	public String selectedItem = null;
	
	private final SiteButton btnPage = new SiteButton("/_local/images/common/add.png", "Page", "Page", "");

	private final SiteButton btnStock = new SiteButton("/_local/images/common/add.png", "Stock", "Stock", "");

	private final SiteButton btnFile = new SiteButton("/_local/images/common/add.png", "File", "File", "");
	
//	private final SiteButton btnLink = new SiteButton("/_local/images/common/add.png", "Link", "Link", "");

	private BrowseCallback cb;
	
	public BrowseItems(BrowseCallback cb1) {		
		this.cb = cb1;
		initWidget(ui());
	}

	public Widget ui() {
		VerticalPanel vp = new VerticalPanel();
		vp.add(btnPage);
		vp.add(btnStock);
		vp.add(btnFile);
		
		forPage();
		forStock();
		forFile();

		return vp;
	}

	private void forFile() {
		btnFile.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				final BrowseFiles br = new BrowseFiles();
				br.show();
				br.center();
				br.addCloseHandler(new CloseHandler<PopupPanel>() {					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						selectedItem = br.selected;
						cb.selected(selectedItem);
					}
				});
				
			}
		});
		
	}

	private void forStock() {
		btnStock.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {				
				final BrowseStocks br = new BrowseStocks();
				br.show();
				br.center();
				br.addCloseHandler(new CloseHandler<PopupPanel>() {					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						selectedItem = br.selected;
						cb.selected("/kendin-tasarla?o_stock=" + selectedItem);
					}
				});
				
			}
		});
	}

	private void forPage() {
		btnPage.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				
				final BrowsePages1 br = new BrowsePages1();
				br.show();
				br.center();
				br.addCloseHandler(new CloseHandler<PopupPanel>() {					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						selectedItem = br.selected;
						cb.selected(selectedItem);
					}
				});
				
			}
		});
	}

	public String getSelected() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
