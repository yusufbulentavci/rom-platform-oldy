package com.bilgidoku.rom.internetapi.fotolia;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

@Immutable
public class FotoliaApiResponse implements ResponseHandler<String> {

	/**
	 * Returns the response body as a String if the response was successful (a
	 * 2xx status code). If no response body exists, this returns null. If the
	 * response was unsuccessful (>= 300 status code), throws an
	 * {@link HttpResponseException}.
	 */
	public String handleResponse(final HttpResponse response)
			throws FotoliaApiException, IOException {
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		JSONObject obj;
		String error_msg;
		int error_code;

		if (statusLine.getStatusCode() != 200) {
			if (entity == null) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			} else {
				try {
					obj = new JSONObject(EntityUtils.toString(entity));
					error_msg = (String) obj.get("error");
					if (obj.get("code") != null) {
						error_code = Integer.parseInt((String) obj.get("code"));
					} else {
						error_code = statusLine.getStatusCode();
					}

				} catch (ParseException e) {
					throw new IOException("Parse exception",e);
				} catch (JSONException e) {
					throw new IOException("JSONException",e);
				}
				throw new FotoliaApiException(error_code, error_msg);
			}
		}

		return entity == null ? null : EntityUtils.toString(entity);
	}

}
