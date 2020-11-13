package com.bilgidoku.rom.site.yerel.styles;

public class Style {
	private String widget;
	private String tag;
	private String pseClass;
	private String property;
	private String value;
	
	public Style(String w, String t, String pse, String p, String v) {
		this.setWidget(w);
		this.setTag(t);
		this.setPseClass(pse);
		this.setProperty(p);
		this.setValue(v);
	}

	public String getWidget() {
		return widget;
	}

	public void setWidget(String widget) {
		this.widget = widget;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPseClass() {
		return pseClass;
	}

	public void setPseClass(String pseClass) {
		this.pseClass = pseClass;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
