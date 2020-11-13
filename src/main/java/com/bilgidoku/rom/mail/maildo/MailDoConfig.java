package com.bilgidoku.rom.mail.maildo;

public class MailDoConfig {

	public String attachError() {
		return null;
	}

	public int attachment(int type) {
		return type;
	}

	public boolean fakeDomainCheck() {
		return false;
	}

	public int inlineType(int type) {
		return type;
	}

	public boolean isDebug(boolean b) {
		return b;
	}

	public boolean isReply() {
		return false;
	}

	public boolean isStatic(boolean b) {
		return b;
	}

	public String message(String string) {
		return string;
	}

	public String notice(String message) {
		return message;
	}

	public boolean passThrough() {
		return false;
	}

	public boolean passThrough(boolean b) {
		return b;
	}

	public String prefix() {
		return null;
	}

	public String prefix(String string) {
		return string;
	}

	public String recipients() {
		return null;
	}

	public String replyTo() {
		return null;
	}

	public String reversePath() {
		return null;
	}

	public String sender() {
		return null;
	}

	public String subject() {
		return null;
	}

	public String to() {
		return null;
	}

}
