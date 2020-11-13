package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.db.elems.Img;
import com.google.gwt.dom.client.Style.Cursor;

public class CreateImg extends Change {

	private String img;
	private int x;
	private int y;
	private Elem created=null;
	private int dx;
	private int dy;

	public CreateImg(String img, int x, int y, int dx, int dy) {
		super();
		this.img=img;
		this.x = x;
		this.y = y;
		this.dx=dx;
		this.dy=dy;
		cursor = Cursor.CROSSHAIR;
	}
	
	@Override
	public void doit(Db db) {
		created=new Img(img,x,y, dx, dy);
		db.addElem(copy(created));
	}

	@Override
	public void undo(Db db) {
		db.remove(created);
		created=null;
	}

	@Override
	public Change clone() {
		return copy(new CreateImg(img,x, y, dx, dy));
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
		return "createimg";
	}
}
