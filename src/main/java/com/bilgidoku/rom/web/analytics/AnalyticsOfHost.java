package com.bilgidoku.rom.web.analytics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.web.analytics.useragent.UserAgent;
import com.bilgidoku.rom.min.Sistem;

public class AnalyticsOfHost {
	final Map<String,Integer> referrers=new HashMap<String,Integer>();
	final Map<String,Integer> bringingWords=new HashMap<String,Integer>();
	final Map<String,Integer> langs=new HashMap<String,Integer>();
	final Map<String,Integer> countries=new HashMap<String,Integer>();
	final Map<String,Integer> browsers=new HashMap<String,Integer>();
	final Map<String, Integer> pageVisitsTime=new HashMap<String, Integer>();
	final Map<String, Integer> pageVisitsCount=new HashMap<String, Integer>();
	Long startTime;
	
	long cpuUsage;
	long netRead;
	long netWrite;
	
	
	public AnalyticsOfHost(int hostId, long startTime) {
		this.startTime=startTime;
	}


	public void addReferrer(String host) {
		more(referrers, host);
	}

	private void more(Map<String,Integer> m, String host) {
		Integer i=m.get(host);
		if(i==null){
			m.put(host, 1);
		}else{
			m.put(host, i+1);
		}
	}
	
	private void more(Map<String, Integer> m, String host,
			int count) {
		Integer i=m.get(host);
		if(i==null){
			m.put(host, count);
		}else{
			m.put(host, i+count);
		}
	}

	public void mergeBringingWords(String[] q) {
		for(String qw:q){
			if(qw.length()<2)
				continue;
			more(bringingWords, qw);
		}
	}

	//userAgent.getBrowser().getGroup().getName(), userAgent.getBrowserVersion(), userAgent.getBrowser().getBrowserType().getName(), 
	public synchronized void appendCounters(String country, String lang,
			UserAgent userAgent, Map<String, PageVisit> pvs, long cpu, long netRead, long netWrite) {
		more(langs, lang);
		more(countries, country);
		more(browsers, userAgent.getBrowser().getGroup().getName());
		if(startTime==null)
			startTime=System.currentTimeMillis();
		
		for (Entry<String, PageVisit> it : pvs.entrySet()) {
			more(pageVisitsTime, it.getKey(), it.getValue().time);
			more(pageVisitsCount, it.getKey(), it.getValue().count);
		}
		
		this.cpuUsage+=cpu;
		this.netRead+=netRead;
		this.netWrite+=netWrite;
	}
	
	public synchronized void reset(){
		startTime=Sistem.millis();
		referrers.clear();
		countries.clear();
		langs.clear();
		browsers.clear();
		bringingWords.clear();
		pageVisitsTime.clear();
		pageVisitsCount.clear();
		cpuUsage=0;
		netRead=0;
		netWrite=0;
	}
}
