package com.bilgidoku.rom.site.kamu.register.client;

import com.bilgidoku.rom.gwt.client.util.common.TopOld;
import com.bilgidoku.rom.site.kamu.register.client.constants.registertrans;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class RegisterUI extends Composite {

	private final registertrans trans = GWT.create(registertrans.class);
	private String token;
	private final FlexTable pnlCenter = new FlexTable();

	public RegisterUI() {
		
		pnlCenter.setSize("100%", "100%");		
		pnlCenter.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		pnlCenter.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		pnlCenter.setWidget(0, 0, signIn());
		
		DockLayoutPanel holder = new DockLayoutPanel(Unit.PX);
		holder.addNorth(new TopOld("one"), 34);
		holder.add(pnlCenter);
		this.initWidget(holder);

		this.token = Location.getParameter("token");
		if (token == null || token.isEmpty()) {
			Window.alert(trans.noToken());
			return;
		}		
		

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute() {
//				password.setFocus(true);
			}																							
		});

	}

	private void forEnter(TextBoxBase repassword) {
		repassword.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
//					changePassword(password.getValue(), repassword.getValue());
				}
			}
		});

	}

	private void forOk(Button ok) {
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				changePassword(password.getValue(), repassword.getValue());
			}
		});

	}

	private Widget signIn() {

		TextBox userName = new TextBox();
		TextBoxBase password = new PasswordTextBox();
		Button ok = new Button(trans.signIn());		
		RadioButton rbNew = new RadioButton("new", "New User");
		RadioButton rbExt = new RadioButton("new", "Existing User");
		rbNew.setValue(true);
		
		Label header = new Label(trans.signIn());
		header.setStyleName("site-header");
		
		Label error = new Label();
		
		forCheckUserName(userName);
		forOk(ok);
			
		ok.setHeight("32px");		
		error.setStyleName("site-error");

		FlexTable form = new FlexTable();		
		form.setCellPadding(4);
		form.setStyleName("site-register");
	
		form.setWidget(0, 0, header);
		
		form.setHTML(1, 0, trans.eMail());
		form.setWidget(1, 1, userName);

		form.setWidget(2, 1, rbNew);
		form.setWidget(3, 1, rbExt);		
		
		form.setHTML(4, 0, trans.password());
		form.setWidget(4, 1, password);

		form.setWidget(5, 1, ok);

		form.setWidget(6, 1, error);
		return form;

	}

	private void forCheckUserName(TextBox userName) {
		userName.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	
	

}
