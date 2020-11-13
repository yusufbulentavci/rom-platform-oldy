package com.bilgidoku.rom.gwt.client.util.panels;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBoxBase;

public class PnlChangepass extends Composite {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private final TextBoxBase password = new PasswordTextBox();
	private final TextBoxBase repassword = new PasswordTextBox();
	private final SiteButton ok = new SiteButton(trans.setpassword(), "");
	
	private HasPassword caller;

	public PnlChangepass(HasPassword caller) {
		this.caller = caller;
		
		forOk();
		forEnter();

		Label error = new Label();
		error.setStyleName("site-error");
		
		FlexTable form = new FlexTable();
		form.setHeight("50px");
		form.setCellPadding(2);		

		form.setHTML(0, 0, trans.newPassword());
		form.setWidget(0, 1, password);
		form.setHTML(1, 0, trans.retypepassword());
		form.setWidget(1, 1, repassword);
		form.setWidget(2, 1, ok);

		form.setWidget(4, 1, error);
		initWidget(new SimplePanel(form));

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute() {
				password.setFocus(true);
			}
		});

	}

	private void forEnter() {
		repassword.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					changePassword(password.getValue(), repassword.getValue());
				}
			}
		});

	}

	protected void changePassword(String value, String value2) {
		
		if (password.getValue().isEmpty() || repassword.getValue().isEmpty()) {
			Window.alert(trans.emptyForm());
			password.setFocus(true);
			return;			
		}
		
		if (!password.getValue().equals(repassword.getValue())) {
			Window.alert(trans.differentPasswords());
			password.setFocus(true);
			return;
		}

		
		if (password.getValue().length() < 6) {
			Window.alert(trans.tooShortPass());
			password.setFocus(true);
			return;					
		}

		caller.changePassword(password.getValue());

	}

	private void forOk() {
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				changePassword(password.getValue(), repassword.getValue());
			}
		});

	}

	public void clear() {
		password.setValue("");
		repassword.setValue("");		
	}


}
