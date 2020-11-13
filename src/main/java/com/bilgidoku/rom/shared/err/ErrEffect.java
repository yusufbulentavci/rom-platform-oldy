package com.bilgidoku.rom.shared.err;

public class ErrEffect {
	public static final String DBGONE="db_gone";
	public static final String CONFIG="config";
	public static final String DNS="dns";
	public static final String MAILREPO="mailrepo";
	
	
	final String errCode;
	Boolean waitClean=false;
	Integer waitPeriod=null;
	String causedService=null;
	
	private ErrEffect(String errCode){
		this.errCode=errCode;
	}
	
	public static ErrEffect dbgone(){
		ErrEffect ee=new ErrEffect(DBGONE);
		return ee;
	}
	
	public static ErrEffect config(){
		ErrEffect ee=new ErrEffect(CONFIG);
		ee.waitClean=true;
		return ee;
	}
	
	public static ErrEffect dns(){
		ErrEffect ee=new ErrEffect(DNS);
		ee.waitClean=true;
		return ee;
	}

	public static ErrEffect mailRepo() {
		ErrEffect ee=new ErrEffect(MAILREPO);
		ee.waitClean=true;
		return ee;
	}
	
	
	

}
