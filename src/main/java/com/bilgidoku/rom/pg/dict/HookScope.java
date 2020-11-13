package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.ilk.json.JSONObject;


public class HookScope {

	final public CallInteraction request; 
	//
	public HookScope(CallInteraction request) {
		this.request = request;
	}
//	
//	public String outUriParam(String name, boolean canBeNull) throws KnownError{
//		String s=stringParam(name, canBeNull);
//		return s;
//	}
//
//	public String uriParam(String name, boolean canBeNull) throws KnownError{
//		String s=stringParam(name, canBeNull);
//		
//		return s;
//	}
//		
	public String getParam(String name, Integer minSize, Integer maxSize, boolean notNull) throws KnownError, KnownError, ParameterError {
		return request.getParam(name, minSize, maxSize, notNull);
	}
	
	public Boolean getBoolParam(String name, boolean notNull) throws KnownError, KnownError, ParameterError {
		return request.getBoolean(name, notNull);
	}
	public Integer getIntParam(String name, boolean notNull) throws KnownError, KnownError, ParameterError {
		return request.getIntParam(name, notNull);
	}
	public Long getLongParam(String name, boolean notNull) throws KnownError, KnownError, ParameterError {
		return request.getLongParam(name, notNull);
	}
	public JSONObject getJsonParam(String name, Integer minSize, Integer maxSize, boolean notNull) throws KnownError, KnownError, ParameterError {
		return request.getJsonParam(name, minSize, maxSize, notNull);
	}
	
	public Long[] getLongParams(String name, boolean notNull) throws ParameterError{
		return request.getLongParams(name, notNull);
	}
	
//	
	public Integer getHostId(){
		return request.getHostId();
	}
	
	public Integer getIntraHostId(){
		int hostId = request.getHostId();
		if ( hostId % 2 == 0 ){
			return hostId-1;
		}
		return hostId;
	}
//	
//	public void done(){
//
//	}
	public void paramOverride(String param, String value) {
		request.paramOverride(param, value);
	}
	public CallInteraction getRequest() throws NotInlineMethodException {
		return request;
	}
	public String getUri(){
		return request.getUri();
	}
	public int[] getIntParams(String name, boolean notNull) throws ParameterError {
		return request.getIntParams(name, notNull);
	}


}
