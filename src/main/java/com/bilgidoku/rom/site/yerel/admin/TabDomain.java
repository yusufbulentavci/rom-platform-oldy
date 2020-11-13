package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabDomain extends Composite {
	private final SiteButton buy = new SiteButton("/_local/images/common/sepet.png", Ctrl.trans.buy(Ctrl.trans.domain()),
			Ctrl.trans.buy(Ctrl.trans.domain()), "");
	private final SiteButton query = new SiteButton("/_local/images/common/search_.png", Ctrl.trans.query(), Ctrl.trans.query(), "");
	private final SiteButton ok = new SiteButton("/_local/images/common/check.png", Ctrl.trans.finish(), Ctrl.trans.finish(), "");
	private final TextBox text = new TextBox();
	private final TabDomainRegForm regForm = new TabDomainRegForm();
	private final HTML msg = new HTML();

	public TabDomain() {
		forBuy();
		forFinish();
		forQuery();
		forTextEnter();
		initWidget(start());
	}


	private void forTextEnter() {
		text.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					queryDomain();
				}
			}
		});
	}

	private void forFinish() {
		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				final String wanted = text.getValue();
				if (wanted == null || wanted.isEmpty())
					return;
				
				Json reg = regForm.getRegistrant();
				if (reg == null) {
					Window.alert(Ctrl.trans.someValuesEmpty());
					return;
				}
				
//				InternetDao.buyDomain(wanted, regForm.getRegistrant(), new BooleanResponse() {
//					public void ready(Boolean value) {
//						state1();
//					};
//					
//					@Override
//					public void err(int statusCode, String statusText, Throwable exception) {
//						
//						Window.alert(Ctrl.trans.checkYourCredit());
//						
//						
//					}
//				});

			}
		});

	}

	private void forBuy() {
		buy.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				state3();

			}
		});

	}

	private void forQuery() {
		query.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				queryDomain();
			}
		});

	}

	protected void queryDomain() {
		final String wanted = text.getValue();
		if (wanted == null || wanted.isEmpty())
			return;
		
		if (!wanted.matches("^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$")) {
			msg.setStyleName("site-warning");
			msg.setHTML(Ctrl.trans.regexpDomain());			
			msg.setVisible(true);
			return;			
		}
		
//		OturumIciCagriDao.checkdomain(wanted, new BooleanResponse() {
//
//			@Override
//			public void ready(Boolean value) {
//				if (!value) {
//					msg.setStyleName("site-warning");
//					msg.setHTML(Ctrl.trans.domainNotAvailable());
//					msg.setVisible(true);
//				} else {
//					state2();
//
//				}
//			}
//
//			@Override
//			public void err(int statusCode, String statusText, Throwable exception) {
//				state2();
//			}
//		});

	}

	private Widget start() {
		FlowPanel fp = new FlowPanel();
		fp.add(text);
		fp.add(query);
		
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(10);
		vp.add(fp);
		vp.add(msg);
		vp.add(buy);
		vp.add(regForm);
		vp.add(ok);
		state1();
		return vp;
	}

	private void state1() {
		msg.setVisible(false);
		buy.setVisible(false);
		regForm.setVisible(false);
		ok.setVisible(false);
	}

	private void state2() {
		msg.setStyleName("site-message");
		msg.setHTML(Ctrl.trans.domainAvailable());
		msg.setVisible(true);
		buy.setVisible(true);
		regForm.setVisible(false);
		ok.setVisible(false);
	}

	private void state3() {
		msg.setHTML(Ctrl.trans.fillFormBelow());
		buy.setVisible(false);
		regForm.setVisible(true);
		ok.setVisible(true);
	}

}