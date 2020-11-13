package com.bilgidoku.rom.web.html;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.dbop.Info;

/**
 *	 

og:title - The title of your object as it should appear within the graph, e.g., "The Rock".
og:type - The type of your object, e.g., "video.movie". Depending on the type you specify, other properties may also be required.
	http://ogp.me/#types
	
og:image - An image URL which should represent your object within the graph.
og:url - The canonical URL of your object that will be used as its permanent ID in the graph, e.g., 

<meta property="og:title" content="The Rock" />
<meta property="og:type" content="video.movie" />
<meta property="og:url" content="http://www.imdb.com/title/tt0117500/" />
<meta property="og:image" content="http://ia.media-imdb.com/images/rock.jpg" />


OPTIONAL:
og:audio - A URL to an audio file to accompany this object.
og:description - A one to two sentence description of your object.
og:determiner - The word that appears before this object's title in a sentence. An enum of (a, an, the, "", auto). If auto is chosen, the consumer of your data should chose between "a" or "an". Default is "" (blank).
og:locale - The locale these tags are marked up in. Of the format language_TERRITORY. Default is en_US.
og:locale:alternate - An array of other locales this page is available in.
og:site_name - If your object is part of a larger web site, the name which should be displayed for the overall site. e.g., "IMDb".
og:video - A URL to a video file that complements this object.

<meta property="og:audio" content="http://example.com/bond/theme.mp3" />
<meta property="og:description" 
  content="Sean Connery found fame and fortune as the
           suave, sophisticated British agent, James Bond." />
<meta property="og:determiner" content="the" />
<meta property="og:locale" content="en_GB" />
<meta property="og:locale:alternate" content="fr_FR" />
<meta property="og:locale:alternate" content="es_ES" />
<meta property="og:site_name" content="IMDb" />
<meta property="og:video" content="http://example.com/bond/trailer.swf" />



 *
 */
public class OpenGraphGenerator {

	public static String generate(String domainName, String url, String lang, String country, Info info, JSONObject item)
			throws KnownError, UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();

		try {

			url = optAddDomainName(domainName, url);

			addNoEncode(sb, "og:url", url);

//			// TODO: Let it works in Turkey
//			addNoEncode(sb, "og:locale", locale+"_TR");

			
			for (String it : info.langcodes) {
				if (it.equals(lang)){
					addNoEncode(sb, "og:locale", genLocale(lang, country));
				}else{
					addNoEncode(sb, "og:locale:alternate", genLocale(it, country));
				}
			}
		

			if (info.title != null && info.title.length > 0) {
				addNoEncode(sb, "og:site_name", info.title[0]);
			}

			String type = "article";
			JSONObject nesting = item.optJSONObject("nesting");
			if (nesting != null) {
				String typeAl = nesting.optString("ogtype");
				if (typeAl != null)
					type = typeAl;
			}
			addNoEncode(sb, "og:type", type);

			String image = item.optString("medium_icon");

			if (image != null) {
				image = optAddDomainName(domainName, image);
				addNoEncode(sb, "og:image", image);
			}

			JSONArray titleArray = item.optJSONArray("title");
			if (titleArray != null && titleArray.length() > 0) {
				String title = titleArray.getString(0);
				addNoEncode(sb, "og:title", title);
				addTitle(sb, title);
			}

			String audio = item.optString("sound");
			String video = item.optString("sound");
			String description = null;

			JSONArray summaryArray = item.optJSONArray("summary");
			if (summaryArray != null && summaryArray.length() > 0) {
				description = summaryArray.optString(0);
				if(description!=null)
					addNoEncode(sb, "og:description",description);
			}

		} catch (JSONException e) {
			throw new KnownError("Generating open graph", e);
		}

		return sb.toString();
	}

	protected static String genLocale(String lang, String country) {
		if(lang.equals("tr")){
			return "tr_TR";
		}
		return lang+"_US";//+country
	}

	private static String optAddDomainName(String domainName, String url) {
		// if url is null or url is not start with / and url is not empty
//		if (url == null || !((url.startsWith("/") && url.length() == 0)))
//			return url;
//		
//		if (!(url.startsWith("/") || url.equals("")))
//			return url;
//		
		
		return url = "http://www."+domainName + url;
	}

	private static void add(StringBuilder sb, String key, String value) throws UnsupportedEncodingException {
		sb.append("<meta property=\"");
		sb.append(key);
		sb.append("\" content=\"");
		sb.append(URLEncoder.encode(value, "UTF-8"));
		sb.append("\" />\n");

	}

	private static void addNoEncode(StringBuilder sb, String key, String value) throws UnsupportedEncodingException {
		sb.append("<meta property=\"");
		sb.append(key);
		sb.append("\" content=\"");
		sb.append(value);
		sb.append("\" />\n");
	}
	
	private static void addTitle(StringBuilder sb, String value) throws UnsupportedEncodingException {
		sb.append("<title>");
		sb.append(value);
		sb.append("</title>\n");
	}

}
