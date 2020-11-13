package com.bilgidoku.rom.site.yerel.initial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class LangSwitchHandler extends FlowPanel {
	private final String cookieName = LocaleInfo.getLocaleCookieName();
	private final String queryParam = LocaleInfo.getLocaleQueryParam();
	private final LangCellPattern langCell = new LangCellPattern();
	private final CellList<String> listLang = new CellList<String>(langCell);
	private final SingleSelectionModel<String> listLangSelModel = new SingleSelectionModel<String>();

	public LangSwitchHandler() {
		initLocaleBox();
		listLang.setSize("30px", "14px");
		listLang.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		this.add(listLang);
		forChange();
	}
	
	private void initLocaleBox() {
		if (cookieName == null && queryParam == null) {
			getListLang().setVisible(false);
			return;
		}		
		String currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (currentLocale.equals("default")) {
			currentLocale = "en";
		}
		List<String> locales = new ArrayList<String>();
		String[] localeNames = LocaleInfo.getAvailableLocaleNames();
		for (String localeName : localeNames) {
			if (!localeName.equals("default")) {
				locales.add(localeName);
			}
		}
		listLang.setSelectionModel(listLangSelModel);
		listLang.setRowCount(locales.size(), true);
		listLang.setRowData(0, locales);
	}

	public CellList<String> getListLang() {
		return listLang;
	}
	
	private void forChange() {
		listLangSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			//@SuppressWarnings("deprecation")
			public void onSelectionChange(SelectionChangeEvent event) {
				String localeName = listLangSelModel.getSelectedObject();
				if (cookieName != null) {
					// expire in one year //TODO check
					Date expires = new Date();
					expires.setYear(expires.getYear() + 1);
					Cookies.setCookie(cookieName, localeName, expires);
				}
				if (queryParam != null) {
					UrlBuilder builder = Location.createUrlBuilder().setParameter(queryParam, localeName);
					Window.Location.replace(builder.buildString());
				} else {
					// If we are using only cookies, just reload
					Window.Location.reload();
				}

			}
		});
	}

}
