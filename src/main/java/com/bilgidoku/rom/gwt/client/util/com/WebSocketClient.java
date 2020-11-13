package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

public class WebSocketClient{

	private final boolean supported;
	private boolean die = false;
	private String visible;

	private boolean available = false;

	private JavaScriptObject jsWebSocket;
	private boolean trying = false;

	public WebSocketClient() {
		this.supported = IsSupported();
	}

	public void die() {
		this.die = true;
		try {
			close();
		} catch (Exception e) {
		}
	}

	public void connect(String presence) {
		if (!this.supported || trying) {
			return;
		}
		Sistem.outln("Websocket connecting...");
		trying = true;
		String host = Window.Location.getHost();
		boolean isSecure = Location.getProtocol().equals("https:");
		this.jsWebSocket = createJSWebSocket((isSecure ? "wss://" : "ws://") + host + "/_romevents", this);
	}

	/**
	 * @return <code>True</code> if the WebSocket component is supported by the
	 *         current browser
	 */
	public static native boolean IsSupported() /*-{
		if ($wnd.WebSocket || $wnd.MozWebSocket) {
			return true;
		} else {
			return false;
		}
	}-*/;

	public native void _send(String message) /*-{
		if (message == null)
			return;

		this.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::jsWebSocket
				.send(message);
	}-*/;

	public native void close() /*-{
		this.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::jsWebSocket
				.close();
	}-*/;

	public native int getBufferedAmount() /*-{
		return this.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::jsWebSocket.bufferedAmount;
	}-*/;

	public native int getReadyState() /*-{
		return this.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::jsWebSocket.readyState;
	}-*/;

	public native String getURL() /*-{
		return this.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::jsWebSocket.url;
	}-*/;

	/**
	 * Creates the JavaScript WebSocket component and set's all callback
	 * handlers.
	 * 
	 * @param url
	 */
	private native JavaScriptObject createJSWebSocket(final String url, final WebSocketClient webSocket) /*-{
		var jsWebSocket = new WebSocket(url);

		jsWebSocket.onopen = function() {
			webSocket.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::onOpen()();
		}

		jsWebSocket.onclose = function(event) {
			webSocket.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::onClose(I)(event.code);
		}

		jsWebSocket.onerror = function(event) {
			webSocket.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::onError(Ljava/lang/String;)(event.data);
		}

		jsWebSocket.onmessage = function(socketResponse) {
			if (socketResponse.data) {
				webSocket.@com.bilgidoku.rom.gwt.client.util.com.WebSocketClient::onMsg(Ljava/lang/String;)(socketResponse.data);
			}
		}

		return jsWebSocket;
	}-*/;

	/**
	 * 
	 */
	private void onOpen() {
		trying = false;
		Sistem.outln("Websocket connected!");
		String sid = Cookies.getCookie("sid");
		if (sid == null) {
			Sistem.log.errln("SID is null");
			return;
		}
		String userAgent = Window.Navigator.getUserAgent();
		String host = Location.getHost();
		String referrer = Document.get().getReferrer();

		JSONArray js = new JSONArray();
		js.set(0, new JSONString(sid));
		js.set(1, new JSONString(userAgent));
		js.set(2, new JSONString(host));
		js.set(3, new JSONString(referrer));

		_send(js.toString());

		setOnline(true);

		// Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
		//
		// @Override
		// public boolean execute() {
		// if(!available)
		// return true;
		// _send("p");
		// Sistem.outln("Websocket ping!");
		// return true;
		// }
		// }, 5000);
	}

	protected void setOnline(boolean on) {
		available = on;
		RomEntryPoint.com().setbool("netonline", on);
	}

	private void send(RtCommon rt) {
		_send(rt.toString());
	}

	/**
	 * 
	 */
	private void onError(String data) {
		trying = false;
		setOnline(false);
		Portable.one.error("websocket", "Data:" + data);
	}

	/**
	 * 
	 */
	private void onClose(int code) {
		trying = false;
		setOnline(false);
		Sistem.outln("Websocket closed! " + code + " Die:" + die);
		if (!die && (code == 1001 || code == 1006)) {
			Sistem.outln("Websocket reconnecting!");
			connect("available");
		}
	}

	public void onMsg(String data) {
		trying = false;
		if (data.equals("p")) {
			// Sistem.outln("Websocket ping arrived!");
			return;
		}
		 Sistem.outln("Websocket Msg!"+data);
		JSONValue obj = JSONParser.parseLenient(data);
		JSONObject cjo = new JSONObject(obj);
		RomEntryPoint.com().post(cjo, null, null);
	}

	
}