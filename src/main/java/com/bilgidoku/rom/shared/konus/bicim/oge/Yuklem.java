package com.bilgidoku.rom.shared.konus.bicim.oge;

import com.bilgidoku.rom.shared.konus.bicim.Fabrika;
import com.bilgidoku.rom.shared.konus.bicim.Kelime;
import com.bilgidoku.rom.shared.konus.bicim.ek.ZamanEki;
import com.bilgidoku.rom.shared.konus.bicim.govde.Govde;

public class Yuklem extends Oge{

	public Yuklem(Govde govde) {
		kelimeler.add(new Kelime(govde));
	}
	
	@Override
	public char kodu() {
		return Fabrika.YUKLEM;
	}

	public ZamanEki zamani() {
		return kelimeler.get(0).zamani();
	}


}
