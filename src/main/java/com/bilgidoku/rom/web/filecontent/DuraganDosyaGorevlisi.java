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
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.web.http.RomHttpHandler;
import com.bilgidoku.rom.web.http.RomHttpResponse;

/**
 * A simple handler that serves incoming HTTP requests to send their respective
 * HTTP responses. It also implements {@code 'If-Modified-Since'} header to take
 * advantage of browser cache, as described in
 * <a href="http://tools.ietf.org/html/rfc2616#section-14.25">RFC 2616</a>.
 * 
 * <h3>How Browser Caching Works</h3>
 * 
 * Web browser caching works with HTTP headers as illustrated by the following
 * sample:
 * <ol>
 * <li>Request #1 returns the content of <code>/file1.txt</code>.</li>
 * <li>Contents of <code>/file1.txt</code> is cached by the browser.</li>
 * <li>Request #2 for <code>/file1.txt</code> does return the contents of the
 * file again. Rather, a 304 Not Modified is returned. This tells the browser to
 * use the contents stored in its cache.</li>
 * <li>The server knows the file has not been modified because the
 * <code>If-Modified-Since</code> date is the same as the file's last modified
 * date.</li>
 * </ol>
 * 
 * <pre>
 * Request #1 Headers
 * ===================
 * GET /file1.txt HTTP/1.1
 * 
 * Response #1 Headers
 * ===================
 * HTTP/1.1 200 OK
 * Date:               Tue, 01 Mar 2011 22:44:26 GMT
 * Last-Modified:      Wed, 30 Jun 2010 21:36:48 GMT
 * Expires:            Tue, 01 Mar 2012 22:44:26 GMT
 * Cache-Control:      private, max-age=31536000
 * 
 * Request #2 Headers
 * ===================
 * GET /file1.txt HTTP/1.1
 * If-Modified-Since:  Wed, 30 Jun 2010 21:36:48 GMT
 * 
 * Response #2 Headers
 * ===================
 * HTTP/1.1 304 Not Modified
 * Date:               Tue, 01 Mar 2011 22:44:28 GMT
 * 
 * </pre>
 */

@HttpCallServiceDeclare(uri = "/_static", name = "DuraganDosya", custom = true, paket="com.bilgidoku.rom.web.filecontent")
public class DuraganDosyaGorevlisi extends GorevliDir implements FileCallHandler {
	public static final int NO = 38;

	public static DuraganDosyaGorevlisi tek() {
		if (tek == null) {
			synchronized (DuraganDosyaGorevlisi.class) {
				if (tek == null) {
					tek = new DuraganDosyaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static DuraganDosyaGorevlisi tek;

	private DuraganDosyaGorevlisi() {
		super("DuraganDosya", NO);
		this.prefix = "/_static";
		this.prefixPlus = prefix + "/";

		this.directoryPath = Dir.WwwDir + prefix;

	}

	final static private MC mc = new MC(DuraganDosyaGorevlisi.class);

	final static private Astate uriError = mc.c("file-uri-error");
	final static private Astate fileNotFound = mc.c("file-not-found");
	final static private Astate guestRequestMemberFile = mc.c("guest-requested-member-file");
	final static private Astate directoryRequested = mc.c("directory-requested");

	private final String directoryPath;
	private String prefix;

	private String prefixPlus;

	@Override
	public void selfDescribe(JSONObject jo) {
	}

	private static final Astate _romHandle = mc.c("romHandle");

	@Override
	public void romHandle(RomHttpResponse request) throws KnownError {
		destur();
		_romHandle.more();

		try {

			String requestPath = request.getPath();
			if (!requestPath.startsWith(prefixPlus)) {
				fileNotFound.more();
				throw new KnownError(request.getPath()).notFound(request.getPath());
			}
			// Decode the path.
			String uri = Genel.sanitizingUri(requestPath);

			if (prefix != null) {
				uri = uri.substring(prefix.length());
			}
			String path = directoryPath + uri;

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
