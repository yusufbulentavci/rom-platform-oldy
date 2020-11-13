package com.bilgidoku.rom.shared.konus.bicim.ek;

import com.bilgidoku.rom.shared.konus.bicim.Fabrika;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;

public class KucultmeEki extends YapimEki {

	KucultmeEki(int kucukOnce) {
		super(Fabrika.CEKIM_KUCULTME, kucukOnce);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean eylemdemi() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	@Override
	public boolean noktalama() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void yazi(YaziDurum yaziIsi) {
		// TODO Auto-generated method stub
		
	}

}
