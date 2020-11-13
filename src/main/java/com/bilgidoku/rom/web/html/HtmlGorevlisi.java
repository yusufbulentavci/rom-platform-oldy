package com.bilgidoku.rom.web.html;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dict.Format;
import com.bilgidoku.rom.pg.dict.RetStream;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.MinRequest;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RequestCreator;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.shared.code.CodeRepo;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.expr.Evaluator;
import com.bilgidoku.rom.shared.state.Scope;
import com.bilgidoku.rom.shared.state.State;
import com.bilgidoku.rom.web.cagridagitma.IcCagriDagitmaGorevlisi;
import com.bilgidoku.rom.web.db.DbDao;
import com.bilgidoku.rom.web.db.InfoCoder;
import com.bilgidoku.rom.web.dbop.Info;
import com.bilgidoku.rom.web.http.RomHttpResponse;
import com.bilgidoku.rom.web.http.session.AppSession;

/**
 * 
 * Give response to resource requests for Format.html format Response types:
 * thin client(a), rich client(edit,show,showdebug)
 * 
 * General: Resource should have htmlapp widget field Meta datas are generated
 * with use of host info table and resource/content lang fields "Note" json
 * object is generated like meta datas, given to state engine
 * 
 * thin client (a): Resource call is made. Create Runner Runner generates inner
 * html OpenGraphGenerator generates meta data HtmlApp generates response html
 * request.sendResponse
 * 
 * rich client: Does not make resource call HtmlApp generates response html
 * request.sendResponse State: stateless
 * 
 * Use: InlineDispatchService: get resource for requested item Runner Format
 * HtmlApp OpenGraphGenerator RomHttpResponse
 * 
 * 
 * Procedures: edit html:
 * 
 * 
 * 
 * Rules:
 * 
 * @author avci
 */
public class HtmlGorevlisi extends GorevliDir {
	public static final int NO=31;
		
		public static HtmlGorevlisi tek(){
			if(tek==null) {
				synchronized (HtmlGorevlisi.class) {
					if(tek==null) {
						tek=new HtmlGorevlisi();
						tek.giris();
					}
				}
			}
			return tek;
		}
		
		static HtmlGorevlisi tek;
		private HtmlGorevlisi() {
			super("Html", NO);
			Portable.one = new PortableServer();
		}
	

	final static private MC mc = new MC(HtmlGorevlisi.class);

	
	// final static private SiteService siteService =
	// ServiceDiscovery.getService(SiteService.class);

	final static private Astate rhc = mc.c("app-rom-handle-count");

	// final static RomErrorResolver errorResolver = new RomErrorResolver();

	

	public void selfDescribe(JSONObject jo) {

	}

	public void romHandle(RomHttpResponse request) throws KnownError {
		destur();

		rhc.more();

		StringBuilder sb;
		Long cache;
		Date ts;
		try {
			
			
			int hostId = request.getHostId();
//			int intraHostId=HostingUtils.hostIdInter(hostId);
//			
//			Org org=getOrg(intraHostId);
			
			
			String htmlFile = request.getRomResource().getHtmlFile();
			// siteService.getHtmlFileOfWriting(hostId, request.getUri());
			if (htmlFile == null)
				throw new KnownError().notFound("Html file not found");

			
			
			
			String lang = request.getReqLang();

			Info info = DbDao.getInfo(hostId, null, lang);
			lang = info.langcodes[0];
			request.setReqLang(lang);

			String favIcon = info.browser_icon == null ? "/_static/img/etc/favicon.ico" : info.browser_icon;

			String path = request.getUri();
			String topLevelDomain = request.getDomain().getTopLevel();
			String domainName = request.getDomain().getDomainName();
			// String mainLang = request.getDomain().getMainLang();

			JSONArray availableLangs = new JSONArray();
			for (String l : info.langcodes) {
				availableLangs.put(l);
			}

			AppSession ses = (AppSession) request.getSession();

			JSONObject note = new JSONObject();
			note.put("m", ses.isMobile());
			note.put("bw", ses.screenWidth());
			note.put("ua", ses.getUserAgent().toJson());
			note.put("lang", lang);
			note.put("topd", topLevelDomain);
			note.put("domain", domainName);
			note.put("uri", request.getRawUri());
			note.put("path", path);

			note.put("langs", availableLangs);

			String self = path;
			sb = new StringBuilder();
			boolean serverDebug = false;

			String romRender=null;
			try {
				romRender = request.getParam("rom.render");
				String bw = request.getParam("rom.bw");
				if (bw != null) {
					try {
						note.put("bw", Integer.parseInt(bw));
					} catch (Exception e) {
					}
				}
			} catch (ParameterError e) {
			}
			cache = null;
			ts = null;
			if (romRender != null) {
				sb.setLength(0);
				switch (romRender) {
				case "client":
					HtmlApp.setUpRenderClient(sb, info.style, self, htmlFile, note.toString(), lang, favIcon);
					break;
				case "client.debug":
					HtmlApp.setUpRenderClientDebug(sb, info.style, self, htmlFile, note.toString(), lang, favIcon);
					break;
				case "htmledit":
					HtmlApp.setUpRenderEditHtml(sb, info.style, self, htmlFile, note.toString(), lang, false, favIcon);
					break;
				case "htmledit.debug":
					HtmlApp.setUpRenderEditHtml(sb, info.style, self, htmlFile, note.toString(), lang, true, favIcon);
					break;
				case "server.debug":
					serverDebug=true;
					break;
					
				default:
					throw new KnownError("Unknown rom.render parameter:"+romRender);

				}
			}
			if(romRender==null || serverDebug)
			{
				request.setFormat(Format.JSON);
				HtmlReply hrep = IcCagriDagitmaGorevlisi.tek().call(request);
				cache = hrep.cache;
				ts = hrep.ts;
				
				if(hrep.object instanceof RetStream){
					RetStream rs = (RetStream) hrep.object;
					String fileName=DbfsGorevlisi.tek().noDbfs(rs.file.getName());
					request.sendFile(rs.file, fileName, hrep.cache);
					request.getSession().visiting(self);
					return;
				}
				
				
				JSONObject item = null;
				if (hrep.object instanceof JSONArray) {
					item = new JSONObject();
					item.put("list", (JSONArray) hrep.object);
				} else {
					item = (JSONObject) hrep.object;
				}
				if (item == null)
					return;
				// JSONObject k = item.getJSONObject("viewy");

				// appService.render(debug, request.getSession(), sb, item,
				// info, ao, wo, sa, self, request.getSession().getUserAgent(),
				// trans);

				Runner r = createRunner(request, htmlFile, info, item, note);

				String openGraph = OpenGraphGenerator.generate(topLevelDomain, path, lang,
						request.getSession().getCountry(), info, item);

				HtmlApp.setupRenderServer(serverDebug, r.render(), r.store().toString(), openGraph, favIcon, sb);
				request.getSession().visiting(self);
			}
			request.sendResponse(sb, ts, cache);
		} catch (JSONException e) {
			throw err(e);
		} catch (RunException e) {
			throw err(e);
		} catch (UnsupportedEncodingException e) {
			throw err(e);
		}

	}

	private Runner createRunner(RomHttpResponse request, String htmlFile, Info info, JSONObject item, JSONObject note)
			throws JSONException, RunException, KnownError {
		int hostId = request.getHostId();
		JSONObject infoJson = (JSONObject) new InfoCoder().encode(info);
		CodeRepo cr = new CodeRepo(objAdapter(getWidget(hostId)));
		Runner r = new Runner(new RequestCreator() {

			@Override
			public MinRequest create(Runner runner) {
				return request;
			}
		}, objAdapter(infoJson), objAdapter(info.logo_img.getObject()), objAdapter(info.text_font.getObject()),
				objAdapter(info.palette.getObject()), arrayAdapter(getStyle(hostId, info.style)), objAdapter(item), cr,
				objAdapter(getTrans(hostId, request.getReqLang())),
				// objAdapter(k),
				htmlFile, request.getDomain(), request.getSession(), arrayAdapter(getStyleCommon(hostId)),
				objAdapter(note), request.getOParams());
		return r;
	}

	private com.bilgidoku.rom.shared.json.JSONObject objAdapter(JSONObject app) {
		return new com.bilgidoku.rom.shared.json.JSONObject(app);
	}

	private com.bilgidoku.rom.shared.json.JSONObject parseObjAdapter(JSONObject app) {
		return new com.bilgidoku.rom.shared.json.JSONObject(app);
	}

	private com.bilgidoku.rom.shared.json.JSONArray arrayAdapter(JSONArray app) {
		return new com.bilgidoku.rom.shared.json.JSONArray(app);
	}

	private static final Astate wnf = mc.c("widget-not-found");

	public JSONObject getWidget(int hostId) throws KnownError, KnownError, JSONException {
		try (DbThree db = new DbThree("select codes from rom.widgets_get(?, ?)")) {
			db.setInt(hostId);
			db.setString("/_/widgets");
			db.executeQuery();
			db.checkedNext();
			String codes = db.getString();
			return new JSONObject(codes);
		}
	}

	private static final Astate snf = mc.c("style-not-found");

	private JSONArray getStyle(int hostId, String uri) throws KnownError, JSONException, KnownError {
		try (DbThree db = new DbThree("select codes from rom.styles_get(?, ?)")) {
			db.setInt(hostId);
			db.setString(uri);
			db.executeQuery();
			db.checkedNext();
			String codes = db.getString();
			if (codes == null)
				return new JSONArray();
			return new JSONArray(codes);
		}
	}

	private JSONArray getStyleCommon(int hostId) throws KnownError, KnownError, JSONException {
		try (DbThree db = new DbThree("select codes from rom.styles_get(?, ?)")) {
			db.setInt(hostId);
			db.setString("/_/styles/common");
			db.executeQuery();
			db.checkedNext();

			String codes = db.getString();
			if (codes == null)
				return new JSONArray();
			return new JSONArray(codes);
		}
	}

	
	private JSONObject getTrans(int hostId, String lang) throws KnownError, KnownError, JSONException {
		try (DbThree db = new DbThree("select title[1]::text from rom.trans_get(?,?,?::rom.langs, ?::rom.langs)")) {
			db.setInt(hostId);
			db.setString("/_/_trans");
			db.setString(lang);
			db.setString(lang);
			db.executeQuery();
			db.checkedNext();

			return new JSONObject(db.getString());
		}
	}


	public String evaluate(RomHttpResponse request, String text) throws RunException {
		Scope scope = new Scope(request, new State(), new com.bilgidoku.rom.shared.json.JSONObject());
		return Evaluator.evaluateText(text, scope);
	}

}
