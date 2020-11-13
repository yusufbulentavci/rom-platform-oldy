package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.wgts.edit.Seen;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextArea;

public class NodePnlText extends FlexTable implements Seen {
	private Code code;
	
	private boolean shown = false;
	private final TextArea nodeTextArea = new TextArea();

	boolean changed=false;

	public NodePnlText() {
		forChange();
		ui();
	}
	
	private void ui() {
		this.setSize("92%", "120px");
		this.nodeTextArea.setSize("100%", "100px");
		this.setWidget(0, 0, nodeTextArea);

		this.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		this.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
	}
	
	private void forChange() {
		nodeTextArea.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				changed();
			}
		});
		nodeTextArea.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				changed();
			}
		}, PasteEvent.TYPE);
		
		nodeTextArea.addKeyUpHandler(new KeyUpHandler() {			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				changed();				
			}
		});
		
	}

	public void update(Code code) {
		this.code=code;
	}

	@Override
	public void showData() {
		if (code.tag.startsWith("w:"))
			return;
		
		if (code == null)
			return;
		nodeTextArea.setValue(code.getText());
	}

	@Override
	public void dataChanged(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	private void changed() {
		this.code.setText(nodeTextArea.getValue());
		this.changed=true;
	}

	@Override
	public void dataChanged(String type, String name, String value) {
		// TODO Auto-generated method stub
		
	}

}
