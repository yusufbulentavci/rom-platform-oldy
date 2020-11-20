package com.bilgidoku.rom.pg;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.gunluk.RotatingStream;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Uygulama;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.ilk.util.Shell;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.os.KabukGorevlisi;
import com.bilgidoku.rom.run.KosuGorevlisi;

public class PgGorevlisi extends GorevliDir {
	public static final int NO=5;
	
	public static PgGorevlisi tek(){
		if(tek==null) {
			synchronized (PgGorevlisi.class) {
				if(tek==null) {
					tek=new PgGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static PgGorevlisi tek;
	private PgGorevlisi() {
		super("Pg", NO);
	}
	
	private final static MC mc=new MC(PgGorevlisi.class);

	private final int PORT=5445;


	private String dbDataDir;
	private String logFile;
	private String codeDir;

	private File pgDir;

	private String[] bins = new String[] { "clusterdb", "createuser", "dropuser", "oid2name", "pgbench", "pg_ctl",
			"pg_isready", "pg_resetxlog", "pg_test_fsync", "pg_xlogdump", "psql", "vacuumlo", "createdb", "dropdb",
			"ecpg", "pg_archivecleanup", "pg_config", "pg_dump", "pg_receivexlog", "pg_restore", "pg_test_timing",
			"postgres", "reindexdb", "createlang", "droplang", "initdb", "pg_basebackup", "pg_controldata",
			"pg_dumpall", "pg_recvlogical", "pg_standby", "pg_upgrade", "postmaster", "vacuumdb" };

	private boolean started = false;


	@Override
	protected void bitir(boolean dostca) {
		if (!started) {
			return;
		}

		System.out.println("terminating");
		if (dostca) {
			lastSql();
		}

		shutdown();
		System.out.println("done");

	}

	//public boolean 

	@Override
	protected void kur() throws KnownError {

		if(!checkIfPgServerRunning()){
			mc.out("Starting postgresql...");	
//			Shell.exec("pg_ctl", "start");
			KabukGorevlisi.tek().exec("pgctl.sh", "start");
			
			mc.out("Postgresql started");	
		}

		if(!Uygulama.tek().isProd()){
			mc.out("Drop testdb");	
			KabukGorevlisi.tek().exec("pg-droptestdb.sh");
			mc.out("Create testdb");	
			KabukGorevlisi.tek().exec("pg-createtestdb.sh");
			mc.out("Creating extensions");	
			//Shell.exec("psql", "-c", "create extension postgis;", "testdb");
//			Shell.exec("psql", "-c", "create extension postgis; create extension postgis_topology; create extension rompg; create extension hstore;", "testdb");
			KabukGorevlisi.tek().exec("pg-exec.sh", "testdb", "create extension rompg; create extension hstore;");
		} 
			
//		System.out.println("starting");
//		boolean upgradeData = false;
//
//		checkIfPgServerRunning();

//		if (pgDir.exists()) {
//			if (machineUpgrade != null && (fromVersion == null || fromVersion.compareTo(machineUpgrade) < 0)) {
//				makePg();
//			}
//		} else {
//			makePg();
//		}
//
//		envo();
//
//		if (dataUpgrade != null && (fromVersion == null || fromVersion.compareTo(dataUpgrade) < 0)) {
//			upgradeData = true;
//		}
//		File dd = new File(this.dbDataDir);
//		if (dd.exists() && upgradeData) {
//			try {
//				FileUtils.moveDirectory(dd, new File(backupDir(Sistem.millis())));
//			} catch (IOException e) {
//				throw new KnownError("Can not backup/moce pg datadir before upgrade");
//			}
//		}
//
//		if (!dd.exists()) {
//			initdb(dd);
//		}
//
//		try {
//			startDb();
//			this.started = true;
//
//			createDbUser();
//
//			boolean dbCreated = Uygulama.tek().isTest() ? createTestDb() : createDb();
//			verifyDb();
//
//			if (dbCreated) {
//				restoreLastSql();
//			}
//
//			// throw new KnownError("HODO");
//		} catch (KnownError e) {
//			if (started) {
//				try {
//					shutdown();
//				} catch (Exception er) {
//				}
//			}
//			throw e;
//		}
//
		// System.out.println("done");
	}

	public void verifyDb() throws KnownError {
		// String ret = getSqlResponse("select 1;", null);
		// Assert.beTrue(ret.length() > 0);
		System.out.println("============================");
		// String met = getSqlResponse("select rompg_mkplatformdir('/alişğiüIÖ');",
		// null);
		String met = getSqlResponse("select true;", null);
		// System.out.println("--");
		System.out.println(met);
		System.out.println("============================");
	}

	// SELECT 1 from pg_database WHERE datname='abc';
	private boolean createTestDb() throws KnownError {

		String resp = getSqlResponse("SELECT 1 from pg_database WHERE datname='testdb';", "postgres");
		if (resp.length() == 0) {
			KosuGorevlisi.tek().exec(null, false, false, true, null, null, "createdb", "-E", "UTF8", "testdb");
			KosuGorevlisi.tek().exec(null, false, false, true, null, null, "psql", "-c", "create extension hstore;", "testdb");
			StringBuilder out = new StringBuilder();
			KosuGorevlisi.tek().exec(null, false, false, true, out, out, "psql", "-c", "create extension rompg;", "testdb");
			System.out.println(out);
			// "create language plperlu; create extension hstore;", "testdb");
			return true;
		} else {
			System.out.println("Db exists");
			return false;
		}

	}

	private String getSqlResponse(String sql, String db) throws KnownError {
		//StringBuilder out = new StringBuilder();
		//KosuGorevlisi.tek().exec(null, false, false, false, out, out, "psql", "-qtA", "-c", sql,
	//			db == null ? (Uygulama.tek().isTest() ? "testdb" : "rom") : db);
	//	return out.toString();
//		return KabukGorevlisi.tek.exec("psql", "-qtA", "-c", sql,
//				db == null ? (Uygulama.tek().isTest() ? "testdb" : "rom") : db);
		return KabukGorevlisi.tek().exec("pg-exec.sh", db, sql);
	}

	private boolean createDb() throws KnownError {

		String resp = getSqlResponse("SELECT 1 from pg_database WHERE datname='rom';", "postgres");
		if (resp.length() == 0) {
			KosuGorevlisi.tek().exec(null, false, false, true, null, null, "createdb", "-E", "UTF8", "rom");
			KosuGorevlisi.tek().exec(null, false, false, true, null, null, "psql", "-c", "create extension hstore;", "rom");
			StringBuilder out = new StringBuilder();
			KosuGorevlisi.tek().exec(null, false, false, true, out, out, "psql", "-c", "create extension rompg;", "rom");

			System.out.println("Db created");
			return true;
		} else {
			System.out.println("Db exists");
			return false;
		}
	}

	private void createDbUser() throws KnownError {
		String sql = "DO $body$ BEGIN IF NOT EXISTS (SELECT * FROM pg_catalog.pg_user WHERE usename = 'yuzrun') THEN create user yuzrun with password 'jvl5SSFq8fsemod' superuser createdb createrole inherit login; END IF; END $body$;";
		// System.err.println(sql);
		KosuGorevlisi.tek().exec(null, false, false, true, null, null, "psql", "-t", "-c", sql, "postgres");

		System.out.println("Db user created");
	}

	private boolean checkIfPgServerRunning() throws KnownError {
		try {
			Socket ss = new Socket("rom.intranet", PORT);
			ss.close();
			return true;
		} catch (IOException e) {
			//throw new KnownError("Postgresql server is running (on port 5443). It shouldnt be").fatal();
			return false;
		}
	}



	private void lastSql() {
		File f = lastSqlFile();
		try {
			KosuGorevlisi.tek().exec(null, false, false, true, null, null, "pg_dump", "-f", f.getPath(),
					Uygulama.tek().isProd() ? "rom" : "testdb");
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Could not take last sql");
		}
	}

	private void restoreLastSql() {
		File f = lastSqlFile();
		if (!f.exists())
			return;
		try {
			KosuGorevlisi.tek().exec(null, false, false, true, null, null, "pg_restore", "-f", f.getPath(),
					Uygulama.tek().isProd() ? "rom" : "testdb");
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Could not take last sql");
		}
	}

	private File lastSqlFile() {
		File f = new File(dataDir(), "last.sql");
		return f;
	}

	private void envo() {
		KosuGorevlisi.tek().setEnvo("PGUSER", Sistem.cur.getRomUser());
		KosuGorevlisi.tek().setEnvo("PGPORT", PORT+"");
		KosuGorevlisi.tek().setEnvo("PGDATA", dbDataDir);
		KosuGorevlisi.tek().setPathDir(new File(pgDir, "bin").getPath(), false, bins);
	}

	private void initdb(File dd) throws KnownError {
		if (dd.exists()) {
			try {
				FileUtils.deleteDirectory(dd);
			} catch (IOException e) {
				throw new KnownError("Pg db dir couldnt be deleted before recreation", e).fatal();
			}
		}
		try {
			FileUtils.forceMkdir(dd);
		} catch (IOException e) {
			throw new KnownError("Pg db dir couldnt be created for initdb", e).fatal();
		}
		StringBuilder out = new StringBuilder();
		KosuGorevlisi.tek().exec(null, false, false, true, out, null, "initdb", "--encoding=UTF8");

		File postgresConf = new File(dd, "postgresql.conf");
		File hbaConf = new File(dd, "pg_hba.conf");
		Assert.beTrue(postgresConf.exists());
		Assert.beTrue(hbaConf.exists());

		try {
			FileUtils.write(postgresConf, "\nlisten_addresses = 'rom.intranet'", Charset.defaultCharset(), true);
			FileUtils.write(hbaConf, "\nhost	rom		 yuzrun		0.0.0.0/0		md5"
					+ "\nhost	testdb		 yuzrun		0.0.0.0/0		trust"
			// + "\nhost all rom 192.168.1.0/24 trust"
					, Charset.defaultCharset(), true);

		} catch (IOException e) {
			throw new KnownError("Coudnt customize postgresql.conf or pg_hba.conf", e).fatal();
		}

		// Set mode for db dir
		KosuGorevlisi.tek().exec(null, false, false, true, out, null, "chmod", "0700", dbDataDir);
		System.out.println();

	}

	private void makePg() throws KnownError {
		try {
			ExtractDirectorFromClasspath.extract("pg/", pgDir.getPath());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			throw new KnownError("Postgres couldnt be installed", e).fatal();
		}

	}

	// private void backup(){
	// String label = "db-"+System.currentTimeMillis()+".sql";
	// ProcessBuilder pb = new ProcessBuilder("sudo", "-u", "rompg", pgCtl,
	// "-w", "-D", dataDir, "-s", "-l",
	// logFile);
	// String output = IOUtils.toString(pb.start().getInputStream(),
	// Charset.defaultCharset());
	// }

	private void startDb() throws KnownError {

		// try {
		// Shell.exec("sudo", "-u", "rompg", pgCtl, "-w", "-D", dbDataDir, "-s",
		// "-l", logFile);
		// } catch (KnownError e) {
		// perm.safePut("firstStart", false);
		// throw e;
		// }
		// perm.safePut("firstStart", true);
		// savePerm();

		// , "-p", pgDir.getPath()
		KosuGorevlisi.tek().exec(null, false, false, true, null, null, "pg_ctl", "start", "-w", "--mode=fast", "-l", logFile);

	}

	private void shutdown() {
//		try {
//			KosuGorevlisi.tek().exec(null, false, false, true, null, null, "pg_ctl", "stop", "-w", "--mode=fast");
//		} catch (KnownError e) {
//			e.printStackTrace();
//		}
	}

	//
	// // [avci@corn bin]$ ./pg_ctl -V
	// // pg_ctl (PostgreSQL) 9.5.3
	// private String version() throws KnownError {
	// String output = Shell.exec("sudo", "-u", "rompg", pgCtl, "-V");
	// if (!output.startsWith("pg_ctl (PostgreSQL) ")) {
	// throw new KnownError("Unexpected pg_ctl -V response:" + output);
	// }
	// String v = output.substring("pg_ctl (PostgreSQL) ".length()).trim();
	// return v;
	// }

	@Override
	public void selfDescribe(JSONObject jo) {
	}


	// [avci@corn bin]$ sudo -u rompg pg_ctl -s status -D
	// /opt/bilgidoku/rom-server/var/pg

	// Running
	// pg_ctl: server is running (PID: 427)
	// /opt/bilgidoku/packs/pg-9.5.3/bin/postgres "-D"
	// "/opt/bilgidoku/rom-server/var/pg"
	// Not running
	// pg_ctl: no server running

	// private boolean isRunning() throws KnownError {
	// String output = Shell.exec("sudo", "-u", "rompg", pgCtl, "-s", "status",
	// "-D", dbDataDir);
	// return output.contains("PID");
	//
	// }

	protected JSONObject defaultPerm() {
		return new JSONObject();
	}

	public int getPort() {
		return PORT;
	}

}
