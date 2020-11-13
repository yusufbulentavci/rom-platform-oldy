package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

public class CreateMove extends Change {

	private int x;
	private int y;
	private Elem created = null;
	private Elem prev;

	public CreateMove(Elem prev, int x, int y) {
		super();
		this.prev = prev;
		this.x = x;
		this.y = y;
		cursor = Cursor.MOVE;
	}

	@Override
	public void doit(Db db) {
		created = new com.bilgidoku.rom.site.kamu.graph.client.db.elems.Move(this.prev, x, y);
		db.addElem(copy(created));
	}

	@Override
	public void undo(Db db) {
		db.remove(created);
		created = null;
	}

	@Override
	public Change clone() {
		return copy(new CreateMove(prev, x, y));
	}

	@Override
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean isCreator() {
		return true;
	}

	@Override
	public String getName() {
		return "createmove";
	}
}
