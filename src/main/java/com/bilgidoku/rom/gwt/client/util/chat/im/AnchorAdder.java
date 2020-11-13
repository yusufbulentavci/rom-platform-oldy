package com.bilgidoku.rom.gwt.client.util.chat.im;

public class AnchorAdder {
//	private static final Pattern urlPattern = Pattern.compile(
//			// "https");
//			"(https?|ftp|file):(//[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])");
//	private static final String REPLACEMENT = "<a href='$1:$2' target='_blank'></a>";

	public static String addAnchor(String txt) {

		int ind = txt.indexOf("http");
		if (ind < 0)
			return txt;

		StringBuilder sb=new StringBuilder();
		if(ind>0){
			sb.append(txt.substring(0, ind));
		}
		do {
			int i = ind;
			for (; i < txt.length(); i++) {
				char c = txt.charAt(i);
				if (characterIsWhitespace(c)) {
					break;
				}
			}
			
			String uri=txt.substring(ind, i);
			
			sb.append("<a href='");
			sb.append(uri);
			sb.append("' target='_blank'></a>");
			
			if(i>=txt.length())
				break;

			ind = txt.indexOf("http://", i);

			if(ind<0 && i<txt.length()-1){
				sb.append(txt.substring(i));
				break;
			}else{
				sb.append(txt.substring(i, ind));
			}
		} while (ind >= 0);
		
		return sb.toString();

//		Matcher matcher = urlPattern.matcher(txt);
//		if (!matcher.find())
//			return txt;
//		// Matcher matcher = urlPattern.matcher(txt);
//		// if(!matcher.matches())
//		// return txt;
//		//
//		return matcher.replaceAll(REPLACEMENT);
	}

	private static boolean characterIsWhitespace(char c) {
		return (c=='\t' || c=='\n' || c==' ' || c=='\r');
		
	}

}
