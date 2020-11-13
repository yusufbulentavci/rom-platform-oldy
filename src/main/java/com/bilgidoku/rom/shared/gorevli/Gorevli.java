package com.bilgidoku.rom.shared.gorevli;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.json.JSONObject;

public abstract class Gorevli {

	private final int no;
	protected final String kod;
	boolean kuruldu = false;

	public Gorevli(String kodu, int no) {
		this.kod = kodu;
		this.no = no;
	}

	public String getKod() {
		return kod;
	};

	protected boolean giris() {
		try {
			GorevliYonetimi.tek().yeni(this);
			kur();
		} catch (Exception e) {
			Sistem.outln("Kur basarisiz:");
			Sistem.printStackTrace(e);
			GorevliYonetimi.tek().terminate("Kur basarisiz", null);
			return false;
		}
		kuruldu = true;
		return true;
	}

	protected void bitirme() {
		if (!kuruldu)
			return;
		bitir(true);
		kuruldu = false;
	}

	public void yenidenBaslat() throws KnownError {
		if (kuruldu) {
			bitirme();
		}
		giris();
	}

	protected void kur() throws KnownError {
	};

	protected void dur() {
	};

	protected void devam() {
	};

	protected void bitir(boolean dostca) {
	}

	public void selfDescribe(JSONObject jo) {

	}
	
	protected KnownError confError(Exception e) {
		return new KnownError(this.kod + " configuration error", e);
	}

	protected KnownError confError(String string, Exception e) {
		return new KnownError(this.kod + " configuration error:" + string, e);
	}

	protected void destur() {

	}
}
