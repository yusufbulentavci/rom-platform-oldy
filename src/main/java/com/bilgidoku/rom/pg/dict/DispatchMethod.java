package com.bilgidoku.rom.pg.dict;

/*
TypeControl contains dispatchMethods by name
Used to make a call with control
*/
public class DispatchMethod {
	public final MethodControl methodControl;
	public final DaoCall dao;

	public DispatchMethod(MethodControl mc, DaoCall dao2) {
		this.methodControl = mc;
		this.dao = dao2;
	}

}
