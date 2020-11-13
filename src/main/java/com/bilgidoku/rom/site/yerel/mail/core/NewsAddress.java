package com.bilgidoku.rom.site.yerel.mail.core;


public class NewsAddress {

	private String group;
	private String host;

	public NewsAddress(String grp, String hst) {
		this.group=grp;
		this.host=hst;
	}

	public String getNewsgroup() {
		return group;
	}

	public String getHost() {
		return host;
	}

}
