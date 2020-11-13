package com.bilgidoku.rom.gwt.client.util.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.min.geo.Four;
import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Utils;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class ClientUtil {

	public static String getName(Contacts con) {
		if (con.last_name != null && !con.last_name.isEmpty() && con.first_name != null && !con.first_name.isEmpty()) {
			return con.first_name + " " + con.last_name;
		}

		if (con.first_name != null && !con.first_name.isEmpty())
			return con.first_name;

		if (con.last_name != null && !con.last_name.isEmpty())
			return con.last_name;

		if (con.email != null && !con.email.isEmpty())
			return con.email;

		return con.uri;
	}

	public static void startWaiting() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
	}

	public static void stopWaiting() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
	}

	public static String getAddress(String uri, String type) {
		UrlBuilder builder1 = Location.createUrlBuilder();

		String redirect = builder1.buildString();

		UrlBuilder builder = Location.createUrlBuilder().setPath("/_public/login.html");
		builder.setParameter("redirect", redirect);

		return builder.buildString();

	}

	public static JSONObject getObject(JSONValue jsonValue) {
		if (jsonValue == null)
			return null;
		return jsonValue.isObject();
	}

	public static Widget getTitle(String text, String size) {
		FlowPanel hPanel = new FlowPanel();
		if (size == null)
			hPanel.setStyleName("site-header");
		else
			hPanel.setStyleName("site-header" + size);
		hPanel.setHeight("100%");
		hPanel.setWidth("100%");
		hPanel.add(new HTML(text));
		return hPanel;
	}

	public static String getString(JSONObject cjo, String key) throws RunException {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isString() == null) {
			throw new RunException("Key(String) not found in JSONObject key:" + key);
		}

		return jsonValue.isString().stringValue();
	}

	public static int getInt(JSONObject cjo, String key) throws RunException {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isNumber() == null) {
			throw new RunException("Key(String) not found in JSONObject key:" + key);
		}

		return (int) jsonValue.isNumber().doubleValue();
	}

	public static String optString(JSONObject cjo, String key) throws RunException {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isString() == null) {
			return null;
		}

		return jsonValue.isString().stringValue();
	}

	public static Boolean optBoolean(JSONObject cjo, String key) {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isBoolean() == null) {
			return null;
		}

		return jsonValue.isBoolean().booleanValue();

	}

	public static JSONArray optArray(JSONObject cjo, String key) throws RunException {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isArray() == null) {
			return null;
		}

		return jsonValue.isArray();
	}

	public static JSONObject optObject(JSONObject cjo, String key) throws RunException {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isObject() == null) {
			return null;
		}

		return jsonValue.isObject();
	}

	public static String optString(JSONObject cjo, String key, String defaul) throws RunException {
		String ret = optString(cjo, key);
		if (ret == null)
			return defaul;
		return null;
	}

	public static JSONObject getObject(JSONObject cjo, String key) throws RunException {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isObject() == null) {
			return null;
			// throw new RunException("Key not found in JSONObject key:" + key);
		}

		return jsonValue.isObject();
	}

	public static JSONValue parseEncodedJson(String value) {
		value = Utils.switchQuote(value);
		return com.google.gwt.json.client.JSONParser.parseStrict(value);

	}

	public static JSONObject parseEncodedObject(String value) {
		JSONValue obj = parseEncodedJson(value);
		if (obj == null)
			return null;
		return obj.isObject();
	}

	public static int arrIntOnIndex(JSONArray ja, int ind) {
		JSONValue val = ja.get(ind);
		return (int) val.isNumber().doubleValue();
	}

	public static Integer optInteger(JSONObject cjo, String key) {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isNumber() == null) {
			return null;
		}

		return (int) jsonValue.isNumber().doubleValue();
	}

	public static Double optDouble(JSONObject cjo, String key) {
		JSONValue jsonValue = cjo.get(key);
		if (jsonValue == null || jsonValue.isNumber() == null) {
			return null;
		}

		return jsonValue.isNumber().doubleValue();
	}

	public static boolean isValidUserName(String usr) {
		return usr.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*$");
	}

	public static boolean isValidEMail(String eMail) {
		return eMail.matches("^[A-Z0-9a-z._%+-]+@[A-Z0-9a-z._%+-]+$");

	}

	public static String[] getPriceAndUnit(JSONValue jsonValue) {
		if (jsonValue == null || jsonValue.isArray() == null)
			return null;

		JSONArray ja = jsonValue.isArray();
		Double dbl = ja.get(0).isNumber().doubleValue();
		int i = dbl.intValue();

		int whole = i / 100;
		int frac = i % 100;

		double value = Double.parseDouble(whole + "." + frac);

		NumberFormat num = NumberFormat.getFormat("#,###,###,##0.00");
		String formatted = num.format(value);
		String unit = ClientUtil.getString(ja.get(1));
		unit = unit.replaceAll("TRY", "TL");
		unit = unit.replaceAll("YTL", "TL");
		String[] ret = { formatted, unit };
		return ret;
	}

	public static String getPrice(JSONValue jsonValue) {
		if (jsonValue == null || jsonValue.isArray() == null)
			return "-";

		JSONArray ja = jsonValue.isArray();
		Double dbl = ja.get(0).isNumber().doubleValue();
		int i = dbl.intValue();

		int whole = i / 100;
		int frac = i % 100;

		double value = Double.parseDouble(whole + "." + frac);

		return getLocalePrice(value, ClientUtil.getString(ja.get(1)));
	}

	public static String getLocalePrice(double value, String unit) {
		NumberFormat num = NumberFormat.getCurrencyFormat(unit);
		// String formatted = num.format(value).replace("YTL", "₺");
		String formatted = num.format(value).replace("YTL", "TL");
		return formatted;
	}

	public static Anchor getImageAnchor(ImageResource imageResource) {
		Anchor anc = new Anchor();

		Image img = new Image(imageResource);
		anc.getElement().removeAllChildren();
		anc.getElement().appendChild(img.getElement());

		return anc;
	}

	public static Anchor getImageAnchor(String imageUri, String title) {
		Anchor anc = new Anchor();

		anc.getElement().removeAllChildren();
		anc.setHTML(imageItemHTML(imageUri, title));

		return anc;
	}

	public static void setCursor(Cursor cursor) {
		updateCursor(cursor, null);
	}

	private static void updateCursor(Cursor cursor, Element element) {
		if (element == null)
			element = RootPanel.getBodyElement();
		element.getStyle().setCursor(cursor);
	}

	public static void safePut(JSONObject jo, String key, String value) {
		if (value == null)
			return;
		jo.put(key, new JSONString(value));
	}

	public static void safePut(JSONObject jo, String key, Double value) {
		if (value == null)
			return;
		jo.put(key, new JSONNumber(value));
	}

	public static void safePut(JSONObject jo, String key, Integer value) {
		if (value == null)
			return;
		jo.put(key, new JSONNumber(value));
	}

	public static void safePut(JSONObject jo, String key, JSONArray value) {
		if (value == null)
			return;
		jo.put(key, value);
	}

	public static void safePut(JSONObject jo, String key, Boolean value) {
		if (value == null)
			return;
		jo.put(key, JSONBoolean.getInstance(value));
	}

	public static String intToHex(int i) {
		String s = "000000" + Integer.toHexString(i).toUpperCase();
		return s.substring(s.length() - 6);
	}

	public static int hexToInt(String hex) {
		return Integer.valueOf(hex.replace("#", ""), 16).intValue();
	}

	public static String imageItemHTML(ImageResource imageProto, String title) {
		if (title == null) {
			return AbstractImagePrototype.create(imageProto).getHTML();
		}
		return AbstractImagePrototype.create(imageProto).getHTML() + " " + title;
	}

	public static String getStackTrace(Throwable e) {
		StackTraceElement[] st = e.getStackTrace();
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement stackTraceElement : st) {
			sb.append(stackTraceElement.toString());
			sb.append("\n    ");
		}
		return sb.toString();

	}

	public static String getLastUri(String img) {

		if (!img.contains("?")) {
			img = img + "?_ts=" + System.currentTimeMillis();
			return img;
		}

		if (!img.contains("_ts")) {
			img = img + "&_ts=" + System.currentTimeMillis();
			return img;
		}

		int ind = img.indexOf("_ts=");
		img = img.substring(0, ind + 4) + System.currentTimeMillis();
		return img;

	}

	public static String getOnlyUri(String img) {
		if (img.contains("?")) {
			return img.substring(0, img.lastIndexOf("?"));
		}

		return img;

	}

	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}

	public static InlineHTML pipe() {
		return new InlineHTML("&nbsp;|&nbsp;");
	}

	public static String getString(JSONValue jsonValue) {
		if (jsonValue == null)
			return "";
		if (jsonValue.isString() == null) {
			return "";
		} else {
			return jsonValue.isString().stringValue();
		}
	}

	public static double[] getNumericArray(JSONValue jsonValue) {
		if (jsonValue == null || jsonValue.isArray() == null)
			return null;
		JSONArray array = jsonValue.isArray();
		if (array.size() == 0)
			return null;

		double[] ret = new double[array.size()];
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i) != null) {
				if (array.get(i).isNumber() != null) {
					ret[i] = array.get(i).isNumber().doubleValue();
				}
			}
		}
		return ret;
	}

	public static String[] getArray(JSONValue jsonValue) {
		if (jsonValue == null || jsonValue.isArray() == null)
			return null;
		JSONArray array = jsonValue.isArray();
		if (array.size() == 0)
			return null;

		String[] ret = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			ret[i] = getString(array.get(i));
		}
		return ret;
	}

	public static int getNumber(JSONValue jsonValue) {
		if (jsonValue == null)
			return 0;
		if (jsonValue.isNumber() == null) {
			return 0;
		} else {
			Double dbl = jsonValue.isNumber().doubleValue();
			return dbl.intValue();
		}
	}

	public static boolean getBoolean(JSONValue jsonValue) {
		if (jsonValue == null)
			return false;
		if (jsonValue.isBoolean() == null) {
			return false;
		} else {
			return jsonValue.isBoolean().booleanValue();
		}
	}

	public static String imageItemHTML(String imageUri, String title) {

		String s = "";
		if (imageUri != null)
			s = "<img src='" + imageUri + "'>";

		if (title != null && !title.isEmpty())
			s = s + " " + title;

		return s;

	}

	public static SafeHtml getHeader(String text) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<div class='site-panelheader' type='button'>" + text + "</div>");
		return sb.toSafeHtml();

	}

	// public static String getAddress(String uri, String type) {
	// UrlBuilder builder = Location.createUrlBuilder().setPath(uri);
	// ClientUtil.removeAllParameters(builder);
	//
	// if (type.equals("edit")) {
	//// builder.setParameter("gwt.debug", "edit");
	// } else if (type.equals("login")) {
	// String redirect = Location.createUrlBuilder().buildString();
	// builder.setParameter("redirect", redirect);
	// }
	//
	// if (Location.getParameter("locale") != null)
	// builder.setParameter("locale", Location.getParameter("locale"));
	//
	// return builder.buildString();
	//
	// }

	public static void removeAllParameters(UrlBuilder builder) {
		Set<String> keySet = Location.getParameterMap().keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			// if (key.equals("locale"))
			// continue;
			builder.removeParameter(key);
		}
	}

	public static FlowPanel getToolbar(Widget[] widgets, int mod) {
		FlowPanel tbHolder = new FlowPanel();
		int i = 0;

		HorizontalPanel first = new HorizontalPanel();
		first.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		tbHolder.add(first);
		HorizontalPanel second = null;
		if (widgets.length > mod) {
			second = new HorizontalPanel();
			second.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			tbHolder.add(second);
		}
		for (Widget widget : widgets) {
			if (i < mod) {
				first.add(widget);
			} else {
				second.add(widget);
			}
			i++;
		}
		tbHolder.getElement().getStyle().setPadding(2, Unit.PX);
		tbHolder.setStyleName("gwt-RichTextToolbar");
		return tbHolder;
	}

	private static final long MEGABYTE = 1024L * 1024L;
	private static final long KILOBYTE = 1024L;

	public static long bytesToMeg(long bytes) {
		return bytes / MEGABYTE;
	}

	public static long bytesToKilo(long bytes) {
		return bytes / KILOBYTE;
	}

	public static Widget getTabTitle(String title, String color, String helpUri) {
		final SiteToolbarButton hPanel = new SiteToolbarButton(title, title, helpUri);
		if (color == null || color.isEmpty())
			color = "site-tabheader site-tabheader-" + Data.DEFAULT_COLOR;
		else
			color = "site-tabheader site-tabheader-" + color;

		hPanel.setStyleName(color, true);
		hPanel.setStyleName("site-toolbarbutton", false);

		hPanel.setText(title);
		hPanel.setTitle(title);

		// final HorizontalPanel hPanel = new HorizontalPanel();
		// if (color == null || color.isEmpty())
		// color = "site-tabheader site-tabheader-" + Data.DEFAULT_COLOR;
		// else
		// color = "site-tabheader site-tabheader-" + color;
		//
		// hPanel.setStyleName(color, true);
		// hPanel.add(new HTML("<nobr/>" + title));
		// // hPanel.setText(title);
		// hPanel.setTitle(title);

		return hPanel;
	}

	public static Widget getTabTitle(String title) {
		final FlowPanel hPanel = new FlowPanel();
		String color = "site-tabheader site-tabheader-" + Data.DEFAULT_COLOR;
		hPanel.setStyleName(color, true);
		hPanel.add(new HTML("<nobr/>&nbsp;" + title));
		hPanel.setTitle(title);
		return hPanel;
	}

	public static int findIndex(ListBox list, String value) {
		if (value == null || value.isEmpty())
			return -1;
		int foundIndex = -1;
		for (int j = 0; j < list.getItemCount(); j++) {
			String listValue = list.getValue(j);
			if (listValue.toLowerCase().equals(value.toLowerCase())) {
				foundIndex = j;
				break;
			}
		}
		return foundIndex;
	}

	public static void findAndSelect(ListBox list, String value) {
		int ind = findIndex(list, value);
		if (ind >= 0)
			list.setSelectedIndex(ind);
	}

	public static Widget getHeaderWidget(String text, String image) {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setHeight("100%");
		hPanel.setWidth("100%");
		hPanel.setSpacing(10);
		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		if (image != null)
			hPanel.add(new Image(image));
		HTML headerText = new HTML(text);
		headerText.setStyleName("site-header");
		hPanel.add(headerText);
		return hPanel;
	}

	public static Widget getHeader(String text, String image) {
		FlowPanel hPanel = new FlowPanel();
		hPanel.setStyleName("site-header");
		hPanel.setHeight("100%");
		hPanel.setWidth("100%");
		if (image != null)
			hPanel.add(new Image(image));
		// HTML headerText = new HTML(text);
		hPanel.add(new Label(text));
		return hPanel;
	}

	public static boolean existInArray(String[] langs, String lang) {
		boolean exists = false;
		for (int i = 0; i < langs.length; i++) {
			if (lang.equals(langs[i])) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	public static String getFontColor(String hex) {
		hex = hex.replace("#", "");
		if (hex.isEmpty())
			return "#FFFFFF";

		String rr = hex.substring(0, 2);
		String gg = hex.substring(2, 4);
		String bb = hex.substring(4);

		int r = hexToInt(rr);
		int g = hexToInt(gg);
		int b = hexToInt(bb);

		int t = 0;

		if (r <= 128)
			t = t + 1;

		if (g <= 128)
			t = t + 1;

		if (b <= 128)
			t = t + 1;

		if (t > 1) {
			return "#FFFFFF"; // black
		} else {
			return "#3F4041"; // white
		}
	}

	public static String getWeakFontColor(String string) {
		return "#999999";
	}

	public static String getDarkerFontColor(String string) {
		return "#333333";
	}

	public static boolean isToday(String dd) {
		Date date0 = getDate(dd);
		Date date1 = new Date();
		return CalendarUtil.isSameDate(date0, date1);
	}

	public static String fmtDate(Date date) {
		// 2013-09-04 15:31:12.958632
		DateTimeFormat fmt = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		return fmt.format(date);
	}

	public static String fmtSqlDate(Date date) {
		// 2013-09-04 15:31:12.958632
		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(date);
	}

	public static String fmtDate(String dd) {
		if (dd == null || dd.isEmpty())
			return "";
		Date date = getDate(dd);
		return fmtDate(date);
	}

	public static Date getDate(String dd) {
		if (dd.length() > 20)
			dd = dd.substring(0, 19);
		return DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").parse(dd);
	}

	public static String fmtShortDate(String dd) {
		if (dd == null || dd.isEmpty())
			return "";
		Date date = getDate(dd);
		DateTimeFormat fmt = DateTimeFormat.getFormat("d MMM");
		return fmt.format(date);
	}

	public static String fmtTime(String dd) {
		if (dd == null || dd.isEmpty())
			return "";
		Date date = getDate(dd);
		DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm");
		return fmt.format(date);
	}

	public static String getTitleFromUri(String uri) {
		if (uri.equals("/"))
			return "Ana Sayfa";

		String named = uri.substring(uri.lastIndexOf("/") + 1);
		if (named.length() > 33)
			named = named.substring(0, 30) + "...";

		return named;
	}

	public static HTML getSeperator() {
		HTML seperator = new HTML("");
		seperator.setStyleName("site-seperator");
		seperator.getElement().getStyle().setPadding(2, Unit.PX);
		return seperator;
	}

	private static HTML getWarning(String text, String color) {
		HTML ast = new HTML("* " + text);
		ast.setStyleName("site-warning");
		ast.getElement().getStyle().setColor(color);
		return ast;
	}

	public static boolean checkUri(String u) {
		if (u.isEmpty())
			return true;
		if (!u.matches("^[a-z][a-z0-9._-]*")) {
			return false;
		}
		return true;
	}

	public static boolean checkTitle(String u) {
		if (u.isEmpty())
			return true;

		if (!u.matches("[a-zA-ZÇŞĞÜÖİçşğüöı0-9._!?:'\"\\ \\)\\(,-]*")) {
			return false;
		}
		return true;
	}

	public static Widget getWarningGreen(String string) {
		return getWarning(string, "green");
	}

	public static Widget getWarningRed(String string) {
		return getWarning(string, "red");
	}

	public static FlowPanel getVerticalToolbar(Widget[] widgets) {
		FlowPanel tbHolder = new FlowPanel();

		VerticalPanel first = new VerticalPanel();
		tbHolder.add(first);
		for (Widget widget : widgets) {
			widget.setSize("110px", "30px");

			first.add(widget);
		}
		tbHolder.getElement().getStyle().setPadding(2, Unit.PX);
		tbHolder.setStyleName("gwt-RichTextToolbar");
		tbHolder.setStyleName("site-padding");

		return tbHolder;
	}

	public static JSONValue detachJsonVal(com.bilgidoku.rom.gwt.client.common.Json[] nested) {
		if (nested == null || nested.length == 0)
			return null;
		return nested[0].getValue();
	}

	public static com.google.gwt.json.client.JSONArray detachJsonValArr(
			com.bilgidoku.rom.gwt.client.common.Json[] nested) {
		JSONValue k = detachJsonVal(nested);
		if (k == null)
			return null;
		return k.isArray();
	}

	public static com.google.gwt.json.client.JSONObject detachJsonValObj(
			com.bilgidoku.rom.gwt.client.common.Json[] nested) {
		JSONValue k = detachJsonVal(nested);
		if (k == null)
			return null;
		return k.isObject();
	}

	public static void jsonObjPutStr(com.google.gwt.json.client.JSONObject item, String key, String innerText) {
		if (innerText != null && !innerText.isEmpty())
			item.put(key, new com.google.gwt.json.client.JSONString(innerText));
		else
			item.put(key, new com.google.gwt.json.client.JSONString(""));

	}

	public static void jsonObjPutStrAsArr(com.google.gwt.json.client.JSONObject item, String key, String innerText) {
		com.google.gwt.json.client.JSONArray ja = new com.google.gwt.json.client.JSONArray();
		if (innerText != null && !innerText.isEmpty())
			ja.set(0, new com.google.gwt.json.client.JSONString(innerText));
		item.put(key, ja);
	}

	public static void jsonObjPutArrAsArr(com.google.gwt.json.client.JSONObject item, String key,
			com.google.gwt.json.client.JSONArray innerText) {
		com.google.gwt.json.client.JSONArray ja = new com.google.gwt.json.client.JSONArray();
		ja.set(0, innerText);
		item.put(key, ja);
	}

	public static void jsonObjPutArrAsObj(com.google.gwt.json.client.JSONObject item, String key,
			com.google.gwt.json.client.JSONObject innerText) {
		com.google.gwt.json.client.JSONArray ja = new com.google.gwt.json.client.JSONArray();
		ja.set(0, innerText);
		item.put(key, ja);
	}

	public static boolean isImage(String uri) {
		if (uri == null || uri.isEmpty())
			return false;
		String muri = uri.toLowerCase();
		return muri.indexOf(".jpg") > 0 || muri.indexOf(".png") > 0 || muri.indexOf(".jpeg") > 0
				|| muri.indexOf(".bmp") > 0 || muri.indexOf(".gif") > 0;
	}

	public static List<Widget> findButtons(Widget widget, List<Widget> found) {
		if (widget instanceof SiteToolbarButton) {
			found.add(widget);
		}
		if (widget instanceof HasWidgets || widget instanceof HasOneWidget) {
			Iterator<Widget> iter = ((HasWidgets) widget).iterator();
			while (iter.hasNext()) {
				Widget nextWidget = iter.next();
				findButtons(nextWidget, found);
			}
		}
		return found;
	}

	public static Widget getTabTitle(String named, final String tabIcon) {

		if (named != null && named.length() > 30)
			named = named.substring(0, 27) + "...";

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		hp.add(new Image(tabIcon));
		hp.add(new HTML("&nbsp;" + named + "&nbsp;&nbsp;"));
		hp.setTitle(named);
		return hp;
	}

	public static String findLangMatch(String lang) {
		String[][] langCodes = Data.LANG_CODES;
		String found = "";
		for (int i = 0; i < langCodes.length; i++) {
			String[] arr = langCodes[i];
			if (arr[0].equals(lang)) {
				found = arr[1];
				break;
			}
		}
		return found;
	}

	public static String getUriFromlastUri(String fileUri) {
		if (fileUri.indexOf("?") > 0) {
			return fileUri.substring(0, fileUri.indexOf("?"));
		}
		return fileUri;

	}

	public static Widget getSpace() {
		HTML spc = new HTML("");
		spc.setStyleName("site-space");
		spc.setWidth("4px");
		return spc;
	}

	public static String getNow() {
		Date date = new Date();
		String now = DateTimeFormat.getFormat("yyyyMMddHHmmss").format(date);
		return now;
	}

	public static Point getPoint(JSONObject jo, String string) {
		JSONArray array = jo.get(string).isArray();
		return new Point((int) array.get(0).isNumber().doubleValue(), (int) array.get(1).isNumber().doubleValue());
	}

	public static Point getPoint(JSONArray array) {
		return new Point((int) array.get(0).isNumber().doubleValue(), (int) array.get(1).isNumber().doubleValue());
	}

	public static JSONValue toJson(Point p) {
		JSONArray a = new JSONArray();
		a.set(0, new JSONNumber(p.getX()));
		a.set(1, new JSONNumber(p.getY()));
		return a;
	}

	public static JSONArray toJson(Four f) {
		JSONArray a = new JSONArray();
		a.set(0, new JSONNumber(f.getX()));
		a.set(1, new JSONNumber(f.getY()));
		a.set(2, new JSONNumber(f.getW()));
		a.set(3, new JSONNumber(f.getH()));
		return a;
	}

	public static Four getFour(JSONArray array) {
		int x = (int) array.get(0).isNumber().doubleValue();
		int y = (int) array.get(1).isNumber().doubleValue();
		int w = (int) array.get(2).isNumber().doubleValue();
		int h = (int) array.get(3).isNumber().doubleValue();
		return new Four(x, y, w, h);
	}

	// public static boolean isImage(String uri) {
	// if (uri == null || uri.isEmpty())
	// return false;
	// String muri = uri.toLowerCase();
	// return muri.endsWith("jpg") || muri.endsWith("png") ||
	// muri.endsWith("jpeg") || muri.endsWith("bmp")
	// || muri.endsWith("gif");
	// }
	//
	// public static String getTitleFromUri(String uri) {
	// if (uri.equals("/"))
	// return "Ana Sayfa";
	//
	// String named = uri.substring(uri.lastIndexOf("/") + 1);
	// if (named.length() > 33)
	// named = named.substring(0, 30) + "...";
	//
	// return named;
	// }
	//

	public static int findInList(List<String> arr, String value) {
		if (value == null || value.isEmpty())
			return -1;
		int foundIndex = -1;

		for (int j = 0; j < arr.size(); j++) {
			String listValue = arr.get(j);
			if (listValue.toLowerCase().equals(value.toLowerCase())) {
				foundIndex = j;
				break;
			}
		}
		return foundIndex;
	}

	public static String getId(String str) {
		return str.substring(str.lastIndexOf("/") + 1);
	}

	public static String getTranslation(String replace, JSONObject translations1) {
		if (replace == null)
			return "";

		if (translations1 == null)
			return "";

		String label = replace;
		if (translations1.get(replace) != null) {
			label = ClientUtil.getString(translations1.get(replace));
		}
		return label;
	}

	public static boolean isUser(String ownercid, List<String[]> users) {
		for (int i = 0; i < users.size(); i++) {
			String[] user = users.get(i);
			if (user[1].equals(ownercid))
				return true;
		}
		return false;
	}

	public static String originalDbFileName(String name) {
		if (name.indexOf("-") > 0) {
			int start = name.lastIndexOf('-');
			String encoded = name.substring(start + 1);
			return encoded;
		} else {
			return name;
		}
	}

	public static void addContainer(TreeItem parent, Long mask, String uri, String uri_prefix, String text, Tree tree) {
		String conImg = "/_local/images/common/folder_page.png";
		if (mask.equals(Data.WRITING_PRIVATE_MASK))
			conImg = "/_local/images/common/folder_page_locked.png";

		SiteTreeItemData data = new SiteTreeItemData(text, uri, true, uri_prefix);
		data.setMask(mask);

		SiteTreeItem node = new SiteTreeItem(text, conImg);
		node.setUserObject(data);

		node.addTextItem("!");
		node.setState(false);

		if (parent == null)
			tree.addItem(node);
		else
			parent.addItem(node);

	}

	public static void addLeaf(TreeItem parent, String uri, String title, Tree tree) {
		String itemImg = "/_local/images/common/writing.png";

		SiteTreeItem node = new SiteTreeItem(title, itemImg);
		node.setUserObject(new SiteTreeItemData(title, uri, false, uri));
		if (parent == null)
			tree.addItem(node);
		else {
			parent.addItem(node);
		}
	}

	public static String removeParameter(String str, String param) {
		// no parameter
		if (str.indexOf("?") < 0)
			return str;

		String strParams = str.substring(str.indexOf("?") + 1);
		String[] arrParams = strParams.split("&");
		Map<String, String> mParams = new HashMap<>();

		for (int i = 0; i < arrParams.length; i++) {
			String[] parts = arrParams[i].split("=");
			mParams.put(parts[0], parts[1]);
		}

		mParams.remove(param);
		if (mParams.size() <= 0)
			return getOnlyUri(str);

		String ret = str.substring(0, str.indexOf("?") + 1);
		Set<String> keySet = mParams.keySet();
		boolean first = true;
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			if (!first) {
				ret = ret + "&";
			}
			first = false;
			String key = (String) iterator.next();
			ret = ret + key + "=" + mParams.get(key);
		}

		return ret;

	}

	public static String addParameter(String str, String param, String value) {
		String ret = str;
		ret = removeParameter(ret, param);
		if (ret.indexOf("?") > 0) {
			ret = ret + "&" + param + "=" + value;
		} else {
			ret = ret + "?" + param + "=" + value;
		}
		return ret;
	}

	public static String toNullStr(String s) {
		if (s == null)
			return "NULL";
		return s;
	}

	public static String fromNullStr(String s) {
		if (s == null || s.equals("NULL"))
			return null;
		return s;
	}

	public static boolean checkNotNull(String s) {
		return !(s == null || s.equals("NULL"));
	}

	public static String maxNChars(String s, int n) {
		if (s == null)
			return "";
		if (s.length() <= n)
			return s;
		return s.substring(0, n - 3) + "..";
	}

}
