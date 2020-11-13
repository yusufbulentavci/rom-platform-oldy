package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

/**
 * 
 * Removes elements from db, can be undo
 * Line, bezier, moveto removal is not supported yet
 * 
 *
 */
public class RmElem extends Change {

	private Elem removed=null;

	public RmElem(Elem r) {
		super();
		this.removed=r;
		cursor = Cursor.CROSSHAIR;
	}
	
	@Override
	public void doit(Db db) {
		db.remove(removed);
	}

	@Override
	public void undo(Db db) {
		db.addElem(removed);
	}

	@Override
	public Change clone() {
		return copy(new RmElem(removed));
	}

	@Override
	public void update(int x, int y) {
	}

	@Override
	public boolean isCreator() {
		return false;
	}
	
	@Override
	public String getName() {
		return "rmelem";
	}
}
