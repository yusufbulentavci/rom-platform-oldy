package com.bilgidoku.rom.pg.dict.mapper;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;

public class ContactNoMapper extends ArgMapper{
	public ContactNoMapper(short i, boolean canBeNull) {
		super(i,canBeNull);
	}

	
	public String toString(){
		return "ContactNoMapper:";
	}

	@Override
	public void setValue(CallInteraction request, String self, DbThree ps) throws KnownError{
		ps.setString(request.getSession().getCid().substring(6));
	}


	@Override
	public Object getValue(CallInteraction request, String self) {
		return request.getSession().getCid().substring(6);
	}

}
