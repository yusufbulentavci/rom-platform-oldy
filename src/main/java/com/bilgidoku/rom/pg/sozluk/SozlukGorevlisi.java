package com.bilgidoku.rom.pg.sozluk;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;

public class SozlukGorevlisi extends GorevliDir {
	public static final int NO=18;


	public static SozlukGorevlisi tek() {
		if (tek == null) {
			synchronized (SozlukGorevlisi.class) {
				if (tek == null) {
					tek = new SozlukGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static SozlukGorevlisi tek;

	private SozlukGorevlisi() {
		super("Sozluk", NO);
	}
	
	
	public Integer kelimeNo(String kelime) {
		
		return null;
	}
	
	public String noKelime(int no) {
		
		return null;
	}

	
	
}
