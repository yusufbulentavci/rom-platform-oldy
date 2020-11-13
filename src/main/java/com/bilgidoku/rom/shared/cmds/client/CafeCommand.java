package com.bilgidoku.rom.shared.cmds.client;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;

public class CafeCommand extends ClientCommandBase {

	public CafeCommand() {
		super("c:cafe");
	}

	@Override
	public Tag getDef() {
		Att reqcmd = new AttImpl("reqcmd", false, Att.STRING, emptylist, 1, "");
		Att str = new AttImpl("str", false, Att.STRING, emptylist, 1, "");
//		Att more = new AttImpl("more", false, Att.OBJECT, emptylist, 1, "");
		return new TagImpl("c:cafe", false, "cmd", new Att[] { reqcmd, str}, emptylist, null, "");
	}

}
