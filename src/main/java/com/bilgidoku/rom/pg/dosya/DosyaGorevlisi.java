package com.bilgidoku.rom.pg.dosya;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;

class Dosya {
	public Dosya(int id, String name) {
		this.id=id;
		this.adi=name;
	}
	int id;
	String adi;
	long degismeZamani = System.currentTimeMillis();
	Long oid;
}

public class DosyaGorevlisi extends GorevliDir {
	public static final int NO = 6;

	public static DosyaGorevlisi tek() {
		if (tek == null) {
			synchronized (DosyaGorevlisi.class) {
				if (tek == null) {
					tek = new DosyaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static DosyaGorevlisi tek;

	private DosyaGorevlisi() {
		super("Dosya", NO);
	}

	private File root;
	private AtomicInteger serial = new AtomicInteger();

	private Map<Integer, Dosya> dosyaById = new HashMap<>();
	private Map<Long, Dosya> dosyaByOid = new HashMap<>();

	@Override
	protected void kur() throws KnownError {
		this.root = safeTmpDir();
		emptyDir(this.root);
	}

	@Override
	protected void bitir(boolean dostca) {
		emptyDir(this.root);
	}

	@Override
	public void selfDescribe(JSONObject jo) {
//		jo.safePut("errcount", errorCounter.get());
//		jo.safePut("file", log.describe());
	}

	public int ekle(InputStream is, String adi, Integer omru, Long oid) throws KnownError {
		int id = serial.getAndIncrement();
		File f;
		try {
			f = dosyaById(id);
			FileUtils.copyInputStreamToFile(is, f);
			Dosya d = new Dosya(id, adi);
			dosyaById.put(id, d);
			if(oid != null) {
				d.oid=oid;
				dosyaByOid.put(oid, d);
			}
			return id;
		} catch (IOException e) {
			throw new KnownError("Dosya eklerken hata olustu:"+adi, e);
		}

	}
	
	public File dosyaById(Integer id) {
		return new File(this.root, Integer.toString(id));
	}
	
	public void sil(Integer id) {
		Dosya d=dosyaById.remove(id);
		if(d!=null) {
			if(d.oid!=null) {
				dosyaByOid.remove(d.oid);
			}
			dosyaById(id).delete();
		}
	}

}
