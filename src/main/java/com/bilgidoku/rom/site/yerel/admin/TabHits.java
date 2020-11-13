package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

public class TabHits extends ScrollPanel implements DataReady {
	private SiteToolbarButton btnPrev = new SiteToolbarButton("/_local/images/common/page_previous.png", Ctrl.trans.prevWeek(), "", "");
	private SiteToolbarButton btnNext = new SiteToolbarButton("/_local/images/common/page_next.png", Ctrl.trans.nextWeek(), "", "");
	
	private HitsActions chartController;
	
	private final VerticalPanel holder = new VerticalPanel();
	
	private com.google.gwt.visualization.client.visualizations.corechart.Options lineHitsOptions = com.google.gwt.visualization.client.visualizations.corechart.Options
			.create();
	private com.google.gwt.visualization.client.visualizations.GeoMap.Options mapCountryOptions = com.google.gwt.visualization.client.visualizations.GeoMap.Options
			.create();
	private com.google.gwt.visualization.client.visualizations.corechart.Options pieBrowserOptions = com.google.gwt.visualization.client.visualizations.corechart.Options
			.create();
	private LineChart lineHits = null;
	private GeoMap mapCountries;
	private PieChart pieBrowsers;


	
	public TabHits(DataTable lineData, DataTable mapData, DataTable pieData, boolean hasNextWeek) {
		this.chartController = new HitsActions(this);
		btnNext.setEnabled(false);
		forPrev();
		forNext();

		lineHitsOptions.setHeight(240);
		lineHitsOptions.setWidth(800);
		lineHitsOptions.setTitle("Hits");

		mapCountryOptions.setDataMode(GeoMap.DataMode.REGIONS);
		mapCountryOptions.setHeight(300);
		mapCountryOptions.setWidth(450);
		// mapOptions.setShowLegend(false);
		// mapOptions.setColors(0xFF8747, 0xFFB581, 0xc06000);
		// mapOptions.setRegion("world");

		pieBrowserOptions.setWidth(400);
		pieBrowserOptions.setHeight(240);
		pieBrowserOptions.setTitle("Browsers");
		// pieOptions.setEnableTooltip(true);

		 lineHits = new LineChart(lineData, lineHitsOptions);
		 mapCountries = new GeoMap(mapData, mapCountryOptions);
		 pieBrowsers = new PieChart(pieData, pieBrowserOptions);

		FlowPanel buttons = new FlowPanel();
		buttons.add(btnPrev);
		buttons.add(btnNext);

		holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		holder.setSpacing(4);
		holder.add(buttons);

		CaptionPanel pnlDaily = new CaptionPanel(Ctrl.trans.dailyHits());
		pnlDaily.add(lineHits);

		CaptionPanel pnlCountry = new CaptionPanel(Ctrl.trans.countryHits());
		pnlCountry.add(mapCountries);

		CaptionPanel pnlBrowser = new CaptionPanel(Ctrl.trans.browserHits());
		pnlBrowser.add(pieBrowsers);

//		CaptionPanel pnlOpSys = new CaptionPanel(Ctrl.trans.opsysHits());
//		pnlOpSys.add(new Label("under con"));
//
//		CaptionPanel pnlByPage = new CaptionPanel(Ctrl.trans.hitsByPage());
//		pnlByPage.add(new Label("under con"));
//
//		CaptionPanel pnlSource = new CaptionPanel(Ctrl.trans.referrerSites());
//		pnlSource.add(new Label("under con"));
//
//		CaptionPanel pnlKeyWords = new CaptionPanel(Ctrl.trans.searchKeywords());
//		pnlKeyWords.add(new Label("under con"));

		holder.add(pnlDaily);
		holder.add(pnlCountry);
		holder.add(pnlBrowser);
//		holder.add(pnlOpSys);
//		holder.add(pnlByPage);
//		holder.add(pnlSource);
//		holder.add(pnlKeyWords);

		this.add(holder);
	}

	private void forNext() {
		btnNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				chartController.getNextWeek();
			}
		});
	}

	private void forPrev() {
		btnPrev.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				chartController.getPrevWeek();
			}
		});
	}

	public void drawData(DataTable lineData, DataTable mapData, DataTable pieData, boolean hasNextWeek) {
			lineHits.draw(lineData);
			
		mapCountries.draw(mapData);
		pieBrowsers.draw(pieData);

		if (hasNextWeek)
			btnNext.setEnabled(true);
		else
			btnNext.setEnabled(false);
	}

	@Override
	public void hitsDataReady(DataTable lineData, DataTable mapData, DataTable pieData, boolean hasNextWeek) {
		this.drawData(lineData, mapData, pieData, hasNextWeek);
	}

}
