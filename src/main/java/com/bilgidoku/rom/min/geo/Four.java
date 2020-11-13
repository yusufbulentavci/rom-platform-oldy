package com.bilgidoku.rom.min.geo;

public class Four {
	private int x;
	private int y;

	private int w;
	private int h;

	public Four(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
	}

//	public Four(JSONArray array) {
//		this.x = (int) array.get(0).isNumber().doubleValue();
//		this.y = (int) array.get(1).isNumber().doubleValue();
//		this.w = (int) array.get(2).isNumber().doubleValue();
//		this.h = (int) array.get(3).isNumber().doubleValue();
//	}
	
//	public JSONArray toJson(){
//		JSONArray a = new JSONArray();
//		a.set(0, new JSONNumber(x));
//		a.set(1, new JSONNumber(y));
//		a.set(2, new JSONNumber(w));
//		a.set(3, new JSONNumber(h));
//		return a;
//	}

	public String toString() {
		return "Four:" + x + "," + y+ "," + w+ "," + h;
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

	public double diff(int x, int y) {
		return Math.sqrt((this.x - x) ^ 2 + (this.y - y) ^ 2);
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

	public Four clone() {
		return new Four(this.x, this.y, this.w, this.h);
	}

	
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
}
