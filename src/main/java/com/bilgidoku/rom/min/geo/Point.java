package com.bilgidoku.rom.min.geo;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	//
	// public Point(JSONArray array) {
	// this.x = (int) array.get(0).isNumber().doubleValue();
	// this.y = (int) array.get(1).isNumber().doubleValue();
	// }
	//
	// public JSONArray toJson(){
	// JSONArray a = new JSONArray();
	// a.set(0, new JSONNumber(x));
	// a.set(1, new JSONNumber(y));
	// return a;
	// }

	public String toString() {
		return "Point:" + x + "," + y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double diff(double x, double y) {
		return Math.sqrt((this.x - x) * (this.x - x)  + (this.y - y) * (this.y - y));
	}

	public void subY(int difY) {
		this.y -= difY;
	}

	public void subX(int difX) {
		this.x -= difX;
	}

	public void addY(int difY) {
		this.y += difY;
	}

	public void addX(int difX) {
		this.x += difX;
	}

	public Point clone() {
		return new Point(this.x, this.y);
	}

	public void sub(Point p) {
		x -= p.x;
		y -= p.y;
	}

	public void add(Point p) {
		x -= p.x;
		y -= p.y;
	}

	public double tangent() {
		if (x == 0)
			return Double.MAX_VALUE;
		return (double) y / x;
	}

	public double distance(Point p) {
		return Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
	}

	public void rotate(double a) {
		int nx = (int) (Math.cos(a) * x - Math.sin(a) * y);
		int ny = (int) (Math.sin(a) * x + Math.cos(a) * y);
		x = nx;
		y = ny;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point po = (Point) obj;
			return po.x == x && po.y == y;
		}
		return false;
	}

	public int area() {
		return x * y;
	}
}
