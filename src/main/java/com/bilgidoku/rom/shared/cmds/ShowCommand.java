package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;


public class ShowCommand extends Command{

	public static ShowCommand one;
		
	public ShowCommand() {
		super("c:show");
		one=this;
	}
	
	@Override
	public Tag getDef() {
		Att var = new AttImpl("toshow", false, Att.STRING, emptylist, 1, "");
		Att item = new AttImpl("item", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:show", false, "cmd", new Att[] { var, item }, emptylist, null, "");
	}
	
	@Override
	public void execute(Elem curElem, final RenderCallState rcs) throws RunException {
		Boolean inverse = rcs.evaluateCondition("toshow", true, true);
		String item = rcs.evaluateParam("item", false, null);
		Portable.one.domShow(item, !inverse);
		
	}



}
