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

public class PasswordBox extends Composite {

	public boolean isOK = false;
	final PasswordTextBox tb = new PasswordTextBox();
	final HTML lb = new HTML();

	public PasswordBox() {
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

	private static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	
	protected void checkValue(String pass) {
		RegExp patt=RegExp.compile(PASSWORD_PATTERN);
		
		String fault = null;
		if (patt.exec(pass)==null) {
			fault = Ctrl.trans.regexpPassword();
		}

		else if (pass.length() < 6) {
			fault = Ctrl.trans.tooShort(Ctrl.trans.password());
		}

		else if (pass.length() > 20) {
			fault = Ctrl.trans.tooLong(Ctrl.trans.password());
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
