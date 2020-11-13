package com.bilgidoku.rom.web.html;

import java.text.DateFormat;
import java.util.Locale;

public class LangToLocale {
	
	 static public void main(String[] args) {
	        Locale list[] = DateFormat.getAvailableLocales();
	        for (Locale aLocale : list) {
	            System.out.println(aLocale.toString());
	        }
	    }

}
