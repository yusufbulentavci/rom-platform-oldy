package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.Date;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.IntegerResponse;
import com.bilgidoku.rom.gwt.araci.client.service.HosgeldinDao;
import com.bilgidoku.rom.gwt.shared.Welcome;
import com.bilgidoku.rom.site.kamu.pay.client.widgets.EMailBox2;
import com.bilgidoku.rom.site.kamu.pay.client.widgets.PasswordBox2;
import com.bilgidoku.rom.site.kamu.pay.client.widgets.UsernameBox2;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * Welcome type is site description for user It is stored in tepeweb.welcome
 * table Fields: public String email; public int hostid; public String site;
 * public String ip; public String country; public Long lastactivity;
 * 
 * 
 * Take email address from user Check user's sites registered for email address
 * HosgeldinDao.sitesof(email, methodResp); List user sites if found So user can
 * go its host login screen
 * 
 * Create a "host creation token" via HosgeldinDao.create(email, username,
 * password methodResp); returns hostid Redirect to
 * host<hostid>.tlos.net/_local/one.html?username=<username>
 * 
 * 
 * @author avci
 *
 */

public class WelcomeUI extends Composite {
	// private final String source;
	private final EMailBox2 eMail = new EMailBox2();
	private final UsernameBox2 userName = new UsernameBox2(pay.trans.userNameLength());
	private final PasswordBox2 password = new PasswordBox2(true, pay.trans.passwordLength());
	private final PasswordBox2 password1 = new PasswordBox2(true, null);

	private final VerticalPanel pnlHolder = new VerticalPanel();

	public WelcomeUI() {

		pnlHolder.addStyleName("site-holder");

		pnlHolder.add(uiStep1());

		this.initWidget(pnlHolder);

	}

	private Widget uiStep1() {
		Button btnStart = new Button(pay.trans.start());
		btnStart.setStyleName("site-btn");
		btnStart.addStyleName("site-btnnext");
		forStep1(btnStart);

		HTML lbl = new HTML("<h5>" + pay.trans.email() + ":</h5>");

		FlexTable ft = new FlexTable();
		ft.setWidget(0, 0, lbl);
		ft.setWidget(1, 0, eMail);
		ft.setWidget(2, 0, btnStart);

		return ft;
	}

	private void forStep1(Button btnOk) {
		btnOk.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (eMail.isEmpty) {
					eMail.setMessage(pay.trans.eMailEmpty());
					return;
				}

				if (eMail.fault != null) {
//					eMail.setMessage(eMail.fault);
					return;
				}
				HosgeldinDao.sitesof(eMail.getValue(), new ArrayResponse<Welcome>() {
					@Override
					public void ready(Welcome[] value) {
						if (value == null || value.length == 0)
							step2Create();
						else
							step2ListSites(value);
					}

					
				});

			}
		});
	}

	private void step2ListSites(Welcome[] value) {
		pnlHolder.remove(0);
		pnlHolder.add(uiStep2ListSites(value));
	}

	private void step2Create() {
		pnlHolder.remove(0);
		pnlHolder.add(uiStep2Create());

	}

	private Widget uiStep2Create() {

		Button btnSend = new Button("Create Website");
		btnSend.setStyleName("site-btn");
		btnSend.addStyleName("site-btnnext");
		// btnSend.setWidth("330px");

		forCreate(btnSend);

		
		final FlexTable pnlNew = new FlexTable();

		pnlNew.setWidget(0, 0, new HTML("<h5>" + pay.trans.userName() + ":</h5>"));
		pnlNew.setWidget(1, 0, userName);

		pnlNew.setWidget(2, 0, new HTML("<h5>" + pay.trans.password() + ":</h5>"));
		pnlNew.setWidget(3, 0, password);

		pnlNew.setWidget(4, 0, new HTML("<h5>" + pay.trans.passwordAgain() + ":</h5>"));
		pnlNew.setWidget(5, 0, password1);

		pnlNew.setWidget(6, 0, btnSend);

		return pnlNew;

	}

	private void forCreate(final Button btnSend) {
		btnSend.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (userName.isEmpty) {
					userName.setMessage(pay.trans.empty(pay.trans.userName()));
					userName.setFocus(true);
					return;
				}

				if (password.isEmpty) {
					password.setMessage(pay.trans.empty(pay.trans.password()));
					return;
				}

				if (password1.isEmpty) {
					password1.setMessage(pay.trans.empty(pay.trans.password()));
					return;
				}

				if (!password.getValue().equals(password1.getValue())) {
					password.setMessage(pay.trans.differenPasswords());
					return;
				}

				if (userName.fault != null) {
					userName.setMessage(userName.fault);
					return;
				}

				if (password.fault != null) {
					userName.setMessage(password.fault);
					return;
				}
				btnSend.setEnabled(false);
				HosgeldinDao.create(eMail.getValue(), userName.getValue(), password.getValue(), new IntegerResponse() {
					@Override
					public void ready(Integer value) {
						if (value == null) {
							Window.alert(pay.trans.tryAgainLater());
							return;
						}
						Window.Location.replace("http://home.host" + value.intValue() + ".enjoy-tlos.net");
					}
				});
			}
		});
	}

	private Widget uiStep2ListSites(Welcome[] value) {

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(20);
		for (int i = 0; i < value.length; i++) {
			Welcome wel = value[i];
			Date d = new Date(wel.lastactivity);
			String cd = DateTimeFormat.getMediumDateTimeFormat().format(d);

			String text = wel.site != null ? wel.site : pay.trans.siteCreatedAt(cd);

			Anchor a = new Anchor(text);
			a.setTitle("enjoy-tlos");
			forSiteClick(wel, a);
			vp.add(a);
		}

		return vp;
	}

	private void forSiteClick(final Welcome wel, Anchor a) {
		a.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				Window.Location.replace("http://home.host" + wel.hostid + ".enjoy-tlos.net");

			}
		});
	}

}
