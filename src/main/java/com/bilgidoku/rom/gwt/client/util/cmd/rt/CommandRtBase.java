package com.bilgidoku.rom.gwt.client.util.cmd.rt;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.CommandRt;
import com.bilgidoku.rom.shared.cmds.RtUi;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONObject;

public abstract class CommandRtBase implements CommandRt{

	
	private final String cmd;

	public CommandRtBase(String cmd){
		this.cmd=cmd;
	}

	public void exec(boolean initial, String htmlId, String cid, RenderCallState rz, RtUi ui, JSONObject jo) throws RunException {
//		Sistem.outln(rz.getCurTag());
		execute(initial, htmlId, cid, rz, ui, jo);
	}

	protected abstract void execute(boolean initial, String htmlId, String cid, RenderCallState rz, RtUi ui, JSONObject jo);

	public String getCmd() {
		return cmd;
	}
	
	public abstract Json json();
}