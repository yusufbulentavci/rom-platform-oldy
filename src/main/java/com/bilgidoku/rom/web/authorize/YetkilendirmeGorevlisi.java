package com.bilgidoku.rom.web.authorize;

import java.util.Map;

import com.bilgidoku.rom.gwt.shared.DescResp;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Bmap;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.internetapi.api.SocialOne;
import com.bilgidoku.rom.internetapi.fb.FbApi;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.session.dbop.ConfirmedCid;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.dbop.CidByEmailDbOp;
import com.bilgidoku.rom.web.dbop.NewContactDbOp;
import com.bilgidoku.rom.web.dbop.hosts.NestedOfHostDbOp;
import com.bilgidoku.rom.web.http.RomHttpHandler;
import com.bilgidoku.rom.web.http.session.AppSession;
import com.bilgidoku.rom.web.http.session.AppSessionExtension;
import com.bilgidoku.rom.web.tokens.TokenNew;
import com.bilgidoku.rom.yerel.YerelGorevlisi;

@HttpCallServiceDeclare(uri = "/_auth", name = "Yetkilendirme", paket="com.bilgidoku.rom.web.authorize")
public class YetkilendirmeGorevlisi extends GorevliDir {

	public static final int NO=26;
		
		public static YetkilendirmeGorevlisi tek(){
			if(tek==null) {
				synchronized (YetkilendirmeGorevlisi.class) {
					if(tek==null) {
						tek=new YetkilendirmeGorevlisi();
						tek.giris();
					}
				}
			}
			return tek;
		}
		
		static YetkilendirmeGorevlisi tek;
		private YetkilendirmeGorevlisi() {
			super("Yetkilendirme", NO);
			OturumGorevlisi.tek().addExtension(AppSessionExtension.one());
			appSessionExtension = (AppSessionExtension) OturumGorevlisi.tek().getExtension(AppSessionExtension.APP);
		}
	
	
	final static private MC mc = new MC(YetkilendirmeGorevlisi.class);
	private final AppSessionExtension appSessionExtension;
	private final FbApi fbApi = new FbApi();



	public void selfDescribe(JSONObject jo) {
	}

	final static private Astate reloginWarn = mc.c("relogin");
	final static private Astate lsc = mc.c("login-success");

	private static final Astate _login = mc.c("login");

	@HttpCallMethod(http = "post", audit = "p_user")
	public DescResp login(@hcp(n = "a_session") AppSession session, @hcp(n = "a_domain") RomDomain domain,
			@hcp(n = "p_user") String user, @hcp(n = "p_credential") String credential) throws KnownError {
		destur();
		_login.more();

		try {
			if (!(session.isGuest() || session.isNoAuth())) {
				reloginWarn.more();
				throw new KnownError();
			}
			
			int suc = appSessionExtension.authenticate(session, user, credential, false, null, false, false);
			DescResp dr = new DescResp();
			if (suc < 0) {
				dr.romerror = "Login failed!";
				return dr;
			}
			lsc.more();
			return dr;
		} catch (KnownError e) {
			_login.fail(session);
			throw e;
		}
	}

	final static private Astate guestLogout = mc.c("guest-logout");
	final static private Astate logoutCount = mc.c("logout-count");

	@HttpCallMethod(http = "post", roles = "contact", audit = "")
	public DescResp logout(@hcp(n = "a_session") AppSession session) throws KnownError {
		destur();

		if (!session.authenticated()) {
			guestLogout.more();
			throw new KnownError();
		}

		appSessionExtension.logout(session);
		logoutCount.more();
		DescResp dr = new DescResp();
		return dr;

	}

	final static private Astate c1 = mc.c("check");

	@HttpCallMethod(http = "post")
	public String check(@hcp(n = "a_session") AppSession session) throws KnownError {
		destur();
		c1.more();
		if (session.isGuest() || session.isUser())
			return session.getUserName();
		// This is contact
		return session.getCname();
	}


	public RomHttpHandler getCustomService() {
		throw new RuntimeException("Custom service not available");
	}

	private static final Astate _registerauthfailed = mc.c("registered-auth-failed");
	private static final Astate _register = mc.c("register");

	@HttpCallMethod(http = "post", audit = "p_email")
	public DescResp register(@hcp(n = "a_session") AppSession session, @hcp(n = "a_domain") RomDomain domain,
			@hcp(n = "a_lang") String a_lang, @hcp(n = "p_firstname") String p_firstname,
			@hcp(n = "p_lastname") String p_lastname, @hcp(n = "p_countrycode") String p_countrycode,
			@hcp(n = "p_email") String p_email, @hcp(n = "p_credential") String p_credential) throws KnownError {
		destur();
		_register.more();
		try {
			if (!(session.isGuest() || session.isAuth())) {
				reloginWarn.more();
				throw new KnownError();
			}

			DescResp dc = new DescResp();

			if (p_email == null) {
				dc.romerror = "email field is empty";
				return dc;
			}

			ConfirmedCid ccid = OturumGorevlisi.tek().getCidByEmail(session.getIntraHostId(), p_email);
			if (ccid != null) {
				dc.romerror = "Is already a contact.";
				return dc;
			}

			String cid = new NewContactDbOp().doIt(session.getIntraHostId(), a_lang, p_countrycode, p_email,
					p_credential, p_firstname, p_lastname, null, null, null, session.getIpAddress());

			int suc = appSessionExtension.authenticate(session, p_email, p_credential, true, cid, false, false);
			if (suc < 0) {
				_registerauthfailed.more();
				throw new KnownError();
			}
			return dc;

		} catch (KnownError e) {
			_register.failed(e, session, a_lang, p_firstname, p_lastname, p_countrycode, p_email, p_credential);
			throw e;
		}
	}

	// @HttpCallMethod(http = "post")
	// public DescResp upload(@hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_user") String user,
	// @hcp(n = "p_credential") String credential) throws KnownError,
	// RomRequestException {
	// rc.more();
	// if (!session.isGuest()) {
	// reloginWarn.more();
	// throw new RomRequestException(HttpResponseStatus.FORBIDDEN,
	// "You already have a user session; logout first");
	// }
	// boolean suc = OturumGorevlisi.tek().authenticate(session, user,
	// credential);
	// DescResp dr=new DescResp();
	// if (!suc) {
	// dr.romerror="Login failed!";
	// return dr;
	// }
	// lsc.more();
	// return dr;
	// }

	@HttpCallMethod(http = "post", audit = "p_email")
	public Boolean forgetcontactpass(@hcp(n = "a_host") Integer a_host, @hcp(n = "a_lang") String a_lang,
			@hcp(n = "p_email") String p_email) throws KnownError {

		destur();

		String pwd = OturumGorevlisi.tek().getContactPwdByEmail(a_host, p_email);
		if (pwd == null)
			return false;

		// mailService.sendSystemMail(a_lang, "reset", rcpt, params);
		// resetContactPassword(a_lang, p_email);
		//
		// TokenNew token = new TokenNew().create(1, null, ":grow", "tlos.grow",
		// dataClient, dataServer,
		// Conv.dayToMin(7));
		//
		// Map<String,Object> bo=new
		// Bmap().key("hostname").val(p_business).key("token").val(token).ret();
		// mailService.sendSystemMail(p_lang, "activation", p_email, bo);
		//
		return true;
	}

	@HttpCallMethod(http = "post", audit = "p_email")
	public Boolean forgetpass(@hcp(n = "a_session") AppSession a_session, @hcp(n = "a_lang") String a_lang,
			@hcp(n = "a_rid") String a_rid, @hcp(n = "p_email") String p_email) throws KnownError {

		destur();

		String userName = OturumGorevlisi.tek().getUserByEmail(a_session.getIntraHostId(), p_email);
		if (userName == null) {
			userName = new CidByEmailDbOp().doit(a_session.getIntraHostId(), p_email);
			if (userName == null) {
				return false;
			}
		}

		JSONObject serverJo = new JSONObject();
		try {
			serverJo.put("user", userName);
			String token = new TokenNew().create(a_session.getIntraHostId(), null, Genel.getHostName(),
					"user.changepass", new JSONObject(), serverJo, 60);
			String domain = a_session.getCookieDomainName();
			Map<String, Object> bo = new Bmap().key("domain").val(domain).key("token").val(token).ret();
			EpostaGorevlisi.tek().sendSystemMail(a_lang, "ChangePassword", p_email, bo, EpostaGorevlisi.MAILER_DOMAIN_BOT, domain,
					a_rid);

			return true;
		} catch (JSONException e) {
			throw new KnownError(e);
		}

	}

	@HttpCallMethod(http = "post", audit = "p_email")
	public String extplay(@hcp(n = "a_session") AppSession a_session, @hcp(n = "p_platform") String platform)
			throws KnownError {
		destur();
		JSONObject jo = new NestedOfHostDbOp().doit(a_session.getIntraHostId());
		if (jo == null)
			return null;
		jo = jo.optJSONObject("plats");
		if (jo == null)
			return null;
		jo = jo.optJSONObject(platform);
		if (jo == null)
			return null;
		return jo.optString("play");
	}

	// https://graph.facebook.com/me?access_token=CAAMvY9tDo8gBAMLoUdfB2DnT0pmZBmkP2ZA2Wv45BiMRbVkDqO7kW7XQ8WiUwtuUNEKeZAo2SJMrv8uEuQrl2ulNg9L09dWZBZCceKk3UkCJHBst6jQXipMlBArYiHepYOCDq2ZBJZC8ScWrf4BdAzOlwVKv69okNvniBw9gEw5wqvDZCHaFEQwJipPdG4fdZAdrmIlZBVg7SYLAZDZD
	// {
	// "id": "1474614949454414",
	// "email": "tlosnet00\u0040gmail.com",
	// "first_name": "Yusuf B\u00fclent",
	// "gender": "male",
	// "last_name": "Avc\u0131",
	// "link": "https://www.facebook.com/app_scoped_user_id/1474614949454414/",
	// "locale": "tr_TR",
	// "name": "Yusuf B\u00fclent Avc\u0131",
	// "timezone": 3,
	// "updated_time": "2015-09-07T13:04:56+0000",
	// "verified": true
	// }

	private static final Astate _trustedloginattempt = mc.c("trusted-login-attempt");

	@HttpCallMethod(http = "post", audit = "p_user")
	public Boolean trustedplatformlogin(@hcp(n = "a_session") AppSession session,
			@hcp(n = "p_platform") String platform, @hcp(n = "p_accesstoken") String accesstoken) throws KnownError {
		destur();
		_trustedloginattempt.more();

		try {
			if (!(session.isGuest() || session.isNoAuth())) {
				reloginWarn.more();
				throw new KnownError();
			}

			if (platform.equals("facebook")) {
				boolean upgrade=false;

				SocialOne so = fbApi.socialOneAccessToken(accesstoken);

				if (so == null) {
					return false;
				}

				String[] lcodes = YerelGorevlisi.tek().resolveLocale(so.locale);

				ConfirmedCid ccid = OturumGorevlisi.tek().getCidByPlatform(session.getIntraHostId(), "facebook", so.id);
				String cid = null;
				if (ccid == null) {
					
					if(session.getCid()==null){
						cid = new NewContactDbOp().doIt(session.getIntraHostId(), lcodes[0], lcodes[1], null, null,
								so.firstName, so.lastName, so.id, null, null, session.getIpAddress());
					}else{
						DbThree db3 = new DbThree("update rom.contacts "
								+ "set first_name=?,last_name=?,fb_id=? "
								+ "where host_id=? and uri=?");
						db3.setString(so.firstName);
						db3.setString(so.lastName);
						db3.setString(so.id);
						db3.setInt(session.getIntraHostId());
						db3.setString(session.getCid());
						db3.checkedExecute();
						upgrade=true;
					}
					
					

					if (cid == null)
						return false;

				} else {
					cid = ccid.cid;
				}

				String userName = OturumGorevlisi.tek().getUserByCid(session.getIntraHostId(), cid);
				
				if(userName!=null){
					_trustedloginattempt.failed();
					return false;
				}

				appSessionExtension.authenticate(session, userName, null, true, cid, false, upgrade);

				return true;
			}

			return false;

		} catch (KnownError e) {
			_trustedloginattempt.fail(session, e);
			return false;
		}
	}

	private static final Astate _letmecontact = mc.c("letmecontact");

	@HttpCallMethod(http = "post")
	public Boolean letmecontact(@hcp(n = "a_session") AppSession session) throws KnownError {
		destur();
		_trustedloginattempt.more();

		try {
			if (!session.isGuest()) {
				reloginWarn.more();
				throw new KnownError();
			}

			String cid = new NewContactDbOp().doIt(session.getIntraHostId(), session.getCountryLang(),
					session.getCountry(), null, null, null, null, null, null, null, session.getIpAddress());

			appSessionExtension.authenticate(session, null, null, false, cid, true, false);

			return true;

		} catch (KnownError e) {
			_letmecontact.fail(session, e);
			return false;
		}
	}

}
