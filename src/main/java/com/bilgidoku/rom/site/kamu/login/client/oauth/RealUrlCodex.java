package com.bilgidoku.rom.site.kamu.login.client.oauth;


public class RealUrlCodex {
	public static native String encode(String url) /*-{
		var regexp = /%20/g;
		return encodeURIComponent(url).replace(regexp, "+");
	}-*/;

	public static native String decode(String url) /*-{
		var regexp = /\+/g;
		return decodeURIComponent(url.replace(regexp, "%20"));
	}-*/;

}