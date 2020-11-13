package com.bilgidoku.rom.site.kamu.a.client;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Dialogs;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsResponse;
import com.bilgidoku.rom.gwt.client.util.RequestClient;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.shared.MinRequest;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RequestCreator;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.shared.code.CodeRepo;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class a extends RomEntryPoint {
	
	public final CompInfo info=new CompInfo("+after.html", 50, new String[]{"isauth","*html"}, new String[]{}, new String[]{});
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
	

	public a() {
		super("Rom Server Based Html Engine", false, null, true, true);
	}
	
	private Runner runner;
	public Comp comp=new CompBase(){
		@Override
		public CompInfo compInfo() {
			return info;
		}
		private boolean userContact;

		public void dataChanged(String key, String val) {
			userContact=RomEntryPoint.com().getBool("isauth");
		}
		
		public void processNewState() {
			if(userContact)
				prepareCafeDlgUri();
			
		}
		
		@Override
		public void initial(){
			JSONObject widgetsTable = new JSONObject(hackWidgets());
			JSONObject widgets = widgetsTable.get("codes").isObject();
			JSONObject o = new JSONObject(hackState());

			JSONObject trans = new JSONObject(hackTrans());
			JSONArray transArray = trans.get("title").isArray();
			if (transArray == null) {
				Portable.one.error("loading", "Not an array:" + trans.toString());
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

			try {
				CodeRepo cr = new CodeRepo(new com.bilgidoku.rom.shared.json.JSONObject(widgets));
				runner = new Runner(new RequestCreator() {

					@Override
					public MinRequest create(Runner r) {
						return new RequestClient(r);
					}
				},

				new com.bilgidoku.rom.shared.json.JSONObject(o), cr,
						new com.bilgidoku.rom.shared.json.JSONObject(selectedTrans), null, null);

				/**
				 * Session login callback'ten once renderCompletedIn'e girmesi iyi degil. Geciktirdik...
				 */
				Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
					
					@Override
					public boolean execute() {
						renderCompletedIn();
						return false;
					}
				}, 5000);
				
				runner.afterRender();

			} catch (RunException e) {
				Portable.one.fatal(e);
			}
		}
		
		@Override
		public boolean handle(String cmd, com.bilgidoku.rom.shared.json.JSONObject cjo) throws RunException {
			if(cmd.equals("*html")){
				runner.fireEvent(cjo);
				return true;
			}
			return true;
		}
		
	};
	
	

	


	private boolean preparingDlg=false;
	private boolean dlgPrepared=false;
	


	protected void renderCompleted() {
	}
	

//	public void prepareCafeDlgUri() {
//		try {
//			String dlgUri = runner.evaluateText("${item.dialog_uri}");
//
//			if (dlgUri == null)
//				return;
//
//			DialogsDao.get(dlgUri, new DialogsResponse() {
//
//				@Override
//				public void ready(Dialogs value) {
//					if (value.cafe != null && value.cafe) {
//						ActionBar bar=(ActionBar) RomEntryPoint.cm().comp("+actionbar", null);
//						if(bar!=null)
//							bar.enableCafe(value.uri);
//					}
//				}
//
//				@Override
//				public void err(int statusCode, String statusText, Throwable exception) {
//					super.err(statusCode, statusText, exception);
//				}
//				
//			});
//
//		} catch (RunException e) {
//			return;
//		}
//	}

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

	

	private static native JavaScriptObject hackState()
	/*-{
		return $wnd.st;
	}-*/;

	private static native JavaScriptObject hackWidgets()
	/*-{
		return $wnd.wids;
	}-*/;

	private static native JavaScriptObject hackTrans()
	/*-{
		return $wnd.trns;
	}-*/;


	@Override
	public String evaluate(String str) throws RunException{
		return this.runner.evaluateText(str);
	}
	
	
	

}
