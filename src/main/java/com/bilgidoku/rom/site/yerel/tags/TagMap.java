package com.bilgidoku.rom.site.yerel.tags;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.shared.Tag;

public abstract class TagMap {
	protected final Map<String, Tag> tagDefs = new HashMap<String, Tag>();
	public final Map<String, StyleDef> styleDefs = new HashMap<String, StyleDef>();

	public TagMap(StyleDef[] styles, Tag[] tags) {
		for (StyleDef s : styles) {
			styleDefs.put(s.name, s);
		}

		for (Tag tag : tags) {
			tagDefs.put(tag.getNamed(), tag);
		}
	}

	public Map<String, StyleDef> getStyleMap() {
		return styleDefs;
	}

	public Map<String, Tag> getTagDefs() {
		return tagDefs;
	}
	
	public Tag getTagDef(String key){
		return tagDefs.get(key);
	}

	public boolean hasStyle() {
		return getStyleMap().size() != 0;
	}
}
