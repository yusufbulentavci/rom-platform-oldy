package com.bilgidoku.rom.gwt.client.util;

import com.google.gwt.user.client.Cookies;

public class KnownVariable {
	
	public static String cid(){
		return Cookies.getCookie("cid");
	}

}
