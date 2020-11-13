package com.bilgidoku.rom.haber;

public enum TalkResult {
	success(0), failed(1), retry(2);

	private final int value;

	private TalkResult(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
