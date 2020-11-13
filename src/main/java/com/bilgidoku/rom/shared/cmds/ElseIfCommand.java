package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;


public class ElseIfCommand extends Command {

	public ElseIfCommand() {
		super("c:elseif");
	}

	@Override
	public void execute(final Elem curElem, RenderCallState rcs) throws RunException {
		if(!rcs.cs().canElse){
			return;
		}
		if(!rcs.evaluateCondition("when", false, null)){
			rcs.cs().canElse=true;
			return;
		}
		rcs.cs().canElse=false;
		Elem cur = Doc.appendGroupingNode(curElem);
		rcs.walkChildren(cur);
	}
	
	@Override
	public Tag getDef() {
		Att when = new AttImpl("when", false, Att.STRING, emptylist, 1, "Change if this evaluates to true");
		Att goal = new AttImpl("goal", false, Att.STRING, emptylist, 1, "_goal variable should be equal to");

		return new TagImpl("c:elseif", false, "cmd", new Att[] { when, goal}, emptylist, null, "");
	}
}
