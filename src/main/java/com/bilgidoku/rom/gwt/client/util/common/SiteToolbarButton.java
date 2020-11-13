package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;

public class SiteToolbarButton extends Button implements HelpButton {

	private Helpy helpy = null;
	private boolean selected = false;

	public SiteToolbarButton(String imgIcon, String buttonText, String helpTitle, String helpUri) {		
		super(ClientUtil.imageItemHTML(imgIcon, buttonText));

		this.setStyleName("site-toolbarbutton");
		this.setTitle(helpTitle);
		
		String[] arr = { helpUri };
		this.helpy = new Helpy(helpTitle, arr, this);
	}

	public SiteToolbarButton(String imgIcon, String buttonText, String helpTitle, String[] arr) {		
		super(ClientUtil.imageItemHTML(imgIcon, buttonText));

		this.setStyleName("site-toolbarbutton");
		this.setTitle(helpTitle);
		
		this.helpy = new Helpy(helpTitle, arr, this);
	}

	
	public SiteToolbarButton(SafeHtml html, String helpTitle, String helpUri) {
		super(html);
		this.setTitle(helpTitle);
		
		String[] arr = { helpUri };
		this.helpy = new Helpy(helpTitle, arr, this);

	}
	

	public SiteToolbarButton(String buttonText, String helpTitle, String helpUri) {
		this(null, buttonText, helpTitle, helpUri);
	}

	public SiteToolbarButton(SafeHtml header, String pageColors, String[] strings) {
		this(header, pageColors, "");
		this.helpy.setUris(strings);
	}

	public void setUris(String[] arr) {
		helpy.setUris(arr);
	}

	public void changeTitle(String tip) {
		this.setTitle(tip);
	}

	@Override
	public Helpy getHelpy() {
		return helpy;
	}

	public void setSelected() {
		this.selected = true;
		this.addStyleName("site-ucgen");
	}

	public void removeSelected() {
		this.selected = false;
		this.removeStyleName("site-ucgen");
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setHelpy(String uri) {
		if (uri != null && !uri.isEmpty()) {
			String[] arr = { uri };
			this.helpy = new Helpy(this.getTitle(), arr, this);
		}
	}

}
