package com.bilgidoku.rom.shared.konus.bicim.bilgisi;

public class Unlu {
	private static final String unluler = "aeıioöuü";
	private static final String duz = "aeıi";
	private static final String yuvarlak = "oöuü";
	private static final String genis = "aeoö";
	private static final String dar = "ıiuü";
	private static final String kalin = "aıou";
	private static final String ince = "eiöü";
	private static final String ae = "ae";
	
	private static final String daralmis = "ıiıiuüuü";
	private static final String aeuygunu = "aeaeaeae";

	public static boolean unlumu(char c) {
		return unluler.indexOf(c) >= 0;
	}

	public static boolean duzmu(char c) {
		return duz.indexOf(c) >= 0;
	}

	public static boolean genismi(char c) {
		return genis.indexOf(c) >= 0;
	}

	public static boolean kalinmi(char c) {
		return kalin.indexOf(c) >= 0;
	}

	public static char incesi(char c) {
		return ince.charAt(kalin.indexOf(c));
	}

	public static char kalini(char c) {
		return kalin.charAt(ince.indexOf(c));
	}

	public static boolean aemi(char c) {
		return c=='a' || c=='e';
	}
	

//	public static char daralma(char onceki, char son) {
//
//		if (son == 'a') {
//			if (onceki == 'u') {
//				return 'u';
//			}
//
//			return 'ı';
//		}
//
//		Kontrol.dogrudur(son == 'e');
//		if (onceki == 'ü') {
//			return 'ü';
//		}
//
//		return 'i';
//	}
	
	public static char daralt(char c) {
		return daralmis.charAt(unluler.indexOf(c));
	}

	public static char aeUygunu(char c) {
		return aeuygunu.charAt(unluler.indexOf(c));
	}

	public static boolean varmi(String eklenecek) {
		for(int i=0; i<eklenecek.length(); i++) {
			if(unlumu(eklenecek.charAt(i)))
				return true;
		}
		return false;
	}

}
