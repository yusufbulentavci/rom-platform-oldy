package com.bilgidoku.rom.shared.err;

public class InvalidUriException extends Exception{
	private String uri;
	public InvalidUriException(String uri, String reason){
		super("Uri:"+uri+" Reason:"+reason);
		this.uri=uri;
	}
	
}
