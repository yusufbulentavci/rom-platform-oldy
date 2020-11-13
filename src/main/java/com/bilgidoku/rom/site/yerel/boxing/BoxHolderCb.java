package com.bilgidoku.rom.site.yerel.boxing;

import java.util.List;

import com.bilgidoku.rom.shared.code.Code;
import com.google.gwt.json.client.JSONObject;

public interface BoxHolderCb {

	Code popTransfer();

	void pushTransfer(Code code);

	void canvasResize();

	JSONObject getSelectedTrans();

	List<Code> getBoxHtmlCode(String html);

	void logoChanged(com.bilgidoku.rom.shared.json.JSONObject store);

}
