package com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.change.Change;
import com.bilgidoku.rom.site.kamu.graph.client.change.CreateRect;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class DlgTextForm extends ActionBarDlg {	
	
	public String text = null;
	
	TextArea ta = new TextArea();

	private ChangeCallback caller;
	
	public DlgTextForm(final ChangeCallback caller) {
		
		super(GraphicEditor.trans.addText(), null, GraphicEditor.trans.useIt());
		this.caller = caller;
		
		
		ta.setSize("400px", "80px");
		run();
		this.setGlassEnabled(true);
		this.show();
		this.center();

		
	}

	@Override
	public Widget ui() {
		return ta;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ok() {
		text = ta.getValue();
		
		
		int w = (int) (GraphicEditor.db.getEditSize().getX()*0.60);
		int h = (int) (w*0.75);
		
		Sistem.outln("EDITLOC:"+GraphicEditor.db.getEditLocation().getX()+","+GraphicEditor.db.getEditLocation().getY());
//		
		Change change = new CreateRect(GraphicEditor.db.getEditLocation().getX(), GraphicEditor.db.getEditLocation().getY(), w, h, text);
//		Change change = new CreateRect(73, 187, w, h, text);
		caller.textChanged(change);				

		
	}

}
