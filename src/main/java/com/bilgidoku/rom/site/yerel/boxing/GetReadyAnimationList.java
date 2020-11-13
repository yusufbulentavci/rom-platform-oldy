package com.bilgidoku.rom.site.yerel.boxing;

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

public class GetReadyAnimationList {

	public GetReadyAnimationList(final AnimationDlg animationDlg) {
		final String url = URL.encode("/_local/etc/animations.js");

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
								animationDlg.animationsReady(obj);
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

}
