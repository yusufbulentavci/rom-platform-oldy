package com.bilgidoku.rom.site.kamu.graph.client.db.elems;

import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasContext;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.json.client.JSONObject;

public class Move extends Elem {
	public Move(Elem prev, int x, int y) {
		super(Elem.LINE, prev, x, y);
	}

	public Move(JSONObject el) throws RunException {
		super(MOVETO, el);
	}

	@Override
	public void draw(final CanvasContext c) {
		c.beginPath();
		c.moveTo(getOrigin().getX(),
				getOrigin().getY());
	}

	@Override
	public boolean isPathElem() {
		return true;
	}

}
