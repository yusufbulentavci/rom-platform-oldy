package com.bilgidoku.rom.pg.dict;

public enum Format {
	HTML, JSON, JS;
	
	public static Format fromStr(String s){
		if(s==null)
			return HTML;
		
		if(s.equals("js"))
			return JS;
		
		if(s.equals("json"))
			return JSON;
		
		return HTML;
	}
}
