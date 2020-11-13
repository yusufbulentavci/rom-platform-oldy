package com.bilgidoku.rom.shared.konus.bicim.ek;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.Fabrika;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.Unlu;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.YaziDurum;
import com.bilgidoku.rom.shared.util.Kontrol;

public class SahisEki extends CekimEki {
	public static final int BEN = 2;
	public static final int SEN = 4;
	public static final int O = 0;
	public static final int BIZ = 7;
	public static final int SIZ = 5;
	public static final int ONLAR = 1;

	public static int birlestir(boolean tekil, boolean benDahilmi, boolean senDahilmi) {
		int ret = 0;
		if (!tekil)
			ret = 1;
		if (benDahilmi)
			ret += 2;
		if (senDahilmi)
			ret += 4;
		return ret;
	}

	private int kim;

	public SahisEki(int kim) {
		super(Fabrika.CEKIM_SAHIS, Fabrika.ONCELIK_SAHIS);
		this.kim = kim;
	}

	@Override
	public void yazi(YaziDurum yaziIsi) throws KnownError {
		switch (kim) {
		case BEN:
			ekle(yaziIsi, "m");
			break;
		case SEN:
			ekle(yaziIsi, "sun");
			break;
		case O:
			break;
		case BIZ:
			ekle(yaziIsi, "uz");
			break;
		case SIZ:
			ekle(yaziIsi, "sunuz");
			break;
		case ONLAR:
			ekle(yaziIsi, "lar");
			break;
		}
	}

//	private void ben(YaziDurum yaziIsi) throws KnownError {
//		char sh = yaziIsi.sonHarf();
//		if (Unlu.unlumu(sh)) {
//			yaziIsi.yaziyaEkle('m');
//			return;
//		}
//
//		char su = yaziIsi.sonUnlu();
//		Kontrol.sayiFarkli(0, su);
//
//		yaziIsi.yaziyaEkle(Unlu.daralt(su));
//
//		yaziIsi.yaziyaEkle('m');
//	}


}
