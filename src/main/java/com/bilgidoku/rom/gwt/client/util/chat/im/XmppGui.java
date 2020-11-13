package com.bilgidoku.rom.gwt.client.util.chat.im;

import com.bilgidoku.rom.gwt.client.util.chat.DlgChat;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.CbContacts;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.ContactBla;
import com.bilgidoku.rom.shared.util.AsyncMethodNoParam;

public interface XmppGui extends CbContacts{

	void askForNewCall(String from, AsyncMethodNoParam asyncMethodNoParam);
	void notice(String note);
	void showChatDlg(DlgChat dlg);
	void showChatDlg(ContactBla selectedObject);
	void contactAdd(ContactBla contact);
	void contactChanged(ContactBla contact);
	void deskReady(boolean b);
	void online(boolean b);
	void xmmsPresence(String from, int code, String presence);
	void contactRemove(String cid);
	void showContacts();
}
