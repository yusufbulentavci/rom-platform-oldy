package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.site.yerel.admin.TabOrg.ContractsPanel;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.user.client.Window;

public class GetContract {

	public GetContract(final ContractsPanel contractsPanel, final String type, String lang) {
		String url = "/_static/templates/contracts/";

		if (type.equals("pic")) {
			url = url + "pic/tr.txt";
		} else if (type.equals("rs")) {
			url = url + "rs/tr.txt";
		} else {
			return;
		}

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
		try {
			builder.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					Window.alert("Couldn't retrieve");
				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						try {
							String resp = response.getText();
							contractsPanel.fileReady(resp, type);
						} catch (JSONException e) {
							Window.alert("Could not parse");
						}
					} else {
						Window.alert("Couldn't retrieve (" + response.getStatusText() + ")");
					}
				}

			});
		} catch (RequestException e) {
			Window.alert("Couldn't retrieve JSON");
		}
	}

}
