package com.bilgidoku.rom.ilk.gorevli;

import java.io.File;

import com.bilgidoku.rom.min.Sistem;


public class RomDir {
	
	static File ROMDIR=new File(Sistem.cur.getRomUserDir(), "rom");
	static File DATADIR=new File(ROMDIR, "data");
	static File DOMAINDIR=new File(ROMDIR, "domain");
	static File PACKSDIR=new File(ROMDIR, "packs");
	
	
	public static File dataDir() {
		return DATADIR;
	}
	
	public static File domainDir() {
		return DOMAINDIR;
	}
	
	public static File packsDir() {
		return PACKSDIR;
	}
	
	public static File domainSubDir(String subdir) {
		return new File(domainDir(), subdir);
	}

	public static File packDir(String pack) {
		return new File(PACKSDIR, pack);
	}
	
	public static File packDirVersion(String pack, String version) {
		return new File(packDir(pack), version);
	}
	
	public static File packDirActive(String pack) {
		return packDirVersion(pack, "active");
	}
	
	public static File pgDataDirBase() {
		return new File(DATADIR, "onemli/pg");
	}
	
	public static File pgDataDir(String name) {
		return new File(pgDataDirBase(), name);
	}
	
	public static File pgDataDirRom() {
		return new File(pgDataDirBase(), "rom");
	}
	

}
