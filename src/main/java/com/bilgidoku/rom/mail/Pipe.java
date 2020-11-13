package com.bilgidoku.rom.mail;

import java.util.Collection;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.min.Sistem;

public class Pipe {
	public final static String MATCHER_MATCHED_ATTRIBUTE = "matched";

	private final Matcher matcher;
	private final MailDo matchMailet;
	private final ProcDef matchPipe;
	private Pipe next;

	public Pipe(Matcher matcher, MailDo mailetMailet, ProcDef matchedPipe) {
		this.matcher = matcher;
		this.matchMailet = mailetMailet;
		this.matchPipe = matchedPipe;
	}

	public Pipe pipe(Matcher matcher, MailDo mailetMailet, ProcDef matchedPipe) {
		next = new Pipe(matcher, mailetMailet, matchedPipe);
		return next;
	}

	public void service(Email mail) throws KnownError {
		if (matcher == null) {
			goMatched(mail);
			return;
		}
		Collection<MailAddress> matched = matcher.match(mail);
		if (matched != null && matched.size() > 0) {
			if (mail.getRecipients().size() == matched.size()) {
				goMatched(mail);
			} else {
				mail.getRecipients().removeAll(matched);
				goUnmatched(mail);
				Email newMail = new EmailImpl(mail.getJson());
				newMail.setRecipients(matched);
//				newMail.setMatched(true);
				goMatched(newMail);
			}
		} else {
			goUnmatched(mail);
		}

	}

	private void goUnmatched(Email mail) throws KnownError {
		if (next != null) {
			next.service(mail);
		}
	}

	private void goMatched(Email mail) throws KnownError {
		if (matchMailet != null) {
			if (matchMailet == null) {
				Sistem.errln("-->" + this);
			} else
				Sistem.errln("-->" + matchMailet.getMailetName());
			matchMailet.service(mail);
		}
		if (matchPipe != null) {
			matchPipe.service(mail);
		} else if (next != null) {
			next.service(mail);
		}
	}

	public Pipe getNext() {
		return next;
	}

	public void setNext(Pipe next) {
		this.next = next;
	}

	public boolean hasNext() {
		return next != null;
	}

}
