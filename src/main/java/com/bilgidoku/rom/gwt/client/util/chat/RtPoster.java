package com.bilgidoku.rom.gwt.client.util.chat;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.shared.util.AsyncMethodNoParam;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class RtPoster extends CompBase{

	public static final CompInfo info = new CompInfo("+rtposter", 100, null, new String[] {"netonline"}, new String[] {});
	public static final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return new RtPoster();
		}
	};
	private boolean online=false;

	
	@Override
	public CompInfo compInfo() {
		return info;
	}
	
	
	
	@Override
	public void processNewState() {
		online=RomEntryPoint.com().get("netonline")!=null;
	}


	private final static List<Runnable> whenAvailable = new ArrayList<Runnable>();
//	private static boolean online=false;

	public void postCall(final String cidTo) {
//		Utils.consoleStr("SIG:< postCall");

		whenAvailable.add(new Runnable() {
			@Override
			public void run() {
				OturumIciCagriDao.rtexchange(cidTo, "*rt:call", null, null, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						// Utils.consoleStr("Call send to cid:" + cidTo);
					}
				});
			}
		});

		checkAvailable();

	}

	public void online(boolean on){
//		online=on;
		checkAvailable();
	}
	
	private void checkAvailable() {
//		if(!online || whenAvailable.size()==0){
//			return;
//		}
		
		while(whenAvailable.size()>0){
			Runnable r=whenAvailable.remove(0);
			r.run();	
		}
		
	}

	public void postCallConfirmed(final String cidTo) {
//		Utils.consoleStr("SIG:< postCallConfirmed");

		whenAvailable.add(new Runnable() {
			@Override
			public void run() {

				OturumIciCagriDao.rtexchange(cidTo, "*rt:call_confirmed", null, null, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
//						Utils.consoleStr("--- postCallConfirmed to cid:" + cidTo);
					}

				});

			}
		});

		checkAvailable();
	}

	public void postRelay(final String cidTo, final String icmd, final JSONObject jso) {
//		Utils.consoleStr("SIG:< postRelay " + icmd);

		whenAvailable.add(new Runnable() {
			@Override
			public void run() {

				jso.put("icmd", new JSONString(icmd));

				OturumIciCagriDao.rtexchange(cidTo, "*rt:relay", null, new Json(jso), new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						// Utils.consoleLog("Relay send to cid:" + cidTo);
					}
				});

			}
		});

		checkAvailable();
	}

	public void postEndCall(final String cidTo) {
//		Utils.consoleStr("SIG:< postEndCall");

		whenAvailable.add(new Runnable() {
			@Override
			public void run() {

				OturumIciCagriDao.rtexchange(cidTo, "*rt:endcall", null, null, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						// Utils.consoleLog("End call send to cid:" + cidTo);
					}

				});

			}
		});

		checkAvailable();
	}
	
	public void postSay(final String cidTo, final String text, final AsyncMethodNoParam as) {
//		Utils.consoleStr("SIG:< postSay");

		whenAvailable.add(new Runnable() {
			@Override
			public void run() {

				OturumIciCagriDao.rtsay(cidTo, text, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						

						
						if (value)
							as.run();
						else
							as.error();
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						as.error();
					}
				});

			}
		});

		checkAvailable();
	}


	public static RtPoster one(){
		return (RtPoster)RomEntryPoint.cm().comp("+rtposter", null);
	}






}
