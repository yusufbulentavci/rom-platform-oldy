package com.bilgidoku.rom.shared.data;

import java.util.HashMap;
import java.util.Map;

public class Sozluk {
	private static Map<String,String> turAdi2Table=new HashMap<>();
	
	public static String turAdiToTableName(String turAdi) {
		return turAdi2Table.get(turAdi);
	}

}
