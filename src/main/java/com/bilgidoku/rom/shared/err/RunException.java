package com.bilgidoku.rom.shared.err;


public class RunException extends RuntimeException{
	private ErrEffect ee;

	public RunException(ErrEffect ee){
		this.ee=ee;
	}

	public RunException(ErrEffect ee, String string, Throwable e) {
		super(string,e);
		this.ee=ee;
	}

	public RunException(ErrEffect ee, String string) {
		super(string);
		this.ee=ee;
	}
	
}
