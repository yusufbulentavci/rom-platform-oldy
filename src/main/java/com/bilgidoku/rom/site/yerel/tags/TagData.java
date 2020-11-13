package com.bilgidoku.rom.site.yerel.tags;

import java.util.Map;

import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.NullableMap;

public class TagData {
	
	private Code code;
	private String tag;
	
	public TagData(String tag, Code code) {
		this.setCode(code);
		this.setTag(tag);
	}

	public Map<String, String> getStyleByType(String key) {
		return code.getStyleByType(key);
	}

	public String getNodeText() {
		return code.getText();
	}

	public NullableMap<String, String>  getParams() {
		return code.params;
	}

	public NullableMap<String, String> getAttribute() {
		return code.ats;
	}

	public void setNodeText(String value) {
		code.text = value;		
	}

	public void setAttribute(String name, String value) {
		code.ats.put(name, value);
	}

	public void setStyleByType(String styleType, String name, String value) {		
		code.setStyleByType(styleType, name, value);		
	}

	public void setParam(String name, String value) {
		code.params.put(name, value);
		
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public void setStyles(Map<String, String> styles) {
		// TODO Auto-generated method stub
		
	}

	public void addStyleByType(String sType, String styleName, String styleValue) {		
		code.addStyleByType(sType, styleName, styleValue);		
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}