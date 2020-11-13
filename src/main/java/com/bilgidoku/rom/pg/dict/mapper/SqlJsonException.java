package com.bilgidoku.rom.pg.dict.mapper;

public class SqlJsonException extends Exception {
	public SqlJsonException(String string, int index, String cause) {
		super("Index:"+index+" Cause:"+cause+" FullString:"+string);
	}
}
