package com.bilgidoku.rom.site.kamu.activate.client;

import java.util.Iterator;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.TokensDao;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.kamu.activate.client.constants.activatetext;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Steps extends Composite {
	private final activatetext trans = GWT.create(activatetext.class);

	private final FlexTable holder = new FlexTable();

	private final SiteButton btn1 = new SiteButton(trans.domainName(), trans.domainName());
	private final SiteButton btn2 = new SiteButton(trans.lang(), trans.lang());
	private final SiteButton btn3 = new SiteButton(trans.contactInfo(), trans.contactInfo());
	private final SiteButton btn4 = new SiteButton(trans.userName(), trans.userName());

	DomainPanel panelDomain = new DomainPanel(this);
	PanelLang panelLang = new PanelLang(this);
	PanelAddress panelAddress = new PanelAddress(this);
	PanelPassword panelPassword = new PanelPassword(this);
	HorizontalPanel header = new HorizontalPanel();

	private HTML notReadyYet = new HTML(trans.notReadyYet());
	FlexTable statePanel = new FlexTable();

	private final SiteButton[] btns = { btn1, btn2, btn3, btn4 };
	public JSONObject actObj = new JSONObject();
	private String token;

	private boolean isLangOK = false;
	private boolean isPassOK = false;
	private boolean isDomanOK = false;
	private boolean isAddrOK = false;

	private VerticalPanel main = new VerticalPanel();

	public Steps() {

		token = Location.getParameter("token");
		if (token != null && !token.isEmpty()) {
			TokensDao.get(token, new JsonResponse() {
				@Override
				public void ready(Json value) {

					notReadyYet.setStyleName("site-launchmessage");

					for1();
					for2();
					for3();
					for4();

					ui();
					state1();

					loadValues(value);
				}

				@Override
				public void err(int statusCode, String statusText, Throwable exception) {
					Window.alert(trans.denied());
				}
			});
		}

		this.initWidget(main);

	}

	private void ui() {
		holder.setWidth("900px");
		holder.setHeight("400px");
		holder.setCellPadding(0);
		holder.setCellSpacing(0);
		// holder.setBorderWidth(1);

		statePanel.setWidget(0, 0, getEmpty());
		statePanel.setWidget(0, 1, btn1);

		statePanel.setWidget(1, 0, getEmpty());
		statePanel.setWidget(1, 1, btn2);

		statePanel.setWidget(2, 0, getEmpty());
		statePanel.setWidget(2, 1, btn3);

		statePanel.setWidget(3, 0, getEmpty());
		statePanel.setWidget(3, 1, btn4);

		statePanel.getFlexCellFormatter().setWidth(0, 0, "32px");
		statePanel.getFlexCellFormatter().setWidth(1, 0, "32px");
		statePanel.getFlexCellFormatter().setWidth(2, 0, "32px");
		statePanel.getFlexCellFormatter().setWidth(3, 0, "32px");

		holder.setWidget(0, 0, statePanel);
		holder.setWidget(0, 1, panelDomain);

		holder.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		holder.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		holder.getFlexCellFormatter().setWidth(0, 0, "180px");

		holder.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		holder.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);

		HTML title = new HTML("<h1>" + trans.activateAccount() + "</h1>");
		title.setStyleName("site-title");

		main.setStyleName("site-center");
		main.addStyleName("site-holder");

		main.add(title);
		main.add(holder);

	}

	private void for4() {
		btn4.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				state4();
			}
		});
	}

	protected void loadValues(Json jo) {

		actObj = jo.getValue().isObject();

		// user
		String user = getStringValue(actObj, "admin");
		String pass = getStringValue(actObj, "credential");

		if (user != null && pass != null && !user.isEmpty() && !pass.isEmpty()) {
			panelPassword.setValues(user, pass);
			state4OK(null, null);
		}

		String lang = getStringValue(actObj, "lang");
		if (lang != null && !lang.isEmpty()) {
			panelLang.setValues(lang);
			state2OK(null);
		}

		if (actObj.get("contact") != null && actObj.get("contact").isObject() != null
				&& actObj.get("contact").isObject().get("firstname") != null) {
			JSONObject contact = actObj.get("contact").isObject();
			panelAddress.loadData(contact);
			state3OK(null);
		}

		String domainName = getStringValue(actObj, "hostname");
		if (domainName != null) {
			String authInfo = getStringValue(actObj, "authinfo");
			Boolean manual = false;
			if (actObj.get("manual") != null && actObj.get("manual").isBoolean() != null)
				manual = actObj.get("manual").isBoolean().booleanValue();

			panelDomain.setValues(domainName, authInfo, manual);
			state1OK(null, null, false);
		}

	}

	public void check1() {
		statePanel.setWidget(0, 0, getCheck());
	}

	public void check2() {
		statePanel.setWidget(1, 0, getCheck());
	}

	public void check3() {
		statePanel.setWidget(2, 0, getCheck());
	}

	public void state1OK(String domainName, String authInfo, boolean b) {
		if (domainName != null) {
			// initial load
			actObj.put("hostname", new JSONString(domainName));
			actObj.put("authinfo", new JSONString(authInfo));
			actObj.put("manual", JSONBoolean.getInstance(b));
		}
		isDomanOK = true;
		statePanel.setWidget(0, 0, getCheck());

		state2();
	}

	public void state1NotOK() {
		isDomanOK = false;
		statePanel.setWidget(0, 0, getEmpty());
	}

	public void state3NotOK() {
		isAddrOK = false;
		statePanel.setWidget(2, 0, getEmpty());
	}

	public void state2OK(String lang) {
		if (lang != null)
			actObj.put("lang", new JSONString(lang));

		isLangOK = true;
		statePanel.setWidget(1, 0, getCheck());

		state3();
	}

	public void state3OK(JSONObject addressObj) {
		if (addressObj != null)
			actObj.put("contact", addressObj);

		isAddrOK = true;
		statePanel.setWidget(2, 0, getCheck());
		state4();

	}

	public void state4OK(String user, String pass) {
		if (user != null) {
			actObj.put("admin", new JSONString(user));
			actObj.put("credential", new JSONString(pass));
		}
		isPassOK = true;
		statePanel.setWidget(3, 0, getCheck());

	}

	public void state4NotOK() {
		isPassOK = false;
		statePanel.setWidget(3, 0, getEmpty());

	}

	protected Json formJson() throws NotReadyException {
		JSONObject jo = new JSONObject();

		panelPassword.setJson(jo);
		panelLang.setJson(jo);
		panelAddress.setJson(jo);
		panelDomain.setJson(jo);

		return new Json(jo);

	}

	public static String getStringValue(JSONObject client, String val) {
		if (client.get(val) != null && client.get(val).isString() != null
				&& client.get(val).isString().stringValue() != null
				&& !client.get(val).isString().stringValue().isEmpty())
			return client.get(val).isString().stringValue();
		else
			return null;

	}

	private void state1() {
		holder.setWidget(0, 1, panelDomain);
		selectButton(btn1);
		btn1.setSelected();
	}

	private void state2() {
		holder.setWidget(0, 1, panelLang);
		selectButton(btn2);
		btn2.setSelected();

	}

	private void state3() {
		holder.setWidget(0, 1, panelAddress);
		selectButton(btn3);
		btn3.setSelected();
	}

	private void state4() {
		holder.setWidget(0, 1, panelPassword);
		selectButton(btn4);
		btn4.setSelected();
	}

	protected void selectButton(SiteButton btn) {
		btn.setSelected();
		for (int i = 0; i < btns.length; i++) {
			SiteButton mtn = (SiteButton) btns[i];
			if (mtn.isSelected())
				mtn.removeSelected();
		}
	}

	private void for3() {
		btn3.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				state3();
			}
		});
	}

	private void for2() {
		btn2.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				state2();

			}
		});

	}

	private void for1() {
		btn1.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				state1();
			}
		});

	}

	public static String notNull(String string, String value) throws NotReadyException {
		if (value == null) {
			throw new NotReadyException(string);
		}
		return value;
	}

	public void launch() {

		if (!(isDomanOK && isAddrOK && isLangOK && isPassOK)) {
			Window.alert("check all fields");
			return;
		}

		panelPassword.disable();

		try {
			TokensDao.change(formJson(), token, new StringResponse() {

				@Override
				public void ready(String value) {
					TokensDao.ready(token, new BooleanResponse() {
						@Override
						public void ready(Boolean value) {
							Window.alert(trans.launchSuccess());

							redirectToLogin();

						}

						protected void redirectToLogin() {

							UrlBuilder builder = Location.createUrlBuilder().setPath("/_public/login.html");
							removeAllParameters(builder);
							String redirect = "/_local/one.html";

							if (Location.getQueryString() != null && !Location.getQueryString().isEmpty())
								redirect = redirect + Location.getQueryString();

							builder.setParameter("redirect", redirect);

							Window.Location.replace(builder.buildString());

						}

						public void removeAllParameters(UrlBuilder builder) {
							Set<String> keySet = Location.getParameterMap().keySet();
							for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
								String key = (String) iterator.next();
								if (key.equals("locale"))
									continue;
								builder.removeParameter(key);
							}
						}

						@Override
						public void err(int statusCode, String statusText, Throwable exception) {
							Window.alert("An error occured, please try again later. /n ");
							super.err(statusCode, statusText, exception);
						}

					});
				}

				@Override
				public void err(int statusCode, String statusText, Throwable exception) {
					Window.alert("An error occured, please try again later. /n ");
					super.err(statusCode, statusText, exception);
				}

			});
		} catch (NotReadyException e) {
			Window.alert("Enter value for field:" + e.field);
		}

	}

	private HTML getCheck() {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<img src='/_public/images/check.png' width=30px/>");

		HTML logo = new HTML(sb.toSafeHtml());
		return logo;
	}

	private HTML getEmpty() {
		HTML logo = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		return logo;
	}

}
