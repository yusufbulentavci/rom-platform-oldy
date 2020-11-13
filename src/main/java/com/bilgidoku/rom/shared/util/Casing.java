package com.bilgidoku.rom.shared.util;

import java.util.Locale;

public class Casing {

	static char[] turkishExcLower = { 'i', 'ı' };
	static char[] turkishExcUpper = { 'İ', 'I' };

	private static final String TURKISH = "tr";

	public static String upperCase(String string, String lang) {
		if (!lang.equals(TURKISH)) {
			return string.toUpperCase();
		}

		StringBuilder sb = new StringBuilder();
		for (char it : string.toCharArray()) {
			boolean hit = false;
			for (int i = 0; i < turkishExcLower.length; i++) {
				char c = turkishExcLower[i];
				if (c == it) {
					sb.append(turkishExcUpper[i]);
					hit = true;
				}
			}
			if (!hit) {
				sb.append(Character.toUpperCase(it));
			}
		}
		return sb.toString();
		// Locale def = Locale.getDefault();
		// Locale toLang = new Locale(lang);
		// boolean changed = false;
		//
		// try {
		// if (!def.getLanguage().equals(toLang.getLanguage())) {
		// Locale.setDefault(toLang);
		// }
		// return string.toUpperCase();
		//
		// } finally {
		// if (changed) {
		// Locale.setDefault(def);
		// }
		// }
	}

	public static String lowerCase(String string, String lang) {
		if (!lang.equals(TURKISH)) {
			return string.toLowerCase();
		}

		StringBuilder sb = new StringBuilder();
		for (char it : string.toCharArray()) {
			boolean hit = false;
			for (int i = 0; i < turkishExcLower.length; i++) {
				char c = turkishExcUpper[i];
				if (c == it) {
					sb.append(turkishExcLower[i]);
					hit = true;
				}
			}
			if (!hit) {
				sb.append(Character.toLowerCase(it));
			}
		}
		return sb.toString();

		// Locale def = Locale.getDefault();
		// Locale toLang = new Locale(lang);
		// boolean changed = false;
		//
		// try {
		// if (!def.getLanguage().equals(toLang.getLanguage())) {
		// Locale.setDefault(toLang);
		// }
		// return string.toLowerCase();
		//
		// } finally {
		// if (changed) {
		// Locale.setDefault(def);
		// }
		// }
	}

	// public static String capitalize(String value) {
	// return value == null ? value : value.substring(0, 1).toUpperCase() +
	// value.substring(1).toLowerCase();
	// }

	public static String capitalize(String string, String lang) {
		StringBuilder sb = new StringBuilder();
		boolean up=true;
		for (char it : string.toCharArray()) {
			sb.append(up?charUpper(it, lang):it);
			if(it==' '){
				up=true;
			}else{
				up=false;
			}
		}
		return sb.toString();

		// Locale def = Locale.getDefault();
		// Locale toLang = new Locale(lang);
		// boolean changed = false;
		//
		// try {
		// if (!def.getLanguage().equals(toLang.getLanguage())) {
		// Locale.setDefault(toLang);
		// }
		//
		// String nlang = string.toLowerCase();
		// final StringBuilder result = new StringBuilder(nlang.length());
		// String[] words = nlang.split("\\s");
		// for (int i = 0, l = words.length; i < l; ++i) {
		// if (i > 0)
		// result.append(" ");
		// result.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));
		//
		// }
		//
		// return result.toString();
		//
		// } finally {
		// if (changed) {
		// Locale.setDefault(def);
		// }
		// }
	}

	private static char charUpper(char it, String lang) {
		if (!lang.equals(TURKISH)) {
			return Character.toUpperCase(it);
		}

		for (int i = 0; i < turkishExcLower.length; i++) {
			char c = turkishExcLower[i];
			if (c == it) {
				return turkishExcUpper[i];
			}
		}
		return Character.toUpperCase(it);
	}
}
