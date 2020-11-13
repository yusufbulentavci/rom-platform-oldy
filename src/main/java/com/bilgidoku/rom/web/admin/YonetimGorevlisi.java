package com.bilgidoku.rom.web.admin;

import java.io.File;

import com.bilgidoku.rom.gwt.shared.RomCert;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.RetStream;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.shared.err.KnownError;

@HttpCallServiceDeclare(uri = "/_admin", name = "Yonetim", paket="com.bilgidoku.rom.web.admin")
public class YonetimGorevlisi extends GorevliDir {

	public static final int NO = 0;

	public static YonetimGorevlisi tek() {
		if (tek == null) {
			synchronized (YonetimGorevlisi.class) {
				if (tek == null) {
					tek = new YonetimGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static YonetimGorevlisi tek;

	private YonetimGorevlisi() {
		super("Yonetim", NO);
	}



	@HttpCallMethod(http = "post", roles = "admin")
	public String[] keys(@hcp(n = "a_host") int a_host) throws KnownError {
		destur();
		//return secureService.keysOfHost(a_host);
		return null;
	}
	
	@HttpCallMethod(http = "post", roles = "admin")
	public RomCert[] getHostCerts(@hcp(n = "a_host") int a_host) throws KnownError {
		destur();
		//return secureService.certsOfHost(a_host);
		return null;
	}

	@HttpCallMethod(http = "post", roles = "admin")
	public RomCert addCert(@hcp(n = "a_host") int a_host,@hcp(n = "p_alias") String p_alias, @hcp(n = "f_cert") File f_cert) throws KnownError {
		destur();
		//return secureService.addCert(a_host, p_alias, f_cert);
		return null;
	}
	
	@HttpCallMethod(http = "post", roles = "admin")
	public Boolean removeCert(@hcp(n = "a_host") int a_host,@hcp(n = "p_alias") String p_alias) throws KnownError {
		destur();
		//return secureService.removeCert(a_host, p_alias);
		return null;
	}
	
	@HttpCallMethod(http = "post", roles = "admin")
	public Boolean updateKeys(@hcp(n = "a_host") int a_host,@hcp(n = "p_alias") String p_alias) throws KnownError {
		destur();
		//return secureService.updateKeys(a_host, p_alias);
		return null;
	}
	
	
	@HttpCallMethod(http = "post", roles = "admin", file = 1)
	public RetStream getCsr(@hcp(n = "a_host") int a_host,@hcp(n = "p_alias") String p_alias) throws KnownError {
		destur();
		//return secureService.getCsr(a_host, p_alias);
		return null;
	}
	
	@HttpCallMethod(http = "post", roles = "admin", file = 1)
	public RetStream getPrivate(@hcp(n = "a_host") int a_host,@hcp(n = "p_alias") String p_alias) throws KnownError {
		destur();
		//return secureService.getPrivate(a_host, p_alias);
		return null;
	}
	
	@HttpCallMethod(http = "post", roles = "admin", file = 1)
	public RetStream getPublic(@hcp(n = "a_host") int a_host,@hcp(n = "p_alias") String p_alias) throws KnownError {
		destur();
		//return secureService.getPublic(a_host, p_alias);
		return null;
	}
	

}
