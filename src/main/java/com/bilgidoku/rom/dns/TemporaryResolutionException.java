package com.bilgidoku.rom.dns;

import java.io.IOException;

/**
 * Exception to throw when a temporary DNS resolution problem occurs.
 */
@SuppressWarnings("serial")
public class TemporaryResolutionException extends IOException {

	public TemporaryResolutionException() {
		super();
	}

	public TemporaryResolutionException(String message) {
		super(message);
	}
}
