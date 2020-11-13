package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.TextArea;

class GenericHTMLTextRegistry extends InputBoxRegistry {


	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_HTMLTEXT;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new GenericHTMLText(att, value);
	}

}

public class GenericHTMLText extends InputBox {

	private final TextArea textBox;

	protected GenericHTMLText(Att att, String value) {
		textBox = getTextBox(att, value);
		initWidget(textBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new GenericHTMLTextRegistry();
	}

	private TextArea getTextBox(final Att att, String value) {
		final TextArea tf = new TextArea() {
			@Override
			public String getValue() {
				String val = super.getValue();
				if (val == null)
					return null;							
				val = val.replace("\n", "<br>");
				return val;
			}
			
			
		};
		tf.setTitle(att.getNamed());
		tf.setSize("302px", "60px");
		tf.setValue(value != null ? value.replace("<br>", "\n") : "");

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

		return tf;
	}	
	

}
