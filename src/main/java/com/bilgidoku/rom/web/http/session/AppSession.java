package com.bilgidoku.rom.web.http.session;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.EventSource;
import com.bilgidoku.rom.ilk.util.EventSourceImpl;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.ilk.util.HostingUtils;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.CommonSession;
import com.bilgidoku.rom.pg.dict.LifeCycleEvents;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.util.Presence;
import com.bilgidoku.rom.web.analytics.AnalitikGorevlisi;
import com.bilgidoku.rom.web.analytics.PageVisit;
import com.bilgidoku.rom.web.analytics.useragent.UserAgent;
import com.bilgidoku.rom.web.cookie.CookieGorevlisi;
import com.bilgidoku.rom.web.dbop.MergeCids;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.PushCommand;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtWaitingChanged;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtYou;

public class AppSession implements CommonSession, Cookie {
	private static final MC mc = new MC(AppSession.class);

	private static AtomicLong SerialSint=new AtomicLong();
	
	private final Long sint;
	private final String sid;

	private final AppSessionToHost up;

	private RomUser user;
	private final String ipAddress;
	private final long creationTime;
	private long usedTime;
	private int useCounter;
	private int useFrequency;
	private boolean open = true;
	private boolean cookieDirty = true;
	private final UserAgent userAgent;
	private Set<String> usingFiles;
	private final String country;

	private final String referrer;
	private final Map<String, PageVisit> pageVisits = new HashMap<String, PageVisit>();
	private AtomicLong netRead = new AtomicLong();
	private AtomicLong netWrite = new AtomicLong();
	private AtomicLong cpu = new AtomicLong();

	private String lastVisitUri = null;
	private long lastVisitTime = 0;

	private final Map<Integer, PushConn> requestHandlers = new ConcurrentHashMap<Integer, PushConn>();
	private final int intraHostId;
	private final int hostId;
	private final String topDomainName;
	private final String emailDomain;
	private final String cookieDomain;
	private String presence = Presence.StrFree;
	private int presenceCode = Presence.FREE;
	private PushConn rtCon;
	private String cookieLang;
	private final String langOfCountry;
	private final EventSource<CommonSession> lifeCycleSource = new EventSourceImpl<>();
	private boolean noAuth = false;

	public JSONObject selfDescribe() {
		JSONObject jo = new JSONObject();
		if (user != null) {
			jo.safePut("cid", user.getCid()).safePut("user", user.getName());
		}

		if (referrer != null)
			jo.safePut("referrer", referrer);

		jo.safePut("ct", new Date(creationTime).toString()).safePut("ip", ipAddress).safePut("cn", country)
				.safePut("p", presence).safePut("domain", topDomainName);

		JSONObject c = new JSONObject();
		jo.safePut("c", c);
		for (PushConn it : requestHandlers.values()) {
			c.safePut(it.getSockId() + "", it.isOkForRt());
		}

		return jo;
	}

	AppSession(AppSessionToHost up, String sid, int hostId, String topDomainname, String emailDomain,
			String cookieDomain, RomUser user, String ipAddress, UserAgent userAgent, String country,
			String langOfCountry, String referrer) {
		this.up = up;
		this.creationTime = System.currentTimeMillis();
		this.hostId = hostId;
		this.intraHostId = HostingUtils.hostIdIntra(hostId);

		this.topDomainName = topDomainname;
		this.emailDomain = emailDomain;
		this.cookieDomain = cookieDomain;

		this.user = user;
		this.ipAddress = ipAddress;
		this.sid = sid;
		this.userAgent = userAgent;
		this.country = country;
		this.langOfCountry = langOfCountry;
		this.referrer = referrer;
		this.sint=SerialSint.getAndIncrement();
	}

	public RomUser getUser() {
		return user;
	}

	public boolean isAuth() {
		return user.getCid() != null;
	}

	public boolean isGuest() {
		return user.isGuest();
	}

	public boolean isUser() {
		return user.isUser();
	}

	public void touch() {
		this.usedTime = System.currentTimeMillis();
		this.useCounter++;
		this.useFrequency++;
	}

	public long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(long usedTime) {
		this.usedTime = usedTime;
	}

	public int getUseCounter() {
		return useCounter;
	}

	public void setUseCounter(int useCounter) {
		this.useCounter = useCounter;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public int getUseFrequency() {
		return useFrequency;
	}

	public long notUsedForSeconds() {
		return (System.currentTimeMillis() - usedTime) / 1000;
	}

	public void expired() {
		this.open = false;
		for (PushConn pc : this.requestHandlers.values()) {
			try {
				pc.close();
			} catch (Exception e) {
			}

		}
		AnalitikGorevlisi.tek().addSessionCounters(this.intraHostId, this.topDomainName, this.creationTime, country, "en",
				referrer, userAgent, this.pageVisits, this.cpu.getAndSet(0), this.netRead.getAndSet(0),
				this.netWrite.getAndSet(0));

		lifeCycleSource.broadcast(this, LifeCycleEvents.closed);
	}

	public String getConn() {
		return this.ipAddress;
	}

	Astate _changeUser = mc.c("changeUser");

	public void changeUser(RomUser user) {
		_changeUser.more();
		if (!user.isContact() && !user.isUser()) {
			rtOffline(null);
		}

		if (noAuth) {
			try {
				new MergeCids().doIt(intraHostId, this.user.getCid(), user.getCid());
			} catch (KnownError e) {
				_changeUser.failed(e);
			}
		}

		setNoAuth(false);
		this.user = user;
		this.cookieDirty = true;
		lifeCycleSource.broadcast(this, LifeCycleEvents.userChanged);
	}

	Astate _changeContact = mc.c("changeContact");

	public void changeContact(String cid, String cname, boolean noAuth2) {
		_changeContact.more();
		if (noAuth && cid!=null) {
			try {
				new MergeCids().doIt(intraHostId, this.user.getCid(), cid);
			} catch (KnownError e) {
				_changeContact.failed(e);
			}
		}

		this.user.setCid(cid);
		this.user.setCname(cname);
		this.noAuth=noAuth2;
		this.cookieDirty = true;
	}

	Astate _upgradeToContact = mc.c("upgradeToContact");

	public void upgradeToContact(String cid, String cname) {
		_upgradeToContact.more();

		this.user.setCname(cname);
		this.noAuth=false;
		this.cookieDirty = true;
	}

	
	

	@Override
	public String getSid() {
		return sid;
	}

	//
	// @Override
	// public String getLang() {
	// return this.domain.getLang();
	// }

	// @Override
	// public void setLang(String value) {
	// this.lang = value;
	// }

	public String[] getCookieHeaders() throws KnownError {
		this.cookieDirty = false;
		return CookieGorevlisi.tek().write(this);
	}

	

	// public RomDomain getDomain() {
	// return domain;
	// }

	@Override
	public String getCookieUser() {
		if(!user.isUser())
			return null;
		return user.getName();
	}
	
	@Override
	public String getUserName() {
		return user.getName();
	}

	@Override
	public String getCookieDomainName() {
		return cookieDomain;
	}

	@Override
	public void cookieSent() {
		this.cookieDirty = false;
	}

	@Override
	public String getRoles() {
		return user.getRole() + "";
	}

	@Override
	public boolean cookieDirty() {
		return cookieDirty;
	}

	@Override
	public String getCname() {
		return this.user.getCname();
	}

	@Override
	public String getCid() {
		return this.user.getCid();
	}

	public int getInterHostId() {
		return HostingUtils.hostIdInter(intraHostId);
	}

	public int getIntraHostId() {
		return this.intraHostId;
	}

	public int getHostId() {
		return hostId;
	}

	// public String getEmailDomain() {
	// return this.domain.getEmailDomain();
	// }

	public UserAgent getUserAgent() {
		return userAgent;
	}

	public boolean isMobile() {
		return userAgent.getBrowser().isMobile();
	}

	public boolean authenticated() {
		return user.authenticated();
	}

	private final static long MAX_FIVE_MINUTES = 5 * 60 * 1000;

	public void visiting(String self) {
		long now = System.currentTimeMillis();
		visitingEnded();
		PageVisit p = pageVisits.get(self);
		if (p == null) {
			p = new PageVisit();
		}
		p.appendCount();
		lastVisitTime = now;
		lastVisitUri = self;
	}

	private void visitingEnded() {
		long now = System.currentTimeMillis();
		if (lastVisitUri != null) {
			long period = now - lastVisitTime;
			if (period > MAX_FIVE_MINUTES)
				period = MAX_FIVE_MINUTES;
			int inSecs = (int) (period / 1000);
			PageVisit p = pageVisits.get(lastVisitUri);
			if (p != null)
				p.appendSecs(inSecs);
		}
	}

	public void connClosed(PushConn conn, long[] ls) {
		this.netRead.addAndGet(ls[0]);
		this.netWrite.addAndGet(ls[1]);
		this.requestHandlers.remove(conn.getSockId());
		if (requestHandlers.size() == 0) {
			visitingEnded();
		}
	}

	public void connReady(PushConn req) {
		this.requestHandlers.put(req.getSockId(), req);
	}

	public void rtReady(PushConn req) {
		boolean wasOffline = (rtCon == null);
		this.rtCon = req;
		String cid = getCid();
		if (cid != null) {
			this.setPresence(Presence.FREE, Presence.StrFree);
			up.cidOnline(cid);

		}
	}

	public void rtOffline(PushConn req) {
		if (req != null && rtCon == req) {
			rtCon = null;
		}
		if (getCid() != null) {
			this.setPresence(0, Presence.StrOffline);
			up.cidOffline(getCid());
		}
	}

	public String getaName() {
		if (isGuest()) {
			if (getCid() == null) {
				String ret = Genel.parseIp(ipAddress);
				return ret;
			}
			return getCname();
		}
		return getUserName();
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

	public int getRole() {
		return user.getRole();
	}

	@Override
	public Cookie getCookie() {
		return this;
	}

	// @Override
	// public Integer getBw() {
	// return this.userAgent.getBrowser().getBrowserType().getWidth();
	// }
	//
	// @Override
	// public void setBw(Integer bw) {
	//
	// }

	@Override
	public void appendCpu(int cpu) {

	}

	@Override
	public int screenWidth() {
		return userAgent.getBrowser().getBrowserType().getWidth();
	}

	@Override
	public String getUserEmail() {
		return user.getEmail();
	}

	@Override
	public long getRoleMask() {
		return user.getRoleMask();
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	@Override
	public String getTopLevelDomain() {
		return this.topDomainName;
	}

	public void usingFile(String f_attach) {
		if (usingFiles == null) {
			this.usingFiles = new HashSet<String>();
		}
		this.usingFiles.add(f_attach);
	}

	public void setPresence(Integer p_code, String p_presence) {
		this.presenceCode = p_code;
		this.presence = p_presence;
	}

	public String getPresenceCoded() {
		return this.presenceCode + "-" + this.presence;
	}

	public int getPresenceCode() {
		return this.presenceCode;
	}

	public String getCountry() {
		return country;
	}

	public void pingRt() {
		if (this.rtCon != null) {
			rtCon.sendRt(new RtYou(getCid()).toString());
			touch();
		}
	}

	public void sendWaitingChanged(String app, String code, JSONArray inref, int times) {
		if (this.rtCon != null) {
			rtCon.sendRt(new RtWaitingChanged(app, code, inref, times).toString());
		}
	}

	@Override
	public String getCookieLang() {
		return cookieLang;
	}

	@Override
	public String getCountryLang() {
		return langOfCountry;
	}

	@Override
	public EventSource<CommonSession> getLifeCycleSource() {
		return lifeCycleSource;
	}

	public void setNoAuth(boolean noA) {
		this.noAuth = noA;
	}

	public boolean isNoAuth() {
		return noAuth;
	}

	@Override
	public String getCookieHostName() {
		return emailDomain;
	}

	@Override
	public String getCookiePresence() {
		return presenceCode+":"+presence;
	}

	@Override
	public boolean getCookieAuth() {
		return user.getCid()!=null && !noAuth;
	}

	public Integer getCint() {
		return user.getCint();
	}

	//
	// public void removePushInterface(PushInterface webSocket) {
	// this.pushInterface = null;
	// }
	//
	// public void addPushInterface(PushInterface webSocket) {
	// this.pushInterface = webSocket;
	// }
	//
	// public void sendProgressEvent(String path, long contentLength,
	// long writerIndex) {
	// if(this.pushInterface!=null){
	// try {
	// JSONObject jo=new JSONObject();
	// jo.put("type", "upload");
	// jo.put("index", writerIndex);
	// jo.put("size", contentLength);
	// this.pushInterface.pushEvent(jo);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// PrintStackTrace.print483(e);
	// }
	// }
	// }
}
