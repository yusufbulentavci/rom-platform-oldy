package com.bilgidoku.rom.web.account;

import com.bilgidoku.rom.gwt.shared.Account;
import com.bilgidoku.rom.gwt.shared.Feature;
import com.bilgidoku.rom.gwt.shared.HostFeature;
import com.bilgidoku.rom.gwt.shared.Tariff;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.account.dbop.GetAccountDbOp;
import com.bilgidoku.rom.web.account.dbop.ListFeaturesDbOp;
import com.bilgidoku.rom.web.account.dbop.ListHostFeauteDbOp;
import com.bilgidoku.rom.web.account.dbop.ListModelTariffsDbOp;
import com.bilgidoku.rom.web.http.RomHttpHandler;

@HttpCallServiceDeclare(uri = "/_account", name = "Hesap", roles = "designer,author,admin", paket="com.bilgidoku.rom.web.account")
public class HesapGorevlisi extends GorevliDir {
	public static final int NO = 0;

	public static HesapGorevlisi tek() {
		if (tek == null) {
			synchronized (HesapGorevlisi.class) {
				if (tek == null) {
					tek = new HesapGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static HesapGorevlisi tek;

	private HesapGorevlisi() {
		super("Hesap", NO);
	}


	public RomHttpHandler getCustomService() {
		return null;
	}


	@HttpCallMethod(http = "post")
	public HostFeature[] hostfeatures(@hcp(n = "a_host") Integer a_host) throws KnownError {
		return new ListHostFeauteDbOp().doit(a_host);
	}


	@HttpCallMethod(http = "post")
	public Tariff[] tariffs(@hcp(n = "a_host") Integer a_host) throws KnownError {
		return new ListModelTariffsDbOp().doit(a_host);
	}


	@HttpCallMethod(http = "post")
	public Account account(@hcp(n = "a_host") Integer a_host) throws KnownError {
		return new GetAccountDbOp().doit(a_host);
	}


	@HttpCallMethod(http = "post")
	public Feature[] features() throws KnownError {
		return new ListFeaturesDbOp().doit();
	}


	public void selfDescribe(JSONObject jo) {

	}

}
