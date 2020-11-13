package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;

class BooleanRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_BOOLEAN;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new BooleanContextBox(att, value);
	}

}

public class BooleanContextBox extends InputBox {

	private final CheckBox cBox;

	protected BooleanContextBox(Att att, String value) {
		cBox = getCheckBox(att, value);
		initWidget(cBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new BooleanRegistry();
	}

	private CheckBox getCheckBox(final Att att, String value) {
		final CheckBox tf = new CheckBox();
		tf.setTitle(att.getNamed());
		tf.setWidth("300px");
		boolean val = Boolean.parseBoolean(value);
		tf.setValue(val);

		tf.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				fireEvent(new InputChanged(att.getNamed(), tf.getValue() + ""));
				
			}
		});
		

		return tf;
	}

}
