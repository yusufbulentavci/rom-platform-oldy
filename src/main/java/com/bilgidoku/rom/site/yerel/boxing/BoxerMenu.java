package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.site.yerel.events.DlgClosed;
import com.bilgidoku.rom.site.yerel.events.DlgClosedHandler;
import com.bilgidoku.rom.site.yerel.pagedlgs.BasePanel;

public class BoxerMenu extends BasePanel {
	
	private BoxerMenuButton myButton;

	public BoxerMenu(String title, int layer) {
		super(title, layer);
		this.setVisible(false);
		this.getElement().getStyle().setZIndex(Layer.layer2);
		this.addStyleName("site-fixed");

		this.addHandler(new DlgClosedHandler() {
			@Override
			public void dlgClosed(DlgClosed event) {
				if (myButton != null)
					myButton.deactivate();
			}
		}, DlgClosed.TYPE);
	}
	
	public void setMyButton(BoxerMenuButton btn) {
		this.myButton = btn;
	}
	

}
