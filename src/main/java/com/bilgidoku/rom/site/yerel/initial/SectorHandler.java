package com.bilgidoku.rom.site.yerel.initial;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.constants.InitialConstants;
import com.bilgidoku.rom.site.yerel.constants.SectorNames;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;

public class SectorHandler {
	private final InitialConstants con = GWT.create(InitialConstants.class);
	private final ListBox sectorList = new ListBox();
	private final ListBox exampleGroups = new ListBox();
	private final ListBox examplePages = new ListBox();
	private final Button addExmGroup = new Button(ClientUtil.imageItemHTML("/_local/images/common/right.png", ""));
	private final Button addExmPage = new Button(ClientUtil.imageItemHTML("/_local/images/common/right.png", ""));
	private String sector = null;
	private String contentLang;

	private JSONArray sectorPages;
	private final InitialPage init;

	public SectorHandler(final InitialPage init) {
		this.init = init;
		setState(false);
		forAddGroup();
		forAddPage();
		forSectorListSelected();
		exampleGroups.setVisibleItemCount(5);
		examplePages.setVisibleItemCount(5);
		SectorNames constants = GWT.create(SectorNames.class);
		//String[] allsectors = constants.getStringArray("allsectors");
		String[] allsectors = {"societyculture", "communications", "computer", "financialservice", "healthbeauty", "organizations", "auto", "services", "educationtraining", "hoteltravel", "citygovernment", "weddings", "portalsearchengine", "multimedia", "energyoil", "agriculture", "event", "advertising", "family", "homegarden", "sportsrecreation", "manufacturing", "sciencetechnology", "realestate", "legalservices", "shopping", "restaurantbar", "businesseconomy", "artsentertainment", "construction", "industrialsupply", "newsmedia" };
		getSectorList().clear();
		getSectorList().addItem("", "");
		for (String string : allsectors) {
			getSectorList().addItem(constants.getString(string), string);
		}
	}

	private void setState(boolean state) {
		sectorList.setEnabled(state);
		exampleGroups.setEnabled(state);
		examplePages.setEnabled(state);
	}

	public void setContentLang(String lang) {
		setState(true);
		this.contentLang = lang;
	}

	private void forSectorListSelected() {
		getSectorList().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				//init.emptyPageTree();
				String oldSector = sector;
				sector = getSectorList().getValue(getSectorList().getSelectedIndex());
				if (oldSector == null) {
					getSectorList().removeItem(0);
				}
				loadSectorExamples();
				init.step4();
			}
		});
	}

	private void forAddPage() {
		addExmPage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String clicked = examplePages.getValue(examplePages.getSelectedIndex());
				addPage(clicked);
			}
		});

	}

	private void forAddGroup() {
		addExmGroup.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String clicked = exampleGroups.getValue(exampleGroups.getSelectedIndex());
				addGroup(clicked);
			}
		});

	}

	public String getSector() {
		return sector;
	}

	public ListBox getSectorList() {
		return sectorList;
	}

	public ListBox getExampleGroups() {
		return exampleGroups;
	}

	public ListBox getExamplePages() {
		return examplePages;
	}

	public Button getAddPageBtn() {
		return addExmPage;
	}

	public Button getAddGrpBtn() {
		return addExmGroup;
	}

	private void loadSectorExamples() {

		final String url = URL.encode("pages/" + sector.toLowerCase().substring(0, 4) + "_" + contentLang + ".js");
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			builder.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					Window.alert("Couldn't retrieve JSON:" + url);
				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						try {
							// parse the response text into JSON
							JSONValue jsonValue = JSONParser.parseStrict(response.getText());
							sectorPages = jsonValue.isArray();							
							if (sectorPages != null) {
								loadExamplesAndDefaults();
							} else {
								throw new JSONException();
							}
						} catch (JSONException e) {
							Window.alert("Could not parse JSON");
						}
					} else {
						Window.alert("Couldn't retrieve JSON (" + response.getStatusText() + "):" + url);
					}
				}
			});
		} catch (RequestException e) {
			Window.alert("Couldn't retrieve JSON");
		}
	}

	protected void loadExamplesAndDefaults() {
		exampleGroups.clear();
		examplePages.clear();
		for (int i = 0; i < sectorPages.size(); i++) {
			JSONArray page = sectorPages.get(i).isArray();
			if (getIsGroup(page)) {
				exampleGroups.addItem(getName(page), getUri(page));
				if (init.isfirstRun) {
					if (getDefault(page)) {
						addGroup(getUri(page));
					}
				}
			} else {
				examplePages.addItem(getName(page), getUri(page));
				if (init.isfirstRun) {
					if (getDefault(page)) {
						addPage(getUri(page));
					}
				}
			}
		}
	}

	private JSONArray getPage(String uri) {
		for (int i = 0; i < sectorPages.size(); i++) {
			JSONArray page = sectorPages.get(i).isArray();
			if (getUri(page).equals(uri)) {
				return page;
			}
		}
		return null;
	}

	private void addGroup(String uri) {
		JSONArray page = getPage(uri);
		String list = getList(page);
		init.addGroup(getName(page), getUri(page), list.indexOf('m') >= 0, list.indexOf('f') >= 0, list.indexOf('v') >= 0, getApp(page));
	}

	private void addPage(String uri) {
		JSONArray page = getPage(uri);
		String list = getList(page);
		String g = getGroups(page);
		if (g.length() > 0) {
			addGroup(g);
		} else {
			g = init.getSelectedPageGroup();
		}
		init.addPage(getName(page), getUri(page), list.indexOf('m') >= 0, list.indexOf('f') >= 0, list.indexOf('v') >= 0, getApp(page), g);
	}

	private final static int LOC_URI = 0;
	private final static int LOC_NAME = 1;
	private final static int LOC_DEFAULT = 2;
	private final static int LOC_GROUP = 3;
	private final static int LOC_LIST = 4;
	private final static int LOC_ISGROUP = 5;
	private final static int LOC_APP = 6;

	private String getUri(JSONArray page) {
		return page.get(LOC_URI).isString().stringValue();
	}

	private String getName(JSONArray page) {
		return page.get(LOC_NAME).isString().stringValue();
	}

	private boolean getDefault(JSONArray page) {
		return page.get(LOC_DEFAULT).isString().stringValue().equals("1");
	}

	private String getGroups(JSONArray page) {
		return page.get(LOC_GROUP).isString().stringValue();
	}

	private String getList(JSONArray page) {
		return page.get(LOC_LIST).isString().stringValue();
	}

	private boolean getIsGroup(JSONArray page) {
		return page.get(LOC_ISGROUP).isString().stringValue().equals("1");
	}

	private String getApp(JSONArray page) {
		return page.get(LOC_APP).isString().stringValue();
	}

	public void setSector(String sector2) {
		for (int i = 0; i < sectorList.getItemCount(); i++) {
			if (sectorList.getValue(i).equals(sector2)) {
				sectorList.setSelectedIndex(i);
				sectorList.removeItem(0);
				this.sector = sector2;
				loadSectorExamples();
				return;
			}
		}
		Window.alert("Unexpected sector:" + sector2);
	}

}
