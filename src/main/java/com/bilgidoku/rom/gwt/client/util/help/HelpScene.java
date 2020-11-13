package com.bilgidoku.rom.gwt.client.util.help;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.ImageAnchor;
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

public class HelpScene extends DialogBox {
	
	/*
	 * butonlarının eklenmesi istenen paneller HasWidget'ı implement etmeli!
	 * 
	 * */

	VerticalPanel vpTop = new VerticalPanel();
	VerticalPanel vpNav = new VerticalPanel();
	VerticalPanel vpTabTb = new VerticalPanel();
	VerticalPanel vpNavTb = new VerticalPanel();
	VerticalPanel vpPer = new VerticalPanel();
	VerticalPanel vpPerBut = new VerticalPanel();

	List<Helpy> visiblesTabTb = new ArrayList<Helpy>();
	List<Helpy> visiblesNav = new ArrayList<Helpy>();
	List<Helpy> visiblesTop = new ArrayList<Helpy>();
	List<Helpy> visiblesNavTb = new ArrayList<Helpy>();
	List<Helpy> visiblesPer = new ArrayList<Helpy>();
	List<Helpy> visiblesPerBut = new ArrayList<Helpy>();

	public HelpScene() {

		Button btnClose = new Button("Close");
		btnClose.setStyleName("site-closebutton");
		forClose(btnClose);

		vpTop.setSpacing(1);
		vpNav.setSpacing(1);
		vpNavTb.setSpacing(1);
		vpTabTb.setSpacing(1);
		vpPerBut.setSpacing(1);
		vpPer.setSpacing(1);

		
		FlowPanel fp = new FlowPanel();
		fp.add(vpTop);
		fp.add(vpNav);
		fp.add(vpNavTb);
		fp.add(vpTabTb);
		fp.add(vpPerBut);
		fp.add(vpPer);

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
				HelpScene.this.hide();
			}
		});

	}

	public void go() {
		uiSection(visiblesTop, "Top buttons", vpTop);
		
		uiSection(visiblesNav, "Nav", vpNav);
		uiSection(visiblesNavTb, "Nav TB", vpNavTb);
		
		uiSection(visiblesTabTb, "Tab TB", vpTabTb);
		
		uiSection(visiblesPer, "Pers", vpPer);
		
		uiSection(visiblesPerBut, "Per But", vpPerBut);
		
		this.show();
		this.setPopupPositionAndShow(new PositionCallback() {

			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				// HelpScene.this.setPopupPosition(Window.getClientWidth() -
				// HelpScene.this.getOffsetWidth(), 50);
				HelpScene.this.center();
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
//		hp.setSpacing(0);
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(a);

		if (h.uris != null && h.uris.length > 0 && h.uris[0] != null && !h.uris[0].isEmpty()) {
			ImageAnchor ia = new ImageAnchor("/_public/images/bar/movie.png");
			ia.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new VideoDlg(h.getText(), h.uris);
				}
			});
			hp.add(ia);
		}

		return hp;

	}
	
	private static void point(int left, int top) {
		DOM.getElementById("point").getStyle().setDisplay(Display.BLOCK);
		DOM.getElementById("point").getStyle().setTop(top + 20, Unit.PX);
		DOM.getElementById("point").getStyle().setLeft(left + 20, Unit.PX);
	}

	private static void pointOut() {
		DOM.getElementById("point").getStyle().setDisplay(Display.NONE);

	}



	public void clearTabToolbar() {
		this.visiblesTabTb.clear();

	}

	public void addToTopVisibles(Helpy helpy) {
		this.visiblesTop.add(helpy);
	}

	public void addToTabTbVisibles(Helpy helpy) {
		this.visiblesTabTb.add(helpy);
	}

	public void addToNavTbVisibles(Helpy helpy) {
		this.visiblesNavTb.add(helpy);

	}

	public void clearNavToolbar() {
		this.visiblesNavTb.clear();

	}

	public void addToPerspVisibles(Helpy helpy) {
		this.visiblesPer.add(helpy);
	}

	public void clearPerBut() {
		this.visiblesPerBut.clear();

	}

	public void addToPerspBtnsVisibles(Helpy helpy) {
		this.visiblesPerBut.add(helpy);

	}
	
	public void addToNavVisibles(Helpy helpy) {
		this.visiblesNav.add(helpy);

	}

	public void clearNav() {
		this.visiblesNav.clear();

	}

	public void addToPageMenuVisibles(Helpy helpy) {
		// TODO Auto-generated method stub
		
	}


}
