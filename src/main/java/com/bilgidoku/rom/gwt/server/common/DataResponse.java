package com.bilgidoku.rom.gwt.server.common;

import java.util.List;

public abstract class  DataResponse<T> {
	public void ready(T value){
	}
	public void array(List<T> value){
	}
	public void empty(){
	}
	public void err(int statusCode, String statusText){
	}
}