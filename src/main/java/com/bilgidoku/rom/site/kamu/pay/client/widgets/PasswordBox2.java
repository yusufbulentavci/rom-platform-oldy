package com.bilgidoku.rom.site.kamu.pay.client.widgets;

import com.bilgidoku.rom.site.kamu.pay.client.widgets.Warn.WarnTemplate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class PasswordBox2 extends Composite {

	/**
	 * This {@link Constants} interface is used to make the toolbar's strings
	 * internationalizable.
	 */
	public interface Trans extends Constants {
		String regexpPasswordExt();

		String regexpPasswordSimple();

		String password();

		String tooShort();

		String tooLong();

		String passwordLength();
	}

	private Trans trans = (Trans) GWT.create(Trans.class);

	public boolean isEmpty = true;

	final PasswordTextBox tb = new PasswordTextBox();
	

	private static final WarnTemplate TEMPLATE = GWT.create(WarnTemplate.class);
	
	final HTML err = new HTML(TEMPLATE.message(""));

	private boolean isSimple;

	public String fault;

	public PasswordBox2(boolean isSimple, String initialWarning) {

		this.isSimple = isSimple;

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
				err.setHTML("");
			}
		});

		err.setStyleName("site-msg");
		err.setVisible(false);
		if (initialWarning != null) {
			err.setHTML(TEMPLATE.message(initialWarning));
			err.setVisible(true);
		}
		err.getElement().getStyle().setTop(0, Unit.PX);
		err.getElement().getStyle().setLeft(144, Unit.PX);
		
		tb.setStyleName("site-text");
		
		FlowPanel hp = new FlowPanel();
		hp.setStyleName("site-relative");
		hp.add(tb);
		hp.add(err);
		initWidget(hp);
	}

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	private static final String PASSWORD_PATTERN_SIMPLE = "((?=.*[a-zA-Z0-9]).{6,20})";

	protected void checkValue(String value) {
		err.setVisible(false);
		isEmpty = false;
		if (value == null || value.isEmpty()) {
			isEmpty = true;
			return;
		}

		if (value.length() < 6) {
			fault = trans.tooShort();
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(fault));
			return;
		}

		if (value.length() > 20) {
			fault = trans.tooLong();
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(fault));
			return;
		}
		
		if (isSimple) {
			RegExp patt = RegExp.compile(PASSWORD_PATTERN_SIMPLE);

			String fault = null;

			if (patt.exec(value) == null) {
				fault = trans.regexpPasswordSimple();
				err.setVisible(true);
				err.setHTML(TEMPLATE.message(fault));
				return;
			}

		} else {

			RegExp patt = RegExp.compile(PASSWORD_PATTERN);

			String fault = null;

			if (patt.exec(value) == null) {
				fault = trans.regexpPasswordExt();
				err.setVisible(true);
				err.setHTML(TEMPLATE.message(fault));
				return;
			}

		}

		fault = null;
		err.setHTML(TEMPLATE.message(""));
		err.setVisible(false);
		
	}

	public String getValue() {
		return tb.getValue();
	}

	public void setMessage(String msg) {
		err.setVisible(true);
		err.setHTML(TEMPLATE.message(msg));
	}

}
