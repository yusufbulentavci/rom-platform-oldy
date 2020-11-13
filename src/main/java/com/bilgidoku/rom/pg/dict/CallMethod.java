package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.pg.dict.AfterHook;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public interface CallMethod {
	public ArgMapper[] getArgMappers();
	public BeforeHook getBeforeHook();
	public AfterHook getAfterHook();
	public String getCallProto();
	public boolean isVoid();
	public boolean returnsPrimitive();
	public boolean isRetset();
	public TypeHolder getRetType();
	public boolean isRetFile();
	public boolean cantBeInline();
}
