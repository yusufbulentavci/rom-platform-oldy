package com.bilgidoku.rom.ilk.file;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

public class FromResource {
	public static InputStream inputStream(String rn) throws IOException {
		InputStream f = getRes(rn);
		if (f == null) {
			throw new NullPointerException("Resource file not found:" + rn);
		}
		return f;
	}

	public static InputStream getRes(String rn) {

		if (!rn.startsWith("/")) {
			//rn = rn.substring(1);
			rn = "/"+rn;
		}

		//InputStream u = ClassLoader.getSystemClassLoader().getResourceAsStream(rn);
		InputStream u = FromResource.class.getResourceAsStream(rn);
		if(u != null)
			return u;
		//System.out.println("Failed for:" + rn);

		rn=rn.substring(1);

		//System.out.println("Tried for:" + rn);
		//u = ClassLoader.getSystemClassLoader().getResourceAsStream(rn);
		u = FromResource.class.getResourceAsStream(rn);
		return u;
	}

	public static String loadString(String rn) throws IOException {
		InputStream f = getRes(rn);
		if (f == null) {
			throw new NullPointerException("Resource file not found:" + rn);
		}

		byte[] buffer = new byte[100000];
		int t = IOUtils.read(f, buffer);
		String s = new String(buffer, 0, t, "UTF-8");
		// System.err.println("========="+rn+"============"+t);
		// System.err.println(s);
		// System.err.println("========================");
		return s;
	}

	public static JSONObject loadJsonObject(String rn) throws IOException, JSONException {
		return new JSONObject(loadString(rn));

	}

	public static JSONArray loadJsonArray(String rn) throws IOException, JSONException {
		return new JSONArray(loadString(rn));
	}

}
