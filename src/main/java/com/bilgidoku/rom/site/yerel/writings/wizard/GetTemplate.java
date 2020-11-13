package com.bilgidoku.rom.site.yerel.writings.wizard;

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

public class GetTemplate {

	public GetTemplate(final ApplyTemplate caller, final String template, String lang) {
//		final String url = URL.encode("/_local/templates/" + template.replace("link", "") + "/" + lang.toLowerCase()
//				+ ".js");

		final String url = URL.encode("/_local/templates/" + template.replace("link", "") + ".js");

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
							String parentUri = caller.parentUri;
							String title = caller.title;
							if (template.equals("linklisting3")) {
								parentUri = "/_/links/public" + caller.writingUri;
							}
							
							resp = resp.replaceAll("#TITLE#", title);
							resp = resp.replaceAll("#REPLACEPARENT#", parentUri);
							resp = resp.replaceAll("#REPLACEPARENTTITLE#", caller.parentName);
							resp = resp.replaceAll("#REPLACECONTAINER#", "/_/writings" + caller.writingUri);

							
							String thumb = ApplyReadyImages.getNextReadyImage();
							String medIcon = "";
							String largeIcon = "";
							if (thumb != null && !thumb.isEmpty()) {
								medIcon = thumb.replace("_t.", "_m.");
								largeIcon = thumb.replace("_t.", "_l.");
							}

							resp = resp.replaceAll("#REPLACELARGEIMG#", largeIcon);
							resp = resp.replaceAll("#REPLACEMEDIMG#", medIcon);

							JSONValue jsonValue = JSONParser.parseStrict(resp);
							JSONObject obj = jsonValue.isObject();
							if (obj != null) {
								caller.templateReady(obj);
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
