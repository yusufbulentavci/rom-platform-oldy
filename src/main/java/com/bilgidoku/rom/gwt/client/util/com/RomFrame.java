package com.bilgidoku.rom.gwt.client.util.com;


/**
 * 
 * Frame
 * A widget that wraps an IFRAME element, which can contain an arbitrary web site.
 *  
 * @see com.google.gwt.user.client.ui.Frame
 * 
 * @see RomFrameImpl
 * 
 * 
 * 
 * @author rompg
 *
 */
public interface RomFrame {

	void ready();

	void setItem(String cls, String uri);

	void focusItem(String cls, String uri);
	
}
