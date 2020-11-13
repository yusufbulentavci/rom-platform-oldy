package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONObject;

public interface CommandRt {
	

	public void exec(boolean initial, String htmlId, String cid, RenderCallState rz, RtUi rtUi, JSONObject jo) throws RunException;

	public String getCmd();
}
