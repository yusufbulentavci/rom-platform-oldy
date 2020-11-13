package com.bilgidoku.rom.web.http;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.CallInteraction;
import com.bilgidoku.rom.pg.dict.CommonRequestHandler;
import com.bilgidoku.rom.pg.dict.CommonSession;
import com.bilgidoku.rom.pg.dict.Format;
import com.bilgidoku.rom.pg.dict.MethodInteract;
import com.bilgidoku.rom.pg.dict.Net;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.Postman;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.web.cagridagitma.GetDbCallInteraction;
import com.bilgidoku.rom.web.cookie.CookieGorevlisi;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

public class RomHttpResponse extends MethodInteract implements CallInteraction {
	private static final MC mc = new MC(RomHttpResponse.class);
	
	
	private final CommonRequestHandler handler;
	private final HttpRequest request;
	
	private Runner runner;
	private String reqLang;

	public RomHttpResponse(CommonRequestHandler handler, HttpRequest request,
			String rawUri, QueryStringDecoder uri, List<InterfaceHttpData> datas, Format format) {
		super(handler.getDomain(), handler.getSession(), rawUri, uri, datas, request.getMethod(), format);
		this.handler = handler;
		this.request = request;
		CommonSession ses = getSession();
		setRid(GunlukGorevlisi.tek().request(ses.getIpAddress(),handler.getDomain().getHostId(),ses.getCid(),"http", uri.path()));
	}

	// public RomDomain getDomain() {
	// return this.handler.getDomain();
	// }


	public void sendFile(File file, Long cache) throws KnownError, KnownError {
		this.handler.sendFile(file, getUri(), request, false, cache);
	}

	void requestSatisfied(boolean suc, String desc) {
		GunlukGorevlisi.tek().response(getRid(), suc, desc);
	}

	public void sendFile(File file, String fileName, Long cache)
			throws KnownError, KnownError {
		this.handler.sendFile(file, fileName, request, false, cache);
	}

	public void sendAttach(File file, String fileName, Long cache)
			throws KnownError, KnownError {
		this.handler.sendFile(file, fileName, request, true, cache);
	}

	public void sendNotModified() throws KnownError {
		this.handler.sendNotModified();
	}

	public void sendResponse(StringBuilder buf, Date ts, Long cache)
			throws KnownError {
		sendResponse(buf.toString(), ts, cache);
	}

	public void sendResponse(String buf, Date ts, Long cache) throws KnownError {
		this.handler.sendResponse(buf, request, ts, cache);
	}

	public void setNet(Net net2) {
		this.net = net2;
	}

	public void sendResponse(String string, String contenttype, Date ts,
			Long cache) throws KnownError {
		this.handler.sendResponse(string, request, contenttype, ts, cache);
	}


	public void setResource(String resource) {
		this.resourceId = resource;
	}

	public Date getHeaderIfModifiedSince() {
		try {
			return HttpHeaders.getDateHeader(request, "If-Modified-Since");
		} catch (ParseException e) {
			return null;
		}
	}

	public void redirectLogin() throws KnownError {
		handler.redirectLogin(request.uri());
	}

	@Override
	public int getBw() {
		return runner.getBw();
	}

	@Override
	public JSONArray getLangs() {
		return runner.getLangs();
	}

	@Override
	public Postman getPostman() {
		return new GetDbCallInteraction(this);
	}

	@Override
	public boolean isInitial() {
		return true;
	}
	

	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	public void setReqLang(String string) {
		this.reqLang=string;
	}
	
	
	private static final Astate langValError = mc.c("lang-val-error");
	
	@Override
	public String getReqLang() {
		if(this.reqLang!=null){
			return this.reqLang;
		}
		String lang;
		try {
			lang = getParam("lng", null, null, false);
		} catch (ParameterError e) {
			throw new RuntimeException(e);
		}
		if (lang == null) {
			Cookie c=getCookie();
			if(c!=null ){
				lang = c.getCookieLang();	
			}
			if(lang==null){
				lang=getSession().getCountryLang();
			}
			
			if(lang==null){
				lang="tr";
			}
			
		}
		if(lang!=null && !(lang.equals("en") || lang.equals("tr"))){
			try {
				Cookie c=getCookie();
				langValError.more("", "lang", lang, "param", getParam("lng", null, null, false), 
						"country", getSession().getCountryLang(), "cookie", c==null?null:c.getCookieLang());
				
			} catch (ParameterError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lang;
	}

	


	@Override
	public boolean isACookie(String name) {
		return CookieGorevlisi.tek().isACookie(name);
	}

	

}
