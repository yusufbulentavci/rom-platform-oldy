package com.bilgidoku.rom.site.kamu.activate.client;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;

public interface MyTemplate extends SafeHtmlTemplates {
	@Template("<img src=\"{0}\" />")
	SafeHtml createItem(SafeUri uri);
}
