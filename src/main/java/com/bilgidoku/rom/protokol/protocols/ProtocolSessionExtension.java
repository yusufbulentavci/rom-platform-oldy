package com.bilgidoku.rom.protokol.protocols;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.session.SessionExtension;
import com.bilgidoku.rom.shared.err.KnownError;

public abstract class ProtocolSessionExtension<PSA extends ProtocolSessionActivity> extends SessionExtension {

	private static final MC mc=new MC(ProtocolSessionExtension.class);
	
	private final Map<Integer, ProtocolHostSession<PSA>> hostSessions = new ConcurrentHashMap<Integer, ProtocolHostSession<PSA>>();

	private Set<ProtocolSession<PSA>> anonymSession=new HashSet<ProtocolSession<PSA>>();

	protected abstract boolean authFailed(int moduleId, String ipAddress, String string, Object object);
	protected abstract RomUser getRomUserByEmail(ProtocolSession<PSA> ses, String username);

	
	public ProtocolSessionExtension(String name) {
		super(name);
	}

	protected PSA getActivity(int hostId, String cid) throws KnownError {
		ProtocolHostSession<PSA> hs = getHostSession(hostId);
		if(hs==null)
			return null;
		
		return hs.getActivity(cid);
	}
	
	private static final Astate _getHostSession = mc.c("getHostSession");
	
	protected ProtocolHostSession<PSA> getHostSession(int hostId) throws KnownError {
		_getHostSession.more();

		ProtocolHostSession<PSA> hs;
		hs = hostSessions.get(hostId);
		if (hs == null) {
			hs=new ProtocolHostSession<PSA>();
			hostSessions.put(hostId, hs);
		}
		return hs;
	}
	
	public JSONObject selfDescribe() {
		JSONObject jo=new JSONObject();
		jo.safePut("hostcount", hostSessions.size()).safePut("anonymcount", anonymSession.size());
		return jo;
	}

	
	public int protocolLogin(ProtocolSession<PSA> ses, String username, String password) {
		RomUser user;
		try {
			user = getRomUserByEmail(ses, username);
			if (user == null) {
				if (authFailed(ses.getConfiguration().getModuleId(), ses.getIpAddress(), "", null)) {
					return -2;
				}
				return -1;
			}

			if (!user.verifyPassword(password)) {
				if (authFailed(ses.getConfiguration().getModuleId(), ses.getIpAddress(), "", username)) {
					return -2;
				}
				return -1;
			}

			ProtocolHostSession<PSA> hs = getHostSession(user.getDomain().getHostId());

			this.anonymSession.remove(ses);
			ses.loggedIn(user);
			hs.loggedIn(ses);
			
			return 0;
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "username:" + username + " pass:" + password);
			if (authFailed(ses.getConfiguration().getModuleId(), ses.getIpAddress(), "", username)) {
				return -2;
			}
			return -1;
		}
	}



	final static private Astate slogot = mc.c("protocol-logout");

	public void logout(ProtocolSession<PSA> ses) {
		slogot.more();
		anonymSession.remove(ses);
		int hostId = ses.getIntraHostId();
		if (hostId < 0)
			return;
		try {
			ProtocolHostSession<PSA> hs = getHostSession(hostId);
			hs.logout(ses);
		} catch (KnownError e) {
			Sistem.printStackTrace(e,
					"host session not found for smtp hostid:" + hostId + " user:" + ses.getProtocolUser());
			slogot.failed();
		}
	}

	final static private Astate csm = mc.c("create-session");

	public ProtocolSession<PSA> createSession(ProtocolTransport transport, ProtocolConfiguration config) {
		csm.more();
		
		ProtocolSession<PSA> ses = construct(transport, config);
		anonymSession.add(ses);
		return ses;
	}

	protected abstract ProtocolSession<PSA> construct(ProtocolTransport transport, ProtocolConfiguration config);
	
	@Override
	public boolean isOnline(int hostId, String cid) throws KnownError {
		ProtocolHostSession<PSA> hs = getHostSession(hostId);
		if(hs==null)
			return false;

		return hs.isOnline(cid);
	}

}
