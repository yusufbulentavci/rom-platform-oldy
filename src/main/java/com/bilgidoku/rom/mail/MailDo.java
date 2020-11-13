package com.bilgidoku.rom.mail;

import com.bilgidoku.rom.shared.err.KnownError;

public interface MailDo {
	void service(Email mail) throws KnownError;
	String getMailetName();
}
