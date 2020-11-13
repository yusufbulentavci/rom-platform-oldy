package com.bilgidoku.rom.shared.konus.bicim;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;

public interface Yazi {
	
	public void yazi(YaziDurum yaziIsi) throws KnownError;

}
