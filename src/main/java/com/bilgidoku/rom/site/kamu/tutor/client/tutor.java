package com.bilgidoku.rom.site.kamu.tutor.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.util.AsyncMethod;
import com.bilgidoku.rom.site.kamu.tutor.client.ScriptInjector.FromUrl;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.Callback;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

/*
 * 
 * 
 * http://home.mlos.net/_public/tutordebug.html?btns=v-410-90&cd=10-10&libs=abc&data=/f/docs/music/scale/music-scale-1.5.js
 * 
 * http://home.mlos.net/_public/tutordebug.html?cont=%2Ff%2Fimages%2Frastgele&sum=0-200-400-200-3-red-gray-0.7&btns=h-210-0
 * 
 * 
 * btns=input butonları nasıl görünecek, delimeter -
 * v/h-x-y
 * v-410-90
 * 
 * 
 * sum: summary params delimeter -
 * x-y-width-height-fontsize(1-7)-fontcolor-backgroundcolor-opacity
 * sum=0-200-300-200-5-red-gray-0.5
 * 
 * cd=code / notayi göstermek için 
 * cd=x-y
 * 
 * libs=abc
 * 
 * data: file bazlı çalışan
 * data=/f/docs/music/scale/music-scale-1.5.js
 * 
 * cont: container bazlı çalışan, file folder
 * cont=/f/images/rastgele
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class tutor extends RomEntryPoint {


	int canvasWidth = 500;
	int canvasHeight = 500;

	public tutor() {
		super("Rom Server Tutor Application", false, null, false, false);
	}
	

	
	@Override
	public void main() {
		final String useLibs = Location.getParameter("libs");

		if (useLibs != null && (useLibs.indexOf("abc") >= 0)) {
			FromUrl si = ScriptInjector.fromUrl("/_public/js/abcjs.js");
			si.setWindow(ScriptInjector.TOP_WINDOW);
			si.setCallback(new Callback<Void, Exception>() {
				public void onFailure(Exception reason) {
					Sistem.errln("Failed loading abc");
					readyToUp();
				}

				public void onSuccess(Void result) {
					Sistem.outln("Abc loaded");
					// tuneRender();
					readyToUp();
				}
			}).inject();
		} else {
			readyToUp();
		}
	}

	
	
	protected void drawLine(Context2d context1) {
		context1.beginPath();
		context1.moveTo(25, 0);
		context1.lineTo(0, 20);
		context1.lineTo(25, 40);
		context1.lineTo(25, 0);
		context1.setStrokeStyle(CssColor.make(255, 0, 0));
		context1.stroke();
		context1.closePath();
	}

	public void drawSomethingNew(Context2d context) {

		// Get random coordinates and sizing
		int rndX = Random.nextInt(canvasWidth);
		int rndY = Random.nextInt(canvasHeight);
		int rndWidth = Random.nextInt(canvasWidth);
		int rndHeight = Random.nextInt(canvasHeight);

		// Get a random color and alpha transparency
		int rndRedColor = Random.nextInt(255);
		int rndGreenColor = Random.nextInt(255);
		int rndBlueColor = Random.nextInt(255);
		double rndAlpha = Random.nextDouble();

		CssColor randomColor = CssColor
				.make("rgba(" + rndRedColor + ", " + rndGreenColor + "," + rndBlueColor + ", " + rndAlpha + ")");

		context.setFillStyle(randomColor);
		context.fillRect(rndX, rndY, rndWidth, rndHeight);
		context.fill();
	}

	// public static native void tuneRender() /*-{
	// console.log($wnd.ABCJS);
	// console.log(parent.ABCJS);
	// console.log(window.ABCJS);
	// }-*/;
	//

	private void readyToUp() {
		final String lng = Location.getParameter("lng");
		final String code = Location.getParameter("cd");
		final String btnsParams = Location.getParameter("btns");
		final String summaryParams = Location.getParameter("sum");
		final String data = Location.getParameter("data");
		if (data != null) {
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, data);
			try {
				builder.sendRequest(null, new RequestCallback() {

					public void onError(Request request, Throwable exception) {
						Window.alert("Couldn't retrieve JSON:" + data);
					}

					@Override
					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							String resp = response.getText();
							JSONObject jo;
							try {
								jo = Portable.one.jsonParserParseStrict(resp).isObject();
								InstrumentTutor t = (InstrumentTutor) TutorFactory.create(false, lng, jo, btnsParams,
										code, summaryParams);
								t.start();
							} catch (RunException e) {
								Window.alert("Error while parsing json");
							}

						} else {
							Window.alert("Couldn't retrieve JSON (" + response.getStatusText() + "):" + data);
						}
					}

				});
			} catch (RequestException e) {
				Window.alert("Couldn't retrieve JSON");
			}
			return;
		}

		boolean isList=true;
		String dene = Location.getParameter("list");
		if(dene==null){
			isList=false;
			dene = Location.getParameter("cont");
		}

		// String dene="/_/lists/publictutordene";
		Collector c = new Collector(lng, dene, isList, btnsParams, code, summaryParams, new AsyncMethod<InstrumentTutor, String>() {

			@Override
			public void run(InstrumentTutor param) {
				param.start();
			}

			@Override
			public void error(String param) {
			}

		});
		c.run();
	}

}
