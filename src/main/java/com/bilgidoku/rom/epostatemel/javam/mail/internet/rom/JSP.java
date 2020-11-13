package com.bilgidoku.rom.epostatemel.javam.mail.internet.rom;


public class JSP {

	/**
	 * @param s
	 * @return a string in html compatible format tags and apex are all encoded
	 *         and \n replaced with <br>
	 */
	public static String encode(String s) {
		return convertLineFeedToBR(htmlEncode(s));
	}

	public static String htmlEncode(String s) {
		return htmlEncodeApexesAndTags(htmlEncodeAmpersands(JSP.w(s)));
	}

	public static final String htmlEncodeApexesAndTags(String source) {
		return htmlEncodeTag(htmlEncodeApexes(source));
	}

	public static final String htmlEncodeApexes(String source) {
		if (source != null) {
			String result = replaceAllNoRegex(source, new String[] { "\"", "'" }, new String[] { "&quot;", "&#39;" });
			return result;
		} else
			return null;
	}

	public static final String htmlEncodeTag(String source) {
		if (source != null) {
			String result = replaceAllNoRegex(source, new String[] { "<", ">" }, new String[] { "&lt;", "&gt;" });
			return result;
		} else
			return null;
	}

	public static String removeLineFeed(String text) {

		if (text != null)
			return replaceAllNoRegex(text, new String[] { "\n", "\f", "\r" }, new String[] { " ", " ", " " });
		else
			return null;
	}

	/**
	 * use to display in html a text area content
	 */
	public static String convertLineFeedToBR(String text) {
		if (text != null)
			return replaceAllNoRegex(text, new String[] { "\r\n", "\n", "\f", "\r" }, new String[] { "<br>", "<br>",
					"<br>", " " });
		else
			return null;
	}

	// todo:
	private static String replaceAllNoRegex(String text, String[] strings, String[] strings2) {
		for(int i=0; i<strings.length; i++){
			text=text.replace(strings[i], strings2[i]);
		}
		return text;
	}

	public static String w(Object o) {
		if (o == null)
			return w("");
		else
			return w(o + "");
	}

	public static String htmlEncodeAmpersands(String source) {
		// return illegalAmpersands.matcher(s).replaceAll("&amp;");
		if (source != null) {
			String result = replaceAllNoRegex(source, new String[] { "&" }, new String[] { "&amp;" });
			return result;
		} else
			return null;
	}
	//
	//
	//
	// /**
	// * codifica il contenuto di str secondo la codifica per le costanti
	// stringa
	// * di Javascript da usare per passare valori a script javascript es:
	// * onclick="alert('< % =javascriptEncode(variabile) % >')"
	// */
	// public static final String javascriptEncode(String source) {
	// if (source != null) {
	// String s = StringUtilities.replaceAllNoRegex(source, new String[] { "\\",
	// "\"", "'", "’", "\n", "\r" },
	// new String[] { "\\\\", "\\\"", "\\'", "\\'", "\\n", "" });
	// return s;
	// } else
	// return null;
	// }
	//
	// public static String urlEncode(String s) {
	// try {
	// return URLEncoder.encode(JSP.w(s), "UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// throw new PlatformRuntimeException(e);
	// }
	// }
	//
	// public static String w(Identifiable o) {
	// if (o == null)
	// return w("");
	// else
	// return w(o.getName());
	// }
	//
	// public static String w(Object o) {
	// if (o == null)
	// return w("");
	// else
	// return w(o + "");
	// }
	//
	// public static String b(String s) {
	// return makeTag("b", "", w(s));
	// }
	//
	// public static String i(String s) {
	// return makeTag("i", "", w(s));
	// }
	//
	// /**
	// * @param what
	// * string to indent
	// * @param level
	// * is the level
	// * @param pixelForLevel
	// * how many pixel for each level
	// * @return the string wrapped in a table with the
	// */
	// public static String indent(String what, int level, int pixelForLevel) {
	// return
	// "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"><tr><td width=\""
	// + level
	// * pixelForLevel + "\">&nbsp;</td><td>" + what + "</td></tr></table>";
	// }
	//
	// public static void indent(JspIncluder what, int level, int pixelForLevel,
	// PageContext pageContext)
	// throws IOException, ServletException {
	// String indent = indent("||||", level, pixelForLevel);
	// pageContext.getOut().write(indent.substring(0, indent.indexOf("||||")));
	// what.toHtml(pageContext);
	// pageContext.getOut().write(indent.substring(indent.indexOf("||||") + 4));
	//
	// }
	//
	// public static String currency(Number amount) {
	// return NumberUtilities.currency(amount);
	// }
	//
	// public static String timeStamp(Date when) {
	// return DateUtilities.dateAndHourToString(when);
	// }
	//
	// /**
	// * @deprecated use HtmlSanitizer.getText()
	// * @param s
	// * @return
	// */
	// public static String cleanHTML(String s) {
	// if (s != null) {
	// StringBuffer result = new StringBuffer();
	// int pos = 0;
	// String sLower = s.toLowerCase();
	// while (pos < s.length()) {
	//
	// char c = s.charAt(pos);
	// String nextTen = sLower.substring(pos, Math.min(s.length(), pos + 10));
	// if (nextTen.startsWith("<script"))
	// pos = moveToMarkerEnd(pos, "</script>", sLower);
	// else if (nextTen.startsWith("<!--"))
	// pos = moveToMarkerEnd(pos, "-->", sLower);
	// else if (nextTen.startsWith("<"))
	// pos = moveToMarkerEnd(pos, ">", sLower);
	// else {
	// result.append(c);
	// pos++;
	// }
	// }
	//
	// return result.toString();
	// } else
	// return "";
	// }
	//
	// private static int moveToMarkerEnd(int pos, String marker, String s) {
	// int i = s.indexOf(marker, pos);
	// if (i > -1)
	// pos = i + marker.length();
	// else
	// pos = s.length();
	// return pos;
	// }
	//
	// public static String wHelp(String text) {
	// return "<span class=\"descrEl\">" + text + "</span>";
	// }
	//
	// public static boolean ex(Serializable contents) {
	// return contents != null && (contents + "").trim().length() > 0;
	// }
	//
	// public static boolean ex(int value) {
	// return value != 0;
	// }
	//
	// /**
	// * exist?
	// *
	// * @param contents
	// * @return true when contents not null and content.lenght>0
	// */
	// public static boolean ex(String contents) {
	// return contents != null && contents.trim().length() > 0;
	// }
	//
	// public static boolean ex(String... strings) {
	// boolean ex = true;
	// for (String t : strings) {
	// if (!JSP.ex(t)) {
	// ex = false;
	// break;
	// }
	// }
	// return ex;
	// }
	//
	// public static boolean ex(Date... dates) {
	// boolean ex = true;
	// for (Date d : dates) {
	// if (d != null) {
	// ex = false;
	// break;
	// }
	// }
	// return ex;
	// }
	//
	// public static boolean ex(Identifiable... strings) {
	// boolean ex = true;
	// for (Identifiable t : strings) {
	// if (t == null) {
	// ex = false;
	// break;
	// }
	// }
	// return ex;
	// }
	//
	// public static boolean exOr(String... strings) {
	// boolean ex = false;
	// for (String t : strings) {
	// if (JSP.ex(t)) {
	// ex = true;
	// break;
	// }
	// }
	// return ex;
	// }
	//
	// public static boolean ex(Collection contents) {
	// return contents != null && contents.size() > 0;
	// }
	//
	// public static boolean ex(Map contents) {
	// return contents != null && !contents.isEmpty();
	// }
	//
	// public static boolean ex(ClientEntry entry) {
	// return entry != null && JSP.ex(entry.stringValueNullIfEmpty());
	// }
	//
	// public static boolean ex(Page page) {
	// return page != null && ex(page.getThisPageElements());
	// }
	//
	// public static boolean ex(PersistentFile contents) {
	// return contents != null;
	// }
	//
	// public static boolean exForValue(String content, String expectedValue) {
	// return JSP.ex(content) && JSP.ex(expectedValue) &&
	// content.equals(expectedValue);
	// }
	// private static Pattern illegalAmpersands =
	// Pattern.compile("(?i)&(?!(nbsp|lt|gt|quote|(?:#\\d{2,6})|amp|);)");
	//
	// public static String w(String... s) {
	// String ret = "";
	// for (String x : s) {
	// if (JSP.ex(x))
	// ret += w(x);
	// else
	// break;
	//
	// }
	// return ret;
	// }
	//
	// public static String w(String s) {
	// if (s == null)
	// return "";
	// else
	// return s;
	// }
	//
	// public static String wSmile(String s, PageContext pageContext) {
	// return SmileyUtilities.getTextWithSmileys(JSP.w(s), pageContext);
	// }
	//
	// public static String w(Period p) {
	// if (p == null)
	// return "";
	// else
	// return DateUtilities.dateAndHourToString(p.getStartDate()) + "-"
	// + DateUtilities.dateAndHourToString(p.getEndDate());
	// }
	//
	// public static String w(Date d) {
	// if (d != null)
	// return DateUtilities.dateToString(d);
	// else
	// return "";
	// }
	//
	// public static String perc(double d) {
	// if (Double.isNaN(d))
	// d = 0;
	// NumberFormat nf = NumberFormat.getNumberInstance();
	// nf.setMaximumFractionDigits(2);
	// return nf.format(d);
	// }
	//
	// public static String perc(double d, int fractionDigits) {
	// if (Double.isNaN(d))
	// d = 0;
	// NumberFormat nf = NumberFormat.getNumberInstance();
	// nf.setMaximumFractionDigits(fractionDigits);
	// return nf.format(d);
	// }
	//
	// public static String w(Double s) {
	// if (s != null) {
	// return NumberUtilities.decimalNoGrouping(s);
	// } else
	// return "";
	// }
	//
	// public static String w(Float s) {
	// if (s != null) {
	// return NumberUtilities.decimalNoGrouping(s);
	// } else
	// return "";
	// }
	//
	// public static String w(Integer s) {
	// if (s != null) {
	// return s + "";
	// } else
	// return "";
	// }
	//
	// public static String w(Serializable s) {
	//
	// if (s != null) {
	// return w(s.toString());
	// } else
	// return "";
	// }
	//
	// public static String w(PersistentFile pf) {
	// if (pf != null)
	// return pf.getFileLocation();
	// else
	// return "";
	// }
	//
	// public static String wId(Identifiable obj) {
	// if (PersistenceHome.NEW_EMPTY_ID.equals(obj.getId()))
	// return "NEW";
	// else
	// return w(obj.getId());
	// }
	//
	// public static void feedbackError(ClientEntry statefulCE, PageContext
	// pageContext) {
	// feedbackError(statefulCE, true, pageContext);
	// }
	//
	// public static void feedbackError(ClientEntry statefulCE, boolean
	// translateError, PageContext pageContext) {
	// FeedbackError fe = new FeedbackError();
	// if (statefulCE != null) {
	// fe.suggestedValue = statefulCE.suggestedValue;
	// fe.errorCode = statefulCE.errorCode;
	// fe.id = statefulCE.name;
	// }
	// fe.translateError = translateError;
	// fe.toHtml(pageContext);
	// }
	//
	// /**
	// * Metodo per mettere qualcosa all'interno di un tag html
	// *
	// * @param tagName
	// * nome del tag (es. "font")
	// * @param tagParam
	// * parametri da inserire nel tag (es. color="#ff0000" size="3" )
	// * @param text
	// * testo
	// */
	// public static String makeTag(String tagName, String tagParam, String
	// text) {
	// StringBuffer sb = new StringBuffer(512);
	//
	// sb.append(tagName == null ? "" : "<" + tagName).append(' ');
	// sb.append(tagParam == null ? "" : tagParam);
	// sb.append(tagName == null ? "" : '>');
	// sb.append(text == null ? "&nbsp;" : text);
	// sb.append(tagName == null ? "" : "</" + tagName + ">");
	// return sb.toString();
	// }
	//
	// public static String limWr(String content, int maxLength) {
	// return limWr(content, maxLength, true);
	// }
	//
	// public static String limWr(String content, int maxLength, boolean
	// fromLeft) {
	// if (JSP.ex(content)) {
	// if (content.length() > maxLength) {
	// if (fromLeft)
	// content = content.substring(0, maxLength) + "...";
	// else
	// content = "..." + content.substring(content.length() - maxLength,
	// content.length());
	// }
	// return content;
	// } else
	// return "";
	// }
	//
	//

}
