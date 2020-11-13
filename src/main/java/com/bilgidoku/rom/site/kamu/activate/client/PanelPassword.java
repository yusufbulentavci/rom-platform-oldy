package com.bilgidoku.rom.site.kamu.activate.client;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.kamu.activate.client.constants.activatetext;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelPassword extends Composite {
	private final activatetext trans = GWT.create(activatetext.class);
	final TextBox userName = new TextBox();
	final PasswordTextBox newPass = new PasswordTextBox();
	final PasswordTextBox newPass2 = new PasswordTextBox();
	final Steps st;
	private SiteButton launch = new SiteButton(trans.launch(), trans.launchDesc(), "next");
	HTML msgUser = new HTML(trans.validUserName());
	HTML msgPass = new HTML(trans.validPass());
	HTML msgPass2 = new HTML(trans.valisPass2());

	public PanelPassword(Steps steps) {
		this.st = steps;

		launch.setEnabled(false);

		forKeyPass1();
		forKeyPass2();
		forKeyUser();
		forLaunch();
		this.initWidget(ui());

	}

	private void forLaunch() {
		launch.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!checkFields()) {
					return;
				}
				launch.setEnabled(false);
				st.state4OK(userName.getValue(), newPass.getValue());
				st.launch();

			}
		});

	}

	private void forKeyUser() {
		userName.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {

				String val = userName.getValue();
				if (!val.isEmpty() && val.length() > 5 && val.length() < 41)
					msgUser.setStyleName("site-boxgreen");
				else
					msgUser.setStyleName("site-boxred");

				enableLaunch();

			}
		});

	}

	private void forKeyPass2() {

		newPass2.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {

				String val = newPass2.getValue();
				if (!val.isEmpty() && val.length() > 5 && val.length() < 41 && val.equals(newPass.getValue()))
					msgPass2.setStyleName("site-boxgreen");
				else
					msgPass2.setStyleName("site-boxred");

				enableLaunch();

			}
		});

	}

	private void forKeyPass1() {
		newPass.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				String val = newPass.getValue();
				if (!val.isEmpty() && val.length() > 5 && val.length() < 41)
					msgPass.setStyleName("site-boxgreen");
				else
					msgPass.setStyleName("site-boxred");

				enableLaunch();

			}
		});

	}

	protected void enableLaunch() {
		if (!userName.getValue().isEmpty() && !newPass.getValue().isEmpty() && !newPass2.getValue().isEmpty()
				&& newPass.getValue().equals(newPass2.getValue()) && userName.getValue().matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*$")) {
			launch.setEnabled(true);
			st.state4OK(userName.getValue(), newPass.getValue());
		} else {
			launch.setEnabled(false);
			st.state4NotOK();
		}

	}

	protected boolean checkFields() {
		String usr = userName.getValue();
		if (usr.isEmpty() || newPass.getValue().isEmpty() || newPass2.getValue().isEmpty()) {
			Window.alert(trans.fillFormProperly());
			userName.setFocus(true);
			return false;
		}
		
		if (!ClientUtil.isValidUserName(usr)) {
			Window.alert(trans.regexpUserName());
			userName.setFocus(true);
			return false;
		}

		if (usr.length() < 6) {
			Window.alert(trans.tooShortPass());
			userName.setFocus(true);
			return false;
		}

		
		if (usr.length() > 40) {
			Window.alert(trans.tooLongUserName());
			newPass.setFocus(true);
			return false;
		}
		
		if (userName.getValue().equals("abuse") || userName.getValue().equals("postmaster")) {
			Window.alert("This username is not allowed, please change it");
			return false;
		}

		
		if (!newPass.getValue().equals(newPass2.getValue())) {
			Window.alert(trans.differentPasswords());
			newPass.setFocus(true);
			return false;
		}


		if (newPass.getValue().length() < 6) {
			Window.alert(trans.tooShortPass());
			newPass.setFocus(true);
			return false;
		}

		if (newPass.getValue().length() > 40) {
			Window.alert(trans.tooLongUserName());
			newPass.setFocus(true);
			return false;
		}

		return true;

	}

	private Widget ui() {

		msgUser.setStyleName("site-boxred");
		msgPass.setStyleName("site-boxred");
		msgPass2.setStyleName("site-boxred");

		FlexTable form = new FlexTable();
		form.setSize("100%", "50px");
		form.setHTML(0, 0, "<h3>" + trans.userName() + "</h3>");
		form.setWidget(0, 1, userName);
		form.setWidget(0, 2, msgUser);

		form.setHTML(1, 0, "<h3>" + trans.newPassword() + "</h3>");
		form.setWidget(1, 1, newPass);
		form.setWidget(1, 2, msgPass);

		form.setHTML(2, 0, "<h3>" + trans.confirmPassword() + "</h3>");
		form.setWidget(2, 1, newPass2);
		form.setWidget(2, 2, msgPass2);

		form.setWidget(3, 0, launch);
		form.getFlexCellFormatter().setColSpan(3, 0, 3);
		form.getFlexCellFormatter().setWidth(0, 0, "150px");

		HTML title = new HTML("<h2>" + trans.userNamePasswrd() + "</h2>");
		title.setStyleName("site-title");

		VerticalPanel vp = new VerticalPanel();
		vp.add(title);
		vp.add(form);

		vp.setStyleName("site-panel");
		return vp;
	}

	public void setValues(String user, String pass) {
		userName.setValue(user);
		newPass.setValue(pass);
		newPass2.setValue(pass);
	}

	public void setJson(JSONObject jo) throws NotReadyException {
		jo.put("admin", new JSONString(Steps.notNull("user", userName.getValue())));
		jo.put("credential", new JSONString(Steps.notNull("password", newPass.getValue())));
	}

	public void disable() {
		launch.setEnabled(false);
		launch.addStyleName("site-buttondisabled");

	}

}
