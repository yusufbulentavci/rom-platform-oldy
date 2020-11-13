package com.bilgidoku.rom.web.html;

public class HtmlApp {
	
	public static final String transScriptBefore = "<script type=\"text/javascript\" src=\"/_/_trans?outform=js&outvar=trns&lng=";
	public static final String scriptAfter = "\"></script>";

	private static final String start= "<!doctype html><html>  "
			+ "<head>    "
			+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">	 "
			+ "<link type=\"text/css\" rel=\"stylesheet\" href=\"/_static/css/app.css\" />"	;
			
			
	private static final String favicostart= "<link rel=\"icon\" href=\"";
//	type=\"image/x-icon\"
	private static final String localestart=  "\"><meta http-equiv=\"gwt:property\" content=\"locale=";
	
	private static final String fontFaceOpenSans = "<style>@font-face {font-family: 'Open Sans';font-style: normal;font-weight: normal;src:local('Open Sans'),local('OpenSans'),url('/_static/fonts/Open_Sans/Open_Sans.woff') format('woff');}</style>";

	private static final String afterLangHtmlNoTrans = "\">"
			+fontFaceOpenSans
			+ "<script type=\"text/javascript\" src=\"/_/siteinfo?outform=js&outvar=inf&lng=";
	

	private static final String JS_START = "<script type=\"text/javascript\">";
	private static final String JS_END = "</script>";

	private static final String startSrcScript = "<script type=\"text/javascript\" src=\"";
	private static final String widgets = "?outform=js&outvar=styl\"></script>	<script type=\"text/javascript\" src=\"/_/widgets?outform=js&outvar=wids\"></script> <script type=\"text/javascript\" src=\"/_/styles/common?outform=js&outvar=stylcmn\"></script>";

	private static final String beforeItem = "<script type=\"text/javascript\" src=\"";
	private static final String endHtml = "?outform=js&outvar=itm\"></script>    <script type=\"text/javascript\" src=\"/_public/show/show.nocache.js\"></script>  </head>  <body>  </body></html>";

	private static final String endHtmlDebug = "?outform=js&outvar=itm\"></script>    <script type=\"text/javascript\" src=\"http://localhost.com:9876/show/show.nocache.js\"></script>  </head>  <body> </body></html>";
	private static final String endHtmlEdit = "?outform=js&outvar=itm\"></script>    <script type=\"text/javascript\" src=\"/_local/boxer/boxer.nocache.js\"></script>  </head>  <body> <div id='loading' style='display:block;position:absolute;top:50%;left:50%;text-align:center;margin-left:-200px;margin-top:-200px;'><img src='/_local/images/loading.gif' /><br>Loading...</div> </body></html>";
	private static final String endHtmlEditDebug = "?outform=js&outvar=itm\"></script>    <script type=\"text/javascript\" src=\"http://localhost.com:9876/boxer/boxer.nocache.js\"></script>  </head>  <body> <div id='loading' style='display:block;position:absolute;top:50%;left:50%;text-align:center;margin-left:-200px;margin-top:-200px;'><img src='/_local/images/loading.gif' /><br>Loading...</div> </body></html>";

	public static void setUpRenderClient(StringBuilder sb, String style, String item,
			String apps, String note, String lang, String favicon) {
		sb.append(start);
		sb.append(favicostart);
		sb.append(favicon);
		sb.append(localestart);
		sb.append(lang);
		sb.append(afterLangHtmlNoTrans);
		sb.append(lang);
		sb.append(scriptAfter);
		sb.append(transScriptBefore);
		sb.append(lang);
		sb.append(scriptAfter);		

		addNote(sb, note);

		sb.append(startSrcScript);
		sb.append(style);
		sb.append(widgets);
		sb.append("");
		sb.append(beforeItem);
		sb.append(item);
		sb.append(endHtml);
	}

	private static void addNote(StringBuilder sb, String note) {
		sb.append(JS_START);
		sb.append("var note=");
		sb.append(note);
		sb.append(";");
		sb.append(JS_END);
	}

	public static void setUpRenderClientDebug(StringBuilder sb, String style, String item,
			String apps, String note, String lang, String favicon) {
		sb.append(start);
		sb.append(favicostart);
		sb.append(favicon);
		sb.append(localestart);
		sb.append(lang);
		sb.append(afterLangHtmlNoTrans);
		sb.append(lang);
		sb.append(scriptAfter);
		sb.append(transScriptBefore);
		sb.append(lang);
		sb.append(scriptAfter);		

		addNote(sb, note);
		sb.append(startSrcScript);
		sb.append(style);
		sb.append(widgets);
		// sb.append(beforeApps);
		// sb.append(apps);
		sb.append(beforeItem);
		sb.append(item);
		sb.append(endHtmlDebug);
	}

	public static void setUpRenderEditHtml(StringBuilder sb, String style, String item,
			String apps, String note, String lang, boolean debugEdit, String favicon) {
		sb.append(start);
		sb.append(favicostart);
		sb.append(favicon);
		sb.append(localestart);
		sb.append(lang);
		sb.append(afterLangHtmlNoTrans);
		sb.append(lang);
		sb.append(scriptAfter);
		sb.append(transScriptBefore);
		sb.append(lang);
		sb.append(scriptAfter);		

		sb.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"/_local/edit.css\" />");
		addNote(sb, note);
		sb.append(startSrcScript);
		sb.append(style);
		sb.append(widgets);
		// sb.append(beforeApps);
		// sb.append(apps);
		sb.append(beforeItem);
		sb.append(item);
		if(debugEdit)
			sb.append(endHtmlEditDebug);
		else
			sb.append(endHtmlEdit);
	}

	private static final String START = "<!doctype html>"
			+ "<html>"
			+ "<head>"
			+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
			+ "<link type=\"text/css\" rel=\"stylesheet\" href=\"/_static/css/app.css\" />";
	
			// "<script type=\"text/javascript\" src=\"/_public/util.js\"></script>"

//	private static final String FB = "<script>(function(d, s, id) {\n"
//			+ "var js, fjs = d.getElementsByTagName(s)[0];\n"
//			+ "if (d.getElementById(id)) return;\n"
//			+ "js = d.createElement(s); js.id = id;\n"
//			+ "js.src = \"//connect.facebook.net/tr_TR/all.js#xfbml=1\";\n"
//			+ "fjs.parentNode.insertBefore(js,fjs);\n"
//			+ "}(document, 'script', 'facebook-jssdk'));</script>";
	
	private static final String FB = "";
	
	
	private static final String WG = "<script type=\"text/javascript\" src=\"/_/widgets?outform=js&outvar=wids\"></script>";
	private static final String JS_APP = "<script type=\"text/javascript\" src=\"/_public/a/a.nocache.js\"></script>";
	private static final String JS_APP_DEBUG = "<script type=\"text/javascript\" src=\"http://localhost.com:9876/a/a.nocache.js\"></script>";
	private static final String HISTORY_FRAME = "<iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" tabIndex='-1' style=\"position:absolute;width:0;height:0;border:0\"></iframe>";

	public static void setupRenderServer(boolean debug, String generated, String store,
			String openGraph, String favicon, StringBuilder sb) {

		sb.append(START);
		sb.append("<link rel=\"icon\" href=\"");
		sb.append(favicon);
		sb.append("\">\n");
		sb.append(transScriptBefore);
		sb.append("en");
		sb.append(scriptAfter);
		
		sb.append(openGraph);
		sb.append("</head>");
		sb.append("<body>");
		sb.append(HISTORY_FRAME);
		sb.append(generated);
		sb.append(WG);
		sb.append(JS_START);
		sb.append("var st=");
		sb.append(store);
		sb.append(";");
		sb.append(JS_END);
		if (debug)
			sb.append(JS_APP_DEBUG);
		else
			sb.append(JS_APP);
		sb.append("</body></html>");
		// syso(sb.toString());
	}

}
