package com.bilgidoku.rom.shared.util;

public final class Presence {

	public static final int Visible = 1;
	public static final int DontDisturb = 2;
	public static final int Away = 4;
	public static final int CanChat = 8;
	public static final int CanCall = 16;
	public static final int VisibleAll = 32;
	public static final int DeskUser = 64;

	public static final int FREE = Visible | CanChat | CanCall | VisibleAll;

	public static final String StrOffline = "offline";
	public static final String StrFree = "free";

	public static final String CODED_FREE = FREE + "-" + StrFree;

	public static String img(int code) {
		if ((code & DontDisturb) > 0) {
			return "/_public/images/presence/dontdisturb.png";
		}
		if ((code & Away) > 0) {
			return "/_public/images/presence/away.png";
		}
		if ((code & CanCall) > 0) {
			return "/_public/images/presence/cancall.png";
		}
		if ((code & CanChat) > 0) {
			return "/_public/images/presence/canchat.png";
		}
		if ((code & Visible) == 0) {
			return "/_public/images/presence/offline.png";
		}

		return "/_public/images/presence/online.png";
	}

	public static boolean can(int code, int j) {
		return (code & j) > 0;
	}

}
