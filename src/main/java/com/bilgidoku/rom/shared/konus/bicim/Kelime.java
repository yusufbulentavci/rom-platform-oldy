package com.bilgidoku.rom.shared.konus.bicim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;
import com.bilgidoku.rom.shared.konus.bicim.ek.Ek;
import com.bilgidoku.rom.shared.konus.bicim.ek.ZamanEki;
import com.bilgidoku.rom.shared.konus.bicim.govde.Govde;
import com.bilgidoku.rom.shared.util.Kontrol;

public class Kelime implements Yazi {
	List<Bolum> bolumler = new ArrayList<>();

	public Kelime(Govde govde) {
		bolumler.add(govde);
	}

	public void ekle(Ek ek) {
		bolumler.add(ek);
	}

	@Override
	public void yazi(YaziDurum yaziIsi) throws KnownError {

		bolumler.sort(new Comparator<Bolum>() {
			@Override
			public int compare(Bolum o1, Bolum o2) {
				return o1.getKucukOnce()-o2.getKucukOnce();
			}
		});

		for (Bolum b : bolumler) {
			b.yazi(yaziIsi);
		}

	}

	public boolean eylemmi() throws KnownError {
		Kontrol.bosCollectionDegil(bolumler);
		return bolumler.get(0).eylemmi();
	}

	public ZamanEki zamani() {
		for (Bolum bolum : bolumler) {
			if(bolum.zamanmi()) {
				return (ZamanEki) bolum;
			}
		}
		return null;
	}

//	public String yaz() {
//		StringBuilder sb=new StringBuilder();
//		for (Bolum b : bolumler) {
//			sb.append(b.yaz());
//		}
//		return sb.toString();
//	}

}
