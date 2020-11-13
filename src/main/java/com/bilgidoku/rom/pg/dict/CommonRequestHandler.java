package com.bilgidoku.rom.pg.dict;

import io.netty.handler.codec.http.HttpRequest;

import java.io.File;
import java.util.Date;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.dict.CommonSession;

public interface CommonRequestHandler {

	public void sendFile(File file, final String fileName, HttpRequest request, boolean attach, Long cache)
			throws KnownError, KnownError;

	void sendNotModified() throws KnownError;

	void sendResponse(String buf, HttpRequest request, String contenttype, Date ts, Long cache) throws KnownError;

	void sendResponse(String buf, HttpRequest request, Date ts, Long cache) throws KnownError;
	
	CommonSession getSession();

	public RomDomain getDomain();
	public void redirectLogin(String redirectAfter) throws KnownError;
}
