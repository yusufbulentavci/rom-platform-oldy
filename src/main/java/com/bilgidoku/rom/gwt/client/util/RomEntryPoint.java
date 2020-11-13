package com.bilgidoku.rom.gwt.client.util;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.ClientDeep;
import com.bilgidoku.rom.gwt.client.common.ClientDeepNotification;
import com.bilgidoku.rom.gwt.client.util.akil.AkilComp;
import com.bilgidoku.rom.gwt.client.util.chat.RtPoster;
import com.bilgidoku.rom.gwt.client.util.chat.im.Messaging;
import com.bilgidoku.rom.gwt.client.util.com.Authenticator;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.ComponentManager;
import com.bilgidoku.rom.gwt.client.util.com.FrameCom;
import com.bilgidoku.rom.gwt.client.util.com.TopWindow;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.gwt.client.util.common.Login;
import com.bilgidoku.rom.gwt.client.util.common.TopCb;
import com.bilgidoku.rom.gwt.client.util.ecommerce.Ecommerce;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class RomEntryPoint implements EntryPoint {
	
	
	public static RomEntryPoint one;

	
	protected Authenticator authenticator;
	protected ComponentManager dispatcher;
	private FrameCom frameCom;

	protected boolean pageEdit = false;

	private TopCb topCb;

	public String currentLocale;

	protected String uri;

	protected int tick = 0;
	
	protected boolean ctrlKey=false;


	private final boolean top;


	private final boolean hasActionBar;


	public final String name;
	

	public RomEntryPoint(String name, boolean limit, String pageUri, boolean top, boolean hasActionBar) {
		one = this;
//		if(limit){
//			JSONObject ua = userAgentObj();
//			String brname=ua.get("name").isString().stringValue();
//			double ver=ua.get("ver").isNumber().doubleValue();
//			switch(brname){
//			case "chrome":
//				if(ver<50){
//					Window.alert("You are using Google Chrome, that's good. But you have to upgrade it to latest version");
//					Window.Location.replace("chrome://help/");
//				}
//				break;
//			case "firefox":
//				if(ver<45){
//					Window.alert("You are using Firefox, that's good. But you have to upgrade it to latest version");
//					Window.Location.replace("https://support.mozilla.org/tr/kb/firefox-son-surume-guncellemek");
//				}
//				break;
//			default:
//				Window.alert("You are using incompatible web-browser. Try to use google-chrome or firefox.");
//				Window.Location.replace("https://www.google.com/intl/tr/chrome/browser/desktop/");
//			}
//			
//			
//		}
		this.name=name;
		this.uri = pageUri;
		this.top=top;
		this.hasActionBar=hasActionBar;
	}

	@Override
	public void onModuleLoad() {
		Sistem.outln("Rom app started!");
		removeLoading();

		setlocale();

		Portable.one = new PortabilityImpl();
		this.dispatcher = new ComponentManager((ArrayList<CompFactory>) factory());
		this.frameCom = new FrameCom(dispatcher);

		ClientDeep.n = new ClientDeepNotification() {

			@Override
			public void cookieChanged() {
				frameCom.cookieChanged();
			}
		};

		precondition();

		beforeHook();

		main();

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				afterMain();
			}
		});

		postcondition();

		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

			@Override
			public boolean execute() {
				tick++;
				dispatcher.tick(tick);
				com().tick(tick);
				return true;
			}
		}, 500);
		
		forKeyboardShortCuts();
		
	}
	
	private void forKeyboardShortCuts() {
		RootPanel.get().addDomHandler(new KeyDownHandler() {

			

			@Override
			public void onKeyDown(KeyDownEvent event) {
//				if(event.isControlKeyDown()){
//					Sistem.outln("DD"+event.getNativeKeyCode());
					if(event.getNativeKeyCode()==17){
						RomEntryPoint.com().post("*wnd.ctrlbegin");
						ctrlKey=true;
					}
//				}
			}
		}, KeyDownEvent.getType());

		RootPanel.get().addDomHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==17){
					RomEntryPoint.com().post("*wnd.ctrlend");
					if(event.getNativeKeyCode()=='a'){
						RomEntryPoint.com().post("*wnd.ctrla");
					}
					ctrlKey=false;
				}
//				Sistem.outln("UP"+event.getNativeKeyCode());
//				if(event.isControlKeyDown()){
//				}
			}
		}, KeyUpEvent.getType());

	}


	protected void main() {

	}

	protected void postcondition() {
		if (!FrameCom.isTop()) {

		}
	}

	protected void precondition() {
		if (FrameCom.isTop()) {
			Sistem.outln("WND TOP");
			frameCom.set("_wndtop", "true");
		} else {
			Sistem.outln("WND NOT TOP");
			// Sistem.outln("ParentId"+frameCom.getId()+"/"+frameCom.parentId);

		}

	}

	protected void afterMain() {
	}

	private void setlocale() {
		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (locale.equals("default")) {
			currentLocale = "en";
		} else {
			currentLocale = locale;
		}
		Sistem.outln("Current locale:" + currentLocale);
	}

	protected void renderCompletedIn() {
		if (hasActionBar) {
			final ActionBar top = (ActionBar) cm().comp("+actionbar", null);
			top.getElement().getStyle().setZIndex(ActionBar.enarkaUstu);
			top.getElement().getStyle().setPosition(Position.FIXED);

			RomEntryPoint.one.addToRootPanel(top, 0, 0);

			// com.google.gwt.dom.client.Element el =
			// Document.get().getElementById("romtop");
			// if(el!=null){
			// ActionBar top=getTop();
			// el.appendChild(top.getElement());
			// }
		}
		renderCompleted();
	}

	protected void renderCompleted() {

	}

	protected void beforeHook() {
	}

	private void removeLoading() {
		if (DOM.getElementById("loading") != null)
			RootPanel.getBodyElement().removeChild(DOM.getElementById("loading"));
	}

	public TopCb getTopCb() {
		if (this.topCb == null) {
			this.topCb = createTopCb();
		}
		return this.topCb;
	}

	protected TopCb createTopCb() {
		return new TopCb() {

			@Override
			public void gotoMessaging() {
			}

			@Override
			public void gotoMails() {
			}

			@Override
			public void gotoIssue(String uri) {
			}

			@Override
			public void gotoMyInfos(String[] mine) {
			}

			@Override
			public void gotoNotifications() {
			}
		};
	}

	public void addToRealRootPanel(Widget html) {
		RootPanel.get().add(html);
		checkPageEdit();
	}

	public void addToRootPanel(String id, Widget w) {
		RootPanel.get(id).add(w);
		checkPageEdit();
	}

	private void checkPageEdit() {
		if (!pageEdit) {
			pageEdit = true;
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					renderCompletedIn();
				}
			});
		}
	}

	public void addToRootPanel(Widget vp) {
		RootLayoutPanel.get().add(vp);
		checkPageEdit();
	}

	public Widget getFirstWidgetOnRoot() {
		return RootLayoutPanel.get().getWidget(0);
	}

	public void showWaitCursor() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		// DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");

	}

	public void showDefaultCursor() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
		// DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor",
		// "default");
	}

	public void setCursor(Cursor cursor) {
		updateCursor(cursor, null);

	}

	public void addToRootPanel(Widget w, int left, int t) {
		RootPanel.get().add(w, left, t);
	}

	public void updateCursor(Cursor cursor, Element element) {
		if (element == null)
			element = RootPanel.getBodyElement();
		element.getStyle().setCursor(cursor);
	}

	public void rootPanelRemove(Widget w) {
		RootPanel.get().remove(w);
	}

	public void clear() {
		RootLayoutPanel.get().clear();
	}

	public void rootPanelSetWidgetLeftRight(DockLayoutPanel maximizeHolder, int i, Unit em, int j, Unit em2) {
		((LayoutPanel) RootLayoutPanel.get()).setWidgetLeftRight(maximizeHolder, i, em, j, em2);

	}

	public void rootPanelSetWidgetTopBottom(DockLayoutPanel maximizeHolder, int i, Unit em, int j, Unit em2) {
		((LayoutPanel) RootLayoutPanel.get()).setWidgetTopBottom(maximizeHolder, i, em, j, em2);
	}

	public Widget rootPanelGetWidget(int i) {
		return RootLayoutPanel.get().getWidget(i);
	}

	// public String cafeDlgUri() {
	// return null;
	// }

	public String getVersion() {
		return "";
	}

	public String evaluate(String str) throws RunException {
		return null;
	}

	public boolean evaluateBoolean(String string) {
		try {
			String s = evaluate(string);
			if (s == null)
				return false;
			return s.equalsIgnoreCase("true");
		} catch (RunException e) {
			Sistem.printStackTrace(e);
			return false;
		}
	}

	public native static boolean isMobile()
	/*-{
		var check = false;
		(function(a) {
			if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i
					.test(a)
					|| /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i
							.test(a.substr(0, 4)))
				check = true;
		})(navigator.userAgent || navigator.vendor || window.opera);
		return check;
	}-*/;

	public void setStatus(String text) {
		com().set("wnd.status", text);
	}

	public void startWaiting(String text) {
		com().set("wnd.status", text);
	}

	public void showSplash() {
		com().setbool("wnd.loading", true);
	}

	public void comSetStr(String set) {

	}

	public String comGetStr() {
		return null;
	}

	public ComponentManager getCommandDispatcher() {
		return dispatcher;
	}

	public static FrameCom com() {
		return one.frameCom;
	}

	public List<CompFactory> factory() {
		ArrayList<CompFactory> fact = new ArrayList<>();
		fact.add(TopWindow.factory);
		fact.add(Login.factory);
		fact.add(RtPoster.factory);
		fact.add(Messaging.factory);
		fact.add(ActionBar.factory);
		fact.add(Ecommerce.factory);
		fact.add(AkilComp.factory);

		return fact;
	}

	public String comGetAttr(String name) {
		if (name.equals("uri"))
			return uri;
		return null;
	}

	public static ComponentManager cm() {
		return one.dispatcher;
	}

	
	public static JSONObject userAgentObj(){
		return new JSONObject(_userAgent());
	}
	private native static JavaScriptObject _userAgent()/*-{

var regexps = {
              'Chrome': [ /Chrome\/(\S+)/ ],
              'Firefox': [ /Firefox\/(\S+)/ ],
              'MSIE': [ /MSIE (\S+);/ ],
              'Opera': [
                  /Opera\/.*?Version\/(\S+)/,     
                  /Opera\/(\S+)/                  
              ],
              'Safari': [ /Version\/(\S+).*?Safari\// ]
          },
          re, m, browser, version;

      var userAgent = navigator.userAgent;
      var  elements = 2;
      
      for (browser in regexps)
          while (re = regexps[browser].shift())
              if (m = userAgent.match(re)) {
                  version = (m[1].match(new RegExp('[^.]+(?:\.[^.]+){0,' + --elements + '}')))[0];
                  return {name: browser, ver: parseFloat(version)};
              }

      return null;

	}-*/;

	
	public boolean getCtrlKey(){
		return ctrlKey;
	}
}
