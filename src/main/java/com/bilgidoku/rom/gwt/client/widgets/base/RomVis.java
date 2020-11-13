package com.bilgidoku.rom.gwt.client.widgets.base;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.Window;

public class RomVis {

	private static Set<String> waitingInjection = new HashSet<String>();
	private static Set<String> injected = new HashSet<String>();
	private static Runnable run = null;

	/**
	 * Should be called once
	 */
	public static void init() {
		RomVis.nativeCreateCallback();
	}
	

	public static void loadApi(Runnable r, String... module){
		
		if(run!=null){
			Window.alert("Waiting to load other library, sorry");
			return;
		}
		run=r;
		for (String string : module) {
			injectJsapi(string);
		}
	}
	
	private static void injectJsapi(String module) {
		if (injected.contains(module))
			return;
		if (waitingInjection.contains(module))
			return;
		waitingInjection.add(module);

		Document doc = Document.get();
		// String key = (apiKey == null) ? "" : ("key=" + apiKey + "&");
		// hostname = (hostname == null) ? "www.google.com" : hostname;
		// String src = getProtocol() + "//" + hostname + "/jsapi?" + key
		// + "callback=__gwt_AjaxLoader_onLoad";

		String src="http://home.mlos.net/_static/js/"+module+".js";
		
		ScriptElement script = doc.createScriptElement();
		script.setSrc(src);
		script.setType("text/javascript");
		doc.getBody().appendChild(script);
	}

	private RomVis() {
		// Do not allow this class to be instantiated.
	}

	private static native boolean nativeCreateCallback() /*-{
		$wnd._rom_module_onLoad = function(module) {
			@com.bilgidoku.rom.gwt.client.widgets.base.RomVis::onLoadCallback(Ljava/lang/String;)(module);
		}
		// The application must wait for a callback.
		return false;
	}-*/;

	private static void onLoadCallback(String module) {
		waitingInjection.remove(module);
		injected.add(module);
		if(run!=null)
			run.run();
		
	}

	
}