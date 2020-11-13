package com.bilgidoku.rom.internetapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;

public class InternetApiGorevlisi extends GorevliDir{
	public static final int NO=20;
	
	public static InternetApiGorevlisi tek(){
		if(tek==null) {
			synchronized (InternetApiGorevlisi.class) {
				if(tek==null) {
					tek=new InternetApiGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static InternetApiGorevlisi tek;
	private InternetApiGorevlisi() {
		super("InternetApi", NO);
	}
	

	
	public void selfDescribe(JSONObject jo) {
	}

	
	public String httpGet(URIBuilder build) throws KnownError {
		destur();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget;
		try {
			httpget = new HttpGet(build.build());
		} catch (URISyntaxException e1) {
			throw err(e1);
		}
		try (CloseableHttpResponse response = httpclient.execute(httpget)) {
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KnownError("HttpGet failed: Uri:" + build.toString() + "\n Response:" + response.toString());
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				throw new KnownError().temporary();
			}
			InputStream instream = entity.getContent();
			InputStreamReader sr = new InputStreamReader(instream);
			StringBuilder sb = new StringBuilder();
			int l;
			char[] tmp = new char[2048];
			while ((l = sr.read(tmp)) != -1) {
				sb.append(tmp, 0, l);
			}

			return sb.toString();
		} catch (IOException e) {
			throw err(e);
		}

	}

	
	public JSONObject httpGetJSONObject(URIBuilder build) throws KnownError {
		destur();
		try {
			return new JSONObject(httpGet(build));
		} catch (JSONException e) {
			throw err(e);
		}

	}

	// 
	// public String httpGet(URIBuilder build) {
	// destur();
	//
	// try (CloseableHttpClient chc=HttpClientBuilder.create().build();){
	// HttpHost targetHost = new HttpHost(url.getHost(), 443, "https");
	//
	// UriBuilder post = new UriBuilder(domainCheck);
	// post.setParameter("ApiKey", apiKey);
	// post.setParameter("Password", bsPassword);
	// post.setParameter("ResponseFormat", "JSON");
	// post.setParameter("Domain", domainName);
	//
	// try(CloseableHttpResponse resp = chc.execute(targetHost, post.get());){
	// if(resp.getStatusLine().getStatusCode()!=200){
	// throw new KnownError("Domain check failed for "+domainName);
	// }
	// HttpEntity entity = resp.getEntity();
	// if (entity == null) {
	// _domainCheck.log("noresponse");
	// throw new KnownError().temporary();
	// }
	// InputStream instream = entity.getContent();
	// InputStreamReader sr = new InputStreamReader(instream);
	// StringBuilder sb = new StringBuilder();
	// int l;
	// char[] tmp = new char[2048];
	// while ((l = sr.read(tmp)) != -1) {
	// sb.append(tmp, 0, l);
	// }
	// JSONObject obj = new JSONObject(sb.toString());
	// return new DomainAvailability(obj);
	// }
	//
	// } catch (IOException | KnownError | JSONException e) {
	// _domainCheck.failed(e,domainName);
	// throw err(e);
	// }
	// return null;
	// }

}
