package com.bilgidoku.rom.shared.konus.bicim.ek;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.Fabrika;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;

public class ZamanEki extends CekimEki {
	
	public static final char SIMDIKI = 's';
	public static final char GECMIS = 'g';
	private char zamani;

	public ZamanEki(char zamani) {
		super(Fabrika.CEKIM_ZAMAN, Fabrika.ONCELIK_ZAMAN);
		this.zamani=zamani;
	}

	@Override
	public void yazi(YaziDurum yaziIsi) throws KnownError {
		switch(zamani) {
		case SIMDIKI:
			ekle(yaziIsi, "yor");
			break;
		case GECMIS:
			ekle(yaziIsi, "di");
			break;
		}
	}
	
	public boolean karsilastir(char c) {
		return zamani==c;
	}

}
