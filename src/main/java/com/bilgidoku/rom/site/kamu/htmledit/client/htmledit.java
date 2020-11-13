package com.bilgidoku.rom.site.kamu.htmledit.client;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

/**
 * 
 * 
 * @author avci
 *
 */
public class htmledit extends RomEntryPoint {
	
	public static final CompInfo info = new CompInfo("+htmledit", 100, new String[] {},
			new String[] {"user","+actionbar"}, null);

	private CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return comp;
		}

	};

	private CompBase comp = new CompBase() {
		
		
		public void resolve(){
			super.resolve();
			checkEditor();
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

	};
	
	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}
	
	

	JavaScriptObject editor;

	public htmledit() {
		super("Rom Server Html Editor Application", false, null, false, false);
	}



	private void checkEditor() {
		if (checkEditorIn()) {
			Sistem.outln("Editor is defined");
		} else {
			Sistem.outln("Editor is UNDEFINED");
		}

	}

	protected native boolean checkEditorIn()/*-{
		if (!(typeof editor == 'undefined')) {
			return true;
		}
		try {
			var tryed = $wnd.CKEDITOR.instances["htmledit"];
			if (typeof tryed == 'undefined') 
				return false;
			
			if(tryed.status != "ready"){
				//console.log(tryed.status);
				//console.log('Not loaded');
				return false;
			}
			console.log('Html editor ready');
			
			editor = tryed;
			editor.resize('100%', '100%');
			
			return true;
		}catch (err) {
		}
		return false;

	}-*/;

	@Override
	public void comSetStr(final String s) {
		if (!checkEditorIn()) {
			Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
				@Override
				public boolean execute() {
					comSetStr(s);
					return false;
				}
			}, 500);
			return;
		}

		setHtml(s);
	}

	@Override
	public String comGetStr() {
		if (!checkEditorIn())
			throw new RuntimeException("Html editor not ready");
		return getHtml();
	}

	

	protected native void setHtml(String text)/*-{
		editor.setData(text);
	}-*/;

	protected native void setHtmlFocus()/*-{
		editor.focus();
	}-*/;

	protected native String getHtml()/*-{
		return editor.getData();
	}-*/;

	

}
