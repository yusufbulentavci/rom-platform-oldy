package com.bilgidoku.rom.site.yerel.mail.widgets;

import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class MailAddrs extends Composite {

	public MailAddrs(InternetAddress[] internetAddresses) {

		HorizontalPanel fp = new HorizontalPanel();
		fp.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

		if (internetAddresses != null)
			for (int i = 0; i < internetAddresses.length; i++) {
				InternetAddress addr = internetAddresses[i];
				fp.add(new MailAddr(addr, isContact(addr)));
			}

		this.initWidget(fp);
	}

	private boolean isContact(InternetAddress addr) {
		return true;
	}


}
