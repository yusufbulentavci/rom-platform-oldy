package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.service.YetkilendirmeDao;
import com.bilgidoku.rom.gwt.client.util.com.Authenticator;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.shared.util.AsyncMethod;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

interface IdReady {
	public void idReady(String id);
}

public class DlgLogin extends ActionBarDlg {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);

	public DlgLogin() {
		super(trans.loginTitle(), null, null);
		setAutoHideEnabled(false);
		run();
		show();
		center();
	}

	@Override
	public Widget ui() {
		return new LoginGUI();
	}

	@Override
	public void cancel() {

	}

	@Override
	public void ok() {

	}

	class LoginGUI extends Composite {

		// private final OneTrans trans = GWT.create(OneTrans.class);

		private final SimplePanel holder = new SimplePanel();

		private final ForgotPassPanel passForgotPanel = new ForgotPassPanel();
		private final LoginPanel loginPanel = new LoginPanel();

		public LoginGUI() {

			loginPanel.setSize("390px", "180px");
			passForgotPanel.setSize("390px", "180px");

			holder.add(loginPanel);
			this.initWidget(holder);

			Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
				public void execute() {
					loginPanel.focusUserName();
				}
			});

		}

		public void error(String text) {

		}

		private class ForgotPassPanel extends Composite {
			final Anchor back = new Anchor(trans.goBack());
			final Button btnSendPassword = new Button(trans.send());
			final TextBox eMail = new TextBox();
			final Label error = new Label();

			public ForgotPassPanel() {
				back.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						holder.remove(passForgotPanel);
						holder.add(loginPanel);
						loginPanel.setError("");
					}
				});
				btnSendPassword.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {
						if (eMail.getValue() == null || eMail.getValue().isEmpty()) {
							setError(trans.eMailEmpty());
							return;
						}

						if (!ClientUtil.isValidEMail(eMail.getValue())) {
							setError(trans.notValidEMail());
							return;
						}

						YetkilendirmeDao.forgetpass(eMail.getValue(), new BooleanResponse() {

							public void ready(Boolean value) {
								setError(trans.emailSent());
								disableForm();
							}

							@Override
							public void err(int statusCode, String statusText, Throwable exception) {
								setError(statusText);
							}
						});
					}

				});

				initWidget(ui());
			}

			public void setError(String m) {
				error.setText(m);
			}

			private void disableForm() {
				btnSendPassword.setEnabled(false);
			}

			public void enableForm() {
				eMail.setValue("");
				btnSendPassword.setEnabled(true);
			}

			private FlexTable ui() {
				back.addStyleName("site-message");
				btnSendPassword.setSize("100px", "32px");
				error.setStyleName("site-error");
				eMail.setSize("98%", "24px");
				eMail.setStyleName("site-box");

				btnSendPassword.setStyleName("site-btn btn-fancy");

				Label lbl = new Label(trans.eMail());
				lbl.setStyleName("site-label");

				Label lbl2 = new Label(trans.forgotPasswordMsg());
				lbl2.setStyleName("site-message");

				final FlexTable pnlPass = new FlexTable();
				pnlPass.setStyleName("site-loginform");
				pnlPass.setWidget(0, 0, lbl2);
				pnlPass.setWidget(1, 0, lbl);
				pnlPass.setWidget(1, 1, eMail);
				pnlPass.setWidget(2, 1, btnSendPassword);
				pnlPass.setWidget(3, 0, back);
				pnlPass.setWidget(4, 0, error);
				pnlPass.getFlexCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_BOTTOM);
				pnlPass.getFlexCellFormatter().setHeight(2, 1, "45px");
				pnlPass.getFlexCellFormatter().setHeight(0, 0, "45px");
				pnlPass.getFlexCellFormatter().setColSpan(4, 0, 2);
				pnlPass.getFlexCellFormatter().setColSpan(0, 0, 2);
				return pnlPass;
			}
		}

		private class LoginPanel extends Composite {

			final TextBoxBase tbUserName = new TextBox();
			final TextBoxBase tbPassword = new PasswordTextBox();
			final Button btnLogin = new Button(trans.login());

			final Button btnFacebook = new Button("<div style='display: inline-block; margin: 13px; '>" + trans.loginFacebook() + "</div>");

			final Label lblLoginErr = new Label();
			final Anchor forgotPassword = new Anchor(trans.forgotPassword());

			public LoginPanel() {
				tbUserName.setHeight("24px");
				tbPassword.setHeight("24px");
				
				tbUserName.addFocusHandler(new FocusHandler() {
					@Override
					public void onFocus(FocusEvent event) {
						setError("");
					}
				});

				tbUserName.addKeyUpHandler(new KeyUpHandler() {
					@Override
					public void onKeyUp(KeyUpEvent event) {
						tbUserName.setValue(tbUserName.getValue().toLowerCase());
					}
				});

				tbPassword.addKeyPressHandler(new KeyPressHandler() {
					public void onKeyPress(KeyPressEvent event) {
						setError("");
						if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
							login();
						}
					}
				});

				btnLogin.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						login();
					}
				});

				btnFacebook.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Authenticator.fbLogin();
					}
				});

				forgotPassword.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						holder.remove(loginPanel);
						holder.add(passForgotPanel);
						passForgotPanel.enableForm();
						passForgotPanel.setError("");
					}
				});

				initWidget(ui());

			}

			protected void login() {
				if (!checkForm())
					return;
				Authenticator.userPassLogin(tbUserName.getValue(), tbPassword.getValue(),
						new AsyncMethod<String, String>() {
							@Override
							public void run(String param) {
								
							}

							@Override
							public void error(String param) {
								// TODO Auto-generated method stub

							}
						});

			}

			protected boolean checkForm() {
				String userName = tbUserName.getValue();
				String pass = tbPassword.getValue();

				if (userName == null || userName.isEmpty()) {
					tbUserName.setFocus(true);
					setError(trans.empty(trans.userName()));
					return false;
				}

				if (pass == null || pass.isEmpty()) {
					tbPassword.setFocus(true);
					setError(trans.empty(trans.password()));
					return false;
				}

				return true;
			}

			public void focusUserName() {
				tbUserName.setFocus(true);
			}

			private FlexTable ui() {
				tbPassword.getElement().setId("pwd");
				tbUserName.getElement().setId("usr");
				btnLogin.getElement().setId("login");

				lblLoginErr.setStyleName("site-error");

				btnLogin.setSize("110px", "32px");
				btnFacebook.setSize("100%", "45px");
				btnFacebook.setStyleName("site-face");

				tbUserName.setStyleName("site-box");
				tbPassword.setStyleName("site-box");

				tbUserName.setWidth("98%");
				tbPassword.setWidth("98%");
				btnLogin.setStyleName("site-btn btn-fancy");

				Label lbl = new Label(trans.userName());
				Label lbl2 = new Label(trans.password());
				lbl.setStyleName("site-label");
				lbl2.setStyleName("site-label");

				forgotPassword.addStyleName("site-message");

				FlowPanel fp = new FlowPanel();
				fp.add(forgotPassword);
				fp.add(btnLogin);
				
				HTML h = new HTML("<div class='fancy' style='line-height:0.5;text-align:center;'><span>"
						+ trans.or() + "</span></div>");
				h.setWidth("100%");

				FlexTable pnlLogin = new FlexTable();
				pnlLogin.setStyleName("site-loginform");
				pnlLogin.getElement().setId("loginPanel");

				pnlLogin.setWidget(0, 0, btnFacebook);

				pnlLogin.setWidget(1, 0, h);

				pnlLogin.setWidget(2, 0, lbl);
				pnlLogin.setWidget(3, 0, tbUserName);

				pnlLogin.setWidget(4, 0, lbl2);
				pnlLogin.setWidget(5, 0, tbPassword);

				pnlLogin.setWidget(6, 0, forgotPassword);
				pnlLogin.setWidget(6, 1, btnLogin);

				pnlLogin.setWidget(7, 0, lblLoginErr);

				pnlLogin.getFlexCellFormatter().setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				pnlLogin.getFlexCellFormatter().setVerticalAlignment(6, 1, HasVerticalAlignment.ALIGN_BOTTOM);
				pnlLogin.getFlexCellFormatter().setHeight(6, 1, "45px");
				pnlLogin.getFlexCellFormatter().setVerticalAlignment(6, 0, HasVerticalAlignment.ALIGN_BOTTOM);
				pnlLogin.getFlexCellFormatter().setColSpan(0, 0, 2);
				pnlLogin.getFlexCellFormatter().setColSpan(1, 0, 2);
				pnlLogin.getFlexCellFormatter().setColSpan(2, 0, 2);
				pnlLogin.getFlexCellFormatter().setColSpan(3, 0, 2);
				pnlLogin.getFlexCellFormatter().setColSpan(4, 0, 2);
				pnlLogin.getFlexCellFormatter().setColSpan(5, 0, 2);
				pnlLogin.getFlexCellFormatter().setColSpan(7, 0, 2);

				return pnlLogin;

			}

			public void setError(String errorText) {
				lblLoginErr.setText(errorText);

			}

		}

		// public void setError(String errorText) {
		// if (holder.getWidget() instanceof LoginPanel) {
		// LoginPanel pnl = (LoginPanel) holder.getWidget();
		// pnl.setError(errorText);
		// } else {
		// Window.alert(errorText);
		// }
		//
		// }

	}

}
