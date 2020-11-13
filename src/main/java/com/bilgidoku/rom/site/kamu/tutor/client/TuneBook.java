package com.bilgidoku.rom.site.kamu.tutor.client;

import com.google.gwt.user.client.ui.HTML;

public class TuneBook extends HTML{


	private int width;


	public TuneBook(String name, int width) {
		super();
		this.getElement().setId(name);
		this.width=width;
	}

	
	public String getId(){
		return this.getElement().getId();
	}
	
	public void render(String tunes) {
		tuneRender(getId(), tunes, width);
	}

	
	public static native void tuneRender(final String id, final String tunes, int width2) /*-{
	   $wnd.ABCJS.renderAbc(id, tunes,{},{staffwidth: width2}); 
	}-*/;


	public void showNote(String note) {
		render("X:1\nT:\nM:\n"+note);
	}
}
