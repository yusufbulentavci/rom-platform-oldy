package com.bilgidoku.rom.site.yerel.comment;

import com.bilgidoku.rom.gwt.araci.client.rom.Comments;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;

public class CommentsExtended{
	
	private Contacts con;
	private Comments com;
	
	public CommentsExtended(Comments com) {
		this.setCom(com);		
	}

	public Contacts getCon() {
		return con;
	}

	public void setCon(Contacts con) {
		this.con = con;
	}

	public Comments getCom() {
		return com;
	}

	public void setCom(Comments com) {
		this.com = com;
	}

	public String delegated() {
		return com.delegated;
	}
	
}
