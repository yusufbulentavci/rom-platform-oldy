package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.util.common.Data;

public class SiteTreeItemData {
	private String uriPrefix;
	private String title;
	private String uri;
	private boolean isContainer;
	private Long mask = Data.WRITING_PUBLIC_MASK;

	public SiteTreeItemData(String text, String uri, boolean isContainer, String uriPrefix) {
		this.setTitle(text);
		this.setUri(uri);
		this.setUriPrefix(uriPrefix);
		this.setContainer(isContainer);
	}

	public SiteTreeItemData(String id, boolean isContainer) {
		this.setTitle(id);
		this.setUri(id);
		this.setContainer(isContainer);
	}

	public String getUriPrefix() {
		return uriPrefix;
	}

	public void setUriPrefix(String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	public String getTitle() {
		return title;
	}

	public String getTextFromUri() {
		return uri.substring(uri.lastIndexOf("/") + 1);
	}

	public void setTitle(String text) {
		this.title = text;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isContainer() {
		return isContainer;
	}

	public void setContainer(boolean isContainer) {
		this.isContainer = isContainer;
	}

	public void setMask(Long nMask) {
		this.mask = nMask;
	}

	public Long getMask() {
		return mask;
	}
}
