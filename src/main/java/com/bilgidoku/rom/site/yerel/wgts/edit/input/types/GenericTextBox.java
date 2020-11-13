package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.TextBox;

class GenericTextBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return true;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new GenericTextBox(att, value);
	}

}

public class GenericTextBox extends InputBox {

	private final TextBox textBox;

	protected GenericTextBox(Att att, String value) {
		textBox = getTextBox(att, value);
		initWidget(textBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new GenericTextBoxRegistry();
	}

	private TextBox getTextBox(final Att att, String value) {
		final TextBox tf = new TextBox();
		tf.setTitle(att.getNamed());
		tf.setWidth("300px");
		tf.setValue(value);

//		tf.addKeyPressHandler(new KeyPressHandler() {			
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				fireEvent(new InputChanged(att.getNamed(), tf.getValue()));				
//			}
//		});
		tf.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				fireEvent(new InputChanged(att.getNamed(), tf.getValue()));
			}
		});
		tf.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				fireEvent(new InputChanged(att.getNamed(), tf.getValue()));
			}
		});
		tf.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				fireEvent(new InputChanged(att.getNamed(), tf.getValue()));
			}
		}, PasteEvent.TYPE);

		tf.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				fireEvent(new InputChanged(att.getNamed(), tf.getValue()));				
			}
		});

		return tf;
	}

}
