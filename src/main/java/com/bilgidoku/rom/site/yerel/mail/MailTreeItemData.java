package com.bilgidoku.rom.site.yerel.mail;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.mail.core.EmailMbMeta;


public class MailTreeItemData extends SiteTreeItemData {
	
	public EmailMbMeta meta = null;

	public MailTreeItemData(String text, String uri, boolean b, String uri_prefix, EmailMbMeta mm) {
		super(text, uri, b, uri_prefix);
		this.meta = mm;
	}

	
}
