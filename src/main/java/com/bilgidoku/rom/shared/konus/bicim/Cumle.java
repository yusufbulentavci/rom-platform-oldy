package com.bilgidoku.rom.shared.konus.bicim;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;
import com.bilgidoku.rom.shared.konus.bicim.ek.OlumsuzlukEki;
import com.bilgidoku.rom.shared.konus.bicim.ek.SahisEki;
import com.bilgidoku.rom.shared.konus.bicim.ek.ZamanEki;
import com.bilgidoku.rom.shared.konus.bicim.oge.Oge;
import com.bilgidoku.rom.shared.konus.bicim.oge.Yuklem;
import com.bilgidoku.rom.shared.util.Kontrol;

/**
 * 
 *  Çeşitli duygu, düşünce, istek ve dilekleri karşımızdakilere aktarmaya yarayan; 
 *  anlam, yapı ve görev ilgisiyle bütünleşen sözcük öbeklerine “cümle” denir. 
 *  Her cümle mutlaka bir “yargı” bildirir. 
 *  Yargı bildirmek ise sözcüğün ya da söz öbeğinin kip, şahıs veya ek fiil ile çekimlenmiş olması demektir.
 * 
 * @author rompg
 *
 */
public class Cumle implements Yazi{
	List<Oge> sirali=new ArrayList<>();
	
	public void zamani(ZamanEki eki) throws KnownError {
		Yuklem y = yuklem();
		Kontrol.bosDegil(y);
		y.ekle(eki);
	}
	
	public void kisi(SahisEki eki) throws KnownError {
		Yuklem y = yuklem();
		Kontrol.bosDegil(y);
		y.ekle(eki);
	}
	
	public void olumsuz() throws KnownError {
		Yuklem y = yuklem();
		Kontrol.bosDegil(y);
		y.ekle(new OlumsuzlukEki());
	}
	
	public Cumle oge(Oge oge){
		sirali.add(oge);
		return this;
	}

	private Yuklem yuklem() {
		return (Yuklem) ogeByKod(Fabrika.YUKLEM);
	}

	private Oge ogeByKod(char k) {
		for (Oge oge : sirali) {
			if(oge.kodu()==k)
				return oge;
		}
		return null;
	}
	
	
//	public String yaz() {
//		StringBuilder sb=new StringBuilder();
//		for (Oge oge : sirali) {
//			sb.append(oge.yaz());
//			sb.append(" ");
//		}
//		return sb.toString();
//	}

	@Override
	public void yazi(YaziDurum yaziIsi) throws KnownError {
		yaziIsi.setCumle(this);
		for (Oge oge : sirali) {
			yaziIsi.setOge(oge);
			oge.yazi(yaziIsi);
		}
	}
	
	public String yaz() throws KnownError {
		YaziDurum yd=new YaziDurum();
		yazi(yd);
		return yd.yaz();
	}

	public ZamanEki zamani() throws KnownError {
		Yuklem y = yuklem();
		Kontrol.bosDegil(y);
		return y.zamani();
	}

	

	

}
