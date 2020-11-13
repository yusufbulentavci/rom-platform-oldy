package com.bilgidoku.rom.site.yerel.widget;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

public class ContactMultiWordSuggestion extends MultiWordSuggestion {
	private Contacts personDTO = null;

	public ContactMultiWordSuggestion(Contacts user) {
		// super(MailUtil.getAddr(user), "<img src='"+"'/>" +
		// MailUtil.getAddr(user));
		super(user.email, MailUtil.getAddr(user));
		this.personDTO = user;
	}

	/**
	 * @return the personDTO
	 */
	public Contacts getContact() {
		return personDTO;
	}

}
