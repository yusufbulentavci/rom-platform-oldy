package com.bilgidoku.rom.site.kamu.graph.client.db.elems;

import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasContext;
import com.bilgidoku.rom.min.geo.Point;

public class Bezier extends Elem{
	
	public Bezier(Elem prev, int x, int y, int cp1x, int cp1y, int cp2x, int cp2y){
		super(Elem.LINE,prev, x, y);
		setCp1(new Point(cp1x,cp1y));
		setCp2(new Point(cp2x,cp2y));
	}

	@Override
	public void draw(final CanvasContext c){
		c.bezierCurveTo(
				getOrigin().getX()+getCp1().getX(), 
				getOrigin().getY()+getCp1().getY(),
				getOrigin().getX()+getCp2().getX(), 
				getOrigin().getY()+getCp2().getY(), 
				getOrigin().getX(), 
				getOrigin().getY()
				);
		
		if(getNext()==null){
//			c.closePath();
			c.stroke();
//			c.fill();
		}
	}

	@Override
	public boolean isPathElem() {
		return true;
	}
}
