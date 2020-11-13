package com.bilgidoku.rom.site.yerel.data;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

public class DataAccess {

	public static void getContainers(DataResponse response) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, "/_/c");
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20001, e.getMessage());
		}
	}

	public static void newContainer(DataResponse response, String contName, String type) {

		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, "/_/c/new.rom");
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
		StringBuffer data = new StringBuffer("schema=site&table="+type+"&uri=" + URL.encodeQueryString("/_/" + type + "/" + contName)
				+ "&uri_prefix=" + URL.encodeQueryString("/" + contName + "/"));
		try {
			requestBuilder.sendRequest(data.toString(), responseHandler);
		} catch (RequestException e) {
			response.dataErr(20002, e.getMessage());
		}

	}

	public static void getWritings(DataResponse response, String uri) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, uri);
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20001, e.getMessage());
		}

	}

	public static void getWriting(DataResponse response, String uri) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, uri);
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20001, e.getMessage());
		}

	}

	public static void getContentList(DataResponse response, String recordUri) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, recordUri + "/content_list.rom");
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20001, e.getMessage());
		}

	}

	public static void deleteItem(DataResponse response, String uri) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.DELETE, uri);
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20002, e.getMessage());
		}

	}

	public static void getSiteInfo(DataResponse response) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, "/_/siteinfo?lang=1");
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20001, e.getMessage());
		}

	}

	public static void getLists(DataResponse response, String uri) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, uri);
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20001, e.getMessage());
		}

	}

	public static void getLinks(DataResponse response, String uri) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, uri);
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20001, e.getMessage());
		}

	}

//	public static void getTemplates(DataResponse response, String uri) {
//		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, uri);
//		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
//		requestBuilder.setHeader("Accept", "application/json");
//		try {
//			requestBuilder.sendRequest(null, responseHandler);
//		} catch (RequestException e) {
//			response.dataErr(20001, e.getMessage());
//		}
//
//	}
//
//	public static void getWidgets(DataResponse response) {
//		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, "/_/widgets/active.rom");
//		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
//		requestBuilder.setHeader("Accept", "application/json");
//		try {
//			requestBuilder.sendRequest(null, responseHandler);
//		} catch (RequestException e) {
//			response.dataErr(20001, e.getMessage());
//		}
//
//	}
//
//	public static void saveHtmlApp(DataResponse response, String uri, String name, String codes, String appGrp, String prop) {
//
//		StringBuffer postData = new StringBuffer();
//		postData.append("named=").append(URL.encode(name));
//		postData.append("&");
//		postData.append("codes=").append(URL.encode(codes));
//		postData.append("&");
//		postData.append("htmlappgroup=").append(URL.encode(appGrp));
//		postData.append("&");
//		postData.append("prop=").append(URL.encode(prop));
//
//		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, uri);
//		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
//		requestBuilder.setHeader("Accept", "application/json");
//		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
//		try {
//			requestBuilder.sendRequest(postData.toString(), responseHandler);
//		} catch (RequestException e) {
//			response.dataErr(20001, e.getMessage());
//		}
//	}
//
//	public static void saveAllWidgets(DataResponse response, String codes) {
//
//		StringBuffer postData = new StringBuffer();
//		postData.append("codes=").append(URL.encode(codes));
//
//		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, "/_/widgets/change_active.rom");
//		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
//		requestBuilder.setHeader("Accept", "application/json");
//		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
//		try {
//			requestBuilder.sendRequest(postData.toString(), responseHandler);
//		} catch (RequestException e) {
//			response.dataErr(20001, e.getMessage());
//		}
//	}

	public static void getPages(DataResponse response, String lang, String sector) {
		String sectorPrefix = sector.toLowerCase().substring(0, Math.min(4, sector.length()));
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, "pages/pages_" + sectorPrefix + "_" + lang + ".js");
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		try {
			requestBuilder.sendRequest(null, responseHandler);
		} catch (RequestException e) {
			response.dataErr(20002, e.getMessage());
		}
	}

	public static void saveDesign(DataResponse response, String contentLang, String sector, String pages) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, "/_/_initials");
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
		StringBuffer data = new StringBuffer("site_lang=");
		data.append(contentLang);
		data.append("&sector=");
		data.append(sector);
		data.append("&pages=");
		data.append(URL.encodeQueryString(pages));

		try {
			requestBuilder.sendRequest(data.toString(), responseHandler);
		} catch (RequestException e) {
			response.dataErr(20002, e.getMessage());
		}
	}

	public static void designReady(DataResponse response) {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, "/_/_initials/ready.rom");
		JSONResponseHandler responseHandler = new JSONResponseHandler(response);
		requestBuilder.setHeader("Accept", "application/json");
		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");

		try {
			requestBuilder.sendRequest("", responseHandler);
		} catch (RequestException e) {
			response.dataErr(20002, e.getMessage());
		}
	}

}
