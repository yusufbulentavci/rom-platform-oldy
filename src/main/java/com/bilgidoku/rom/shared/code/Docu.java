package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.shared.RunException;

public class Docu {
	Map<String,Code> ws=new HashMap<String,Code>();
	public void newWidget(Code code) throws RunException {
		ws.put(code.ensureId(),code);
	}
	


}
