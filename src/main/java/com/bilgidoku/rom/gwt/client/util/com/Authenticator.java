package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.service.DescRespResponse;
import com.bilgidoku.rom.gwt.araci.client.service.YetkilendirmeDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.shared.DescResp;
import com.bilgidoku.rom.shared.util.AsyncMethod;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

public class Authenticator {

	private static JavaScriptObject trustedWnd;

	public static void userPassLogin(final String userName, final String password,
			final AsyncMethod<String, String> ret) {
		YetkilendirmeDao.login(userName, password, new DescRespResponse() {

			public void ready(DescResp value) {

				if (value.romerror == null) {
					String cid = Cookies.getCookie("cid");
					String cname = Cookies.getCookie("cname");
					String myDir = (cid == null) ? null : "/f/users/" + cid.substring("/_/co/".length());
					String domain = RomEntryPoint.com().get("wnd.domain");
					String mail = userName + "@" + domain;
					ret.run(cid);
				} else {
					RomEntryPoint.com().set("wnd.error", value.romerror);
					ret.error(value.romerror);
				}
			}

			public void err(int statusCode, String statusText, Throwable exception) {
				RomEntryPoint.com().set("wnd.error", statusText);
				ret.error(statusText);
			}

		});
	}

	public static void needCid(final AsyncMethod<String, String> ret) {
		String cid = RomEntryPoint.com().get("cid");
		if (cid != null)
			return;

		YetkilendirmeDao.letmecontact(new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				if (value.booleanValue()) {
					String cid = Cookies.getCookie("cid");
					if (ret != null)
						ret.run(cid);
				}

			}

		});
	}

	public static void fbLogin() {
		String debug = Window.Location.getParameter("rom.debug");
		String defUrl = (debug != null && debug.indexOf("login") >= 0) ? "/_public/login_debug.html"
				: "/_public/login.html";
		UrlBuilder bd = Location.createUrlBuilder();
		Window.Location.replace(defUrl+"?autologin=facebook&redirect="+URL.encode(bd.buildString()));
	}

//	private static native JavaScriptObject open(String url, String name, String features) /*-{
//		return $wnd.open(url, name, features);
//	}-*/;
//
//	public static void closeTrusted(){
//		if(trustedWnd!=null)
//			close(trustedWnd);
//			
//	}
//
//	private static native JavaScriptObject close(JavaScriptObject o) /*-{
//		return o.close();
//	}-*/;

	public static void logout() {
		String contact = RomEntryPoint.com().get("user.contact");
		if (contact != null)
			return;

		YetkilendirmeDao.logout(new DescRespResponse() {
			@Override
			public void ready(DescResp value) {
				Window.Location.reload();
			}
		});
	}

}
