package com.bilgidoku.rom.web.cagridagitma;

import java.util.Date;

import com.bilgidoku.rom.gwt.araci.server.DispatchData;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.dict.DispatchMethod;
import com.bilgidoku.rom.pg.dict.Format;
import com.bilgidoku.rom.pg.dict.RetStream;
import com.bilgidoku.rom.pg.dict.RomResource;
import com.bilgidoku.rom.pg.dict.TypeControl;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.err.SecurityError;
import com.bilgidoku.rom.type.CustomNode;
import com.bilgidoku.rom.type.DispatchBase;
import com.bilgidoku.rom.web.filecontent.SiteDosyaGorevlisi;
import com.bilgidoku.rom.web.html.HtmlGorevlisi;
import com.bilgidoku.rom.web.http.RomHttpResponse;
import com.bilgidoku.rom.web.uri.UriAnalysesDirect;
import com.bilgidoku.rom.web.uri.UriGorevlisi;
import com.bilgidoku.rom.web.uri.Uriz;

import io.netty.handler.codec.http.HttpMethod;

public class CagriDagitmaGorevlisi extends GorevliDir {
	public static final int NO = 27;

	public static CagriDagitmaGorevlisi tek() {
		if (tek == null) {
			synchronized (CagriDagitmaGorevlisi.class) {
				if (tek == null) {
					tek = new CagriDagitmaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static CagriDagitmaGorevlisi tek;

	private CagriDagitmaGorevlisi() {
		super("CagriDagitma", NO);
		this.dispatchData = new DispatchData();
		this.directDispatchData = new com.bilgidoku.rom.gwt.araci.direct.DispatchData();
	}

	private static final String RESPONSE_JS_VAR_PARAM = "outvar";


	private DispatchBase directDispatchData;
	protected DispatchBase dispatchData;

	// = new com.bilgidoku.rom.gwt.direct.DispatchData();

	
	
	public void selfDescribe(JSONObject jo) {

	}


	
	public void romHandle(RomHttpResponse request) throws KnownError {
		destur();
		try {
			String path = request.getPath();
			// syso(path);

			CustomNode rhh = dispatchData.matchCustom(path);
			if (rhh != null) {
				request.setNet(rhh.net);
				request.setResource(path);
				// request.getSession().appendCpu(rhh.getCpu());
				rhh.dao.call(request);
				return;
			}

			DispatchMethod node = null;
			if (path.equals("/_/c/new.rom")) {
				String schema = request.getParam("schema", 2, 20, true);
				String table = request.getParam("table", 2, 20, true);
				request.setResource("/_/c");
				node = dispatchData.getBreed(schema, table);
			} else {

				// UriAnalyses ua = new UriAnalyses(path, request.getMethod());
				Uriz ua = UriGorevlisi.tek().analysis(request.getHostId(), path, request.getMethod(),
						request.getDomain().getEmailDomain());

				TypeControl tc = dispatchData.getTypeControl(ua.prefix);
				if (tc == null) {
					throw new KnownError("Not found prefix:" + ua.prefix).badRequest();
				}
				request.setNet(tc.net);
				if (ua.hasResource()) {
					request.setRomResource(IcCagriDagitmaGorevlisi.tek().getResource(tc, request, ua));
					request.setResource(ua.resource);
				}
				node = tc.getMethod(ua.method);
				if (node == null) {
					throw new KnownError().badRequest();
				}
				if (ua.hasResource()) {

					if (node.methodControl == null)
						com.bilgidoku.rom.min.Sistem.errln("NULL methodControl");
					if (request.getSession() == null)
						com.bilgidoku.rom.min.Sistem.errln("NULL session");

					if (request.getMethod() == HttpMethod.GET && request.getFormat() == Format.HTML) {
						if (node.methodControl.ableToLoginCheckAccess(request.getRomResource(),
								request.getSession().isGuest(), request.getSession().getRoleMask(),
								request.getSession().getCid())) {
							request.redirectLogin();
							return;
						}
					}
					node.methodControl.checkAccess(request.getRomResource(), request.getSession().isGuest(),
							request.getSession().getRoleMask(), request.getSession().getCid());

				}
				// dispatchData.isWriting(tc) &&

				if (request.getFormat() == Format.HTML && ua.hasResource()
						&& request.getRomResource().getHtmlFile() != null) {
					HtmlGorevlisi.tek().romHandle(request);
					return;
				}

				if (ua.file && request.getFormat() == Format.HTML) {
					if (ua.iscontainer) {
						HtmlGorevlisi.tek().romHandle(request);
						return;
					}

					SiteDosyaGorevlisi.tek().romHandle(request);
					return;
				}
				Long cachePeriod = node.dao.getCachePeriod();
				if (cachePeriod != null && ua.hasResource() && request.getMethod() == HttpMethod.GET) {
					Date ifModifiedSinceDate = request.getHeaderIfModifiedSince();
					if (ifModifiedSinceDate != null) {
						long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
						RomResource rr = request.getRomResource();
						long fileLastModifiedSeconds = rr.getModifiedDate().getTime() / 1000;
						if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
							request.sendNotModified();
							return;
						}
					}
				}
			}

			if (node == null) {
				throw new KnownError().badRequest();
			}

			Object resp = node.dao.call(request);
			if (resp == null) {
				if (request.getFormat() == Format.JSON) {
					request.sendResponse("null", null, null);
					return;
				}

				throw new KnownError().notFound("");
			}

			String mime = node.methodControl.getMime();

			if (resp instanceof JSONObject || resp instanceof JSONArray) {
				if (request.getFormat() == Format.JS) {
					String outvar = request.getParam(RESPONSE_JS_VAR_PARAM, 1, 40, true);
					request.sendResponse("var " + outvar + "=" + resp.toString() + ";",
							request.getRomResource().getModifiedDate(), node.dao.getCachePeriod());
				} else {
					// syso(resp.toString());
					if (request.getRomResource() == null) {
						request.sendResponse(resp.toString(), null, null);
					} else {
						request.sendResponse(resp.toString(), request.getRomResource().getModifiedDate(),
								node.dao.getCachePeriod());
					}
				}
			} else if (resp instanceof RetStream) {
				RetStream rs = (RetStream) resp;
				request.sendFile(rs.file, rs.fileName, node.dao.getCachePeriod());
			} else if (mime != null && !mime.equals("text/html; charset=UTF-8") && resp instanceof String) {
				Date modified = null;
				RomResource romres = request.getRomResource();
				if (romres != null) {
					modified = romres.getModifiedDate();
				}
				request.sendResponse((String) resp, mime, modified, node.dao.getCachePeriod());
			} else {
				throw new RuntimeException("Unknown response type:" + resp.toString());
			}
		} catch (KnownError | NotInlineMethodException | ParameterError | SecurityError e) {
			throw err(e);
		}

	}

	
	public void directHandle(RomHttpResponse request) throws KnownError {
		destur();
		try {
			String path = request.getPath();
			// syso(path);

			CustomNode rhh = directDispatchData.matchCustom(path);
			if (rhh != null) {
				request.setNet(rhh.net);
				request.setResource(path);
				rhh.dao.call(request);
				return;
			}

			DispatchMethod node = null;

			UriAnalysesDirect ua = new UriAnalysesDirect(path, request.getMethod());

			TypeControl tc = directDispatchData.getTypeControl(ua.prefix);
			if (tc == null) {
				throw new KnownError().badRequest();
			}
			// request.getSession().appendCpu(tc.getCpu());
			request.setNet(tc.net);
			node = tc.getMethod(ua.method);
			if (node == null) {
				throw new KnownError().badRequest();
			}
			Object resp = node.dao.call(request);
			if (resp == null) {
				throw new KnownError().badRequest();
			}
			if (resp instanceof JSONObject || resp instanceof JSONArray) {
				if (request.getFormat() == Format.JS) {
					String outvar = request.getParam(RESPONSE_JS_VAR_PARAM, 1, 40, true);
					request.sendResponse("var " + outvar + "=" + resp.toString() + ";",
							request.getRomResource().getModifiedDate(), node.dao.getCachePeriod());
				} else {
					// syso(resp.toString());
					if (request.getRomResource() == null) {
						request.sendResponse(resp.toString(), null, null);
					} else {
						request.sendResponse(resp.toString(), request.getRomResource().getModifiedDate(),
								node.dao.getCachePeriod());
					}
				}
			} else if (resp instanceof RetStream) {
				RetStream rs = (RetStream) resp;
				request.sendFile(rs.file, rs.fileName, node.dao.getCachePeriod());
			} else {
				throw new KnownError("Unknown response type:" + resp.toString()).badRequest();
			}
		} catch (KnownError | NotInlineMethodException | ParameterError | SecurityError e) {
			throw err(e);
		}

	}

}
