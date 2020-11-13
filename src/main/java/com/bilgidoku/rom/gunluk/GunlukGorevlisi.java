package com.bilgidoku.rom.gunluk;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.min.ILogger;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryMinute;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.GorevliYonetimi;
import com.bilgidoku.rom.shared.json.JSONObject;

public class GunlukGorevlisi extends GorevliDir implements ILogger, EveryMinute {
	public static final int NO=0;
	
	public static GunlukGorevlisi tek(){
		if(tek==null) {
			synchronized (GunlukGorevlisi.class) {
				if(tek==null) {
					tek=new GunlukGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static GunlukGorevlisi tek;
	private GunlukGorevlisi() {
		super("Gunluk", NO);
	}
	

	private static final int MAX_ERR_PER_MIN = 150;


	private RotatingStream log;
	private String fileName="rom";
	private AtomicInteger errorCounter=new AtomicInteger(0);
	
	private final String requestIdPrefix=System.currentTimeMillis()+"-";
	private final AtomicLong requestDyn=new AtomicLong();



	@Override
	protected void kur() throws KnownError {
		KosuGorevlisi.tek().waitMin(this);
		log = new RotatingStream(safeCheapDir(), fileName
				+ ".log", safeDataDir(), fileName+".txt");
		Sistem.log=this;
	}

	@Override
	protected void bitir(boolean dostca) {
		KosuGorevlisi.tek().waitMinRemove(this);
	}

	@Override
	public void selfDescribe(JSONObject jo) {
		jo.safePut("errcount", errorCounter.get());
//		jo.safePut("file", log.describe());
	}

	
	

	@Override
	public void outln(Object s) {
		log.log(LogCmds.out, true, LogPriority.NOTICE, "str", s);

	}

	@Override
	public void errln(Object s) {
		log.log(LogCmds.out, false, LogPriority.HIGH, "str", s);		
		incrementAndCheckErrCount();
	}

	private void incrementAndCheckErrCount() {
		int count = errorCounter.incrementAndGet();
		if(count>MAX_ERR_PER_MIN){
			GorevliYonetimi.tek().terminate("Exceeded err per minute, log service triggers shutdown", null); 
		}
	}

	@Override
	public void printStackTrace(Throwable x, Object extra) {
		log.log(x, extra==null?"":extra.toString());
	}

	@Override
	public void printStackTrace(Throwable x) {
		log.log(x,"");
	}

	public void log(String cmd, boolean isGood, int priority, JSONObject s) {
		log.log(cmd,isGood,priority, s);
	}

	public void log(String cmd, boolean isGood, int priority, Object... args) {
		log.log(cmd,isGood,priority,args);
	}

	public List<JSONObject> getTokensOfCurrentFile() {
		return null;
//		return log.getTokensOfCurrentFile();
	}

	public void unfinished(String id, String reason, Throwable t, Object... args) {
		log.log(LogCmds.unfinished, false, 15, args);
		if(t!=null){
			printStackTrace(t);
		}
	}

	@Override
	public void everyMinute(int year, int month, int day, int hour, int minute) {
		errorCounter.set(0);
		if(log!=null)
			log.everyMinute(year, month, day, hour, minute);
	}
	
	private final String requestId(String identifier){
		String ret=requestIdPrefix+requestDyn.incrementAndGet();
		return ret;
	}

	public String request(String ip, Integer hostId, String userAddr, String command, String identifier) {
		String rid = requestId(identifier);
		this.log.request(rid, ip, hostId, userAddr, command, identifier);
		return rid;
	}

	public void response(String id, boolean suc, String code) {
		this.log.response(id, suc, code);
	}

	public void requestToQueue(String rid, String ref) {
		this.log.requestToQueue(rid,ref);
	}

	public void ban(String ip, String reason) {
		log.log(LogCmds.ban,false,LogPriority.LOW, LogParams.ip, ip, LogParams.str, reason);
	}
	
	public void addToSet(String name, String value) {
//		log.log(LogCmds.addtoset,false,LogPriority.LOW, LogParams.cls, name, LogParams.str, value);
	}
	
}
