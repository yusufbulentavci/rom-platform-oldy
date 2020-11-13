package com.bilgidoku.rom.haber;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Genel;

public final class TalkUtil {

	public static final int SERVER_PORT = 9005;

	public static JSONObject data(JSONObject jo) throws JSONException {
		return jo.getJSONObject("d");
	}

	public static String strData(JSONObject jo) throws JSONException {
		return jo.getString("d");
	}
	
	public static JSONArray arrData(JSONObject jo) throws JSONException {
		return jo.getJSONArray("d");
	}

	public static String cmd(JSONObject jo) throws JSONException {
		return jo.getString("c");
	}
	
	
	public static String from(JSONObject jo) throws JSONException {
		return jo.getString("f");
	}
	
	public static String to(JSONObject jo) throws JSONException {
		return jo.optString("to");
	}

	public static void rid(JSONObject jo, String rid) throws JSONException {
		jo.put("rid", rid);
	}

	public static String rid(JSONObject jo) throws JSONException {
		return jo.getString("p");
	}

	public static byte retryPattern(JSONObject jo) throws JSONException {
		return (byte) jo.getInt("rp");
	}
	
	public static byte alarmLevel(JSONObject jo) throws JSONException {
		return (byte) jo.getInt("a");
	}
	public static long time(JSONObject jo) throws JSONException {
		return jo.getInt("t");
	}
	public static int hostId(JSONObject jo) throws JSONException{
		return jo.getInt("h");
	}

	public static boolean ani(JSONObject jo){
		return jo.optBoolean("ani", false);
	}
	
	public static boolean line(JSONObject jo) throws JSONException{
		return jo.optBoolean("line", false);
	}
	
	public static JSONObject m(int hostId, String to, String cmd, JSONObject data, String from) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("f", from);
		jo.put("c", cmd);
		jo.put("to", to);
		jo.put("h", hostId);
		jo.put("d", data);
		jo.put("t", System.currentTimeMillis());
		return jo;
	}
	
	public static JSONObject m(int hostId, String to, String cmd, JSONObject data) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("f", Genel.getHostName());
		jo.put("c", cmd);
		jo.put("to", to);
		jo.put("h", hostId);
		jo.put("d", data);
		jo.put("t", System.currentTimeMillis());
		return jo;
	}

	public static JSONObject m(int hostId, String cmd, JSONObject data) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("f", Genel.getHostName());
		jo.put("to", Genel.getHostName());
		jo.put("c", cmd);
		jo.put("h", hostId);
		jo.put("d", data);
		jo.put("t", System.currentTimeMillis());
		return jo;
	}
	
	public static JSONObject m(String cmd, JSONObject data) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("f", Genel.getHostName());
		jo.put("to", Genel.getHostName());
		jo.put("c", cmd);
		jo.put("d", data);
		jo.put("t", System.currentTimeMillis());
		return jo;
	}

	public static void trust(JSONObject jo, long l) throws JSONException {
		jo.put("tid", l);
	}
	
	public static boolean isLocal(JSONObject jo) throws JSONException{
		String world = TalkUtil.to(jo);
		if(world==null)
			return true;
		if(Genel.getHostName().equals(world)){
			return true;
		}
		return false;
	}

	// public JSONObject repoJson() throws JSONException {
	// JSONObject jr = new JSONObject();
	// jr.put("r", retryPattern);
	// jr.put("o", jo);
	// jr.put("t", time);
	// jr.put("l", alarmLevel);
	//
	// return jr;
	// }

}
