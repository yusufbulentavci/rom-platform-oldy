package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;

public class SiteIntegerBox extends TextBox {

	public SiteIntegerBox() {
		super();
		this.setAlignment(TextAlignment.RIGHT);
		this.setWidth("35px");
		this.setStyleName("gwt-TextBox");
		forKeyUp();
	}

	private void forKeyUp() {
		this.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {				
				String input = SiteIntegerBox.this.getText();
				if (input == null || input.isEmpty())
					return;
				if (!input.matches("^-?\\d+$")) {
					input = input.replaceAll("[^[^-?\\d+$]]", "");
					SiteIntegerBox.this.setText(input);
				}

				String intVal = SiteIntegerBox.this.getValue();
				if (intVal == null) {
					return;
				}

				int val = 0;
				try {
					val = Integer.parseInt(intVal);
				} catch (NumberFormatException f) {
					Window.alert(Ctrl.trans.notValid(Ctrl.trans.number()));
				}

				boolean upPressed = KeyCodes.KEY_UP == event.getNativeEvent().getKeyCode();
				if (upPressed) {
					intVal = (val + 1) + "";
				}

				boolean downPressed = KeyCodes.KEY_DOWN == event.getNativeEvent().getKeyCode();
				if (downPressed) {
					intVal = (val - 1) + "";
				}
				SiteIntegerBox.this.setText(intVal);
			}
		});

	}

	public void setNameValue(final String name, String value) {
		this.setTitle(name);
		this.setName(name);
		this.setValue(value);

	}

	public int getIntVal() {

		String intVal = SiteIntegerBox.this.getValue();
		if (intVal == null) {
			return 0;
		}

		int val = 0;
		try {
			val = Integer.parseInt(intVal);
		} catch (NumberFormatException f) {
		}

		return val;
	}

	public void setJSONValue(JSONValue jsonValue) {
		setValue(ClientUtil.getNumber(jsonValue) + "");
	}

	public void setIntValue(Integer weight) {
		if (weight == null) {
			this.setValue(null);
			return;
		}
		int i = weight.intValue();
		this.setValue(i + "");
	}

}