package com.bilgidoku.rom.site.kamu.login.client;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.google.gwt.user.client.Window.Location;

public class login extends RomEntryPoint {
	public final CompInfo info = new CompInfo("+trustedlogin", 500, new String[] { "isauth" },
			new String[] { "+login" }, new String[] {});

	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}

	public final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return login.this.comp;
		}
	};
	Comp comp = new CompBase() {

		@Override
		public CompInfo compInfo() {
			return info;
		}

		public void resolve() {
			start();
		}

		@Override
		public void dataChanged(String key, String val) {
			if (key.equals("isauth") && val != null) {
				success();
			}
		}

	};

	public login() {
		super("Rom Server Login Application", false, null, true, false);
	}

	protected void success() {
		TrustedAuth.done(true);
	}

	protected void start() {
		
		boolean isAuth = RomEntryPoint.com().getBool("isauth");
		if (isAuth) {
			TrustedAuth.done(true);
			return;
		}
		String comesfrom = Location.getParameter("comesfrom");
		if (comesfrom != null && comesfrom.equals("facebook")) {
			String hash = Location.getHash();
			if (hash != null && hash.length() > 0) {
				// Sistem.outln(hash);
				TrustedAuth.autologinsuccess(hash);
				return;
			}
		}

		String autoLogin = Location.getParameter("autologin");
		if (autoLogin != null && autoLogin.equalsIgnoreCase("facebook")) {
			TrustedAuth.fbLogin();
			return;
		}

		RomEntryPoint.com().post("*userneed", "mode", "contact");
	}

	//
	// @Override
	// protected Authenticator createAuthenticator() {
	// return new AuthenticatorImpl();
	// }
	//
	// @Override
	// protected RunnerCom createRunnerCom() {
	// return null;
	// }
	//
	// @Override
	// protected SessionHook createSessionHook() {
	// return new SessionHook() {
	//
	// @Override
	// public boolean login(boolean initial, String cid, String userName, String
	// hostName) {
	// String where = Location.getParameter("redirect");
	// if (where == null || where.isEmpty()) {
	// if (SessionManage.one.isUser()) {
	// Window.Location.replace("/_local/one.html");
	// } else {
	// Window.Location.replace("/_public/contact.html");
	// }
	// } else {
	//
	// String goes = URL.decode(where);
	// /*
	// * Phishing alarm: where shouldnt have a host part
	// */
	// //
	// // if(goes.length()!= 0 && goes.startsWith("/")){
	// // }
	//
	// Window.Location.replace(goes);
	// }
	// return false;
	// }
	//
	// @Override
	// public boolean logout(boolean initial) {
	// if (initial) {
	// String autoLogin = Location.getParameter("autologin");
	// if (autoLogin != null && autoLogin.equals("facebook")) {
	//
	// String hash = Location.getHash();
	// if (hash != null && hash.length() > 0) {
	// Sistem.outln(hash);
	// ((AuthenticatorImpl) authenticator).autologinsuccess(hash);
	// return false;
	// }
	//
	// String where = Location.getParameter("redirect");
	// ((AuthenticatorImpl) authenticator).autofacebookLogin(where);
	// return false;
	//
	// }
	// }
	//
	// showLogin();
	// return false;
	// }
	//
	// @Override
	// public boolean error(boolean initial, String errorText, Throwable
	// errException) {
	// if (lui != null) {
	// lui.setError(errorText);
	// } else {
	// Window.alert(errorText);
	// }
	//
	// return false;
	// }
	//
	// };
	// }
	//
	// private void showLogin() {
	// RomEntryPoint.one.clear();
	// lui = new LoginUI();
	// RomEntryPoint.one.addToRootPanel(lui);
	//
	// }
}
