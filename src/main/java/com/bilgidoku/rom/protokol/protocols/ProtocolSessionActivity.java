package com.bilgidoku.rom.protokol.protocols;

import com.bilgidoku.rom.min.Sistem;

public abstract class ProtocolSessionActivity {
	protected long initialLogin;
	protected long lastActivity;

	public void initialLogIn(){
		this.initialLogin=Sistem.millis.millis();
		lastActivity=initialLogin;
	}
	
	public void touch(){
		lastActivity=Sistem.millis.millis();
	}
	
	public abstract boolean isOnline();

}
