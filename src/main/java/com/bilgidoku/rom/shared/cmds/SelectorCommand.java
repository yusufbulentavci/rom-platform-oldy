package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.xml.Elem;

public class SelectorCommand extends Command {

	public SelectorCommand() {
		super("c:selector");
	}

	@Override
	public Tag getDef() {
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");
		Att query = new AttImpl("query", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:selector", false, "cmd", new Att[] { var, query}, emptylist, null, "");
	}
	
	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
	
		String var = rcs.getParamStr("var", true, "_load");
		String query = rcs.evaluateParam("query", false, null);
		
		JSONArray ja=Portable.one.select(query);
		
		rcs.setVariable(var, ja);
		
	}
}