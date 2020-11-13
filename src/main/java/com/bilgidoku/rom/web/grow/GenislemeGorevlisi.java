package com.bilgidoku.rom.web.grow;

import java.util.Map;

import com.bilgidoku.rom.haber.NodeTalkMethod;
import com.bilgidoku.rom.haber.TalkResult;
import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Bmap;
import com.bilgidoku.rom.ilk.util.Conv;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.DomainName;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.tokens.TokenNew;

public class GenislemeGorevlisi extends GorevliDir {
	public static final int NO = 33;

	public static GenislemeGorevlisi tek() {
		if (tek == null) {
			synchronized (GenislemeGorevlisi.class) {
				if (tek == null) {
					tek = new GenislemeGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static GenislemeGorevlisi tek;

	private GenislemeGorevlisi() {
		super("Genisleme", NO);
	}

	private static final MC mc = new MC(GenislemeGorevlisi.class);

	public void selfDescribe(JSONObject jo) {

	}

	private static final Astate _newhost = mc.c("newhost.newtoken.sendemail");

	@NodeTalkMethod(cmd = "genisle.growtoken")
	public TalkResult growtoken(JSONObject jor) throws KnownError {
		destur();

		_newhost.more();

		try {
			JSONObject jo = TalkUtil.data(jor);
			String business = jo.getString("business");
			String cc = jo.getString("cc");
			String lang = jo.getString("lang");
			String email = jo.getString("email");
			long money = jo.getLong("money");
			String remotetid = jo.getString("remotid");
			String world = jo.getString("world");

			putgrowtoken(business, cc, lang, email, money, remotetid, world);
		} catch (JSONException e) {
			_newhost.fail(1, e, jor.toString());
			return TalkResult.failed;
		}

		return TalkResult.success;

	}

	public String putgrowtoken(String business, String cc, String lang, String email, long money, String remotetid,
			String world) throws JSONException, KnownError {
		String token;
		JSONObject dataClient = new JSONObject();
		dataClient.put("hostname", business);
		dataClient.put("cc", cc);
		dataClient.put("lang", lang);

		JSONObject contact = new JSONObject();
		contact.put("email", email);
		dataClient.put("contact", contact);

		JSONObject dataServer = new JSONObject();
		dataServer.put("email", email);
		dataServer.put("money", money);

		// TODO: grow belli bir local world'e gonderilmek zorunda degil
		token = new TokenNew().create(1, remotetid, world, "genisle.grow", dataClient, dataServer, Conv.dayToMin(7));

		Map<String, Object> bo = new Bmap().key("hostname").val(business).key("token").val(token).ret();
		EpostaGorevlisi.tek().sendSystemMail(lang, "activation", email, bo, EpostaGorevlisi.tek().MAILER_TLOS, null,
				remotetid);
		return token;
	}

	private static final Astate _grow = mc.c("grow.f.hostname.admin");
	
	
	@NodeTalkMethod(cmd = "genisle.daral")
	public TalkResult daral(JSONObject jo) {
		destur();
		_grow.more();
		try {
			daralInternal(jo);
			return TalkResult.success;
		} catch (JSONException e) {
			_grow.failed(e, jo);
			return TalkResult.failed;
		} catch (KnownError e) {
			_grow.failed(e, jo);
			return TalkResult.failed;
		}
	}
	
	public int daralInternal(JSONObject jo) throws JSONException, KnownError {
		JSONObject d = jo.getJSONObject("d");
		JSONObject client = d.getJSONObject("c");

		String hostName = client.optString("hostname", null);

		RmHostDbOp dbOp = new RmHostDbOp();
		int hostId = dbOp.rm(hostName);
		KurumGorevlisi.tek().updateHosts();
		return hostId;
//		if(manual==null || !manual){
//			JSONObject cr=new JSONObject();
//			cr.put("domainname", hostName);
//			cr.put("hostid", hostId);
//			cr.put("contact", contact);
//			String cmd;
//			if(authInfo==null || authInfo.trim().length()==0){
//				cmd="domain.create";
//				cr.append("years", 1);
//			}else{
//				cmd="domain.transfer";
//				cr.append("authinfo", authInfo);
//			}
//			JSONObject tosend = TalkUtil.m(hostId, cmd, cr);
//			TalkUtil.rid(tosend, rid);
//			RunTime.talking(tosend);
//		}
//		
//		EpostaGorevlisi.tek().createDomain(hostId, hostName);
//		EpostaGorevlisi.tek().mailSubscription(hostId, adminName);
	}
	

	@NodeTalkMethod(cmd = "genisle.grow")
	public TalkResult grow(JSONObject jo) {
		destur();
		_grow.more();
		try {
			growInternal(jo);
			return TalkResult.success;
		} catch (JSONException e) {
			_grow.failed(e, jo);
			return TalkResult.failed;
		} catch (KnownError e) {
			_grow.failed(e, jo);
			return TalkResult.failed;
		}
	}

	public int growInternal(JSONObject jo) throws JSONException, KnownError {
		JSONObject d = jo.getJSONObject("d");
		JSONObject client = d.getJSONObject("c");
		JSONObject server = d.optJSONObject("s");

		String hostName = client.optString("hostname", null);

		if (hostName != null) {
			DomainName dn = new DomainName(hostName);
			if (dn.getTopLevelName() == null) {
				_grow.failed("Invalid host name:" + hostName, jo);
				throw new KnownError("Invalid host name:" + hostName);
			}
			hostName = dn.getTopLevelName();
		}

		String lang = client.getString("lang");
		JSONObject contact = client.getJSONObject("contact");
		String adminName = client.optString("admin");
		if (adminName == null || adminName.length() > 40) {
			_grow.failed("Invalid admin name:" + adminName, jo);
			throw new KnownError("Invalid admin name:" + adminName);
		}

		String credential = client.getString("credential");
		Boolean manual = client.optBoolean("manual");
		String authInfo = client.optString("authinfo");

		String payerId=null,rid=null; 
		long money=0;
		if(server!=null) {
			payerId = server.optString("payerid");
			rid = server.optString("rid");
			money = server.optLong("money", 0);
		}

		NewHostDbOp dbOp = new NewHostDbOp();
		int hostId = dbOp.create(hostName, lang, contact.toString(), payerId, adminName, credential, rid, money, null,
				null);
		KurumGorevlisi.tek().updateHosts();
		return hostId;
//		if(manual==null || !manual){
//			JSONObject cr=new JSONObject();
//			cr.put("domainname", hostName);
//			cr.put("hostid", hostId);
//			cr.put("contact", contact);
//			String cmd;
//			if(authInfo==null || authInfo.trim().length()==0){
//				cmd="domain.create";
//				cr.append("years", 1);
//			}else{
//				cmd="domain.transfer";
//				cr.append("authinfo", authInfo);
//			}
//			JSONObject tosend = TalkUtil.m(hostId, cmd, cr);
//			TalkUtil.rid(tosend, rid);
//			RunTime.talking(tosend);
//		}
//		
//		EpostaGorevlisi.tek().createDomain(hostId, hostName);
//		EpostaGorevlisi.tek().mailSubscription(hostId, adminName);
	}

	public Integer newHost(String hostName, String email, String adminName, String password, String cc, String lang)
			throws KnownError {
		try {
			JSONObject dataClient = new JSONObject();
			dataClient.put("hostname", hostName);
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
			// dataServer.put("money", 100000);

			JSONObject jo = new JSONObject();
			jo.put("s", dataServer);
			jo.put("c", dataClient);

			JSONObject m = new JSONObject();
			m.put("d", jo);

			Integer ret = growInternal(m);
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
