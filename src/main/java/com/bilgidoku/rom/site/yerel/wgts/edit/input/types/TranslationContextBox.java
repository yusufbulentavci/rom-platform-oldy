package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class TranslationContextBoxRegistry extends InputBoxRegistry {


	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_TRANS;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new TranslationContextBox(att, value);
	}

}

public class TranslationContextBox extends InputBox {

	private final Widget textBox;

	protected TranslationContextBox(Att att, String value) {
		textBox = getTextBox(att, value);
		initWidget(textBox);
	}

	public static InputBoxRegistry getRegistry() {
		return new TranslationContextBoxRegistry();
	}

	private Widget getTextBox(final Att att, String value) {
		final TextBox tf = new TextBox();
		tf.setTitle(att.getNamed());
		tf.setValue(value);
//		tf.setEnabled(false);
				
		tf.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				fireEvent(new InputChanged(att.getNamed(), tf.getValue()));
			}
		});
		tf.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				fireEvent(new InputChanged(att.getNamed(), tf.getValue()));
			}
		}, PasteEvent.TYPE);

		SiteButton cb = new SiteButton("/_local/images/common/dunya.png", "", Ctrl.trans.translations(), "");
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final BrowseTranslations bi = new BrowseTranslations();
				bi.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (bi.selectedTranslation == null || bi.selectedTranslation.isEmpty())
							return;
						tf.setValue(bi.selectedTranslation);
						TranslationContextBox.this.fireEvent(new InputChanged(att.getNamed(), bi.selectedTranslation));
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
