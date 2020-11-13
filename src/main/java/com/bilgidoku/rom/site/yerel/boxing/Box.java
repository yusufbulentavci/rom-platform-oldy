package com.bilgidoku.rom.site.yerel.boxing;

import java.util.List;

import com.bilgidoku.rom.shared.code.Code;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;

public class Box {
	static final int LEFT = 0;
	static final int TOP = 1;
	static final int RIGTH = 2;
	static final int BOTTOM = 3;

	static final int CENTER = 4;

	static final int EDIT = 5;

	static final int HITCENTER = (1 << CENTER);

	static final int HITLEFT = (1 << LEFT);
	static final int HITLEFTTOP = (1 << LEFT | 1 << TOP);
	static final int HITLEFTBOTTOM = (1 << LEFT | 1 << BOTTOM);
	static final int HITRIGTH = (1 << RIGTH);
	static final int HITRIGTHTOP = (1 << RIGTH | 1 << TOP);
	static final int HITRIGTHBOTTOM = (1 << RIGTH | 1 << BOTTOM);
	static final int HITTOP = (1 << TOP);
	static final int HITBOTTOM = (1 << BOTTOM);

	// static final int E = (1 << EDIT);

	private static final int SNAPHIT = 8;
	private int left, top, width, height;
	private int padLeft, padTop, padRight, padBottom;

	final String id;
	final private Code code;
	private int originalx;
	private int originaly;
	private int originalw;
	private int originalh;
	
//	private boolean cbOn=false;
//	private List<BoxCb> cbs;
//	
//	public void setCb(boolean cbOn){
//		this.cbOn=cbOn;
//	}
//	
//	public void addCb(BoxCb cb){
//		if(this.cbs==null){
//			this.cbs=new ArrayList<BoxCb>();
//		}
//		this.cbs.add(cb);
//	}
//	
//	public void removeCb(BoxCb cb){
//		if(this.cbs==null)
//			return;
//		this.cbs.remove(cb);
//	}

	public Box(Code code, String id, int x, int y, int w, int h) {
		this.code = code;
		this.id = id;
		if (w == 0) {
			w = 10;
		}
		setLeft(x);
		setTop(y);
		setWidth(w);
		setHeight(h);
//		setPadLeft(padLeft);
//		setPadTop(padTop);
//		setPadRight(padRight);
//		setPadBottom(padBottom);
//		setStyle("borderWidth", "border-width",  borderWidth);
//		setStyle("borderColor", "border-color", borderColor);
//		setStyle("borderStyle", "border-style", borderStyle);
	}

	public boolean contains(int x, int y) {
		int useLeft = boxLeft();
		int useTop = boxTop();
		return (x >= useLeft && y >= useTop && x < useLeft + boxWidth() && y < useTop + boxHeight());
	}

	public boolean closeTo(int x, int y) {
		int useLeft = boxLeft();
		int useTop = boxTop();

		return (x >= useLeft - SNAPHIT && y >= useTop - 5 && x < useLeft + boxWidth() + SNAPHIT && y < useTop
				+ boxHeight() + SNAPHIT);
	}

	public int hitMode(int x, int y) {
		if (!closeTo(x, y))
			return 0;

		int useLeft = boxLeft();
		int useTop = boxTop();

		// if (x - useLeft >= 0 && x - useLeft < 24 && y - useTop >= 0 && y -
		// useTop <= 24)
		// return E;

		int ret = 0;

		if (hit(x, useLeft))
			ret |= HITLEFT;

		if (hit(y, useTop))
			ret |= HITTOP;

		if (hit(x, boxRight()))
			ret |= HITRIGTH;

		if (hit(y, boxBottom()))
			ret |= HITBOTTOM;

		if (ret == 0 && contains(x, y)) {
			return HITCENTER;
		}

		return ret;

	}

	private boolean hit(int x, int l) {
		return Math.abs(x - l) < SNAPHIT;
	}

	public Element getEl() {
		Element el = Document.get().getElementById(id);
		return el;
	}

	int boxTop() {
		// return left + width + getMoreWidth(this);
		return top;
	}

	int boxLeft() {
		// return left + width + getMoreWidth(this);
		return left;
	}

	int boxRight() {
		// return left + width + getMoreWidth(this);
		return left + padLeft + width + padRight;
	}

	int boxBottom() {
		// return top + height + getMoreHeight(this);
		return top + height + padTop + padBottom;
	}

	public int boxWidth() {
		return width + padRight + padLeft;
	}

	public int boxHeight() {
		return height + padTop + padBottom;
	}

	public int boxXmid() {
		return (boxLeft() + boxRight()) / 2;
	}

	public int boxYmid() {
		return (boxBottom() + boxTop()) / 2;
	}

	private void setWidth(int nw) {
		width = nw;
		
		Element el = getEl();
		el.getStyle().setWidth(nw, Unit.PX);
		
		code.setStyleByType("defaultstyle", "width", nw + "px");
	}

	public void setStyle(String gwtName, String name, String value) {		
		
		Element el = getEl();
		el.getStyle().setProperty(gwtName, value);
		
//		code.setStyleByType("defaultstyle", name, value);
	}
	
	private void setHeight(int ny) {
		height = ny;
		Element el = getEl();
		el.getStyle().setHeight(ny, Unit.PX);
		code.setStyleByType("defaultstyle", "height", ny + "px");
	}

	
	private void setTop(int y) {
		top = y;
		Element el = getEl();
		el.getStyle().setTop(y, Unit.PX);
		code.setStyleByType("defaultstyle", "top", y + "px");
	}

	private void setLeft(int x) {
		left = x;
		Element el = getEl();
		el.getStyle().setLeft(x, Unit.PX);
		code.setStyleByType("defaultstyle", "left", x + "px");
	}

	public void setPadTop(int nw) {
		padTop = nw;
		Element el = getEl();
		el.getStyle().setPaddingTop(nw, Unit.PX);
		code.setStyleByType("defaultstyle", "padding-top", nw + "px");
	}

	public void setPadLeft(int nw) {
		padLeft = nw;
		Element el = getEl();
		el.getStyle().setPaddingLeft(nw, Unit.PX);
		code.setStyleByType("defaultstyle", "padding-left", nw + "px");
	}

	public void setPadBottom(int nw) {
		padBottom = nw;
		Element el = getEl();
		el.getStyle().setPaddingBottom(nw, Unit.PX);
		code.setStyleByType("defaultstyle", "padding-bottom", nw + "px");
	}

	public void setPadRight(int nw) {
		padRight = nw;
		Element el = getEl();
		el.getStyle().setPaddingRight(nw, Unit.PX);
		code.setStyleByType("defaultstyle", "padding-right", nw + "px");
	}

	public Integer replaceCode(List<Code> ret) {

		this.code.replaceChildren(ret);

		Element el = getEl();

		String currHeight = el.getStyle().getHeight();

		int currH = Code.intPx(currHeight);

		int newH = el.getScrollHeight() + 10;

		if (newH != currH) {
			this.height = newH;
			code.setPx("height", newH);
			setHeight(height);
			return (newH - currH);
		}

		return 0;
	}

	public boolean isHtml() {
		return code.isHtml();
	}

	public void updateFromCode() {

	}

	public void replaceHtml(String html) {
		getEl().removeFromParent();

	}

	public void cx(int nx, int nw) {
		setLeft(nx);
		setWidth(nw);
	}

	public void width(int nw) {
		width = nw;
		setWidth(nw);
	}

	public void cy(int ny, int i) {
		setTop(ny);
		setHeight(i);
	}

	public void height(int ny) {
		setHeight(ny);
	}

	public void setXy(int x, int y) {
		setLeft(x);
		setTop(y);
	}

	public void storeDims() {
		originalx = boxLeft();
		originaly = boxTop();
		originalw = boxWidth();
		originalh = boxHeight();
	}
	
	public void restoreDims() {
		setLeft(originalx);
		setTop(originaly);
		setWidth(originalw);
		setHeight(originalh);
	}

	public int originalx() {
		return originalx;
	}

	public int originaly() {
		return originaly;
	}

	public int originalw() {
		return originalw;
	}

	public int originalh() {
		return originalh;
	}

	public boolean isInside(Box it) {
		return boxLeft() <= it.boxLeft() && boxTop() <= it.boxTop() && boxRight() >= it.boxRight()
				&& boxBottom() >= it.boxBottom();
	}

	public boolean smaller(Box it) {
		
		return alan()<it.alan();
	}

	public int alan() {
		return width*height;
	}

	public Code getCode() {
		return code;
	}

	
//	if(cbOn && this.cbs!=null){
//		for (CodeCb it : this.cbs) {
//			it.animationChanged(this, ai);
//		}
//	}
	
	
		

}
