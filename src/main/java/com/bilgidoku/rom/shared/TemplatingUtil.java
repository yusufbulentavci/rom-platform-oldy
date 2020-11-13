package com.bilgidoku.rom.shared;


public class TemplatingUtil {

	public static void assertNotNull(Object b, String s) {
		if(b==null)
			throw new RuntimeException("ERROR: expected true but came false:"+s);
		
	}
	
	public static void assertNotNull(Object b) {
		if(b==null)
			throw new RuntimeException("ERROR: expected not null but null");
		
	}
	
	public static void assertNull(Object b) {
		if(b!=null)
			throw new RuntimeException("ERROR: expected null but not null");
		
	}


	public static void assertThat(boolean b, String s) {
		if(!b)
			throw new RuntimeException("ERROR: expected true but came false:"+s);
	}

}
