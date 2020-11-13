package com.bilgidoku.rom.type;

import com.bilgidoku.rom.pg.dict.Net;
import com.bilgidoku.rom.pg.dict.UriHierarychy;

public interface UriWorkBase {

	public Net getNetByPrefix(String pref);

	public UriHierarychy getHierByPrefix(String pref);

	public boolean isService(String pref);

}
