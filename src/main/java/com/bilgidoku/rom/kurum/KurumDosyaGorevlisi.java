package com.bilgidoku.rom.kurum;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.cokluortam.twod.ResimGorevlisi;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.ilk.util.HostingUtils;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.shared.Utils;
import com.bilgidoku.rom.shared.err.KnownError;

public class KurumDosyaGorevlisi extends GorevliDir {

public static final int NO=22;
	
	public static KurumDosyaGorevlisi tek(){
		if(tek==null) {
			synchronized (KurumDosyaGorevlisi.class) {
				if(tek==null) {
					tek=new KurumDosyaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static KurumDosyaGorevlisi tek;
	private KurumDosyaGorevlisi() {
		super("KurumDosya", NO);
	}
	
	// private final String tempDir;
	// private final String commonsDir;
	// private final String localDirPlus;


	private static final MC mc = new MC(KurumDosyaGorevlisi.class);
	private static final String DELETE_SUFFIX = ".romdeleted_";




	private String realFile(int hostId, String uri) {
		if (!uri.startsWith("/"))
			uri = "/" + uri;

		return DbfsGorevlisi.tek().wwwPath(hostId, "/f", uri);
		// return HostingUtils.contentPath(this.localDirPlus, hostId,
		// HostingUtils.SITE_SUFFIX, uri);
	}

	private String deletedFile(int hostId, String file, Long ts) {
		return realFile(hostId, file) + DELETE_SUFFIX + ts;
	}

	private static final Astate fnf = mc.c("file-not-found");

	private String realFileDirectory(int hostId, String file) throws KnownError {
		String rf = realFile(hostId, file);
		File f = new File(rf);
		Assert.beFalse(f.isDirectory());
		return f.getParent();
	}

	private String newDeletedFile(int hostId, String file) {
		return realFile(hostId, file) + DELETE_SUFFIX + System.currentTimeMillis();
	}

	private static final Astate fd = mc.c("file-delete");
	private static final Astate fcd = mc.c("file-can-not-deleted");


	public File deleteFile(int hostId, String file, boolean errorIfNot) throws KnownError {
		destur();
		fd.more();
		// Get real file
		String hfile = realFile(hostId, file);
		this.realFileChanged(hfile);
		File f = new File(hfile);

		File deleted = new File(newDeletedFile(hostId, file));
		// Rename as deleted
		if (f.renameTo(deleted)) {
			return deleted;
		}

		Assert.beFalse(errorIfNot);
		return null;
	}

	private static final Astate fu = mc.c("file-undel-failed");
	private static final Astate fuf = mc.c("file-undel");


	public boolean undeleteFile(int hostId, String file, boolean errorIfNot) throws KnownError {
		destur();
		fu.more();

		Long rf = getRecentDeletedFile(hostId, file);
		if (rf == null) {
			Assert.beFalse(errorIfNot);
			return false;
		}

		// No deletion if not exists
		realDelete(hostId, file, false);
		String realFile = realFile(hostId, file);
		realFileChanged(realFile);

		if (!realRename(deletedFile(hostId, file, rf), realFile(hostId, file), errorIfNot)) {
			return false;
		}

		return true;
	}

	private boolean realRename(String from, String to, boolean errorIfNot) throws KnownError {
		if (new File(from).renameTo(new File(to))) {
			return true;
		}
		Assert.beFalse(errorIfNot);
		return false;
	}

	private boolean realDelete(int hostId, String file, boolean errorIfNot) throws KnownError {
		if (new File(realFile(hostId, file)).delete()) {
			return true;
		}
		Assert.beFalse(errorIfNot);

		return false;
	}

	public List<Long> getDeletedFiles(int hostId, String file) throws KnownError {

		// Find path of deleted file
		File f = new File(realFileDirectory(hostId, file));

		// Prefix of deleted file
		final String dprefix = new File(file).getName() + DELETE_SUFFIX;

		// Find files in directory that matches deleted file prefix
		File[] fs = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(dprefix);
			}
		});

		// Put all deleted files into list
		List<Long> dfiles = new LinkedList<Long>();
		for (File fd : fs) {
			String fn = fd.getName().substring(dprefix.length());
			dfiles.add(Long.parseLong(fn));
		}
		return dfiles;
	}

	public Long getRecentDeletedFile(int hostId, String file) throws KnownError {
		List<Long> fs = getDeletedFiles(hostId, file);
		Long max = Long.MIN_VALUE;
		for (Long dt : fs) {
			if (dt > max) {
				max = dt;
			}
		}
		if (max == Long.MIN_VALUE)
			return null;
		return max;
	}

//	@Override
//	public File getCommonFile(String file, boolean checkExistence, boolean errorIfNotExist) throws KnownError {
//		destur();
//		if (file.contains("../")) {
//			throw new KnownError().notFound(file);
//		}
//		String commonsDir;
//		if (file.startsWith("/_public/")) {
//			commonsDir = publicWwwDir;
//		} else if (file.startsWith("/_local/")) {
//			commonsDir = localWwwDir;
//		} else {
//			throw new KnownError().notFound(file);
//		}
//		File f = new File(commonsDir + file);
//		Assert.beFalse(!f.exists() && checkExistence && errorIfNotExist);
//		return f;
//
//	}


	public File getFile(int hostId, String file, boolean checkExistence, boolean errorIfNotExist) throws KnownError {
		destur();
		File f = new File(realFile(hostId, file));
		Assert.beFalse(!f.exists() && checkExistence && errorIfNotExist);
		return f;

	}

	private static final Astate _mvFile = mc.c("mvFile");


	public boolean mvFile(int hostId, String from, String to, boolean errorIfNotExist) throws KnownError {
		destur();
		_mvFile.more();
		try {
			String toFileStr = realFile(hostId, to);
			File ft = new File(toFileStr);
			if (ft.exists()) {
				if (errorIfNotExist) {
					throw new KnownError("Destination already exist" + toFileStr);
				}

				return false;
			}

			File ff = new File(realFile(hostId, from));
			if (!ff.exists()) {
				if (errorIfNotExist) {
					throw new KnownError("Source not exist;" + ff.toString());
				}
				return false;
			}

			String parent = null;
			String filename = null;
			if (!ff.isDirectory()) {
				parent = ff.getParent();
				filename = ff.getName();
			}

			if (!ff.renameTo(ft)) {
				if (errorIfNotExist) {
					throw new KnownError("Can not be renamed; " + ff.toString() + " => " + toFileStr);
				}
				return false;
			}

			if (ft.isDirectory())
				return true;

			File parentDir = new File(parent);
			final String deletedFilePrefix = filename + DELETE_SUFFIX;
			File[] deletedFiles = parentDir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.startsWith(deletedFilePrefix);
				}
			});

			for (File file : deletedFiles) {
				String ts = file.getName().substring(deletedFilePrefix.length());
				if (!file.renameTo(new File(toFileStr + DELETE_SUFFIX + ts))) {
					if (errorIfNotExist) {
						throw new KnownError("Failed to rename deleted file to " + toFileStr + DELETE_SUFFIX + ts);
					}
				}
				this.realFileChanged(file.getPath());
			}

			return true;
		} catch (KnownError e) {
			_mvFile.failed(e, hostId, from, to, errorIfNotExist);
			throw e;
		}
	}

	private static final Astate fmkdir = mc.c("file-mkdir");
	private static final Astate fmkdirError = mc.c("file-mkdir-error");


	public boolean mkdir(int hostId, String file, boolean errorIfNot) throws KnownError {
		destur();
		fmkdir.more();
		File f = new File(realFile(hostId, file));
		if (!f.exists()) {
			if (f.mkdir()) {
				return true;
			}
		}
		Assert.beFalse(errorIfNot);

		return false;
	}

	private static final Astate frmdir = mc.c("file-rmdir");
	private static final Astate frmdirError = mc.c("file-rmdir-error");


	public boolean rmdir(int hostId, String file, boolean errorIfNot) throws KnownError {
		destur();
		frmdir.more();
		File f = new File(realFile(hostId, file));
		if (f.exists()) {
			if (f.delete()) {
				return true;
			}
		}

		Assert.beFalse(errorIfNot);

		return false;
	}


	public String realPath(int hostId, String name) throws KnownError {
		destur();
		return realFile(hostId, name);
	}

	// private String getIntraHostPath(int hostId) {
	// return hostsFolder + hostId;
	// }
	//
	// private String getInterHostPath(int adminHostId) {
	// int publicHostId = adminHostId + 1;
	// return hostsFolder + publicHostId;
	// }
	//
	// private String getPublishTempPath(int adminHostId) {
	// int publicHostId = adminHostId + 1;
	// return tempDir + "/" + publicHostId;
	// }

	private static final Astate publichostdelete = mc.c("public-host-delete");
	private static final Astate copyadminhost = mc.c("copy-admin-host");
	private static final Astate publishHost = mc.c("publish-host-req");

	private static final Astate _publishHost = mc.c("publishHost");


	public void publishHost(Integer hostId) throws KnownError {
		destur();

		publishHost.more();

		int intraHostId = HostingUtils.hostIdIntra(hostId);
		int interHostId = HostingUtils.hostIdInter(hostId);

		String intraHostLocation = DbfsGorevlisi.tek().wwwDir(intraHostId);
		String interHostLocation = DbfsGorevlisi.tek().wwwDir(interHostId);

		File interHost = new File(interHostLocation);
		File intraHost = new File(intraHostLocation);
		if (interHost.exists()) {
			try {
				FileUtils.deleteDirectory(interHost);
			} catch (IOException e) {
				_publishHost.failed(e, intraHostId);
				throw err(e);
			}
		}

		try {
			FileUtils.copyDirectory(intraHost, interHost, new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return !pathname.getName().endsWith(DELETE_SUFFIX);
				}
			});
		} catch (IOException e) {
			_publishHost.failed(e, intraHostId);
			throw err(e);
		}
	}

	private static final Astate _restoreHost = mc.c("restoreHost");


	public void restoreHost(Integer hostId) throws KnownError {
		destur();

		_restoreHost.more();

		int intraHostId = HostingUtils.hostIdIntra(hostId);
		int interHostId = HostingUtils.hostIdInter(hostId);

		String intraHostLocation = DbfsGorevlisi.tek().wwwDir(intraHostId);
		String interHostLocation = DbfsGorevlisi.tek().wwwDir(interHostId);

		File interHost = new File(interHostLocation);
		File intraHost = new File(intraHostLocation);
		if (intraHost.exists()) {
			try {
				FileUtils.deleteDirectory(intraHost);
			} catch (IOException e) {
				_restoreHost.failed(e, intraHostId);
				throw err(e);
			}
		}

		try {
			FileUtils.copyDirectory(interHost, intraHost, new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return !pathname.getName().endsWith(DELETE_SUFFIX);
				}
			});
		} catch (IOException e) {
			_restoreHost.failed(e, intraHostId);
			throw err(e);
		}
	}


	public String nameFix(String name) {
		return Utils.nameFix(name);
	}


	public void realFileChanged(String fileName) {
		
		File thumbDir = new File(fileName);
		thumbDir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".romthumb")){
					new File(dir,name).delete();
				}
				return false;
			}
		});

		if (fileName.endsWith(".png")) {
			File f = new File(fileName);
			if (f.exists()) {
				try {
					ResimGorevlisi.tek().compressImage(f, null);
				} catch (KnownError e) {
					Sistem.printStackTrace(e, "HostingFileService: RealFileChanged" + fileName);
				}
			}
		}

	}

}
