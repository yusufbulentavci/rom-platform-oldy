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


public class RtDeskCommand extends Command {

	public RtDeskCommand() {
		super("c:rtdesk");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
//		String dv=rcs.getParamStr("dlgvar", true, "_dlgvar");
//		String name=rcs.evaluateParam("name", true, "desk");
//		
//		String subject=rcs.getParamStr("subject", true, null);
//		
//		String data=(subject==null)?"":"subject="+subject;
//		
//		JsonResponse obj = rcs.post("/_sesfuncs/rtdlgdesk", data);
//		if (!obj.succ) {
//			rcs.setVariable("_postok", JSONBoolean.getInstance(false));
//			if(obj.warn){
//				String errgoal=rcs.getParamStr("warngoal", true, null);
//				rcs.setVariable("_goal", errgoal);
//				rcs.setVariable("_postwarn", JSONBoolean.getInstance(obj.warn));
//			}else{
//				rcs.setVariable("_posterr", new JSONString(obj.errText));
//				String errgoal=rcs.getParamStr("errgoal", true, null);
//				rcs.setVariable("_goal", errgoal);
//			}
//			return;
//		}
//
//		rcs.setVariable(dv, obj.obj.isObject().get("def").isString().stringValue());
//		
//		rcs.setVariable("_postok", JSONBoolean.getInstance(true));
//		
//		String sucgoal=rcs.getParamStr("sucgoal", true, null);
//		
////		Portable.one.rtConnect(name, rcs.getId(), "im", subject);
//		rcs.setVariable("_goal", sucgoal);
	}
	
	@Override
	public Tag getDef() {
		Att name = new AttImpl("name", false, Att.STRING, emptylist, 1, "");
		Att dlgvar = new AttImpl("dlgvar", false, Att.STRING, emptylist, 1, "");
		Att subject = new AttImpl("subject", false, Att.STRING, emptylist, 1, "");
		Att sucgoal = new AttImpl("sucgoal", false, Att.STRING, emptylist, 1, "");
		Att errgoal = new AttImpl("errgoal", false, Att.STRING, emptylist, 1, "");
		Att warngoal = new AttImpl("warngoal", false, Att.STRING, emptylist, 1, "");
		
		
		return new TagImpl("c:rtdesk", false, "cmd", new Att[] { name, dlgvar, subject, sucgoal, warngoal, errgoal}, emptylist, null, "");
	}
}
