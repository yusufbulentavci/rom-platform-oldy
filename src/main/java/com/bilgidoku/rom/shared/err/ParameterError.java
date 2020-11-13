package com.bilgidoku.rom.shared.err;

public class ParameterError extends Exception {
	private String  name;
	private String error;
	public ParameterError(String name, String error){
		super(name+" "+error);
		this.name=name;
		this.error=error;
	}
	public ParameterError(String name, String error, Throwable e){
		super(name+" "+error,e);
		this.name=name;
		this.error=error;
	}
}
