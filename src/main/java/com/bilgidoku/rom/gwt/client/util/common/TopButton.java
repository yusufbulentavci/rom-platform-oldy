package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;

public class TopButton extends Anchor implements HelpButton {
	
	private Helpy helpy = null;
	
	public TopButton(String img, String text, String title, String helpUri) {
		super();
		this.setHTML(getNote(img, text));
		this.setTitle(title);
		this.getElement().getStyle().setBorderWidth(0, Unit.PX);
		this.getElement().getStyle().setColor("white");
		this.getElement().getStyle().setProperty("background", "transparent");
		this.getElement().getStyle().setProperty("padding", "7px 3px 1px");		
		this.setSize("20px", "20px");
		this.addMouseOverHandler(new MouseOverHandler() {			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "pointer");				
			}
		});
		
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");				
			}
		});
		
		
		String[] arr = { helpUri };
		this.helpy = new Helpy("", arr, this);
	}
	
	private String getNote(String img, String count) {
		if (img == null)
			return count;
		
		if (count == null || (count != null && count.isEmpty()))
			return ClientUtil.imageItemHTML(img, null);
		
		return ClientUtil.imageItemHTML(img, "<sup style='font-size: 10px;font-family:arial;font-weight:bold;color: black; background-color:white; padding: 3px; border-radius: 6px; margin-left: -4px;'>"+count+"</sup>");
	}
	
	public void changeHTML(String img, String count) {
		this.setHTML(getNote(img, count));
	}

	@Override
	public Helpy getHelpy() {
		return helpy;
	}
	
	
}
