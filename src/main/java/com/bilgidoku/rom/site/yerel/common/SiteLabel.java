package com.bilgidoku.rom.site.yerel.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class SiteLabel extends HorizontalPanel {
	
	public SiteLabel(String text, final String desc) {
		this.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);		
		this.add(new HTML(text));
		if (desc != null && !desc.isEmpty()) {
			final ImageAnchor anchor = new ImageAnchor("/_local/images/common/info.png");
			anchor.setTitle(desc);
			anchor.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {
					PopupPanel pp = new PopupPanel(true);
					pp.add(new HTML(desc));
					pp.setPopupPosition(anchor.getAbsoluteLeft() + 10, anchor.getAbsoluteTop() + 10);
					pp.show();
					
				}
			});
			this.add(new HTML("&nbsp;&nbsp;"));
			this.add(anchor);
			this.setTitle(desc);
			
		}
	}
	
}
