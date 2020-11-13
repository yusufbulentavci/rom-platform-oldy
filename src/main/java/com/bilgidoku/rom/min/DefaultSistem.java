package com.bilgidoku.rom.min;

public class DefaultSistem implements ISistem {

	private String user = "rompg";
	private String userDir = "/home/rompg";

	public DefaultSistem() {
	}
	
	@Override
	public String getRomUser() {
		return user;
	}

	@Override
	public String getRomUserDir() {
		return userDir;
	}
	
	



}
