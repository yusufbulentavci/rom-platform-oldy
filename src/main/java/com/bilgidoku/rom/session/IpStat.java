package com.bilgidoku.rom.session;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.Eye;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;

public class IpStat {
	private static MC mc = new MC(IpStat.class);
	public static final String[] RANGES = { "min", "hour", "day" };
	public static final int RANGE_MIN = 0;
	public static final int RANGE_HOUR = 1;
	public static final int RANGE_DAY = 2;

	public static final String[] MODULES = { "http", "smtp", "pop3", "protocol" };
	public static final int MODULE_HTTP = 0;
	public static final int MODULE_SMTP = 1;
	public static final int MODULE_POP3 = 2;
	public static final int MODULE_PROTOCOL = 3;

	public static int getModuleIdByName(String serviceName) {
		for (int i = 0; i < MODULES.length; i++) {
			if (MODULES[i].equals(serviceName))
				return i;
		}
		throw new RuntimeException("Unkown service name:" + serviceName);
	}

	public static final String[] COUNTERS = { "hit", "autherr", "connect", "abuse" };
	public static final int COUNTER_HIT = 0;
	public static final int COUNTER_AUTHERR = 1;
	public static final int COUNTER_CONNECT = 2;
	public static final int COUNTER_ABUSE = 3;

	// static final int MAX_AUTH_FAIL_DAILY = 10;
	// static final int MAX_HIT_IN_MIN = 500;
	// static final int MAX_CONNECT_IN_MIN = 200;
	// static final int MAX_ABUSE_IN_MIN = 3;

	static final int BAN1_PERIOD_IN_MILLIS = 60 * 60 * 1000; // An hour
	static final int BAN2_PERIOD_IN_MILLIS = 24 * 60 * 60 * 1000; // A day

	static final int REMOVE_INACTIVITY_MILLIS = 60 * 60 * 1000;

	static final int MAX_CONS = 40;

	static final int[] MINUTE_ARR = { 2000, 3, 100, 3 };
	static final int[] HOUR_ARR = { 2000 * 20, 10, 100 * 30, 10 };
	static final int[] DAY_ARR = { 2000 * 20 * 10, 10, 100 * 30 * 10, 10 };

	private static final int[][][] LIMITS = {
			// For minute
			{ MINUTE_ARR, // http
					MINUTE_ARR, // smtp
					MINUTE_ARR, // pop3
					MINUTE_ARR // protocol
			}, // For hour
			{ HOUR_ARR, // http
					HOUR_ARR, // smtp
					HOUR_ARR, // pop3
					HOUR_ARR // protocol
			}, // For day
			{ DAY_ARR, // http
					DAY_ARR, // smtp
					DAY_ARR, // pop3
					DAY_ARR // protocol
			} };

	int[][][] counters = new int[RANGES.length][MODULES.length][COUNTERS.length];

	long lastActivity = 0;
	long bannedTo = 0;

	final boolean isInternet;

	boolean wasBanned = false;
	String host;
	String user;

	int banRange;
	int banModule;
	int banCounter;
	int[][][] banCounterCopy;

	List<ConnectionSession> connections = new ArrayList<ConnectionSession>();

	private void reset() {
		resetCounter(RANGE_MIN);
		resetCounter(RANGE_HOUR);
		resetCounter(RANGE_DAY);
		bannedTo = 0;
		wasBanned = false;
		banRange = 0;
		banModule = 0;
		banCounter = 0;
		banCounterCopy = null;
	}

	public synchronized JSONObject report() {
		JSONObject ret = new JSONObject();
		ret.safePut("lastActivity", lastActivity);
		ret.safePut("counters", counterToJson(counters));
		JSONArray props = new JSONArray();
		boolean hasProp = false;
		if (!isInternet) {
			props.put("intranet");
			hasProp = true;
		}
		if (wasBanned) {
			props.put("wasbanned");
			hasProp = true;
		}
		if (hasProp)
			ret.safePut("props", props);

		if (host != null)
			ret.safePut("host", host);
		if (user != null)
			ret.safePut("user", user);

		if (bannedTo != 0) {
			ret.safePut("bannedto", bannedTo);
		}

		if (banCounterCopy != null) {
			ret.safePut("banrange", banRange);
			ret.safePut("banmodule", banModule);
			ret.safePut("bancounter", banCounter);
			ret.safePut("bancounters", counterToJson(banCounterCopy));
		}

		if (this.connections.size() > 0) {
			JSONArray cons = new JSONArray();
			for (ConnectionSession cs : connections) {
				cons.put(cs.report());
			}
			ret.safePut("cons", cons);
		}

		return ret;

	}

	private JSONArray counterToJson(int[][][] ca) {
		JSONArray ret = new JSONArray();
		for (int r = 0; r < RANGES.length; r++) {
			JSONArray range = new JSONArray();
			for (int m = 0; m < MODULES.length; m++) {
				JSONArray module = new JSONArray();
				for (int c = 0; c < COUNTERS.length; c++) {
					module.put(ca[r][m][c]);
				}
				range.put(module);
			}
			ret.put(range);
		}
		return ret;
	}

	public IpStat(boolean isIntranet) {
		this.isInternet = !isIntranet;
	}

	private static final Astate bancount = mc.c("ban");

	private void ban(int range, int module, int counter) {
		bancount.more();
		if (Eye.on) {
			mc.trace("Ip banned: " + report().toString());
		}

		this.banCounter = counter;
		this.banRange = range;
		this.banModule = module;
		bannedTo = lastActivity + (wasBanned ? BAN2_PERIOD_IN_MILLIS : BAN1_PERIOD_IN_MILLIS);
		wasBanned = true;
	}

	private synchronized void counterCheck(int range, int module, int counter, String host, String user) {
		if (Eye.on) {
			mc.trace("IpStat event:" + RANGES[range] + " " + MODULES[module] + " " + COUNTERS[counter] + " " + host
					+ " " + user);
		}

		int cur = ++this.counters[range][module][counter];
		if (host != null)
			this.host = host;
		if (user != null)
			this.user = user;

		lastActivity = System.currentTimeMillis();
		if (this.isInternet && cur >= LIMITS[range][module][counter]) {
			ban(range, module, counter);
		}
	}

	// private synchronized void counterCheckNoBan(int range, int module, int
	// counter, String host, String user) {
	// if (Eye.on) {
	// mc.trace("IpStat event/no ban:"+RANGES[range]+" "+MODULES[module]+"
	// "+COUNTERS[counter]+" "+host+" "+user);
	// }
	//
	// ++this.counters[range][module][counter];
	// if (host != null)
	// this.host = host;
	// if (user != null)
	// this.user = user;
	//
	// lastActivity = System.currentTimeMillis();
	//
	// return false;
	// }

	private static final Astate afcount = mc.c("authfailed");

	public boolean authFailed(int from, String toHost, String forUser) {
		afcount.more();

		counterCheck(RANGE_MIN, from, COUNTER_AUTHERR, toHost, forUser);
		return isBanned();
	}

	private static final Astate hcount = mc.c("hitcount");

	public boolean hit(int from, String toHost) {
		hcount.more();
		counterCheck(RANGE_MIN, from, COUNTER_HIT, toHost, null);
		return isBanned();
	}

	private static final Astate ccount = mc.c("connectcount");

	public boolean connect(int from, ConnectionSession cs) {
		ccount.more();

		connections.add(cs);
		if (connections.size() > MAX_CONS) {
			Sistem.outln("Over connection limit");
			return true;
		}
		counterCheck(RANGE_MIN, from, COUNTER_CONNECT, null, null);
		return isBanned();
	}

	private boolean isBanned() {
		return bannedTo != 0;
	}

	private static final Astate dcount = mc.c("disconnectcount");

	public void disconnect(int from, String remoteIp, ConnectionSession cs) {
		dcount.more();
		connections.remove(cs);
	}

	public boolean abuse(int from) {
		counterCheck(RANGE_MIN, from, COUNTER_ABUSE, null, null);
		return isBanned();
	}

	/**
	 * 
	 * Resets hits, connects: Zero them every minute if no banning
	 * 
	 * Check ban ending: Zero all counters after ban
	 * 
	 * Inactivity: If no activity, let ip release
	 * 
	 * 
	 * @param time
	 *            : currenttimemillis
	 * @return true to remove from cache
	 */
	public synchronized void checkMin(long time) {
		if (bannedTo != 0) {
			checkBan(time);
			return;
		}
		transferRange(RANGE_MIN, RANGE_HOUR);
	}

	public synchronized boolean checkHour(long time) {
		if (bannedTo != 0) {
			return false;
		}
		transferRange(RANGE_HOUR, RANGE_DAY);
		if (lastActivity + REMOVE_INACTIVITY_MILLIS < time) {
			return true;
		}
		return false;
	}

	public synchronized void checkDay(long time) {
		reset();
	}

	private void transferRange(int rangefrom, int rangeto) {
		for (int m = 0; m < MODULES.length; m++) {
			for (int c = 0; c < COUNTERS.length; c++) {
				counters[rangeto][m][c] += counters[rangefrom][m][c];
				counters[rangefrom][m][c] = 0;
			}
		}
	}

	private void checkBan(long time) {
		if (bannedTo > time)
			return;
		bannedTo = 0;
		resetCounter(banRange);
	}

	private void resetCounter(int range) {
		for (int m = 0; m < MODULES.length; m++) {
			for (int c = 0; c < COUNTERS.length; c++)
				counters[range][m][c] = 0;
		}
	}

}