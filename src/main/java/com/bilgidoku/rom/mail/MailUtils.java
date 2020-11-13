package com.bilgidoku.rom.mail;

public class MailUtils {

	public static String getInbox(String name) {
		return "/_/mails/"+name+"/inbox";
	}
	
	public static String getSpam(String name) {
		return "/_/mails/"+name+"/spam";
	}
}
