package com.bilgidoku.rom.min;

public class DefaultLogger implements ILogger{

	@Override
	public void outln(Object s) {
		System.out.println(s==null?null:s.toString());
	}

	@Override
	public void errln(Object s) {
		System.err.println(s==null?null:s.toString());
	}

	@Override
	public void printStackTrace(Throwable x, Object extra) {
		String m = x.getMessage();
		if(m!=null)
			errln(m);
		if(extra!=null)
			System.err.println(extra);
		x.printStackTrace();
	}

	@Override
	public void printStackTrace(Throwable x) {
		String m = x.getMessage();
		if(m!=null)
			errln(m);
		x.printStackTrace();
	}

}
