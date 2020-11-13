package com.bilgidoku.rom.gwt.client.util.common;

import java.util.Date;

import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class SwitchLocale extends MenuBar {
	private final String cookieName = LocaleInfo.getLocaleCookieName();
	private final String queryParam = LocaleInfo.getLocaleQueryParam();
	private final MenuBar langOptions = new MenuBar(true);

	/**
	 * Switch between locales defined in .gwt.xml file
	 * 
	 * @param currentLocale
	 */
	public SwitchLocale(String currentLocale) {
		this.setAutoOpen(false);
		this.setWidth("49px");
		this.setAnimationEnabled(true);
		initLocaleBox(currentLocale);
	}

	private void initLocaleBox(String currentLocale) {
		if (cookieName == null && queryParam == null) {
			this.setVisible(false);
			return;
		}

		this.addItem(getFlag(currentLocale), langOptions);

		Sistem.outln("Setting Locale names:");
		String[] locales = LocaleInfo.getAvailableLocaleNames();
		for (final String localeName : locales) {
			Sistem.outln("Locale:"+localeName);
			if (!localeName.equals("default") && !localeName.equals(currentLocale)) {
				MenuItem mi = new MenuItem(getFlag(localeName), new Command() {
					@Override
					public void execute() {
						
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
		if (flg.toLowerCase().indexOf("en") >= 0 || flg.toLowerCase().indexOf("default") >= 0) {
			flag = "/_public/images/bar/english.png";
		} else if (flg.toLowerCase().indexOf("tr") >= 0)
			flag = "/_public/images/bar/turkish.png";
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<img src='" + flag + "' style='vertical-align: middle;'/>");
		return sb.toSafeHtml();
	}

}
