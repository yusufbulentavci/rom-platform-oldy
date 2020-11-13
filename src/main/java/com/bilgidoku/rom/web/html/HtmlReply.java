package com.bilgidoku.rom.web.html;

import java.util.Date;

public class HtmlReply {
	public HtmlReply(Object o, Date t, Long c){
		this.object=o;
		this.cache=c;
		this.ts=t;
	}
	public Object object;
	public Long cache;
	public Date ts;
	
	@Override
	public String toString() {
		return object.toString();
	}
	
	
}
