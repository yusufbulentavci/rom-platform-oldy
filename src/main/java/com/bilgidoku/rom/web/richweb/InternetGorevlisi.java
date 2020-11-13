package com.bilgidoku.rom.web.richweb;

import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.internetapi.RichWebApi;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.charge.FeatureNames;
import com.bilgidoku.rom.web.charge.UcretlendirmeGorevlisi;
import com.bilgidoku.rom.web.http.HttpCallHandler;
import com.bilgidoku.rom.web.http.RomHttpHandler;


@HttpCallServiceDeclare(uri = "/_richweb", name = "Internet", roles = "designer,author,admin", paket="com.bilgidoku.rom.web.richweb")
public class InternetGorevlisi extends GorevliDir implements HttpCallHandler {
	public static final int NO = 37;

	public static InternetGorevlisi tek() {
		if (tek == null) {
			synchronized (InternetGorevlisi.class) {
				if (tek == null) {
					tek = new InternetGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static InternetGorevlisi tek;

	private InternetGorevlisi() {
		super("Internet", NO);
	}
	
	@Override
	public void kur() throws KnownError {
		try {
			JSONObject providers = new JSONObject()
					.safePut("bing",
							new JSONObject().safePut("accountKey", "EejK7ONM+MFQG3f82/nZojFIOuoh00Nj6CqS8P0M3jw="))
					.safePut("fotolia",
							new JSONObject().safePut("apiKey", "api").safePut("user", "user").safePut("pass", "pass"))
					.safePut("yaymicro", new JSONObject().safePut("apiKey", "7bb46b7b-9c99-420e-8aa3-13ee80266cbe"));
			provider = new Provider(providers);
		} catch (JSONException e) {
			throw confError(e);
		}
		
	}

	private static final MC mc = new MC(InternetGorevlisi.class);


	private Provider provider;

	// {
	// "bing":{
	// "accountKey":"EejK7ONM+MFQG3f82/nZojFIOuoh00Nj6CqS8P0M3jw="
	// },
	// "fotolia":{
	// "apiKey":"api",
	// "user":"user",
	// "pass":"pass"
	// },
	// "yaymicro":{
	// "apiKey":"7bb46b7b-9c99-420e-8aa3-13ee80266cbe"
	// }
	// }
	

	@Override
	public void selfDescribe(JSONObject jo) {

	}

	class RichWebAccess {
		public RichWebAccess(RichWebApi fotolia) {
			this.api = fotolia;
		}

		RichWebApi api;
	}

	@Override
	public RomHttpHandler getCustomService() {
		return null;
	}

	private RichWebAccess getAccessByName(int hostId, int p_pr) throws KnownError {
		Assert.notNull(p_pr);
		RichWebApi ret = provider.getAccess(p_pr);
		if (ret == null)
			throw new RuntimeException("Unexpected, db'de var ama if'te yok. Provider:" + p_pr);
		return new RichWebAccess(ret);
	}

	@HttpCallMethod(http = "post")
	public ImageResp[] searchimg(@hcp(n = "a_host") Integer a_host, @hcp(n = "a_contact") String a_contact,
			@hcp(n = "p_pr") Integer p_pr, @hcp(n = "p_limit") Integer p_limit, @hcp(n = "p_offset") Integer p_offset,

			@hcp(n = "p_phrase") String p_phrase, @hcp(n = "p_size") String p_size,
			@hcp(n = "p_aspect") String p_aspect, @hcp(n = "p_style") String p_style,
			@hcp(n = "p_colors") String p_colors, @hcp(n = "p_face") String p_face) throws KnownError {

		destur();

		try {
			RichWebAccess access = getAccessByName(a_host, p_pr);

			ImageResp[] rets = access.api.searchImage(p_limit, p_offset, p_phrase, p_size, p_aspect, p_style, p_colors,
					p_face);

			return rets;
		} catch (KnownError e) {
			throw err(e);
		}
	}

	private static final Astate _buyMedia = mc.c("buy-media");

	@HttpCallMethod(http = "post", audit = "p_provider,p_pid")
	public String buyMedia(@hcp(n = "a_host") Integer a_host, @hcp(n = "a_contact") String a_contact,
			@hcp(n = "p_provider") Integer p_pr, @hcp(n = "p_pid") String p_pid) throws KnownError {

		destur();
		_buyMedia.more();

		Integer tid = null;
		try {
			RichWebAccess access = getAccessByName(a_host, p_pr);

			ImageResp ir = access.api.infoMedia(p_pid);
			Assert.notNull(ir);

			tid = UcretlendirmeGorevlisi.tek().sale(a_host, FeatureNames.imgSale(p_pr), null, a_contact, p_pid, null, null, 1, null,
					null);

			access.api.buyMedia(p_pid);
			return "";
		} catch (KnownError e) {
			if (tid != null) {
				UcretlendirmeGorevlisi.tek().cancelTransaction(a_host, tid, FeatureNames.imgSale(p_pr), null, p_pid, "thirdparty");
			}

			_buyMedia.failed(e, a_host, a_contact, p_pr, p_pid, tid);

			throw e;
		}
	}

	private static final Astate buyDomain = mc.c("buy-domain");
//
//	@Override
//	@HttpCallMethod(http = "post", audit = "a_host,a_contact,p_domainname")
//	public Boolean buyDomain(@hcp(n = "a_host") Integer a_host, @hcp(n = "a_contact") String a_contact,
//			@hcp(n = "p_domainname") String p_domainname, @hcp(n = "p_registrant") Json p_registrant)
//			throws KnownError {
//
//		destur();
//		buyDomain.more();
//
//		Integer tid = null;
//		try {
//
//			Calendar cal = Calendar.getInstance();
//			Long fromDate = cal.getTimeInMillis();
//
//			cal.add(Calendar.YEAR, 1);
//			Long toDate = cal.getTimeInMillis();
//
//			tid = UcretlendirmeGorevlisi.tek().sale(a_host, FeatureNames.buyDomain, p_domainname, a_contact, null, fromDate, toDate, 1,
//					null, null);
//
//			domainService.domainCreate(RomDomain.intra(a_host), p_domainname, p_registrant.getObject(), 1);
//
//			try {
//				KurumGorevlisi.tek().addAlias(RomDomain.intra(a_host), p_domainname);
//			} catch (KnownError e) {
//				GunlukGorevlisi.tek().unfinished("domain.buy", "Couldnt add alias", e, "host_id", a_host, "domaiName",
//						p_domainname);
//				return false;
//			}
//
//			return true;
//		} catch (KnownError e) {
//			if (tid != null) {
//				UcretlendirmeGorevlisi.tek().cancelTransaction(a_host, tid, FeatureNames.buyDomain, p_domainname, null, "thirdparty");
//			}
//
//			buyDomain.failed(e, a_host, a_contact, tid, p_domainname);
//
//			throw e;
//		}
//
//	}

	public boolean isPublic(Integer pr, String pid) {
		return provider.isPublic(pr);
	}

}
