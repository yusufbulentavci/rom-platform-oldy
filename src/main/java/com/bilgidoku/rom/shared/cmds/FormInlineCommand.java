package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.AjaxForm;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.xml.Elem;


public class FormInlineCommand extends Command {

	public FormInlineCommand() {
		super("c:forminline");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		AjaxForm af = rcs.form;
		if(af==null)
			return;
		ensureId(curElem);
		af.var=rcs.getParamStr("var", true, null);
		af.sucgoal=rcs.getParamStr("sucgoal", true, null);
		af.sucRedirect=rcs.getParamStr("sucredirect", true, null);
		String s=rcs.getParamStr("sucremove", true, null);
		if(s!=null){
			String[] ss = s.trim().split(" ");
			int i=0;
			for (String string : ss) {
				string=string.trim();
				af.remove.set(i++, new JSONString(string));
			}
		}
		
		af.errgoal=rcs.getParamStr("errgoal", true, null);
		af.errRedirect=rcs.getParamStr("errredirect", true, null);
		
		af.accepted=true;
	}
	
	@Override
	public Tag getDef() {
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");
		Att sucgoal = new AttImpl("sucgoal", false, Att.STRING, emptylist, 1, "");
		Att sucredirect = new AttImpl("sucredirect", false, Att.STRING, emptylist, 1, "");
		Att errgoal = new AttImpl("errgoal", false, Att.STRING, emptylist, 1, "");
		Att errredirect = new AttImpl("errredirect", false, Att.STRING, emptylist, 1, "");
		
		return new TagImpl("c:forminline", false, "cmd", new Att[] { var, sucgoal,sucredirect,errgoal,errredirect}, emptylist, null, "");
	}
}
