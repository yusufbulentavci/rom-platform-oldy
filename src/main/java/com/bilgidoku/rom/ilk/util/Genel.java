package com.bilgidoku.rom.ilk.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.shared.err.KnownError;


public class Genel {
	
	private final static AtomicInteger count=new AtomicInteger();
	private static String hostName;
	private static String ip;
	public static String uniqueName(String n){
		return n+"-"+count.getAndIncrement();
	}
	
	public static String notNull(Object o){
		if(o==null)
			return "null";
		return o.toString();
	}
	
	public static String parseIp(String ipAddress) {
		String ret = ipAddress;
		if (ret.startsWith("/"))
			ret = ret.substring(1);
		int k = ret.indexOf(':');
		if (k > 0)
			ret = ret.substring(0, k);
		return ret;
	}

	
	
	public static String getHostName(){
		try {
			if(hostName==null)
				hostName=InetAddress.getLocalHost().getHostName();
			return hostName;
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getIp(){
		try {
			if(ip==null)
				ip=InetAddress.getLocalHost().getHostAddress();
			return ip;
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(getIp());
	}
	
	

	public static String sanitizingUri(String uri) throws KnownError {
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			try {
				uri = URLDecoder.decode(uri, "ISO-8859-1");
			} catch (UnsupportedEncodingException e1) {
				throw new KnownError(e).badRequest();
			}
		}

		// // Convert file separators.
		// uri = uri.replace('/', File.separatorChar);

		// Simplistic dumb security check.
		// You will have to do something serious in the production environment.
		if (uri.contains('/' + ".") || uri.contains("." + '/') || uri.startsWith(".") || uri.endsWith(".")) {
			throw new KnownError("Bad path:" + uri).badRequest();
		}
		return uri;
	}

	public static boolean sameDay(long millis1, long millis2) {
		Calendar c1=Calendar.getInstance();
		c1.setTimeInMillis(millis1);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int month1 = c1.get(Calendar.MONTH);
		int year1 = c1.get(Calendar.YEAR);

		c1.setTimeInMillis(millis2);
		int day2 = c1.get(Calendar.DAY_OF_MONTH);
		int month2 = c1.get(Calendar.MONTH);
		int year2 = c1.get(Calendar.YEAR);

		return day1==day2 && month1==month2 && year1==year2;
	}

	public static boolean sameDay(long millis1, int day2, int month2, int year2) {
		Calendar c1=Calendar.getInstance();
		c1.setTimeInMillis(millis1);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int month1 = c1.get(Calendar.MONTH);
		int year1 = c1.get(Calendar.YEAR);

		return day1==day2 && month1==month2 && year1==year2;
	}
}
