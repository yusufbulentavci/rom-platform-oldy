package com.bilgidoku.rom.pg.dict.mapper;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public class HeaderMapper extends ArgMapper {
	private String headerName;
	
	public HeaderMapper(short i, String headerName, TypeHolder sqlType, boolean canBeNull) {
		super(i,canBeNull);
		this.headerName=headerName;
		this.sqlType=sqlType;
	}

	@Override
	public void setValue(CallInteraction request, String self, DbThree ps) throws KnownError{
		throw new KnownError().notImplemented();
	}

	@Override
	public Object getValue(CallInteraction request, String self)
			{
		return null;
	}



}
