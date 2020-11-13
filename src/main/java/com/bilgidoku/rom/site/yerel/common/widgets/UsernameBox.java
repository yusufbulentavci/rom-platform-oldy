package com.bilgidoku.rom.site.yerel.common.widgets;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class UsernameBox extends Composite {

	public boolean isOK = false;
	final TextBox tb = new TextBox();
	final HTML lb = new HTML();

	public UsernameBox() {
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

	protected void checkValue(String usr) {
		String fault = null;
		if (ClientUtil.isValidUserName(usr)) {
			fault = Ctrl.trans.regexpUserName();
		}

		else if (usr.length() < 6) {
			fault = Ctrl.trans.tooShort(Ctrl.trans.userName());
		}

		else if (usr.length() > 40) {
			fault = Ctrl.trans.tooLong(Ctrl.trans.userName());
		}

		else if (usr.equals("abuse") || usr.equals("postmaster") || usr.equals("rombot")) {
			fault = Ctrl.trans.forbiddenUserNames();
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
