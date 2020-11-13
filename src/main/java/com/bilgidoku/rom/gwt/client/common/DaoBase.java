package com.bilgidoku.rom.gwt.client.common;

import com.google.gwt.http.client.URL;

public class DaoBase {

	public static int append(String paramName, String param, StringBuffer data,
			 int hopCus, int i) {
		i++;
		if (param != null) {
			data.append(paramName);
			data.append("=");
			data.append(URL.encodeQueryString(param));
			if (i != hopCus)
				data.append("&");
		}
		return i;
	}
}
