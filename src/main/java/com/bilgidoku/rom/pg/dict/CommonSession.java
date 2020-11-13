package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.ilk.util.EventSource;
import com.bilgidoku.rom.shared.Cookie;

public interface CommonSession {
	int getInterHostId();

	int getIntraHostId();

	int getRole();

	String getCid();
	
	Cookie getCookie();

	String getUserName();

//	RomUser getUser();

	String getIpAddress();

	String getRoles();

	void visiting(String self);

	boolean isGuest();

	void appendCpu(int cpu);

	int screenWidth();
	
	void changeUser(RomUser ru);

	String getUserEmail();

	long getRoleMask();

	String getSid();
	
	String getEmailDomain();
	String getTopLevelDomain();
	
	String getCountry();

	String getCountryLang();
	
	EventSource<CommonSession>  getLifeCycleSource();
}
