package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.ilk.util.HostingUtils;
import com.bilgidoku.rom.izle.MC;

public class RomDomain {
	final static private MC mc = new MC(RomDomain.class);

	private final boolean home;
	private final String domainName;
	private String emailDomain;
	private final String cookieDomain;
	private final boolean emailBanned;
	
	private Boolean cacheEnabled;
	
	private final int hostId;
	private final int intra;
	private final int inter;
	private final boolean isIntra;
	
//	private final String lang;
//	private final String mainLang;

//	public String getMainLang() {
//		return mainLang;
//	}

	private boolean disabled=false;

	private final boolean forceHttps;

	public RomDomain(boolean home, String domainName, String cookieDomain, boolean emailBanned, 
			Boolean cacheEnabled, String emailDomain, int hostId, boolean forceHttps
//			, String lang, String mainLang
			) {
		this.home=home;
		this.domainName=domainName; 
		this.emailDomain=emailDomain;
		this.emailBanned=emailBanned||emailDomain==null;
		this.cookieDomain=cookieDomain;
		this.cacheEnabled=cacheEnabled;
		this.forceHttps=forceHttps;
//		this.mainLang=mainLang;
//		this.lang=((lang == null) ? mainLang : lang);
		
		this.hostId=hostId;
//		Check.beTrue(hostId>0);
		
		this.intra=HostingUtils.hostIdIntra(hostId);
		this.inter=HostingUtils.hostIdInter(hostId);
		this.isIntra=HostingUtils.isIntra(hostId);
	}

	public Boolean getCacheEnabled() {
		return cacheEnabled;
	}

	public String getDomainName() {
		return domainName;
	}
	
	public String getEmailDomain() {
		return emailDomain;
	}
	
	public String getTopLevel() {
		return emailDomain;
	}

//	public RomHost getHost() {
//		return host;
//	}
	
//	public int getEffectiveHostId(){
//		if(administration)
//			return host.getHostId();
//		
//		return host.getHostId()+1;
//	}
//	
//	private final Astate fah=mc.c("get-admin-host-in-public");
//	public int getAdminHostId(){
////		if(!administration){
////			fah.exception(true,"Domain name:"+this.getDomainName());
////		}
//		return host.getHostId();
//	}
//
	public int getHostId() {
		return hostId;
	}
	
	public int getIntra() {
		return intra;
	}

	public int getInter() {
		return inter;
	}

	public boolean isIntra() {
		return isIntra;
	}

	public void setEmailDomain(String email2) {
		this.emailDomain=email2;
	}


	public void synch(RomDomain changed) {
		this.cacheEnabled=changed.cacheEnabled;
		this.emailDomain=changed.emailDomain;

	}

	public void disable() {
		this.disabled=true;
	}
	public boolean isDisabled(){
		return disabled;
	}

//	public String getLang() {
//		return lang;
//	}

	public static int intra(int id) {
		if((id%2)==0)
			return id-1;
		return id;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public boolean isEmailBanned() {
		return emailBanned;
	}

	public boolean isHome() {
		return home;
	}

	public boolean isForceHttps() {
		return forceHttps;
	}
	
//	
//	private final Astate x1=mc.c("get-email-host");
//	public int getEmailHostId(){
//		if(administration){
//			fah.exception(true,"Domain name:"+this.getDomainName());
//		}
//		return host.getHostId();
//	}
//	
//	private final Astate fah2=mc.c("get-public-host-in-admin");
//	public int getPublicHostId(){
//		if(administration){
//			fah2.exception(true,"Domain name:"+this.getDomainName());
//		}
//		return host.getHostId()+1;
//	}
	
	
}