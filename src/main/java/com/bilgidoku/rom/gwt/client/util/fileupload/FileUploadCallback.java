package com.bilgidoku.rom.gwt.client.util.fileupload;

public interface FileUploadCallback {
	public void uploaded(String name, String id);
	void setStatus(String text);
}
