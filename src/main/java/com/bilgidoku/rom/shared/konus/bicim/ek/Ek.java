package com.bilgidoku.rom.shared.konus.bicim.ek;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.Bolum;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.Unlu;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.Unsuz;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;
import com.bilgidoku.rom.shared.util.Kontrol;

public abstract class Ek extends Bolum{

	Ek(char kodu, int kucukOnce) {
		super(kodu, kucukOnce);
	}

	public boolean ekmi() {
		return true;
	}
	
	protected void ekle(YaziDurum yaziIsi, String eklenecek) throws KnownError {
		char sh = yaziIsi.sonHarf();

		char eih = eklenecek.charAt(0);
		
		if(eklenecek.equals("yor")) {
			unsuzYumusamasi(yaziIsi, sh);
			unluDaralmasi(yaziIsi, sh);
		}

		if (Unlu.unlumu(eih)) {
//			if(!Unlu.unlumu(sh)) {
//				return;
//			}
			ekiEkle(yaziIsi, eklenecek);

		} else {
			if(Unlu.unlumu(sh)) {
				ekiEkle(yaziIsi, eklenecek);
				return;
			}
			if(!Unlu.varmi(eklenecek)) {
				char su=yaziIsi.sonUnlu();
				Kontrol.sayiFarkli(0, su);
				
				yaziIsi.yaziyaEkle(Unlu.daralt(su));
			}
			ekiEkle(yaziIsi, eklenecek);
			
		}
		
		

	}

	private void ekiEkle(YaziDurum yaziIsi, String eklenecek) {
		if(eklenecek.charAt(0) == 'd' && Unsuz.d2tmi(yaziIsi.sonHarf())) {
			yaziIsi.yaziyaEkle('t');
			if(eklenecek.length()>1)
				yaziIsi.yaziyaEkle(eklenecek.substring(1));
			return;
		}
		yaziIsi.yaziyaEkle(eklenecek);
	}

	protected void unsuzYumusamasi(YaziDurum yaziIsi, char sh) throws KnownError {
		if(!Unsuz.pctkmi(sh))
			return;
		if(yaziIsi.yaziTekHecemi()) {
			char su = yaziIsi.sonUnlu();
			if(Unlu.kalinmi(su))
				return;
		}
		
		yaziIsi.sonHarfiDegistir(Unsuz.yumusa(sh));
		
	}


	protected void unluDaralmasi(YaziDurum eki, char sh) throws KnownError {
		char su = eki.sonUnlu();
		char ou = eki.sondanBirOncekiUnlu();
		unsuzYumusamasi(eki, sh);
		if(Unlu.unlumu(sh)) {
			if(eki.eylemmi()) {
				if(sh>0) {
					if(Unlu.aemi(sh)) {
						char yeniHarf=Unlu.daralt(ou>0?ou:sh);
						eki.sonHarfiDegistir(yeniHarf);
					}
				}
			}
		}else {
			eki.yaziyaEkle(Unlu.daralt(su));
		}
		
	}


}
