package com.bilgidoku.rom.gwt.client.util.com;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageEvent;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Frame;

abstract class FrameResponse {
	long delivered = Sistem.millis();
	private int msgId;

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getMsgId() {
		return msgId;
	}

	abstract public void response(JSONObject cjo);

}

/**
 *  Her frame kendi icinde bir dunya. Her frame icin bir rom client uygulamasi yukleniyor.
 *  Hangi uygulamanin yuklenecegi url ile parametre olarak romframe'e veriliyor.
 *  
 *  Frame'in durumu iki veriyapisi uzerinden tutuluyor
 *  i. SessionStoge
 *  	Bir tarayici uzerindeki tum frameler bunu paylasiyor
 *  	Ortak alan WebStorage/session; webstorage key-value seklinde degerleri tutarken 
 *   	storage uzerindeki degisimleri de event olarak bildiriyor.
 *   	Storage uzerinde yapilan degisiklikler tum frame'lere bir pipeline uzerinden broadcast edilmis oluyor.
 *      Mesajlasma bunun uzerinden oluyor
 *  ii. Yerel
 *      Ornegin bir frame'in master olup olmadigina yerel durum gosteriyor. Master ise websocket aciyor
 *      Yerellerin key degeri '_' ile basliyor
 *  
 *  
 *  Mesajlasma:
 *   Mesajlasma session storage'a key olarak SESSION_ONLINE_MESSAGE = "s.o.m" verilerek yapiliyor.
 *   Tarayicilar tek thread calistiklari icin threading sorunu olmuyor.
 *  
 *   FrameCom frame'ler arasinda iletisimi sagliyor.
 *   
 *   Her frame gelen mesajlari analiz ediyor kendisi ile ilgili ise aliyor.
 *   
 *   Gelen mesajlar @see ComponentManager a yonlendiriliyor
 *   
 *   FrameCom ayni zamanda html eventlerini de yonetiyor
 *    *   Iki tur html mesaj mumkun
 *   Birincisi java mesajlari
 *   				"rzid" : rzid,
					"changeid" : changeid,
					"routine" : routine,
					"type" : type,
					"value" : value,
					"mk" : false,
					"params" : params
 *   
 *   Ikincisi ile mouse-keyboard mesajlari
 *   
 *  HtmlEvent'ler bu sinifin calup fonksiyonu ile html component'ten alinir 
 *  session storage'a ugramadan dogrudan component dispatch'e girer
 *  Bunlara cmd olarak *html verilir.
 *  show ve a uygulamalari bunlari alir ve @see Runner a event olarak atar   
 *   
 * 
 * 
 * 
 * @author rompg
 *
 */
public class FrameCom implements com.google.gwt.storage.client.StorageEvent.Handler {
	private static final String LAST_USED_FRAME_ID = "l.u.f.i";
	private static final String SESSION_ONLINE_NODE = "s.o.n";
	static final String SESSION_ONLINE_TIME = "_s.o.t";
	private static final String SESSION_ONLINE_MESSAGE = "s.o.m";

	// private static final String SESSION_NEEDS_AUTH = "s.n.a";
	// private static final String SESSION_NEEDS_CID = "s.n.c";

	private static int FrameMsgId = 0;

	private final Integer frameId;

//	private Integer parentId = null;

	private Map<Integer, FrameResponse> waitingResponse = new HashMap<>();

	private Storage paylasilan = Storage.getSessionStorageIfSupported();
//	private Storage paylasilan = Storage.getLocalStorageIfSupported();
	
	private Map<String, String> frameYerel = new HashMap<>();
	Map<String, String> change = new HashMap<String, String>();

	private final ComponentManager commandDispatcher;

	public FrameCom(ComponentManager commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
		String lu = paylasilan.getItem(LAST_USED_FRAME_ID);
		if (lu == null) {
			paylasilan.setItem(LAST_USED_FRAME_ID, "0");
			frameId = 0;
		} else {
			int id = Integer.parseInt(lu);
			frameId = id + 1;
			paylasilan.setItem(LAST_USED_FRAME_ID, "" + frameId);
		}
		Storage.addStorageEventHandler(this);
		initHtmlEvents();
//		Integer pid = getParentId();
//		if (pid != null && pid.intValue() != frameId)
//			parentId = pid;
		cookieChanged();
	}

	public void tick(int tick) {

	}

	// public void changeData(String name, String val) {
	// String newval = ClientUtil.toNullStr(val);
	// String oldval = pipeline.getItem(name);
	// pipeline.setItem(name, newval);
	// oldval = ClientUtil.fromNullStr(oldval);
	//
	// commandDispatcher.dataChanged(name, oldval, val);
	// }

//	public void postToParent(JSONObject cjo, FrameResponse resp) {
//		post(cjo, parentId, resp);
//	}

	public void post(String cmd, String... more) {
		JSONObject jo = new JSONObject();
		jo.put("cmd", cmd);
		if (more != null)
			for (int i = 0; i < more.length; i = i + 2){
				if(more[i + 1]!=null)
					jo.put(more[i], more[i + 1]);
			}

		post(jo, null, null);
	}

	public void post(final JSONObject cjo, Integer toFrame, FrameResponse resp) {
		cjo.put("fr", frameId);
		if (toFrame != null)
			cjo.put("tofr", toFrame);
		if (resp != null) {
			resp.setMsgId(FrameMsgId++);
			cjo.put("fmid", resp.getMsgId());

			waitingResponse.put(resp.getMsgId(), resp);
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				paylasilan.setItem(SESSION_ONLINE_MESSAGE, cjo.toString());
				// process(cjo);
			}
		});

	}

	@Override
	public void onStorageChange(StorageEvent event) {
		Sistem.outln("------");

		String key = event.getKey();
		if (key.equals(SESSION_ONLINE_MESSAGE)) {
			String val = paylasilan.getItem(SESSION_ONLINE_MESSAGE);
			JSONValue obj = JSONParser.parseLenient(val);
			JSONObject cjo = new JSONObject(obj);
			process(cjo);
		} else {
			Sistem.outln("FC: Add change ==>" + key);
			change.put(key, event.getNewValue());
		}

		// String cmd = cjo.optString("cmd");
		// if (cmd != null) {
		// if (cmd.equals("you")) {
		// long time;
		// try {
		// time = cjo.getLong("time");
		// SessionManage.one.rtSaysYouAre(cjo.optString("cid"), time);
		// } catch (RunException e) {
		// Sistem.printStackTrace(e);
		// }
		//
		// return;
		// } else if (cmd.equals("waiting.changed")) {
		// try {
		// String app = cjo.getString("app");
		// String code = cjo.getString("code");
		// String[] inref = cjo.getStringArray("inref");
		// int times = cjo.getInt("times");
		// RomEntryPoint.one.rtWaiting(cmd, app, code, inref, times);
		//
		// } catch (RunException e) {
		// Sistem.printStackTrace(e);
		// }
		//
		// return;
		// } else {
		// try {
		// Xmpp.dispatch.incoming((com.google.gwt.json.client.JSONObject)
		// cjo.getNative());
		// } catch (RunException e) {
		// Sistem.printStackTrace(e, "RtMsg");
		// }
		// }
		// }

		// }
	}

	private void process(JSONObject cjo) {
		try {
			if (cjo.optBoolean("resp", false)) {
				int fid = cjo.getInt("fr");
				if (fid != frameId) {
					return;
				}
				int fmid = cjo.getInt("fmid");
				FrameResponse fr = waitingResponse.get(fmid);
				if (fr == null) {
					return;
				}
				waitingResponse.remove(fmid);
				fr.response(cjo);
				return;
			}
			Integer toFrame = cjo.optInt("tofr");
			if (toFrame != null && !toFrame.equals(frameId))
				return;
			commandDispatcher.dispatch(cjo);

		} catch (RunException e) {
			Sistem.printStackTrace(e);
		}

	}

	public void response(JSONObject cjo) {
		cjo.put("resp", true);
		paylasilan.setItem(SESSION_ONLINE_MESSAGE, cjo.toString());
		process(cjo);

	}

	private native void initHtmlEvents()/*-{

		var that = this;

		$wnd.rom = {
			calup : null,
			tje : function(rzid, changeid, routine, type, event, params, value) {
				var obj = {
					"rzid" : rzid,
					"changeid" : changeid,
					"routine" : routine,
					"type" : type,
					"value" : value,
					"mk" : false,
					"params" : params
				};
				if (event.target != null) {
					obj.targettag = event.target.tagName.toLowerCase();
					if (event.target.id != null)
						obj.targetid = event.target.id;
				}
				this.calup(obj);
			},
			je : function(rzid, changeid, routine, event, params, value) {
				this.tje(rzid, changeid, routine, event.type, event, params,
						value);
			},
			mke : function(rzid, changeid, routine, event, params) {
				//var wnd=(parent==null)?window:parent;
				var obj = {
					"rzid" : rzid,
					"changeid" : changeid,
					"routine" : routine,
					"type" : event.type,
					"value" : null,
					"mk" : true,
					"params" : params,
					"altkey" : event.altKey,
					"button" : event.button,
					"clientx" : event.clientX,
					"clienty" : event.clientY,
					"ctrlkey" : event.ctrlKey,
					"metakey" : event.metaKey,
					"screenx" : event.screenX,
					"screeny" : event.screenY,
					"shiftkey" : event.shiftKey
				};
				if (event.target != null) {
					obj.targettag = event.target.tagName.toLowerCase();
					if (event.target.id != null)
						obj.targetid = event.target.id;
				}
				this.calup(obj);
			}
		};

		$wnd.rom.calup = $entry(function(obj) {
			that.@com.bilgidoku.rom.gwt.client.util.com.FrameCom::calup(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
		});

		$wnd.rom.getId = $entry(function() {
			return that.@com.bilgidoku.rom.gwt.client.util.com.FrameCom::getId()();
		});

		$wnd.rom.getAttr = $entry(function(str) {
			return that.@com.bilgidoku.rom.gwt.client.util.com.FrameCom::getAttr(Ljava/lang/String;)(str);
		});

		$wnd.rom.setStr = $entry(function(str) {
			that.@com.bilgidoku.rom.gwt.client.util.com.FrameCom::setStr(Ljava/lang/String;)(str);
		});

		$wnd.rom.getStr = $entry(function() {
			return that.@com.bilgidoku.rom.gwt.client.util.com.FrameCom::getStr()();
		});

	}-*/;

	public void calup(com.google.gwt.core.client.JavaScriptObject we) {
		com.google.gwt.json.client.JSONObject jo = new com.google.gwt.json.client.JSONObject(we);
		JSONObject cjo = new JSONObject(jo);
		cjo.put("cmd", "*html");
		process(cjo);

	}

	public Integer getId() {
		return frameId;
	}

	public static String getChildAttr(Frame f, String str) {
		IFrameElement ife = f.getElement().cast();
		return getChildAttr(ife, str);
	};

	private static native String getChildAttr(JavaScriptObject we, String str)/*-{
		return we.contentWindow.rom.getAttr(str);
	}-*/;

	public static boolean isReady(Frame f) {
		IFrameElement ife = f.getElement().cast();
		return isReady(ife);
	}

	private static native boolean isReady(JavaScriptObject we)/*-{
		return (we.contentWindow.rom);
	}-*/;

	public String getAttr(String name) {
		return RomEntryPoint.one.comGetAttr(name);
	}

	public void setStr(String str) {
		RomEntryPoint.one.comSetStr(str);
	}

	public String getStr() {
		return RomEntryPoint.one.comGetStr();
	}

	public static String getChildStr(Frame f) {
		IFrameElement ife = f.getElement().cast();
		return getChildStr(ife);
	};

	private static native String getChildStr(JavaScriptObject we)/*-{
		return we.contentWindow.rom.getStr();
	}-*/;

	public static void setChildStr(Frame f, String text) {
		IFrameElement ife = f.getElement().cast();
		setChildStr(ife, text);
	};

	private static native void setChildStr(JavaScriptObject we, String text)/*-{
		we.contentWindow.rom.setStr(text);
	}-*/;

	public static String getId(Frame f) {
		IFrameElement ife = f.getElement().cast();
		return getId(ife);
	};

	private static native String getId(JavaScriptObject we)/*-{
		return we.contentWindow.rom.getId();
	}-*/;

	private static native Integer getParentId()/*-{
		var win = $wnd;
		if (win.opener) {
			win = win.opener;
			if (win.rom)
				return win.rom.getId();
		}
		if (win.parent) {
			win = win.parent;
			if (win.rom)
				return win.rom.getId();
		}
		return null;
	}-*/;

	public static native boolean isTop()/*-{
		var topwin = $wnd.top;
		var win = $wnd;
		//if ($wnd.opener && $wnd.opener.top && $wnd.opener.top.rom) {
		//	win=$wnd.opener.top;
		//}

		return (topwin.rom.getId() == $wnd.rom.getId());
	}-*/;

	public static native String getParentAttr(String name)/*-{
		var win = $wnd;
		if (win.opener) {
			win = win.opener;
			if (win.rom)
				return win.rom.getAttr(name);
		}
		if (win.parent) {
			win = win.parent;
			if (win.rom)
				return win.rom.getAttr(name);
		}
		return null;
	}-*/;

	public boolean containsKey(String need) {
		if (need.charAt(0) == '_')
			return frameYerel.containsKey(need);
		else
			return ClientUtil.checkNotNull(paylasilan.getItem(need));
	}

	public void setbool(String key, boolean val) {
		if (val)
			set(key, "true");
		else
			reset(key);
	}

	public void set(String... keyvals) {
		for (int i = 0; i < keyvals.length; i += 2) {

			String key = keyvals[i];
			String val = keyvals[i + 1];
			Sistem.outln("FC: Set ::>" + key + ":" + val);
			boolean isnull = val == null;
			if (key.startsWith("_")) {
				if (isnull)
					localsRemove(key);
				else
					localsPut(key, val);
			} else {
				if (keyvals[i + 1] == null) {
					paylasilan.removeItem(key);
					// change.put(key, "NULL");
				} else {
					paylasilan.setItem(key, val);
					// change.put(key, val);
				}
			}
		}
		commandDispatcher.setDirty();
	}

	private void localsPut(String key, String val) {
		frameYerel.put(key, val);
		change.put(key, val);
	}

	private void localsRemove(String key) {
		frameYerel.remove(key);
		change.put(key, "NULL");
	}

	public void reset(String... keys) {
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			if (key.startsWith("_")) {
				localsRemove(key);
			} else {
				paylasilan.removeItem(key);
			}
		}
		commandDispatcher.setDirty();
	}

	public String get(String key) {
		if (key.startsWith("_")) {
			return frameYerel.get(key);
		}
		return paylasilan.getItem(key);
	}

	public int optInt(String key, int def) {
		String val = get(key);
		if (val == null)
			return def;
		return Integer.parseInt(val);
	}

	public boolean getBool(String string) {
		String s = get(string);

		return s != null && (s.equals("1") || s.equalsIgnoreCase("true"));
	}

	public void cookieChanged() {
		checkCookieChange("user");
		checkCookieChange("roles");
		checkCookieChange("cname");
		checkCookieChange("cid");
		checkCookieChange("host");
		checkCookieChange("presence");
		checkCookieChange("isauth");
	}

	private void checkCookieChange(String key) {
		String c = Cookies.getCookie(key);
		String i = get(key);
		if (c == null) {
			if (i != null) {
				reset(key);
			}
		} else if (i == null || !c.equals(i)) {
			set(key, c);
		}
	}

	public String getMail() {
		String user = RomEntryPoint.com().get("user");
		String host = RomEntryPoint.com().get("host");
		return user + "@" + host;
	}

	public String getMyDir() {
		String cid = RomEntryPoint.com().get("cid");

		return (cid == null) ? null : "/f/users/" + cid.substring("/_/co/".length());
	}

}
