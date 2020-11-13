package com.bilgidoku.rom.shared;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;

public class WaitingZone{
	Elem container;
	RunZone rz;
	Code codes;
	public WaitingZone(Code codes, RunZone rz, Elem container) {
		this.rz=rz;
		this.container=container;
		this.codes=codes;
	}

	public void render() throws RunException {
		try{
			new RenderCallState(rz).walk(codes, container);
		}catch(Exception e){
			Sistem.printStackTrace(e);
		}
	}
}