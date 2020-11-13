package com.bilgidoku.rom.ilk.util;

public interface RomEventListener <K> {
	public boolean romEvent(K k, int code, Object more);
}
