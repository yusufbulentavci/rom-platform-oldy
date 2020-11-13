package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxFactory;
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

class PageContextBoxRegistry extends InputBoxRegistry {
	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_PAGE;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new PageContextBox(att, value);
	}

}

public class PageContextBox extends InputBox {

	private final Widget holder;
	final TextBox tf = new TextBox();
	final BrowsePages bi = (BrowsePages) InputBoxFactory.getBrowseFor("page");

	protected PageContextBox(Att att, String value) {
		holder = getTextBox(att, value);
		initWidget(holder);
		forCloseBrowsePages(att);
	}

	public static InputBoxRegistry getRegistry() {
		return new PageContextBoxRegistry();
	}


	private void forCloseBrowsePages(final Att att) {
		bi.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (bi.selected == null || bi.selected.isEmpty())
					return;
				tf.setValue(bi.selected);
				PageContextBox.this.fireEvent(new InputChanged(att.getNamed(), bi.selected));
			}
		});		
	}

	private Widget getTextBox(final Att att, String value) {
		
		tf.setTitle(att.getNamed());
		tf.setValue(value);
				
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

		SiteButton cb = new SiteButton("/_local/images/common/document.png", "", Ctrl.trans.writings(), "");
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				bi.show();
				bi.center();
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
