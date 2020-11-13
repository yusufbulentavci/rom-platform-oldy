package com.bilgidoku.rom.pg.dict;

public class RomHost {
	private final String hostName;
	private final int hostId;
	private boolean disabled=false;

	
	public RomHost(String hostName, int hostId){
		this.hostName=hostName;
		this.hostId=hostId;
	}
	
	public String getHostName() {
		return hostName;
	}

	public void disable() {
		disabled=true;
	}

	public boolean isDisabled() {
		return disabled;
	}


}