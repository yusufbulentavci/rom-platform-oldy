package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;

public interface Comp {
	public CompInfo compInfo();
	
	boolean handle(String cmd, JSONObject cjo) throws RunException;
	boolean areYouThere();

	
	public Object getInterface(String name);

	public void freeze();
	public void resolve();

	void initial();

	void last();

	void dataChanged(String key, String string);
	void processNewState();

	public void compAdded(Comp h);

	public void compRemoved(Comp h);

}
