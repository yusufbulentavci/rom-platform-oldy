package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.xml.Elem;


public class LogCommand extends Command {

	public LogCommand() {
		super("c:log");
	}
	
	@Override
	public Tag getDef() {
		Att val = new AttImpl("val", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:set", false, "cmd", new Att[] { val }, emptylist, null, "");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		MyLiteral val = rcs.evaluateLiteral("val", true, null);
		Sistem.outln(val.toString());
	}
}
