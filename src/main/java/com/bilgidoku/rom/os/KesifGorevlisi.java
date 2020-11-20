package com.bilgidoku.rom.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;

public class KesifGorevlisi extends GorevliDir {

	public static final int NO = 0;

	public static KesifGorevlisi tek() {
		if (tek == null) {
			synchronized (KesifGorevlisi.class) {
				if (tek == null) {
					tek = new KesifGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static KesifGorevlisi tek;

	private KesifGorevlisi() {
		super("Kesif", NO);
	}
	
	public List<String> getResourceFiles(String path) throws IOException {
	    List<String> filenames = new ArrayList<>();

	            InputStream in = getResourceAsStream(path);
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String resource;

	        while ((resource = br.readLine()) != null) {
	            filenames.add(resource);
	        }

	    return filenames;
	}

	private InputStream getResourceAsStream(String resource) {
	    final InputStream in
	            = getContextClassLoader().getResourceAsStream(resource);

	    return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	private ClassLoader getContextClassLoader() {
	    return Thread.currentThread().getContextClassLoader();
	}



}
