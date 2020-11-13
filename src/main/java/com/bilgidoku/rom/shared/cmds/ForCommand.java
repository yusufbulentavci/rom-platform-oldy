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


public class ForCommand extends Command {

	public ForCommand() {
		super("c:for");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		int limit=rcs.evaluateInt("limit", false,null);
		int initial=rcs.evaluateInt("initial", false,null);
		int increment=rcs.evaluateInt("increment", false,null);
		String itemnovar = rcs.getParamStr("var", true, "_i");
		Elem cur = Doc.appendGroupingNode(curElem);
		
		for (int i = initial; i < limit; i = i + increment) {
				rcs.setVariable(itemnovar, i);
				Elem c = Doc.appendGroupingNode(cur);
				rcs.walkChildren(c);
		}
	}
	@Override
	public Tag getDef() {
		Att limit = new AttImpl("limit", false, Att.STRING, emptylist, 1, "");
		Att initial = new AttImpl("initial", false, Att.STRING, emptylist, 1, "");
		Att increment = new AttImpl("increment", false, Att.STRING, emptylist, 1, "");
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:for", false, "cmd", new Att[] { limit, initial, increment, var}, emptylist, null, "");
	}

	
}
