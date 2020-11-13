package com.bilgidoku.rom.shared.konus.bicim.oge;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.Kelime;
import com.bilgidoku.rom.shared.konus.bicim.Yazi;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;
import com.bilgidoku.rom.shared.konus.bicim.ek.Ek;
import com.bilgidoku.rom.shared.util.Kontrol;

public abstract class Oge implements Yazi {
	List<Kelime> kelimeler=new ArrayList<>();

	public abstract char kodu();
	
	public void ekle(Ek ek) throws KnownError {
		Kontrol.bosDegil(kelimeler);
		Kelime k = kelimeler.get(kelimeler.size() - 1);
		k.ekle(ek);
	}

	public String yaz() {
		StringBuilder sb=new StringBuilder();
		return sb.toString();
	}
	
	@Override
	public void yazi(YaziDurum yaziIsi) throws KnownError {
		for (Kelime kelime : kelimeler) {
			yaziIsi.setKelime(kelime);
			kelime.yazi(yaziIsi);
		}
		
	}


}
