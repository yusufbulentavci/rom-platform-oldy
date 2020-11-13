package com.bilgidoku.rom.pg.dict.mapper;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;

public class RemoteAddrMapper extends ArgMapper {

	public RemoteAddrMapper(short i, boolean canBeNull) {
		super(i, canBeNull);
	}


	public String toString() {
		return "RemoteAddrMapper:";
	}

	@Override
	public void setValue(CallInteraction request, String self, DbThree ps) throws KnownError{
		String x = request.getSession().getIpAddress();
		ps.setString(x);
	}


	@Override
	public Object getValue(CallInteraction request, String self)
			{
		return request.getSession().getIpAddress();
	}

}
