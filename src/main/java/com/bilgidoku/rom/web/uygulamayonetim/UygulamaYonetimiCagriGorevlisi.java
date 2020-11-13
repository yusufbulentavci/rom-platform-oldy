package com.bilgidoku.rom.web.uygulamayonetim;

import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Gorevli;
import com.bilgidoku.rom.web.http.HttpCallHandler;
import com.bilgidoku.rom.web.http.RomHttpHandler;

@HttpCallServiceDeclare(uri = "/_directmethods", name = "UygulamaYonetimiCagri",direct=true, paket="com.bilgidoku.rom.web.uygulamayonetim")
public class UygulamaYonetimiCagriGorevlisi extends Gorevli implements HttpCallHandler{
	public static final int NO=41;
		
		public static UygulamaYonetimiCagriGorevlisi tek(){
			if(tek==null) {
				synchronized (UygulamaYonetimiCagriGorevlisi.class) {
					if(tek==null) {
						tek=new UygulamaYonetimiCagriGorevlisi();
						tek.giris();
					}
				}
			}
			return tek;
		}
		
		static UygulamaYonetimiCagriGorevlisi tek;
		private UygulamaYonetimiCagriGorevlisi() {
			super("UygulamaYonetimiCagri", NO);
		}



	/**
	 * 
	http://www.tlos.net/oauth_callback/?id=2939&code=AQCx5LBkPUOvWjEjK8PrgM1YQV_3QPj5g1beDKkmtk3635_-wmjCKP0j9ZAnd-Pr-7hPIXjlGs57J5EUtt0IV_wAiMb_DqSvedTdj1PSt4z-frSNBgX7Lt23QC9OEHNlc8P8GLtSMZF2DmOz9MBdNMzkh9CU83tUcVTlIsH6D4xop3w8FPgua-G0RrwgTLxH2jft6uM0zq42Asyvixw5pmpVk8laJ-hWYooPnVI84iiME4BTBnHAckviSnchrgx1tHgOFVcPzHuc7khDEY8KLTPHCqKj-P-mM2XOMBjmH92nydg9Yr4DT7VceUOuTGcK7kVLD2CdHnIK2npE3nD7NMC_#_=_
	 */

	@HttpCallMethod(http = "post")
	public String fbcb(@hcp(n = "p_cbid") String cbid, 
			@hcp(n = "p_code") String code, 
			@hcp(n = "p_error_code") Integer error_code,
			@hcp(n = "p_error_message") String error_message) throws KnownError {
		destur();
		
//		authorizeService.cbResponse(cbid, code!=null, code);
		return "ok";
//		try {
//			if (!session.isGuest()) {
//				reloginWarn.more();
//				throw new KnownError();
//			}
//			int suc = sessionService.authenticate(session, user, credential);
//			DescResp dr = new DescResp();
//			if (suc<0) {
//				dr.romerror = "Login failed!";
//				return dr;
//			}
//			lsc.more();
//			return dr;
//		} catch (KnownError e) {
//			_login.fail(session);
//			throw e;
//		}
	}

	@Override
	public RomHttpHandler getCustomService() {
		return null;
	}
	
	
}
