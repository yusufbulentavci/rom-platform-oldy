package com.bilgidoku.rom.web.analytics;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine {
	String name;
	String domainName;
	Pattern pattern;
	String url;

	public SearchEngine(String name, String domainName, String url,
			String pattern) {
		this.name = name;
		this.domainName = domainName;
		this.url = url;
		this.pattern = Pattern.compile(pattern);
	}

	public String[] extractQuery(String q) {
		if(q==null || q.length()==0)
			return null;
		Matcher m = pattern.matcher(q.toLowerCase());

		if (!m.find()) {
			return null;
		}
		int start = m.start();
		int end = m.end();

		String query = q.substring(start + 3, end);
		if (query.endsWith("&")) {
			query = query.substring(0, query.length() - 1);
		}
		try {
			query = URLDecoder.decode(query, "UTF-8").trim();
		} catch (Exception e) {
		}
		if(query.length()==0){
			return null;
		}
		return query.split(" ");
	}

}
