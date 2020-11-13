package com.bilgidoku.rom.min;

public class Sistem {
	public static IRun run=new DefaultRun();
	public static ISistem cur=new DefaultSistem();
	public static ILogger log=new DefaultLogger();
	public static MillisProvider millis=new DefaultMillis();

	public static void outln(Object s) {
		log.outln(s);
	}

	public static void errln(Object s) {
		log.errln(s);
	}

	public static void printStackTrace(Throwable x, Object extra) {
		log.printStackTrace(x, extra);
	}

	public static void printStackTrace(Throwable x) {
		log.printStackTrace(x);
	}

	public static long millis() {
		return millis.millis();
	}
	
}
