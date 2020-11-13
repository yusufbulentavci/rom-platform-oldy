package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;

public class ApplySearchPage {
	public ApplySearchPage() {
		WritingsDao.neww(Ctrl.infoLang(), Ctrl.trans.search(), "/search", "/_/writings", new StringResponse() {
			public void ready(final String writingUri) {
				ResourcesDao.sethtmlfile("w:searchpage", "/search", new StringResponse() {
				});

				// JSONValue jsonValue = JSONParser.parseStrict("[{\"t\":
				// \"w:page_title\", \"s\": { \"defaultstyle\": {\"float\":
				// \"left\"}}}]");
				// WritingsDao.spot(Ctrl.infoLang(), new
				// Json(jsonValue.isArray()), "/search", new StringResponse()
				// {});
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				super.err(statusCode, statusText, exception);
			}
		});

	}
}
