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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;

public class UsernameBox2 extends Composite {

	/**
	 * This {@link Constants} interface is used to make the toolbar's strings
	 * internationalizable.
	 */
	public interface Trans extends Constants {
		String regexpUserName();

		String userName();

		String forbiddenUserNames();
		String tooShort();

		String tooLong();

	}

	private Trans trans = (Trans) GWT.create(Trans.class);

	public boolean isEmpty = true;
	public String fault = null;
	final TextBox tb = new TextBox();
	
	private static final WarnTemplate TEMPLATE = GWT.create(WarnTemplate.class);
	final HTML err = new HTML(TEMPLATE.message(""));

	public UsernameBox2(String msg) {
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
		
		if (msg != null) {
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(msg));
		}

		

		tb.setStyleName("site-text");
		FlowPanel hp = new FlowPanel();
		hp.setStyleName("site-relative");
		hp.add(tb);
		hp.add(err);
		initWidget(hp);
	}

	protected void checkValue(String value) {
		isEmpty = false;
		err.setVisible(false);
		if (value == null || value.isEmpty()) {
			isEmpty = true;
			return;
		}

		if (value.length() < 4) {
			fault = trans.tooShort();
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(fault));

			return;

		}


		if (value.length() > 40) {
			fault = trans.tooLong();
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(fault));

			return;

		}

		if (!value.matches("^[a-z]+(\\.[_a-z0-9-]+)*$")) {
			fault = trans.regexpUserName();
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(fault));

			return;
		}


		if (value.equals("abuse") || value.equals("postmaster") || value.equals("rombot")) {
			fault = trans.forbiddenUserNames();
			err.setVisible(true);
			err.setHTML(TEMPLATE.message(fault));

			return;

		}
		fault = null;
		err.setHTML("");
		err.setVisible(false);
		return;

	}

	public void setFocus(boolean b) {
		tb.setFocus(true);

	}

	public String getValue() {
		return tb.getValue();
	}

	public void setMessage(String empty) {
		err.setVisible(true);
		err.setHTML(TEMPLATE.message(fault));		
	}
}
