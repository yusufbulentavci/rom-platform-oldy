package com.bilgidoku.rom.pg.dbfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;
import com.bilgidoku.rom.ilk.gorevli.Dir;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.run.timer.CurrentTime;
import com.bilgidoku.rom.shared.err.KnownError;

import io.netty.handler.codec.http.multipart.FileUpload;

class FileItem {
	File f;
	FileUpload fu;
	InputStream is;

	private String string;
	boolean deleteOnError = false;
	private String suffix;
	private String name;
	private MimeMessage mime;

	public FileItem(File f, boolean deleteOnError) {
		this.f = f;
		this.deleteOnError = deleteOnError;
	}

	public FileItem(FileUpload fu) {
		this.fu = fu;
	}

	public FileItem(InputStream is, String name) {
		this.is = is;
		this.name = name;
	}

	public FileItem(String string2, String suffix) {
		this.string = string2;
		this.suffix = suffix;
		this.deleteOnError = false;
	}

	public FileItem(MimeMessage msg) {
		this.mime = msg;
		this.suffix = ".m.txt";
		this.deleteOnError = false;
	}

	public String getName() {
		if (f != null)
			return f.getName();
		if (suffix != null) {
			return System.currentTimeMillis() + suffix;
		}
		if (name != null)
			return name;
		return fu.getFilename();

	}

	public boolean renameTo(File cp) throws IOException, MessagingException {
		if (f != null) {
			// FileInputStream is = new FileInputStream(f);
			FileUtils.moveFile(f, cp);
			return true;
		}
		if (string != null) {
			FileUtils.write(cp, string, "UTF-8");
			return true;
		} else if (is != null) {
			FileUtils.copyInputStreamToFile(is, cp);
			return true;
		} else if (mime != null) {
			mime.writeTo(new FileOutputStream(cp));
			return true;
		} else {
			return fu.renameTo(cp);
		}
	}

	public void delete() {
		if (!deleteOnError)
			return;
		f.delete();
	}
}

public class DbfsGorevlisi extends GorevliDir {

	final static private MC mc = new MC(DbfsGorevlisi.class);

	public static final int NO = 19;

	public static DbfsGorevlisi tek() {
		if (tek == null) {
			synchronized (DbfsGorevlisi.class) {
				if (tek == null) {
					tek = new DbfsGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static DbfsGorevlisi tek;

	private DbfsGorevlisi() {
		super("Dbfs", NO);
		DbFileProp.base = dataDir();
		DbFileProp.basePlus = DbFileProp.base + "/";
		Dir.existDir(DbFileProp.base);
	}

	public void selfDescribe(JSONObject jo) {
	}

//	public HostingFS(File base, long counterFirst) {
//		// controlTime();
//		// counter = new AtomicLong(counterFirst);
//	}

	// private final AtomicLong counter;

//	private final int worldId;

	// private CurrentTime currentTime;

	// private volatile String curPlus;

	// @Override
	// public synchronized void controlTime() {
	// //
	// // String year = Integer.toString(c.get(Calendar.YEAR));
	// // int m=c.get(Calendar.MONTH);
	// // String month=(m<10)?"0"+m:Integer.toString(m);
	// // if(year.equals(curYear) && month.equals(curMonth))
	// // return;
	// setYearMonth(curYear, curMonth, curDay);
	// }

	public String make(int hostId, MimeMessage msg) throws KnownError {
		return make(hostId, new FileItem(msg));
	}

	public String make(int hostId, FileUpload old) throws KnownError {
		return make(hostId, new FileItem(old));
	}

	public String make(int hostId, File old, boolean deleteOnError) throws KnownError {
		return make(hostId, new FileItem(old, deleteOnError));
	}

	AtomicInteger ucount = new AtomicInteger();

//	private long initialCounter() {
//		return (System.currentTimeMillis() % Integer.MAX_VALUE) << 32;
//	}

	// private String getUnique() {
	// long t = System.currentTimeMillis();
	// int u;
	// synchronized (ucount) {
	// if(t==lastTime){
	// u=ucount.incrementAndGet();
	// }else{
	// ucount.set(0);
	// u=0;
	// }
	// lastTime=t;
	// }
	// return Long.toHexString(t)+"-"+u;
	// }

	Astate fileNameCollision = mc.c("file-name-collision");

	private String make(int hostId, FileItem oldi) throws KnownError {
		String oldName = oldi.getName();
		if (oldName.length() > 240) {
			oldi.delete();
			throw new KnownError("Dbfs file name length is over 240:" + oldName);
		}

		CurrentTime now = new CurrentTime();
		// boolean checkAndChange=false;
		int unique;
		File p;
		// synchronized (ucount) {
		// if(currentTime==null){
		// checkAndChange=true;
		// currentTime=now;
		// }else if(now.anotherDay(currentTime)){
		// checkAndChange=true;
		// }
		// if(now.millis!=currentTime.millis){
		// ucount.set(0);
		// unique=0;
		// }else{
		// }
		// unique=(int) (Math.random()*10000);
		// this.currentTime=now;
		// }
		synchronized (ucount) {
			if (ucount.get() > 999) {
				ucount.set(0);
			}
			unique = ucount.getAndIncrement();
			p = DbFileProp.currentPath(hostId, now, false);
			if (!p.exists()) {
				if (!p.mkdirs()) {
					oldi.delete();
					throw new KnownError("Dbfs failed to make dirs:" + p.getPath());
				}
			}
		}

		String dbfsid = DbFileProp.encode(now, unique, oldName);
		String fn = DbFileProp.encodeFileName(now, unique, oldName);

		File cp = new File(p, fn);
		if (cp.exists()) {
			fileNameCollision.more(oldName);
			boolean found = false;
			for (unique = 1000; unique < 2000; unique++) {
				fn = DbFileProp.encodeFileName(now, unique, oldName);
				cp = new File(p, fn);
				if (!cp.exists()) {
					found = true;
					break;
				}
			}
			if (!found) {
				fileNameCollision.failed(oldName);
				oldi.delete();
				throw new KnownError("Already existing file for " + oldName + " file:" + cp.getPath());
			}
		}

		boolean suc;
		try {
			suc = oldi.renameTo(cp);
			if (!suc) {
				oldi.delete();
				throw new KnownError("HostingFS cant rename for " + oldName + " to " + cp.getPath());
			}
			return dbfsid;
		} catch (IOException e) {
			oldi.delete();
			throw new KnownError("HostingFS cant rename for " + oldName + " to " + cp.getPath(), e);
		} catch (MessagingException e) {
			throw new KnownError("HostingFS cant write down mime " + cp.getPath(), e);
		}
	}

	public File get(int hostId, String name) throws KnownError {
		File ret = findFile(hostId, name);
		return ret;
	}

	public void delete(int hostId, String name) throws KnownError {
		File ret = findFile(hostId, name);
		if (!ret.delete())
			throw new KnownError("Dbfs failed to delete file " + hostId + ":" + name + ":" + ret.getPath());
	}

	private File findFile(int hostId, String name) throws KnownError {
		if (name == null) {
			throw new KnownError().notFound("hostId:" + hostId + "-null");
		}

		name = name.trim();
		if (name.isEmpty()) {
			throw new KnownError().notFound(name);
		}

		DbFileProp dbfp = DbFileProp.decode(name);

		// String[] yearMonthId = parse(name);
		// File path = curPath(hostId, "/" + yearMonthId[0] + "/" +
		// yearMonthId[1]);
		// File ret = new File(path, yearMonthId[2] + "-" + yearMonthId[3]);
		//
		File ret = dbfp.toPathFile(hostId);

		if (!ret.exists()) {
			throw new KnownError("Dbfs file not found:hostId:" + hostId + " dbfs:" + name
					+ " not found in directory file:" + ret.getPath()).notFound(name);
		}
		return ret;
	}

	//
	// private File optFile(int hostId, String name) throws KnownError {
	// String[] yearMonthId = parse(name);
	// File path = curPath(hostId, "/" + yearMonthId[0] + "/" + yearMonthId[1]);
	// File ret = new File(path, yearMonthId[2] + "-" + yearMonthId[3]);
	// return ret;
	// }
	//
	// private File newFile(int hostId, String name) throws KnownError {
	// String[] yearMonthId = parse(name);
	// File path = curPath(hostId, "/" + yearMonthId[0] + "/" + yearMonthId[1]);
	// File ret = new File(path, yearMonthId[2] + "-" + yearMonthId[3]);
	// return ret;
	// }
	//
	// public final static String noDbfsName(String name) throws KnownError {
	// String[] parts = parse(name);
	//
	// return parts[3];
	// }

	// public void setYearMonth(int year, int month, int day) {
	// curYear = year;
	// curMonth = month;
	// curDay = day;
	// curPlus = "/" + year + "/" + month + "/" + day;
	// }

	public String make(int hostId, String string, String suffix) throws KnownError {
		return make(hostId, new FileItem(string, suffix));
	}

	public String make(int hostId, InputStream old, String name) throws KnownError {
		return make(hostId, new FileItem(old, name));
	}

	public String noDbfs(String name) throws KnownError {
		DbFileProp dbfp = DbFileProp.decode(name);
		return dbfp.getFileName();
	}

	public InputStream getInputStream(int hostId, String dbfs) throws FileNotFoundException, KnownError {
		return new FileInputStream(get(hostId, dbfs));
	}

	public File tempFile(String extension) throws KnownError {
		try {
			return File.createTempFile("dbfs", ".t" + extension);
		} catch (IOException e) {
			throw new KnownError(e);
		}
	}

	public void symlink(String commonDbfs, int hostIdTo) throws KnownError {
		// File real = optFile(0, commonDbfs);
		DbFileProp fp = DbFileProp.decode(commonDbfs);

		File real = fp.toPathFile(0);

		if (!real.exists()) {
			throw new KnownError("Symlink; real file not found:" + fp.toString());
		}

		File link = fp.toPathFile(hostIdTo);

		if (link.exists()) {
			return;
		}

		if (!link.getParentFile().exists()) {
			if (!link.getParentFile().mkdirs()) {
				throw new KnownError("Symlink; link folder couldnt be created:" + link.getParentFile().toString());
			}
		}

		try {
			Files.createSymbolicLink(link.toPath(), real.toPath());
		} catch (IOException e) {
			throw new KnownError("HostingFS cant symlink failed for real:" + real.getPath() + " link:" + link.getPath(),
					e);
		}

		// if (real.exists()) {
		// File link = newFile(hostIdTo, commonDbfs);
		// if (link.exists()) {
		// return;
		// }
		// try {
		// Files.createSymbolicLink(link.toPath(), real.toPath());
		// } catch (IOException e) {
		// throw new KnownError("HostingFS cant symlink failed for real:"
		// + real.getPath() + " link:" + link.getPath(), e);
		// }
		// } else {
		// real = findFile(hostIdTo, commonDbfs);
		// File link = newFile(0, commonDbfs);
		// if (link.exists()) {
		// return;
		// }
		// try {
		// Files.createSymbolicLink(link.toPath(), real.toPath());
		// } catch (IOException e) {
		// throw new KnownError("HostingFS cant symlink failed for real:"
		// + real.getPath() + " link:" + link.getPath(), e);
		// }
		// }

	}

	public Path getPath(int hostId, String name) throws KnownError {
		return get(hostId, name).toPath();
	}

	public String wwwPath(int hostId, String eatPrefix, String uri) {
		return DbFileProp.wwwPath(hostId, eatPrefix, uri);
	}

	public String wwwDir(int hostId) {
		return DbFileProp.wwwDir(hostId);
	}

	// public void resetForTest(){
	// counter.set(0);
	// }

}
