package com.bilgidoku.rom.mail;

import java.util.Collection;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;

import com.bilgidoku.rom.shared.err.KnownError;

public interface MailProcessContext {
	public void sendMail(Email mail) throws KnownError;

	public MailAddress getPostmaster();

//	public Set<MailAddress> getMailServers(String domain);

	public boolean isLocalEmail(MailAddress recipient) throws KnownError;

	public void storeMail(MailAddress recipient, Email mail) throws KnownError;

	public void remoteDeliver(Email mail) throws KnownError;

	// Repository
	public void log(String to, Email mail);

	public void deliveryFailed(Email mail, Collection<MailAddress> errors);
}
