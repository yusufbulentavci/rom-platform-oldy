package com.bilgidoku.rom.gwt.server.middle;

import com.bilgidoku.rom.pg.dict.CallInteraction;

public abstract class DbDaoBase extends DaoBase{
	

	public String getDbfsOwner(CallInteraction request){
		return request.getUri();
	}
	
	
}
