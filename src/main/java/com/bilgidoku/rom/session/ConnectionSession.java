package com.bilgidoku.rom.session;

import com.bilgidoku.rom.ilk.json.JSONObject;

import io.netty.channel.ChannelHandler;

public interface ConnectionSession extends ChannelHandler {
	public int moduleId();
	public String appName();
	public String conId();
	public JSONObject report();
	public void close();
	public void msg(int mode, String from, String title, String body);
	public boolean inStreamingMode();
	public String getCountry();
	public String getIp();

	public void setCountry(String country);
	public void setIp(String ip);
	

}
