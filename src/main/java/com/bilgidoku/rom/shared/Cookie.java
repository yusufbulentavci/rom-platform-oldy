package com.bilgidoku.rom.shared;

public interface Cookie {
	public void cookieSent();
	public boolean cookieDirty();

	public String getSid();
	public String getCookieUser();
	public String getRoles();
	public String getCookieDomainName();
	public String getCname();
	public String getCid();
	public String getCookieLang();
	public String getCookieHostName();
	public String getCookiePresence();
	public boolean getCookieAuth();
}
