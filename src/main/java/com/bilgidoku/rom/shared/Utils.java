package com.bilgidoku.rom.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

//	private static String TRANS_FROM = "çüşöğı";
//	private static String TRANS_TO = "cusogi";

	public static String switchQuote(String r){
		StringBuilder sb=new StringBuilder();
		for (char it : r.toCharArray()) {
			if(it=='"'){
				sb.append('½');
			}else if(it=='½'){
				sb.append('"');
			}else{
				sb.append(it);
			}
		}
		return sb.toString();
	}
	
	public static String nameFix(String fileName) {
		if (fileName == null)
			return null;
		
//		StringBuilder sb = new StringBuilder();
		
		fileName = fileName.toLowerCase();
		fileName = fileName.replaceAll("İ", "i");
		fileName = fileName.replaceAll("ç", "c");
		fileName = fileName.replaceAll("ü", "u");
		fileName = fileName.replaceAll("ş", "s");
		fileName = fileName.replaceAll("ö", "o");
		fileName = fileName.replaceAll("ğ", "g");
		fileName = fileName.replaceAll("ı", "i");
		
		fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "-");
		
		fileName = fileName.replace("---", "-");
		fileName = fileName.replace("--", "-");		
		
		if (fileName.startsWith("-")) {
			fileName = fileName.substring(1);
		} 
		
		if (fileName.endsWith("-")) {
			fileName = fileName.substring(0, fileName.length() -1);
		}
		
		
//		for (int i = 0; i < fileName.length(); i++) {
//			char c = fileName.charAt(i);
//
//			int tr = TRANS_FROM.indexOf(c);
//			if (tr >= 0) {
//				sb.append(TRANS_TO.charAt(tr));
//				continue;
//			}
//			
//			if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.') {
//				sb.append(c);
//			} else {
//				sb.append("-");
//			}
//		}
		return fileName;
	}

	public static boolean isUrlChar(char c) {
		// From MediaWiki: "._\\/~%-+&#?!=()@"
		// From http://www.ietf.org/rfc/rfc2396.txt :
		// reserved: ";/?:@&=+$,"
		// unreserved: "-_.!~*'()"
		// delim: "%#"
		if (isLatinLetterOrDigit(c))
			return true;
		return "/?@&=+,-_.!~()%#;:$*".indexOf(c) >= 0; // I excluded '\''
	}

	public static boolean isLatinLetterOrDigit(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
	}

	/**
	 * Filters text so there are no '\r' chars in it ("\r\n" -&gt; "\n"; then
	 * "\r" -&gt; "\n"). Most importantly makes all blank lines (lines with only
	 * spaces) exactly like this: "\n\n". WikiParser relies on that.
	 * 
	 * @param text
	 * @return filtered text
	 */
	public static String preprocessWikiText(String text) {
		if (text == null)
			return "";
		text = text.trim();
		int length = text.length();
		char[] chars = new char[length];
		text.getChars(0, length, chars, 0);
		StringBuilder sb = new StringBuilder();
		boolean blankLine = true;
		StringBuilder spaces = new StringBuilder();
		for (int p = 0; p < length; p++) {
			char c = chars[p];
			if (c == '\r') { // "\r\n" -> "\n"; then "\r" -> "\n"
				if (p + 1 < length && chars[p + 1] == '\n')
					p++;
				sb.append('\n');
				spaces.delete(0, spaces.length()); // discard spaces if there is
													// nothing else on the line
				blankLine = true;
			} else if (c == '\n') {
				sb.append(c);
				spaces.delete(0, spaces.length()); // discard spaces if there is
													// nothing else on the line
				blankLine = true;
			} else if (blankLine) {
				if (c <= ' '/* && c!='\n' */) {
					spaces.append(c);
				} else {
					if (spaces.length() > 0)
						sb.append(spaces);
					blankLine = false;
					sb.append(c);
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String escapeHTML(String s) {
		if (s == null)
			return "";
		StringBuffer sb = new StringBuffer(s.length() + 100);
		int length = s.length();

		for (int i = 0; i < length; i++) {
			char ch = s.charAt(i);

			if ('<' == ch) {
				sb.append("&lt;");
			} else if ('>' == ch) {
				sb.append("&gt;");
			} else if ('&' == ch) {
				sb.append("&amp;");
			} else if ('\'' == ch) {
				sb.append("&#39;");
			} else if ('"' == ch) {
				sb.append("&quot;");
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	private static HashMap<String, Character> entities = null;

	private static synchronized HashMap<String, Character> getHtmlEntities() {
		if (entities == null) {
			entities = new HashMap<String, Character>();
			entities.put("lt", '<');
			entities.put("gt", '>');
			entities.put("amp", '&');
			entities.put("quot", '"');
			entities.put("apos", '\'');
			entities.put("nbsp", '\u00A0');
			entities.put("shy", '\u00AD');
			entities.put("copy", '\u00A9');
			entities.put("reg", '\u00AE');
			entities.put("trade", '\u2122');
			entities.put("mdash", '\u2014');
			entities.put("ndash", '\u2013');
			entities.put("ldquo", '\u201C');
			entities.put("rdquo", '\u201D');
			entities.put("euro", '\u20AC');
			entities.put("middot", '\u00B7');
			entities.put("bull", '\u2022');
			entities.put("laquo", '\u00AB');
			entities.put("raquo", '\u00BB');
		}
		return entities;
	}

	public static String unescapeHTML(String value) {
		if (value == null)
			return null;
		if (value.indexOf('&') < 0)
			return value;
		HashMap<String, Character> ent = getHtmlEntities();
		StringBuffer sb = new StringBuffer();
		final int length = value.length();
		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);
			if (c == '&') {
				char ce = 0;
				int i1 = value.indexOf(';', i + 1);
				if (i1 > i && i1 - i <= 12) {
					if (value.charAt(i + 1) == '#') {
						if (value.charAt(i + 2) == 'x') {
							ce = (char) atoi(value.substring(i + 3, i1), 16);
						} else {
							ce = (char) atoi(value.substring(i + 2, i1));
						}
					} else {
						synchronized (ent) {
							Character ceObj = ent.get(value.substring(i + 1, i1));
							ce = ceObj == null ? 0 : ceObj.charValue();
						}
					}
				}
				if (ce > 0) {
					sb.append(ce);
					i = i1;
				} else
					sb.append(c);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	static public int atoi(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Throwable ex) {
			return 0;
		}
	}

	static public int atoi(String s, int base) {
		try {
			return Integer.parseInt(s, base);
		} catch (Throwable ex) {
			return 0;
		}
	}

	public static String replaceString(String str, String from, String to) {
		StringBuffer buf = new StringBuffer();
		int flen = from.length();
		int i1 = 0, i2 = 0;
		while ((i2 = str.indexOf(from, i1)) >= 0) {
			buf.append(str.substring(i1, i2));
			buf.append(to);
			i1 = i2 + flen;
		}
		buf.append(str.substring(i1));
		return buf.toString();
	}

	public static String[] split(String s, char separator) {
		// this is meant to be faster than String.split() when separator is not
		// regexp
		if (s == null)
			return null;
		ArrayList<String> parts = new ArrayList<String>();
		int beginIndex = 0, endIndex;
		while ((endIndex = s.indexOf(separator, beginIndex)) >= 0) {
			parts.add(s.substring(beginIndex, endIndex));
			beginIndex = endIndex + 1;
		}
		parts.add(s.substring(beginIndex));
		String[] a = new String[parts.size()];
		return parts.toArray(a);
	}

	private static final String translitTable = "àaábâvãgädåe¸eæzhçzèiéyêkëlìmínîoïpðrñsòtóuôfõhöts÷chøshùschüûyúýeþyuÿyaÀAÁBÂVÃGÄDÅE¨EÆZHÇZÈIÉYÊKËLÌMÍNÎOÏPÐRÑSÒTÓUÔFÕHÖTS×CHØSHÙSCHÜÛYÚÝEÞYUßYA";

	/**
	 * Translates all non-basic-latin-letters characters into latin ones for use
	 * in URLs etc. Here is the implementation for cyrillic (Russian) alphabet.
	 * Unknown characters are omitted.
	 * 
	 * @param s
	 *            string to be translated
	 * @return translated string
	 */
	public static String translit(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder(s.length() + 100);
		final int length = s.length();
		final int translitTableLength = translitTable.length();

		for (int i = 0; i < length; i++) {
			char ch = s.charAt(i);
			// com.bilgidoku.Sistem.errln("ch="+(int)ch);

			if ((ch >= 'à' && ch <= 'ÿ') || (ch >= 'À' && ch <= 'ß') || ch == '¸' || ch == '¨') {
				int idx = translitTable.indexOf(ch);
				char c;
				if (idx >= 0) {
					for (idx++; idx < translitTableLength; idx++) {
						c = translitTable.charAt(idx);
						if ((c >= 'à' && c <= 'ÿ') || (c >= 'À' && c <= 'ß') || c == '¸' || c == '¨')
							break;
						sb.append(c);
					}
				}
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String emptyToNull(String s) {
		return "".equals(s) ? null : s;
	}

	public static String noNull(String s) {
		return s == null ? "" : s;
	}

	public static String noNull(String s, String val) {
		return s == null ? val : s;
	}

	public static boolean isEmpty(String s) {
		return (s == null || s.length() == 0);
	}

	public static boolean objectsEqual(Object a, Object b) {
		if (a == null && b == null)
			return true;
		if (a == null && b != null)
			return false;
		if (b == null && a != null)
			return false;
		
		return a.equals(b);
	}
}
