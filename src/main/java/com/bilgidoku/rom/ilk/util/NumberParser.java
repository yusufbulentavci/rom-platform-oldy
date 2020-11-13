package com.bilgidoku.rom.ilk.util;

public class NumberParser {
	
	/**
	 * 
	 * 
	 * s->seconds
	 * m->minute
	 * h->hour
	 * d->day
	 * o->month
	 * y->year
	 * @param s
	 * @param checkEmpty
	 * @return
	 */
	public static long longOfTimeInSecs(String s, boolean checkEmpty){
		s=s.trim();
		if(s.length()<1){
			if(checkEmpty)
				throw new NullPointerException("Time to parse is empty");
			return 0;
		}
		Character last=s.charAt(s.length()-1);
		String toParse=s;
		long katsayi=1;
		if(!Character.isDigit(last)){
			toParse=s.substring(0, s.length()-1);
			switch(last.charValue()){
			case 's':
				break;
			case 'm':
				katsayi=60;
				break;
			case 'h':
				katsayi=60*60;
				break;
			case 'd':
				katsayi=60*60*24;
				break;
			case 'o':
				katsayi=60*60*24*30;
				break;
			case 'y':
				katsayi=60*60*24*30*365;
				break;
			}
		}
		return Long.parseLong(toParse)*katsayi;
	}

}
