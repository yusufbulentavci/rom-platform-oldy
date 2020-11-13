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


public class SetCommand extends Command {

	public SetCommand() {
		super("c:set");
	}
	
	@Override
	public Tag getDef() {
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");
		Att val = new AttImpl("val", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:set", false, "cmd", new Att[] { var, val }, emptylist, null, "");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		String var=rcs.getParamStr("var", false, null);
		MyLiteral val = rcs.evaluateLiteral("val", true, null);
		if(val==null)
			rcs.setVariable(var, new MyLiteral());
		else
			rcs.setVariable(var, val);
	}
}
