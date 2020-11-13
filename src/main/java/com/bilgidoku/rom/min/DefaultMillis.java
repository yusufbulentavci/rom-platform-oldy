package com.bilgidoku.rom.min;

public class DefaultMillis implements MillisProvider {

	@Override
	public long millis() {
		return System.currentTimeMillis();
	}

}
