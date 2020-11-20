package com.bilgidoku.rom.os;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.shared.err.KnownError;

public abstract class Uygulama {
	List<Surum> uygulamaSurumleri;
	List<Surum> kurulabilecekUygulamaSurumleri;
	Map<String, Surum> veriler;

	void kesfet() throws KnownError {
		kurulabilecekUygulamaSurumleri = kesfetKurulabilecekUygulamaSurumleri();
		uygulamaSurumleri = kesfetUygulamaSurumleri();
		veriler = kesfetVeriler();
	}

	public void calistir(Surum surum, String app, String... args) {
		if (surum == null)
			surum = defaultSurum();

		File appPath = new File(binDir(surum), app);
	}

	protected Surum defaultSurum() {
		return uygulamaSurumleri.get(0);
	}

	abstract List<Surum> kesfetUygulamaSurumleri();

	abstract List<Surum> kesfetKurulabilecekUygulamaSurumleri() throws KnownError;

	abstract Map<String, Surum> kesfetVeriler() throws KnownError;

	abstract void kur(Surum s) throws KnownError;

	abstract File packDir(Surum s);

	abstract File binDir(Surum s);

}
