package com.bilgidoku.rom.web.guest;

import java.util.Map;

import com.bilgidoku.rom.gwt.shared.DescResp;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Bmap;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.http.session.AppSession;

@HttpCallServiceDeclare(uri = "/_guest", name = "Misafir", paket="com.bilgidoku.rom.web.guest")
public class MisafirGorevlisi extends GorevliDir {
	public static final int NO = 45;

	public static MisafirGorevlisi tek() {
		if (tek == null) {
			synchronized (MisafirGorevlisi.class) {
				if (tek == null) {
					tek = new MisafirGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static MisafirGorevlisi tek;

	private MisafirGorevlisi() {
		super("Misafir", NO);
	}
	
	@Override
	public void selfDescribe(JSONObject jo) {
		
	}
	
	@HttpCallMethod(http = "post", audit="p_email")
	public DescResp contactform(@hcp(n = "a_session") AppSession session, @hcp(n = "a_lang") String a_lang,
			@hcp(n = "a_rid") String a_rid,
			@hcp(n = "p_title") String title,
			@hcp(n = "p_body") String body,
			@hcp(n = "p_email") String email,
			@hcp(n = "p_phone") String phone,
			@hcp(n = "p_addr") String addr
			) throws KnownError {
		destur();
		
		String hs=KurumGorevlisi.tek().getDeskEmail(session.getIntraHostId());
		if(hs==null){
			throw new KnownError("No desk user");
		}
		
//		recipients,title,body,email,phone,address,user,ip,date
		
		Map<String,Object> bo=new Bmap().key("title").val(title).
				key("body").val(body).key("email").val(email).key("phone").val(phone).key("addr").val(addr).
				key("user").val(session.getaName()).key("ip").val(session.getIpAddress()).
				ret();
		EpostaGorevlisi.tek().sendSystemMail(a_lang, "ContactForm", hs+"@"+session.getEmailDomain(),  bo,
				EpostaGorevlisi.MAILER_DOMAIN_BOT, session.getEmailDomain(), a_rid);

		DescResp dr = new DescResp();
		return dr;

	}


}
