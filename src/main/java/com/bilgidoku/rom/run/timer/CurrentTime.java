package com.bilgidoku.rom.run.timer;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CurrentTime {
	public int year;
	public int month;
	public int day;
	public long millis;

	public CurrentTime(){
		Calendar c = GregorianCalendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		millis=c.getTimeInMillis();
	}
	
	public CurrentTime(long time) {
		millis=time;
		Calendar c = GregorianCalendar.getInstance();
		c.setTimeInMillis(time);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}

	public boolean anotherDay(CurrentTime ct){
		return (day!=ct.day || month!=ct.month || year!=ct.year);
	}

}
