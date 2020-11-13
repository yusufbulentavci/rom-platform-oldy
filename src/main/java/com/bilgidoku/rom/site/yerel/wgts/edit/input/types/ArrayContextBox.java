package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.ChangeHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class ArrayContextBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_ARRAY;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new ArrayContextBox(att, value);
	}

}

public class ArrayContextBox extends InputBox {

	private final Widget textBox;

	public ArrayContextBox(Att att, String value) {
		textBox = getTextBox(att.getNamed(), value);
		initWidget(textBox);
	}

	public ArrayContextBox(String name, String value) {
		textBox = getTextBox(name, value);
		initWidget(textBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new ArrayContextBoxRegistry();
	}

	private JSONArray arr = null;

	private Widget getTextBox(final String named, final String value) {
		final TextBox tf = new TextBox();
		// tf.setEnabled(false);
		tf.setTitle(named);

		tf.addChangeHandler(new com.google.gwt.event.dom.client.ChangeHandler() {
			@Override
			public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {
				ArrayContextBox.this.fireEvent(new InputChanged(named, tf.getValue()));
			}
		});

		if (value != null && !value.isEmpty() && value.length() > 2) {
			com.google.gwt.json.client.JSONArray a = JSONParser.parseLenient(value).isArray();
			arr = new JSONArray(a);
			tf.setValue(arr.size() + " items");

		}
		
		SiteButton cb = new SiteButton("/_local/images/common/pencil.png", Ctrl.trans.select(""), Ctrl.trans.select(""),
				"");
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final BrowseArrayItems bi = new BrowseArrayItems();
				if (value != null && !value.isEmpty() && value.length() > 2) {
					bi.loadData(arr);
				}
				bi.show();
				bi.center();
				bi.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {

						if (bi.returningArray == null || bi.returningArray.isEmpty())
							return;
						// tf.setValue(bi.returningArray.split(",").length +
						// "
						// items");
						tf.setValue("[" + bi.returningArray + "]");
						ArrayContextBox.this.fireEvent(new InputChanged(named, "[" + bi.returningArray + "]"));

					}
				});
			}
		});

		tf.setWidth("300px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(tf);
		hp.add(cb);

		return hp;

	}

}
