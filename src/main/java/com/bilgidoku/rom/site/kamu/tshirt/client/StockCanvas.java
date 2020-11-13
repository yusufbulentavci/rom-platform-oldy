package com.bilgidoku.rom.site.kamu.tshirt.client;

import com.google.gwt.json.client.JSONObject;

public class StockCanvas {
	String name;
	int height;
	int width;
	int x;
	int y;
	
	String rendered;
	
	String backImg;
	int backW;
	int backH;
	JSONObject elms;

	
	public StockCanvas(String key, int width2, int height2, int x2, int y2, int backW2, int bachH2, String backImage2, String rendered2, JSONObject elms) {
		this.name=key;
		this.width=width2;
		this.height=height2;
		this.x = x2;
		this.y = y2;
		this.backW = backW2;
		this.backH = bachH2;		
		this.rendered=rendered2;
		this.backImg = backImage2;
		this.elms=elms;
	}
}
