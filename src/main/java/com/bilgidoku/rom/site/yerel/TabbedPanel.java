package com.bilgidoku.rom.site.yerel;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.common.ImageAnchor;
import com.bilgidoku.rom.site.yerel.common.SiteTabPanel;
import com.bilgidoku.rom.site.yerel.events.ResizeEvent;
import com.bilgidoku.rom.site.yerel.events.ResizeEventHandler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class TabbedPanel extends ResizeComposite {

	private SiteTabPanel tabs;
	
	public TabbedPanel() {
		setTabs(new SiteTabPanel(29, Unit.PX));
		addCloseAllTab();
		initWidget(getTabs());

	}

	private void addCloseAllTab() {
		add("closeall", new SimplePanel(), getCloseAllTitle());
		tabs.getTabWidget(0).setVisible(false);
  	}
	
	void showCloseAllTab() {
		tabs.getTabWidget(0).setVisible(true);
	}
	
	void hideCloseAllTab() {
		tabs.getTabWidget(0).setVisible(false);
	}

	private Widget getTabTitle(final Widget widget, final String uri, final String named, final String color) {
		String style;
		if (color.isEmpty())
			style = "site-tabheader site-tabheader-" + Data.DEFAULT_COLOR;
		else
			style = "site-tabheader site-tabheader-" + color;

		final ImageAnchor closeBtn = new ImageAnchor();
		closeBtn.changeResource("/_local/images/common/cross_tab.png");
		closeBtn.setTitle(uri);
		closeBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeTab(uri);
			}
		});
		

		String title = named;
		String lang = "";
		if (named.indexOf("(")>0) {
			lang = title.substring(title.indexOf("("));
			title = title.substring(0, title.indexOf("("));
		} 
		if (title.length() > 20)		{
			title = title.substring(0,17)+"...";
		}
		
		HTML tt = new HTML("&nbsp;" + title + lang + "&nbsp;");
		tt.getElement().getStyle().setPadding(1,Unit.PX);
		
		MyFlowPanel hPanel = new MyFlowPanel(uri);
		hPanel.setStyleName(style, true);
		hPanel.add(tt);
		hPanel.add(closeBtn);
		hPanel.setTitle(uri);
		return hPanel;
	}

	public void closeAndOpenTab(String id, String named, Widget theTab, String color) {
		this.closeTab(id);
		this.openTab(id, named, theTab, color);
	}

	public void closeTab(String id) {
		int index = getIndex(id);

		if (index < 0)
			return;

		if (index > 0) {
			// set selected tab
			getTabs().selectTab(index - 1);
		}
		
		getTabs().remove(index);
		if (getTabs().getWidgetCount() == 1) {
			hideCloseAllTab();
		}
		Ctrl.tabClosed();

	}

	public void add(String uri, Widget tab, Widget header) {
		int index = getIndex(uri);
		if (index < 0) {
			//really adding
			tabs.add(tab, header);
			tabs.selectTab(tab, false);
			
			if (tabs.getWidgetCount() >= 2) {
				showCloseAllTab();
			}
			
		} else {
			getTabs().selectTab(index, false);
		}
	}

	public void openTab(String uri, String named, Widget theTab, String color) {
		add(uri, theTab, getTabTitle(theTab, uri, named, color));
	}

	private int getIndex(String uri) {
		int index = -1;

		for (int j = 0; j < getTabs().getWidgetCount(); j++) {
			if (getTabs().getTabWidget(j) instanceof MyFlowPanel) {
				MyFlowPanel fp = (MyFlowPanel) getTabs().getTabWidget(j);
				if (fp.getId().equals(uri)) {
					index = j;
					break;
				}
			}
		}

		return index;
	}

	public SiteTabPanel getTabs() {
		return tabs;
	}

	public void setTabs(SiteTabPanel tabs) {
		this.tabs = tabs;
	}

	@Override
	public void onResize() {
		fireEvent(new ResizeEvent());
	}

	public HandlerRegistration addResizedHandler(ResizeEventHandler handler) {
		return this.addHandler(handler, ResizeEvent.TYPE);
	}

	private Widget getCloseAllTitle() {
		final String myStyle = "site-tabheader site-tabheader-" + Data.DEFAULT_COLOR;

		final ImageAnchor closeBtn = new ImageAnchor();
		closeBtn.changeResource("/_local/images/cross_tab.png");
		closeBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeAllTabs();
			}
		});

		closeBtn.getElement().getStyle().setPadding(1, Unit.PX);
		
		MyFlowPanel hPanel = new MyFlowPanel("closeall");
		hPanel.setStyleName(myStyle, true);
		hPanel.add(closeBtn);
		hPanel.setTitle(Ctrl.trans.closeAll());

		return hPanel;
	}

	protected void closeAllTabs() {
		getTabs().clear();
		add("closeall", new SimplePanel(), getCloseAllTitle());
		tabs.getTabWidget(0).setVisible(false);
		Ctrl.tabClosed();
	}

	protected class MyFlowPanel extends FlowPanel {
		private String id;

		public MyFlowPanel(String id1) {
			super();
			id = id1;
		}

		public String getId() {
			return id;
		}

		public void setId(String id1) {
			id = id1;
		}

	}

}
