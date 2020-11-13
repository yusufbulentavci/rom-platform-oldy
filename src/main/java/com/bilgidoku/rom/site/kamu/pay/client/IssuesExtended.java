package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.Issues;

public class IssuesExtended {
	private Contacts con;
	private Issues com;
	
	public IssuesExtended(Issues com) {
		this.setCom(com);		
	}

	public Contacts getCon() {
		return con;
	}

	public void setCon(Contacts con) {
		this.con = con;
	}

	public Issues getCom() {
		return com;
	}

	public void setCom(Issues com) {
		this.com = com;
	}

	public String delegated() {
		return com.delegated;
	}

}
