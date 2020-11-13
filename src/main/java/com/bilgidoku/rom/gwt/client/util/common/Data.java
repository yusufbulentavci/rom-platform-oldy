package com.bilgidoku.rom.gwt.client.util.common;

import java.util.LinkedHashMap;


public class Data {
	public static String[] ROLENAMES = { "guest", "contact", "owner", "group", "related", "user", "admin", "designer",  
			"desk", "deskhelper", "editor", "manager" };
	public static int ROLECOUNT = ROLENAMES.length;
	public static int GUESTROLE = 0;
	public static int CONTACTROLE = 1;
	public static int USERROLE = 5;
	public static int ADMINROLE = 6;

	public static final Long WRITING_PUBLIC_MASK = new Long(2059748);
	public static final Long WRITING_NOGUEST_MASK = new Long(2059744);//CRoleMask
	public static final Long WRITING_PRIVATE_MASK = new Long(2059744);

	public static String PERSPECTIVE_MAIL = "mail";
	public static String PERSPECTIVE_DESIGN = "design";
	public static String PERSPECTIVE_CONTENT = "content";
	public static String PERSPECTIVE_ADMIN = "admin";

	public static final String MAIL_COLOR = "orange";
	public static final String CONTENT_COLOR = "blue";
	public static final String HOME_COLOR = "red";
	public static final String DESIGN_COLOR = "purple";
	public static final String DEFAULT_COLOR = "default";

	public static final String[] WIDGET_ATT_TYPES = { "s", "i", "f", "b", "a", "o" };

	public static final String TAG_ROOT = "/_/tags";
	public static final String TARIFFMODEL_ROOT = "/_/tariffmodel";
	public static final String LAYOUT_ROOT = "/_/apps/layout";
	public static final String LIST_ROOT = "/_/lists";
	public static final String LINKS_ROOT = "/_/links";
	public static final String HOUSEHOLDS_ROOT = "/_/households";
	public static final String ISSUE_ROOT = "/_/issues";
	public static final String FILES_ROOT = "/f";
	public static final String WRITING_ROOT = "/_/writings";
	public static final String STYLE_CONTAINER = "/_/styles";
	public static final String APP_CONTAINER = "/_/apps";
	public static final String CONTACT_CONTAINER = "/_/co";
	public static final String WRITING_APP = "/_/apps/layout/standart";
	public static final String STOCK_ROOT = "/_/_stocks";

	public static final String[] LAYOUTS = { "w:standart", "w:searchpage" };

	public static final String INITIAL_PASSWORD = "123456";

	public static final String[] STYLE_TYPES = { "defaultstyle", "link", "visited", "active", "hover", "focus",
			"first-letter", "first-line", "first-child", "before", "after" };

	public static final String[] BACK_PATTERNS = { "/_static/bg/patterns/ptn1.png", "/_static/bg/patterns/ptn2.png",
			"/_static/bg/patterns/ptn3.png",
			"/_static/bg/patterns/ptn4.png",
			"/_static/bg/patterns/ptn5.png",
			"/_static/bg/patterns/ptn6.png",
			// "/_static/bg/patterns/ptn7.png",
			"/_static/bg/patterns/ptn8.png", "/_static/bg/patterns/ptn9.png", "/_static/bg/patterns/ptn10.png",
			"/_static/bg/patterns/ptn11.png", "/_static/bg/patterns/ptn12.png", "/_static/bg/patterns/ptn13.png",
			"/_static/bg/patterns/ptn14.png", "/_static/bg/patterns/ptn15.png", "/_static/bg/patterns/ptn16.png",
			"/_static/bg/patterns/ptn17.png", "/_static/bg/patterns/plus.png", "/_static/bg/patterns/ptn18.png" };

	// public static final String[] BACK_IMAGES = {
	// "/_local/images/bg/sm_bg1.jpg", "/_local/images/bg/sm_bg2.jpg",
	// "/_local/images/bg/sm_bg3.jpg", "/_local/images/bg/sm_bg4.jpg",
	// "/_local/images/bg/sm_bg5.jpg" };

	public static final String[] BANNER_IMAGES = { "/_static/bg/banner/ptn_sm-0.jpg",
			"/_static/bg/banner/ptn_sm-01.jpg", "/_static/bg/banner/ptn_sm-2.jpg", "/_static/bg/banner/ptn_sm-3.jpg",
			"/_static/bg/banner/ptn_sm-4.jpg", "/_static/bg/banner/ptn_sm-5.jpg", "/_static/bg/banner/ptn_sm-6.jpg",
			"/_static/bg/banner/ptn_sm-7.jpg", "/_static/bg/banner/ptn_sm-8.jpg", "/_static/bg/banner/ptn_sm-9.jpg",
			"/_static/bg/banner/ptn_sm-10.jpg", "/_static/bg/banner/ptn_sm-11.jpg", "/_static/bg/banner/ptn_sm-12.jpg",
			"/_static/bg/banner/ptn_sm-13.jpg", "/_static/bg/banner/ptn_sm-14.jpg", "/_static/bg/banner/ptn_sm-15.jpg",
			"/_static/bg/banner/ptn_sm-16.jpg", "/_static/bg/banner/ptn_sm-17.jpg", "/_static/bg/banner/ptn_sm-18.jpg",
			"/_static/bg/banner/ptn_sm-19.jpg" };

	/*
	 * back, backmost, fore, foremost, color3, text_color, text_hili_color,
	 * text_weak_color, color_on_foremost, color_on_color3, text_back_color
	 */

	public static final LinkedHashMap<String, String> PAGECOLORS = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("1", "#FFFFFF,#AEE3E0,#EDEED8,#F38228,#65CEE3");
			put("2", "#FFFFFF,#70384E,#F5DE96,#547D82,#CA304A");
			put("3", "#FFFFFF,#547D82,#FCF0CB,#542437,#CA304A");
			put("4", "#FFFFFF,#051B3F,#ECE2CE,#043E5F,#02706F");
			put("5", "#FFFFFF,#4B5765,#D0F975,#FF7676,#33C2BD");
			put("6", "#FFFFFF,#7DAB96,#FFDFBD,#FF5478,#C8C8A5");
			put("7", "#FFFFFF,#FFF6D7,#DCD8BA,#7EA693,#FF3F62");
			put("8", "#FFFFFF,#DAD1BF,#E0E8E3,#75A5AE,#A8A396");
			put("9", "#FFFFFF,#FBFBFB,#2E3F57,#629BBB,#89151E");
			put("10", "#FFFFFF,#FBFBFB,#629BBB,#2E3F57,#89151E");
			put("11", "#FFFFFF,#D0EE9F,#F2FBC4,#F87398,#FFA97A");
			put("12", "#FFFFFF,#666161,#F2FADB,#74C687,#4DABA6");
			put("13", "#FFFFFF,#EEEDDB,#F7F7F7,#C9B3A0,#C2CCC4");
			put("14", "#FFFFFF,#F7F3E4,#D4E7E7,#D1B38E,#EE8165");
			put("15", "#FFFFFF,#FCFCE3,#2B2031,#8E0E33,#B7BFA4");
			put("16", "#FFFFFF,#FFFBC7,#E0E563,#DE1972,#088386");
			put("17", "#FFFFFF,#7A9481,#230613,#E84266,#C9C00E");
			put("18", "#FFFFFF,#BDE87D,#F7FDED,#79DFE6,#52A092");
			put("19", "#FFFFFF,#CBCCBD,#30232D,#F88044,#E01A69");
			put("20", "#FFFFFF,#F9FBFF,#E7F0F1,#CEECF3,#B1CFDA");
			put("21", "#FFFFFF,#F9FBFF,#D4E1E7,#E31467,#FF8838");
			put("22", "#FFFFFF,#FFFFFF,#D3EC80,#A4CF5F,#302419");
			put("23", "#FFFFFF,#464252,#776772,#FBE3B4,#CD9DA0");
			put("24", "#FFFFFF,#F49072,#FAD494,#BD101B,#499193");
			put("25", "#FFFFFF,#C6DDD4,#EBEFD9,#5498AF,#8DD6C6");
			put("26", "#FFFFFF,#1E8D9D,#D2FF8E,#63DC0E,#03D8B7");
			put("27", "#FFFFFF,#C2DEDF,#4A3F3E,#5C7E81,#843C3C");
			put("28", "#FFFFFF,#C7DBE0,#EDF7FB,#B4C6CC,#7DA9BB");
			put("29", "#FFFFFF,#5388A6,#BDD6E5,#DBF2FF,#72ADCF");
			put("30", "#FFFFFF,#843F61,#DDD5B3,#898338,#5F343E");
			put("31", "#FFFFFF,#F8F1D3,#445564,#172D3B,#7A746D");
			put("32", "#FFFFFF,#FFFFF0,#F1DBAC,#3D475C,#663838");
			put("33", "#FFFFFF,#DAE6FE,#EBCCD3,#ADB0D2,#B89FBD");
			put("34", "#FFFFFF,#F6E0C3,#648E89,#45455A,#C1243A");
			put("35", "#FFFFFF,#FFF3B3,#3B1840,#A9D772,#2C9161");
			put("36", "#FFFFFF,#E8F0BD,#B6C16A,#4E0329,#928950");
			put("37", "#FFFFFF,#FFB29A,#FFDEAB,#73581D,#8EB04B");
			put("38", "#FFFFFF,#F8F9F5,#EFF5CB,#FF4747,#D1EB5A");
			put("39", "#FFFFFF,#C5BC08,#B61336,#281E33,#F4C521");
			put("40", "#FFFFFF,#EAE7B9,#F3A95B,#7E4D3A,#96D5C0");
			put("41", "#FFFFFF,#EBD8BC,#BB1C2F,#F7B053,#0A2838");
			put("42", "#FFFFFF,#004779,#66CAE9,#0068C1,#007889");
			put("43", "#FFFFFF,#74003E,#FDF7E8,#FD3AA2,#CC0F74");
			put("44", "#FFFFFF,#9CAC9F,#EEE9D6,#2B0117,#366F67");
			put("45", "#FFFFFF,#F3FFF0,#AFE653,#166A6E,#4DA351");
			put("46", "#FFFFFF,#C6C99D,#FAFDD6,#1E240A,#9EA368");
			put("47", "#FFFFFF,#F7F0CE,#CFB5AA,#DCE8BC,#AB526B");
			put("48", "#FFFFFF,#463429,#F0EEE2,#BD6D37,#6D584A");
			put("49", "#FFFFFF,#F2E9CD,#F5F0DC,#9BD6BE,#E4A9B0");
			put("50", "#FFFFFF,#E0DFE0,#F8F8F8,#CCD1BE,#B5C7C9");
			put("51", "#FFFFFF,#B3A9AC,#FAFAFA,#ED66A6,#CBB0B5");
			put("52", "#FFFFFF,#C5C6BE,#FFFFFF,#E8D850,#A5BD35");
			put("53", "#FFFFFF,#F6F6F6,#FFFFFF,#A1A1A1,#393939");
			put("54", "#FFFFFF,#E4E4EC,#FFFFFF,#63636D,#B0062A");
			put("55", "#FFFFFF,#F1F3F5,#FFFFFF,#145194,#49A2ED");
			put("56", "#FFFFFF,#344950,#FFFFFF,#C04B2A,#DC9844");
			put("57", "#FFFFFF,#EBECE5,#FFFFFF,#88A111,#D3D7BC");
			put("58", "#FFFFFF,#D4D5CC,#FFFFFF,#F18739,#65C0E8");
			put("59", "#FFFFFF,#F3F3F3,#FFFFFF,#443A32,#870000");
			put("60", "#FFFFFF,#E8E9E7,#FFFFFF,#000000,#FF8000");
			put("61", "#464E5C,#464E5C,#FFFFFF,#FF9933,#E3771D");
			put("62", "#FFFFFF,#F7F7F7,#FBFBFB,#0099FF,#BF865B");

			//
			put("63", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#04BFEA");
			put("64", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#0099FF");
			put("65", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#006699");
			put("66", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#66CC66");
			put("67", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#339933");
			put("68", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#669933");
			put("69", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#CCCC33");
			put("70", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#FFCC00");
			put("71", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#FF9933");
			put("72", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#FF0000");
			put("73", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#993300");
			put("74", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#FF0066");
			put("75", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#CC3366");
			put("76", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#CC00CC");
			put("77", "#FFFFFF,#C5C4B9,#EDEEE7,#3F3F3F,#9900CC");

			put("78", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#6633CC");
			put("79", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#AEC71E");
			put("80", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#EC5923");
			put("81", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#2D5C88");
			put("82", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#2997AB");

			put("83", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#719430");
			put("84", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#85742E");
			put("85", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#8BBBE0");
			put("86", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#435960");
			put("87", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#46424F");
			put("88", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#E44884");
			put("89", "#FFFFFF,#C5C4B9,#F9F9F9,#3F3F3F,#A81010");

			// put("100", "#F7F7F7,#F7F7F7,#383843,#464653,#ACCA42");

		}
	};
	
	public static final String[][] LANG_CODES = { { "tr", "Türkçe" }, { "en", "English" },
			{ "fr", "Francais" }, { "es", "Espanol" }, { "de", "Deutsch" },
			{ "it", "Italiano" }, { "ru", "Russian" } };
	
	public static final String[] ISSUE_CLS = {"task","dogum","asi"};


}
