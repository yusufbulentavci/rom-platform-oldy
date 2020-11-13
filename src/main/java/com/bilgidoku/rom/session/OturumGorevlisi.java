package com.bilgidoku.rom.session;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


import com.bilgidoku.rom.dns.Dns;
import com.bilgidoku.rom.dns.DnsImplement;
import com.bilgidoku.rom.dns.Dnsbl;
import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.haber.NodeTalkMethod;
import com.bilgidoku.rom.haber.TalkResult;
import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.CommonSession;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryDay;
import com.bilgidoku.rom.run.timer.EveryHour;
import com.bilgidoku.rom.run.timer.EveryMinute;
import com.bilgidoku.rom.session.dbop.CheckContactPwd;
import com.bilgidoku.rom.session.dbop.ConfirmedCid;
import com.bilgidoku.rom.session.dbop.GetCidByEmail;
import com.bilgidoku.rom.session.dbop.GetCidByFbId;
import com.bilgidoku.rom.session.dbop.GetContactEmail;
import com.bilgidoku.rom.session.dbop.GetContactName;
import com.bilgidoku.rom.session.dbop.GetContactPwd;
import com.bilgidoku.rom.session.dbop.GetContactPwdByEmail;
import com.bilgidoku.rom.session.dbop.GetUserNameByCid;
import com.bilgidoku.rom.session.dbop.UserNameByEmail;
import com.bilgidoku.rom.shared.err.KnownError;

public class OturumGorevlisi extends GorevliDir
		implements Runnable, EveryMinute, EveryHour, EveryDay {
	public static final int NO=11;
	
	public static OturumGorevlisi tek(){
		if(tek==null) {
			synchronized (GunlukGorevlisi.class) {
				if(tek==null) {
					tek=new OturumGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static OturumGorevlisi tek;
	private OturumGorevlisi() {
		super("Oturum", NO);
		this.watchPeriod = 60;
		this.expireDelay = 1500;
		try {
			this.filterDns = new Dnsbl(10000);
		} catch (UnknownHostException e) {
			this.filterDns = null;
		}
	}
	
	
	
	final static private MC mc = new MC(OturumGorevlisi.class);


	Dnsbl filterDns;
	final Map<String, Long> filtered = new ConcurrentHashMap<>();
	final Dns dns = DnsImplement.one();

	final Map<String, IpStat> stat = new ConcurrentHashMap<String, IpStat>();

	private final List<SessionExtension> extensions = new ArrayList<SessionExtension>();
	
	private SessionExtension mailSender;
	private SessionExtension mailReader;
	
	private SessionExtension onlineSender;

	private ScheduledFuture<?> randevu;

	public final int expireDelay;

	private int watchPeriod;


	@Override
	public void kur() throws KnownError {
		randevu = KosuGorevlisi.tek().scheduleAtFixedRate(this, watchPeriod, watchPeriod, TimeUnit.SECONDS);
		KosuGorevlisi.tek().waitMin(this);
		KosuGorevlisi.tek().waitDay(this);
	}


	public void selfDescribe(JSONObject jo) {
		int bannedCount = 0;
		for (IpStat ipstat : stat.values()) {
			if (ipstat.bannedTo > 0)
				bannedCount++;
		}

		jo.safePut("stats", stat.size()).safePut("bannedcount", bannedCount);

		for (SessionExtension se : extensions) {
			JSONObject jso = se.selfDescribe();
			if (jso != null)
				jo.safePut(se.getName(), jso);
		}
	}


	public void addExtension(SessionExtension extension) {
		extensions.add(extension);
		if(extension.getName().equals("app")){
			onlineSender = extension;
		}else if(extension.getName().equals("smtp")){
			mailSender = extension;
		}else if(extension.getName().equals("pop3")){
			mailReader = extension;
		}
	}


	public SessionExtension getExtension(String name) {
		for (SessionExtension it : extensions) {
			if (it.getName().equals(name)) {
				return it;
			}
		}
		throw new RuntimeException("Unknown session extension:" + name);
	}


	


	
	final static private Astate userLoad = mc.c("db-user-load");
	final static private Astate userLoadFatal = mc.c("db-user-load-error");



	public RomUser getUser(CommonSession session, Integer hostIdIntra, String userName) throws KnownError {
		destur();
		userLoad.more();
		return new LoadUser().doit(session, hostIdIntra, userName);
	}


	public String getUserByEmail(Integer a_host, String p_email) throws KnownError {
		destur();
		return new UserNameByEmail().doit(a_host, p_email);
	}


	public String[] checkContactPwd(int hostId, String email, String password) throws KnownError {
		destur();
		return new CheckContactPwd().doit(hostId, email, password);
	}


	public String getContactName(int hostId, String cid) throws KnownError {
		destur();
		return new GetContactName().doit(hostId, cid);
	}


	public String getContactEmail(int hostId, String cid) throws KnownError {
		destur();
		return new GetContactEmail().doit(hostId, cid);
	}


	public String getContactPwd(int hostId, String cid) throws KnownError {
		destur();
		return new GetContactPwd().doit(hostId, cid);
	}


	public String getContactPwdByEmail(int hostId, String email) throws KnownError {
		destur();
		return new GetContactPwdByEmail().doit(hostId, email);
	}

	private static final Astate _getRomUserByName = mc.c("get-user-by-name");

	public RomUser getRomUserByEmail(CommonSession session, String name) throws KnownError {
		// RomUser user=new RomUser(new RomHost("coreks",1), "coreks",
		// "123456",new
		// String[]{});
		_getRomUserByName.more();
		int ind = name.indexOf('@');
		if (ind <= 0) {
			_getRomUserByName.fail(name);
			return null;
			// throw new KnownError("Bad email address:" + name).security();
		}
		String userName = name.substring(0, ind);
		String dName = name.substring(ind + 1);

		return optUser(session, userName, dName);
	}


	public RomUser optUser(CommonSession session, String userName, String dName) throws KnownError {
		RomDomain domain = KurumGorevlisi.tek().optDomain(dName);
		if (domain == null || domain.isDisabled()) {
			return null;
		}

		RomUser user = getUser(session, domain.getIntra(), userName);
		return user;
	}

	final static private Astate getUserByNameCount = mc.c("mail-get-user-by-name-count");


	public RomUser getUserByName(CommonSession session, String name) throws KnownError {
		destur();
		getUserByNameCount.more();
		return getRomUserByEmail(session, name);
	}


	public ConfirmedCid getCidByPlatform(int hostId, String platform, String platformUserId) {
		if (platform.equals("facebook")) {
			try {
				return new GetCidByFbId().doit(hostId, platformUserId);
			} catch (KnownError e) {
				Sistem.printStackTrace(e, hostId + "-" + platformUserId);
				return null;
			}
		}
		return null;
	}


	public ConfirmedCid getCidByEmail(int hostId, String email) {
		try {
			return new GetCidByEmail().doit(hostId, email);
		} catch (KnownError e) {
			Sistem.printStackTrace(e, hostId + "-" + email);
		}
		return null;
	}


	public String getUserByCid(int hostId, String cid) {
		try {
			return new GetUserNameByCid().doit(hostId, cid);
		} catch (KnownError e) {
			Sistem.printStackTrace(e, hostId + "-" + cid);
			return null;
		}
	}


	public String getCidByUserName(RomDomain domain, String userName) {

		try {
			RomUser k = new LoadUser().doit(null, domain.getIntra(), userName);
			if (k == null)
				return null;
			return k.getCid();
		} catch (KnownError e) {
			Sistem.printStackTrace(e, domain.getDomainName());
			return null;
		}
	}



	public boolean contains(CommonSession session, String name) throws KnownError {
		destur();
		RomUser user = getRomUserByEmail(session, name);
		return (user != null);
	}

	private IpStat getStat(String ip) {
		IpStat is = stat.get(ip);
		if (is != null)
			return is;
		// is = new IpStat(RomEnvFactory.isIntranet(ip));
		is = new IpStat(false);
		stat.put(ip, is);
		return is;
	}

	final static private Astate au = mc.c("auth-failed");

	public boolean authFailed(int from, String ip, String toHost, String forUser) {
		au.more();
		IpStat is = getStat(ip);
		return is.authFailed(from, toHost, forUser);
	}

	final static private Astate hi = mc.c("hit");


	public boolean hit(int from, String ip, String toHost, ConnectionSession cs) {
		hi.more();
		IpStat is = getStat(ip);
		return is.hit(from, toHost);
	}

	final static private Astate co = mc.c("connect");


	public boolean connect(int from, String ip, ConnectionSession cs) {
		co.more();

		if (filter(from, ip, cs.getCountry())) {
			return true;
		}

		IpStat is = getStat(ip);
		return is.connect(from, cs);
	}

	private boolean filter(int from, final String ip, String country) {
//		if (filterDns == null)
//			return false;
//		
//		if(country!=null && country.equalsIgnoreCase("TR")){
//			return false;
//		}
//
//		Long now = System.currentTimeMillis();
//		Long l = filtered.get(ip);
//		if (l != null) {
//			if (now < l) {
//				return true;
//			}
//			filtered.remove(ip);
//		}
//
//		if (from != IpStat.MODULE_HTTP) {
//			return filterCheck(ip);
//		}
//
//		Sistem.run.runInWorker(new Runnable() {
//
//		
//			public void run() {
//
//				filterCheck(ip);
//
//			}
//		});

		return false;
	}

	final static private Astate fcb = mc.c("filterCheckBan");
	final static private Astate fcp = mc.c("filterCheckPass");
	
	private boolean filterCheck(String ip) {
		if (filterDns.dnsbl(ip)) {
			filtered.put(ip, System.currentTimeMillis() + 1000 * 60 * 60 * 5);
			fcb.more();
			GunlukGorevlisi.tek().ban(ip, "dnsbl");
			return true;
		}
		fcp.more();
		return false;
	}

	final static private Astate ab = mc.c("abuse");

	public boolean abuse(int from, String ip) {
		ab.more();
		if (filter(from, ip, null)) {
			return true;
		}
		IpStat is = getStat(ip);
		return is.abuse(from);
	}

	final static private Astate rc = mc.c("check-expire");


	public void run() {
		/*
		 * Watch period
		 */

		for (SessionExtension it : extensions) {
			try {
				it.check();
			} catch (Throwable t) {
				Sistem.printStackTrace(t, "Session extension check");
			}
		}

		rc.more();
	}

	private static final Astate em = mc.c("every-minute");


	public void everyMinute(int year, int month, int day, int hour, int minute) {
		long time = System.currentTimeMillis();
		for (Entry<String, IpStat> it : stat.entrySet()) {
			it.getValue().checkMin(time);
		}
		em.more();
	}
	
	private static final Astate eh = mc.c("every-hour");


	public void everyHour(int year, int month, int day, int hour) {
		long time = System.currentTimeMillis();
		List<String> toRemove = new ArrayList<String>();
		for (Entry<String, IpStat> it : stat.entrySet()) {
			if (it.getValue().checkHour(time))
				toRemove.add(it.getKey());
		}
		for (String string : toRemove) {
			stat.remove(string);
		}
		eh.more();
	}

	private static final Astate ed = mc.c("every-day");


	public void everyDay(int year, int month, int day) {
		long time = System.currentTimeMillis();
		for (Entry<String, IpStat> it : stat.entrySet()) {
			it.getValue().checkDay(time);
		}
		ed.more();
	}


	public Map<String, IpStat> getIpStat() {
		return stat;
	}


	public boolean unauthSmtpSendReq(String remoteHost, String ipAddress) {
		
		if (ipAddress != null) {
			return abuse(IpStat.MODULE_SMTP, ipAddress);
		}
		return false;
	}


	public void welcomeCallNotFromTlos(String string, String ipAddress) {
		abuse(IpStat.MODULE_HTTP, ipAddress);
	}


	public void welcomeCreateFrequentCall(String string, String ipAddress) {
		abuse(IpStat.MODULE_HTTP, ipAddress);
	}

	public void disconnect(int from, String remoteIp, ConnectionSession cs) {
		IpStat is = getStat(remoteIp);
		is.disconnect(from, remoteIp, cs);
	}

	@NodeTalkMethod(cmd = "s.m")
	public TalkResult process(JSONObject jo) {

		try {

			JSONArray ja = TalkUtil.arrData(jo);
			int hostId = ja.getInt(0);
			String cid = ja.getString(1);
			String app = ja.getString(2);
			String code = ja.getString(3);
			JSONArray inref = ja.getJSONArray(4);
			int times = ja.getInt(5);
			JSONArray title = ja.getJSONArray(6);
			JSONArray user = ja.getJSONArray(7);
			
			if (onlineSender != null && onlineSender.isOnline(hostId, cid)){
				onlineSender.waitingChanged(hostId, cid, app, code, inref, times, title, user);
				return TalkResult.success;
			}
			
			if(!app.equals("mail") && mailSender!=null && mailReader!=null && mailReader.isOnline(hostId, cid)){
				mailSender.waitingChanged(hostId, cid, app, code, inref, times, title, user);
			}

			return TalkResult.success;

		} catch (JSONException | KnownError e) {
			Sistem.printStackTrace(e, "s.m:" + jo.toString());
			return TalkResult.failed;
		}
	}
	
	@NodeTalkMethod(cmd = "s.b")
	public TalkResult broadcast(JSONObject jo) {

		try {
			
			String bcmd=jo.getString("bcmd");
			jo.put("cmd", bcmd);
			if(onlineSender!=null)
				onlineSender.broadcast(jo);
			return TalkResult.success;

		} catch (JSONException e) {
			Sistem.printStackTrace(e, "s.b:" + jo.toString());
			return TalkResult.failed;
		}
	}

}
