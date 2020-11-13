package com.bilgidoku.rom.site.kamu.tutor.client;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;

public class Vector3d {
	public Vector3d(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3d(JSONArray array) throws RunException {
		this.x=array.get(0).isNumber().intValue();
		this.y=array.get(1).isNumber().intValue();
		this.z=array.get(2).isNumber().intValue();
	}

	public int x, y, z;

}
