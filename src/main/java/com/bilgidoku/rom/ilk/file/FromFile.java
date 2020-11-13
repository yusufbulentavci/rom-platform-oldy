package com.bilgidoku.rom.ilk.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

public class FromFile {
	
	public static JSONObject loadJsonObject(File file) throws IOException, JSONException{
		try {
			
			return new JSONObject(FileUtils.readFileToString(file));
		} catch (JSONException e) {
			throw new JSONException("Error in json file:"+file+" ",e);
		}
	}

}
