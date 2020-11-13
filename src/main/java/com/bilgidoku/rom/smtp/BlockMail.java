package com.bilgidoku.rom.smtp;

public class BlockMail extends Exception {
	private final String reason;

	public BlockMail(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
}