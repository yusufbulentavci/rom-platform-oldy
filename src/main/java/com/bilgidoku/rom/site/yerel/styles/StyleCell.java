package com.bilgidoku.rom.site.yerel.styles;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class StyleCell extends AbstractCell<Style> {
	
	@Override
	public void render(Context context, Style row, SafeHtmlBuilder sb) {
		if (row == null) {
			return;
		}
		sb.appendHtmlConstant("<table style='border-bottom: 1px solid #999999; width: 97%'>");
		sb.appendHtmlConstant("<tr>");
		sb.appendHtmlConstant("<td width='10%' >"+row.getWidget()+"</td>");
		sb.appendHtmlConstant("<td width='10%' >"+row.getTag()+"</td>");
		sb.appendHtmlConstant("<td width='20%' >"+row.getPseClass()+"</td>");
		sb.appendHtmlConstant("<td width='30%' >"+row.getProperty()+"</td>");
		sb.appendHtmlConstant("<td width='30%' >"+row.getValue()+"</td>");
		sb.appendHtmlConstant("</tr></table>");

	}
}
