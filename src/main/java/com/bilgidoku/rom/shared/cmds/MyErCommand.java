package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;


public class MyErCommand extends Command {

	public MyErCommand() {
		super("c:myer");
	}
	
	@Override
	public Tag getDef() {

		return new TagImpl("c:myer", false, "cmd", new Att[] { }, emptylist, null, "");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		Sistem.errln("Error now");
	}
}
