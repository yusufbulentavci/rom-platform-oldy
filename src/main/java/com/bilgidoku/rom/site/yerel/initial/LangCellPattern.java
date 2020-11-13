package com.bilgidoku.rom.site.yerel.initial;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class LangCellPattern extends AbstractCell<String> {
	@Override
	public void render(Context context, String row, SafeHtmlBuilder sb) {
		if (row == null) {
			return;
		}		
		String flag = "";
		if (row.equals("tr") || row.equals("tr_TR")) {
			flag = "/_local/images/common/turkish.png";
		} else if (row.equals("en") || row.equals("en_EN")) {
			flag = "/_local/images/common/english.png";
		}
		//String nativeName = LocaleInfo.getLocaleNativeDisplayName(row);
		sb.appendHtmlConstant("<div style='float:left;height:15px;width:25px;background-repeat:no-repeat;background-image: url(" + flag +")'></div>");
	}
}
