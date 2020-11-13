package com.bilgidoku.rom.gwt.client.util.help;

import com.google.gwt.user.client.ui.Widget;


public class Helpy {
	protected String text;
	protected String[] uris;
	private Widget comp;	
	
	public Helpy(String title, String[] helpUri, Widget comp) {
		super();
		this.text = title;
		this.uris = helpUri;
		this.setComp(comp);
		
	}


	public String getText() {
		return text;
	}

	public void setText(String title) {
		this.text = title;
	}


	public void setUris(String[] arr) {
		this.uris = arr;		
	}

	public String[] getUris() {
		return this.uris;
	}

	public Widget getComp() {
		return comp;
	}


	public void setComp(Widget comp) {
		this.comp = comp;
	}
	
}
