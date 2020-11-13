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
public class Back extends Change {

	private Elem removed=null;
	private boolean suc=false;

	public Back(Elem r) {
		super();
		this.removed=r;
		cursor = Cursor.CROSSHAIR;
	}
	
	@Override
	public void doit(Db db) {
		suc=db.toBack(removed);
	}

	@Override
	public void undo(Db db) {
		if(!suc)
			return;
		db.toFront(removed);
	}

	@Override
	public Change clone() {
		return copy(new Back(removed));
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
		return "back";
	}
}
