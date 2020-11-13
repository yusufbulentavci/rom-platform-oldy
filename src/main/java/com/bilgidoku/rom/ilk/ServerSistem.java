package com.bilgidoku.rom.ilk;

import com.bilgidoku.rom.min.ISistem;

public class ServerSistem implements ISistem {

	private final String user;
	private final String userDir;

	public ServerSistem() {
		this.user=System.getenv("USER");
		this.userDir = System.getProperty("user.home");
		if(user==null)
			throw new RuntimeException("USER is null; It shouldnt be!");
		if(this.user.equalsIgnoreCase("root"))
			throw new RuntimeException("USER is ROOT; It shouldnt be!");
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
