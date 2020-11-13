package com.bilgidoku.rom.shared;

import java.util.Map;

import com.bilgidoku.rom.shared.code.Code;

public interface CodeEditor {

	void addBoxer(RunZone rz, String nid, String lngcode, String uri, String tbl, String symbl, String clmn, Code code);
	void htmlReady();
	
	Map boxHolders();
	
	boolean inPreviewMode();

}
