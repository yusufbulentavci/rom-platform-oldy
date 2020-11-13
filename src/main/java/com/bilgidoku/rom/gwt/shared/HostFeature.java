package com.bilgidoku.rom.gwt.shared;

public class HostFeature implements Transfer {
	public String feature;
	public String named;
	public Long expire;
	public Boolean disabled;
	public Integer autorenewperiod;
	public Integer usage;
	public Integer[] usage_hourly;
	public Integer[] usage_daily;
	public Integer[] usage_monthly;
	public String[] refid;
	public Integer[] reasons;

	public HostFeature(String feature, String named, Boolean disabled, Long expire, Integer autorenewperiod, Integer usage,
			Integer[] usage_hourly, Integer[] usage_daily, Integer[] usage_monthly, String[] refid, Integer[] reasons) {
		super();
		this.feature = feature;
		this.named = named;
		this.disabled=disabled;
		this.expire = expire;
		this.autorenewperiod = autorenewperiod;
		this.usage = usage;
		this.usage_hourly = usage_hourly;
		this.usage_daily = usage_daily;
		this.usage_monthly = usage_monthly;
		this.refid = refid;
		this.reasons=reasons;
	}
	
	public HostFeature(){
	}
}
