package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;


public class RedirectCommand extends Command {

	public RedirectCommand() {
		super("c:redirect");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		
		String addr=rcs.evaluateParam("addr", false, null);
		
		
		Portable.one.redirect(addr);
	}
	
	@Override
	public Tag getDef() {
		Att addr = new AttImpl("addr", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:addr", false, "cmd", new Att[] { addr}, emptylist, null, "");
	}
}
