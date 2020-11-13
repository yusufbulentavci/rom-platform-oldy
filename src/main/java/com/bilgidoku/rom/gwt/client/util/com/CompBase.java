package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;
/**
 * 
 * 
 * 
 * 
 * @author rompg
 *
 */
public abstract class CompBase implements Comp {
	protected int id;
	protected boolean available;
	
	
	public boolean areYouThere() {
		return true;
	}

	protected JSONObject response(JSONObject cjo) {
		cjo.put("resp", true);
		return cjo;
	}

	protected JSONObject respstr(JSONObject cjo, String resp) {
		cjo.put("resp", true);
		cjo.put("respstr", resp);
		return cjo;
	}
	
	protected JSONObject respint(JSONObject cjo, int resp) {
		cjo.put("resp", true);
		cjo.put("respint", resp);
		return cjo;
	}
	
	protected void post(String cmd){
		JSONObject jo=new JSONObject();
		jo.put("cmd", cmd);
		post(jo);
	}
	
	protected void post(String cmd, String param, int val){
		JSONObject jo=new JSONObject();
		jo.put("cmd", cmd);
		jo.put(param, val);
		post(jo);
	}
	
	protected void post(String cmd, String param, boolean val) {
		JSONObject jo=new JSONObject();
		jo.put("cmd", cmd);
		jo.put(param, val);
		post(jo);
	}
	
	protected void post(JSONObject cjo){
		RomEntryPoint.com().post(cjo, null, null);
	}
	
	protected void needResp(JSONObject cjo, FrameResponse listen){
		RomEntryPoint.com().post(cjo, null, listen);
	}
	

	@Override
	public boolean handle(String cmd, JSONObject cjo) throws RunException {
		return false;
	}
	
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	

	@Override
	public Object getInterface(String name) {
		return this;
	}

	
	@Override
	public void initial() {
		
	}
	
	@Override
	public void last() {
		
	}

	@Override
	public void freeze() {
		this.available=false;
	}

	@Override
	public void resolve() {
		this.available=true;
	}


	@Override
	public void dataChanged(String key, String val) {
		
	}

	@Override
	public void processNewState(){
		
	}
	
	@Override
	public void compAdded(Comp h) {
		
	}



	@Override
	public void compRemoved(Comp h) {
		
	}

}
