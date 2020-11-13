package com.bilgidoku.rom.shared.gorevli;

import com.bilgidoku.rom.min.Sistem;

public class Uygulama {
	
	private static Uygulama tek;
	
	public static Uygulama prod() {
		if(tek == null) {
			tek=new Uygulama(true);
		}else {
			Sistem.outln("UYGULAMA zaten yaratilmis");
		}
		return tek;
	}
	
	protected static Uygulama test() {
		if(tek == null) {
			tek=new Uygulama(false);
		}else {
			Sistem.outln("UYGULAMA zaten yaratilmis");
		}
		return tek;
	}
	
	public static Uygulama tek() {
		if(tek == null) {
			return test();
		}
		return tek;
	}

	public boolean integrationTest = false;
	private final boolean isProd;
	
	private Uygulama(boolean isProd) {
		this.isProd=isProd;
	}

	public void gorevliYaratildi(GorevliYonetimi tek2) {
		
	}
	
	
	
	public boolean isTest() {
		return ! isProd;
	}

	public boolean isProd() {
		return isProd;
	}

	public String modeStr() {
		return isProd?"PROD":"TEST";
	}

	public boolean testDb() {
		return isTest() && !integrationTest;
	}

	public boolean unitTest() {
		return testDb();
	}

	public void reRoot() {
		// TODO Auto-generated method stub
		
	}

	public void dropRoot() {
		// TODO Auto-generated method stub
		
	}

}
