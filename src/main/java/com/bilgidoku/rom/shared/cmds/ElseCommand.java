package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;


public class ElseCommand extends Command {

	public ElseCommand() {
		super("c:else");
	}

	@Override
	public void execute(final Elem curElem, RenderCallState rcs) throws RunException {
		if(!rcs.cs().canElse){
			return;
		}
		rcs.cs().canElse=false;
		Elem cur = Doc.appendGroupingNode(curElem);
		rcs.walkChildren(cur);
	}

	@Override
	public Tag getDef() {
		return new TagImpl("c:else", false, "cmd", new Att[] {}, emptylist, null, "");
	}

	
}
