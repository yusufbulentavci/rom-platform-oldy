package com.bilgidoku.rom.shared.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Knife {
	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	

	public static Map<String, String> initMap(HashMap<String, String> map, String[] values) {
		for(int i=0; i<values.length; i=i+2){
			map.put(values[i], values[i+1]);
		}
		return map;
	}

}
