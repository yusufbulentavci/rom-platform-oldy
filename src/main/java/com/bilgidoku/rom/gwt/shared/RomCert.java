package com.bilgidoku.rom.gwt.shared;

public class RomCert implements Transfer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Integer hostId;
	public String alias;
	public String issuer;
	public Boolean test;
	public Long notBefore;
	public Long notAfter;
	public String serial;
	public RomCert(){
	}

	public RomCert(Integer hostId, String alias, String issuer, Boolean test, Long notBefore, Long notAfter,
			String serial) {
		super();
		this.hostId = hostId;
		this.alias = alias;
		this.issuer = issuer;
		this.test = test;
		this.notBefore = notBefore;
		this.notAfter = notAfter;
		this.serial = serial;
	}

	

//	public JSONObject toJson() {
//		return new JSONObject().safePut("hostId", hostId).safePut("alias", alias).safePut("issuer", issuer)
//				.safePut("test", test).safePut("notBefore", notBefore.toString())
//				.safePut("notAfter", notAfter.toString()).safePut("serial", serial.toString());
//	}
}