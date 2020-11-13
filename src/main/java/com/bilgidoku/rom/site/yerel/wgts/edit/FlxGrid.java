package com.bilgidoku.rom.site.yerel.wgts.edit;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxFactory;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FlxGrid extends ScrollPanel {

	public final Seen caller;
	// private int rowIndex = 0;
	private final VerticalPanel pnlRows = new VerticalPanel();
	private final boolean isDel;

	public FlxGrid(Seen caller, boolean isDeletable) {
		this.caller = caller;
		this.isDel = isDeletable;
		pnlRows.setSpacing(2);
		pnlRows.setWidth("98%");
		this.add(pnlRows);
	}

	public void resetTable() {
		pnlRows.clear();
	}

//	public void addOneRow(String key, String value) {
//		addRow(key, key + ":", value, false, getAttTextBox(key, value));
//	}

	public void addOneParam(Att att, String value, JSONObject translations) {

		InputBox ib = InputBoxFactory.getFor(att, value);
		ib.addInputChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				caller.dataChanged(event.named, event.newValue);
			}
		});

		String label = att.getNamed() + ":";
		if (translations != null && translations.get(att.getNamed()) != null) {
			label = ClientUtil.getString(translations.get(att.getNamed())) + ":";
		}

		addRow(att.getNamed(), label, value, att.isReq(), ib);

	}

	public void addOneRow(Att att, String value) {

		InputBox ib = InputBoxFactory.getFor(att, value);
		ib.addInputChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				caller.dataChanged(event.named, event.newValue);
			}
		});

		addRow(att.getNamed(), att.getNamed() + ":", value, att.isReq(), ib);

	}

	private void addRow(String key, String keyLabel, String value, boolean isReq, Widget tf) {

		Label lbl = new Label(keyLabel);
		lbl.setWidth("80px");

		// Widget warn = isReq ? ClientUtil.getWarningRed("") : new HTML("");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setWidth("330px");
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		hp.setStyleName("site-seperator");
		// hp.add(warn);
		hp.add(lbl);
		hp.add(tf);

		// hp.setCellWidth(warn, "8px");
		hp.setCellWidth(lbl, "80px");

		if (isDel)
			hp.add(getDelButton(key));

		pnlRows.add(hp);

		this.scrollToBottom();
	}

	private Button getDelButton(final String key) {
		final Button btn = new Button("Del");
		btn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Widget parent = btn.getParent();
				parent.removeFromParent();
				caller.dataChanged(key, null);
			}
		});
		return btn;
	}

	public void updateStyleRow(String keyName, String value) {
		for (int i = 0; i < pnlRows.getWidgetCount(); i++) {
			HorizontalPanel hp = (HorizontalPanel) pnlRows.getWidget(i);
			Widget w = hp.getWidget(2);
			if (w instanceof TextBox) {
				TextBox tb = (TextBox) w;
				String key = tb.getTitle();
				if (key.equals(keyName)) {
					tb.setValue(value);
					break;
				}
			} else if (w instanceof ListBox) {
				ListBox tb = (ListBox) w;
				String key = tb.getTitle();
				if (key.equals(keyName)) {
					ClientUtil.findAndSelect(tb, value);
					break;
				}

			}
		}

	}

	public void deleteRow(String name) {
		for (int i = 0; i < pnlRows.getWidgetCount(); i++) {
			Widget wg = pnlRows.getWidget(i);
			if (wg instanceof HorizontalPanel) {
				HorizontalPanel hp = (HorizontalPanel) wg;
				TextBox tb = (TextBox) hp.getWidget(2);
				if (tb.getTitle().trim().equals(name)) {
					hp.removeFromParent();
				}
			}
		}
	}

}
