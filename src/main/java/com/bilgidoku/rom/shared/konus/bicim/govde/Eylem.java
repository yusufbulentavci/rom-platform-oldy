package com.bilgidoku.rom.shared.konus.bicim.govde;

import com.bilgidoku.rom.shared.konus.bicim.Fabrika;

public class Eylem extends Govde{
	

	public Eylem(String yazi) {
		super(Fabrika.EYLEM, yazi);
	}

	protected boolean eylemmi() {
		return true;
	}

}
