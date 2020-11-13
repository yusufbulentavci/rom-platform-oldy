package com.bilgidoku.rom.site.kamu.graph.client.ui.forms;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.change.Change;
import com.bilgidoku.rom.site.kamu.graph.client.change.CreateRect;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextForm extends Composite {	
	
	public String text = null;
	
	TextArea ta = new TextArea();
	
	public TextForm(final ChangeCallback caller) {	
		SiteButton ok = new SiteButton("/_public/images/bar/plus.png", GraphicEditor.trans.useIt(), GraphicEditor.trans.useIt());
		ok.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				text = ta.getValue();				
				Change change = new CreateRect(10, 10, 100, 100, text);
				caller.textChanged(change);				
			}
		});
		
		ta.setSize("400px", "80px");
		
		VerticalPanel holder= new VerticalPanel();
		holder.setSpacing(12);
		holder.add(ta);
		holder.add(ok);
		
		VerticalPanel vp = new VerticalPanel();		
		vp.add(holder);

		initWidget(vp);
		
	}

}
