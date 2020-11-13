package com.bilgidoku.rom.gwt.client.util.cmd.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Authenticator;
import com.bilgidoku.rom.gwt.client.util.ecommerce.Ecommerce;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.client.AddToCartCommand;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.util.AsyncMethod;
import com.bilgidoku.rom.shared.xml.Elem;

public class AddToCartCommandImpl extends AddToCartCommand {

	public void doit(final RenderCallState rz) {
		String stock;
		try {
			stock = rz.evaluateParam("stock", false, null);
			if (stock == null)
				return;

			Ecommerce e=(Ecommerce) RomEntryPoint.cm().comp("+ecommerce", null);
			
			e.changeCartStockAmount(stock, 1);
		} catch (RunException e) {
			Sistem.printStackTrace(e);
		}

	}

	@Override
	protected void execute(Elem curElem, final RenderCallState rz) throws RunException {
		String cid = RomEntryPoint.com().get("cid");
		if (cid != null) {
			doit(rz);
		}
		Authenticator.needCid(new AsyncMethod<String, String>() {

			@Override
			public void run(String param) {
				doit(rz);
			}

			@Override
			public void error(String param) {

			}
		});

	}

}
