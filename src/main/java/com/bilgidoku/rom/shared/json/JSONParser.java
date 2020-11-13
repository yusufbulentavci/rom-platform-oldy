package com.bilgidoku.rom.shared.json;

import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;

public class JSONParser {

	public static JSONValue parseStrict(String text) throws RunException{
		return Portable.one.jsonParserParseStrict(text);
	}

}
