package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;

public class Rotate extends Change {

	private Elem elem;
	private double radian;
	private double prevRadian;

	public Rotate(Elem elem, double d) {
		this.elem = elem;
		this.radian = d;
		cursor = null;
	}

	@Override
	public void doit(Db db) {
		prevRadian = elem.getRot();
		elem.setRot(radian);
		db.update(elem);
	}

	@Override
	public void undo(Db db) {
		elem.setRot(prevRadian);
		db.update(elem);
	}

	@Override
	public void update(int x, int y) {
		int dx = x - elem.getOrigin().getX();
		int dy = elem.getOrigin().getY() - y;
		double d = dy > 0 ? Double.MAX_VALUE : Double.MIN_VALUE;
		if (dx != 0) {
			d = (double) dy / dx;
		}
		radian = Math.atan(d);
		if (dx < 0) {
			radian = Math.PI + radian;
		}
	}

	@Override
	public Change clone() {
		return new Rotate(elem, radian);
	}

	@Override
	public boolean isCreator() {
		return false;
	}

	@Override
	public boolean isTransform() {
		return true;
	}

	@Override
	public String getName() {
		return "rotate";
	}
}
