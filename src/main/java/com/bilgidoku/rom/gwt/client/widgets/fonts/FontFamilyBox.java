package com.bilgidoku.rom.gwt.client.widgets.fonts;

import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class FontFamilyBox extends Composite implements HasChangeHandlers {
	private Images images = GWT.create(Images.class);
	private String activeFont = "Arial";
	private String text = "Bir şeyler yazılabilir";
	private HTML box = new HTML(getBox());
	
	public FontFamilyBox() {
		box.setStyleName("site-box");
		box.setWidth("190px");
		box.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DlgFontFamily dlg = new DlgFontFamily(text);
				if (activeFont != null) {
					dlg.selectFont(activeFont);
				}
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (dlg.font != null) {
							setFont(dlg.font);
							ChangeEvent.fireNativeEvent(Document.get().createChangeEvent(), FontFamilyBox.this);
						}						
					}
				});
				dlg.show();
				dlg.setPopupPosition(box.getAbsoluteLeft(), event.getClientY() + 20);
				
			}
		});

		HorizontalPanel panel = new HorizontalPanel();
		
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.add(box);
		this.initWidget(panel);
	}

	private String getBox() {
		String html = "<span style='float:left;padding:6px;line-height:14px;font-size:18px;width: 160px; font-family:"+activeFont+";'>"+activeFont.substring(0, 12)+"</span>"
				+ "<span style='width: 16px; display: block;  float: right; height: 100%; position:relative; padding: 4px 0;'>"
				+ AbstractImagePrototype.create(images.arrow_down()).getHTML() + "</span>";
		return html;
	}

	public void setFont(String font) {
		activeFont = font;
		box.setHTML(getBox());
	}

	public String getValue() {
		return activeFont;
	}


	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public void setText(String value) {
		text = value;
		
	}


}
