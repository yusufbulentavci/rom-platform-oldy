package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

public class CreateBezier extends Change {
	private Elem prev;
	private int x;
	private int y;
	private int cp1x;
	private int cp1y;
	private int cp2x;
	private int cp2y;

	private Elem created=null;
	
	public CreateBezier(Elem prev, int x, int y, int cp1x, int cp1y, int cp2x, int cp2y) {
		this.prev=prev;
		this.x=x;
		this.y=y;
		this.cp1x=cp1x;
		this.cp1y=cp1y;
		this.cp2x=cp2x;
		this.cp2y=cp2y;
		cursor = Cursor.CROSSHAIR;
	}

	public Change clone(){
		return copy(new CreateBezier(prev, x, y, cp1x, cp1y, cp2x, cp2y));
	}

	@Override
	public void doit(Db db) {
//		this.created=new Bezier(prev, x, y, cp1x, cp1y, cp2x, cp2y);
//		db.addElem(copy(created));
	}

	@Override
	public void undo(Db db) {
		db.remove(created);
		created=null;
	}

	@Override
	public void update(int x, int y) {
		this.x=x;
		this.y=y;
		
		if(x>this.prev.getOrigin().getX()){
			this.cp1x=-64;
			this.cp2x=-32;
		}else{
			this.cp1x=32;
			this.cp2x=64;
		}
		
		if(y>this.prev.getOrigin().getY()){
			this.cp1y=-64;
			this.cp2y=-32;
		}else{
			this.cp1y=32;
			this.cp2y=64;
		}
	}

	@Override
	public boolean isCreator() {
		return true;
	}
	
	@Override
	public String getName() {
		return "createbezier";
	}
}
