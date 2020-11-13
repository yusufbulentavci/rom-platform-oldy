package com.bilgidoku.rom.min.geo;

public class Real {
	private static final double DOUBLE_TOLERANCE=2.1d;
	
	
	public static boolean hit(double d1, double d2){
		double d=Math.abs(d2-d1);
		System.out.println(d);
		return d<=DOUBLE_TOLERANCE;
	}
}
