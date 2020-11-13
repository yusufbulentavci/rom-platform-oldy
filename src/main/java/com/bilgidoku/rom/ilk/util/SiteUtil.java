package com.bilgidoku.rom.ilk.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SiteUtil {

	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static Integer interHost(int hostId) {
		return hostId % 2 == 0 ? hostId : hostId + 1;
	}

	public static Integer intraHost(int hostId) {
		return hostId % 2 == 1 ? hostId : hostId - 1;
	}

	public static String mapToHstore(Map<String, Integer> m) {
		if (m.size() == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean virgul = false;
		for (Entry<String, Integer> it : m.entrySet()) {
			if (virgul) {
				sb.append(",");
			} else
				virgul = true;
			sb.append(quote(it.getKey().toString()));
			sb.append("=>");
			sb.append(it.getValue());
		}
		return sb.toString();
	}

	/**
	 * Produce a string in double quotes with backslash sequences in all the
	 * right places. A backslash will be inserted within </, producing <\/,
	 * allowing JSON text to be delivered in HTML. In JSON text, a string cannot
	 * contain a control character or an unescaped quote or backslash.
	 * 
	 * @param string
	 *            A String
	 * @return A String correctly formatted for insertion in a JSON text.
	 */
	public static String quote(String string) {
		if (string == null || string.length() == 0) {
			return "\"\"";
		}

		char b;
		char c = 0;
		String hhhh;
		int i;
		int len = string.length();
		StringBuffer sb = new StringBuffer(len + 4);

		sb.append('"');
		for (i = 0; i < len; i += 1) {
			b = c;
			c = string.charAt(i);
			switch (c) {
			case '\\':
			case '"':
				sb.append('\\');
				sb.append(c);
				break;
			case '/':
				if (b == '<') {
					sb.append('\\');
				}
				sb.append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
					hhhh = "000" + Integer.toHexString(c);
					sb.append("\\u" + hhhh.substring(hhhh.length() - 4));
				} else {
					sb.append(c);
				}
			}
		}
		sb.append('"');
		return sb.toString();
	}

	public static void deleteDirContent(File f) {
		if(f==null)
			return;
		File[] lf = f.listFiles();
		if(lf==null)
			return;
		for (File c : lf) {
			if (!c.isDirectory()) {
				c.delete();
			}
		}
	}



	public static String escapeQuoteWithQuote(String value) {
		if (value == null)
			return "null";

		StringBuilder sbuf = new StringBuilder();
		sbuf.append('"');

		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if (ch == '"')
				sbuf.append(ch);
			sbuf.append(ch);
		}

		sbuf.append('"');

		return sbuf.toString();
	}

	public static String quoteDbText(String value) {
		if (value == null)
			return "null";

		StringBuilder sbuf = new StringBuilder();
		sbuf.append('"');

		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if (ch == '"')
				sbuf.append("\"\"");
			else
				sbuf.append(ch);
		}
		sbuf.append('"');
		return sbuf.toString();
	}
	
	public static String singleQuoteDbText(String value) {
		if (value == null)
			return "null";

		StringBuilder sbuf = new StringBuilder();
		sbuf.append('\'');

		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if (ch == '\'')
				sbuf.append("''");
			else
				sbuf.append(ch);
		}
		sbuf.append('\'');
		return sbuf.toString();
	}

	public static String unquoteDbText(String value) {
		if (value == null)
			return "null";

		if (value.length() == 0 || value.charAt(0) != '"')
			return value;

		StringBuilder sb = new StringBuilder();

		for (int i = 1; i < value.length() - 1; ++i) {
			char ch = value.charAt(i);

			if (ch == '"') {
//				Assert.error(i < value.length() - 1 && value.charAt(i + 1) == '"',"Has \" in unquote text");
				sb.append('"');
				++i;
			} else
				sb.append(ch);
		}
		return sb.toString();
	}

	public static String quoteDbArrayText(String value) {
		if (value == null)
			return "null";

		StringBuilder sbuf = new StringBuilder();
		sbuf.append('"');

		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if (ch == '"')
				sbuf.append('\\');
			sbuf.append(ch);
		}
		sbuf.append('"');
		return sbuf.toString();
	}
	
	public static String[] splitDbArray(String value) {
		if (value == null)
			return null;

		List<String> ret=new ArrayList<String>();
		StringBuilder sbuf = new StringBuilder();
		boolean inStr=false;
		for (int i = 1; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if(inStr){
				if (ch == '\\' && i<value.length()-1){
					++i;
					ch = value.charAt(i);
					sbuf.append(ch);
				}else if (ch == '"'){
					inStr=false;
				}else{
					sbuf.append(ch);
				}
			}else if (ch == '"'){
				inStr=true;
			}else if(ch==',' || ch=='}'){
				ret.add(sbuf.toString());
				sbuf=new StringBuilder();
			}else{
				sbuf.append(ch);
			}
		}
		return ret.toArray(new String[ret.size()]);
	}
	
	public static String[] splitDbType(String value) {
		if (value == null)
			return null;

		List<String> ret=new ArrayList<String>();
		StringBuilder sbuf = new StringBuilder();
		boolean inStr=false;
		for (int i = 1; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if(inStr){
				if (ch == '\"' && i<value.length()-1&&value.charAt(i+1)== '\"'){
					++i;
					sbuf.append('\"');
				}else if (ch == '"'){
					inStr=false;
				}else{
					sbuf.append(ch);
				}
			}else if (ch == '"'){
				inStr=true;
			}else if(ch==',' || ch==')'){
				ret.add(sbuf.toString());
				sbuf=new StringBuilder();
			}else{
				sbuf.append(ch);
			}
		}
		return ret.toArray(new String[ret.size()]);
	}
	
	public static String unquoteDbArrayText(String value) {
		if (value == null)
			return "null";

		if (value.length() == 0 || value.charAt(0) != '"')
			return value;

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < value.length() - 1; ++i) {
			char ch = value.charAt(i);
			if (ch == '\\') {
//				Assert.error(i < value.length() - 1, "\\ in text");
				sb.append(value.charAt(i + 1));
				++i;
			} else
				sb.append(ch);
		}
		return sb.toString();
	}
	
	public static String unescapeQuote(String value) {
		if (value == null)
			return "null";

		StringBuilder sbuf = new StringBuilder();

		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if (ch == '"')
				sbuf.append("\\\"\\\"");
			else
				sbuf.append(ch);
		}

		return sbuf.toString();
	}

	public static String escapeQuoteWithBackslash(String value) {
		if (value == null)
			return "null";

		StringBuilder sbuf = new StringBuilder();
		sbuf.append('"');

		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if (ch == '"')
				sbuf.append('\\');
			sbuf.append(ch);
		}

		sbuf.append('"');

		return sbuf.toString();
	}

	public static String sepComma(String[] roles) {
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<roles.length; i++){
			if(i!=0)
				sb.append(',');
			sb.append(roles[i]);
		}
		return sb.toString();
	}
	
	/*
	 * Set of characters that is valid. Must be printable, memorable, and "won't
	 * break HTML" (i.e., not ' <', '>', '&', '=', ...). or break shell commands
	 * (i.e., not ' <', '>', '$', '!', ...). I, L and O are good to leave out,
	 * as are numeric zero and one.
	 */
	protected static char[] goodChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
			'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'2', '3', '4', '5', '6', '7', '8', '9' };

	/* Generate a Password object with a random password. */
	public static String generatePassword(int length) {
		java.util.Random r = new java.util.Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(goodChar[r.nextInt(goodChar.length)]);
		}
		return sb.toString();
	}
}
