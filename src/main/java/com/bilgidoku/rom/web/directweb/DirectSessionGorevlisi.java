package com.bilgidoku.rom.web.directweb;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.Eye;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryMinute;
import com.bilgidoku.rom.session.SessionExtension;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.analytics.useragent.UserAgent;
import com.bilgidoku.rom.web.cookie.CookieGorevlisi;
import com.bilgidoku.rom.web.cookie.ParseCookie;
import com.bilgidoku.rom.web.http.session.AppSession;
import com.bilgidoku.rom.web.http.session.SecureRandomNumber;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.PushCommand;
import com.bilgidoku.rom.yerel.YerelGorevlisi;

public class DirectSessionGorevlisi extends GorevliDir implements EveryMinute {
	private static final MC mc = new MC(DirectSessionGorevlisi.class);

	public static final String DIRECT = "direct";

	final static private SecureRandomNumber random = new SecureRandomNumber();

	private final Map<String, DirectSession> sessionsBySid = new HashMap<String, DirectSession>();

	public static final int NO = 0;

	public static DirectSessionGorevlisi tek() {
		if (tek == null) {
			synchronized (DirectSessionGorevlisi.class) {
				if (tek == null) {
					tek = new DirectSessionGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static DirectSessionGorevlisi tek;

	private DirectSessionGorevlisi() {
		super("DirectSession", NO);
	}

	@Override
	protected void kur() throws KnownError {
		// KosuGorevlisi.tek().waitMin(this);
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

	public DirectSession getDirectSession(String domain, InetSocketAddress ipAddress, String cookieHeader)
			throws KnownError {

		_getSession.more();

		ParseCookie cookie = (cookieHeader == null) ? null : CookieGorevlisi.tek().parse(domain, cookieHeader);
		DirectSession session;
		if (cookie != null && cookie.getSid() != null) {
			session = sessionsBySid.get(cookie.getSid());
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

				if (!domain.equals(session.getCookieDomainName())) {
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

					throw new KnownError("Domain name & session domain name are not equal:" + domain + "~"
							+ session.getCookieDomainName());
				}
				return session;
			}
		}
		session = add(domain, ipAddress.getHostString());
		return session;

	}

	private void logout(DirectSession session) {
		// TODO Auto-generated method stub

	}

	private String generateSessionId() throws KnownError {
		String sid = random.generate();
		if (!sessionsBySid.containsKey(sid)) {
			return sid;
		}
		sid = random.generate();
		if (!sessionsBySid.containsKey(sid)) {
			return sid;
		}
		throw new KnownError("Failed to generate session id");
	}

	public DirectSession add(String domainName, String ipAddress) throws KnownError {

		String sid = generateSessionId();
		DirectSession session = new DirectSession(domainName, sid, ipAddress);

		sessionsBySid.put(sid, session);

		return session;
	}

	private void remove(DirectSession session) {
		if (Eye.on)
			mc.trace("Session removed:" + session.getSid());

		sessionsBySid.remove(session.getSid());

		session.expired();
	}

	private static final Astate _getHostSession = mc.c("getHostSession");

	private static final Astate x1 = mc.c("http-auth-success");
	private static final Astate x2 = mc.c("http-auth-failed");
	private static final Astate x3 = mc.c("http-auth-ban");

	final static private Astate userNotFoundWarn = mc.c("app-user-not-found");
	final static private Astate incorrentPasswordWarn = mc.c("app-incorrent-password");

	private static final Astate _authenticate = mc.c("authenticate");

	public int authenticate(DirectSession session, String username, String password) throws KnownError {

		_authenticate.more();

		return 0;
	}

	public void logout(AppSession session) throws KnownError {

	}

	public void check() {
//		for (AppHostSession hs : hostSessions.values()) {
//			hs.checkExpire(OturumGorevlisi.tek().expireDelay);
//		}
	}

	public void everyMinute(int year, int month, int day, int hour, int minute) {
//		for (AppHostSession it : hostSessions.values()) {
//			it.pingRt();
//		}
	}

	public void waitingChanged(int hostId, String cid, String app, String code, JSONArray inref, int times,
			JSONArray title, JSONArray user) {
//		AppHostSession hs = hostSessions.get(hostId);
//		if (hs == null)
//			return;
//		AppSession s = hs.getSessionsByCid(cid);
//		if (s == null)
//			return;
//
//		s.sendWaitingChanged(app, code, inref, times);
	}

	public boolean canPush() {
		return true;
	}

	public boolean isOnline(int hostId, String cid) throws KnownError {
//		AppHostSession hs = hostSessions.get(hostId);
//		if (hs == null)
//			return false;
//		AppSession s = hs.getSessionsByCid(cid);
//		if (s == null)
//			return false;

		return true;
	}

//	select json_build_object('c','s.dlgcmd', 'ani', true, 'hostid', a_host, 'cmd', p_cmd, 'dlg',a_self,
//			'from', a_contact, tos, v_contacts, 'str', p_str, 'more', p_more)::text into v_text

	public void dlgCmd(JSONObject jo) {
//		try {
//			int hostId = jo.getInt("hostid");
//			AppHostSession hs = getHostSession(hostId);
//			JSONArray ja = jo.getJSONArray("tos");
//			jo.remove("tos");
//			RtDlg rt = new RtDlg(jo);
//			for (int i = 0; i < ja.length(); i++) {
//				String cid = ja.getString(i);
//				hs.sendRt(cid, rt);
//			}
//
//		} catch (JSONException | KnownError e) {
//			Sistem.printStackTrace(e);
//		}

	}

	public void push(int hostId, String cid, PushCommand pc) throws KnownError {
//		AppHostSession hostSession = getHostSession(hostId);
//		if (hostSession == null)
//			return;
//		AppSession session = hostSession.getSessionsByCid(cid);
//		if (session == null)
//			return;
//		session.rtMsg(pc);
	}

	public void broadcast(JSONObject jo) {
//		for (AppHostSession hs : hostSessions.values()) {
//			hs.broadcast(jo);
//		}
	}

}
