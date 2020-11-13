package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.xml.Elem;


public class SetPropertyCommand extends Command {

	public SetPropertyCommand() {
		super("c:setproperty");
	}
	
	@Override
	public Tag getDef() {
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");
		Att val = new AttImpl("val", false, Att.STRING, emptylist, 1, "");

		Att when = new AttImpl("when", false, Att.STRING, emptylist, 1, "Change if this evaluates to true");
		Att goal = new AttImpl("goal", false, Att.STRING, emptylist, 1, "_goal variable should be equal to");


		return new TagImpl("c:setproperty", false, "cmd", new Att[] { var, val, when, goal }, emptylist, null, "");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {

		if(!rcs.evaluateCondition("when", true, true)){
			return;
		}
		
		if(!rcs.checkGoal("goal", true, true)){
			return;
		}
		
		String var=rcs.getParamStr("var", false, null);
		MyLiteral val = rcs.evaluateLiteral("val", true, null);
		if(val!=null)
			curElem.setAttribute(var, val.toString());
	}
}
