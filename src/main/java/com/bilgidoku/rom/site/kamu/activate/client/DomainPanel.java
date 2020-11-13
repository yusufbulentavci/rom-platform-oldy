package com.bilgidoku.rom.site.kamu.activate.client;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.kamu.activate.client.constants.activatetext;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DomainPanel extends Composite {

	private final activatetext trans = GWT.create(activatetext.class);

	private final RadioButton rbCreate = new RadioButton("new");
	private final RadioButton rbTransfer = new RadioButton("new");
	private final RadioButton rbManuel = new RadioButton("new");

	private final FlexTable ft = new FlexTable();
	private final Button btnQuery = new Button(trans.query());
	private final DomainBox txtDomain = new DomainBox(this);
	private final SiteButton next = new SiteButton(trans.next(), trans.nextDesc(), "next");
	private final HTML message = new HTML();

	private final TextBox txtAuthInfo = new TextBox();
	private final Steps steps;

	public DomainPanel(Steps steps) {

		this.steps = steps;
		initRadios();
		next.setEnabled(false);

		forNext(steps);

		forKeyPressDomain();
		forChangeTxtAuth();
		forDomainFocus();

		forDomainQuery();

		forTransferCheck();
		forCreateCheck();
		forManuelCheck();

		this.initWidget(getUi());

	}

	private void forKeyPressDomain() {
		txtDomain.domainBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				checkNext();
			}
		});

	}

	protected void checkNext() {
		next.setEnabled(false);
		String domain = txtDomain.domainBox.getValue();
		String auth = txtAuthInfo.getValue();
		steps.state1NotOK();
		
		if (rbManuel.getValue() || rbCreate.getValue()) {
			if (domain.length()>1) {
//				steps.check1();
				next.setEnabled(true);
				return;				
			}
			
		}

		if (rbTransfer.getValue() && domain.length() > 1 && auth.length() > 1) {
//			steps.check1();
			next.setEnabled(true);
			return;
		}

	}

	private void forDomainFocus() {
		txtDomain.domainBox.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				txtDomain.removeOkStyles();
				txtDomain.domainBox.setValue("");
				next.setEnabled(false);
				message.setHTML("");

			}
		});

	}

	private void forChangeTxtAuth() {
		txtAuthInfo.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				checkNext();				
			}
		});

	}

	private void domainOK() {
		txtDomain.showOK();
		message.setHTML(trans.domainOK());
		message.removeStyleName("site-boxred");
		message.setStyleName("site-boxgreen");
		next.setEnabled(true);
	}

	private void domainNotOK() {
		txtDomain.showNotOK();
		message.setHTML(trans.domainNotOK());
		message.removeStyleName("site-boxgreen");
		message.setStyleName("site-boxred");
		next.setEnabled(false);
	}

	private void forDomainQuery() {
		btnQuery.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				domainOK();
				// TODO ask??? why
				// OturumIciCagriDao.checkdomain(txtDomain.getDomainName(), new
				// BooleanResponse() {
				// @Override
				// public void ready(Boolean value) {
				// if (value)
				// domainOK();
				// else
				// domainNotOK();
				// }
				// });
			}
		});

	}

	private void forManuelCheck() {
		rbManuel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rbTransfer.removeStyleName("site-radioselected");
				rbManuel.addStyleName("site-radioselected");
				rbCreate.removeStyleName("site-radioselected");
				message.setHTML("");
				if (rbManuel.getValue()) {
					showAuth(false);
					btnQuery.setVisible(false);
					txtDomain.setDomainName(".com");
					next.setEnabled(false);
					txtAuthInfo.setValue(null);
				}

			}
		});

	}

	private void initRadios() {
		rbCreate.setStyleName("site-radio");
		rbTransfer.setStyleName("site-radio");
		rbManuel.setStyleName("site-radio");

		rbCreate.setHTML(new SafeHtml() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<h3>" + trans.create() + "</h3>";
			}
		});
		rbTransfer.setHTML(new SafeHtml() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<h3>" + trans.transfer() + "</h3>";
			}
		});
		rbManuel.setHTML(new SafeHtml() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<h3>" + trans.manuel() + "</h3>";
			}
		});

		rbManuel.setValue(true);
		rbManuel.addStyleName("site-radioselected");
		showAuth(false);
		btnQuery.setVisible(true);

	}

	private void showAuth(boolean state) {
		ft.getFlexCellFormatter().setVisible(2, 0, state);
		ft.getFlexCellFormatter().setVisible(2, 1, state);

	}

	private void forCreateCheck() {
		rbCreate.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rbTransfer.removeStyleName("site-radioselected");
				rbManuel.removeStyleName("site-radioselected");
				rbCreate.addStyleName("site-radioselected");
				message.setHTML("");
				if (rbCreate.getValue()) {
					showAuth(false);
					btnQuery.setVisible(true);
					txtDomain.setDomainName(".com");
					next.setEnabled(false);
					txtAuthInfo.setValue(null);
				}
			}
		});
	}

	private void forTransferCheck() {
		rbTransfer.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rbTransfer.addStyleName("site-radioselected");
				rbManuel.removeStyleName("site-radioselected");
				rbCreate.removeStyleName("site-radioselected");
				message.setHTML("");
				if (rbTransfer.getValue()) {
					txtDomain.setDomainName(".com");
					txtAuthInfo.setValue(null);
					showAuth(true);
					btnQuery.setVisible(false);
					next.setEnabled(false);
				}

			}
		});
	}

	public Widget getUi() {

		txtAuthInfo.setWidth("150px");

		FlowPanel fp = new FlowPanel();
		fp.add(rbManuel);
		fp.add(rbCreate);
		fp.add(rbTransfer);
		fp.addStyleName("site-padding");

		ft.setSize("600px", "60px");

		ft.setWidget(0, 0, fp);
		ft.getFlexCellFormatter().setColSpan(0, 0, 3);

		ft.setHTML(1, 0, "<h3>" + trans.domain() + "</h3>");

		ft.setWidget(1, 1, txtDomain);
		ft.setWidget(1, 2, btnQuery);

		ft.setHTML(2, 0, "<h3>" + trans.authInfo() + "</h3>");
		ft.setWidget(2, 1, txtAuthInfo);

		ft.setWidget(3, 1, message);

		ft.getFlexCellFormatter().setWidth(1, 1, "220px");
		ft.getFlexCellFormatter().setWidth(1, 0, "100px");
		ft.getFlexCellFormatter().setWidth(2, 0, "100px");

		ft.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		ft.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_BOTTOM);
		ft.getFlexCellFormatter().setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_BOTTOM);

		ft.getFlexCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		ft.getFlexCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_BOTTOM);

		HTML title = new HTML("<h2>" + trans.domainName() + "</h2>");
		title.setStyleName("site-title");

		VerticalPanel vp = new VerticalPanel();
		vp.add(title);
		vp.add(ft);
		vp.add(next);
		vp.setStyleName("site-panel");

		return vp;
	}

	private void forNext(final Steps steps) {
		next.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String domainName = txtDomain.getDomainName();
				String authInfo = txtAuthInfo.getValue();
				if (rbManuel.getValue())
					steps.state1OK(domainName, authInfo, true);
				else
					steps.state1OK(domainName, authInfo, false);

			}
		});

	}

	public Boolean getIsTransfer() {
		if (rbTransfer.getValue())
			return new Boolean(true);
		return new Boolean(false);
	}

	public String getAuthInfo() {
		return txtAuthInfo.getValue();
	}

	public void setValues(String domainName, String authInfo, Boolean manual) {
		txtDomain.setDomainName(domainName);
		txtAuthInfo.setValue(authInfo);
		if (authInfo != null && !authInfo.isEmpty()) {
			rbTransfer.setEnabled(true);
		} else if (manual) {
			rbManuel.setEnabled(true);
		} else {
			rbCreate.setEnabled(true);
		}

	}

	public void setJson(JSONObject jo) throws NotReadyException {

		jo.put("hostname", new JSONString(Steps.notNull("domain name", txtDomain.getDomainName())));

		jo.put("authinfo", new JSONString(txtAuthInfo.getValue()));
		jo.put("manual", JSONBoolean.getInstance(rbManuel.getValue()));
	}

}
