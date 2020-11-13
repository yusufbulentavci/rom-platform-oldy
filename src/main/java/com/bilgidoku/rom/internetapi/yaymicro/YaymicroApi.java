package com.bilgidoku.rom.internetapi.yaymicro;

import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.internetapi.RichWebApi;
import com.bilgidoku.rom.internetapi.api.SocialOne;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.shared.err.KnownError;

public class YaymicroApi  implements RichWebApi{
	

	private static final MC mc=new MC(YaymicroApi.class);
	
	private final String api;
	private final String search;


	private static final Astate x1=mc.c("yay-error");
	
	public YaymicroApi(JSONObject jo) throws JSONException {
		this.api=jo.getString("apiKey");
		search="http://yaymicro.com/api/search/"+api+"/";
	}
	
	@Override
	public ImageResp[] searchImage(Integer p_limit, Integer p_offset, String p_phrase, String p_size, String p_aspect,
			String p_style, String p_colors, String p_face) throws KnownError {
		return null;
	}

//
//	@Override
//	public ImageResp[] searchImage(String p_phrase, Integer p_limit, Integer p_offset, Boolean p_vector,
//			Boolean p_photo, Boolean p_illustration, Boolean p_offensive, String p_orientation, String[] p_colors,
//			String p_order, String p_age) throws KnownError {
//		
//		
//		ImageResp[] resps;
//		try {
//			URIBuilder b = new URIBuilder(search+URLEncoder.encode(p_phrase, "UTF-8")).addParameter("JSON","true");
//			if(p_limit!=null){
//				b.addParameter("search.limit", p_limit+"");
//			}
//			if(p_offset!=null){
//				b.addParameter("search.offset", p_offset+"");
//			}
//			if(p_vector!=null){
//				b.addParameter("search.vector", p_vector+"");
//			}
//			
//			JSONObject obj=remote.httpGetJSONObject(b);
//			JSONArray results = obj.getJSONArray("images");
//			if(results==null)
//				return null;
//			resps = new ImageResp[results.length()];
//			for(int i=0; i<resps.length; i++){
//				JSONObject jo=results.getJSONObject(i);
//				resps[i]=new ImageResp();
//				resps[i].id=jo.getString("id");
//				resps[i].title=jo.getString("title");
//				resps[i].thumbpath=jo.getString("thumbnail_path");
//				resps[i].thumbheight=jo.getInt("thumbnailHeight");
//				resps[i].thumbwidth=jo.getInt("thumbnailWidth");
//				resps[i].previewpath=jo.getString("preview_path");
//				resps[i].source="yay";
//				resps[i].ispaid=true;
//				resps[i].cost="1";
//				resps[i].height=resps[i].thumbheight*10;
//				resps[i].width=resps[i].thumbwidth*10;
//			}
//			return resps;
//		} catch (URISyntaxException|JSONException | UnsupportedEncodingException e) {
//			x1.more();
//			throw new KnownError(e);
//		}
//		
//	}

	@Override
	public ImageResp infoMedia(String p_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buyMedia(String p_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SocialOne socialOneAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}
}
