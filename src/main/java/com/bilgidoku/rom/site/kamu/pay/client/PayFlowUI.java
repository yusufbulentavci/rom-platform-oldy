package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.client.util.panels.FlowInterface;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class PayFlowUI extends Composite implements FlowInterface {
	private final PnlNext pnlNext = new PnlNext(this);
	private final PnlConfirm pnlConfirm = new PnlConfirm();

	private final HorizontalPanel holder = new HorizontalPanel();
	private final PnlShipment pnlShip = new PnlShipment(this);
	private final PnlPayment pnlPay = new PnlPayment();
	private final PnlSummary pnlSummary = new PnlSummary(this);
	private final PnlCartList pnlCart = new PnlCartList(this);

	protected boolean onPnlShip = false;
	protected boolean onPnlPay = false;
	protected boolean onPnlConfirm = false;
	protected boolean onCart = true; // first tab

	private TabPanel cartFlow = new TabPanel();

	public static interface FlowHeader extends SafeHtmlTemplates {
		@Template("<div><div class='flow-number'>{1}</div> <div class='flow-title'>{0}</div></div>")
		SafeHtml header(String str, int i);
	}

	public PayFlowUI() {		
		
		FlowHeader TEMPLATE = GWT.create(FlowHeader.class);
		
		HTML h1 = new HTML(TEMPLATE.header(pay.trans.cart(), 1));
		HTML h2 = new HTML(TEMPLATE.header(pay.trans.shipment(), 2));
		HTML h3 = new HTML(TEMPLATE.header(pay.trans.paymentStyle(), 3));
		HTML h4 = new HTML(TEMPLATE.header(pay.trans.approve(), 4));
		h1.setWidth("125px");
		h2.setWidth("220px");
		h3.setWidth("220px");
		h4.setWidth("110px");
		
		
		cartFlow.add(pnlCart, h1);
		cartFlow.add(pnlShip, h2);
		cartFlow.add(pnlPay, h3);
		cartFlow.add(pnlSummary, h4);

		cartFlow.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				onPnlShip = false;
				onPnlPay = false;
				onCart = false;
				onPnlConfirm = false;

				int ind = ((Integer) event.getSelectedItem()).intValue();
				if (ind == 0)
					onCart = true;
				else if (ind == 1)
					onPnlShip = true;
				else if (ind == 2)
					onPnlPay = true;
				else if (ind == 3) {
					onPnlConfirm = true;
				}

				if (ind == 3) {
					holder.remove(1);
					// holder.remove(pnlNext);
					holder.add(pnlConfirm);
				} else {
					holder.remove(1);
					holder.add(pnlNext);
				}

			}
		});

		holder.setStyleName("site-holder");
		holder.setWidth("930px");
		holder.add(cartFlow);
		holder.add(pnlNext);
		this.initWidget(holder);
		
		com.google.gwt.core.client.Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				cartFlow.selectTab(0);
				
			}
		});
	}

	public void dataLoaded() {
		pnlPay.loadData();
		pnlShip.loadData();		
	}
	
	public void cartChanged(Cart cart) {
		pnlSummary.loadData(cart);
		pnlNext.loadData(cart);		
	}

	public void goNext() {
		if (onCart) {
			gotoShipmnet();
			return;
		}
		if (onPnlShip) {
			if (!pnlShip.check())
				return;
			pnlShip.save();
			gotoPayment();
			return;
		}
		if (onPnlPay) {
			if (!pnlPay.check())
				return;
			pnlPay.save();
			gotoConfirm();
			return;
		}

	}

	private void gotoConfirm() {
		cartFlow.selectTab(3);
	}

	protected void gotoPayment() {
		cartFlow.selectTab(2);
	}

	protected void gotoShipmnet() {
		cartFlow.selectTab(1);
	}

	@Override
	public void gotoStep(int i) {
		cartFlow.selectTab(i);
	}

}
