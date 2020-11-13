package com.bilgidoku.rom.web.analytics.useragent;

/**
 * Enum constants classifying the different types of browsers which are common in user-agent strings
 */
public enum BrowserType {

	/**
	 * Standard web-browser
	 */
	WEB_BROWSER("std", 1024),
	/**
	 * Special web-browser for mobile devices
	 */
	MOBILE_BROWSER("mobile", 400),
	/**
	 * Text only browser like the good old Lynx
	 */
	TEXT_BROWSER("text", 800),
	/**
	 * Email client like Thunderbird
	 */
	EMAIL_CLIENT("email", 800),
	/**
	 * Search robot, spider, crawler,...
	 */
	ROBOT("robot", 1024),
	/**
	 * Downloading tools
	 */
	TOOL("download", 1024),
	UNKNOWN("unknown", 1024);
	
	private final String name;
	private final int width;
	
	private BrowserType(String name, int width) {
		this.name = name;
		this.width=width;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

}
