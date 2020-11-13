package com.bilgidoku.rom.internetapi.bing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.internetapi.RichWebApi;
import com.bilgidoku.rom.internetapi.api.SocialOne;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;
/**
 * A simple example that uses HttpClient to execute an HTTP request against
 * a target site that requires user authentication.
 * 
 * 
{"d":{"results":[

{
"__metadata":{
	"uri":"https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Image?Query=\u0027dinosour\u0027&$skip=0&$top=1",
	"type":"ImageResult"
	},
"ID":"369068f8-b792-4d50-8082-b56818583325",
"Title":"Dinosaur Royalty Free Stock Photo, Pictures, Images And Stock ...",
"MediaUrl":"http://us.123rf.com/400wm/400/400/sainaniritu/sainaniritu0603/sainaniritu060300048/366556-dinosaur.jpg",
"SourceUrl":"http://www.123rf.com/photo_366556_dinosaur.html","DisplayUrl":"www.123rf.com/photo_366556_dinosaur.html",
"Width":"900",
"Height":"1200",
"FileSize":"316973",
"ContentType":"image/jpeg",
"Thumbnail":{
	"__metadata":{"type":"Bing.Thumbnail"},
		"MediaUrl":"http://ts4.mm.bing.net/images/thumbnail.aspx?q=5058063409939579&id=235c1fe187edcc677d896ab0b10c55ff",
		"ContentType":"image/jpg",
		"Width":"120",
		"Height":"160",
		"FileSize":"5084"
		}
},...

]}}





Size:Small
Size:Medium
Size:Large
Size:Height:<Height>
Of the specified height in pixels, where <Height> is an unsigned int value.
Size:Width:<Width>
Of the specified width in pixels, where <Width> is an unsigned int value.

Aspect:Square
Aspect:Wide
Aspect:Tall

Color:Color
Color:Monochrome
	

Style:Photo
Style:Graphics

Face:Face
That contain faces.
Face:Portrait
That contain portraits (head and shoulders).
Face:Other

 */


public class BingApi implements RichWebApi{
	
	private static final MC mc=new MC(BingApi.class);
	
	private final String accountKey;
	
	
	public static void main(String[] args) throws JSONException, IOException, KnownError {
		BingApi ba=new BingApi();
		ImageResp[] s = ba.searchImage(10, null, "soccer", null, null, null, "Monochrome", null);
		for (ImageResp imageResp : s) {
			System.out.println(imageResp.uri);
		}
	}
//	
	private static final Astate x1=mc.c("bing");

	public BingApi() throws JSONException {
		accountKey="EejK7ONM+MFQG3f82/nZojFIOuoh00Nj6CqS8P0M3jw=";
	}
	
	public BingApi(JSONObject jo) throws JSONException {
		accountKey=jo.getString("accountKey");
	}

	@Override
	public ImageResp[] searchImage(
			Integer p_limit,
			Integer p_offset, 
			String p_phrase, 
			String p_size,
			String p_aspect,
			String p_style,
			String p_colors,
			String p_face
			) throws  KnownError {
		x1.more();
		boolean failed=true;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		InputStreamReader sr=null;
		try {
			HttpHost targetHost = new HttpHost("api.datamarket.azure.com", 443,
					"https");
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(targetHost.getHostName(),
							targetHost.getPort()),
					new UsernamePasswordCredentials(
							accountKey,
							accountKey));

			AuthCache authCache = new BasicAuthCache();
			// Generate BASIC scheme object and add it to the local
			// auth cache
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(targetHost, basicAuth);

			// Add AuthCache to the execution context
			BasicHttpContext localcontext = new BasicHttpContext();
			localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

			StringBuilder sb=new StringBuilder("https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Image?$format=JSON&$top=");
			sb.append(p_limit);
			if(p_offset!=null){
				sb.append("&$skip=");
				sb.append(p_offset);
			}
			boolean first=false;
			if(p_size!=null){
				first=true;
				sb.append("&ImageFilters=%27");
				sb.append("Size%3a");
				sb.append(p_size);
			}
			
			if(p_colors!=null){
				if(!first){
					sb.append("&ImageFilters=%27");
					first=true;
				}else{
					sb.append("%2b");					
				}
				sb.append("Color%3a");
				sb.append(p_colors);
			}
			
			if(p_style!=null){
				if(!first){
					sb.append("&ImageFilters=%27");
					first=true;
				}else{
					sb.append("%2b");					
				}
				sb.append("Style%3a");
				sb.append(p_style);
			}
			
			if(p_aspect!=null){
				if(!first){
					sb.append("&ImageFilters=%27");
					first=true;
				}else{
					sb.append("%2b");
				}
				sb.append("Aspect%3a");
				sb.append(p_aspect);
			}
			
			if(p_face!=null){
				if(!first){
					sb.append("&ImageFilters=%27");
					first=true;
				}else{
					sb.append("%2b");					
				}
				sb.append("Face%3a");
				sb.append(p_face);
			}
			if(first)
				sb.append("%27");
			
			sb.append("&Query=%27");
			sb.append(URLEncoder.encode(p_phrase, "UTF-8"));
			sb.append("%27");
			
//			syso(sb.toString());
			
			HttpGet httpget = new HttpGet(sb.toString());

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				// dnc.more(uri);
				throw new KnownError().notFound("bingsearch");
			}
			// fos = new FileOutputStream(file);
			// syso("----------------------------------------");
			// syso(response.getStatusLine());
			if (entity != null) {
//				syso("Response content length: "
//						+ entity.getContentLength());
			}
			InputStream instream = entity.getContent();
			sr = new InputStreamReader(instream);
			sb = new StringBuilder();
			int l;
			char[] tmp = new char[2048];
			while ((l = sr.read(tmp)) != -1) {
				sb.append(tmp, 0, l);
			}
//			syso(sb.toString());
			JSONObject obj = new JSONObject(sb.toString());
			JSONObject d = obj.getJSONObject("d");
			JSONArray results = d.getJSONArray("results");

			if(results==null||results.length()==0)
				return new ImageResp[0];
			
			ImageResp[] resps=new ImageResp[results.length()];
			for (int i = 0; i < results.length(); i++) {
				 ImageResp r=new ImageResp();
				 JSONObject img = results.getJSONObject(i);
				 
				 
				 
			    r.title=img.getString("Title");
			    r.id=img.getString("ID");
			    r.source=img.getString("SourceUrl");

			    r.ispaid=false;
			    r.uri=img.getString("MediaUrl");
			    r.height=img.getInt("Height");
			    r.width=img.getInt("Width");
			    
			    img = img.getJSONObject("Thumbnail");
			    r.previewpath=img.getString("MediaUrl");
			    r.thumbpath=img.getString("MediaUrl");
			    r.thumbheight=img.getInt("Height");
			    r.thumbwidth=img.getInt("Width");
			    r.filesize=img.getLong("FileSize");
			    resps[i]=r;
				
				
			}
			failed=false;
			return resps;
			// EntityUtils.consume(entity);
		} catch (IllegalStateException | IOException | JSONException e) {
			throw new KnownError(e);
		} finally {
			if(failed)
				x1.failed();
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
			if(sr!=null)
				try {
					sr.close();
				} catch (IOException e) {
					Sistem.printStackTrace(e);
				}
		}
	}

	@Override
	public ImageResp infoMedia(String p_id) {
		throw new RuntimeException("Bing doesnt serve info for image");
	}

	@Override
	public void buyMedia(String p_id) {
		throw new RuntimeException("Bing doesnt sell image");
	}

	@Override
	public SocialOne socialOneAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}
}
