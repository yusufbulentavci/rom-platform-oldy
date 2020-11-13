package com.bilgidoku.rom.ilk.gorevli;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Uygulama;


public class Dir {

	private final static String BaseDataDir = RomDir.dataDir().getPath();

	private final static String TestDir = BaseDataDir + "/test";

	private final static String TmpDir = Uygulama.tek().isProd() ? BaseDataDir + "/gecici" : BaseDataDir + "/test/gecici";
	private final static String CheapDir = Uygulama.tek().isProd() ? BaseDataDir + "/ucuz" : BaseDataDir + "/test/ucuz";
	private final static String BackupDir = Uygulama.tek().isProd() ? BaseDataDir + "/sakla" : BaseDataDir + "/test/sakla";
	private final static String DataDir = Uygulama.tek().isProd() ? BaseDataDir + "/onemli" : BaseDataDir + "/test/onemli";
	private final static String CodeDir = Uygulama.tek().isProd() ? BaseDataDir + "/code" : BaseDataDir + "/test/code";
	private final static String RunDir = Uygulama.tek().isProd() ? BaseDataDir + "/run" : BaseDataDir + "/test/run";


//	public final static String ConsoleDir = BaseOsDir + "/console";
	public final static String WwwDir = CodeDir + "/www";
//	public final static String CertsDir = BaseOsDir + "/certs";
//	public final static String MediasDir = BaseFrontDir + "/medias";

	public static void cleanTestDir() throws KnownError {
		if (Uygulama.tek().isTest()) {
			File f = new File(TestDir);
			if (f.exists()) {
				try {
					FileUtils.deleteDirectory(f);
				} catch (IOException e) {
					throw new KnownError(e);
				}
			}
			f.mkdirs();
		}
	}

	public static String tmpDir(String bundle) {
		return TmpDir + "/" + bundle;
	}

	public static String cheapDir(String bundle) {
		return CheapDir + "/" + bundle;
	}

	public static String backupDir(long timestamp, String bundle) {
		return BackupDir + "/" + timestamp + "/" + bundle;
	}

	public static String dataDir(String bundle) {
		return DataDir + "/" + bundle;
	}

	public static String codeDir(String bundle) {
		return CodeDir + "/" + bundle;
	}
	
	public static String runDir(String bundle) {
		return RunDir + "/" + bundle;
	}
	
	

	public static File safeDataDir(String bundle) {
		File f = new File(dataDir(bundle));
		if (f.exists())
			return f;
		f.mkdirs();
		return f;
	}

	public static File safeTmpDir(String bundle) {
		File f = new File(tmpDir(bundle));
		if (f.exists())
			return f;
		f.mkdirs();
		return f;
	}

	public static File safeCheapDir(String bundle) {
		File f = new File(cheapDir(bundle));
		if (f.exists())
			return f;
		f.mkdirs();
		return f;
	}

	public static File safeBackupDir(String bundle) {
		File f = new File(dataDir(bundle));
		if (f.exists())
			return f;
		f.mkdirs();
		return f;
	}

	public static File safeRunDir(String bundle) {
		File f = new File(runDir(bundle));
		if (f.exists())
			return f;
		f.mkdirs();
		return f;
	}
	
//	public static void consoleRun(String script) throws KnownError {
//		Shell.exec("bash", ConsoleDir + "/" + script);
//	}

	public static void existDir(String s) {
		File f = new File(s);
		if (f.exists())
			return;
		if (!f.mkdirs())
			throw new RuntimeException("Dir couldnt be created:" + s);
	}

	public static void emptyDir(String s) {
		File f = new File(s);
		if (f.exists()){
			try{
				FileUtils.deleteDirectory(f);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		if (!f.mkdirs())
			throw new RuntimeException("Dir couldnt be created:" + s);
	}

	public static void emptyDir(File f) {
		if (f.exists()){
			try{
				FileUtils.deleteDirectory(f);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		if (!f.mkdirs())
			throw new RuntimeException("Dir couldnt be created:" + f.getPath());
	}

	//
	//
	// public static String baseModed() {
	// if (Uygulama.tek().isTest()) {
	// return "/tmp/bilgidoku";
	// }
	// return "/opt/bilgidoku";
	// }
	//
	// public static String serverDir() {
	// return baseModed() + "/rom-server";
	// }
	//
	//
	// public static String base() {
	// return "/opt/bilgidoku";
	// }
	//
	// public static String varDir() {
	// return serverDir() + "/var";
	// }
	//
	//
	// public static String dynamicServerDir(){
	// return serverDir() + "/dynamic";
	// }
	//
	//
	// public static String globalDir(){
	// return base() + "/global";
	// }
	//
	// public static String mediasDir(){
	// return globalDir() + "/medias";
	// }
	//
	// public static String binDir(){
	// return base() + "/bin";
	// }
	//
	// public static String packsDir(){
	// return base() + "/packs";
	// }
	//
	// public static String tmpDir(){
	// return base() + "/tmp";
	// }
	//
	// public static String buildDir(){
	// return tmpDir() + "/build";
	// }
	//
	// public static String dynamicDir(){
	// return base() + "/dynamic";
	// }
	//
	// public static String downloadDir(){
	// return dynamicDir() + "/download";
	// }
	//
	// public static String backupDir(){
	// return dynamicDir() + "/backup";
	// }
	//
	// public static String romServerBackupDir(){
	// return backupDir() + "/rom-server";
	// }
	//
	// public static String varSubDir(String n) {
	// String subdir = varDir() + "/" + n;
	// existDir(subdir);
	// return subdir;
	// }
	//
	// public static String dynamicDir(String n) {
	// String subdir = dynamicDir() + "/" + n;
	// existDir(subdir);
	// return subdir;
	// }
	//
	// public static String logsDir() {
	// return dynamicServerDir("logs");
	// }
	//
	//
	// public static String dynamicServerDir(String n) {
	// String subdir = dynamicServerDir() + "/" + n;
	// existDir(subdir);
	// return subdir;
	// }
}
