package com.bilgidoku.rom.web.directweb;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.cookie.CookieGorevlisi;
import com.bilgidoku.rom.web.http.session.PushConn;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.PushCommand;

public class DirectSession {
	private static final MC mc = new MC(DirectSession.class);

	private static String GUEST = "misafir";

	private static AtomicLong SerialSint = new AtomicLong();

	private final Long sint;
	private final String sid;

	private final String ipAddress;
	private final long creationTime;
	private boolean open = true;
	private boolean cookieDirty = true;

	private Set<String> usingFiles;

	private String lastVisitUri = null;
	private long lastVisitTime = 0;

	private final Map<Integer, PushConn> requestHandlers = new ConcurrentHashMap<Integer, PushConn>();
	private PushConn rtCon;
	private String cookieLang;
	private boolean noAuth = false;

	private String domainName;

	private String userName = GUEST;

	public JSONObject selfDescribe() {
		JSONObject jo = new JSONObject();

		return jo;
	}

	DirectSession(String domainName, String sid, String ipAddress) {
		this.creationTime = System.currentTimeMillis();
		this.domainName = domainName;
		this.ipAddress = ipAddress;
		this.sid = sid;
		this.sint = SerialSint.getAndIncrement();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void expired() {
		this.open = false;
		for (PushConn pc : this.requestHandlers.values()) {
			try {
				pc.close();
			} catch (Exception e) {
			}

		}
	}

	public String getConn() {
		return this.ipAddress;
	}

	public void connClosed(PushConn conn, long[] ls) {
		this.requestHandlers.remove(conn.getSockId());
	}

	public void connReady(PushConn req) {
		this.requestHandlers.put(req.getSockId(), req);
	}

	public void rtReady(PushConn req) {
		boolean wasOffline = (rtCon == null);
		this.rtCon = req;
//		String cid = getCid();
//		if (cid != null) {
//			this.setPresence(Presence.FREE, Presence.StrFree);
//			up.cidOnline(cid);
//
//		}
	}

	public void rtOffline(PushConn req) {
		if (req != null && rtCon == req) {
			rtCon = null;
		}
//		if (getCid() != null) {
//			this.setPresence(0, Presence.StrOffline);
//			up.cidOffline(getCid());
//		}
	}

	public String getUserName() {
		return this.userName;
	}

	final static private Astate _rtConNull = mc.c("rtConNull");

	public boolean rtMsg(String msg) {
		// long latest=0;
		// List<PushConn> toSend=null;
		// for (PushConn it : requestHandlers.values()) {
		// Long pctime = it.isOkForRt();
		// if(pctime!=null ){
		//// && pctime.longValue()>latest
		//// latest=pctime.longValue();
		// if(toSend==null)
		// toSend=new ArrayList<PushConn>();
		// toSend.add(it);
		// }
		// }
		// if(toSend!=null){
		//
		// Sistem.outln("RtMsg:"+msg);
		//
		// for (PushConn pushConn : toSend) {
		// pushConn.sendRt(msg);
		// }
		// return true;
		// }
		if (this.rtCon == null) {
			_rtConNull.more();
			return false;
		}
		this.rtCon.sendRt(msg);
		return true;
	}

	public boolean rtMsg(PushCommand rtc) {
		return rtMsg(rtc.toString());
	}

	public String getCookieDomainName() {
		return this.domainName;
	}

	public String getSid() {
		return sid;
	}

	public String[] getCookieHeaders() throws KnownError {
		this.cookieDirty = false;
		return CookieGorevlisi.tek().write(new Cookie() {

			@Override
			public void cookieSent() {
				DirectSession.this.cookieDirty = false;
			}

			@Override
			public boolean cookieDirty() {
				return DirectSession.this.cookieDirty;
			}

			@Override
			public String getSid() {
				return DirectSession.this.sid;
			}

			@Override
			public String getCookieUser() {
				return DirectSession.this.userName;
			}

			@Override
			public String getRoles() {
				return "";
			}

			@Override
			public String getCookieDomainName() {
				return DirectSession.this.domainName;
			}

			@Override
			public String getCname() {
				return DirectSession.this.userName;
			}

			@Override
			public String getCid() {
				return "";
			}

			@Override
			public String getCookieLang() {
				return "en";
			}

			@Override
			public String getCookieHostName() {
				return DirectSession.this.domainName;
			}

			@Override
			public String getCookiePresence() {
				return "presenceCode:" + "offline";
			}

			@Override
			public boolean getCookieAuth() {
				return false;
			}
		});
	}

	public boolean isCookieDirty() {
		return cookieDirty;
	}

}
