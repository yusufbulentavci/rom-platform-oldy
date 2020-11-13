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
import com.google.gwt.user.client.ui.TextBox;

public class EMailBox2 extends Composite {

	/**
	 * This {@link Constants} interface is used to make the toolbar's strings
	 * internationalizable.
	 */
	public interface Trans extends Constants {
		String emailErr();
	}

	private Trans trans = (Trans) GWT.create(Trans.class);

	public boolean isEmpty = true;
	private final TextBox tb = new TextBox();
	private static final WarnTemplate TEMPLATE = GWT.create(WarnTemplate.class);
	private final HTML err = new HTML(TEMPLATE.message(""));
	
	public String fault = null;

	public EMailBox2() {
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
		err.getElement().getStyle().setTop(0, Unit.PX);
		err.getElement().getStyle().setLeft(144, Unit.PX);
		err.setVisible(false);

		tb.setStyleName("site-text");
		
		FlowPanel hp = new FlowPanel();
		hp.setStyleName("site-relative");
		hp.add(tb);
		hp.add(err);
		initWidget(hp);
	}

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";;

	protected void checkValue(String value) {
		err.setVisible(false);
		isEmpty = false;
		if (value == null || value.isEmpty()) {
			isEmpty = true;
			return;
		}

		RegExp patt = RegExp.compile(EMAIL_PATTERN);

		if (patt.exec(value) == null) {
			fault = trans.emailErr();
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(fault));
			return;
		}

		fault = null;
		err.setHTML("");
	}

	public String getValue() {
		return tb.getValue();
	}

	public void setMessage(String msg) {
		err.setVisible(true);
		err.setHTML(TEMPLATE.message(msg));
	}

}
