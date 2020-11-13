package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;

public class ApplyCartPage {
	public ApplyCartPage() {
		WritingsDao.neww(Ctrl.infoLang(), Ctrl.trans.cart(), "/cart", "/_/writings", new StringResponse() {
			public void ready(final String writingUri) {
				new ApplyTemplate("", "", Ctrl.trans.cart(), "/cart", "cart", null);
			}
		});

	}
}
