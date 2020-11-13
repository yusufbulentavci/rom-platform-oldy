package com.bilgidoku.rom.izle;

import java.util.Set;

import com.bilgidoku.rom.shared.json.JSONObject;



class ThreadSees {
	String ip;
	String cid;
	Integer host;

	public void reset() {
		ip = null;
		cid = null;
		host = null;
	}
}

public class Eye {
	public static boolean on = false;

	static boolean goneCrazy = false;

	private static ThreadLocal<ThreadSees> seen = new ThreadLocal<ThreadSees>();

	static final Set<String> eyeOnPackage = new ConcurrentSet<>();
	private static final Set<String> eyeOnIps = new ConcurrentSet<>();
	private static final Set<String> eyeOnCids = new ConcurrentSet<>();
	private static final Set<Integer> eyeOnHost = new ConcurrentSet<>();

	public static JSONObject selfDescribe() {
		JSONObject jo = new JSONObject();
//		jo.safePut("on", on).safePut("goneCrazy", goneCrazy).safePut("eyeOnPacks", new JSONArray(eyeOnPackage))
//				.safePut("eyeOnPacks", new JSONArray(eyeOnIps)).safePut("eyeOnCids", new JSONArray(eyeOnCids))
//				.safePut("eyeOnHost", new JSONArray(eyeOnHost));
		return jo;
	}

	public static boolean check() {
		if (!on)
			return false;

		if (goneCrazy)
			return true;

		ThreadSees you = getSeen();

		if (you.host != null) {
			if (!eyeOnHost.contains(you.host)) {
				return false;
			}
		}

		if (you.ip != null) {
			if (!eyeOnIps.contains(you.ip)) {
				return false;
			}
		}

		if (you.cid != null) {
			if (!eyeOnCids.contains(you.cid)) {
				return false;
			}
		}

		return true;
	}

	public static void myIpIs(String ip) {
		getSeen().ip = ip;
	}

	public static void myCidIs(String cid) {
		getSeen().cid = cid;
	}

	public static void myHostIs(Integer host) {
		getSeen().host = host;
	}

	private static ThreadSees getSeen() {
		ThreadSees ret = seen.get();
		if (ret == null) {
			ret = new ThreadSees();
			seen.set(ret);
		}
		return ret;
	}

	public static void threadReset() {
		getSeen().reset();
	}

	public static void eyeOnCid(String cid, boolean on) {
		if (on) {
			eyeOnCids.add(cid);
		} else {
			eyeOnCids.remove(cid);
		}
	}

	public static void eyeOnIp(String ip, boolean on) {
		if (on) {
			eyeOnIps.add(ip);
		} else {
			eyeOnIps.remove(ip);
		}
	}

	public static void eyeOnPack(String pack, boolean on) {
		if (on) {
			eyeOnPackage.add(pack);
		} else {
			eyeOnPackage.remove(pack);
		}
	}

	public static void eyeOnHost(Integer host, boolean on) {
		if (on) {
			eyeOnHost.add(host);
		} else {
			eyeOnHost.remove(host);
		}
	}

	public static String whoAmI() {
		StringBuilder sb = new StringBuilder("Iam");
		ThreadSees s = getSeen();
		if (s.ip != null)
			sb.append("Ip:").append(s.ip);
		if (s.cid != null)
			sb.append("Cid:").append(s.cid);
		if (s.host != null)
			sb.append("Host:").append(s.host);
		return sb.toString();
	}

	public static void resetFilters() {
		eyeOnPackage.clear();
		eyeOnIps.clear();
		eyeOnCids.clear();
		eyeOnHost.clear();
	}

}
