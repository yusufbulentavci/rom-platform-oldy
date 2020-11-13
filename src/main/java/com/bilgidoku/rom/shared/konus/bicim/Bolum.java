package com.bilgidoku.rom.shared.konus.bicim;

public abstract class Bolum implements Yazi{

	private char kodu;
	private final int kucukOnce;
	
	protected Bolum(char kodu, int kucukOnce){
		this.kodu = kodu;
		this.kucukOnce=kucukOnce;
	}
	
	public final char kodu() {
		return kodu;
	}
	public boolean ekmi() {
		return false;
	}
	public boolean noktalama() {
		return false;
	}
	public boolean govdemi() {
		return false;
	}

	public int getKucukOnce() {
		return kucukOnce;
	}

	protected boolean eylemmi() {
		return false;
	}

	public boolean zamanmi() {
		return kodu==Fabrika.CEKIM_ZAMAN;
	}


}
