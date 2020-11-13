package com.bilgidoku.rom.site.yerel.boxing;

import java.util.List;

import com.bilgidoku.rom.shared.code.Code;

public interface RichTextFeedback {
	
	void removeHtml(String id);
	void htmlChanged(String id, List<Code> ret, String html);

}
