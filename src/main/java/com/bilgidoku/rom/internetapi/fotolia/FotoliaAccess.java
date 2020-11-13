package com.bilgidoku.rom.internetapi.fotolia;

import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.internetapi.RichWebApi;
import com.bilgidoku.rom.internetapi.api.SocialOne;
import com.bilgidoku.rom.shared.err.KnownError;

public class FotoliaAccess implements RichWebApi {

	// LOGIN
	private final String fotoliauser;
	private final String fotoliapass;
	private final String apiKey;

	private FotoliaApi api;

	public FotoliaAccess(JSONObject jo) throws JSONException {
		fotoliauser = jo.getString("user");
		fotoliapass = jo.getString("pass");
		apiKey = jo.getString("apiKey");
	}

	private void check() throws JSONException {
		if (api != null && !api.test()) {
			api = null;
		}

		if (api == null) {
			api = new FotoliaApi(apiKey);
			api.loginUser(fotoliauser, fotoliapass);
		}
	}

	@Override
	public ImageResp[] searchImage(Integer p_limit, Integer p_offset, String p_phrase, String p_size, String p_aspect,
			String p_style, String p_colors, String p_face) throws KnownError {
		return null;
	}
//
//	
//	@Override
//	public ImageResp[] searchImage(String p_phrase, Integer p_limit, Integer p_offset, Boolean p_vector,
//			Boolean p_photo, Boolean p_illustration, Boolean p_offensive, String p_orientation, String[] p_colors,
//			String p_order, String p_age) throws KnownError {
//		try {
//			check();
//			FotoliaSearchQuery q = new FotoliaSearchQuery(p_phrase);
//			if (p_limit != null)
//				q.setLimit(p_limit);
//			if (p_offset != null)
//				q.setOffset(p_offset);
//			if (p_vector != null)
//				q.setVectorFilter(p_vector);
//			if (p_photo != null)
//				q.setPhotoFilter(p_photo);
//			if (p_illustration != null)
//				q.setIllustrationFilter(p_illustration);
//
//			JSONObject obj = api.getSearchResults(q);
//
//			obj.remove("nb_results");
//			Iterator<String> keys = obj.keys();
//			ImageResp[] resps = new ImageResp[obj.length()];
//			int i = 0;
//			while (keys.hasNext()) {
//				String key = (String) keys.next();
//				JSONObject jo = obj.getJSONObject(key);
//				resps[i] = new ImageResp();
//				resps[i].id = jo.getString("id");
//				resps[i].title = jo.getString("title");
//				resps[i].thumbpath = jo.getString("thumbnail_url");
//				resps[i].thumbheight = jo.getInt("thumbnail_heightt");
//				resps[i].thumbwidth = jo.getInt("thumbnail_width");
//				// resps[i].previewpath=jo.getString("preview_path");
//				resps[i].source = "fotolia";
//				resps[i].ispaid = true;
//				resps[i].cost = "1";
//				i++;
//			}
//			return resps;
//		} catch (JSONException e) {
//			throw new KnownError(e);
//		}
//	}

	@Override
	public ImageResp infoMedia(String p_id) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void buyMedia(String p_id) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public SocialOne socialOneAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}


	//	private JSONObject fotoliagetmediacall(String p_id, String p_license) throws RomRequestException, IOException, JSONException {
	//		return null;
	//		DefaultHttpClient httpclient = new DefaultHttpClient();
	//		InputStreamReader sr = null;
	//		try {
	//			StringBuilder sb = new StringBuilder(getFotoliaSessionedPrefix()+getmedia);
	//		
	//			sb.append(p_id);
	//			sb.append("&license_name=");
	//			sb.append(p_license);
	//			
	//			syso("fotolia getmedia");
	//			HttpGet httpget = new HttpGet(sb.toString());
	//			
	//			HttpResponse response = httpclient.execute(httpget);
	//			HttpEntity entity = response.getEntity();
	//			if (entity == null) {
	//				throw new RomRequestException(HttpResponseStatus.NOT_FOUND);
	//			}
	//			
	//			if (entity != null) {
	//				syso("Response content length: " + entity.getContentLength());
	//			}
	//			InputStream instream = entity.getContent();
	//			sr = new InputStreamReader(instream);
	//			sb = new StringBuilder();
	//			int l;
	//			char[] tmp = new char[2048];
	//			while ((l = sr.read(tmp)) != -1) {
	//				sb.append(tmp, 0, l);
	//			}
	//			syso(sb.toString());
	//			return new JSONObject(sb.toString());
	//		} catch (ClientProtocolException e) {
	//			x1.more();
	//			throw new UnexpectedStateException(e);
	//		} catch (IOException e) {
	//			x1.more(e);
	//			throw e;
	//		} catch (JSONException e) {
	//			x1.more(e);
	//			throw e;
	//		} finally {
	//			// When HttpClient instance is no longer needed,
	//			// shut down the connection manager to ensure
	//			// immediate deallocation of all system resources
	//			httpclient.getConnectionManager().shutdown();
	//			if (sr != null)
	//				try {
	//					sr.close();
	//				} catch (IOException e) {
	//					Sistem.printStackTrace(e);
	//				}
	//		}
	//	}

	//	@HttpCallMethod(http = "post")
	//	public String buymedia(
	//			@hcp(n = "a_host") Integer a_host,
	//			@hcp(n = "a_lang") String a_lang,
	//			@hcp(n = "p_id") String p_id,
	//			@hcp(n = "p_license") String p_license) throws IOException, RomRequestException, JSONException, SQLException {
	//		
	//		if(p_id==null)
	//			return null;
	//		
	//		FotoliaApi api=fotoliaLogin();
	//		
	//		api.getMediaData(p_id);
	//		
	//		if(!KurumGorevlisi.tek().buyFotoliaImage(a_host, p_id))
	//			return null;
	//
	//		JSONObject jo=fotoliagetmediacall(p_id, p_license);
	//		
	//		Assert.errorNull(jo, "fotoliagetmedia call result");
	//		
	//		String uri=jo.getString("uri");
	//		Assert.errorNull(uri, "fotoliagetmedial result uri");
	//		String name=jo.getString("name");
	//		Assert.errorNull(uri, "fotoliagetmedial result name");
	//		
	//		name=name.replaceFirst("Fotolia", "foto").toLowerCase();
	//		return hostingFileService.fotoliaGetMedia(a_host, a_lang, uri, name);
	//	}

}
