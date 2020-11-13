package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.Map;

public class NStylesMap extends NullableMap<String, Styles> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NStylesMap(Map<String, Styles> ret) {
		this.wrap = ret;
	}

	public NStylesMap() {
	}

	@Override
	public NullableMap<String, Styles> cloneWrap() {
		if (wrap == null)
			return new NStylesMap();

		Map<String, Styles> ret = new HashMap<String, Styles>();
		for (java.util.Map.Entry<String, Styles> it : wrap.entrySet()) {
			ret.put(it.getKey(), (Styles) it.getValue().clone());
		}
		return new NStylesMap(ret);
	}

}
