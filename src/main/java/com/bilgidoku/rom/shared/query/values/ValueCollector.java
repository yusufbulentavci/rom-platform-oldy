package com.bilgidoku.rom.shared.query.values;

import java.util.Map;

public interface ValueCollector {
	
	public Map<String,String> getTags(int hostId);
	public Map<String, String> getContainers(int hostId);
	public Map<String, String> getResources(int hostId, String cls);

}
