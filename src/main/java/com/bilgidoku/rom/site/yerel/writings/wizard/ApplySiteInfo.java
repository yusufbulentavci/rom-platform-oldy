package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsDao;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class ApplySiteInfo {
	
	public ApplySiteInfo() {
		
		final String url = URL.encode("/_local/templates/siteinfo/" + Ctrl.infoLang().substring(0, 2).toLowerCase()
				+ ".js");
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
							String resp = response.getText();
							JSONValue jsonValue = JSONParser.parseStrict(resp);
							JSONObject obj = jsonValue.isObject();
							if (obj != null) {
								objReady(obj);
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

	protected void objReady(JSONObject obj) {
		
		ContentsDao.summary(Ctrl.infoLang(), obj.get("summary").isArray().get(0).isString().stringValue(), "/_/siteinfo", new StringResponse() {
			
		});
		InfoDao.headertext(Ctrl.infoLang(), new Json(obj.get("headertext").isArray().get(0)), "/_/siteinfo", new StringResponse() {
			
		});
		InfoDao.sitefooter(Ctrl.infoLang(), new Json(obj.get("site_footer").isArray().get(0)), "/_/siteinfo", new StringResponse() {
			
		});

	}

	
}
