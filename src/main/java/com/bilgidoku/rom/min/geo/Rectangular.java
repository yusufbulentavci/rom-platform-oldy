package com.bilgidoku.rom.min.geo;

public class Rectangular {

	public int x;
	public int y;
	public int dx;
	public int dy;
	public double rot;

	public Rectangular(int x, int y, int dx, int dy, double rot) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.rot = rot;
	}
	
	public String toString(){
		return x+","+y+" "+dx+","+dy+" /"+rot;
	}

	// public boolean isIn(int px, int py) {
	// // rotate d's
	// int nx = (int) (Math.cos(rot) * dx - Math.sin(rot) * dy);
	// int ny = (int) (Math.sin(rot) * dx + Math.cos(rot) * dy);
	//
	// // Move to center
	// px-=x;
	// py-=y;
	//
	// // Opposite sites
	// if(px*nx<0 || py*ny<0)
	// return false;
	//
	// return (Math.abs(nx) >= Math.abs(px) && Math.abs(ny) >= Math.abs(py));
	// }

	public boolean isIn(int clickx, int clicky) {

		// Move to center
		int px = clickx - x;
		int py = clicky - y;

		// rotate d's
		int nx = (int) (Math.cos(rot) * px - Math.sin(rot) * py);
		int ny = (int) (Math.sin(rot) * px + Math.cos(rot) * py);

		// Opposite sites
		if (dx * nx < 0 || dy * ny < 0)
			return false;

		return (Math.abs(dx) >= Math.abs(nx) && Math.abs(dy) >= Math.abs(ny));
	}
	
	/**
	 * Change dx,dy values according to rotation. So set rot zero
	 */
	public void zeroRotate(){
		if(rot==0){
			return;
		}
		
		int nx = (int) (Math.cos(rot) * dx - Math.sin(rot) * dy);
		int ny = (int) (Math.sin(rot) * dx + Math.cos(rot) * dy);
		dx=nx;
		dy=ny;
		rot=0;
	}
	

	public double area() {
		return dx * dy;
	}

	public Point getOrigin() {
		return new Point(x,y);
	}

	public double getRot() {
		return rot;
	}
	
	public Integer minX(Integer minX){
		int mx = Math.min(x, x+dx);
		if(minX==null || mx<minX)
			return mx;
		return minX;
	}
	
	public Integer minY(Integer minY){
		int my = Math.min(y, y+dy);
		if(minY==null || my<minY)
			return my;
		return minY;
	}

	public Integer maxX(Integer maxX){
		int mx = Math.max(x, x+dx);
		if(maxX==null || mx>maxX)
			return mx;
		return maxX;
	}
	
	public Integer maxY(Integer maxY){
		int my = Math.max(y, y+dy);
		if(maxY==null || my>maxY)
			return my;
		return maxY;
	}
}
