package com.bilgidoku.rom.shared.cmds.htmlevents;

public class OnChangeEvent extends HtmlEventCommand{

	public OnChangeEvent() {
		super("onchange");
		hasValueAtt=true;
	}
}
