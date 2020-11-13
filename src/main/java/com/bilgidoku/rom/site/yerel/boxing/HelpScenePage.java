package com.bilgidoku.rom.site.yerel.boxing;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.ImageAnchor;
import com.bilgidoku.rom.gwt.client.util.common.SiteToolbarButton;
import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.bilgidoku.rom.gwt.client.util.help.VideoDlg;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HelpScenePage extends DialogBox {

	VerticalPanel vpMenu = new VerticalPanel();
	VerticalPanel vpPage = new VerticalPanel();
	VerticalPanel vpSite = new VerticalPanel();

	List<Helpy> visiblesMenu = new ArrayList<Helpy>();
	List<Helpy> visiblesPage = new ArrayList<Helpy>();
	List<Helpy> visiblesSite = new ArrayList<Helpy>();
	private BoxerGui gui;

	public HelpScenePage(BoxerGui gui) {

		this.gui = gui;
		Button btnClose = new Button("Close");
		btnClose.setStyleName("site-closebutton");
		forClose(btnClose);

		vpMenu.setSpacing(1);
		vpPage.setSpacing(1);
		vpSite.setSpacing(1);

		FlowPanel fp = new FlowPanel();
		fp.add(vpMenu);
		fp.add(vpSite);
		fp.add(vpPage);

		fp.add(btnClose);

		this.center();
		this.setWidget(fp);
		this.setAutoHideEnabled(true);
		this.setStyleName("site-helpdlg");
		this.setModal(false);
		this.hide();
		this.getElement().getStyle().setOpacity(0.7);
	}

	private void forClose(Button btnClose) {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				HelpScenePage.this.hide();
			}
		});

	}

	public void go() {
		uiSection(visiblesMenu, "Menu", vpMenu);

		if (gui.isPageOpen())
			uiSection(visiblesPage, "Page", vpPage);

		if (gui.isSiteOpen())
			uiSection(visiblesSite, "Site", vpSite);

		this.show();
		this.setPopupPositionAndShow(new PositionCallback() {

			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				HelpScenePage.this.center();
			}

		});

	}

	private void uiSection(List<Helpy> arr, String title, VerticalPanel vp) {
		vp.clear();

		for (int i = 0; i < arr.size(); i++) {
			Helpy h = arr.get(i);
			vp.add(getLine(h));
		}
		if (arr.size() > 0) {
			vp.add(new HTML("<hr>"));
		}

	}

	private Widget getLine(final Helpy h) {

		Anchor a = new Anchor(h.getText());
		a.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// goto Help uri

			}
		});

		a.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				h.getComp().addStyleName("site-helphover");
				point(h.getComp().getAbsoluteLeft(), h.getComp().getAbsoluteTop());

			}
		});

		a.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				pointOut();
				h.getComp().removeStyleName("site-helphover");

			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		// hp.setSpacing(0);
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(a);

		if (h.getUris() != null && h.getUris().length > 0 && !h.getUris()[0].isEmpty()) {
			ImageAnchor ia = new ImageAnchor("/_local/images/common/movie.png");
			ia.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new VideoDlg(h.getText(), h.getUris());
				}
			});
			hp.add(ia);
		}

		return hp;

	}

	public void addToMenuVisibles(Helpy helpy) {
		this.visiblesMenu.add(helpy);
	}

	public void addToPageVisibles(Helpy helpy) {
		this.visiblesPage.add(helpy);
	}

	public void clearSite() {
		this.visiblesSite.clear();

	}

	public void addToSiteVisibles(Helpy helpy) {
		this.visiblesSite.add(helpy);

	}

	private void point(int left, int top) {
		DOM.getElementById("pagepointer").getStyle().setDisplay(Display.BLOCK);
		DOM.getElementById("pagepointer").getStyle().setTop(top + 20, Unit.PX);
		DOM.getElementById("pagepointer").getStyle().setLeft(left + 20, Unit.PX);
	}

	private void pointOut() {
		DOM.getElementById("pagepointer").getStyle().setDisplay(Display.NONE);
	}

	public void addToHelp(Widget[] widgets, String type) {
		if (widgets == null)
			return;
		for (int i = 0; i < widgets.length; i++) {
			addToHelp(widgets[i], type);
		}
	}

	private void addToHelp(Widget widget, String type) {
		if (type.equals("menu")) {

			BoxerMenuButton btn = (BoxerMenuButton) widget;
			addToMenuVisibles(btn.getHelpy());

		} else if (type.equals("page-view")) {

			SiteToolbarButton btn = (SiteToolbarButton) widget;
			addToPageVisibles(btn.getHelpy());

		} else if (type.equals("site-view")) {

			SiteToolbarButton btn = (SiteToolbarButton) widget;
			addToSiteVisibles(btn.getHelpy());

		}
	}

}
