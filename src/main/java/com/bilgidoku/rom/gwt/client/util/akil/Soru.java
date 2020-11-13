package com.bilgidoku.rom.gwt.client.util.akil;

import com.bilgidoku.rom.shared.json.JSONArray;

public class Soru {
	final String yazi;
	final String soru;
	final String tip;
	final JSONArray olasi;

	public Soru(String soru, String yazi, String tip, JSONArray olasi) {
		super();
		this.soru = soru;
		this.yazi = yazi;
		this.tip = tip;
		this.olasi = olasi;
	}
}
