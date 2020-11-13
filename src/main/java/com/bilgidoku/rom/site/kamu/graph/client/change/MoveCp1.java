package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

public class MoveCp1 extends Change{

	private Elem elem;
	int difX;
	int difY;

	public MoveCp1(Elem elem, int difX, int difY){
		this.elem=elem;
		this.difX=difX;		
		this.difY=difY;
		cursor = Cursor.SE_RESIZE;
	}
	@Override
	public void doit(Db db) {
		Point p=elem.getCp1();
		p.setX(difX);
		p.setY(difY);
		db.update(elem);
	}

	@Override
	public void undo(Db db) {
		Point p=elem.getCp1();
		p.setX(-difX);
		p.setY(-difY);
		db.update(elem);
	}

	@Override
	public void update(int x, int y) {
		this.difX=x-elem.getOrigin().getX();
		this.difY=y-elem.getOrigin().getY();
	}

	@Override
	public Change clone(){
		MoveCp1 m = new MoveCp1(elem,difX,difY);
		return m;
	}
	@Override
	public boolean isCreator() {
		return false;
	}
	
	@Override
	public boolean isTransform(){
		return true;
	}
	
	@Override
	public String getName() {
		return "movecp1";
	}
}
