package com.bilgidoku.rom.site.kamu.tutor.client;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;

public class Vector2d {
	public Vector2d(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d(JSONArray array) throws RunException {
		this.x=array.get(0).isNumber().intValue();
		this.y=array.get(1).isNumber().intValue();
	}

	public int x, y;

}
