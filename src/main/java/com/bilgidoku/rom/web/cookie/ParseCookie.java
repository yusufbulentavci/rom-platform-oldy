package com.bilgidoku.rom.web.cookie;

public class ParseCookie {
	private String sid=null;
	private String lang=null;
	private String userName;
	private String domainName;

	public ParseCookie(String domainName) {
		this.domainName=domainName;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
	public void setCookieLang(String value) {
		this.lang=value;
	}
	
	public String getCookieLang(){
		return lang;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
