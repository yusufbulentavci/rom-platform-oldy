package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
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

public class FontBox extends InputBox {

	private final Widget textBox;
	public JSONObject objFont;

	public FontBox(String name, JSONObject value) {
		objFont = value;
		textBox = getTextBox(name);
		initWidget(textBox);
	}


	private Widget getTextBox(final String attName) {
		final TextBox tf = new TextBox();
		tf.setTitle(attName);
		tf.setValue(objFont != null ? objFont.get("fontfamily").isString().stringValue() : "");
		tf.setWidth("125px");
		
		final BrowseFont bi = new BrowseFont(objFont);
		bi.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (bi.selectedObject == null)
					return;

				tf.setValue(bi.selectedObject.get("fontfamily").isString().stringValue());
				objFont = bi.selectedObject;
				FontBox.this.fireEvent(new InputChanged(attName, bi.selectedObject.toString()));
			}
		});

		SiteButton cb = new SiteButton("/_local/images/common/font.png", "", Ctrl.trans.font(), "");
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				bi.show();
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(tf);
		hp.add(cb);

		return hp;

	}

}
