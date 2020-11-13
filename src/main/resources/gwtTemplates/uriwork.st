uriwork(tables,services)::=
<<package com.bilgidoku.rom.gwt.araci.server;
//uriwork
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.pg.dict.*;
import com.bilgidoku.rom.type.UriWorkBase;

public final class UriWork implements UriWorkBase{
	private static final Map<String,Net> nets=new HashMap<String,Net>();
	private static final Map<String,UriHierarychy> hiers=new HashMap<String,UriHierarychy>();
	private static final Set<String> services=new HashSet<String>();
	
	static {
		%tables:uriworkitem()%
		%services:uriworkservice()%
	}
	
	public Net getNetByPrefix(String pref){
		return nets.get(pref);
	}
	public UriHierarychy getHierByPrefix(String pref){
		return hiers.get(pref);
	}
	
	public boolean isService(String pref){
		return services.contains(pref);
	}
}

>>