package com.bilgidoku.rom.web.html;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;

public class RomError {
	private static final char OK='o';
	private static final char WARN='w';
	private static final char FATAL='f';
	
	
	private int IND_FATALITY=0;
	private int IND_SECURITY=1;
	private int IND_VISIBILTY=2;
	private int IND_REPEAT=3;
	
	private final String code;
	private final String obj;
	public RomError(String code2, String substring) {
		code=code2;
		obj=substring;
	}
	public String getCode() {
		return code;
	}
	public String getObj() {
		return obj;
	}
	
	public String errCode(){
		return code.substring(5);
	}
	
	public boolean isClientVisible(){
		return code.charAt(IND_VISIBILTY)==OK;
	}
	
	public boolean toRepeat(){
		return code.charAt(IND_REPEAT)!=FATAL;
	}
	
	public String clientMsg(){
		StringBuilder sb=new StringBuilder();
		JSONWriter jsonWriter = new JSONWriter(sb);
		try {
			jsonWriter.object();
			jsonWriter.key("sign").value("_rom_");
			jsonWriter.key("msg").value(obj);
			jsonWriter.key("repeat").value(toRepeat());
			jsonWriter.key("errcode").value(errCode());
			jsonWriter.endObject();
			return sb.toString();
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		
	}
}
