package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.com.RomFrameHandler;
import com.bilgidoku.rom.gwt.client.util.com.RomFrameImpl;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;

public class HtmlEditAreaWrapper extends RomFrameImpl {
	public static HtmlEditAreaWrapper create(String initial){
		String debug = Window.Location.getParameter("rom.debug");
		String defUrl=(debug!=null && debug.indexOf("htmledit")>=0) ? "/_public/htmleditdebug.html" : "/_public/htmledit.html";
		return new HtmlEditAreaWrapper(defUrl, initial);
	}
	
	private HtmlEditAreaWrapper(String url, String initial){
		super(new UrlBuilder().setPath(url), new RomFrameHandler() {
			
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
