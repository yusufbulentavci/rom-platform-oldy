package com.bilgidoku.rom.pg.dict.mapper;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;

public class UserMapper extends ArgMapper{
	public UserMapper(short i, boolean canBeNull) {
		super(i,canBeNull);
	}

	
	public String toString(){
		return "UserMapper:";
	}

	@Override
	public void setValue(CallInteraction request, String self, DbThree ps) throws KnownError{
		ps.setString(request.getSession().getUserName());
	}


	@Override
	public Object getValue(CallInteraction request, String self)
			{
		return request.getSession().getUserName();
	}

}
