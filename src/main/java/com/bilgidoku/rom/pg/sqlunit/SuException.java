package com.bilgidoku.rom.pg.sqlunit;

public class SuException extends Exception {

	private String fileName;
	private int line;

	public SuException(String fileName, int i, String string) {
		super(string);
		this.fileName=fileName;
		this.line=i;
	}

}
