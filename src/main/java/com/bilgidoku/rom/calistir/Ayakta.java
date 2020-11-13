package com.bilgidoku.rom.calistir;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Uygulama;

public abstract class Ayakta {
	
	public Ayakta() {
		Uygulama.prod();
	}
	
	public void kos() {
		try {
			basla();
			bekle();
		} catch (KnownError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	abstract public void basla() throws KnownError;

	abstract void bekle();
	
	
	

}
