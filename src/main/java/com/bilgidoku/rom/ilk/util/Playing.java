package com.bilgidoku.rom.ilk.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Playing {
	
	
	private static long millis=0;

	public final static long millis(){
		if(millis==0)
			return System.currentTimeMillis();
		return millis;
	}
	
	public static void adjustTime(int year, int month, int day, int hour, int min){
		GregorianCalendar gc=new GregorianCalendar();
		if(year!=0)
			gc.add(Calendar.YEAR, year);
		if(month!=0)
			gc.add(Calendar.MONTH, month);
		if(day!=0)
			gc.add(Calendar.DAY_OF_MONTH, day);
		if(hour!=0)
			gc.add(Calendar.HOUR, hour);
		if(min!=0)
			gc.add(Calendar.MINUTE, min);
		millis=gc.getTimeInMillis();
	}

	public final static GregorianCalendar calendar() {
		if(millis==0)
			return new GregorianCalendar();
		GregorianCalendar gc=new GregorianCalendar();
		gc.setTimeInMillis(millis);
		return gc;
	}

}
