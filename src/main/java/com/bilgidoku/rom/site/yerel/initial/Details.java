package com.bilgidoku.rom.site.yerel.initial;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;

public class Details extends SiteTreeItemData{
	public Details(String name2, String uri2, boolean menu2, boolean footer2, boolean vitrine2, String app2, String group2, boolean isContainer) {
		super(name2, uri2, isContainer, null);
		this.name = name2;
		this.menu = menu2;
		this.footer = footer2;
		this.vitrine = vitrine2;
		this.app = app2;
		this.group = group2;
	}

	public Details(String name2, String uri2, String app2, String group2, boolean isContainer) {
		super(name2, uri2, isContainer, null);
		this.name = name2;
		this.menu = false;
		this.footer = false;
		this.vitrine = false;
		this.app = app2;
		this.group = group2;
	}

	public String realuri;
	public String name;
	public String group;
	public String app;
	public boolean menu = false;
	public boolean footer = false;
	public boolean vitrine = false;

	public String getRealUri() {
		//if not exists
		if (group == null || group.length() == 0) {
			if (getUri().equals("")) {
				return "/";
			}
			return "/" + getUri();
		}

		if (getUri().equals("")) {
			return "/" + group;
		}

		if (getUri().equals("home")) {
			return "/";
		}
		return "/" + group + "/" + getUri();
	}
}
