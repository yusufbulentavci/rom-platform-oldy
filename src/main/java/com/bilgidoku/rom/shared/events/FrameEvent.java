package com.bilgidoku.rom.shared.events;

import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;

public interface FrameEvent extends WebEvent{
	public String sessionId();
	public boolean closed();
	public String cmd();
	public String param1();
	public String param2();
	public String param3();
	public JSONObject paramObj1();
	public JSONObject paramObj2();
	public JSONArray paramArr();
}
