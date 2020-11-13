package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;

public class ApplyKendinTasarla {
	
	public ApplyKendinTasarla() {
		WritingsDao.neww(Ctrl.infoLang(), "Kendin Tasarla", "/kendin-tasarla", "/_/writings", new StringResponse() {
			public void ready(final String writingUri) {
				ResourcesDao.sethtmlfile("w:designpage", "/kendin-tasarla", new StringResponse() {
				});

			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				super.err(statusCode, statusText, exception);
			}
		});

	}

}
