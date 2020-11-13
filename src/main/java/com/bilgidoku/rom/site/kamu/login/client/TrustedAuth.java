package com.bilgidoku.rom.site.kamu.login.client;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.service.YetkilendirmeDao;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.gwt.client.util.com.IdReady;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.kamu.login.client.oauth.Auth;
import com.bilgidoku.rom.site.kamu.login.client.oauth.AuthRequest;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

public class TrustedAuth {

	private static final Auth AUTH = Auth.get();

	private static final String FACEBOOK_AUTH_URL = "https://www.facebook.com/dialog/oauth";

	private static String fbClientId = null;// = "896530857042888"

	private static final String FACEBOOK_EMAIL_SCOPE = "email";


	public static void fbLogin() {
		getFbId(new IdReady() {
			@Override
			public void idReady(String fbid) {
				final AuthRequest req = new AuthRequest(FACEBOOK_AUTH_URL, fbid).withScopes(FACEBOOK_EMAIL_SCOPE)
						.withScopeDelimiter(",");
				String redirect=Window.Location.getParameter("redirect");
				AUTH.redirect(req, redirect);
			}

		});

	}

	public static void getFbId(final IdReady ir) {
		if (fbClientId != null) {
			ir.idReady(fbClientId);
			return;
		}
		InitialsDao.getfbappid("/_/_initials", new StringResponse() {
			@Override
			public void ready(String value) {
				fbClientId = value;
				ir.idReady(fbClientId);
			}
		});
	}

	protected static void trustedLogin(String token) {
		YetkilendirmeDao.trustedplatformlogin("facebook", token, new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				done(true);
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				Sistem.errln("Trusted login failed."+statusText);
				Sistem.printStackTrace(exception);
				done(false);
			}

		});
	}

//	public void autofacebookLogin(final String redirect) {
//		getFbId(new IdReady() {
//
//			@Override
//			public void idReady(String fbId) {
//				autofacebookLoginIdReady(redirect, fbId);
//			}
//		});
//	}

//	private void autofacebookLoginIdReady(String redirect, String fbId) {
//		final AuthRequest req = new AuthRequest(FACEBOOK_AUTH_URL, fbId).withScopes(FACEBOOK_EMAIL_SCOPE)
//				// .withScopes(FACEBOOK_EMAIL_SCOPE,
//				// FACEBOOK_BIRTHDAY_SCOPE)
//				// Facebook expects a comma-delimited list of scopes
//				.withScopeDelimiter(",");
//
//		AUTH.redirect(req, redirect);
//
//	}

	public static void autologinsuccess(String hash) {
		AUTH.finishPublic(hash, new Callback<String, Throwable>() {
			@Override
			public void onSuccess(String token) {
				trustedLogin(token);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error:\n" + caught.getMessage());
				done(false);
			}
		});
	}
	
	public static void done(boolean suc){
//		String redirect=Location.getParameter("redirect");
//		if(redirect==null)
		String redirect="/_local/onedebug.html";
		Window.Location.replace(redirect);
	}

}
