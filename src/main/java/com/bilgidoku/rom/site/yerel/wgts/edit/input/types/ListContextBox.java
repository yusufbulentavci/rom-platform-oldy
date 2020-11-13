package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxFactory;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class ListContextBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_LIST || att.getNamed().indexOf("list") > 0;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new ListContextBox(att, value);
	}

}

public class ListContextBox extends InputBox {

	private final Widget textBox;
	final BrowseLists bi = (BrowseLists) InputBoxFactory.getBrowseFor("list");
	final TextBox tf = new TextBox();

	protected ListContextBox(Att att, String value) {
		textBox = getTextBox(att, value);
		initWidget(textBox);
		forClose(att);
	}

	private void forClose(final Att att) {
		bi.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (bi.selected == null || bi.selected.isEmpty())
					return;
				tf.setValue(bi.selected, false);
				ListContextBox.this.fireEvent(new InputChanged(att.getNamed(), bi.selected));
			}
		});

	}

	public static InputBoxRegistry getRegistry() {
		return new ListContextBoxRegistry();
	}

	private Widget getTextBox(final Att att, String value) {
		tf.setTitle(att.getNamed());
		tf.setValue(value);
		tf.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				ListContextBox.this.fireEvent(new InputChanged(att.getNamed(), tf.getValue()));
			}
		});

		SiteButton btnBrowse = new SiteButton("/_local/images/common/image_search.png", Ctrl.trans.select(""),
				Ctrl.trans.select(""), "");
		btnBrowse.addClickHandler(new ClickHandler() {
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
		hp.add(btnBrowse);
//		hp.add(btnOPenList);

		return hp;

	}

}
