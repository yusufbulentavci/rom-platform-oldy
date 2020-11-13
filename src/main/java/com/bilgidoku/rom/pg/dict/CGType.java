package com.bilgidoku.rom.pg.dict;

import java.util.Collection;
import java.util.List;

public interface CGType {
	public String getNameFirstUpper();
	public Collection<CGMethod> getDaomethods();
	public Collection<CGMethod> getClientDaomethods();
	public boolean getNoUri();
	public boolean isPrimitive();
	
	public List<CGAtt> getAtts();
}
