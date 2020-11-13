package com.bilgidoku.rom.site.yerel.common;


import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class SimpleProgressBar extends Composite {

	public interface StatusCellSafeHTMLTemplate extends SafeHtmlTemplates {
		@Template("<div><div style=\"float:left;padding-top:5px;\">{1}</div><div style=\"float:right;margin-top:3px;height:15px;width:300px;border:thin #7ba5d5 solid;\">"
				+ "<div style=\"height:15px;width:{0}%; background:#8cb6e6; background-image: url('/_local/images/progress_background.png');\"></div>" +
				//"<div style=\"height:15px; margin:-15px;font-weight:bold;color:#4e7fba;\"><center></center></div>" +
				"</div></div>")
				SafeHtml status(int percentage, String desc);
	}

	final private StatusCellSafeHTMLTemplate statusCellSafeHTMLTemplate = (StatusCellSafeHTMLTemplate) GWT
	.create(StatusCellSafeHTMLTemplate.class);

	final private HTML widget = new HTML();

	public SimpleProgressBar() {
		initWidget(widget);
	}

	public void setProgress(String desc, int prc) {
		widget.setHTML(statusCellSafeHTMLTemplate.status(prc, desc));
	}

}
