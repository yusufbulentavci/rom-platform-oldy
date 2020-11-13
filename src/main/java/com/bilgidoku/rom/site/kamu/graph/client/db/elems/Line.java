package com.bilgidoku.rom.site.kamu.graph.client.db.elems;

import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasContext;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.json.client.JSONObject;

public class Line extends Elem {
	public Line(Elem prev, int x, int y) {
		super(Elem.LINE, prev, x, y);
	}

	public Line(JSONObject el) throws RunException {
		super(LINE,el);
	}

	@Override
	public void draw(final CanvasContext c) {
		c.lineTo(getOrigin().getX(),
				getOrigin().getY());
	}

	@Override
	public boolean isPathElem() {
		return true;
	}
}
