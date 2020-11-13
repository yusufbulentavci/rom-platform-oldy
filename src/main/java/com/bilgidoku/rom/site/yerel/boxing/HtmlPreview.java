package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Doc;

public class HtmlPreview {
	public static boolean inPreviewMode;

	public static String doit(Code c, RunZone runZone) throws RunException {

		try {
			inPreviewMode = true;
			c.ensureId();

			Doc doc = new Doc();
			RenderCallState rcs = new RenderCallState(runZone);
			new RenderCallState(runZone).walk(c, doc);

			// runZone.setCodeCur(c);
			// if (c.isWidget()) {
			// WidgetCommand.preview(runZone, doc);
			// } else if (c.isHtml()) {
			// TagCommand.preview(runZone, doc);
			// }
			return doc.toString();
		} finally {
			inPreviewMode = false;
		}
	}

}
