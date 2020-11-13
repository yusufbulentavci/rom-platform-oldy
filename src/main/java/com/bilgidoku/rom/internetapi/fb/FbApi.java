package com.bilgidoku.rom.internetapi.fb;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.internetapi.InternetApiGorevlisi;
import com.bilgidoku.rom.internetapi.RichWebApi;
import com.bilgidoku.rom.internetapi.api.SocialOne;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;

public class FbApi implements RichWebApi {

	


	@Override
	public ImageResp infoMedia(String p_id) throws KnownError {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buyMedia(String p_id) throws KnownError {
		// TODO Auto-generated method stub

	}

	@Override
	public SocialOne socialOneAccessToken(String accessToken) {
		
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpGet httpget = new HttpGet("https://graph.facebook.com/me/?access_token="+accessToken);
//		try (CloseableHttpResponse response= httpclient.execute(httpget)){
//			try {
//				System.out.println(response.toString());
//			} finally {
//				try {
//					response.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			JSONObject jo=InternetApiGorevlisi.tek().httpGetJSONObject(new URIBuilder("https://graph.facebook.com/me").addParameter("access_token", accessToken));
			
			if(jo==null)
				return null;
			System.out.println(jo.toString());
			if(jo.has("error")){
				Sistem.outln("Fb.socialOneAccessToken failed:" + jo.toString());
			}
			
			SocialOne so=new SocialOne();
			so.id=jo.optString("id");
			so.email=jo.optString("email");
			so.firstName=jo.optString("name");
			so.lastName=jo.optString("last_name");
			so.link=jo.optString("link");
			so.locale=jo.optString("locale");
			so.timeZone=jo.optString("timezone");
			so.verified=jo.optBoolean("verified", false);
			
			return so;
			
		} catch (KnownError e) {
			Sistem.printStackTrace(e);
		} catch (URISyntaxException e) {
			Sistem.printStackTrace(e);
		}

		return null;
	}

	@Override
	public ImageResp[] searchImage(Integer p_limit, Integer p_offset, String p_phrase, String p_size, String p_aspect,
			String p_style, String p_colors, String p_face) throws KnownError {
		return null;
	}

}
