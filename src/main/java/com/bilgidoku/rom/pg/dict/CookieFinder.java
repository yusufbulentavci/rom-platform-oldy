package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.shared.Cookie;

public interface CookieFinder {

	String getCookieByName(Cookie cookie, String cookieName);

}
