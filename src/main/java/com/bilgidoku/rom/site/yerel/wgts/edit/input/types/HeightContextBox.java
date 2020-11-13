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
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class HeightBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		// return att.getContext() == Att.CONTEXT_HEIGHT;
		return att.getContext() == 17;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new HeightContextBox(att, value);
	}

}

public class HeightContextBox extends InputBox {

	private final Widget textBox;
	private final TextBox tf = new TextBox();

	protected HeightContextBox(Att att, String value) {
		textBox = getTextBox(att.getNamed(), value);
		initWidget(textBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new HeightBoxRegistry();
	}

	private Widget getTextBox(final String name, String value) {
		tf.setTitle(name);
		tf.setValue(value);
		tf.setWidth("160px");

		SiteButton cb = new SiteButton("/_local/images/common/resize-height.png", "", Ctrl.trans.height(), "");
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String val = tf.getValue();

				final BrowseHeight bi = new BrowseHeight(val);
				bi.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						// if (bi.selectedValue == null ||
						// bi.selectedValue.isEmpty())
						// return;
						tf.setValue(bi.selectedValue);
						fireEvent(new InputChanged(name, bi.selectedValue));
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

	public void setValue(String val) {
		tf.setValue(val);
	}

	public String getValue() {
		return tf.getValue();
	}

}
