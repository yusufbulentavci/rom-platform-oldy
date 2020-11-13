package com.bilgidoku.rom.web.cagridagitma;

import com.bilgidoku.rom.gwt.araci.server.DispatchData;
import com.bilgidoku.rom.gwt.araci.server.UriWork;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.DispatchMethod;
import com.bilgidoku.rom.pg.dict.MethodInteract;
import com.bilgidoku.rom.pg.dict.RetStream;
import com.bilgidoku.rom.pg.dict.RomResource;
import com.bilgidoku.rom.pg.dict.TypeControl;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.err.SecurityError;
import com.bilgidoku.rom.type.DispatchBase;
import com.bilgidoku.rom.type.UriWorkBase;
import com.bilgidoku.rom.web.html.HtmlReply;
import com.bilgidoku.rom.web.uri.UriAnalyses;
import com.bilgidoku.rom.web.uri.UriGorevlisi;
import com.bilgidoku.rom.web.uri.Uriz;

import io.netty.handler.codec.http.HttpMethod;

public class IcCagriDagitmaGorevlisi extends GorevliDir {
	public static final int NO = 29;

	public static IcCagriDagitmaGorevlisi tek() {
		if (tek == null) {
			synchronized (IcCagriDagitmaGorevlisi.class) {
				if (tek == null) {
					tek = new IcCagriDagitmaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static IcCagriDagitmaGorevlisi tek;

	private IcCagriDagitmaGorevlisi() {
		super("IcCagriDagitma", NO);
		this.dispatchData = new DispatchData();
		this.uriWork = new UriWork();
		container = dispatchData.getTypeControl("/_/c");
	}

	private static final MC mc = new MC(IcCagriDagitmaGorevlisi.class);

	//
	// protected final static CookieService cookieService =
	// ServiceDiscovery.getService(CookieGorevlisi.tek().class);
	// protected final static AuthorizeService authService =
	// ServiceDiscovery.getService(AuthorizeService.class);
	// protected final static LocalFileService memberfileservService =
	// ServiceDiscovery.getService(LocalFileService.class);
	// protected final static DirectFileService tlosadminfileservService =
	// ServiceDiscovery.getService(DirectFileService.class);
	// protected final static ReadyMediaService readymediaService =
	// ServiceDiscovery.getService(ReadyMediaService.class);
	//
	// protected final static AuditService auditService =
	// ServiceDiscovery.getService(AuditService.class);
	//
	// protected final static PublicFileService publicfileservService =
	// ServiceDiscovery.getService(PublicFileService.class);
	// protected final static SessionFuncsService sessionfuncsService =
	// ServiceDiscovery.getService(SessionFuncsService.class);
	// protected final static RichWebService richwebService =
	// ServiceDiscovery.getService(RichWebService.class);
	// protected final static PaypalService paypalService =
	// ServiceDiscovery.getService(PaypalService.class);
	//
	// protected final static SiteFileService sitefileservService =
	// ServiceDiscovery.getService(SiteFileService.class);
	// protected final static UriService uriService =
	// ServiceDiscovery.getService(UriService.class);

	private DispatchBase dispatchData;
	private UriWorkBase uriWork;
	private TypeControl container;

	public void selfDescribe(JSONObject jo) {

	}

	

	public HtmlReply call(MethodInteract request) throws KnownError {
		destur();
		String path = request.getPath();
		// syso(path);
		try {
			DispatchMethod node = null;
			if (path.equals("/_/c")) {
				if (request.getMethod() == HttpMethod.POST) {
					String schema = request.getParam("schema", 2, 20, true);
					String table = request.getParam("table", 2, 20, true);
					node = dispatchData.getBreed(schema, table);
				} else {
					String space = request.getParam("space", 2, 20, true);
					String name = request.getParam("table", 2, 20, true);
				}
			} else {
				// UriAnalyses ua = new UriAnalyses(path, request.getMethod());
				Uriz ua = UriGorevlisi.tek().analysis(request.getHostId(), path, request.getMethod(),
						request.getDomain().getEmailDomain());
				TypeControl tc = dispatchData.getTypeControl(ua.prefix);
				if (tc == null) {
					throw new KnownError().badRequest();
				}
				request.setNet(tc.net);
				request.getSession().appendCpu(tc.getCpu());
				if (!ua.service)
					request.setRomResource(getResource(tc, request, ua));
				if (!ua.service)
					request.setResource(ua.resource);
				node = tc.getMethod(ua.method);
				if (!ua.service)
					node.methodControl.checkAccess(request.getRomResource(), request.getSession().isGuest(),
							request.getSession().getRoleMask(), request.getSession().getCid());

			}

			if (node == null) {
				throw new KnownError().badRequest();
			}

			Object resp = node.dao.call(request);
			if (resp == null) {
				throw new KnownError().badRequest();
			}
			if (resp instanceof JSONObject || resp instanceof JSONArray || resp instanceof RetStream) {
				// syso(resp.toString());
				return new HtmlReply(resp, request.getRomResource().getModifiedDate(), node.dao.getCachePeriod());
			} else {
				return null;
			}
		} catch (KnownError | NotInlineMethodException | ParameterError | SecurityError e) {
			throw err(e);
		}
	}

	private static final Astate _getResource = mc.c("getResource");

	public RomResource getResource(TypeControl tc, MethodInteract request, Uriz ua) throws KnownError {

		if (ua.iscontainer && !tc.isOne()) {
			tc = container;
		}
		_getResource.more();
		RomResource rr = null;
		try (DbThree db3 = new DbThree(tc.getResourceStatement())) {
			db3.setInt(request.getHostId());
			db3.setString(ua.resource);
			db3.executeQuery();
			if (!db3.next()) {
				throw new KnownError("HostId:" + request.getHostId() + " Resource:" + ua.resource + " Query:"
						+ tc.getResourceStatement()).notFound(ua.resource);
			}
			// container,html_file,modified_date,creation_date,owner_role
			// container,html_file,modified_date,creation_date,delegated,ownercid,gid,relatedcids,mask

			rr = new RomResource(tc, ua.resource, db3.getString(), db3.getString(), db3.getTimestamp(),
					db3.getTimestamp(), db3.getString(), db3.getString(), db3.getString(), db3.getStringArray(),
					db3.getLong(), ua.iscontainer);
			if (rr.getDelegated() != null && rr.mayNeedDelegation()) {
				// try {
				fillFromDelegated(request.getHostId(), rr.getDelegated(), rr, request.getDomain().getEmailDomain());
				// } catch (SQLException | NotFound e) {
				// throw RunTime.one.error(this, e);
				// }
			}
			return rr;
		} catch (KnownError e) {
			if (!e.isNotFound())
				_getResource.failed(e, tc, request, ua);
			throw e;
		}
	}

	private void fillFromDelegated(int hostId, String delegated, RomResource real, String emailDomain)
			throws KnownError, KnownError {
		UriAnalyses ua = new UriAnalyses(delegated, HttpMethod.GET, uriWork, emailDomain);
		TypeControl tc = dispatchData.getTypeControl(ua.prefix);
		if (tc == null) {
			throw new KnownError().badRequest();
		}
		RomResource rr;
		try (DbThree db3 = new DbThree(
				"select container,html_file,modified_date,creation_date,delegated,ownercid,gid,relatedcids,mask "
						+ "from rom.resources where host_id=? and uri=?")) {
			db3.setInt(hostId);
			db3.setString(ua.resource);
			db3.executeQuery();
			if (!db3.next())
				throw new KnownError(ua.resource).badRequest();
			// container,html_file,modified_date,creation_date,owner_role
			// container,html_file,modified_date,creation_date,delegated,ownercid,gid,relatedcids,mask

			rr = new RomResource(tc, ua.resource, db3.getString(), db3.getString(), db3.getTimestamp(),
					db3.getTimestamp(), db3.getString(), db3.getString(), db3.getString(), db3.getStringArray(),
					db3.getLong(), false);
		}

		real.delegate(rr);

		if (rr.getDelegated() != null && real.mayNeedDelegation()) {
			fillFromDelegated(hostId, rr.getDelegated(), real, emailDomain);
		}
	}

}
