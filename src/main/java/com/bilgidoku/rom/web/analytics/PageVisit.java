package com.bilgidoku.rom.web.analytics;

public class PageVisit {
	int time=0;
	int count=0;
	
	public void appendSecs(int inSecs) {
		time+=inSecs;
	}
	public void appendCount(){
		count++;
	}
}
