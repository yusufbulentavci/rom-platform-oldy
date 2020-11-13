package com.bilgidoku.rom.min.geo;

import java.util.ArrayList;
import java.util.List;


public class Polygon {
	private List<Point> points=new ArrayList<Point>();
	
	
	public void addPoint(Point p){
		points.add(p);
		System.err.println(p);
	}
	
	public Point isOn(Point p){
		if(points.size()<2)
			return null;
		
		for(int i=1;i<points.size();i++){
			Point po=points.get(i-1);
			Point pe=points.get(i);
			
			Point lineTransformed=pe.clone();
			lineTransformed.sub(po);
			
			Point pointTranformed=p.clone();
			pointTranformed.sub(po);
			
			System.out.println("dif:"+lineTransformed);
			System.out.println(pointTranformed);
			
			if(Real.hit(lineTransformed.tangent(), pointTranformed.tangent())){
				return(p.distance(po)<p.distance(pe))?po:pe;
			}
		}
		
		return null;
	}

}
