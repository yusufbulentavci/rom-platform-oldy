package com.bilgidoku.rom.site.kamu.tutor.client;

public class Level {
	final String name;
	final short lowerBound;
	final int excludedSize;
	
	public Level(String name, short lowerBound, int excludedSize) {
		super();
		this.name = name;
		this.lowerBound = lowerBound;
		this.excludedSize = excludedSize;
	}

	public int getUp(int optionCount) {
		return optionCount-(excludedSize-lowerBound);
	}
}
