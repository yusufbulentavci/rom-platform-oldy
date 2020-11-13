package com.bilgidoku.rom.shared.util;

import java.util.List;

public class StringListUtil {
	
	public static String str(List<String> in, String delim) {
		return String.join(delim, in);
	}

}
