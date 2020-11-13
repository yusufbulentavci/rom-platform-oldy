package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public interface CGAtt {
	public String getName();

	public TypeHolder getTypeHolder();
	
	public String getPrefix();
	public String getSuffix();
	
	public boolean isParam();
	public boolean isCookie();
	public boolean isLang();
	public boolean isSelf();
	public boolean isContact();
	public boolean isRoles();
	public boolean isIp();
	public boolean isSession();
	public boolean isDomain();
	public boolean isUser();
	public boolean isHost();
	public boolean isFile();
	public boolean isMask();
	public boolean isCont();
	public boolean isSid();
	public boolean isSint();
	public boolean isRid();
	public boolean isContactno();

	
}