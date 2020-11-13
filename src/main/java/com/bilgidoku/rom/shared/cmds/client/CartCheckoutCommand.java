package com.bilgidoku.rom.shared.cmds.client;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;

public class CartCheckoutCommand extends ClientCommandBase{
	
	public CartCheckoutCommand() {
		super("c:cartcheckout");
	}

	@Override
	public Tag getDef() {
		return new TagImpl("c:cartcheckout", false, "cmd", new Att[] {}, emptylist, null, "");
	}


}
