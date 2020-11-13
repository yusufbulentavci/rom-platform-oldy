package com.bilgidoku.rom.ilk.gorevli;

import java.io.File;

import com.bilgidoku.rom.min.Sistem;


public class RomDir {
	
	static File ROMDIR=new File(Sistem.cur.getRomUserDir(), "rom");
	static File DATADIR=new File(ROMDIR, "data");
	static File DOMAINDIR=new File(ROMDIR, "domain");
	
	
	public static File dataDir() {
		return DATADIR;
	}
	
	public static File domainDir() {
		return DOMAINDIR;
	}
	public static File domainSubDir(String subdir) {
		return new File(domainDir(), subdir);
	}

}
