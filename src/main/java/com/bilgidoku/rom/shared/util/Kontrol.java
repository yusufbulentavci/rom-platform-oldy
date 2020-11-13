package com.bilgidoku.rom.shared.util;

import java.util.Collection;

import com.bilgidoku.rom.shared.err.KnownError;

public class Kontrol {
//	public static void notice(boolean b, String s){
//		if(!b)
//			syso("NOTICE:expected true but came false:"+s);
//	}
//	public static void noticeNull(Object b, String s){
//		if(b==null)
//			syso("NOTICE:object is null:"+s);
//	}
	
	public static void dogrudur(boolean b){
		if(!b)
			throw new RuntimeException("ERROR: Dogru olmali idi ama olmadi");
	}
	
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

	public static void bosCollectionDegil(Collection kelimeler) throws KnownError {
		if(kelimeler == null || kelimeler.size()==0)
			throw new KnownError("Bos Degil dendi ama bos");
	}

	public static void bosDegil(Object b) throws KnownError {
		if(b==null)
			throw new KnownError("ERROR: object is null");
	}

	public static void sayiFarkli(int i, int su) throws KnownError {
		if(i==su)
			throw new KnownError("ERROR: Farkli olmaliydi "+i+"=="+su);
	}
}
