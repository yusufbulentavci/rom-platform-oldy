package com.bilgidoku.rom.site.kamu.graph.client.db;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.geo.Four;
import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.min.geo.Rectangular;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasContext;
import com.bilgidoku.rom.site.kamu.graph.client.draw.Drawer;
import com.bilgidoku.rom.site.kamu.graph.client.draw.Viewing;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public abstract class Elem implements Comparable<Elem> {

	private static int Serial = 0;

	public static final double[] dashedLinePattern = new double[] { 5.0d, 5.0d };
	public static final double[] setToSetLinePattern = new double[] { 10.0d, 20.0d };
	public static final int dashedLineWidth = 2;

	public static final FillStrokeStyle SELECT_COLOR = CssColor.make("rgba(255,0,0,1)"); // #B3FFB3

	public static final FillStrokeStyle TRANPARENT_COLOR = CssColor.make("rgba(0,0,0,0)"); // #B3FFB3

	// public static final char TEXT = 't';
	public static final char RECT = 'r';
	public static final char LINE = 'l';
	public static final char MOVETO = 'm';
	public static final char BEZIER = 'b';
	public static final char IMAGE = 'i';
	public static final char TRANSPARENT = 't';

	public int id;
	public char type;

	private Integer prevId, nextId;
	private Elem prev, next;
	private Point origin;

	private String fillColor = null;
	private String strokeColor = null;
	private Integer strokeWidth = null;

	private String textFillColor = null;
	private String textStrokeColor = null;
	private Integer textStrokeWidth = null;

	private String text = null;
	private Point dp = null;
	private Double rot = null;
	private Point cp1 = null;
	private Point cp2 = null;
	private String font = null;
	private Integer fontWidth = null;
	private String img = null;

	private Four margin;

	private Integer selector = null;
	private boolean selected = false;
	private int drawIndex = 0;

	protected boolean textFitPage = true;
	
	
	private boolean hidden=false;
	private Elem parent,child;
	

	public Elem(char type, Elem prev, int x, int y) {
		this.id = Serial++;
		this.type = type;
		if (prev != null) {
			setPrev(prev);
			if (prev.getNext() != null) {
				this.setNext(prev.getNext());
				prev.getNext().setPrev(this);
			}
			prev.setNext(this);
		}
		origin = new Point(x, y);
	}

	public Elem(char c, JSONObject jo) throws RunException {
		this.type = c;
		id = ClientUtil.optInteger(jo, "id");
		prevId = ClientUtil.optInteger(jo, "prev");
		nextId = ClientUtil.optInteger(jo, "next");
		origin = optPoint(ClientUtil.optArray(jo, "orig"));

		fillColor = ClientUtil.optString(jo, "fc");
		strokeColor = ClientUtil.optString(jo, "sc");
		strokeWidth = ClientUtil.optInteger(jo, "sw");

		textFillColor = ClientUtil.optString(jo, "tfc");
		textStrokeColor = ClientUtil.optString(jo, "tsc");
		textStrokeWidth = ClientUtil.optInteger(jo, "tsw");

		text = ClientUtil.optString(jo, "text");
		dp = optPoint(ClientUtil.optArray(jo, "dp"));
		rot = ClientUtil.optDouble(jo, "rot");
		cp1 = optPoint(ClientUtil.optArray(jo, "cp1"));
		cp2 = optPoint(ClientUtil.optArray(jo, "cp2"));
		font = ClientUtil.optString(jo, "f");
		fontWidth = ClientUtil.optInteger(jo, "fw");
		img = ClientUtil.optString(jo, "img");
		margin = optFour(ClientUtil.optArray(jo, "margin"));
	}

	public void fillSecondPass(Db db) throws RunException {
		if (prevId != null)
			prev = db.getElemById(prevId);
		if (nextId != null)
			next = db.getElemById(nextId);
	}

	public JSONObject toJson() {
		JSONObject ret = new JSONObject();
		ret.put("tp", new JSONNumber(type));
		ret.put("id", new JSONNumber(id));
		ClientUtil.safePut(ret, "prev", prevId);
		ClientUtil.safePut(ret, "next", nextId);
		if (origin != null)
			ret.put("orig", ClientUtil.toJson(origin));

		ClientUtil.safePut(ret, "fc", fillColor);
		ClientUtil.safePut(ret, "sc", strokeColor);
		ClientUtil.safePut(ret, "sw", strokeWidth);

		ClientUtil.safePut(ret, "tfc", textFillColor);
		ClientUtil.safePut(ret, "tsc", textStrokeColor);
		ClientUtil.safePut(ret, "tsw", textStrokeWidth);

		ClientUtil.safePut(ret, "text", text);

		if (dp != null)
			ret.put("dp", ClientUtil.toJson(dp));

		ClientUtil.safePut(ret, "rot", rot);

		if (cp1 != null)
			ret.put("cp1", ClientUtil.toJson(cp1));
		if (cp2 != null)
			ret.put("cp2", ClientUtil.toJson(cp2));

		if (margin != null)
			ret.put("margin", ClientUtil.toJson(margin));

		ClientUtil.safePut(ret, "f", font);
		ClientUtil.safePut(ret, "fw", fontWidth);
		ClientUtil.safePut(ret, "img", img);

		return ret;
	}

	private Point optPoint(JSONArray optArray) {
		if (optArray == null)
			return null;
		return ClientUtil.getPoint(optArray);
	}

	private Four optFour(JSONArray optArray) {
		if (optArray == null)
			return null;
		return ClientUtil.getFour(optArray);
	}

	public String toString() {
		return type + " cp1:" + getCp1() + " cp2:" + getCp2();
	}

	public void updateOrig(int x, int y) {
		this.origin.setX(x);
		this.origin.setY(y);
	}

	public void onDelete() {
		if (prev != null && next != null) {
			prev.setNext(next);
			next.setPrev(prev);
		} else if (next != null) {
			next.setPrev(null);
		} else if (prev != null) {
			prev.setNext(null);
		}
	}

	public char getType() {
		return type;
	}

	abstract public void draw(final CanvasContext c);

	public Elem getHead() {
		Elem ret = this;
		while (ret.getPrev() != null) {
			ret = ret.getPrev();
		}
		return ret;
	}

	public Elem getPrev() {
		return prev;
	}

	public void setPrev(Elem prev) {
		this.prev = prev;
		if (prev == null)
			prevId = null;
		else
			prevId = prev.id;
	}

	public Elem getNext() {
		return next;
	}

	public void setNext(Elem next) {
		this.next = next;
		if (next == null)
			nextId = null;
		else
			nextId = next.id;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public double distance(Point p) {
		return getOrigin().distance(p);
	}

	// public Point center(){
	// if(dp==null){
	// // it is a line or text
	// Elem n=getNext();
	// if(n==null)
	// return origin;
	//
	//
	// }
	// }

	public void setSelected(boolean b) {
		this.selected = b;
	}

	public Integer getSelector() {
		return selector;
	}

	public void setSelector(Integer selector) {
		this.selector = selector;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isPathElem() {
		return false;
	}

	public int getDrawIndex() {
		return drawIndex;
	}

	public void setDrawIndex(int drawIndex) {
		this.drawIndex = drawIndex;
	}

	public String getFillColor() {
		return fillColor;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public Integer getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(Integer strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getRot() {
		return rot;
	}

	public void setRot(Double rot) {
		this.rot = rot;
	}

	public boolean getEdit() {
		return (this.type == 'i' && this.getImg().startsWith(GraphicEditor.userDir)) || this.type == 'r';
		// return (this.type == 'i' &&
		// this.getImg().startsWith(GraphicEditor.userDir));
	}

	public Point getCp1() {
		return cp1;
	}

	public void setCp1(Point cp1) {
		this.cp1 = cp1;
	}

	public Point getCp2() {
		return cp2;
	}

	public void setCp2(Point cp2) {
		this.cp2 = cp2;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Integer getFontWidth() {
		return fontWidth;
	}

	public void setFontWidth(Integer fontWidth) {
		this.fontWidth = fontWidth;
	}

	public String getTextFillColor() {
		return textFillColor;
	}

	public void setTextFillColor(String textFillColor) {
		this.textFillColor = textFillColor;
	}

	public String getTextStrokeColor() {
		return textStrokeColor;
	}

	public void setTextStrokeColor(String textStrokeColor) {
		this.textStrokeColor = textStrokeColor;
	}

	public Integer getTextStrokeWidth() {
		return textStrokeWidth;
	}

	public void setTextStrokeWidth(Integer textStrokeWidth) {
		this.textStrokeWidth = textStrokeWidth;
	}

	public void future(GraphicWaiting waiting) {
	}

	public String getImg() {
		return img;
	}

	public void imageChanged() {
		img = ClientUtil.getLastUri(img);
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Point getDp() {
		return dp;
	}

	public void setDp(Point dp) {
		this.dp = dp;
	}

	@Override
	public int compareTo(Elem o) {
		return new Double(area()).compareTo(o.area());
	}

	private double area() {
		return getBorder().area();
	}

	public Rectangular getBorder() {
		Point np = dp;
		if (np == null) {
//			if (next != null) {
//				np = next.dp;
//			}
			if (np == null)
				np = new Point(30, 30);
		}
		return new Rectangular(origin.getX(), origin.getY(), np.getX(), np.getY(), rot);
	}

	public void drawBorder(final CanvasContext c) {
		// c.restoreStyle();

		Rectangular cur = getBorder();
		Point o = cur.getOrigin();

		c.setStrokeStyle(SELECT_COLOR);
		c.setLineWidth(3);

		c.beginPath();
		double a = cur.getRot();
		c.setTransform(a, origin.getX(), origin.getY());
		c.rect(getDp().getX(), getDp().getY());
		
		int rd = Math.min(getDp().getX()/6, getDp().getY()/6);
		
		
		c.moveTo(rd, 0);
//		c.lineTo(getDp().getX()/6, 0);
//		c.moveTo(0, 0);
		
		c.arcTo(rd, rd, 0, rd, rd);
		
		int arw=8;
		
		c.moveTo(arw, rd+arw);
		
		c.lineTo(0, rd);
		
		c.lineTo(arw, rd-arw);
//		
		
		
//		int lineW= (int) (Math.sqrt(rdx*rdx + rdy*rdy)/6);
		
		c.moveTo(getDp().getX(), getDp().getY());
		
		c.lineTo(getDp().getX()-rd, getDp().getY()-rd);
		
		c.lineTo(getDp().getX()-rd+arw+1, getDp().getY()-rd -1);
		
		c.moveTo(getDp().getX()-rd-1, getDp().getY()-rd);
		
		c.lineTo(getDp().getX()-rd-3, getDp().getY()-rd+arw+1);
		
		
		
		
		c.stroke();
		c.setStrokeStyle(TRANPARENT_COLOR);
		c.resetTransform();
	}

	public void prepareDraw(CanvasContext c) {

	}

	public void reloadImg(String href) {

	}

	protected void setStyle(CanvasContext canvasContext) {
		if (getFillColor() != null) {
			canvasContext.setFillStyle(Drawer.getColor(getFillColor()));
		}
		if (getStrokeColor() != null) {
			canvasContext.setStrokeStyle(Drawer.getColor(getStrokeColor()));
		}
		if (getStrokeWidth() != null) {
			canvasContext.setLineWidth(getStrokeWidth());
		}
	}

	protected void resetStyle(CanvasContext canvasContext) {
		if (getFillColor() != null) {
			canvasContext.setFillStyle(TRANPARENT_COLOR);
		}
		if (getStrokeColor() != null) {
			canvasContext.setStrokeStyle(TRANPARENT_COLOR);
		}
		if (getStrokeWidth() != null) {
			canvasContext.setLineWidth(1);
		}
	}

	public void transformMatrix(final CanvasContext c) {
		Point o = getOrigin();
		if (getRot() == null) {
			c.resetTransform();
		} else {
			c.setTransform(getRot(), getOrigin().getX(), getOrigin().getY());
		}
	}

	public boolean hasSibling() {
		return prev!=null || next!=null;
	}

	public void drawLinkedStart(Elem head, Viewing viewing) {
		// TODO Auto-generated method stub
		
	}

	public void drawLinkedEnd(Elem head, Viewing viewing) {
		// TODO Auto-generated method stub
		
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public double radius() {
		return this.dp.diff(0, 0);
	}
	
}
