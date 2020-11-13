package com.bilgidoku.rom.shared.code;

public class CodeStat {
	private Code code;
	public boolean canElse = false;
	public boolean box = false;
	public Integer overrideTop;
	public Integer overrideLeft;
	

	public void reset() {
		box = false;
		code = null;
		overrideTop=null;
		overrideLeft=null;
	}


	public void setCode(Code code2) {
		if (code2 == null) {
			code2 = new Code();
		}
		this.code=code2;
	}


	public Code getCode() {
		return code;
	}


	public void resetUp() {
		canElse = false;
	}
}