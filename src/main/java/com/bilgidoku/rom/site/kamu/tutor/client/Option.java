package com.bilgidoku.rom.site.kamu.tutor.client;

public class Option {
	final int index;
	final String name;
	String code;
	final String summary;
	
	final int imgInd;
	final Vector2d imgloc;
	final int selectIndex;
	
	public Option(int index, String name, String code, int imgInd, Vector2d imgloc, int selectIndex, String summary) {
		super();
		this.index = index;
		this.name = name;
		this.code=code;
		this.imgInd = imgInd;
		this.imgloc = imgloc;
		this.selectIndex = selectIndex;
		this.summary=summary;
	}
	
	public String note(){
		if(code==null||code.length()<"mnote:".length())
			return null;
		return code.substring("mnote:".length());
	}

}
