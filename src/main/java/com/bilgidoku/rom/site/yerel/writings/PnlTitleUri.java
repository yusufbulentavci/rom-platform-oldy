package com.bilgidoku.rom.site.yerel.writings;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.shared.Utils;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class PnlTitleUri extends Composite {

	final TextBox uri = new TextBox();
	final TextBox tb = new TextBox();
	final Label altNames = new Label();
	final FlexTable holde = new FlexTable();
	private boolean isHome = false;

	public PnlTitleUri() {
		forChangeUri();
		// forFocusTitle();

		tb.setWidth("150px");
		uri.setWidth("150px");
		tb.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!isHome)
					uri.setText(fixName(tb.getValue()));
			}
		});

		holde.setHTML(0, 0, Ctrl.trans.title());
		holde.setWidget(0, 1, tb);
		holde.setWidget(0, 2, altNames);

		holde.setHTML(1, 0, "Uri");
		holde.setWidget(1, 1, uri);

		holde.getFlexCellFormatter().setColSpan(1, 1, 2);
		initWidget(holde);
	}

	private void forChangeUri() {
		uri.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if (uri.getValue() == null || uri.getValue().isEmpty() || !ClientUtil.checkUri(uri.getValue())) {
					Window.alert(Ctrl.trans.validUri());
					return;
				}
			}
		});

	}

	public String getTitle() {
		return tb.getValue();

	}

	public String getUri() {
		return uri.getText().trim();

	}

	public void setTitleValue(String name) {
		if (name == null)
			return;
		tb.setValue(name);
		if (!isHome)
			uri.setValue(fixName(name));
		else
			uri.setValue("");

	}

	private String fixName(String val) {
		if (val == null || val.isEmpty())
			return null;
		String u = Utils.nameFix(val);
		return u;
	}

	public void setAlternativeNames(String[] an) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < an.length; i++) {
			sb.append(", " + an[i]);
		}
		String s = sb.toString().replaceFirst(", ", "");
		if (s != null && !s.isEmpty()) {
			altNames.setText("(" + s + " de olabilir)");
		}

	}

	public void hideUri() {
		holde.getFlexCellFormatter().setVisible(1, 0, false);
		holde.getFlexCellFormatter().setVisible(1, 1, false);
	}

	public void home() {
		isHome = true;
		hideUri();
	}

	public void reset() {
		tb.setValue("");
		uri.setValue("");

	}

}
