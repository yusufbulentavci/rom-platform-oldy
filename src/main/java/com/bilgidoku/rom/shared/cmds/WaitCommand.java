package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;

public class WaitCommand extends Command {
	public WaitCommand() {
		super("c:wait");
	}

	
	protected void execute(Elem curElem, final RenderCallState rcs) throws RunException {
		int period=rcs.evaluateInt("period", true, 3);
		String id = ensureId(curElem);
//		rz.wait(id, period);
		
	}

	public Tag getDef() {
		return null;
	}
}
