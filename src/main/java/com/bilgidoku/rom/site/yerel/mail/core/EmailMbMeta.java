package com.bilgidoku.rom.site.yerel.mail.core;

import java.util.Map;

public class EmailMbMeta {

	private int count;
	private int seen;

	public EmailMbMeta(Map<String, String> nesting) {
		this.count = getInt(nesting, "m.c");
		this.seen = getInt(nesting, "m.s");
	}

	int getInt(Map<String, String> nesting, String name) {
		if (nesting == null)
			return 0;
		String mcstr = nesting.get(name);
		if (mcstr == null) {
			return 0;
		}
		return Integer.parseInt(mcstr);
	}

	public int getCount() {
		return count;
	}

	public int getUnseen() {
		return count - seen;
	}

	public void readMail() {
		this.seen = this.seen + 1;
	}

	public void unreadMail() {
		if (this.seen > 0)
			this.seen = this.seen - 1;
	}

	public void mailDeleted() {
		if (this.count > 0)
			this.count = this.count - 1;

	}

	public void mailAdded() {
		this.count = this.count + 1;		
	}

}
