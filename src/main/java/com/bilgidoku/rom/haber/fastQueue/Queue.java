package com.bilgidoku.rom.haber.fastQueue;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;

public interface Queue {

	boolean retry(Cmd cmd) throws KnownError;

	void failed(Cmd cmd) throws KnownError;

	void success(Cmd cmd) throws KnownError;

	void tick();

	String push(JSONObject jo) throws KnownError;

	Cmd pop();
	
	int size();
}
