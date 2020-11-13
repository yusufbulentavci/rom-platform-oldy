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


public class IfCommand extends Command {

	public IfCommand() {
		super("c:if");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
//		String s=ch.getParamStr("when", true, null);
//		if(s.equals("${not empty _eposta}")){
//			s="th";
//		}
		if(!rcs.evaluateCondition("when", true, true) || !rcs.checkGoal("goal", true, true)){
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

		return new TagImpl("c:if", false, "cmd", new Att[] { when, goal}, emptylist, null, "");
	}
}
