package com.bilgidoku.rom.site.yerel.mail.core;


public class InternetAddress {

	private String address;
	private String personal;

	public InternetAddress(String addr, String pers) {
		this.address=addr;
		this.personal=pers;
	}

	public String getAddress() {
		return address;
	}

	public String getPersonal() {
		return personal;
	}

	public String personalPreferred() {
		if(personal!=null)
			return personal;
		return address;
	}

}
