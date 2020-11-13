package com.bilgidoku.rom.shared.konus.bicim.govde;

import java.util.List;

import com.bilgidoku.rom.shared.konus.bicim.Bolum;
import com.bilgidoku.rom.shared.konus.bicim.Fabrika;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.Hecele;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;

public abstract class Govde extends Bolum{

	private String yazi;

	public Govde(char kodu, String yazi) {
		super(kodu, Fabrika.ONCELIK_GOVDE);
		this.yazi=yazi;
	}
	
	
	@Override
	public void yazi(YaziDurum yaziIsi) {
		List<String> heceli = Hecele.yap(yazi);
		yaziIsi.sifirla(heceli);
	}
}
