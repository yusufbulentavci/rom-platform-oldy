package com.bilgidoku.rom.izle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.gunluk.LogCmds;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryMinute;

// private ThreadLocal<Will> will=new ThreadLocal<Will>();

// private static ThreadLocal<Active> thrActive = new ThreadLocal<Active>();
// private static ThreadLocal<String> thrConn = new ThreadLocal<String>();

// private final MonitorFilter filter=new MonitorFilter();

public class IzlemeGorevlisi extends GorevliDir implements EveryMinute {
	public static final int NO=1;

	public static IzlemeGorevlisi tek(){
		if(tek==null) {
			synchronized (IzlemeGorevlisi.class) {
				if(tek==null) {
					tek=new IzlemeGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static IzlemeGorevlisi tek;
	private IzlemeGorevlisi() {
		super("Izleme", NO);
	}

	private final List<MC> modules = new LinkedList<MC>();
	
	private final Map<String, Set<String>> monitorSets=new HashMap<>();
	
	@Override
	protected void kur() throws KnownError {
		KosuGorevlisi.tek().waitMin(this);
	}

	@Override
	protected void bitir(boolean dostca) {
		KosuGorevlisi.tek().waitMinRemove(this);
	}
	


	public void mc(MC mc) {
		modules.add(mc);
	}

	@Override
	public void everyMinute(int year, int month, int day, int hour, int minute) {

//		try {
//			JSONObject jo = logToJson();
//			if (jo.length() > 0)
//				GunlukGorevlisi.tek().log(LogCmds.stat, true, 0, "cntrs", jo);
//		} catch (JSONException e) {
//			Sistem.printStackTrace(e, "Monitor service everyMinute failed");
//		}
	}

	private JSONObject logToJson() throws KnownError  {
		JSONObject jo = new JSONObject();
		for (MC it : modules) {
			JSONObject r = it.report();
			if (r != null)
				jo.put(it.getCode(), r);
		}
		return jo;
	}

	@Override
	public void selfDescribe(JSONObject jo) {
		jo.safePut("modulecount", modules.size());
//		jo.safePut("Eye", Eye.selfDescribe());
	}

	public void eyeOnReset() {
		boolean match = false;
		for (MC mc : modules) {
			String code = mc.getCode();
			for (String pack : Eye.eyeOnPackage) {
				if (code.startsWith(code)) {
					match = true;
					break;
				}
			}

			mc.eyeOn(match);
		}

	}

	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean assertEqual(String sname, int longtime) {
		// TODO Auto-generated method stub
		return false;
	}

	public void logStates() {
//		try {
//			JSONObject jo = logToJson();
//			System.out.println(jo.toString());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

	}

	
	
	public void addToSet(String name, String value) {
		Set<String> set = monitorSets.get(name);
		if(set==null){
			set=new HashSet<String>();
			monitorSets.put(name, set);
		}
		if(set.contains(value))
			return;
		if(set.size()>200){
			set.clear();
		}
		set.add(value);
		GunlukGorevlisi.tek().addToSet(name, value);
	}

}
