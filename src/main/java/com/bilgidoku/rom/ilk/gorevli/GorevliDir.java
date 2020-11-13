package com.bilgidoku.rom.ilk.gorevli;

import java.io.File;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.gorevli.Gorevli;

public abstract class GorevliDir extends Gorevli {


	public GorevliDir(String kodu, int no) {
		super(kodu, no);
	}


	protected String tmpDir() {
		return Dir.tmpDir(kod);
	}

	protected String cheapDir() {
		return Dir.cheapDir(kod);
	}

	protected String backupDir(long timestamp) {
		return Dir.backupDir(timestamp, kod);
	}

	protected String dataDir() {
		return Dir.dataDir(kod);
	}

	protected String codeDir() {
		return Dir.codeDir(kod);
	}

	protected String runDir() {
		return Dir.runDir(kod);
	}

	protected File safeDataDir() {
		return Dir.safeDataDir(kod);
	}

	protected File safeCheapDir() {
		return Dir.safeCheapDir(kod);
	}

	protected File safeTmpDir() {
		return Dir.safeCheapDir(kod);
	}

	protected File safeBackupDir() {
		return Dir.safeCheapDir(kod);
	}

	protected File safeRunDir() {
		return Dir.safeRunDir(kod);
	}
	
	protected void emptyDir(File safeTmpDir) {
		Dir.emptyDir(safeTmpDir);
	}
	
	final protected KnownError err(Exception e) {
		if (e instanceof KnownError) {
			return (KnownError) e;
		}
		if (e instanceof ParameterError) {
			return new KnownError(e).badRequest();
		}

		return new KnownError(e).internalError();
	}
	
	public boolean mayi() {
//		return this.state == RUNSTATE && ServiceDiscovery.mayi();
		return true;
	}
	
	public void selfDescribe(JSONObject jo) {
		
	}
	
}
