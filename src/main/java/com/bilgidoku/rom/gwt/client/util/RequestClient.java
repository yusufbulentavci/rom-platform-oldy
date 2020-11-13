package com.bilgidoku.rom.gwt.client.util;

import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.MinRequest;
import com.bilgidoku.rom.shared.Postman;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.google.gwt.user.client.Cookies;

public class RequestClient implements MinRequest{
	

	private final Runner runner;

	public RequestClient(Runner runner){
		this.runner=runner;
	}

	@Override
	public String getReqLang() {
		return runner.getLang();
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
		return new PostmanClient(getReqLang());
	}

	@Override
	public boolean isInitial() {
		return false;
	}

	@Override
	public Cookie getCookie() {
		return new Cookie(){

			@Override
			public String getSid() {
				return Cookies.getCookie("sid");
			}


			


			@Override
			public String getRoles() {
				return Cookies.getCookie("roles");
			}

			@Override
			public String getCookieDomainName() {
				return null;
			}

			@Override
			public void cookieSent() {
			}

			@Override
			public boolean cookieDirty() {
				return false;
			}

			@Override
			public String getCname() {
				return Cookies.getCookie("cname");
			}


			@Override
			public String getCid() {
				return Cookies.getCookie("cid");
			}


			@Override
			public String getCookieLang() {
				return Cookies.getCookie("lang");
			}


			@Override
			public String getCookieHostName() {
				return Cookies.getCookie("host");
			}


			@Override
			public String getCookiePresence() {
				return Cookies.getCookie("presence");
			}


			@Override
			public boolean getCookieAuth() {
				String s = Cookies.getCookie("isauth");
				return s!=null && s.equals("1");
			}


			@Override
			public String getCookieUser() {
				return Cookies.getCookie("user");
			}

		};
	}

	@Override
	public boolean isACookie(String name) {
		return name.equals("cid")||name.equals("sid")||name.equals("lang")||name.equals("cname")||name.equals("roles")||name.equals("host")||name.equals("presence")||name.equals("isauth");
	}


}
