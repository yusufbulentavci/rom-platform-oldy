package com.bilgidoku.rom.gwt.client.util.chat;

public interface RtMsgContactsProcessor {

	void xmmsPresence(String from, int code, String presence);

	void dlgJoin(String from, boolean mine);

	void dlgSay(String from, boolean mine, String text);

	void dlgPhoto(String from, boolean mine, String text);

	void dlgVideo(String from, boolean mine, String src, String docmd);

	void dlgTalking(String from, boolean mine);

	void dlgTvImg(String from, boolean mine, String text);

	void dlgTvVideo(String from, boolean mine, String text);

	void dlgTvMark(String from, boolean mine, int parseInt, int parseInt2);

	void dlgTvVideoCtrl(String from, boolean mine, Integer secs, String ctrl);

	void dlgTvText(String from, boolean mine, String text);

	void dlgTvHeader(String from, boolean mine, String text);

	void dlgTvShow(String from, boolean mine, char t);

	void dlgPresence(String from, boolean mine, String text);

	
}
