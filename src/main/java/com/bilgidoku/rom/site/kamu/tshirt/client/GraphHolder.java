package com.bilgidoku.rom.site.kamu.tshirt.client;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.GraphicFeedback;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.SimplePanel;

public class GraphHolder extends SimplePanel implements GraphicFeedback {

	String curCanvasName;
	GraphicEditor ge;

	Map<String, JSONObject> canvases = new HashMap<>();
	private String userDir;
	private tshirt caller;
	private int totalCoef = 0;
	private int canvasCoef = 0;
	private int changedCanvasCoef = 0;

	public GraphHolder(String userDir, tshirt ts) {
		this.userDir = userDir;
		this.caller = ts;
	}

	public void switchCanvas(String name, String backImage, Point backImageSize, Point editLocation, Point editSize)
			throws RunException {
		if (ge != null) {
			// eski canvası kaydet
			JSONObject jo = ge.toJson();
			canvases.put(curCanvasName, jo);
			this.clear();
		}

		ge = new GraphicEditor(this, backImage, backImageSize, editLocation, editSize, userDir);

		JSONObject jo = canvases.get(name);
		if (jo != null) {
			Integer paper = ClientUtil.optInteger(jo, "paper");
			if (paper != null) {
				this.canvasCoef = paperToCoef(paper);
				this.changedCanvasCoef = canvasCoef;
			}
			ge.loadJson(jo);
		}

		curCanvasName = name;
		this.add(ge.getPanel());

		com.google.gwt.core.client.Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				ge.reredraw();
				return false;
			}
		}, 1000);

	}

	public Map<String, JSONObject> getCanvases() {
		// if (canvases.size() > 0)
		// return canvases;
		canvases.put(curCanvasName, ge.toJson());
		return canvases;

	}

	public String getCurCanvasName() {
		return curCanvasName;
	}

	@Override
	public void modified() {
		// if (caller.getVirtualStockId() != null) {
		// return;
		// }
		//
		// if (caller.getVirtualStockId() == null && caller.getRequested())
		// return;
		//
		// Sistem.outln("YENI STOCK ID");
	}

	public void loadCanvases(Stock p) {
		JSONObject jo = p.getStock().options.getValue().isObject();
		try {
			JSONObject obj = ClientUtil.getObject(jo, "canvas");

			for (String key : obj.keySet()) {
				JSONObject objPart = ClientUtil.getObject(obj, key);
				JSONObject objRgf = ClientUtil.getObject(objPart, "rgf");
				if (objRgf != null) {
					canvases.put(key, objRgf);
					Integer paper = ClientUtil.optInteger(objRgf, "paper");
					if (paper != null) {
						this.totalCoef = paperToCoef(paper);
					}
				}
			}

		} catch (Exception e) {
			Sistem.outln(e.getMessage());
		}

	}

	private int paperToCoef(int paper) {
		if (paper > 3) {
			return 1;
		}
		return 2;
	}

	@Override
	public void setStatus(String text) {
		caller.setStatus(text);

	}

	@Override
	public void areaChanged(Point area, int paperSize) {
		int oldCoef = curCoef();
		changedCanvasCoef = paperToCoef(paperSize);
		int nCoef = curCoef();

		if (oldCoef != nCoef) {
			caller.areaCoefChanged(oldCoef, nCoef);
		}

		// Sistem.outln("AAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+area.getX() + "," +
		// area.getY());

	}

	private int curCoef() {
		return totalCoef - canvasCoef + changedCanvasCoef;
	}

}
