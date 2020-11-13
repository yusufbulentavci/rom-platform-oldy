package com.bilgidoku.rom.type;

import com.bilgidoku.rom.pg.dict.DaoCall;
import com.bilgidoku.rom.pg.dict.MethodControl;
import com.bilgidoku.rom.pg.dict.Net;

public class CustomNode {
	final String prefix;
	final MethodControl control;
	public final Net net;
	public final DaoCall dao;
	public CustomNode(String prefix2, DaoCall dao2, Net net2, MethodControl c) {
		this.prefix=prefix2;
		this.dao=dao2;
		this.net=net2;
		this.control=c;
	}

}
