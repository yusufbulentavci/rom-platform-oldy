package com.bilgidoku.rom.shared.konus.bicim.noktalama;

import com.bilgidoku.rom.shared.konus.bicim.Bolum;
import com.bilgidoku.rom.shared.konus.bicim.Fabrika;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;

public class Noktalama extends Bolum{

	private String yazi;

	protected Noktalama(String yazi) {
		super(Fabrika.NOKTALAMA, Fabrika.ONCELIK_NOKTALAMA);
		this.yazi=yazi;
	}

	@Override
	public boolean ekmi() {
		return false;
	}

	@Override
	public boolean noktalama() {
		return true;
	}
	
	public boolean cumleyiBitirirmi() {
		return false;
	}
	
	public boolean yazarkenAyir() {
		return false;
	}

	@Override
	public void yazi(YaziDurum yaziIsi) {
		// TODO Auto-generated method stub
		
	}

}


