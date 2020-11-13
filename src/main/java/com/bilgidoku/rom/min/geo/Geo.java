package com.bilgidoku.rom.min.geo;

public class Geo {

	public static boolean insideCircle(int cx, int cy, int cr, int x, int y) {
		int dx = x - cx;
		int dy = y - cy;

		return dx * dx + dy * dy <= cr * cr;

	}

}
