package com.bilgidoku.rom.site.yerel.tags;


public class StyleDef {
	public String name;
	public String group;
	public String summary;
	public String type;
	public StyleDef (String name,String group, String summary, String type) {
		this.name=name;
		this.group = group;
		this.summary = summary;
		this.type = type;
	}

	public StyleDef (String name,String group, String summary) {
		this(name,group,summary,null);
	}

}
