package com.bilgidoku.rom.web.html;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

public class Infonort  {

	public String uri;
	public String style;
	public String widgets;
	public String default_app;
	public String text1;
	public String text2;
	public String site_footer;
	public String address;
	public String browser_title;
	public String menu1;
	public String menu2;
	public String list1;
	public String list2;
	public String palette;
	public String text_font;
	public String browser_icon;
	public String banner_img;
	public String logo_img;
	public String langcode;
	public String[] langcodes;

	public JSONObject infoJson() throws JSONException{
		JSONObject ret=new JSONObject();
		ret.put("uri", uri);
		ret.put("text1", text1);
		ret.put("text2", text2);
		ret.put("site_footer", site_footer);
		ret.put("address", address);
		ret.put("browser_title", browser_title);
		ret.put("menu1", menu1);
		ret.put("menu2", menu2);
		ret.put("list1", list1);
		ret.put("list2", list2);
		ret.put("browser_icon", browser_icon);
		ret.put("browser_title", browser_title);
//		ret.append("langcode", langcode);
//		ret.append("", );
//		ret.append("", );
		return ret;
	}
	
	JSONObject paletteJson() throws JSONException{
		return new JSONObject(palette);
	}
	
	JSONObject logoJson() throws JSONException{
		return new JSONObject(logo_img);
	}
	
	JSONObject fontJson() throws JSONException{
		return new JSONObject(text_font);
	}
	

}