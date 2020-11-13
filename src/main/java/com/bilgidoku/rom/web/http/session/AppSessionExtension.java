package com.bilgidoku.rom.web.http.session;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.HostingUtils;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryMinute;
import com.bilgidoku.rom.session.IpStat;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.session.SessionExtension;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.analytics.useragent.UserAgent;
import com.bilgidoku.rom.web.cookie.CookieGorevlisi;
import com.bilgidoku.rom.web.cookie.ParseCookie;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.PushCommand;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtDlg;
import com.bilgidoku.rom.yerel.YerelGorevlisi;

public class AppSessionExtension extends SessionExtension implements EveryMinute {
	private static final MC mc = new MC(AppSessionExtension.class);

	public static final String APP = "app";

	private final Map<Integer, AppHostSession> hostSessions = new ConcurrentHashMap<Integer, AppHostSession>();

	private AppSessionExtension() {
		super(APP);
		KosuGorevlisi.tek().waitMin(this);
	}
	
	static AppSessionExtension one;
	public static AppSessionExtension one() {
		if(one==null)
			one=new AppSessionExtension();
		return one;
	}

	private static final Astate _getSession = mc.c("getSession");

	public JSONObject selfDescribe() {
		JSONObject jo = new JSONObject();
//
//		for (Entry<Integer, AppHostSession> it : hostSessions.entrySet()) {
//			jo.safePut(it.getKey() + "", it.getValue().selfDescribe());
//		}

		return jo;
	}

	public AppSession getAppSession(RomDomain domain, InetSocketAddress ipAddress, String cookieHeader,
			String userAgent, String referrer) throws KnownError, SessionIdGenerationError {

		_getSession.more();

		try {
			ParseCookie cookie = (cookieHeader == null) ? null
					: CookieGorevlisi.tek().parse(domain.getDomainName(), cookieHeader);

			int hostId = domain.getIntra();
			AppHostSession hostSession = getCreateHostSession(hostId);

			if (cookie != null && cookie.getSid() != null) {
				AppSession session = hostSession.getSessionsBySid(cookie.getSid());
				if (session != null) {

//					if (cookie.getUserName() != null && !cookie.getUserName().equals(session.getCookieUser())) {
//
//						try {
//							logout(session);
//						} catch (Exception e) {
//							Sistem.printStackTrace(e, "Failed to logout error state1");
//						}
//
//						throw new KnownError("User names are not equal:-" + cookie.getUserName() + "-~-"
//								+ session.getUser().getName());
//						// CookieVersusSession();
//						// differentSessionUserError.more("SECURITY User not
//						// same; session:"
//						// + session.getUser().getName()
//						// + " request:" + cookie.getUserName() + " for host:" +
//						// domain);
//						// throw new KnownError().badRequest();
//					}

					// if (!session.isGuest()) {
					// publicDomainWannaAuthError.more();
					// throw new KnownError().badRequest();
					// }

					if (!domain.getCookieDomain().equals(session.getCookieDomainName())) {
						// differentSessionHostError.more("SECURITY Host not
						// same; session:"
						// + session.domain.getDomainName()
						// + " request:" + domain.getDomainName());
						// throw new KnownError().badRequest();

						try {
							logout(session);
						} catch (Exception e) {
							Sistem.printStackTrace(e, "Failed to logout error state2");
						}

						throw new KnownError("Domain name & session domain name are not equal:"
								+ domain.getCookieDomain() + "~" + session.getCookieDomainName());
					}
					return session;
				}
			}

			RomUser user = new RomUser();
			String cc = YerelGorevlisi.tek().getCountryCode(ipAddress);
			String langOfCountry = YerelGorevlisi.tek().langOfCountry(cc);

			UserAgent ua = UserAgent.parseUserAgentString(userAgent);

			synchronized (this) {
				AppSession session = hostSession.add(domain, user, ipAddress.getHostString(), ua, cc, langOfCountry,
						referrer);
				return session;
			}
		} catch (KnownError e) {
			_getSession.failed(e, domain, ipAddress, cookieHeader, userAgent, referrer);
			throw e;
		}
	}

	private AppHostSession getCreateHostSession(Integer hostId) {
		hostId = HostingUtils.hostIdIntra(hostId);
		AppHostSession hs = hostSessions.get(hostId);
		if (hs == null) {
			// syso("=========NEW SESSION============");
			hs = new AppHostSession();
			hostSessions.put(hostId, hs);
		}
		return hs;
	}

	private static final Astate _getHostSession = mc.c("getHostSession");

	public AppHostSession getHostSession(int hostId) throws KnownError {

		_getHostSession.more();

		hostId = HostingUtils.hostIdIntra(hostId);
		AppHostSession hs;
		hs = hostSessions.get(hostId);
		if (hs == null) {
			_getHostSession.fail(hostId);
			throw new KnownError();
		}
		return hs;
	}

	private static final Astate x1 = mc.c("http-auth-success");
	private static final Astate x2 = mc.c("http-auth-failed");
	private static final Astate x3 = mc.c("http-auth-ban");

	final static private Astate userNotFoundWarn = mc.c("app-user-not-found");
	final static private Astate incorrentPasswordWarn = mc.c("app-incorrent-password");

	private static final Astate _authenticate = mc.c("authenticate");

	public int authenticate(AppSession session, String username, String password, boolean trusted, String cid,
			boolean noAuth, boolean upgrade) throws KnownError {

		_authenticate.more();

		boolean toSystem = !noAuth && (username != null && username.indexOf('@') < 0);

		AppHostSession hostSession = getCreateHostSession(session.getHostId());

		int hostId = session.getIntraHostId();
		if (toSystem) {
			RomUser user = OturumGorevlisi.tek().getUser(session, session.getIntraHostId(), username);
			if (user == null) {
				_authenticate.fail(session, username, password);
				userNotFoundWarn.more();
				x2.more();
				if (OturumGorevlisi.tek().authFailed(IpStat.MODULE_HTTP, session.getIpAddress(),
						session.getEmailDomain(), username)) {
					x3.more();
					return -2;
				}
				return -1;
			}

			if (!trusted && !user.verifyPassword(password)) {
				x2.more();
				if (OturumGorevlisi.tek().authFailed(IpStat.MODULE_HTTP, session.getIpAddress(),
						session.getEmailDomain(), username)) {
					x3.more();
					return -2;
				}
				return -1;
			}

			String contactName = OturumGorevlisi.tek().getContactName(hostId, user.getCid());
			// if (session.getCanAuth()) {
			hostSession.changeUser(session, user, contactName == null ? user.getName() : contactName);
			// } else {
			// hostSession.changeContact(session, user.getCid(), contactName);
			// }
			// publicDomainWannaAuthWarn.more();
			// return false;

		} else {

			if (noAuth) {
				if (upgrade) {
					String cname = OturumGorevlisi.tek().getContactName(hostId, cid);
					session.upgradeToContact(cid, cname);
				} else
					hostSession.changeContact(session, cid, session.getIpAddress(), noAuth);
			} else if (trusted) {
				String cname = OturumGorevlisi.tek().getContactName(hostId, cid);
				hostSession.changeContact(session, cid, cname, noAuth);

			} else {
				String[] ret = OturumGorevlisi.tek().checkContactPwd(session.getIntraHostId(), username, password);
				if (ret == null) {
					_authenticate.fail(session, username, password);
					incorrentPasswordWarn.more();
					x2.more();
					if (OturumGorevlisi.tek().authFailed(IpStat.MODULE_HTTP, session.getIpAddress(),
							session.getEmailDomain(), username)) {
						x3.more();
						return -2;
					}
					return -1;
				}
				hostSession.changeContact(session, ret[0], ret[1] + " " + ret[2], noAuth);
			}

		}

		return 0;
	}

	public void logout(AppSession session) throws KnownError {

		AppHostSession hostSession = getCreateHostSession(session.getIntraHostId());
		hostSession.changeUser(session, new RomUser(), "_guest");
	}

	@Override
	public void check() {
		for (AppHostSession hs : hostSessions.values()) {
			hs.checkExpire(OturumGorevlisi.tek().expireDelay);
		}
	}

	@Override
	public void everyMinute(int year, int month, int day, int hour, int minute) {
		for (AppHostSession it : hostSessions.values()) {
			it.pingRt();
		}
	}

	@Override
	public void waitingChanged(int hostId, String cid, String app, String code, JSONArray inref, int times,
			JSONArray title, JSONArray user) {
		AppHostSession hs = hostSessions.get(hostId);
		if (hs == null)
			return;
		AppSession s = hs.getSessionsByCid(cid);
		if (s == null)
			return;

		s.sendWaitingChanged(app, code, inref, times);
	}

	@Override
	public boolean canPush() {
		return true;
	}

	@Override
	public boolean isOnline(int hostId, String cid) throws KnownError {
		AppHostSession hs = hostSessions.get(hostId);
		if (hs == null)
			return false;
		AppSession s = hs.getSessionsByCid(cid);
		if (s == null)
			return false;

		return true;
	}

//	select json_build_object('c','s.dlgcmd', 'ani', true, 'hostid', a_host, 'cmd', p_cmd, 'dlg',a_self,
//			'from', a_contact, tos, v_contacts, 'str', p_str, 'more', p_more)::text into v_text

	public void dlgCmd(JSONObject jo) {
		try {
			int hostId = jo.getInt("hostid");
			AppHostSession hs = getHostSession(hostId);
			JSONArray ja = jo.getJSONArray("tos");
			jo.remove("tos");
			RtDlg rt = new RtDlg(jo);
			for (int i = 0; i < ja.length(); i++) {
				String cid = ja.getString(i);
				hs.sendRt(cid, rt);
			}

		} catch (JSONException | KnownError e) {
			Sistem.printStackTrace(e);
		}

	}

	@Override
	public void push(int hostId, String cid, PushCommand pc) throws KnownError {
		AppHostSession hostSession = getHostSession(hostId);
		if (hostSession == null)
			return;
		AppSession session = hostSession.getSessionsByCid(cid);
		if (session == null)
			return;
		session.rtMsg(pc);
	}

	@Override
	public void broadcast(JSONObject jo) {
		for (AppHostSession hs : hostSessions.values()) {
			hs.broadcast(jo);
		}
	}

}
