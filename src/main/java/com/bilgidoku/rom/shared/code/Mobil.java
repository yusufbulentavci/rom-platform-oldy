package com.bilgidoku.rom.shared.code;

import java.util.Map;

class Mobil implements Comparable{
	final Code c;
	int top;
	int left;
	int height;
	int width;
	
	private Map<String, String> defaultStyle;
	private int centerLeft;
	// mobil için uygun olabilmesi için width'in belli boyutlarda olması gerek
	private boolean mobileOk;
	private Integer order;
	
	public int newTop;
	public int newLeft;
	boolean newChanged=false;
	
	public Mobil(Code c){
		this.c = c;
		defaultStyle=c.getDefaultStyle();
		top = Code.intPx(defaultStyle.get("top"));
		left = Code.intPx(defaultStyle.get("left"));
		height = Code.intPx(defaultStyle.get("height"));
		width = Code.intPx(defaultStyle.get("width"));
		
		init();
	}

	private void init() {
		this.centerLeft=left+width/2;
		this.mobileOk=width<600;
		this.order=left*left+top*top;
	}
	
	protected Mobil(Code c, Map<String, String> defaultStyle, int top, int left, int height, int width){
		this.c = c;
		defaultStyle=c.getDefaultStyle();
		top = Code.intPx(defaultStyle.get("top"));
		left = Code.intPx(defaultStyle.get("left"));
		height = Code.intPx(defaultStyle.get("height"));
		width = Code.intPx(defaultStyle.get("width"));
		init();
	}

	@Override
	public int compareTo(Object o) {
		return order.compareTo(((Mobil)o).order);
	}

	public int scrWidth() {
		return left+width;
	}

	public int scrHeight() {
		return top+height;
	}

	public boolean isToNewLine() {
		return mobileOk && centerLeft>400;
	}

	public int newLine(Mobil mobil, int lastScrHeight) {
		
		newChanged=true;
		newTop=lastScrHeight;
		top=lastScrHeight;
		newLeft=0;
		left=newLeft;
		
		return scrHeight();
	}

	public void applyNew() {
		if(!newChanged)
			return;
		this.c.setPx("top", newTop);
		this.c.setPx("left", newLeft);
	}
	
}