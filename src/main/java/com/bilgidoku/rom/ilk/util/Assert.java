package com.bilgidoku.rom.ilk.util;

import com.bilgidoku.rom.shared.err.KnownError;

public class Assert {
//	public static void notice(boolean b, String s){
//		if(!b)
//			syso("NOTICE:expected true but came false:"+s);
//	}
//	public static void noticeNull(Object b, String s){
//		if(b==null)
//			syso("NOTICE:object is null:"+s);
//	}
	
	public static void error(boolean b, String s){
		if(!b)
			throw new RuntimeException("ERROR: expected true but came false:"+s);
	}
	public static void errorNull(Object b, String s){
		if(b==null)
			throw new RuntimeException("ERROR: object is null:"+s);
	}
	
	public static void notNull(Object b) throws KnownError{
		if(b==null)
			throw new KnownError("ERROR: object is null");
	}
	public static void beTrue(boolean b) throws KnownError {
		if(!b)
			throw new KnownError("ERROR: not true");
	}
	public static void beFalse(boolean b) throws KnownError {
		if(b)
			throw new KnownError("ERROR: have to be false");
	}
}
