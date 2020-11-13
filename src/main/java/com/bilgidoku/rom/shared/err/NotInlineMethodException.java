package com.bilgidoku.rom.shared.err;

public class NotInlineMethodException extends Exception{
	private String href;
	public NotInlineMethodException(String href) {
		this.href=href;
	}
	
	

}
