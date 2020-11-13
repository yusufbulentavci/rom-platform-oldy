package com.bilgidoku.rom.site.yerel.mail.core;

import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.site.yerel.one;

public class MailUtil {
	private static MimeContentType mct = new MimeContentType();

	public static Mime buildMime(String subject, String mime, String body, Map<String, String> attachs, InternetAddress[] tos,
			InternetAddress[] ccs, InternetAddress[] bccs) {

		if (attachs == null || attachs.size() == 0) {
			Mime text = new Mime(mime+"; charset=\"UTF-8\"", "inline");
			text.setFrom(new InternetAddress(one.userMail, ""));
			text.setSubject(subject);
			text.setStr(body);
			text.setTos(tos);
			text.setCcs(ccs);
			text.setBccs(bccs);
			return text;
		}

		Mime multi = Mime.createMulti("mixed");
		multi.setSubject(subject);
		multi.setFrom(new InternetAddress(one.userMail, ""));
		multi.setTos(tos);
		multi.setCcs(ccs);
		multi.setBccs(bccs);

		Mime text = new Mime(mime+"; charset=\"UTF-8\"", "inline");
		text.setStr(body);

		multi.addPart(text);

		for (Entry<String, String> a : attachs.entrySet()) {
			String ct = mct.getMimeOfFile(a.getValue());
			Mime attach = Mime.createAttach(a.getValue(), a.getKey(), ct);
			multi.addPart(attach);
		}

		return multi;
	}

	public static String draftMailbox() {
		String user=RomEntryPoint.com().get("user");
		return "/_/mails/" + user + "/draft";
	}

	public static String getAddr(Contacts con) {
		String addr = "";
		if (con.first_name != null && !con.first_name.isEmpty()) {
			addr = con.first_name;
		}
		if (con.last_name != null && !con.last_name.isEmpty()) {
			addr = addr + " " + con.last_name;
		}

		if (addr.length() > 0)
			return addr.trim() + " <" + con.email + ">";
		else
			return con.email;

	}

	public static String getAddrEmail(String ad) {
		// bilo <admin@coreks.com>
		if (ad.indexOf("<") > 0) {
			return ad.substring(ad.indexOf("<") + 1, ad.indexOf(">"));
		} else {
			return ad;
		}
	}

	public static String getAddrFirstName(String ad) {
		// bilo avci <admin@coreks.com>
		if (ad.indexOf("<") > 0) {
			String tmp = ad.substring(0, ad.indexOf("<")).trim();
			String[] tokens = tmp.split(" ");
			if (tokens.length >= 1) {
				String ret = "";
				for (int i = 0; i < (tokens.length - 1); i++) {
					ret = ret + tokens[i] + " ";
				}
				return ret.trim();
			} else
				return tmp;

		} else {
			String mail = getAddrEmail(ad);
			return mail.substring(0, mail.indexOf("@"));
		}
	}

	public static String getAddrLastName(String ad) {
		// bilo avci <admin@coreks.com>
		if (ad.indexOf("<") > 0) {
			String tmp = ad.substring(0, ad.indexOf("<")).trim();
			String[] tokens = tmp.split(" ");
			if (tokens.length >= 1) {
				return tokens[tokens.length - 1];
			} else
				return "";
		} else {
			return "";
		}
	}

	public static String getAddr(InternetAddress ia) {
		String addr = "";
		if (ia.getPersonal() != null) {
			addr = ia.getPersonal() + "<" + ia.getAddress() + ">";
		} else {
			addr = ia.getAddress();
		}

		return addr;
	}

	public static String mailFormat(String name, String mail) {
		if (name == null || name.isEmpty())
			return mail;
		return name + " <" + mail + "> ";
	}

	public static String mailShortFormat(String name, String mail) {
		if (name == null || name.isEmpty())
			return mail;
		return name;
	}

}
