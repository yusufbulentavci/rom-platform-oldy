package com.bilgidoku.rom.site.yerel;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.ui.FlowPanel;


public class ViewPreview extends FlowPanel{
	public ViewPreview() {
	}
	
	final IFrameElement iframe = Document.get().createIFrameElement();
	
	public void preview(String content){
		fillIframe(iframe, content);
	}
	
	@Override
    protected void onLoad() {
        super.onLoad();
        iframe.setNoResize(false);
        iframe.setScrolling("auto");
        iframe.setName("myframe");
        iframe.scrollIntoView();
        getElement().appendChild(iframe);
    }
	
	private final native void fillIframe(IFrameElement iframe, String content) /*-{
	  var doc = iframe.document;
	 
	  if(iframe.contentDocument)
	    doc = iframe.contentDocument; // For NS6
	  else if(iframe.contentWindow)
	    doc = iframe.contentWindow.document; // For IE5.5 and IE6
	 
	   // Put the content in the iframe
	  doc.open();
	  doc.writeln('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">');
	  doc.writeln(content);
	  doc.close();
	}-*/;

}
