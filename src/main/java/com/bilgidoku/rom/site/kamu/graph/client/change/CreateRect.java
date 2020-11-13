package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.db.elems.Rect;
import com.google.gwt.dom.client.Style.Cursor;

public class CreateRect extends Change {

	private int x;
	private int y;
	private int dx;
	private int dy;
	private Elem created=null;
	private String text;

	public CreateRect(int x, int y, int dx, int dy, String text) {
		super();
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.text=text;
		cursor = Cursor.CROSSHAIR;
	}
	
	@Override
	public void doit(Db db) {
		created=new Rect(x,y,dx,dy,text);
		db.addElem(copy(created));
	}

	@Override
	public void undo(Db db) {
		db.remove(created);
		created=null;
	}

	@Override
	public Change clone() {
		return copy(new CreateRect(x, y, dx, dy,text));
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
		return "createrect";
	}
}
