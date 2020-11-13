package com.bilgidoku.rom.web.http;

import com.bilgidoku.rom.shared.err.KnownError;

public interface RomHttpHandler {
	public void romHandle(RomHttpResponse requestHandler) throws KnownError;
}
