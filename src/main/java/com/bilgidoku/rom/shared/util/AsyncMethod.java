package com.bilgidoku.rom.shared.util;

public interface AsyncMethod<RunParamType,ErrorParamType> {
	
	public void run(RunParamType param);
	public void error(ErrorParamType param);
	

}
