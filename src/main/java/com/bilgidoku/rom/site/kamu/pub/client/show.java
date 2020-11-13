package com.bilgidoku.rom.site.kamu.pub.client;

import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.araci.client.rom.Dialogs;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsResponse;
import com.bilgidoku.rom.gwt.client.util.RequestClient;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.RomTemplatingClientUtil;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.shared.MinRequest;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RequestCreator;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.shared.code.CodeRepo;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.HTML;


public class show extends RomEntryPoint {
	
	public final CompInfo info=new CompInfo("+render.html", 50, new String[]{"isauth", "*html"}, new String[]{}, new String[]{});
	public final CompFactory factory=new CompFactory() {
		
		@Override
		public CompInfo info() {
			return info;
		}
		
		@Override
		public Comp create() {
			return comp;
		}
	};
	
	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}
	

	public show() {
		super("Rom Server Html Client-site Renderer Application", false, null, true, true);
	}
	
	
	private Runner runner;
	public Comp comp=new CompBase(){
		private boolean userContact;


		@Override
		public CompInfo compInfo() {
			return info;
		}
		
		public void dataChanged(String key, String val) {
			if(key.equals("isauth"))
				userContact=val!=null;
		}
		
		public void processNewState() {
			if(userContact)
				prepareCafeDlgUri();
			
		}
		
		@Override
		public boolean handle(String cmd, com.bilgidoku.rom.shared.json.JSONObject cjo) throws RunException {
			if(cmd.equals("*html")){
				runner.fireEvent(cjo);
				return true;
			}
			return true;
		}
		
		
		@Override
		public void initial(){
			try {

				JavaScriptObject notestr = hackNote();
				JSONObject note = new JSONObject(notestr);

				// if(note.isNull()!=null && note.containsKey("bw")){
				// JSONValue bwv=note.get("bw");
				// int bw=(int) bwv.isNumber().doubleValue();
				// ((PortabilityImpl)Portable.one).setBw(bw);
				// }

				// ((PortabilityImpl)Portable.one).setBw(400);

				JavaScriptObject infoStr = hackInfo();
				JSONObject info = new JSONObject(infoStr);
				JSONObject logo = info.get("logo_img").isObject();
				JSONObject font = info.get("text_font").isObject();
				JSONObject palette = info.get("palette").isObject();
				JSONObject widgetsTable = new JSONObject(hackWidgets());
				JSONObject widgets = widgetsTable.get("codes").isObject();
				JSONObject style = new JSONObject(hackStyle());
				JSONArray styleArray = style.get("codes").isArray();
				if (styleArray == null) {
					Portable.one.fatal("loading", "Not an array:" + style.toString());
					return;
				}

				JSONObject styleCmn = new JSONObject(hackStyleCommon());
				JSONArray styleArrayCmn = styleCmn.get("codes").isArray();
				if (styleArrayCmn == null) {
					Portable.one.fatal("loading", "Not an array:" + styleCmn.toString());
					return;
				}

				// JSONObject jo = new JSONObject(hackApp());
				// JSONObject apps = jo.get("codes").isObject();
				JSONObject item = new JSONObject(hackItem());
				if (item.get("0") != null) {
					item = new JSONObject(hackItem());
					JSONObject repl = new JSONObject();
					repl.put("list", item);
					item = repl;
				}
				

				// JSONValue viewVal = item.get("viewy");
				// JSONObject viewy = (viewVal == null) ?
				// info.get("viewy").isObject() : item.get("viewy").isObject();

				JSONObject trans = new JSONObject(hackTrans());
				JSONArray transArray = trans.get("title").isArray();
				if (transArray == null) {
					Portable.one.fatal("loading", "Not an array:" + trans.toString());
					return;
				}

				JSONObject selectedTrans = transArray.get(0).isObject();
				JSONValue vcns = trans.get("constants");
				if (vcns != null) {
					JSONObject cns = trans.get("constants").isObject();
					for (String key : cns.keySet()) {
						selectedTrans.put(key, cns.get(key));
					}
				}

				CodeRepo cr = new CodeRepo(new com.bilgidoku.rom.shared.json.JSONObject(widgets));
				String htmlApp = item.get("html_file").isString().stringValue();

				Map<String, String> oParams = RomTemplatingClientUtil.extractOParam();

				runner = new Runner(
						new RequestCreator() {

							@Override
							public MinRequest create(Runner r) {
								return new RequestClient(r);
							}
						},
						new com.bilgidoku.rom.shared.json.JSONObject(info),
						new com.bilgidoku.rom.shared.json.JSONObject(logo), new com.bilgidoku.rom.shared.json.JSONObject(
								font), new com.bilgidoku.rom.shared.json.JSONObject(palette),
						new com.bilgidoku.rom.shared.json.JSONArray(styleArray),
						new com.bilgidoku.rom.shared.json.JSONObject(item), cr,
						new com.bilgidoku.rom.shared.json.JSONObject(selectedTrans),
						// new com.bilgidoku.rom.shared.json.JSONObject(viewy),
						htmlApp, null, null, new com.bilgidoku.rom.shared.json.JSONArray(styleArrayCmn),
						new com.bilgidoku.rom.shared.json.JSONObject(note), oParams);
				
				try {
					String s = runner.render();
					HTML html = new HTML(s);

					RomEntryPoint.one.addToRealRootPanel(html);
					
					runner.afterRender();
					
				} catch (Exception e) {
					Portable.one.fatal(e);
				}
				
			} catch (Exception e) {
				Portable.one.fatal(e);
			}
		}
		
		
		
				
		
	};
	
	
	
	
	
	

	

	
	@Override
	protected void beforeHook() {
		
	}
	
	protected void renderCompleted() {
	}
	
	
	private boolean preparingDlg=false;
	private boolean dlgPrepared=false;
	
	public void prepareCafeDlgUri() {
		if(preparingDlg || dlgPrepared)
			return;
		preparingDlg=true;
		try {
			final String dlgUri = this.runner.evaluateText("${item.dialog_uri}");

			if (dlgUri == null)
				return;
			
			DialogsDao.get(dlgUri, new DialogsResponse() {

				@Override
				public void ready(Dialogs value) {
					if (value.cafe != null && value.cafe) {
						RomEntryPoint.com().post("*dlg.exists", "dlg", dlgUri);
					}
				}

				@Override
				public void err(int statusCode, String statusText, Throwable exception) {
					super.err(statusCode, statusText, exception);
				}
				
			});

		

		} catch (RunException e) {
			return;
		}finally{
			preparingDlg=false;
		}
	}

	
	private static native JavaScriptObject hackInfo()
	/*-{
		return $wnd.inf;
	}-*/;

	private static native JavaScriptObject hackWidgets()
	/*-{
		return $wnd.wids;
	}-*/;

	// private static native JavaScriptObject hackApp()
	// /*-{
	// return $wnd.app;
	// }-*/;

	private static native JavaScriptObject hackStyle()
	/*-{
		return $wnd.styl;
	}-*/;

	private static native JavaScriptObject hackStyleCommon()
	/*-{
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
	

	@Override
	public String evaluate(String str) throws RunException{
		return this.runner.evaluateText(str);
	}

	
}
