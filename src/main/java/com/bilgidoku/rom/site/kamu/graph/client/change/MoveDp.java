package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

public class MoveDp extends Change{

	private Elem elem;
	int difX;
	int difY;

	public MoveDp(Elem elem, int difX, int difY){
		this.elem=elem;
		this.difX=difX;
		this.difY=difY;
		cursor = Cursor.SE_RESIZE;
	}
	@Override
	public void doit(Db db) {
		Point p=elem.getDp();
		p.setX(difX);
		p.setY(difY);
		db.update(elem);
	}

	@Override
	public void undo(Db db) {
		Point p=elem.getDp();
		p.setX(-difX);
		p.setY(-difY);
		db.update(elem);
	}

	@Override
	public void update(int x, int y) {
		Point rt=new Point(x-elem.getOrigin().getX(),y-elem.getOrigin().getY());
		rt.rotate(elem.getRot());
		difX=rt.getX();
		difY=rt.getY();
	}

	@Override
	public Change clone(){
		MoveDp m = new MoveDp(elem,difX,difY);
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
		return "movedp";
	}
}
