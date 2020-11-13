package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.util.com.Authenticator;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.gwt.client.util.panels.StatusBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

public class TopOld extends Composite {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
//	private String version;
	
	
	
	public TopOld(String title) {
//		if(version.equals("")){
//			this.version=version;
//		}
//		else{
//			this.version="-"+version;
//		}
	
		FlexTable ft = new FlexTable();
		ft.setSize("100%", "100%");
		ft.setWidget(0, 0, getLogo(title));
		ft.setWidget(0, 1, StatusBar.getOne());
		ft.setWidget(0, 2, getLogout());
		ft.setStyleName("gwt-RichTextToolbar");
		ft.getElement().getStyle().setPadding(0, Unit.PX);
		ft.getElement().getStyle().setMargin(0, Unit.PX);
		ft.getFlexCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		ft.getFlexCellFormatter().setAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		this.initWidget(ft);
		
	}
	
	private Widget getLogout() { 
		SiteButton  btnLogout = new SiteButton ("/_public/images/bar/account_logout.png", "", trans.logout(), "");
		btnLogout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Authenticator.logout();
			}
		});

		return btnLogout;
	}

	public HTML getLogo(String title) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<div style='text-align:bottom;padding:2px;color: #666666;font-size: 16px;font-family:inherit;font-weight:bold;'><img src='/_public/images/bar/hill.png' width='16px' height='16px'/>" + title + "</div>");
		HTML logo = new HTML(sb.toSafeHtml());
		return logo;
	}
	
}
