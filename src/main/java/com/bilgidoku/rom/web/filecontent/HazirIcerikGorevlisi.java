package com.bilgidoku.rom.web.filecontent;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.cokluortam.CokluOrtamGorevlisi;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.internetapi.InternetApiGorevlisi;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.kurum.Media;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.web.http.RomHttpHandler;
import com.bilgidoku.rom.web.http.RomHttpResponse;
import com.bilgidoku.rom.web.richweb.InternetGorevlisi;

@HttpCallServiceDeclare(uri = "/_rm", name = "HazirIcerik", custom = true, paket="com.bilgidoku.rom.web.filecontent")
public class HazirIcerikGorevlisi extends GorevliDir implements  FileCallHandler{

	public static final int NO=36;
		
		public static HazirIcerikGorevlisi tek(){
			if(tek==null) {
				synchronized (HazirIcerikGorevlisi.class) {
					if(tek==null) {
						tek=new HazirIcerikGorevlisi();
						tek.giris();
					}
				}
			}
			return tek;
		}
		
		static HazirIcerikGorevlisi tek;
		private HazirIcerikGorevlisi() {
			super("HazirIcerik", NO);
		}

	final static private MC mc = new MC(HazirIcerikGorevlisi.class);

//	final static private String commonsDir = ps.getString("http.service.dir.commons");
//	final static private String directoryPath = commonsDir + ps.getString("http.service.path.preparedMedia") + "/";
//	RunTime.getFileRoot()+"/var/www-commons"

	private final Map<String, String> thumbs = new HashMap<String, String>();

	@Override
	public void selfDescribe(JSONObject jo) {
		jo.safePut("thumbcount", thumbs.size());
	}
	

	final static private Astate uriError = mc.c("file-uri-error");
	final static private Astate fileNotFound = mc.c("file-not-found");
	final static private Astate directoryRequested = mc.c("directory-requested");

	private static final Astate _romHandle=mc.c("romHandle");

	@Override
	public void romHandle(RomHttpResponse request) throws KnownError{
		destur();
		_romHandle.more();
		// if (htmlService == null) {
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// Sistem.printStackTrace(e);
		// }
		// }

		// If buy failed, give error, do not show img

		File file;
		try {
			String uri = request.getPath();

			// if (uri.contains('/' + ".") || uri.contains("." + '/') ||
			// uri.startsWith(".") || uri.endsWith(".")) {
			// throw new
			// RomRequestException(HttpResponseStatus.BAD_REQUEST,"Bad path");
			// }

			if (uri.endsWith("thumbs.rom")) {
				String cat = request.getParam("cat");
				String resp = thumbs.get(cat);
				if (resp == null) {
					throw new KnownError().notFound(uri);
				}

				request.sendResponse(resp, new Date(), 60 * 60 * 24L);
				return;
			}

			// Which media of fotolia is to be used, find out id
			ReadyMediaUrl rmu = parseFileName(uri);

			if (InternetGorevlisi.tek().isPublic(rmu.pr, rmu.pid)) {
				file = CokluOrtamGorevlisi.tek().imageFile(rmu.pr, rmu.pid, rmu.format, rmu.size);
			} else {
				Media m = KurumGorevlisi.tek().getMedia(request.getHostId(), rmu.pr, rmu.pid);
				if (m == null) {
					// We do not have it
					if (request.getDomain().isIntra()) {
						// If no and in intra, show sketch img
						file = CokluOrtamGorevlisi.tek().sketchFile(rmu.pr, rmu.pid, rmu.format);
					} else {
						// If no and in inter, try to buy it
						InternetGorevlisi.tek().buyMedia(request.getHostId(), null, rmu.pr, rmu.pid);
						file = CokluOrtamGorevlisi.tek().imageFile(rmu.pr, rmu.pid, rmu.format, rmu.size);
					}
				} else {
					// We have it
					file = CokluOrtamGorevlisi.tek().imageFile(rmu.pr, rmu.pid, rmu.format, rmu.size);
				}
			}

			if (file.isHidden() || !file.exists()) {
				fileNotFound.more();
				throw new KnownError().notFound(uri);
			}
			if (!file.isFile()) {
				directoryRequested.more();
				throw new KnownError().forbidden();
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
			request.sendFile(file, 60 * 60 * 24L);
		} catch (KnownError e) {
			_romHandle.failed(e,request);
			throw e;
		} catch (ParameterError e) {
			_romHandle.failed(e,request);
			throw new KnownError(e).badRequest();
		}

	}

	@Override
	public RomHttpHandler getCustomService() {
		return this;
	}

	// "/ali/fotolia_123456_t_1.jpeg"
	static private Pattern uriParser = Pattern.compile("^/([a-z_]+)/([0-9]+)_([a-z0-9]+)(_[tsmlxo])?.([a-z]+)");
	// static final int URI = 0;
	// static final int PROVIDER = 1;
	// static final int ID = 2;
	// static final int SIZE = 3;
	// static final int FORMAT = 4;

	final static private Astate x1 = mc.c("parsefilename-failed");

	public static ReadyMediaUrl parseFileName(String uri) throws KnownError  {
		Matcher k = uriParser.matcher(uri);
		if (!k.find()) {
			x1.more();
			throw new KnownError(uri);
		}
		ReadyMediaUrl rmu = new ReadyMediaUrl(Integer.parseInt(k.group(2)), k.group(3), (k.group(4) == null) ? 'o' : k
				.group(4).charAt(1), k.group(5));

		return rmu;
	}
	
}
