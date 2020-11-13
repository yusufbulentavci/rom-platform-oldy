package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;

public class RoutineCommand extends Command {
	public RoutineCommand() {
		super("r:");
	}

	@Override
	public void execute(Elem curElem, final RenderCallState rcs) throws RunException {
		rcs.walkChildren(curElem);
	}
	
	
	@Override
	public Tag getDef() {
		return null;
	}

}
