package com.bilgidoku.rom.shared.konus.bicim.bilgisi;

public class Unsuz {
	private static final String unsuzler = "fhsşpçtkğjlmnrvyzpçtkbcdg";
	private static final String sert = "fhsşpçtk";
	private static final String yumusak = "ğjlmnrvyzbcdg";
	private static final String surekli = "fhsşğjlmnrvyz";
	private static final String sureksiz = "pçtkbcdg";

	private static final String pctk = "pçtk";
	private static final String bcdg = "bcdg";
	
	private static final String d2t = "sşpçtk";

	
	
	public static boolean pctkmi(char c) {
		return pctk.indexOf(c) >= 0;
	}

	public static char yumusa(char c) {
		return bcdg.charAt(pctk.indexOf(c));
	}
	
	public static boolean d2tmi(char c) {
		return d2t.indexOf(c) >= 0;
	}

}
