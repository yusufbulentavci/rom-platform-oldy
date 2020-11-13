package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.AjaxForm;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;


public class FormFieldResetCommand extends Command {

	public FormFieldResetCommand() {
		super("c:formfieldreset");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		AjaxForm af = rcs.form;
		if(af==null)
			return;
		String name = curElem.getAttribute("name");
		if(name==null)
			return;
		af.resetFields+= " "+name+" ";
	}
	
	@Override
	public Tag getDef() {
		Att name = new AttImpl("name", false, Att.STRING, emptylist, 1, "");
		
		return new TagImpl("c:formfieldreset", false, "cmd", new Att[] { name}, emptylist, null, "");
	}
}
