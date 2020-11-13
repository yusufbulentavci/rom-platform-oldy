package com.bilgidoku.rom.web.welcome;

import java.util.List;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.gunluk.LogCmds;
import com.bilgidoku.rom.gwt.shared.Welcome;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.grow.GenislemeGorevlisi;
import com.bilgidoku.rom.web.http.session.AppSession;

@HttpCallServiceDeclare(uri = "/_welcome", name = "Hosgeldin", paket="com.bilgidoku.rom.web.welcome")
public class HosgeldinGorevlisi extends GorevliDir {

	public static final int NO = 49;

	public static HosgeldinGorevlisi tek() {
		if (tek == null) {
			synchronized (HosgeldinGorevlisi.class) {
				if (tek == null) {
					tek = new HosgeldinGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static HosgeldinGorevlisi tek;

	private HosgeldinGorevlisi() {
		super("Hosgeldin", NO);
	}
	
	private static final MC mc = new MC(HosgeldinGorevlisi.class);


	private boolean isTlos = false;


	
//	@Override
//	public Integer createHost(String ip, String country, String a_lang, String p_email, String p_username,
//			String p_password) throws KnownError {
//		WelcomeService ws=ServiceDiscovery.getService(WelcomeService.class);
//		return ws.createHost(ip, country, a_lang, p_email, p_username, p_password);
//	}

	private static final Astate sitesof = mc.c("sitesof");

	@HttpCallMethod(http = "post")
	public Welcome[] sitesof(@hcp(n = "a_session") AppSession session, @hcp(n = "p_email") String p_email)
			throws KnownError {

		sitesof.more();
		try {
			if (session.getIntraHostId() > 2) {
				OturumGorevlisi.tek().welcomeCallNotFromTlos("welcome", session.getIpAddress());
				return null;
			}

			if (p_email == null || p_email.length() == 0)
				return null;

			List<Welcome> ws = new SitesofDbOp().doit(p_email);
			if (ws.size() == 0)
				return new Welcome[0];

			Welcome[] ret = ws.toArray(new Welcome[ws.size()]);
			for (Welcome welcome : ret) {
				welcome.country = null;
				welcome.ip = null;
			}
			return ret;
		} catch (Throwable t) {
			sitesof.fail("ip", session.getIpAddress(), "email", p_email);
			throw t;
		}

	}

	private static final Astate creat = mc.c("create");
	
	@HttpCallMethod(http = "post")
	public Integer create(@hcp(n = "a_session") AppSession session, @hcp(n = "a_lang") String a_lang,
			@hcp(n = "p_email") String p_email, @hcp(n = "p_username") String p_username,
			@hcp(n = "p_password") String p_password) throws KnownError {

		creat.more();
		try {
			int waitperiod = 2 * 60 * 60 * 1000; // 

			long now = System.currentTimeMillis();

			//		if(!isTlos || session.getHostId()>2){
			//			OturumGorevlisi.tek().welcomeCallNotFromTlos("welcome", session.getIpAddress());
			//			return null;
			//		}

			if (p_email == null || p_email.length() == 0)
				return null;

			List<Welcome> wsi = new SearchByIpDbOp().doit(session.getIpAddress());
			if (wsi != null && wsi.size() > 0) {
				for (Welcome welcome : wsi) {
					if ((now - welcome.lastactivity) < waitperiod) {
						OturumGorevlisi.tek().welcomeCreateFrequentCall("welcome", session.getIpAddress());
						return null;
					}
				}
			}

			//		if(p_hostname==null || p_hostname.length()==0)
			//			return null;
			//		
			//		p_hostname=p_hostname.toLowerCase();
			//		
			//		if(p_hostname.length()>30)
			//			return null;
			//		
			//		String uri = Genel.sanitizingUri(p_hostname);
			//		if(!uri.equals(p_hostname)){
			//			return null;
			//		}

			List<Welcome> ws = new SitesofDbOp().doit(p_email);
			if (ws.size() != 0) {
				for (Welcome welcome : ws) {
					if ((now - welcome.lastactivity) < waitperiod) {
						OturumGorevlisi.tek().welcomeCreateFrequentCall("welcome", session.getIpAddress());
						return null;
					}
				}
			}

			return createHost(session.getIpAddress(), session.getCountry(), a_lang, p_email, p_username, p_password);
		} catch (Throwable e) {
			creat.fail("ip", session.getIpAddress(), "email", p_email);
			throw e;
		}

		//		String ret;
		//		try {
		//			ret = growService.putgrowtoken(p_hostname, session.getCountry(), a_lang, p_email, 0, null, RomEnvFactory.getMine(Genel.getHostName()).domain);
		//			return ret;
		//		} catch (JSONException e) {
		//			throw new KnownError(e);
		//		}

	}


	public Integer createHost(String ip, String country, String a_lang, String p_email, String p_username, String p_password)
			throws KnownError {
		Integer host = newHost(p_email, p_username, p_password, "TR", a_lang);
		if (host == null) {
			return null;
		}

		try {
			new InsertWelcomeOp().doit(p_email, host, ip, country);
		} catch (Throwable e) {
			GunlukGorevlisi.tek().log(LogCmds.failedwelcome, false, 15, "hostid", host, "email", p_email, "ip", ip, "country",
					country);
		}

		return host;
	}

	private Integer newHost(String email, String adminName, String password, String cc, String lang) throws KnownError {
		try {
			JSONObject dataClient = new JSONObject();
			dataClient.put("lang", lang);
			dataClient.put("admin", adminName);
			dataClient.put("credential", password);
			dataClient.put("manual", true);
			JSONObject contact = techcontact(email, cc);
			dataClient.put("contact", contact);

			dataClient.put("business", "Business");
			dataClient.put("cc", cc);

			JSONObject dataServer = new JSONObject();
			dataServer.put("email", contact.getString("email"));
			//			dataServer.put("money", 100000);

			JSONObject jo = new JSONObject();
			jo.put("s", dataServer);
			jo.put("c", dataClient);

			JSONObject m = new JSONObject();
			m.put("d", jo);

			Integer ret = GenislemeGorevlisi.tek().growInternal(m);
			return ret;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	public JSONObject techcontact(String email, String cc) throws KnownError {
		JSONObject ret = new JSONObject();
		try {
			ret.put("firstName", "Firstname").put("lastName", "Lastname").put("organization", "Organization")
					.put("email", email).put("address", "Address").put("countryCode", cc).put("city", "City");
			return ret;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

}
