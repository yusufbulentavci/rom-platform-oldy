package com.bilgidoku.rom.shared;

import com.bilgidoku.rom.shared.code.Code;

public class WidgetInstance{
	private final String htmlId; 
	private final Code codeCur;
	private final RunZone runZone;
	public WidgetInstance(RunZone runZone2, Code codeCur2, String htmlId) {
		this.codeCur=codeCur2;
		this.runZone=runZone2;
		this.htmlId=htmlId;
	}
	
	public String getHtmlId() {
		return htmlId;
	}
	public Code getCodeCur() {
		return codeCur;
	}
	public RunZone getRunZone() {
		return runZone;
	}
}