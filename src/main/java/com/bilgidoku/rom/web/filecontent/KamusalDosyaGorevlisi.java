package com.bilgidoku.rom.web.filecontent;

import java.io.File;
import java.util.Date;

import com.bilgidoku.rom.cokluortam.twod.ResimGorevlisi;
import com.bilgidoku.rom.ilk.gorevli.Dir;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.web.http.RomHttpHandler;
import com.bilgidoku.rom.web.http.RomHttpResponse;

@HttpCallServiceDeclare(uri = "/_public", name = "KamusalDosya", custom = true, paket="com.bilgidoku.rom.web.filecontent")
public class KamusalDosyaGorevlisi extends GorevliDir implements FileCallHandler {
	public static final int NO = 35;

	public static KamusalDosyaGorevlisi tek() {
		if (tek == null) {
			synchronized (KamusalDosyaGorevlisi.class) {
				if (tek == null) {
					tek = new KamusalDosyaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static KamusalDosyaGorevlisi tek;

	private KamusalDosyaGorevlisi() {
		super("KamusalDosya", NO);
		this.prefix = "/_public";
		this.prefixPlus = prefix + "/";

		this.directoryPath = Dir.WwwDir + this.prefix;
	}

	final static private MC mc = new MC(KamusalDosyaGorevlisi.class);

	final static private Astate uriError = mc.c("file-uri-error");
	final static private Astate fileNotFound = mc.c("file-not-found");
	final static private Astate directoryRequested = mc.c("directory-requested");

	private final String directoryPath;
	private String prefix;

	private String prefixPlus;

	@Override
	public void kur() throws KnownError {
		String dir = Dir.WwwDir + "/_public";
//		Dir.emptyDir(dir);		
//		ClasspathManager.getOne().cpDir("hill-client-public", null, dir);
	}

	public void selfDescribe(JSONObject jo) {
	}

	private static final Astate _romHandle = mc.c("romHandle");

	public void romHandle(RomHttpResponse request) throws KnownError {
		destur();
		_romHandle.more();

		try {

			String requestPath = request.getPath();
//			Sistem.outln(requestPath);
			if (!requestPath.startsWith(prefixPlus)) {
				fileNotFound.more();
				throw new KnownError(request.getPath()).notFound(request.getPath());
			}
			// Decode the path.
			String uri = Genel.sanitizingUri(requestPath);

			// Sistem.outln("uri:"+uri);
			if (prefix != null) {
				uri = uri.substring(prefix.length());
			}
			// Sistem.outln("uri:"+uri);
			String path = directoryPath + uri;

			// Sistem.outln("path:"+path);
			if (path == null) {
				uriError.more();
				throw new KnownError().forbidden();
			}

			File file = new File(path);
			if (file.isHidden() || !file.exists()) {
				fileNotFound.more();
				throw new KnownError(request.getPath()).notFound(request.getPath());
			}
			if (!file.isFile()) {
				directoryRequested.more();
				throw new KnownError("Not a file:" + request.getPath()).forbidden();
			}

			String thumb = request.getParam("romthumb");

			if (thumb != null && thumb.length() == 1) {
				char t = thumb.charAt(0);
				File thumbFile = new File(file.getPath() + "." + thumb + ".romthumb");
				if (thumbFile.exists()) {
					request.sendFile(thumbFile, null);
					return;
				}
				ResimGorevlisi.tek().scale(file, thumbFile, t);
				request.sendFile(thumbFile, null);
				return;
			}

			if (file.getName().endsWith(".nocache.js")) {
				request.sendFile(file, null);
				return;
			}

			// // Cache Validation
			Date ifModifiedSinceDate = request.getHeaderIfModifiedSince();
			if (ifModifiedSinceDate != null) {
				long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
				long fileLastModifiedSeconds = file.lastModified() / 1000;
				if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
					request.sendNotModified();
					return;
				}
			}

			// if(handleSpecial(request, file)){
			// return;
			// }

			// if(path.endsWith(".rom")){
			// String text = FileUtils.readFileToString(file);
			// try {
			// text=htmlService.evaluate(request, text);
			// } catch (RunException e) {
			// text="error";
			// Sistem.printStackTrace(e);
			// }
			// request.sendResponse(text, new Date(file.lastModified()), 60L);
			// return;
			// }
			request.sendFile(file, 60 * 60 * 24L);
		} catch (KnownError e) {
			// _romHandle.failed(e,request);

			throw e;
		} catch (ParameterError e) {
			throw new KnownError(e);
		}
	}

	@Override
	public RomHttpHandler getCustomService() {
		return this;
	}

}
