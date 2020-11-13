package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

public class Move extends Change {

	private Elem elem;
	int difX;
	int difY;
	private int originalX;
	private int originalY;

	public Move(Elem elem, int difX, int difY, int originalX, int originalY) {
		this.elem = elem;
		this.difX = difX;
		this.difY = difY;
		this.originalX = originalX;
		this.originalY = originalY;
		cursor = Cursor.MOVE;
	}

	public Change clone() {
		Move m = new Move(elem, difX, difY, originalX, originalY);
		return m;
	}

	@Override
	public void doit(Db db) {
		Point p = elem.getOrigin();
		p.setX(originalX + difX);
		p.setY(originalY + difY);
		db.update(elem);
	}

	@Override
	public void undo(Db db) {
		Point p = elem.getOrigin();
		p.subX(difX);
		p.subY(difY);
		db.update(elem);
	}

	@Override
	public void update(int x, int y) {
		this.difX = x - originalX;
		this.difY = y - originalY;
	}

	@Override
	public boolean isCreator() {
		return false;
	}

	@Override
	public boolean isTransform() {
		return true;
	}

	@Override
	public String getName() {
		return "move";
	}
}
