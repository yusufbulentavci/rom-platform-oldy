package com.bilgidoku.rom.web.utils;

public class Knife {

	
	/*
	 * example System.out.println(replaceExtension("/all/all/a.k/ali.jpg", "png"));
	 */
	public static String replaceExtension(String from, String extension){
		
		int lasti=from.indexOf('.');
		return from.substring(0, lasti)+"."+extension;
		
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(replaceExtension("/all/all/a.k/ali.jpg", "png"));
	}
	
}
