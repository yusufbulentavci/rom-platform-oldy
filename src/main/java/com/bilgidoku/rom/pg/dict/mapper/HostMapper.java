package com.bilgidoku.rom.pg.dict.mapper;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;

public class HostMapper extends ArgMapper{
	public HostMapper(short i, boolean canBeNull) {
		super(i,canBeNull);
	}

	
	public String toString(){
		return "HostMapperMapper:";
	}


	@Override
	public void setValue(CallInteraction request, String self, DbThree ps) throws KnownError{
		ps.setInt(request.getHostId());
	} 


	@Override
	public Object getValue(CallInteraction request, String self)
			{
		return request.getHostId();
	}

}
