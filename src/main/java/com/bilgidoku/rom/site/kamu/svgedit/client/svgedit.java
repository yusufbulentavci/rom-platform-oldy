package com.bilgidoku.rom.site.kamu.svgedit.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class svgedit extends RomEntryPoint {

	JavaScriptObject editor;
	private String width = "800px";
	private String height = "600px";
	
	public svgedit() {
		super("Rom Server Svg-Edit Application", false, null, false, false);
	}

	

	@Override
	protected void main() {
		checkEditor();
	}

	private void checkEditor() {
		if(checkEditorIn()){
			Sistem.outln("Editor is defined");
		}else{
			Sistem.outln("Editor is UNDEFINED");
		}
			
	}



	protected native boolean checkEditorIn()/*-{
		if(!(typeof editor == 'undefined')){
			return true;
		}
		try{
			editor = $wnd.document.getElementById('svgedit').contentWindow.svgCanvas;
			if(!(typeof editor == 'undefined'))
				return true;
		}catch(err){
		}
		return false;

	}-*/;

	protected native void setSvg(String text)/*-{
		editor.setSvgString(text);

	}-*/;
	
	protected native String getSvg()/*-{
		return editor.getSvgString();
	}-*/;

	protected native void focusHtml()/*-{
//		editor.focus();
	}-*/;


	@Override
	public void comSetStr(final String s) {
		if(!checkEditorIn()){
			Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
				@Override
				public boolean execute() {
					comSetStr(s);
					return false;
				}
			}, 500);
			return;
		}
		
		setSvg(s);
	}

	@Override
	public String comGetStr() {
		checkEditor();
		return getSvg();
	}

	

}
