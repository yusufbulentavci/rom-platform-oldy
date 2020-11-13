package com.bilgidoku.rom.min;

public interface ILogger {
	public void outln(Object s);
	public void errln(Object s);

	public void printStackTrace(Throwable x, Object extra);
	
	public void printStackTrace(Throwable x);
}
