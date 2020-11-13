package com.bilgidoku.rom.gwt.client.util.cmd.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.ecommerce.Ecommerce;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.client.CartCheckoutCommand;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;

public class CartCheckoutCommandImpl extends CartCheckoutCommand{
	
	@Override
	protected void execute(Elem curElem, RenderCallState rz) throws RunException {
		String contact=RomEntryPoint.com().get("user.contact");
		
		if(contact==null){
			RomEntryPoint.com().post("*userneed", "mode", "contact");
			return;
		}
		
		Ecommerce e=(Ecommerce) RomEntryPoint.cm().comp("+ecommerce", null);
		
		e.checkOut();
		
	}

}
