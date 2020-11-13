package com.bilgidoku.rom.site.yerel.pagedlgs;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.events.DlgClosed;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BasePanel extends VerticalPanel {
	
	public BasePanel(String title, int layer) {
		
		Button btnCancel = new Button("Cancel");
		btnCancel.setStyleName("site-closebutton-black");
		btnCancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				 BasePanel.this.setVisible(false);
				 BasePanel.this.fireEvent(new DlgClosed());
			}
		});

		this.add(ClientUtil.getHeaderWidget(title, null));
		this.addStyleName("site-rounded");
		this.addStyleName("site-fixed");
		this.getElement().getStyle().setZIndex(layer);
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.add(btnCancel);
	}
	
	
}
