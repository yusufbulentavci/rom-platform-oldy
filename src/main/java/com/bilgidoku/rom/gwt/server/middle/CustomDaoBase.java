package com.bilgidoku.rom.gwt.server.middle;

import com.bilgidoku.rom.pg.dict.CallInteraction;

public abstract class CustomDaoBase extends DaoBase{
	

	public String getDbfsOwner(CallInteraction request){
		throw new RuntimeException("Shouldnt be called");
	}
}
