package com.bilgidoku.rom.os;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.json.JSONObject;

public class OsGorevlisi extends GorevliDir {
	public static final int NO = 52;

	boolean ubuntu = false;
	boolean debian = false;
	boolean centos = false;

	public static OsGorevlisi tek() {
		if (tek == null) {
			synchronized (OsGorevlisi.class) {
				if (tek == null) {
					tek = new OsGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static OsGorevlisi tek;

	private OsGorevlisi() {
		super("Os", NO);
	}

	@Override
	protected void kur() throws KnownError {
		checkLinuxOs();
	}

	@Override
	protected void bitir(boolean dostca) {
	}

	@Override
	public void selfDescribe(JSONObject jo) {
	}

	private void checkLinuxOs() {
		InputStream is = null;
		try {
			final Process process = Runtime.getRuntime().exec(new String[] { "uname", "-a" });
			is = process.getInputStream();
			String uname = IOUtils.toString(is);
			this.ubuntu = uname.toLowerCase().indexOf("ubuntu") >= 0;
			this.debian = uname.toLowerCase().indexOf("debian") >= 0;
			this.centos = uname.toLowerCase().indexOf("centos") >= 0;
		} catch (IOException exception) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void discover() {
		
	}

	public static void main(String[] args) throws KnownError, IOException {
//		System.out.println(OsGorevlisi.tek().centos);
//		OsGorevlisi.tek().createUser();
//		OsGorevlisi.tek().installDependencies();
		OsGorevlisi.tek().installPg();
	}

	public void createUser() throws KnownError {
		System.out.println(KabukGorevlisi.tek().command("sudo", "adduser", "--shell", "/bin/bash", "rompg", "rompg"));
	}

	public void installDependencies() throws KnownError {
		if (debian || ubuntu)
			System.out.println(
					KabukGorevlisi.tek().command("sudo", "apt-get", "-y", "install", "build-essential", "g++", "curl",
							"libreadline-dev", "zlib1g-dev", "libxml2-dev", "libjson-c-dev"));
	}
	
	static final String pgDir="/home/rompg/rom/packs/pg-13rc1";
	
	public void installPg() throws IOException, KnownError {
		URL inputUrl = getClass().getResource("/repos/pg/postgresql-13rc1.tar.gz");
		File dest = new File("/tmp/postgresql-13rc1.tar.gz");
		FileUtils.copyURLToFile(inputUrl, dest);
		System.out.println(KabukGorevlisi.tek().command("tar", "xvf", "/tmp/postgresql-13rc1.tar.gz", "-C", "/tmp"));
		
		System.out.println(KabukGorevlisi.tek().cdCommand("/tmp/postgresql-13rc1", "./configure", "--prefix="+pgDir));
		System.out.println(KabukGorevlisi.tek().cdCommand("/tmp/postgresql-13rc1", "make", "world"));
		System.out.println(KabukGorevlisi.tek().cdCommand("/tmp/postgresql-13rc1", "make", "install-world"));
	}

}
