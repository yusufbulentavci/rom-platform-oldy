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

class FontBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_FONT;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new FontContextBox(att, value);
	}

}

public class FontContextBox extends InputBox {

	private final Widget textBox;
	public JSONObject objFont;
	private BrowseFont bi;

	protected FontContextBox(Att att, String value) {
		if (value != null && value.startsWith("{"))
			objFont = com.google.gwt.json.client.JSONParser.parseStrict(value).isObject().isObject();
		
//		JSONArray ja = null;
		
//		if (boxer.infoObj.get("text_font").isObject().get("extrafonts") != null && boxer.infoObj.get("text_font").isObject().get("extrafonts").isArray() != null)
//			ja = boxer.infoObj.get("text_font").isObject().get("extrafonts").isArray();
		
		bi = new BrowseFont(objFont);
		
		textBox = getTextBox(att.getNamed());
		initWidget(textBox);		
	}

	public static InputBoxRegistry getRegistry() {
		return new FontBoxRegistry();
	}

	private Widget getTextBox(final String attName) {
		final TextBox tf = new TextBox();
		
		bi.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (bi.selectedObject == null)
					return;

				tf.setValue(bi.selectedObject.get("fontfamily").isString().stringValue());
				objFont = bi.selectedObject;
				FontContextBox.this.fireEvent(new InputChanged(attName, bi.selectedObject.toString()));
			}
		});
		
		tf.setTitle(attName);
		tf.setValue(objFont != null ? objFont.get("fontfamily").isString().stringValue() : "");
		tf.setWidth("125px");
		SiteButton cb = new SiteButton("/_local/images/common/font.png", "", Ctrl.trans.font(), "");
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				bi.show();
				bi.center();
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(tf);
		hp.add(cb);

		return hp;

	}

	public String getFontValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
