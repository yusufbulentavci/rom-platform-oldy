package com.bilgidoku.rom.web.http.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.Eye;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.shared.util.Presence;
import com.bilgidoku.rom.web.analytics.useragent.UserAgent;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtCommon;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtDlg;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtPresence;

/**
 * 
 * TODO: A cid may multiple session but this does not support this. TODO:
 * Synchronization is problem too
 * 
 * 
 * @author avci
 *
 */
public class AppHostSession {
	private static final MC mc = new MC(AppHostSession.class);
	final static private SecureRandomNumber random = new SecureRandomNumber();

	private final Map<String, AppSession> sessionsBySid = new HashMap<String, AppSession>();
	
	private final FilteredMap<String, AppSession> sessionsByUserName = new FilteredMap<String, AppSession>(new Filter<String, AppSession>() {

		@Override
		public boolean match(AppSession v) {
			return v.isUser();
		}

		@Override
		public String getKey(AppSession v) {
			return v.getUserName();
		}
	});
	
	
	private final FilteredMap<String, AppSession> sessionsByCid = new FilteredMap<String, AppSession>(new Filter<String, AppSession>() {

		@Override
		public boolean match(AppSession v) {
			return v.isAuth();
		}

		@Override
		public String getKey(AppSession v) {
			return v.getCid();
		}
	});


	public AppSession getSessionsBySid(String sid) {
		return sessionsBySid.get(sid);
	}

	public JSONObject selfDescribe() {
		JSONObject jo = new JSONObject();

		JSONObject se = new JSONObject();
		jo.safePut("s", se);

		for (AppSession it : sessionsBySid.values()) {
			se.safePut(it.getSid(), it.selfDescribe());
		}

		if (sessionsByCid.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (AppSession it : sessionsByCid.values()) {
				sb.append(it.getCid());
				sb.append(",");
			}

			se.safePut("cids", sb.toString());
		}

		return jo;
	}

	public AppSession add(RomDomain domain, RomUser user, String ipAddress, UserAgent userAgent,
			String country, String langOfCountry, String referrer) throws SessionIdGenerationError {
		
		String sid = generateSessionId();
		AppSession session = new AppSession(appSessionToHost, sid, domain.getHostId(), 
				domain.getTopLevel(), domain.getEmailDomain(), domain.getCookieDomain(), user, ipAddress, 
				userAgent, country, langOfCountry, referrer);
		session.touch();
		sessionsBySid.put(sid, session);

		removeClientsOldSessions(session.getCid());

		sessionsByUserName.adding(session);

		sessionsByCid.adding(session);

		return session;
	}
	
	private void remove(AppSession session) {
		if (Eye.on)
			mc.trace("Session removed:" + session.getSid());

		sessionsBySid.remove(session.getSid());
		sessionsByUserName.removing(session);

		sessionsByCid.removing(session);

		session.expired();
		sessionExpiredCounter.more();
	}
	
	public void changeUser(AppSession session, RomUser user, String contactName) {
		sessionsByUserName.removing(session);
		
		sessionsByCid.removing(session);

		removeClientsOldSessions(user.getCid());

		user.setCname(contactName);
		session.changeUser(user);
		sessionsByCid.adding(session);

		sessionsByUserName.adding(session);
	}

	public void changeContact(AppSession session, String cid, String contactName, boolean noAuth) {

		sessionsByCid.removing(session);

		session.changeContact(cid, contactName, noAuth);

		sessionsByCid.adding(session);

	}


	private void removeClientsOldSessions(String cid) {
		if(cid==null)
			return;
		AppSession cs = sessionsByCid.get(cid);
		if (cs != null)
			remove(cs);

	}

	private String generateSessionId() throws SessionIdGenerationError {
		String sid = random.generate();
		if (!sessionsBySid.containsKey(sid)) {
			return sid;
		}
		sid = random.generate();
		if (!sessionsBySid.containsKey(sid)) {
			return sid;
		}
		throw new SessionIdGenerationError();
	}

	
	private RomUser getUserByRole(int role) {
		for (AppSession s : sessionsByUserName.values()) {
			if (s.getUser().hasRole(role)) {
				return s.getUser();
			}
		}
		return null;
	}
	
	

	// public String createRtDialog(AppSession session, boolean isOpen, boolean
	// isPermanent, String[] p_users, String[] p_cids) {
	// String dlgId=this.dialogSeq.incrementAndGet()+"";
	// RtDialog dlg=new RtDialog(dlgId, p_app, p_subject, isOpen, isPermanent);
	//
	// if(p_cids!=null){
	// for (String string : p_cids) {
	// dlg.addByCid(string);
	// }
	// }
	// dialogs.put(dlgId, dlg);
	// return dlgId;
	// }

	

	public AppSession getSessionsByUserName(String un) {
		return this.sessionsByUserName.get(un);
	}

	public AppSession getSessionsByCid(String cid) {
		return this.sessionsByCid.get(cid);
	}

	final static private Astate sessionExpiredCounter = mc.c("session-expired");

	public void checkExpire(long sessionExpireDelay) {
		List<AppSession> toremove = new ArrayList<AppSession>();
		for (Entry<String, AppSession> s : sessionsBySid.entrySet()) {
			if (s.getValue().notUsedForSeconds() > sessionExpireDelay) {
				toremove.add(s.getValue());
			}
		}
		for (AppSession appSession : toremove) {
			remove(appSession);
		}
	}



	public void broadcast(RtCommon rtCommon, boolean deskUserMsg) {
		
		String msg = rtCommon.toString();
		for (AppSession it : sessionsByCid.values()) {

			// dont broadcast to not authenticated user
			String toCid = it.getCid();
			if (toCid == null)
				continue;

			// dont broadcast to yourself
			if (rtCommon.getFromCid().equals(toCid))
				continue;

			// dont broadcast to contacts if message is not deskusers
			if (!it.isUser() && !deskUserMsg) {
				continue;
			}

			it.rtMsg(msg);

		}
	}

	// TODO: synchronization prolem
	public String[] getOnlineCids(boolean isUser) {
		List<String> ret = new ArrayList<String>();
		for (AppSession s : this.sessionsByCid.values()) {
			if(Presence.can(s.getPresenceCode(), Presence.VisibleAll)){
				ret.add(s.getCid());
				ret.add(s.getPresenceCoded());
			}
			
		}

		return ret.toArray(new String[ret.size()]);
	}

	public void pingRt() {
		for (AppSession it : this.sessionsBySid.values()) {
			it.pingRt();
		}
	}


	private AppSessionToHost appSessionToHost = new AppSessionToHostImpl();

	private class AppSessionToHostImpl implements AppSessionToHost {

		@Override
		public void cidOffline(String cid) {
			broadcast(new RtPresence(cid, 0, Presence.StrOffline), false);
		}

		@Override
		public void cidOnline(String cid) {
			broadcast(new RtPresence(cid, Presence.FREE, Presence.StrFree), false);
		}

	}

	public void sendRt(String cid, RtDlg rt) {
		AppSession ses = getSessionsByCid(cid);
		if(ses!=null)
			ses.rtMsg(rt);
	}

	public void broadcast(JSONObject jo) {
		String msg = jo.toString();
		for (AppSession it : sessionsByCid.values()) {

			it.rtMsg(msg);

		}
	}
}
