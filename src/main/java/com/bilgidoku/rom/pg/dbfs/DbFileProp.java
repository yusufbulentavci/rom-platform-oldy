package com.bilgidoku.rom.pg.dbfs;

import java.io.File;

import com.bilgidoku.rom.run.timer.CurrentTime;
import com.bilgidoku.rom.shared.err.KnownError;

class DbFileProp {

	private String unique;
	private String fileName;
	private CurrentTime time;

	public static String base;
	public static String basePlus;

	public DbFileProp(CurrentTime time, String uniquestr, String fileName) {
		this.time = time;
		this.unique = uniquestr;
		this.fileName = fileName;

	}

	public final static DbFileProp decode(String name) throws KnownError {
		// int ind = name.indexOf('-');
		// if (ind < 0 || ind > 4)
		// throw new KnownError("Invalid dbfs file name, world not found:"
		// + name);
		//
		// String worldStr = name.substring(0, ind);
		// int world;
		// try {
		// world = Integer.parseInt(worldStr);
		// } catch (NumberFormatException nfe) {
		// throw new KnownError("Invalid dbfs file name, world not integer:"
		// + name);
		// }
		int timeind = name.indexOf('-');
		if (timeind < 0)
			throw new KnownError("Invalid dbfs file name, time not found:" + name);

		String timeStr = name.substring(0, timeind);
		long time;

		try {
			time = Long.valueOf(timeStr, 16);
		} catch (NumberFormatException nfe) {
			throw new KnownError("Invalid dbfs file name, time not long:" + name);
		}

		int uniqueind = name.indexOf('-', timeind + 1);
		if (uniqueind < 0)
			throw new KnownError("Invalid dbfs file name, uniqueind not found:" + name);

		String uniquestr = name.substring(timeind + 1, uniqueind);

		String fileName = name.substring(uniqueind + 1);

		DbFileProp ret = new DbFileProp(new CurrentTime(time), uniquestr, fileName);
		return ret;
	}

	public static String encode(CurrentTime now, int unique2, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(Long.toHexString(now.millis));
		sb.append("-");
		sb.append(unique2);
		sb.append("-");
		sb.append(fileName);
		return sb.toString();
	}

	public static String encodeFileName(CurrentTime now, int unique2, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(Long.toHexString(now.millis));
		sb.append("-");
		sb.append(unique2);
		sb.append("-");
		sb.append(fileName);
		return sb.toString();
	}

	public static File currentPath(int hostId, CurrentTime time, boolean checkAndChange) {
		StringBuilder ret = new StringBuilder();

		ret.append(basePlus);

		ret.append("local");

		ret.append("/hosts/");

		ret.append(hostId);

		ret.append("/dbfs/");
		ret.append(time.year);
		ret.append("/");
		ret.append(time.month);
		ret.append("/");
		ret.append(time.day);
		File f = new File(ret.toString());
		if (checkAndChange && !f.exists()) {
			f.mkdirs();
		}
		return f;
	}

	public String toPath(int hostId) {
		StringBuilder ret = new StringBuilder();

		ret.append(basePlus);

		// if(hostId==0 && currentWorldId!=world){
		// ret.append("global/");
		// ret.append(world);
		// }else{
		// }
		ret.append("local");

		ret.append("/hosts/");

		ret.append(hostId);

		ret.append("/dbfs/");
		ret.append(time.year);
		ret.append("/");
		ret.append(time.month);
		ret.append("/");
		ret.append(time.day);
		ret.append("/");
		ret.append(Long.toHexString(time.millis));
		ret.append("-");
		ret.append(unique);
		ret.append("-");
		ret.append(fileName);

		return ret.toString();
	}

	public File toPathFile(int hostId) {
		return new File(toPath(hostId));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append(time.month);
		sb.append("/");
		sb.append(time.day);
		sb.append("/");
		sb.append(unique);
		sb.append("-");
		sb.append(fileName);
		return sb.toString();
	}

	// public static File dbfs(int worldId, int hostId,
	// String path) {
	// return new File(basePlus, worldId + "/hosts/" + hostId + "/dbfs/"
	// + path);
	// }

	public String getFileName() {
		return fileName;
	}

	// public static final String SITE_SUFFIX = "www";
	//
	// public static String contentPath(String directoryPath, int hostId,
	// String hostSuffix, String uri) {
	// return directoryPath+"hosts/"+hostId+"/"+hostSuffix+uri;
	// }
	//
	// public static String contentDir(String directoryPath, int hostId,
	// String hostSuffix) {
	// return directoryPath+"hosts/"+hostId+"/"+hostSuffix;
	// }

	public static String wwwPath(int hostId, String eatPrefix, String uri) {
		if(eatPrefix!=null){
			uri=uri.substring(eatPrefix.length());
		}
		StringBuilder ret = new StringBuilder();
		ret.append(basePlus);
		ret.append("local");
		ret.append("/hosts/");
		ret.append(hostId);
		ret.append("/www");
		ret.append(uri);
		return ret.toString();
	}

	public static String wwwDir(int hostId) {
		StringBuilder ret = new StringBuilder();
		ret.append(basePlus);
		ret.append("local");
		ret.append("/hosts/");
		ret.append(hostId);
		ret.append("/www");
		return ret.toString();
	}

}
