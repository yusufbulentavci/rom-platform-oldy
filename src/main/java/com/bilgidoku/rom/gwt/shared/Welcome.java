package com.bilgidoku.rom.gwt.shared;

import java.sql.Timestamp;

public class Welcome implements Transfer{
	
//	email text,
//	hostname int,
//	site text,
//	ip text not null,
//	country text not null,
//	lastactivity timestamp not null default now(),

	public Welcome(){
	}
	
	public Welcome(String email2, Integer hostid, String site2, String ip2, String country2, Timestamp lastactivity2) {
		this.email=email2;
		this.hostid=hostid;
		this.site=site2;
		this.country=country2;
		this.lastactivity=lastactivity2.getTime();
	}
	public String email;
	public Integer hostid;
	public String site;
	public String ip;
	public String country;
	public Long lastactivity;
}
