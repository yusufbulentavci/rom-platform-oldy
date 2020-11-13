package com.bilgidoku.rom.mail;

import java.util.Collection;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;

import com.bilgidoku.rom.shared.err.KnownError;

public interface Matcher {

	Collection<MailAddress> match(Email mail) throws KnownError;

}
