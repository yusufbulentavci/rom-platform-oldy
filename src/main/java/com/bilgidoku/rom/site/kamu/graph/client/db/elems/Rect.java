package com.bilgidoku.rom.site.kamu.graph.client.db.elems;

import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasContext;
import com.bilgidoku.rom.site.kamu.graph.client.draw.Drawer;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.json.client.JSONObject;

public class Rect extends Elem {

	public Rect(int x, int y, int dx, int dy) {
		this(x, y, dx, dy, null);
	}

	public Rect(int x, int y, int dx, int dy, String text) {
		super('r', null, x, y);
		setRot(0d);
		setDp(new Point(dx, dy));
		setText(text);
		setFont("Lobster");
		// setFontWidth(60);
		setTextFillColor("#000000");
	}

	public Rect(JSONObject el) throws RunException {
		super(RECT, el);
	}

	/**
	 * 
	 * INFO: 0-100-66 9 34 INFO: 1-100-297 43 -197 INFO: 2-100-297 -154 -197
	 */

	@Override
	public void prepareDraw(CanvasContext c) {
		String str = getText();
		if (str == null) {
			return;
		}

		if (!textFitPage) {
			//TODO
			// setFontWidth(60);
			// c.setFont("60px " + getFont());
			// c.resetFont();
			// this.getDp().setY((int) (fw * parts.length * 1.3));
			//
			return;
		}

		String[] parts = str.split("\n");
		int maxWidth = 0;
		String selText = "";
		for (String string : parts) {
			if (maxWidth < string.length()) {
				maxWidth = string.length();
				selText = string;
			}
		}

		int fw = (int) ((this.getDp().getX() / maxWidth) * 1.3);
		double scaledX = c.scale(this.getDp().getX() - 10);

		for (int i = 0; i < 3; i++) {
			setFontWidth(fw);
			int zoomedFont = (int) c.scale(getFontWidth());
			c.setFont(zoomedFont + "px " + getFont());
			int mw = c.measureText(selText);

			double diff = scaledX - mw;

			// Sistem.outln(i+"-"+this.getDp().getX() + "-" + mw+" "+fw+"
			// "+diff);
			if (diff >= 0 && diff < 5)
				break;
			if (i != 2)
				fw += (diff / scaledX) * fw;
		}
		c.resetFont();
		this.getDp().setY((int) (fw * parts.length * 1.3));

	}

	@Override
	public void draw(final CanvasContext c) {
		transformMatrix(c);

		setStyle(c);
		c.beginPath();

		int vx = getDp().getX();
		int vy = getDp().getY();
		c.rect(vx, vy);

		if (getFillColor() != null) {
			c.fill();
		}
		if (getStrokeColor() != null && getStrokeWidth() != null) {
			c.stroke();
		}

		c.closePath();
		resetStyle(c);

		String str = getText();
		if (str != null) {
			drawText(c, str, getFontWidth(), getFont(), getOrigin().getX(), getOrigin().getY(), getDp().getX(),
					getDp().getY());
		}
		c.resetTransform();

	}

	public void drawText(CanvasContext c, String str, int fontWidth, String font, int x, int y, int w, int h) {

		c.beginPath();

		if (getTextFillColor() != null) {
			c.setFillStyle(Drawer.getColor(getTextFillColor()));
		}
		if (getTextStrokeColor() != null) {
			c.setStrokeStyle(Drawer.getColor(getTextStrokeColor()));
		}
		if (getTextStrokeWidth() != null) {
			c.setLineWidth(getTextStrokeWidth());
			Sistem.outln(getTextStrokeWidth());
		}

		String[] parts = str.split("\n");

		int rowCount = parts.length;

		// w=(int) c.scale(w);

		int zoomedFont = (int) c.scale(fontWidth);

		c.setFont(zoomedFont + "px " + font);

		// int rowHeight = (int) c.scale(h/rowCount);

		int rowHeight = h / rowCount;

		int step = rowHeight;

		int sy = step / 2;
		int sx = (int) w / 2;

		for (String string : parts) {
			c.setTextAlign("center");
			c.setTextBaseline("middle");

			c.fillText(string, sx, sy);
			c.strokeText(string, sx, sy);
			sy += step;
		}

		if (getTextFillColor() != null) {
			c.setFillStyle(TRANPARENT_COLOR);
		}
		if (getTextStrokeColor() != null) {
			c.setStrokeStyle(TRANPARENT_COLOR);
		}
		if (getTextStrokeWidth() != null) {
			c.setLineWidth(1);
		}
		c.resetFont();
		c.closePath();

	}

}
