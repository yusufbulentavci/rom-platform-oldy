package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class Animation implements JsonTransfer {
	private static final String FRAMES = "frames";
	private static final String DIR = "dir";
	private static final String TIMING = "t";
	private static final String ITERATION = "i";
	private static final String DELAY = "d";
	private static final String DURATION = "dur";
	private static final String INSPIRE = "ins";
	public int duration;
	public Integer delay;
	public Integer iterationCount;
	public String timingFunction;
	public String direction;
	public String fillMode;
	public String inspire;
	public Map<String, Map<String,String> > keyFrames;
	
	public Animation() {
		keyFrames = new HashMap<String, Map<String,String> >();
	}
	
	public Animation(JSONObject jo) throws RunException {
		duration=jo.getInt(DURATION);
		delay=jo.optInt(DELAY);
		iterationCount=jo.optInt(ITERATION);
		timingFunction=jo.optString(TIMING);
		direction=jo.optString(DIR);
		inspire=jo.optString(INSPIRE);
		delay=jo.optInt(DELAY);
		keyFrames=JsonUtil.restoreHashMap(jo.getObject(FRAMES));
	}


	@Override
	public JSONValue store() throws RunException {
		
		JSONObject jo=new JSONObject();
		jo.put(DURATION, duration);
		if(delay!=null)
			jo.put(DELAY, delay);
		if(iterationCount!=null)
			jo.put(ITERATION, iterationCount);
		if(timingFunction!=null)
			jo.put(TIMING, timingFunction);
		if(direction!=null)
			jo.put(DIR, direction);
		
		if(inspire!=null)
			jo.put(INSPIRE, inspire);
		
		JSONObject frames=new JSONObject();
		for(Entry<String, Map<String, String>> f : keyFrames.entrySet()){
			frames.put(f.getKey(), JsonUtil.mapToObj(f.getValue()));
		}
		jo.put(FRAMES, frames);
		
		return jo;
	}

	public Animation cloneWrap() throws RunException {
		JSONValue a = store();
		return new Animation(a.isObject());
	}
}
