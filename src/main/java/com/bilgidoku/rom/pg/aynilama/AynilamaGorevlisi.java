package com.bilgidoku.rom.pg.aynilama;

import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.pg.PgGorevlisi;
import com.bilgidoku.rom.pg.sqlunit.model.Table;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Uygulama;

import net.sf.clipsrules.jni.AkilGorevlisi;

public class AynilamaGorevlisi extends GorevliDir{
	
	public static final int NO = 4;

	public static AynilamaGorevlisi tek() {
		if (tek == null) {
			synchronized (AynilamaGorevlisi.class) {
				if (tek == null) {
					tek = new AynilamaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static AynilamaGorevlisi tek;
	private ReplikeEt replikeEt;

	private AynilamaGorevlisi() {
		super("Aynilama", NO);
		}
	
	public void aynila(Map<String,Table> clipsTables) throws KnownError {
		for (Table tbl : clipsTables.values()) {
			new ImportFactsDbOp(tbl).doit();
		}
		replikeEt=new ReplikeEt("jdbc:postgresql://rom.intranet:" + PgGorevlisi.tek().getPort()
				+ (Uygulama.tek().testDb() ? "/testdb" : "/rom"), clipsTables);
		KosuGorevlisi.tek().exec(replikeEt);
		
//		AkilliGorevli.tek().deleteById("menu", "kim3");
	}
	
}
