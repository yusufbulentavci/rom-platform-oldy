package com.bilgidoku.rom.gwt.client.util.chat.im.repo;

import com.bilgidoku.rom.gwt.client.util.chat.ContactInfo;

public interface ContactBla extends ContactInfo{

	CallWrap getCall();

	void setPresence(int code, String presence);

	void setPresence(Object[] parseCodedPresence);

	String getPresenceImg();

	String getContactName();

	String getImgUrl();

	void setImgUrl(String dataUrl);
	
	void setBusy();
	
	boolean isBusy(long t);

	void setDlgPresence(String text);

	String getDlgPresence();
	
}
