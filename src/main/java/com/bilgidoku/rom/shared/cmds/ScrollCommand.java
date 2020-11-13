package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;


public class ScrollCommand extends Command {

	public ScrollCommand() {
		super("c:scroll");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		
		String refchange=rcs.getParamStr("refchange", true, null);
		if(refchange==null){
			return;
		}
		
		boolean tobottom=rcs.getParamBool("tobottom", true, true);
		
		Portable.one.domScroll(rcs.getChangeHtmlId(), refchange, tobottom);
	}
	
	@Override
	public Tag getDef() {
		Att refchange = new AttImpl("refchange", false, Att.STRING, emptylist, 1, "");
		Att tobottom = new AttImpl("tobottom", false, Att.BOOL, emptylist, 1, "");

		return new TagImpl("c:scroll", false, "cmd", new Att[] { refchange, tobottom}, emptylist, null, "");
	}
}
