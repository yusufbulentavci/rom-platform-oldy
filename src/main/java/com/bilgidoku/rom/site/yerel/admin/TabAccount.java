package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;

public class TabAccount extends Composite {
	Integer cpuLimit = null;
	Integer cpuUsage = null;
	Integer diskLimit = null;
	Long diskUsage = null;
	Integer netwLimit = null;
	Integer netwUsage = null;
	private boolean cpuready = false;
	private boolean netready = false;
	private boolean diskready = false;

	private Grid pnlRich = new Grid(2, 2);
	private Grid pnlDomains = new Grid(2, 2);

	private HTML pnlNet = new HTML();
	private HTML pnlDisk = new HTML();
	private HTML pnlCpu = new HTML();

	private boolean prrichready = false;
	private boolean prnetready = false;
	private boolean prdomainready = false;
	private boolean prdiskready = false;
	private boolean prcpuready = false;

	private final CaptionPanel capUsage = new CaptionPanel(Ctrl.trans.usages());
	private final CaptionPanel capPrice = new CaptionPanel(Ctrl.trans.prices());

	private final FlowPanel holder = new FlowPanel();

	public TabAccount() {
//		getUsageValues();
//		getPrices();

		holder.add(capUsage);
		holder.add(capPrice);

		initWidget(new ScrollPanel(holder));
	}

//	private void getPrices() {
//
//		SellcpuDao.get("/_/_sellcpu", new SellcpuResponse() {
//			@Override
//			public void ready(Sellcpu value) {
//				pnlCpu.setHTML(" " + Ctrl.trans.cpu() + " (" + Ctrl.trans.monthly() + " mhz) : " + value.mhzmonthcredits);
//				prcpuready = true;
//				pricesReady();
//
//			}
//		});
//
//		SelldiskcapacityDao.get("/_/_selldiskcapacity", new SelldiskcapacityResponse() {
//			@Override
//			public void ready(Selldiskcapacity value) {
//				pnlDisk.setHTML(" " + Ctrl.trans.disk() + " (" + Ctrl.trans.monthly() + " gb) : " + value.gbmonthcredits);
//				prdiskready = true;
//				pricesReady();
//
//			}
//		});
//
//		SellnetDao.get("/_/_sellnet", new SellnetResponse() {
//			@Override
//			public void ready(Sellnet value) {
//				pnlNet.setHTML(" " + Ctrl.trans.network() + " (" + Ctrl.trans.monthly() + " kbps) : " + value.kbpsmonthcredits);
//				prnetready = true;
//				pricesReady();
//
//			}
//		});
//
//		SellrichwebDao.list("/_/_sellrichweb", new SellrichwebResponse() {
//			@Override
//			public void array(List<Sellrichweb> myarr) {
//				HorizontalPanel vp = new HorizontalPanel();
//				for (Sellrichweb sr : myarr) {
//
//					Grid ft = new Grid(5, 3);
//					ft.setBorderWidth(1);
//					ft.setHTML(0, 0, sr.pr+"");
//
//					ft.setHTML(1, 1, Ctrl.trans.searchimgcs());
//					ft.setHTML(1, 2, sr.searchimgcs + "");
//
////					ft.setHTML(2, 1, Ctrl.trans.transperwordcs());
////					ft.setHTML(2, 2, sr.transperwordcs + "");
////
////					ft.setHTML(3, 1, Ctrl.trans.ttsperwordcs());
////					ft.setHTML(3, 2, sr.ttsperwordcs + "");
//
//					ft.setHTML(4, 1, Ctrl.trans.getimgcs());
//					ft.setHTML(4, 2, sr.getimgcs + "");
//
//					vp.add(ft);
//
//				}
//
//				pnlRich.setHTML(0, 0, Ctrl.trans.richWeb());
//				pnlRich.setWidget(1, 1, vp);
//				prrichready = true;
//				pricesReady();
//
//			}
//		});
//
//		SelldomainnamesDao.list("/_/_selldomainnames", new SelldomainnamesResponse() {
//			@Override
//			public void array(List<Selldomainnames> value) {
//				HorizontalPanel vp = new HorizontalPanel();
//				for (Selldomainnames dn : value) {
//					FlexTable ft = new FlexTable();
//					ft.setBorderWidth(1);
//					ft.setHTML(0, 0, dn.suffix);
//					ft.setHTML(1, 1, "1 " + Ctrl.trans.year());
//					ft.setHTML(1, 2, dn.oneyearcredits + "");
//					ft.setHTML(2, 1, "2 " + Ctrl.trans.years());
//					ft.setHTML(2, 2, dn.twoyearcredits + "");
//					ft.setHTML(3, 1, "5 " + Ctrl.trans.years());
//					ft.setHTML(3, 2, dn.fiveyearcredits + "");
//					ft.setHTML(4, 1, "10 " + Ctrl.trans.years());
//					ft.setHTML(4, 2, dn.tenyearcredits + "");
//					ft.setHTML(5, 1, Ctrl.trans.move());
//					ft.setHTML(5, 2, dn.movecredits + "");
//					vp.add(ft);
//				}
//
//				pnlDomains.setHTML(0, 0, "Domain");
//				pnlDomains.setWidget(1, 1, vp);
//				prdomainready = true;
//				pricesReady();
//
//			}
//		});
//
//	}
//
//	private void getUsageValues() {
//		CpuusageDao.get("/_/_cpuusage", new CpuusageResponse() {
//			@Override
//			public void ready(Cpuusage value) {
//
//				cpuLimit = value.mhzlimit;
//				cpuUsage = value.currentmhz;
//				cpuready = true;
//				dataReady();
//
//			}
//		});
//
//		NetusageDao.get("/_/_netusage", new NetusageResponse() {
//			@Override
//			public void ready(Netusage value) {
//				netwUsage = value.currentkbps;
//				netwLimit = value.kbpslimit;
//				netready = true;
//				dataReady();
//			}
//		});
//
//		DiskusageDao.get("/_/_diskusage", new DiskusageResponse() {
//			@Override
//			public void ready(Diskusage value) {
//				diskLimit = value.usagelimit;
//				diskUsage = value.currentusage;
//				diskready = true;
//				dataReady();
//
//			}
//		});
//		//
//		// ChargingDao.get("", new ChargingResponse() {
//		// @Override
//		// public void ready(Charging value) {
//		// Long credits = value.credits;
//		// //1 dolar 1 milyon kredi
//		// }
//		// });
//
//	}

	protected void pricesReady() {

		if (!(prcpuready && prdiskready && prdomainready && prnetready && prrichready))
			return;
		
		FlowPanel fp = new FlowPanel();
		fp.add(pnlDisk);
		fp.add(pnlNet);
		fp.add(pnlCpu);		
		fp.add(pnlRich);
		fp.add(pnlDomains);

		capPrice.add(fp);
		
	}

	protected void dataReady() {

		if (!(diskready && cpuready && netready))
			return;

		DataTable gaugeData = DataTable.create();
		gaugeData.addColumn(ColumnType.STRING, Ctrl.trans.date(), "label");
		gaugeData.addColumn(ColumnType.NUMBER, Ctrl.trans.hitCount(), "value");

		if (gaugeData.getNumberOfRows() > 0)
			gaugeData.removeRows(0, 3);
		gaugeData.addRows(3);
		gaugeData.setValue(0, 0, "Disk %");
		gaugeData.setValue(0, 1, getDiskPercentage());
		gaugeData.setValue(1, 0, "Cpu %");
		gaugeData.setValue(1, 1, getCpuPercentage());
		gaugeData.setValue(2, 0, "Network %");
		gaugeData.setValue(2, 1, getNetwPercentage());

		com.google.gwt.visualization.client.visualizations.Gauge.Options options = com.google.gwt.visualization.client.visualizations.Gauge.Options
				.create();
		options.setHeight(240);
		options.setWidth(600);
		options.setRedRange(90, 100);
		options.setYellowRange(75, 90);
		options.setMinorTicks(5);

		com.google.gwt.visualization.client.visualizations.Gauge cpu = new com.google.gwt.visualization.client.visualizations.Gauge(
				gaugeData, options);
		// cpu.draw(gaugeData, options);

		FlexTable ft = new FlexTable();
		ft.setHTML(0, 0, Ctrl.trans.cpuUsage() + " / " +  Ctrl.trans.cpuLimit() );
		ft.setHTML(0, 1, cpuUsage + "");
		ft.setHTML(0, 2, cpuLimit + "");

		ft.setHTML(1, 0, Ctrl.trans.networkUsage() + " / " +  Ctrl.trans.networkLimit());
		ft.setHTML(1, 1, netwUsage + "");
		ft.setHTML(1, 2, netwLimit + "");

		ft.setHTML(2, 0, Ctrl.trans.diskUsage() + " / " +  Ctrl.trans.diskLimit());
		ft.setHTML(2, 1, diskUsage + "");
		ft.setHTML(2, 2, diskLimit + "");

		ft.setBorderWidth(1);
		
		FlowPanel fp = new FlowPanel();
		fp.add(cpu);
		fp.add(ft);
		
		capUsage.add(fp);

	}

	private int getNetwPercentage() {
		if (netwUsage == null || netwLimit == null || netwLimit.equals(0))
			return 0;
		float i = 0;
		try {
			i = (100 * netwUsage / netwLimit);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Math.round(i);
	}

	private int getCpuPercentage() {
		if (cpuLimit == null || cpuUsage == null || cpuLimit.equals(0))
			return 0;
		float i = 0;
		try {
			i = (100 * cpuUsage / cpuLimit);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Math.round(i);
	}

	private int getDiskPercentage() {
		if (diskLimit == null || diskUsage == null || diskLimit.equals(0))
			return 0;
		float i = 0;
		try {
			i = (100 * diskUsage / diskLimit);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Math.round(i);
	}

}
