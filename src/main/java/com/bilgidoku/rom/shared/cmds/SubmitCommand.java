package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.request.JsonResponse;
import com.bilgidoku.rom.shared.xml.Elem;

public class SubmitCommand extends Command {

	public SubmitCommand() {
		super("c:submit");
	}
	
	@Override
	public Tag getDef() {
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:submit", false, "cmd", new Att[] { var}, emptylist, null, "");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
//		String s = rcs.evaluate("event.targetid").getString();
//		if(s.length()==0)
//			return;
//		
//		JsonResponse obj = rcs.submit(s,"");
//		if (!obj.succ) {
//			rcs.setVariable("_submitok", JSONBoolean.getInstance(false));
//			rcs.setVariable("_submitwarn", JSONBoolean.getInstance(obj.warn));
//			rcs.setVariable("_submiterr", new JSONString(obj.errText));
//			return;
//		}
//		String var = rcs.getParamStr("var", true, "_submit");
//		rcs.setVariable(var, obj.obj);
//		rcs.setVariable("_submitok", JSONBoolean.getInstance(true));
	}
}