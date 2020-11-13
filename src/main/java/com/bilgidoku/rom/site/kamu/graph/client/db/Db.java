package com.bilgidoku.rom.site.kamu.graph.client.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.change.Change;
import com.bilgidoku.rom.site.kamu.graph.client.db.elems.Img;
import com.bilgidoku.rom.site.kamu.graph.client.db.elems.Line;
import com.bilgidoku.rom.site.kamu.graph.client.db.elems.Move;
import com.bilgidoku.rom.site.kamu.graph.client.db.elems.Rect;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.min.geo.Rectangular;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;

public class Db {

	private Vector<Change> changes = new Vector<Change>();
	private List<Elem> elems = new ArrayList<Elem>();
	private Integer transactionBegin = null;

	private String backImage;
	private Point backImageSize;
	private Point editLocation;
	private Point editSize;
	private Point area;
	private int areaPaper;
	private AreaFb areaFb;
	private boolean calcDirty = false;

	private Db() {
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {

			@Override
			public boolean execute() {
				if (calcDirty) {
					calcArea();
				}
				return true;
			}
		}, 1000);
	}

	public Db(AreaFb areaFb, String backImage, Point backImageSize, Point editLocation, Point editSize) {
		this();
		this.backImage = backImage;
		this.backImageSize = backImageSize;
		this.editLocation = editLocation;
		Sistem.outln("EDITLOC C:" + editLocation.toString());

		this.editSize = editSize;
		this.areaFb = areaFb;
	}

	public Db(AreaFb areaFb, JSONObject jo) throws RunException {
		this();
		loadFromJson(jo);
		this.areaFb = areaFb;
	}

	private void scheduleCalcArea() {
		this.calcDirty = true;
	}

	final static int A4W = 1200, A4H = 1400;

	private void calcArea() {
		Integer minx = null, maxx = null, miny = null, maxy = null;
		for (Elem e : elems) {
			Rectangular b = e.getBorder();
			b.zeroRotate();
			minx = b.minX(minx);
			miny = b.minY(miny);
			maxx = b.maxX(maxx);
			maxy = b.maxY(maxy);
		}
		if (minx == null)
			minx = 0;
		if (maxx == null)
			maxx = 0;
		if (miny == null)
			miny = 0;
		if (maxy == null)
			maxy = 0;
		Point na = new Point(Math.abs(maxx - minx), Math.abs(maxy - miny));
		if (area != null && !na.equals(area)) {

			int nCoef;
			if (area.area() == 0) {
				nCoef = 0;
			} else {
				if (area.getX() <= A4W && area.getY() <= A4H) {
					nCoef = 4;
				} else {
					nCoef = 3;
				}
			}
			areaPaper = nCoef;

			if (areaFb != null)
				areaFb.areaChanged(na, areaPaper);
		}
		area = na;

		calcDirty = false;
	}

	public void load(final String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			builder.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					Window.alert("Couldn't retrieve JSON:" + url);
				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						JSONObject jo = JSONParser.parseStrict(response.getText()).isObject();
						try {
							loadFromJson(jo);
						} catch (RunException e) {
							Sistem.printStackTrace(e, "Can not load image:" + url);
						}
					}
				}

			});
		} catch (RequestException e) {
			Window.alert("Couldn't retrieve JSON");
		}
	}

	public void save(final String url) {
		JSONObject jo = toJson();

		FilesDao.changetext(jo.toString(), url, new BooleanResponse() {
			@Override
			public void ready(Boolean value) {

			}
		});

	}

	public void loadFromJson(JSONObject jo) throws RunException {
		backImage = ClientUtil.optString(jo, "backimg");
		backImageSize = ClientUtil.getPoint(jo, "backimgsz");
		editLocation = ClientUtil.getPoint(jo, "editloc");
		editSize = ClientUtil.getPoint(jo, "editsz");
		
		Sistem.outln("EDITLOC Json:" + editLocation.toString());

		elems.clear();
		changes.clear();
		JSONArray es = ClientUtil.optArray(jo, "elms");
		updateElms(es);
		scheduleCalcArea();
	}

	public void updateElms(JSONArray es) throws RunException {

		if (es != null) {
			for (int i = 0; i < es.size(); i++) {
				JSONObject el = es.get(i).isObject();
				char t = (char) el.get("tp").isNumber().doubleValue();
				Elem l;
				switch (t) {
				case Elem.RECT:
					l = new Rect(el);
					break;
				case Elem.IMAGE:
					l = new Img(el);
					break;
				case Elem.LINE:
					l = new Line(el);
					break;
				case Elem.MOVETO:
					l = new Move(el);
					break;
				default:
					throw new RunException("U g e:" + t);
				}
				elems.add(l);
			}
		}
		// for (Elem e : elems) {
		// e.fillSecondPass(this);
		// }
		scheduleCalcArea();
	}

	public JSONObject toJson() {
		scheduleCalcArea();
		JSONObject ret = new JSONObject();

		ret.put("backimg", new JSONString(backImage));
		ret.put("backimgsz", ClientUtil.toJson(backImageSize));
		ret.put("editloc", ClientUtil.toJson(editLocation));
		ret.put("editsz", ClientUtil.toJson(editSize));
		if (area != null) {
			ret.put("area", ClientUtil.toJson(area));
			ret.put("paper", new JSONNumber(areaPaper));
		}

		JSONArray es = elmsArray();
		ret.put("elms", es);
		return ret;
	}

	private JSONArray elmsArray() {
		JSONArray es = new JSONArray();
		for (int i = 0; i < elems.size(); i++) {
			es.set(i, elems.get(i).toJson());
		}
		return es;
	}

	public Iterator<Elem> getElems() {
		return elems.iterator();
	}

	public void remove(Elem elem) {
		elem.onDelete();

		if (!elems.remove(elem))
			Window.alert("no");

		GraphicEditor.editState.elemRemoved(elem);
		scheduleCalcArea();
	}

	public void addElem(Elem elem) {
		elems.add(elem);
		GraphicEditor.editState.elemAdded(elem);
		scheduleCalcArea();
	}

	public void change(Change change) {
		backBegin();
		this.transactionBegin = changes.size();
		change.doit(this);
		changes.add(change);
		scheduleCalcArea();
	}

	public void undo() {
		Change change = changes.lastElement();
		if (change == null) {
			return;
		}

		change.undo(this);
		changes.remove(changes.size() - 1);
		scheduleCalcArea();
	}

	public void beginChange() {
		this.transactionBegin = changes.size();
	}

	private void backBegin() {
		if (this.transactionBegin == null)
			return;
		while (this.transactionBegin != changes.size()) {
			undo();
		}
	}

	public void rollback() {
		backBegin();
		this.transactionBegin = null;
		scheduleCalcArea();
	}

	public void commit() {
		this.transactionBegin = null;
		scheduleCalcArea();
	}

	public void getElemSize() {
		this.elems.size();
	}

	public int getChangeSize() {
		return changes.size();
	}

	Integer getTransactionIndex() {
		return this.transactionBegin;
	}

	public Point getImageSize() {
		return editSize;
	}

	public void update(Elem elem) {
		GraphicEditor.editState.elemUpdate(elem);
	}

	public Elem getElemById(int id) throws RunException {
		for (Elem e : elems) {
			if (e.id == id)
				return e;
		}
		throw new RunException("Elem with id not found:" + id);
	}

	public String getBackImage() {
		return backImage;
	}

	public Point getBackImageSize() {
		return backImageSize;
	}

	public Point getEditLocation() {
		return editLocation;
	}

	public Point getEditSize() {
		return editSize;
	}

	public boolean toBack(Elem elem) {
		int ind = elems.indexOf(elem);
		if (ind == 0)
			return false;
		elems.remove(ind);
		elems.add(ind - 1, elem);
		return true;
	}

	public boolean toFront(Elem elem) {
		int ind = elems.indexOf(elem);
		if (ind == elems.size() - 1)
			return false;
		elems.remove(ind);
		elems.add(ind + 1, elem);
		return true;
	}

	public boolean atDeepBack(Elem cur) {
		return elems.size() > 0 && elems.get(0) == cur;
	}

}
