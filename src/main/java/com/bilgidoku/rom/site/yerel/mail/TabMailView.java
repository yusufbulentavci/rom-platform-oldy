package com.bilgidoku.rom.site.yerel.mail;

import java.util.Set;

import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;


public interface TabMailView {
	public String getType();
	public Set<MailWrap> getMailItems();
	public void markAsReaded(MailWrap email);
	public void markAsUnReaded(MailWrap email);
	public void mailDeleted(MailWrap email);
	public void moved(MailWrap email, String target);
	public void reload();
	public void markAsImportant(MailWrap email);
	public void markAsUnImportant(MailWrap email);
}
