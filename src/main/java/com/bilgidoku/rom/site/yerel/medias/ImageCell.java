package com.bilgidoku.rom.site.yerel.medias;

import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class ImageCell extends AbstractCell<Files> {
	@Override
	public void render(Context context, Files row, SafeHtmlBuilder sb) {
		if (row == null) {
			return;
		}
		if (ClientUtil.isImage(row.uri))		
			sb.appendHtmlConstant("<img style='width: 150px; height: 150px; margin: 4px; padding: 0; border: 1px solid #d2d2d2;' src= '" + row.uri + "' />");
		
	}
}
