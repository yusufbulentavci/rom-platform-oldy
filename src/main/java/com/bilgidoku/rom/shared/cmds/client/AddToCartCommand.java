package com.bilgidoku.rom.shared.cmds.client;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;

public class AddToCartCommand extends ClientCommandBase{
	
	public AddToCartCommand() {
		super("c:addtocart");
	}

	@Override
	public Tag getDef() {
		Att stock = new AttImpl("stock", false, Att.STRING, emptylist, 1, "");
		return new TagImpl("c:addtocart", false, "cmd", new Att[] {stock}, emptylist, null, "");
	}


}
