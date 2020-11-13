package com.bilgidoku.rom.gwt.server.middle;

import java.lang.reflect.InvocationTargetException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.dict.CallInteraction;

public abstract class ServiceDaoBase extends DaoBase{
//	private static AtomicLong dbFileIndex=new AtomicLong(0);
//	private static final String dbFilePref="-"+RunTime.getSyscount().getStartCount()+"-";
//
//	String getDbfsOwner(CallInteraction request){
//		return request.getUri()+dbFilePref+dbFileIndex;
//	}
	
	protected KnownError errIllegalAccess(IllegalAccessException e) {
		return new KnownError(e);
	}
	
	protected KnownError errIllegalArgument(IllegalArgumentException e) {
		return new KnownError(e);
	}
	
	protected KnownError errInvocationTarget(InvocationTargetException e) {
		return new KnownError(e);
	}
	
	public String getDbfsOwner(CallInteraction request){
		return request.getUri();
	}
}
