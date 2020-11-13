package com.bilgidoku.rom.web.analytics.useragent;

/**
 * Enum constants classifying the different types of applications which are common in referrer strings
 */
public enum ApplicationType {

	/**
	 * Webmail service like Windows Live Hotmail and Gmail.
	 */
	WEBMAIL("Webmail client"),
	UNKNOWN("unknown");
	
	private String name;
	
	private ApplicationType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
