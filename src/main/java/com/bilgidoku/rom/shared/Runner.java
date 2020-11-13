package com.bilgidoku.rom.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.code.Animation;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.CodeRepo;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.shared.expr.Evaluator;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONNumber;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;
import com.bilgidoku.rom.shared.render.Css;
import com.bilgidoku.rom.shared.state.Scope;
import com.bilgidoku.rom.shared.state.State;
import com.bilgidoku.rom.shared.state.Trigger;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;

public class Runner implements JsonTransfer {
	// public final static TagMap htmlTagMap = new HtmlTagMap();
	
//	Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
//
//		@Override
//		public boolean execute() {
//
//			try {
//				if(RomWebCom.this.com==null)
//					return true;
//				RomWebCom.this.com.tick(tickno++);
//
//				// if ((tickno % 25) == 0)
//				// PassEventHandler.passEvent();
//
//				if (beOnline && (tickno % 5) == 0)
//					checkMaster();
//
//			} catch (RunException e1) {
//				Portable.one.error(e1);
//			}
//			return true;
//		}
//
//	}, 200);

	private final MinRequest request;
	private int runZoneSeq = 0;
	private int htmlIdSeq = 0;
	private int htmlEventSeqId = 0;

	private List<RunZone> runzones = new ArrayList<RunZone>();
	private List<WaitingZone> waitingZones = new ArrayList<WaitingZone>();

	private final State pageState;
	private final Scope scope;

	// private JSONObject codes;

	private final Css css;
	// private final JSONObject widgets;
	private final CodeRepo codeRepo;
	private Code codes;

	private final Map<String, Trigger> htmlEvents = new HashMap<String, Trigger>();
//	private final Object session;
//	private final Object domain;
	private final Map<String, RunZone> editables = new HashMap<String, RunZone>();
	private Map<String, WidgetInstance> wins;
	
	private final int bw;
	private final String lang;
	private final JSONArray langs;
	private JSONObject userAgent;
	private boolean mobile;

	public List<String[]> watchOut() {
		List<String[]> ret = new ArrayList<String[]>();
		pageState.watchOut("ps", ret);
		for (RunZone rzs : runzones) {
			new RenderCallState(rzs).watchOut(ret);
		}

		return ret;
	}

	@Override
	public JSONValue store() throws RunException {
		JSONObject jo = new JSONObject();
		jo.put("rzseq", new JSONNumber(runZoneSeq));
		jo.put("htmlidseq", new JSONNumber(htmlIdSeq));
		jo.put("htmlevseq", new JSONNumber(htmlEventSeqId));
		JsonUtil.storeJsonTransferList(jo, "runzones", runzones);
		jo.put("glbls", pageState.store());
		JsonUtil.storeJsonTransferMap(jo, "htmlevents", htmlEvents);
		jo.put("css", css.store());
		return jo;
	}
	
	
	/**
	 * Html rendered in server, client side runner
	 * 
	 * @param val
	 * @param codeRepo
	 * @param trans
	 * @param domain
	 * @param session
	 * @throws RunException
	 */
	public Runner(RequestCreator rc, JSONValue val, CodeRepo codeRepo, JSONObject trans, Object domain, Object session) throws RunException {
		this.codeRepo = codeRepo;
		this.request=rc.create(this);
		
		JSONObject jo = val.isObject();

		this.runZoneSeq = JsonUtil.intgr(jo, "rzseq");
		this.htmlIdSeq = JsonUtil.intgr(jo, "htmlidseq");
		this.htmlEventSeqId = JsonUtil.intgr(jo, "htmlevseq");

		this.pageState = new State(JsonUtil.arr(jo, "glbls"));
		this.scope = new Scope(request, pageState, trans);
		
		JSONObject note=this.scope.getObj("note", false);
		this.mobile=note.getBoolean("m");
		this.bw=note.getInt("bw");
		this.lang=note.getString("lang");
		this.langs=note.getArray("langs");

		JSONArray rzs = JsonUtil.arr(jo, "runzones");
		for (int i = 0; i < rzs.size(); i++) {
			RunZone rz = new RunZone(this, this.pageState, trans, rzs.get(i));
			this.runzones.add(rz);
		}

		JSONObject jm = JsonUtil.obj(jo, "htmlevents");
		for (String key : jm.keySet()) {
			JSONValue v = jm.get(key);
			Trigger t = new Trigger(v);
			htmlEvents.put(key, t);
		}

		this.css = new Css(JsonUtil.obj(jo, "css"));
		

//		this.domain=domain;
//		this.session = session;
		
		for (RunZone runZone : runzones) {
			runZone.processDelayedClientCommands();
		}
		
		checkClient();
		
	}


	public Runner(RequestCreator rc, JSONObject info, JSONObject logo, JSONObject font, JSONObject palette, JSONArray styleArray2,
			JSONObject item, CodeRepo codeRepo, JSONObject trans, String app, Object domain, Object session,
			JSONArray styleArrayCommon, JSONObject note, Map<String, String> oParams) throws RunException {
		this.codeRepo = codeRepo;
		this.request=rc.create(this);
		this.pageState = new State();
		pageState.addVariable("item", item);
		pageState.addVariable("logo", logo);
		pageState.addVariable("info", info);
		pageState.addVariable("font", font);
		pageState.addVariable("palette", palette);
		pageState.addVariable("note", note);
		if (oParams != null) {
			for (Entry<String, String> it : oParams.entrySet()) {
				pageState.addVariable(it.getKey(), new MyLiteral(it.getValue()));
			}
		}
		// pageState.addVariable("view", viewy);
		this.scope = new Scope(request, pageState, trans);
		this.css = new Css(styleArrayCommon, styleArray2, this);
		
		JSONObject headerfont = font.get("headerfont").isObject();
		JSONObject textfont = font.get("textfont").isObject();
		css.useFont(textfont.get("fontfamily"));
		css.useFont(headerfont.get("fontfamily"));
		
		try {
			JSONArray efs = info.get("text_font").isObject().get("extrafonts").isArray();
			for (int i = 0; i < efs.size(); i++) {
				String ggf = efs.get(i).isString().stringValue();
				css.useFont(ggf);	
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		this.codes = Code.widget(app, codeRepo);

		this.bw=note.getInt("bw");
		this.lang=note.getString("lang");
		this.langs=note.getArray("langs");
		this.userAgent=note.getObject("ua");
		
		checkClient();
	}
	
	private void checkClient() {
		if(!Portable.one.isClient())
			return;
		
		Portable.one.tick(200, new Runnable() {
			int tickno=0;
			
			@Override
			public void run() {
				try {
					Runner.this.C(tickno++);
				} catch (RunException e) {
					Sistem.printStackTrace(e, "=Tick=");
				}

				// if ((tickno % 25) == 0)
				// PassEventHandler.passEvent();

			}
		});
	}


//	public void render(final Elem container) throws RunException {
//		codeCur = codes;
//		walk(container);
//	}
	
	public String render() throws RunException {
		this.editables.clear();
		Doc doc = new Doc();
		newRunZone(null, null, this.codes, doc);
		do {
			WaitingZone wz = waitingZones.remove(0);
			wz.render();
		} while (waitingZones.size() != 0);
		pageState.resetModifieds();
		String s = doc.toString();
		return "<style>" + css.cssText() + "</style>" + s;
		
	}
	
	/**
	 * Called in client
	 * @throws RunException 
	 */
	public void afterRender() throws RunException{
		pageState.addVariable("rendered", new MyLiteral(true));
		broadcastChange();
	}

	public String addStyleClass(String mytag, Map<String, Map<String, String>> style, Animation animation) throws RunException {
		return css.addStyleClass(mytag, style, animation);
	}

	public String addEvent(String runZoneId, String changeId) {
		String htmlEventId = "" + htmlEventSeqId++;
		Trigger he = new Trigger(runZoneId, changeId);
		this.htmlEvents.put(htmlEventId, he);
		return htmlEventId + "";
	}

	public String evaluateText(String string) throws RunException {
		try {
			return Evaluator.evaluateText(string, scope);
		} catch (RunException e) {
			throw new RunException("Evalutation failed for string:" + string, e);
		}
	}
	
	public JSONObject evaluateObj(String string) throws RunException {
		try {
			MyLiteral ml = Evaluator.evaluate(string, scope);
			return ml.getObj();
		} catch (RunException e) {
			throw new RunException("Evalutation failed for obj:" + string, e);
		}
	}

	public void newRunZone(RunZone parent, String rzHtmlId, Code codes, Elem container) throws RunException {
		RunZone rz = new RunZone(this, parent == null ? null : parent.getId(), rzHtmlId, this.scope.clone());
		// container.setAttribute("rom_rz", "true");
		runzones.add(rz);
		waitingZones.add(new WaitingZone(codes, rz, container));
	}

	
//	public void render(final Elem container) throws RunException {
//		RenderCallState rcs=new RenderCallState();
//		codeCur = codes;
//		walk(container, rcs);
//	}
	
	
	public void C(int tickno) throws RunException {
		for (RunZone rz : runzones) {
			new RenderCallState(rz).tick(tickno);
		}
	}
	
	public void fireEvent(JSONObject event) throws RunException {
		
		String rzid=event.optString("rzid");
		RunZone rz = rzid==null?runzones.get(0):getRunZone(rzid);
		String type = event.optString("type");
//		if (type.equals("rt")) {
//			new RenderCallState(rz).rtEvent(event);
//		} else {
//
			if (type!=null && type.equals("form")) {
				new RenderCallState(rz).sendForm(event);
				return;
			}
			new RenderCallState(rz).changeByHtmlEvent(event);
//		}

	}

	public void pageLoaded() throws RunException {
		for (RunZone rz : runzones) {
			new RenderCallState(rz).pageLoaded();
		}
	}

	public Scope getScope() {
		return scope;
	}

	public List<RunZone> getRunZones() {
		return this.runzones;
	}

	public RunZone getRunZone(String id) throws RunException {
		for (RunZone rz : this.runzones) {
			if (id.equals(rz.getId()))
				return rz;
		}
		throw new RunException("Runzone not found:" + id);
	}

//	public Object getSession() {
//		return session;
//	}
//	
//	public Object getDomain() {
//		return domain;
//	}

	public String nextIdSeq() {
		return "" + htmlIdSeq++;
	}

	public String nextRunZoneSeq() {
		return "" + runZoneSeq++;
	}

	// public JSONObject getParentCode(String parent) throws RunException {
	// if(parent==null){
	// return codes;
	// }
	// RunZone rz = getRunZone(parent);
	// return rz.getCodeRoot();
	// }

	public void broadcastChange() throws RunException {
		// Logger logger = Logger.getLogger("Runner broadcast");
		for (RunZone it : runzones) {
			// logger.log(Level.SEVERE,
			// "============="+it.runZoneId+"===========");
			new RenderCallState(it).isChanged(false);
		}
		pageState.resetModifieds();
	}

	public CodeRepo getCodeRepo() {
		return codeRepo;
	}

	public void addEditable(String ensureId, RunZone rz) {
		this.editables.put(ensureId, rz);
	}

	public Set<String> getEditables() {
		return editables.keySet();
	}

	public void addWidgetInstance(String id, Code codeCur, RunZone runZone) {
		if (wins == null) {
			wins = new HashMap<String, WidgetInstance>();
		}
		if (wins.containsKey(id))
			return;
		wins.put(id, new WidgetInstance(runZone, codeCur, id));
	}

	// @Override
	// public Elem wgtPreview(String widgetInstance) throws RunException {
	// if (wins == null)
	// return null;
	// WidgetInstance rc = wins.get(widgetInstance);
	// if (rc == null)
	// return null;
	// Doc doc = new Doc();
	// rc.getRunZone().setCodeCur(rc.getCodeCur());
	// // rc.getCodeCur().execute(rc.getRunZone(), doc);
	//
	// WidgetCommand.preview(rc.getRunZone(), doc, true, widgetInstance);
	//
	// return doc.getOnlyChild();
	// }
	//
	// @Override
	// public String wgtCreate(String editor, Code wcode) throws RunException {
	// if (editables == null)
	// return null;
	// RunZone rz = editables.get(editor);
	// if (rz == null)
	// return null;
	//
	// rz.setCodeCur(wcode);
	// String id = Elem.newId();
	// rz.addWidgetInstance(id);
	// return id;
	//
	// // // Doc doc=new Doc();
	// // // rz.setCodeCur(wcode);
	// // // wcode.preview(rz, doc,"ok");
	// // // Elem sp=doc.getOnlyChild();
	// // // <>
	// // return WidgetCommand.create(rz, curElem);
	// }

	public WidgetInstance getWidgetInstance(String insId) {
		if (wins == null)
			return null;
		WidgetInstance rc = wins.get(insId);
		if (rc == null)
			return null;
		return rc;
	}

	public Code createWidgetCode(String widgetName) throws RunException {
		Code cd;
		if (widgetName.startsWith("w:")) {
			Wgt wgt = this.codeRepo.getWidget(widgetName);
			
			cd = new Code(null, this.codeRepo, widgetName);
			if(wgt.getDefWidth()!=null)
				cd.setStyleByType("defaultstyle", "width", wgt.getDefWidth()+"px");
			if(wgt.getDefHeight()!=null)
				cd.setStyleByType("defaultstyle", "height", wgt.getDefHeight()+"px");
			
			
		} else if (widgetName.equals("html")){
			cd = new Code(null, this.codeRepo, "div");
			Code in = new Code(cd, this.codeRepo, "c:text");
			in.setText("type here");
			cd.addChild(in);
		} else { //svg
			cd = new Code(null, this.codeRepo, "div");
			
			Code in = new Code(cd, this.codeRepo, "svg");
			in.ats.put("xmlns", "http://www.w3.org/2000/svg");
			Code g = new Code(cd, this.codeRepo, "g");
			
			Code rect = new Code(cd, this.codeRepo, "rect");
			rect.ats.put("width", "40");
			rect.ats.put("height", "40");
			rect.addStyleByType("defaultstyle", "fill", "rgb(0,0,255)");
			
			
			g.addChild(rect);
			in.addChild(g);
			
			
			cd.addChild(in);
			
		}

		cd.setStyleByType("defaultstyle", "position", "absolute");
		cd.setStyleByType("defaultstyle", "overflow", "auto");
		return cd;
	}

	public int getBw() {
		return bw;
	}
	
	public String getLang() {
		return lang;
	}


	public MinRequest getRequest() {
		return request;
	}

	public JSONArray getLangs() {
		return langs;
	}

	public JSONObject getUserAgent() {
		return userAgent;
	}
	
	
}
