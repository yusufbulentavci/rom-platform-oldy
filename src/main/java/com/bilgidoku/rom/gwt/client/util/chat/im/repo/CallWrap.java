package com.bilgidoku.rom.gwt.client.util.chat.im.repo;

import com.bilgidoku.rom.gwt.client.util.chat.IDlgChat;
import com.bilgidoku.rom.gwt.client.util.chat.RtMsgProcessor;

public interface CallWrap {

	void setPresence(int code, String online);

	RtMsgProcessor getMsgProcessor();

	IDlgChat dlg();

}
