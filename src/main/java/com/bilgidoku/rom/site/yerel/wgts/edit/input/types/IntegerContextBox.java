package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

class IntegerBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {		
//		return att.getType() == 0;
		return att.getContext() == Att.CONTEXT_INT;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new IntegerContextBox(att, value);
	}

}

public class IntegerContextBox extends InputBox {

	private final ListBox listBox = new ListBox(false);

	protected IntegerContextBox(final Att att, String value) {
		listBox.setName(att.getNamed());		
		listBox.setTitle(att.getNamed());
		
		for (int i = 0; i < 50; i++) {
			listBox.addItem(i + "");
		}
		
		ClientUtil.findAndSelect(listBox, value);
		
		listBox.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				fireEvent(new InputChanged(att.getNamed(), listBox.getValue(listBox.getSelectedIndex())));
				
			}
		});
		
		initWidget(listBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new IntegerBoxRegistry();
	}

}
