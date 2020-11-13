package com.bilgidoku.rom.site.yerel.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.site.Hits;
import com.bilgidoku.rom.gwt.araci.client.site.HitsDao;
import com.bilgidoku.rom.gwt.araci.client.site.HitsResponse;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;

public class HitsActions {
	private Date reportStart;
	private Date reportEnd;	
	private Date ultimateEnd;
	//private DataTable lineData = DataTable.create();
	//private DataTable mapData = DataTable.create();
	//private DataTable pieData = DataTable.create();
	private final DataReady caller;
	
	@SuppressWarnings("deprecation")	
	public HitsActions(DataReady caller) {
		this.caller = caller;
//		Date now = new Date();
//		this.reportEnd = DateTimeFormat.getFormat("yyyy-M-d").parse((now.getYear() + 1900) + "-" + (now.getMonth() + 1) + "-" + now.getDate());
		this.reportEnd=new Date();
		this.reportStart = new Date(reportEnd.getTime() - 7L * 24 * 3600 * 1000);
		this.ultimateEnd = reportEnd;
		//this.lineData.addColumn(ColumnType.STRING, "Date", "date");
		//this.lineData.addColumn(ColumnType.NUMBER, "Hit Count", "hitsByDay");
//		this.mapData.addColumn(ColumnType.STRING, "Country", "country");
//		this.mapData.addColumn(ColumnType.NUMBER, "Hit Count", "hitsByCountry");
//		this.pieData.addColumn(ColumnType.STRING, "Browser Name");
//	    this.pieData.addColumn(ColumnType.NUMBER, "Hit Count");

	}
	
	
	public void getWeeklyData() {
		HitsDao.list(reportStart.getTime(), reportEnd.getTime(), "/_/_hits", new HitsResponse() {
			@Override
			public void array(List<Hits> myarr) {

				DataTable lineData = DataTable.create();
				DataTable mapData = DataTable.create();
				DataTable pieData = DataTable.create();
				
				lineData.addColumn(ColumnType.STRING, Ctrl.trans.date(), "date");
				lineData.addColumn(ColumnType.NUMBER, Ctrl.trans.hitCount(), "hitsByDay");				
				
				mapData.addColumn(ColumnType.STRING, Ctrl.trans.country(), "country");
				mapData.addColumn(ColumnType.NUMBER, Ctrl.trans.hitCount(), "hitsByCountry");
				
				pieData.addColumn(ColumnType.STRING, Ctrl.trans.browserName());
			    pieData.addColumn(ColumnType.NUMBER, Ctrl.trans.hitCount());

				buildDailyHitsData(lineData, myarr);
				buildCountryData(mapData, myarr);
				buildBrowserData(pieData, myarr);
				//buildTopBringingWordsData(pieData, myarr);
				//Window.alert(lineData.getNumberOfRows() + ":"+  mapData.getNumberOfRows() + ":"+  pieData.getNumberOfRows());
				boolean hasNextWeek = true;
				if (reportEnd.compareTo(ultimateEnd) >= 0)
					hasNextWeek = false;
				
				getCaller().hitsDataReady(lineData, mapData, pieData, hasNextWeek);
			}
		});		
	}
	/*
	public void getCurrentWeekData() {
		HitsDao.list(reportStart.getTime(), reportEnd.getTime(), "/_/_hits", new HitsResponse() {
			@Override
			public void array(List<Hits> myarr) {
				DataTable lineData = DataTable.create();
				DataTable mapData = DataTable.create();
				DataTable pieData = DataTable.create();

				lineData.addColumn(ColumnType.STRING, "Date", "date");
				lineData.addColumn(ColumnType.NUMBER, "Hit Count", "hitsByDay");				
				
				mapData.addColumn(ColumnType.STRING, "Country", "country");
				mapData.addColumn(ColumnType.NUMBER, "Hit Count", "hitsByCountry");
				
				pieData.addColumn(ColumnType.STRING, "Browser Name");
			    pieData.addColumn(ColumnType.NUMBER, "Hit Count");

				buildDailyHitsData(lineData, myarr);
				buildCountryData(mapData, myarr);
				buildBrowserData(pieData, myarr);
				//buildTopBringingWordsData(pieData, myarr);
				//Window.alert(lineData.getNumberOfRows() + ":"+  mapData.getNumberOfRows() + ":"+  pieData.getNumberOfRows());
				boolean hasNextWeek = true;
				if (reportEnd.compareTo(ultimateEnd) >= 0)
					hasNextWeek = false;
				
			}
		});		
	}
	*/
	public void getPrevWeek() {
		this.reportEnd = this.reportStart;
		this.reportStart = new Date(reportEnd.getTime() - 7L * 24 * 3600 * 1000);
		getWeeklyData();
	}

	public void getNextWeek() {
		this.reportStart = this.reportEnd;
		this.reportEnd = new Date(reportEnd.getTime() + 7L * 24 * 3600 * 1000);
		getWeeklyData();
	}

	protected void buildBrowserData(DataTable pieData, List<Hits> myarr) {
		if (pieData.getNumberOfRows() > 0)
			pieData.removeRows(0, pieData.getNumberOfRows());

		Map<String, Integer> mapBrowserHit = new HashMap<String, Integer>();
		for (int i = 0; i < myarr.size(); i++) {
			Hits hits = myarr.get(i);
			JSONObject joBro = hits.browsers.getValue().isObject();
			Set<String> cs = joBro.keySet();
			for (String key : cs) {
				if (!mapBrowserHit.containsKey(key)) {
					mapBrowserHit.put(key, new Integer(0));
				}
				Integer total = mapBrowserHit.get(key);
				total = total + getNumericValue(joBro.get(key));
				mapBrowserHit.put(key, total);
			}
		}
		
		Set<String> browsers = mapBrowserHit.keySet();
		int size = browsers.size();
		if (size <= 0) {
			//empty data causes problem, so add some dummy data
			pieData.addRows(1);
			pieData.setValue(0, 0, "ff");
			pieData.setValue(0, 1, 0);
			return;
		}

		pieData.addRows(size);
		int i = 0;
		for (String browser : browsers) {
			pieData.setValue(i, 0, browser);
			pieData.setValue(i, 1, mapBrowserHit.get(browser));
			i = i + 1;
		}
	}

	protected void buildCountryData(DataTable mapData, List<Hits> myarr) {
		
		if (mapData.getNumberOfRows() > 0)
			mapData.removeRows(0, mapData.getNumberOfRows());

		Map<String, Integer> mapCountryHit = new HashMap<String, Integer>();
		for (int i = 0; i < myarr.size(); i++) {
			Hits hits = myarr.get(i);
			JSONObject joCo = hits.countries.getValue().isObject();
			Set<String> cs = joCo.keySet();
			for (String key : cs) {
				if (!mapCountryHit.containsKey(key)) {
					mapCountryHit.put(key, new Integer(0));
				}
				Integer total = mapCountryHit.get(key);
				total = total + getNumericValue(joCo.get(key));
				mapCountryHit.put(key, total);
			}
		}
		
		Set<String> countries = mapCountryHit.keySet();
		int size = countries.size();
		if (size <= 0) {
			//empty data causes problem, so add some dummy data
			mapData.addRows(1);
			mapData.setValue(0, 0, "TR");
			mapData.setValue(0, 1, 0);
			return;
		}
		mapData.addRows(size);
		int i = 0;
		for (String country : countries) {
			mapData.setValue(i, 0, country);
			mapData.setValue(i, 1, mapCountryHit.get(country));
			i = i + 1;
		}		
	}

	protected void buildDailyHitsData(DataTable lineData, List<Hits> myarr) {
		if (lineData.getNumberOfRows() > 0)
			lineData.removeRows(0, 7);
		
		lineData.addRows(7);
		for (int i = 0; i < 7; i++) {
			String dd = getDay(i);
			int hh = getHitsForDay(myarr, i);
			lineData.setValue(i, 0, dd);
			lineData.setValue(i, 1, hh);
		}		
	}

	private int getHitsForDay(List<Hits> myarr, int i2) {
		int total = 0;						
		for (int i = 0; i < myarr.size(); i++) {
			Hits hits = myarr.get(i);
			if (inDay(hits.timebegin, i2)) {		
				JSONObject joCo = hits.countries.getValue().isObject();
				Set<String> cs = joCo.keySet();
				for (String key : cs) {
					total = total + getNumericValue(joCo.get(key));
				}
			}
		}
		return total;
	}


	protected boolean inDay(long timebegin, int i) {
		//time begin i. inci gun icinde mi?
		//2012-12-03 14:40:00.0
//		String d = timebegin.substring(0, 19);
		Date inthDayStart = new Date(reportStart.getTime() + i * 24 * 3600 * 1000);
		Date inthDayEnd = new Date(reportStart.getTime() + (i + 1) * 24 * 3600 * 1000);		
//		Date hitDate = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse(d);
		Date hitDate=new Date(timebegin);
		
		if ( hitDate.compareTo(inthDayStart) >= 0 &&  hitDate.compareTo(inthDayEnd) <= 0)
			return true;
		return false;
	}

//	private String formatDbDate(Date d) {
//		//java.text.DateFormat default DateFormat11/4/03 8:14 PM  ay/gun/yil ...
//		DateTimeFormat df = DateTimeFormat.getFormat("MM/d/yy h:mm a");
//		return df.format(d);
//	}
	
	@SuppressWarnings("deprecation")
	private String formatShowDate(Date d) {
		DateTimeFormat df = DateTimeFormat.getShortDateFormat();
		return df.format(d);		
	}
	private String getDay(int i) {
		Date theDay = new Date(reportStart.getTime() + (i + 1) * 24 * 3600 * 1000);
		return formatShowDate(theDay);
	}

	private int getNumericValue(JSONValue jv) {
		if (jv.isNumber() != null) {
			Double d = jv.isNumber().doubleValue();
			return d.intValue(); 
		} else if (jv.isString() != null) {
			String s = jv.isString().stringValue();
			return Integer.parseInt(s);
		} else {
			return 0;
		}
	}



	public DataReady getCaller() {
		return caller;
	}


}
