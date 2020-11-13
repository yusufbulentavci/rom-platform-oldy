package com.bilgidoku.rom.gunluk;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;

public class IStackTrace {
	final int id;

	final Throwable exception; 
	final IStackTrace cause;
	boolean printed;
	
	public IStackTrace(int id, Throwable exception, IStackTrace cause) {
		super();
		this.id = id;
		this.exception = exception;
		this.cause = cause;
	}
	
	public void print(StringBuilder sb) {
		sb.append("ST:"+id+"\n");
		if(!printed){
			printed=true;
			getStackTrace(sb, exception);
		}
		if(cause!=null){
			sb.append("Caused by\n");
			cause.print(sb);
		}
	}
	
	public JSONObject toJson(){
		JSONObject jo=new JSONObject();
		try {
			jo.put(LogParams.id, id);
			
			jo.put(LogParams.cls, exception.getClass().getSimpleName());
			if(exception.getMessage()!=null){
				jo.put(LogParams.str, exception.getMessage());
			}
			JSONArray els=new JSONArray();
			StackTraceElement[] st = exception.getStackTrace();
			for (StackTraceElement stackTraceElement : st) {
				els.put(stackTraceElement.toString());
			}
			jo.put(LogParams.trace, els);
			if(cause!= null)
				jo.put(LogParams.cause, cause.id);
			
			if(exception instanceof KnownError) {
				jo.put(LogParams.str, ((KnownError)exception).getSummary());
				return jo;
			}
			
			return jo;
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}
	
	private static void getStackTrace(StringBuilder sb, Throwable e){
		StackTraceElement[] st = e.getStackTrace();
		for (StackTraceElement stackTraceElement : st) {
			sb.append(stackTraceElement.toString());
			sb.append("\n    ");
		}
		
	}

	
}
