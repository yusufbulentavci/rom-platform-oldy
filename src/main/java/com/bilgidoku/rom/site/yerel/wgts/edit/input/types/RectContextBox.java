package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class RectBoxRegistry extends InputBoxRegistry {


	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_RECT;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new RectContextBox(att, value);
	}

}

public class RectContextBox extends InputBox {

	private final Widget textBox;

	protected RectContextBox(Att att, String value) {
		textBox = getTextBox(att, value);
		initWidget(textBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new RectBoxRegistry();
	}

	private Widget getTextBox(final Att att, String value) {
		final TextBox tf = new TextBox();
		tf.setTitle(att.getNamed());
		tf.setEnabled(false);
		tf.setValue(getShowValue(value));
		tf.setWidth("160px");

		SiteButton cb = new SiteButton("/_local/images/common/resize_both.png", "", Ctrl.trans.size(), "");
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String[] vals = null;
				String val = tf.getValue();
				if (val != null && val.indexOf(",") > 0)
					vals = val.split(",");

				final BrowseRect bi = new BrowseRect(vals);
				bi.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (bi.selectedValue == null || bi.selectedValue.isEmpty())
							return;

						tf.setValue(bi.selectedValue);
						RectContextBox.this.fireEvent(new InputChanged(att.getNamed(), bi.selectedObject));
					}
				});
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(tf);
		hp.add(cb);

		return hp;

	}

	private String getShowValue(String value) {
		if (value == null || value.isEmpty() || !value.startsWith("{"))
			return null;
		JSONObject jo = com.google.gwt.json.client.JSONParser.parseStrict(value).isObject().isObject();
		String w = jo.get("width").isString().stringValue();
		String h = jo.get("height").isString().stringValue();

		return w + ", " + h + "";
	}

}
