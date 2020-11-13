package com.bilgidoku.rom.site.yerel;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class SwitchLang extends MenuBar {
//	private final String cookieName = LocaleInfo.getLocaleCookieName();
	private final String queryParam = LocaleInfo.getLocaleQueryParam();
	private final MenuBar langOptions = new MenuBar(true);
	
	public SwitchLang() {
	    this.setAutoOpen(true);
	    this.setWidth("49px");
	    this.setAnimationEnabled(true);	    
	    
	    initLocaleBox();
	}
	
	private void initLocaleBox() {
		if (queryParam == null) {
			this.setVisible(false);
			return;
		}		
		this.addItem(getFlag(RomEntryPoint.one.currentLocale), langOptions);
		
		Sistem.outln("Setting Locale names:");
		String[] localeNames = LocaleInfo.getAvailableLocaleNames();
		for (final String localeName : localeNames) {
			Sistem.outln("Locale:"+localeName);
			if (!localeName.equals("default") && !localeName.equals(RomEntryPoint.one.currentLocale)) {
				MenuItem mi = new MenuItem(getFlag(localeName), new Command() {
			    	@Override
					public void execute() {
//						if (cookieName != null) {
//							Date expires = new Date();
//							expires.setYear(expires.getYear() + 1);
//							Cookies.setCookie(cookieName, localeName, expires);
//						}
						if (queryParam != null) {
							UrlBuilder builder = Location.createUrlBuilder().setParameter(queryParam, localeName);
							Window.Location.replace(builder.buildString());
						} else {
							// If we are using only cookies, just reload
							Window.Location.reload();
						}
					}
			    });
				
			    langOptions.addItem(mi);	
			}
		}
	}

	private SafeHtml getFlag(String flg) {
		String flag = "";
		if (flg.equals("tr")) 
			flag = "/_local/images/common/turkish.png";
		else if (flg.equals("en")) {
			flag = "/_local/images/common/english.png";
		}
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<img src='" + flag + "' style='vertical-align: middle;'/>");
		return sb.toSafeHtml();
	}

}
