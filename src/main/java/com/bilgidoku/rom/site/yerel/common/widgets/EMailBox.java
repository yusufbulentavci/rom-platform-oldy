package com.bilgidoku.rom.site.yerel.common.widgets;

import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class EMailBox extends Composite {

	public boolean isOK = false;
	final PasswordTextBox tb = new PasswordTextBox();
	final HTML lb = new HTML();

	public EMailBox() {
		tb.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				checkValue(tb.getValue());
			}
		});

		tb.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				checkValue(tb.getValue());
			}
		});

		tb.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				lb.setHTML("");
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(tb);
		hp.add(lb);
		initWidget(hp);
	}

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	protected void checkValue(String pass) {
		RegExp patt = RegExp.compile(EMAIL_PATTERN);

		String fault = null;
		if (patt.exec(pass) == null) {
			fault = Ctrl.trans.regexpEMail();
		}

		if (fault != null) {
			lb.setHTML(fault);
			isOK = false;
		} else {
			isOK = true;
			lb.setHTML("");
		}
	}
}
