package com.bilgidoku.rom.site.yerel;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.site.Info;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.araci.client.site.InfoResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsResponse;
import com.bilgidoku.rom.gwt.client.util.PortabilityImpl;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.panels.StatusBar;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.CodeRepo;
import com.bilgidoku.rom.site.yerel.boxing.BoxerData;
import com.bilgidoku.rom.site.yerel.boxing.BoxerGui;
import com.bilgidoku.rom.site.yerel.boxing.Permanent;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class boxer extends RomEntryPoint {
	public static final CompInfo info = new CompInfo("+boxer", 30, new String[] {},
			new String[] {"user"}, null);

	private CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return comp;
		}

	};

	private CompBase comp = new CompBase() {
		
		
		public void resolve(){
			super.resolve();
			getInfo();
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

	};
	
	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}

	

	private Permanent permanent;
	public static StatusBar status = StatusBar.getOne();
	public static CodeRepo allCodeRepo;

	public static Writings writing;
	public static JSONObject item;
	public static JSONObject infoObj;
	public static JSONObject note;
	public static String currentLocale;
	
	public static JSONObject selectedTrans;

	public static String pageLang = null;

	public boxer() {
		super("Rom Server Collage/Web Page Design Application", true, Location.getPath(), true, true);
		currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();		
		pageLang = Location.getParameter("pagelang");
	}

	
	



	private Widget getLoading() {
		SimplePanel sp = new SimplePanel();
		sp.getElement().setId("loading");
		sp.add(new Image("/_local/images/common/loading.png"));
		return sp;
	}

	private void getInfo() {
		InfoDao.get(pageLang, "/_/siteinfo", new InfoResponse() {
			@Override
			public void rawReady(JSONValue val) {
				Ctrl.setInfoObj(val.isObject());
				infoObj = val.isObject();

				super.rawReady(val);
			}

			@Override
			public void ready(Info value) {
				Ctrl.setInfo(value);
				// info = value;

				if (pageLang == null || pageLang.isEmpty())
					pageLang = value.langcodes[0];

				start();

			}
		});
	}

	protected void start() {

		JavaScriptObject notestr = hackNote();
		note = new JSONObject(notestr);

		final JSONObject widgetsTable = new JSONObject(hackWidgets());
		final JSONObject widgets = widgetsTable.get("codes").isObject();

		JSONObject trans = new JSONObject(hackTrans());
		JSONArray transArray = trans.get("title").isArray();
		if (transArray == null) {
			Portable.one.fatal("loading", "Not an array:" + trans.toString());
			return;
		}

		selectedTrans = (transArray.get(0).isObject());
		JSONValue vcns = trans.get("constants");
		if (vcns != null) {
			JSONObject cns = trans.get("constants").isObject();
			for (String key : cns.keySet()) {
				selectedTrans.put(key, cns.get(key));
			}
		}
		try {
			allCodeRepo = new CodeRepo(new com.bilgidoku.rom.shared.json.JSONObject(widgets));
		} catch (RunException e) {
			Sistem.printStackTrace(e);
		}

		final BoxerData previewPage = new BoxerData(uri);

		permanent = new Permanent(previewPage);

		((PortabilityImpl) Portable.one).codeEditor(permanent.codeEditor);

		WritingsDao.get(pageLang, uri, new WritingsResponse() {

			public void rawReady(JSONValue val) {
				item = val.isObject();
				super.rawReady(val);
			}

			@Override
			public void ready(Writings value) {
				writing = value;
				permanent.setGui(new BoxerGui(permanent.getGuiCb(), uri));
				Window.setTitle(value.title[0]);
				previewPage.init();
			}
		});

	}
	private static native JavaScriptObject hackInfo()
	/*-{
		return $wnd.inf;
	}-*/;

	private static native JavaScriptObject hackWidgets()
	/*-{
		return $wnd.wids;
	}-*/;

	private static native JavaScriptObject hackStyle()
	/*-{
		return $wnd.styl;
	}-*/;

	private static native JavaScriptObject hackStyleCommon()
	/*-{
		helpImg
		return $wnd.stylcmn;
	}-*/;

	private static native JavaScriptObject hackItem()
	/*-{
		return $wnd.itm;
	}-*/;

	private static native JavaScriptObject hackTrans()
	/*-{
		return $wnd.trns;
	}-*/;

	private static native JavaScriptObject hackNote()
	/*-{
		return $wnd.note;
	}-*/;

	public static String getTranslation(String replace) {
		if (replace == null)
			return "";

		String label = replace;
		if (selectedTrans != null && selectedTrans.get(replace) != null) {
			label = ClientUtil.getString(selectedTrans.get(replace));
		}
		return label;
	}

	
	private int selectedPageIndex = 0;

	public static boolean getECommerce() {
		return ClientUtil.getBoolean(boxer.infoObj.get("ecommerce"));
	}

	public static String getStyle() {
		return ClientUtil.getString(boxer.infoObj.get("style"));
	}


	public void setStatus(String text) {
		status.setStatus(text);
	}


	public static void writingChanged(final Runnable callback) {
		WritingsDao.get(boxer.writing.langcodes[0], boxer.writing.uri, new WritingsResponse() {
			@Override
			public void ready(Writings value) {
				boxer.writing = value;
				callback.run();
			}
		});		
	}

}
