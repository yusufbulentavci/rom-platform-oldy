package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.com.RomFrameHandler;
import com.bilgidoku.rom.gwt.client.util.com.RomFrameImpl;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;

public class UmlEditWrapper extends RomFrameImpl{
	public static UmlEditWrapper create(String initial){
		String debug = Window.Location.getParameter("rom.debug");
		String defUrl=(debug!=null && debug.indexOf("umledit")>=0) ? "/_static/js/uml/umldebug.html" : "/_static/js/uml/uml.html";
		return new UmlEditWrapper(defUrl, initial);
	}
	private UmlEditWrapper(String url, String initial){
		super( new UrlBuilder().setPath(url), new RomFrameHandler() {
			
			@Override
			public void setItem(String cls, String uri) {
			}
			
			@Override
			public void ready() {
			}
			
			@Override
			public void focusItem(String cls, String uri) {
			}
		}, initial);
	}
}	



