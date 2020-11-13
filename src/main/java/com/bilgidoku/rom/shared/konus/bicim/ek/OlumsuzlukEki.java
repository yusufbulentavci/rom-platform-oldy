package com.bilgidoku.rom.shared.konus.bicim.ek;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.Fabrika;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.Unlu;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;

public class OlumsuzlukEki extends CekimEki{

	public OlumsuzlukEki() {
		super(Fabrika.CEKIM_OLUMSUZ, Fabrika.ONCELIK_OLUMSUZ);
	}

	@Override
	public void yazi(YaziDurum yaziIsi) throws KnownError {
		char su = yaziIsi.sonUnlu();
		if(su==0)
			return;
		
		yaziIsi.yaziyaEkle('m');
		if(yaziIsi.zamani(ZamanEki.SIMDIKI)) {
			yaziIsi.yaziyaEkle(Unlu.daralt(su));
		}else {
			yaziIsi.yaziyaEkle(Unlu.aeUygunu(su));
		}
	}

	

}
