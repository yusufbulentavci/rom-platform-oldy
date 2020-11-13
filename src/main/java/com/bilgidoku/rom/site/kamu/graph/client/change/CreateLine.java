package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

public class CreateLine extends Change {

	private int x;
	private int y;
	private Elem created=null;
	private Elem prev;

	public CreateLine(Elem prev, int x, int y) {
		this.prev=prev;
		this.x = x;
		this.y = y;
		cursor = Cursor.CROSSHAIR;
	}
	
	@Override
	public void doit(Db db) {
//		created=new Line(this.prev,x,y);
//		db.addElem(copy(created));
	}

	@Override
	public void undo(Db db) {
		db.remove(created);
		created=null;
	}

	@Override
	public Change clone() {
		return copy(new CreateLine(prev,x,y));
	}


	@Override
	public void update(int x, int y) {
		this.x=x;
		this.y=y;
	}

	@Override
	public boolean isCreator() {
		return true;
	}
	@Override
	public String getName() {
		return "createline";
	}
}
