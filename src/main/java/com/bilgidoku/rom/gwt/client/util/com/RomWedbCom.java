package com.bilgidoku.rom.gwt.client.util.com;

public class RomWedbCom {

	private FrameCom frameCom;
	
	
	
	

}

// package com.bilgidoku.rom.gwt.client.util.com;
//
// import java.util.HashMap;
// import java.util.Map;
//
// import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
// import com.bilgidoku.rom.gwt.client.util.chat.Xmpp;
// import com.bilgidoku.rom.gwt.client.util.common.Authenticator;
// import com.bilgidoku.rom.gwt.client.util.common.SessionHook;
// import com.bilgidoku.rom.gwt.client.util.common.SessionManage;
// import com.bilgidoku.rom.min.Sistem;
// import com.bilgidoku.rom.shared.Portable;
// import com.bilgidoku.rom.shared.RunException;
// import com.bilgidoku.rom.shared.RunnerCom;
// import com.bilgidoku.rom.shared.json.JSONObject;
// import com.google.gwt.core.client.JavaScriptObject;
// import com.google.gwt.core.client.Scheduler;
// import com.google.gwt.core.client.Scheduler.RepeatingCommand;
// import com.google.gwt.core.client.Scheduler.ScheduledCommand;
// import com.google.gwt.dom.client.IFrameElement;
// import com.google.gwt.http.client.UrlBuilder;
// import com.google.gwt.json.client.JSONParser;
// import com.google.gwt.json.client.JSONValue;
// import com.google.gwt.storage.client.Storage;
// import com.google.gwt.storage.client.StorageEvent;
// import com.google.gwt.user.client.Window;
// import com.google.gwt.user.client.Window.Location;
// import com.google.gwt.user.client.ui.Frame;
//
// public class RomWebCom {
//
// private static RomWebCom instance;
//
//
// public RomEntryPoint app;
// private final RunnerCom com;
// private final String id = System.currentTimeMillis() + "";
// private int tickno = 0;
//
// private boolean master = false;
//
// private boolean beOnline;
//
// private Top top=null;
//
// final FrameCom frameCom;
//
//
//
//
// public static void create(RomEntryPoint app, RunnerCom com, boolean
// livesInAuth) throws RunException {
// instance = new RomWebCom(app, com, livesInAuth);
// }
//
// private RomWebCom(RomEntryPoint app, RunnerCom com, final boolean beOnline)
// throws RunException {
// if (instance != null)
// throw new RunException("RomWebCom was constructed!");
// this.app = app;
// this.com = com;
// if(amitop()){
// top=new Top(app.getAp().needsCid, app.getAp().livesInAuth);
// }
// this.beOnline = beOnline;
// frameId = Location.getParameter("frameid");
// // this.parentId = parentId;
// instance = this;
//
// // if (com == null) {
// // return;
// // }
// initHtmlEvents();
// initFrameEvents();
//
// Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
//
// @Override
// public boolean execute() {
//
// try {
// if (RomWebCom.this.com == null)
// return true;
// RomWebCom.this.com.tick(tickno++);
//
// // if ((tickno % 25) == 0)
// // PassEventHandler.passEvent();
//
// if (top!= null && beOnline && (tickno % 5) == 0)
// top.checkMaster();
//
// } catch (RunException e1) {
// Portable.one.error(e1);
// }
// return true;
// }
//
// }, 200);
//
// if (frameId != null) {
// Scheduler.get().scheduleDeferred(new ScheduledCommand() {
// @Override
// public void execute() {
// parentWeAreReady(frameId);
// }
// });
// }
//
// SessionManage.one.addHook(new SessionHook() {
//
// @Override
// public boolean login(boolean initial, String cid, String userName, String
// hostName) {
// beonline();
// return false;
// }
//
// @Override
// public boolean logout(boolean initial) {
// if (!initial)
// die();
// return false;
// }
//
// @Override
// public boolean error(boolean initial, String errorText, Throwable
// errException) {
// return false;
// }
//
// });
//
// }
//
//
//
// public static void calup(com.google.gwt.core.client.JavaScriptObject we) {
// com.google.gwt.json.client.JSONObject jo = new
// com.google.gwt.json.client.JSONObject(we);
// JSONObject cjo = new JSONObject(jo);
// processingStarts(cjo, "html");
// }
//
// public static void childCall(com.google.gwt.core.client.JavaScriptObject we)
// {
// com.google.gwt.json.client.JSONObject jo = new
// com.google.gwt.json.client.JSONObject(we);
// JSONObject cjo = new JSONObject(jo);
// processingStarts(cjo, "child");
// }
//
// protected static void processingStarts(JSONObject cjo, String typ) {
// try {
// cjo.put("typ", typ);
// } catch (RunException e) {
// Sistem.printStackTrace(e);
// }
// try {
// if (instance.com != null)
// instance.com.fireEvent(cjo);
// } catch (RunException e) {
// Portable.one.error(e);
// }
// }
//
//
// private native void initHtmlEvents()/*-{
//
// var that = this;
//
// $wnd.rom={
// calup:null,
// tje:function(rzid,changeid,routine,type,event,params,value) {
// var obj={
// "rzid":rzid,
// "changeid":changeid,
// "routine":routine,
// "type":type,
// "value":value,
// "mk":false,
// "params":params
// };
// if(event.target!=null){
// obj.targettag=event.target.tagName.toLowerCase();
// if(event.target.id!=null)
// obj.targetid=event.target.id;
// }
// this.calup(obj);
// },
// je:function(rzid,changeid,routine,event,params,value) {
// this.tje(rzid,changeid,routine,event.type,event,params,value);
// },
// mke:function(rzid,changeid,routine,event,params) {
// //var wnd=(parent==null)?window:parent;
// var obj={
// "rzid":rzid,
// "changeid":changeid,
// "routine":routine,
// "type":event.type,
// "value":null,
// "mk":true,
// "params":params,
// "altkey":event.altKey,
// "button":event.button,
// "clientx":event.clientX,
// "clienty":event.clientY,
// "ctrlkey":event.ctrlKey,
// "metakey":event.metaKey,
// "screenx":event.screenX,
// "screeny":event.screenY,
// "shiftkey":event.shiftKey
// };
// if(event.target!=null){
// obj.targettag=event.target.tagName.toLowerCase();
// if(event.target.id!=null)
// obj.targetid=event.target.id;
// }
// this.calup(obj);
// }
// };
// $wnd.rom.calup=$entry(@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::calup(Lcom/google/gwt/core/client/JavaScriptObject;));
//
//
// $wnd.rom.setStr = $entry(function(str) {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::setStr(Ljava/lang/String;)(str);
// });
//
// $wnd.rom.focus = $entry(function() {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::focus()();
// });
//
// $wnd.rom.getStr = $entry(function() {
// return that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::getStr()();
// });
//
// $wnd.rom.getFeature = $entry(function(str) {
// return
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::getFeature(Ljava/lang/String;)(str);
// });
//
// $wnd.rom.ccReady = $entry(function(str) {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::ccReady(Ljava/lang/String;)(str);
// });
//
// $wnd.rom.ccShowSplash = $entry(function(fram) {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::ccShowSplash(Ljava/lang/String;)(fram);
// });
//
// $wnd.rom.ccSetStatus = $entry(function(fram,str) {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::ccSetStatus(Ljava/lang/String;Ljava/lang/String;)(fram,str);
// });
//
// $wnd.rom.ccStartWaiting = $entry(function(fram,str) {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::ccStartWaiting(Ljava/lang/String;Ljava/lang/String;)(fram,str);
// });
//
// $wnd.rom.ccLogin = $entry(function(fram,face) {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::ccLogin(Ljava/lang/String;Ljava/lang/String;)(fram,face);
// });
//
// $wnd.rom.ccSetAttr = $entry(function(fram,att,val) {
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::ccSetAttr(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(fram,att,val);
// });
//
// $wnd.rom.ccGetAttr = $entry(function(fram,att) {
// return
// that.@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::ccGetAttr(Ljava/lang/String;Ljava/lang/String;)(fram,att);
// });
//
//
// //
// $wnd.rom.focusItem=$entry(@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::focusItem(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;));
// //
// $wnd.rom.setItem=$entry(@com.bilgidoku.rom.gwt.client.util.com.RomWebCom::setItem(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;));
// }-*/;
//
// // static void focusItem(String callerFrameId, String cls, String uri) {
// // RomFrame fr = instance.frames.get(callerFrameId);
// // fr.focusItem(cls, uri);
// // }
//
// static void setItem(String callerFrameId, String cls, String uri) {
// RomFrame fr = instance.frames.get(callerFrameId);
// fr.setItem(cls, uri);
// }
//
// void ccReady(String callerFrameId) {
// RomFrame fr = frames.get(callerFrameId);
// if (fr == null) {
// Window.alert("FrameId:" + callerFrameId + " null");
// return;
// }
//
// fr.ready();
// }
//
// void ccShowSplash(String callerFrameId) {
// app.comShowSplash();
// }
//
// void ccSetStatus(String callerFrameId, String status) {
// app.comSetStatus(status);
// }
//
// void ccStartWaiting(String callerFrameId, String status) {
// app.comStartWaiting(status);
// }
//
// void ccLogin(String callerFrameId, String face) {
// UrlBuilder builder1 = Location.createUrlBuilder();
// String redirect = builder1.buildString();
//
// UrlBuilder builder =
// Location.createUrlBuilder().setPath("/_public/login.html");
// if (face != null && face.equals("autologin"))
// builder.setParameter("autologin", "autologin");
//
// builder.setParameter("redirect", redirect);
//
// redirect(builder.buildString());
// }
//
// void ccSetAttr(String callerFrameId, String attr, String val) {
// attrs.put(attr, val);
// }
//
// String ccGetAttr(String callerFrameId, String attr) {
// return attrs.get(attr);
// }
//
// void setStr(String set) {
// app.comSetStr(set);
// }
//
// String getStr() {
// return app.comGetStr();
// }
//
// String getFeature(String set) {
// return app.comGetFeature(set);
// }
//
// void focus() {
// app.comFocus();
// }
//
// public static String getChildStr(Frame f) {
// IFrameElement ife = f.getElement().cast();
// return getChildStr(ife);
// };
//
// public static String getChildFeature(Frame f, String feature) {
// IFrameElement ife = f.getElement().cast();
// return getChildFeature(ife, feature);
// };
//
// public static void setChildStr(Frame f, String text) {
// IFrameElement ife = f.getElement().cast();
// setChildStr(ife, text);
// };
//
// public static void focus(Frame f) {
// IFrameElement ife = f.getElement().cast();
// focus(ife);
// };
//
// private static native String getChildStr(JavaScriptObject we)/*-{
// return we.contentWindow.rom.getStr();
// }-*/;
//
// private static native String getChildFeature(JavaScriptObject we, String
// text)/*-{
// return we.contentWindow.rom.getFeature(text);
// }-*/;
//
// private static native void setChildStr(JavaScriptObject we, String text)/*-{
// we.contentWindow.rom.setStr(text);
// }-*/;
//
// private static native void focus(JavaScriptObject we)/*-{
// we.contentWindow.rom.focus();
// }-*/;
//
// public static native void parentFocusesItem(String cls, String uri)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.focusItem(cls, uri);
// }-*/;
//
// public static native void parentSetItem(String cls, String uri)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.setItem(cls, uri);
// }-*/;
//
// private static native void parentWeAreReady(String frameId)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.ccReady(frameId);
// }-*/;
//
// public void parentSetStatus(String status) {
// parentSetStatus(frameId, status);
// }
//
// private static native void parentSetStatus(String frameId, String status)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.ccSetStatus(frameId, status);
// }-*/;
//
// public void parentShowSplash() {
// parentShowSplash(frameId);
// }
//
// private static native void parentShowSplash(String frameId)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.ccShowSplash(frameId);
// }-*/;
//
// public void parentStartWaiting(String status) {
// parentStartWaiting(frameId, status);
// }
//
// private static native void parentStartWaiting(String frameId, String
// status)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.ccStartWaiting(frameId, status);
// }-*/;
//
// public void parentLogin(String face) {
// parentLogin(frameId, face);
// }
//
// private static native void parentLogin(String frameId, String face)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.ccLogin(frameId, face);
// }-*/;
//
// public void parentSetAttr(String att, String val) {
// parentSetAttr(frameId, att, val);
// }
//
// private static native void parentSetAttr(String frameId, String att,
// String val)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// tw.rom.ccSetAttr(frameId, att, val);
// }-*/;
//
// public String parentGetAttr(String att) {
// return parentGetAttr(frameId, att);
// }
//
// private static native String parentGetAttr(String frameId, String att)/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// return tw.rom.ccGetAttr(frameId, att);
//
// }-*/;
//
// public static native boolean amitop()/*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
//
// return tw==$wnd.self;
// }-*/;
//
//
// private static void frameEvent(JavaScriptObject fes) throws RunException {
// com.google.gwt.json.client.JSONObject jo = new
// com.google.gwt.json.client.JSONObject(fes);
//
// // String func = jo.get("romfuncid").isString().stringValue();
// // if (func == null) {
// // Sistem.errln("romfuncid is absent");
// // return;
// // }
// // AsyncMethod<JSONObject, String> m = childDialogs.remove(func);
// // m.run(new JSONObject(jo));
// //
// JSONObject cjo = new JSONObject(jo);
// try {
// cjo.put("type", "frame");
//
// } catch (RunException e) {
// Sistem.printStackTrace(e);
// }
//
// processingStarts(cjo, "frame");
// }
//
// private static native void initFrameEvents() /*-{
// function postMessageListener(e) {
// var curUrl = $wnd.location.protocol + "//" + $wnd.location.hostname;
// if (e.origin !== curUrl)
// return; // security check to verify that we receive event from trusted source
// @com.bilgidoku.rom.gwt.client.util.com.RomWebCom::frameEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(e.data);
// // call function with the name
// }
//
// // Listen to message from child window
//
// if (navigator.userAgent.toLowerCase().indexOf("msie") >= 0) {
// // fucking IE
// $wnd.attachEvent("onmessage", postMessageListener, false);
// } else {
// // "Normal" browsers
// $wnd.addEventListener("message", postMessageListener, false);
// }
// }-*/;
//
// // public static boolean isOne() {
// // return instance.me.name.equals("one");
// // }
//
// public static native void redirectTop(String newUrl) /*-{
// var win = $wnd;var tw = win;while (win = win.opener) {tw = win;}tw=tw.top;
// tw.location.assign(newUrl);
// }-*/;
//
// public void redirect(String sucRedirect) {
// redirectTop(sucRedirect);
// }
//
//
// }
