package com.bilgidoku.rom.web.http.session;

public interface PushConn {

	Integer getSockId();

	Long isOkForRt();

	void sendRt(String msg);
	
	void close();


}
