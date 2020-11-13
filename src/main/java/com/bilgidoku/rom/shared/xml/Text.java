package com.bilgidoku.rom.shared.xml;


public class Text extends Elem{

	private String text;

	public Text(String text) {
		super("text");
		this.text=text;
	}
	
	public void makeStr(StringBuilder sb) {
		if(text==null)
			return;
		sb.append(text);
	}
}
