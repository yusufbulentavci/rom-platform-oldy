package com.bilgidoku.rom.pg.veritabani;

import com.bilgidoku.rom.shared.err.KnownError;

public class DbOp {
	
	protected KnownError err(Exception e) {
		return new KnownError(e);
	}
	

	protected void hasNext(DbThree db3) throws KnownError {
		if(!db3.next()){
			throw new KnownError("Unexpected empty db result");
		}
	}
	

}
