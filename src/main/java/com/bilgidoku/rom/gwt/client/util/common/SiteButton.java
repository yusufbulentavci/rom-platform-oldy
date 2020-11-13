package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;

public class SiteButton extends Button implements HelpButton {

	private int index = 0;
	public boolean selected = false;
	private Helpy helpy = null;

	public SiteButton() {
		super();
	}

	public SiteButton(int index) {
		this();
		this.setIndex(index);
	}

	public SiteButton(String text, String title) {
		super(text);
		this.setTitle(title);
		this.setStyleName("site-btn");
		// this.addStyleName("site-right");
	}

	public SiteButton(String img, String text, String title) {
		super(imageItemHTML(img, text));
		this.setTitle(title);
		this.setStyleName("site-btn");
	}

	/**
	 * 
	 * @param img
	 * @param text
	 * @param title
	 * @param size: null big sml
	 */
	public SiteButton(String img, String text, String title, String size) {
		super(imageItemHTML(img, text));
		this.setTitle(title);
		this.setStyleName("site-btn");
		if (size != null)
			this.addStyleName("btn-" + size);
	}

	public SiteButton(ImageResource img, String text, String helpTitle, String size) {
		super(imageItemHTML(img, text));
		this.setTitle(helpTitle);
		this.setStyleName("site-btn");
		if (size != null)
			this.addStyleName("btn-" + size);

//		String[] arr = { helpUri };
//		this.helpy = new Helpy(helpTitle, arr, this);
	}

	public void setSelected() {
		this.selected = true;
		this.addStyleName("site-btnselected");
	}

	public void removeSelected() {
		this.selected = false;
		this.removeStyleName("site-btnselected");
	}

	public boolean isSelected() {
		return this.selected;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static String imageItemHTML(ImageResource imageProto, String title) {
		if (imageProto == null)
			return title;
		
		if (title == null)
			return AbstractImagePrototype.create(imageProto).getHTML();
		else
			return AbstractImagePrototype.create(imageProto).getHTML() + " " + title;
	}

	public static String imageItemHTML(String imageUri, String title) {
		String s = "";
		if (imageUri != null && !imageUri.isEmpty())
			s = "<img src='" + imageUri + "' style='vertical-align:middle;'>";

		if (title != null)
			s = s + " " + title;

		return s;

	}

	@Override
	public Helpy getHelpy() {
		return helpy;
	}

	public void makeSml() {
		// TODO Auto-generated method stub

	}
	
//	private Helpy helpy = null;
//	private int index = 0;	
//
//	public SiteButton(String img, String buttonTitle, String helpTitle, String helpUri) {
//		super(ClientUtil.imageItemHTML(img, buttonTitle));	
//		
//		setTitle(helpTitle);
//		setHelpy(helpUri);
//		
//	}
//
//	public int getIndex() {
//		return index;
//	}
//
//	public void setIndex(int index) {
//		this.index = index;
//	}
//
//	public Helpy getHelpy() {
//		return helpy;
//	}
//	
//	public void setHelpy(String uri) {
//		if (uri != null && !uri.isEmpty()) {
//			String[] arr = {uri};
//			this.helpy = new Helpy(this.getTitle(), arr, this);
//		}
//	}


}
