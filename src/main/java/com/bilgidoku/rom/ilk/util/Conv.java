package com.bilgidoku.rom.ilk.util;

public class Conv {
	
	public final static long secMsec(long min){
		return min*1000;
	}
	
	public final static long minMsec(long min){
		return min*1000*60;
	}
	
	public final static long hourMsec(long hour){
		return hour*1000*60*60;
	}

	public static int dayToMin(int day) {
		return day*24*60;
	}

}
