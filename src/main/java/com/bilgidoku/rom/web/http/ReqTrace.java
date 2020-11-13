package com.bilgidoku.rom.web.http;

public interface ReqTrace {

	void reqHasNoSession(Integer sockId, HttpRequestHandler httpRequestHandler);

	void hasSession(Integer sockId);

	void reqInactive(int socketid);
	
}
