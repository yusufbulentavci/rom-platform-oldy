package com.bilgidoku.rom.gwt.shared;

public class HostStat implements Transfer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8730509501481569685L;
	
	public Integer hostid;
	public String hostname;
	public String mainlang;
	public String[] domainalias;
	public Boolean servable;
	public Boolean forceHttps;
	
	public String toString(){
		return hostname;
	}
}
