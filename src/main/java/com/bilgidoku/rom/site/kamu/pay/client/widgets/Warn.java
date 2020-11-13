package com.bilgidoku.rom.site.kamu.pay.client.widgets;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public class Warn {

	
	public static interface WarnTemplate extends SafeHtmlTemplates {
		@Template("<div class=\"site-fancywarn\" >"
//				+ "style=\"position: absolute; top: 77px; left: 528px;\">"
				+ "<div class=\"site-warncontent\">* {0}<br></div>"
				+ "<div class=\"site-warnarrow\">"
				+ "<div class=\"line10\"><!-- --></div>"
				+ "<div class=\"line9\"><!-- --></div>"
				+ "<div class=\"line8\"><!-- --></div>"
				+ "<div class=\"line7\"><!-- --></div>"
				+ "<div class=\"line6\"><!-- --></div>"
				+ "<div class=\"line5\"><!-- --></div>"
				+ "<div class=\"line4\"><!-- --></div>"
				+ "<div class=\"line3\"><!-- --></div>"
				+ "<div class=\"line2\"><!-- --></div>"
				+ "<div class=\"line1\"><!-- --></div>"
				+ "</div>"
				+ "</div>")
		SafeHtml message(String msg);
	}

}
