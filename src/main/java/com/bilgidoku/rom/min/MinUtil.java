package com.bilgidoku.rom.min;

public class MinUtil {
	
	public static String merge(String delimeter, String defVal, String... params){
		StringBuilder sb=new StringBuilder();
		boolean  first=true;
		for (String string : params) {
			if(string==null)
				continue;
			if(first)
				first=true;
			else
				sb.append(delimeter);
			sb.append(string);
		}
		if(first)
			return defVal;
		return sb.toString();
	}
	
	public static String coalesce(String first, String last, String def){
		if(first!=null)
			return first;
		if(last!=null)
			return last;
		return def;
	}

}
