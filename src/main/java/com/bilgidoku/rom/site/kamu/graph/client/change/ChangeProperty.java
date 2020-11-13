package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;

public class ChangeProperty extends Change{

	private String oldFillColor;
	private String oldStrokeColor;
	private Integer oldStrokeWidth;
	private String oldText;
	private String oldFont;
	private Integer oldFontWidth;
	
	
	private String oldTextFillColor;
	private String oldTextStrokeColor;
	private Integer oldTextStrokeWidth;
	
	private Elem toChange;
	
	public ChangeProperty(Elem elem){
		this.toChange=elem;
	}
	
	
	@Override
	public void doit(Db db) {
		oldFillColor=toChange.getFillColor();
		oldStrokeColor=toChange.getStrokeColor();
		oldStrokeWidth=toChange.getStrokeWidth();
		oldText=toChange.getText();
		oldFont=toChange.getFont();
		oldFontWidth=toChange.getFontWidth();
		
		oldTextFillColor=toChange.getTextFillColor();
		oldTextStrokeColor=toChange.getTextStrokeColor();
		oldTextStrokeWidth=toChange.getTextStrokeWidth();
		
		this.copy(toChange);
	}

	@Override
	public void undo(Db db) {
		toChange.setFillColor(oldFillColor);
		toChange.setStrokeColor(oldStrokeColor);
		toChange.setStrokeWidth(oldStrokeWidth);
		toChange.setText(oldText);
		toChange.setFont(oldFont);
		toChange.setFontWidth(oldFontWidth);
		toChange.setTextStrokeWidth(oldTextStrokeWidth);
		
		toChange.setTextFillColor(oldTextFillColor);
		toChange.setTextStrokeColor(oldTextStrokeColor);
		toChange.setTextStrokeWidth(oldTextStrokeWidth);
	}

	@Override
	public Change clone() {
		return copy(new ChangeProperty(toChange));
	}

	@Override
	public void update(int x, int y) {
	}

	@Override
	public boolean isCreator() {
		return false;
	}


	@Override
	public String getName() {
		return "changeproperty";
	}
}
