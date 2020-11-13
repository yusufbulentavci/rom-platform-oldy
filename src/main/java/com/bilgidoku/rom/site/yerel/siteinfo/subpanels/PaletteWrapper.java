package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class PaletteWrapper {
	private JSONObject stored;

	public PaletteWrapper(JSONObject siteData) {
		this.stored = siteData;
	}
	
	public String getBack() {
		return ClientUtil.getString(stored.get("back"));
	}

	public void setBack(String back) {
		stored.put("back", new JSONString(back));
	}

	public String getBackmost() {
		return ClientUtil.getString(stored.get("backmost"));
	}

	public void setBackmost(String backmost) {
		stored.put("backmost", new JSONString(backmost));
	}

	public String getForemost() {
		return ClientUtil.getString(stored.get("foremost"));
	}

	public void setForemost(String foremost) {
		stored.put("foremost", new JSONString(foremost));
	}

	public String getFore() {
		return ClientUtil.getString(stored.get("fore"));
	}

	public void setFore(String fore) {
		stored.put("fore", new JSONString(fore));
	}

	public String getColor3() {
		return ClientUtil.getString(stored.get("color3"));
	}

	public void setColor3(String color3) {
		stored.put("color3", new JSONString(color3));
	}

	public String getTextcolor() {
		return ClientUtil.getString(stored.get("text_color"));
	}

	public void setTextcolor(String textcolor) {
		stored.put("text_color", new JSONString(textcolor));
	}

	public String getTexthlcolor() {
		return ClientUtil.getString(stored.get("text_strong"));
	}

	public void setTexthlcolor(String texthlcolor) {
		stored.put("text_strong", new JSONString(texthlcolor));
	}

	public String getTextwkcolor() {
		return ClientUtil.getString(stored.get("text_weak"));
	}

	public void setTextwkcolor(String textwkcolor) {
		stored.put("text_weak", new JSONString(textwkcolor));
	}

	public String getTextColorOnFore() {
		return ClientUtil.getString(stored.get("fore_text_color"));
	}

	public void setTextColorOnFore(String textbkhlcolor) {
		stored.put("fore_text_color", new JSONString(textbkhlcolor));
	}

	public String getTextColorOnForeMost() {
		return ClientUtil.getString(stored.get("foremost_text_color"));
	}

	public void setTextColorOnForeMost(String color) {
		stored.put("foremost_text_color", new JSONString(color));
	}

	public String getTextColorColor3() {
		return ClientUtil.getString(stored.get("color3_text_color"));
	}
	public void setTextColorColor3(String value) {
		stored.put("color3_text_color", new JSONString(value));
	}
	/*
	public void restore() {
		this.stored = JSONParser.parseStrict(this.orig).isObject();
	}
	public void submit() {
		this.orig = this.stored.toString();
	}
	*/
	public JSONObject asJSONObject() {
		return this.stored;
	}

	public void setBackImage(JSONValue imgUri) {
		stored.put("back_img", imgUri);		
	}

	public String getBackImage() {
		return ClientUtil.getString(stored.get("back_img"));		
	}

	public void setBackPattern(JSONValue imgUri) {
		stored.put("back_ptn", imgUri);		
	}

	public String getBackPattern() {
		return ClientUtil.getString(stored.get("back_ptn"));		
	}

	
}
