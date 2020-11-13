package com.bilgidoku.rom.site.yerel.boxing;

import java.util.Map;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.StylesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StylesResponse;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.RequestClient;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.RomTemplatingClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.shared.CodeEditor;
import com.bilgidoku.rom.shared.MinRequest;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RequestCreator;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class BoxerData {

	private BoxerDataCb cb;
	private Runner runner;
	private String html;

	protected JSONObject commonStyle;
	private String uri;

	protected JSONObject style;
	private Runnable displayTrigger;
//	private CodeRepo codeRepo;
//	private JSONObject selectedTrans;
//	private JSONObject note;

	private JSONObject body;
	private JSONObject spot;
	private boolean bodyChanged = false;
	private boolean spotChanged = false;
	private boolean footerChanged = false;
	private boolean headerTextChanged = false;
	private boolean logoChanged = false;

	private BoxHolder bodyHolder = null;
	private BoxHolder spotHolder = null;
	private BoxHolder footerHolder = null;
	private BoxHolder headerHolder = null;

	private JSONObject headerText;
	private JSONObject siteFooter;
	private com.bilgidoku.rom.shared.json.JSONObject logo;
	
	
	private boolean inPreviewMode = false;
	boolean previewing = false;
	private String previewStyle;


	public BoxerData(String uri) {
		this.uri = uri;
		this.setDisplayTrigger(displayTrigger);
	}

	public void init() {
		StylesDao.get("/_/styles/common", new StylesResponse() {
			@Override
			public void rawReady(JSONValue val) {
				commonStyle = val.isObject();
				super.rawReady(val);
				checkLoad();
			}

		});

		StylesDao.get(boxer.getStyle(), new StylesResponse() {
			@Override
			public void rawReady(JSONValue val) {
				style = val.isObject();
				// styleName = newStyle;
				super.rawReady(val);
				checkLoad();
			}

		});

	}

	public String getHtml() {
		return html;
	}

	private void buildRunner() {
		final JSONObject font = boxer.infoObj.get("text_font").isObject();
		final JSONObject palette = boxer.infoObj.get("palette").isObject();

		final JSONArray styleArray = style.get("codes").isArray();
		if (styleArray == null) {
			Portable.one.fatal("loading", "Not an array:" + style.toString());
			return;
		}

		final JSONArray styleArrayCmn = commonStyle.get("codes").isArray();
		if (styleArrayCmn == null) {
			Portable.one.fatal("loading", "Not an array:" + commonStyle.toString());
			return;
		}

		JSONObject mitem = boxer.item;
		
		if (mitem.get("0") != null) {
			JSONObject repl = new JSONObject();
			repl.put("list", mitem);
			mitem = repl;
		}

		final String htmlApp = mitem.get("html_file").isString().stringValue();
		
		Map<String, String> oParams = RomTemplatingClientUtil.extractOParam();

		try {
			this.runner = new Runner(
					new RequestCreator() {

						@Override
						public MinRequest create(Runner r) {
							return new RequestClient(r);
						}
					},					
					new com.bilgidoku.rom.shared.json.JSONObject(boxer.infoObj),
					new com.bilgidoku.rom.shared.json.JSONObject(logo), new com.bilgidoku.rom.shared.json.JSONObject(
							font), new com.bilgidoku.rom.shared.json.JSONObject(palette),
					new com.bilgidoku.rom.shared.json.JSONArray(styleArray),
					new com.bilgidoku.rom.shared.json.JSONObject(mitem), boxer.allCodeRepo,
					new com.bilgidoku.rom.shared.json.JSONObject(boxer.selectedTrans),
					// new com.bilgidoku.rom.shared.json.JSONObject(viewy),
					htmlApp, null, null, new com.bilgidoku.rom.shared.json.JSONArray(styleArrayCmn),
					new com.bilgidoku.rom.shared.json.JSONObject(boxer.note), oParams);
			html = runner.render();
			Permanent.stopSpinner();
			// Window.alert(html);void
		} catch (RunException e) {
			Portable.one.fatal(e);
		}

		getDisplayTrigger().run();

	}

	public void infoChanged() {
		Permanent.startSpinner();
		// after info panel loading, this is called
		checkEditorChange();
		
		
		//TODO styles reloads !!!!! 
		final String currentStyle = boxer.infoObj.get("style").isString().stringValue();
		if (this.previewStyle != null && currentStyle.equals(this.previewStyle)) {
			preview();
		} else {
			this.style = null;
			StylesDao.get(currentStyle, new StylesResponse() {
				@Override
				public void rawReady(JSONValue val) {
					style = val.isObject();
					BoxerData.this.previewStyle = currentStyle;
					super.rawReady(val);
					checkLoad();
				}

			});
		}

	}

	public void logoChanged(com.bilgidoku.rom.shared.json.JSONObject logo1) {
		logoChanged = true;
		this.logo = logo1;		
	}

	public void checkLoad() {
		if (boxer.writing == null || boxer.infoObj == null || commonStyle == null || style == null)
			return;

		preview();

	}

	public void checkEditorChange() {
		CodeEditor ce = Portable.one.codeEditor();
		if (ce == null)
			return;

		try {
			Map<String, BoxHolder> bhs = ce.boxHolders();
			for (BoxHolder bh : bhs.values()) {
				if (!bh.isChanged())
					continue;

				JSONObject bhCodes;
				bhCodes = bh.codes();
				if (bh.tbl.equals("site.writings")) {
					if (bh.clmn.equals("spot")) {
						spotChanged(bhCodes, bh);
					} else if (bh.clmn.equals("body")) {
						bodyChanged(bhCodes, bh);
					}
				} else if (bh.tbl.equals("site.contents")) {
					// if (clmn.equals("summary")) {
					// summaryChanged(ja);
					// }
					// else if (clmn.equals("title")) {
					// titleChanged(span.getInnerText());
					// }
				} else if (bh.tbl.equals("site.info")) {
					if (bh.clmn.equals("headertext")) {
						headerTextChanged(bhCodes, bh);
					} else if (bh.clmn.equals("site_footer")) {
						siteFooterChanged(bhCodes, bh);
					}
				}

			}

		} catch (RunException e) {
			Window.alert(e.getMessage());
		}

	}

	private void headerTextChanged(JSONObject ja, BoxHolder bh) {
		this.headerTextChanged = true;
		this.headerText = ja;
		this.headerHolder = bh;
		
		ClientUtil.jsonObjPutArrAsObj(boxer.infoObj, "headertext", ja);
	}

	private void siteFooterChanged(JSONObject ja, BoxHolder bh) {
		this.footerChanged = true;
		this.siteFooter = ja;
		this.footerHolder = bh;
		
		ClientUtil.jsonObjPutArrAsObj(boxer.infoObj, "site_footer", ja);
		
	}

	// private void saveBody(JSONObject ja, String firstImg) {
	private void saveBody(JSONObject ja) {
		if (bodyChanged) {
			WritingsDao.body(boxer.pageLang, new Json(body), uri, new StringResponse() {
				@Override
				public void ready(String value) {
					bodyChanged = false;
					bodyHolder.setChanged(false);
					RomEntryPoint.one.setStatus(Ctrl.trans.saved() + ":body");
				}
			});
		}

	}

	public void spotChanged(JSONObject ja, BoxHolder bh) {
		this.spotChanged = true;
		this.spot = ja;
		this.spotHolder = bh;
		ClientUtil.jsonObjPutArrAsObj(boxer.item, "spot", ja);
	}

	private void saveSpot(JSONObject ja) {
		if (spotChanged) {
			WritingsDao.spot(boxer.pageLang, new Json(spot), uri, new StringResponse() {
				@Override
				public void ready(String value) {
					spotChanged = false;
					spotHolder.setChanged(false);
					RomEntryPoint.one.setStatus(Ctrl.trans.saved() + ":spot");
				}
			});
		}

	}

	public void bodyChanged(JSONObject ja, BoxHolder bh) {
		this.bodyChanged = true;
		this.body = ja;
		this.bodyHolder = bh;

//		if (bh != null) {
//			JSONValue icon = boxer.item.get("icon");
//			if (icon == null || icon.isNull() != null) {
//
//			}
//		}
		ClientUtil.jsonObjPutArrAsObj(boxer.item, "body", ja);
	}

	

	
	public void preview() {		
		if (previewing)
			return;
		
		checkEditorChange();
		previewing = true;		
		Permanent.startSpinner();
		cb.clearHolders();		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				previewing = false;				
				buildRunner();
			}
		});
	}

	public Runner getRunner() {
		return runner;
	}

	public void save() {
		
		checkEditorChange();

		saveSpot(this.spot);
		saveBody(this.body);
		saveLogo();
		saveHeader();
		savefooter();

		
	}


	private void savefooter() {
		if (footerChanged) {
			InfoDao.sitefooter(boxer.pageLang, new Json(siteFooter), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					footerChanged = false;
					footerHolder.setChanged(false);
					RomEntryPoint.one.setStatus(Ctrl.trans.saved() + ":footer");
				};
			});
		}

	}

	private void saveHeader() {
		if (headerTextChanged) {
			InfoDao.headertext(boxer.pageLang, new Json(headerText), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					headerTextChanged = false;
					headerHolder.setChanged(false);
					RomEntryPoint.one.setStatus(Ctrl.trans.saved() + ":header");
				};
			});
		}

	}

	private void saveLogo() {
		if (logoChanged) {			
			JSONValue lv = (JSONValue) logo.ntv;
			Json nt = new Json(lv);
			InfoDao.logo(nt, "/_/siteinfo", new StringResponse() {
				@Override
				public void ready(String value) {
					logoChanged = false;
					RomEntryPoint.one.setStatus(Ctrl.trans.saved() + ":logo");
				}
			});
		}
	}

	public boolean changed() {
		checkEditorChange();
		return spotChanged || bodyChanged || footerChanged || headerTextChanged;
	}
	

	public Runnable getDisplayTrigger() {
		return displayTrigger;
	}

	public void setDisplayTrigger(Runnable displayTrigger) {
		this.displayTrigger = displayTrigger;
	}

//	public JSONObject getSelectedTrans() {
//		return selectedTrans;
//	}

	public void setCb(BoxerDataCb boxerDataCb) {
		this.cb=boxerDataCb;
	}



}
