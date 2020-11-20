package com.bilgidoku.rom.os;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.ilk.gorevli.RomDir;
import com.bilgidoku.rom.shared.err.KnownError;

public class PgUygulamasi extends Uygulama {

	public static void main(String[] args) throws KnownError, IOException {
//		new PgUygulamasi().kur(new Surum("13.1"));
//		List<String> ret = KesifGorevlisi.tek().getResourceFiles("repos/pg");
//		System.out.println(KesifGorevlisi.tek().getResourceFiles("repos/pg"));
//		System.out.println(Arrays.toString(discoverPgs()));
//		System.out.println(Arrays.toString(discoverPgDatas()));
//		System.out.println(discoverPgDataVersion("rom"));
	}

	public static String discoverPgDataVersion(String dataDir) throws KnownError {
		File f = RomDir.pgDataDir(dataDir);
		File versionFile = new File(f, "PG_VERSION");
		try {
			return FileUtils.readFileToString(versionFile, "UTF-8");
		} catch (IOException e) {
			throw new KnownError("PG_VERSION file not found", e);
		}
	}

	@Override
	List<Surum> kesfetKurulabilecekUygulamaSurumleri() throws KnownError {
//		s.substring(11).replace(".tar.gz", "")
		try {
			List<String> ret = KesifGorevlisi.tek().getResourceFiles("repos/pg");
			List<Surum> us = new ArrayList<Surum>();
			for (String s : ret) {
				us.add(new Surum(s.substring(11).replace(".tar.gz", "")));
			}
			return us;
		} catch (IOException e) {
			throw new KnownError("kurulabilecek postgresql surumleri alinirken hata", e);
		}
	}

	@Override
	List<Surum> kesfetUygulamaSurumleri() {
		File f = RomDir.packDir("pg");
		String[] ss = f.list();
		List<Surum> us = new ArrayList<Surum>();
		for (String s : ss) {
			us.add(new Surum(s.substring(11)));
		}
		return us;
	}

	@Override
	void kur(Surum s) throws KnownError {
		String repo = "/repos/pg/postgresql-" + s.verString() + ".tar.gz";
		String tmp = "/tmp/postgresql-" + s.verString() + ".tar.gz";
		String buildDir = "/tmp/postgresql-" + s.verString();
		String pgDir = packDir(s).getPath();
		URL inputUrl = getClass().getResource(repo);
		File dest = new File(tmp);
		try {
			FileUtils.copyURLToFile(inputUrl, dest);
			System.out.println(KabukGorevlisi.tek().command("tar", "xvf", tmp, "-C", "/tmp"));

			System.out.println(
					KabukGorevlisi.tek().cdCommand(buildDir, "./configure", "--prefix=" + pgDir));
			System.out.println(KabukGorevlisi.tek().cdCommand(buildDir, "make", "world"));
			System.out.println(KabukGorevlisi.tek().cdCommand(buildDir, "make", "install-world"));
		} catch (IOException  e) {
			throw new KnownError("Failed to install postgresql", e);
		}
	}

	@Override
	protected File packDir(Surum s) {
		return new File(RomDir.packDir("pg"), "postgresql-" + s.verString());
	}

	@Override
	protected File binDir(Surum s) {
		return new File(packDir(s), "bin");
	}
	
	@Override
	Map<String, Surum> kesfetVeriler() throws KnownError {
		File f = RomDir.pgDataDirBase();
		String[] ss = f.list();
		Map<String, Surum> us = new HashMap<String, Surum>();
		for (String s : ss) {
			us.put(s, new Surum(discoverPgDataVersion(s)));
		}
		return us;
	}
	
	void newDb(Surum surum, String name) {
		calistir(surum, "initdb", "--encoding=UTF8");
	}
	

}
