package com.bilgidoku.rom.site.kamu.activate.client;

public class NotReadyException extends Exception {

	String field;

	public NotReadyException(String string) {
		this.field=string;
	}

}
