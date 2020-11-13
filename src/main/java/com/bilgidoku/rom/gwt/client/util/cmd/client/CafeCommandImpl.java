package com.bilgidoku.rom.gwt.client.util.cmd.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.client.CafeCommand;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;

public class CafeCommandImpl extends CafeCommand {

	public void doit(final RenderCallState rz) {
		try {
			String reqcmd = rz.getParamStr("reqcmd", false, null);
			String str = rz.evaluateParam("str", false, null);
//			String more = rz.evaluateParam("more", false, null);
			
//			, "more", more.toString()
			RomEntryPoint.com().post("*dlg.req","reqcmd",reqcmd, "str", str);

		} catch (RunException e) {
			Sistem.printStackTrace(e);
		}

	}

	@Override
	protected void execute(Elem curElem, final RenderCallState rz) throws RunException {
		String cid = RomEntryPoint.com().get("cid");
		if (cid == null) {
			return;
		}
		
		doit(rz);
	}

}
