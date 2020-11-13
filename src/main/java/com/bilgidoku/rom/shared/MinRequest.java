package com.bilgidoku.rom.shared;

import com.bilgidoku.rom.shared.json.JSONArray;

public interface MinRequest {
	public int getBw();
	public String getReqLang();
	public JSONArray getLangs();
	public Postman getPostman();
	public boolean isInitial();
	public Cookie getCookie();
	public boolean isACookie(String name);
}
