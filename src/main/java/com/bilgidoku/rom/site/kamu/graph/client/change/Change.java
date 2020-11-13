package com.bilgidoku.rom.site.kamu.graph.client.change;

import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.google.gwt.dom.client.Style.Cursor;

public abstract class Change {
	private String fillColor;
	private String strokeColor;
	private String textFillColor;
	private Integer strokeWidth;
	private String text;
	private String font;
	private Integer fontWidth;
	private Integer textStrokeWidth;
	public Cursor cursor = Cursor.POINTER;
	private String textStrokeColor;

	public abstract void doit(Db db);

	public abstract void undo(Db db);

	public abstract Change clone();

	public abstract void update(int x, int y);

	public abstract boolean isCreator();

	public boolean isTransform() {
		return false;
	}

	protected Change copy(Change change) {
		change.fillColor = fillColor;
		change.strokeColor = strokeColor;
		change.strokeWidth = strokeWidth;
		change.text = text;
		change.font = font;
		change.fontWidth = fontWidth;
		change.textFillColor = textFillColor;
		change.textStrokeColor = textStrokeColor;
		change.textStrokeWidth = textStrokeWidth;
		return change;
	}

	protected Elem copy(Elem elem) {
		Elem e = elem;
		if (this.getFillColor() != null)
			e.setFillColor(this.getFillColor());
		if (this.getStrokeColor() != null)
			e.setStrokeColor(this.getStrokeColor());
		if (this.getStrokeWidth() != null)
			e.setStrokeWidth(this.getStrokeWidth());
		if (this.getText() != null)
			e.setText(this.getText());
		if (this.getFont() != null)
			e.setFont(this.getFont());
		if (this.getFontWidth() != null)
			e.setFontWidth(this.getFontWidth());
		if (this.getTextFillColor() != null)
			e.setTextFillColor(this.getTextFillColor());
		if (this.getTextStrokeColor() != null)
			e.setTextStrokeColor(this.getTextStrokeColor());

		if (this.getTextStrokeWidth() != null)
			e.setTextStrokeWidth(this.getTextStrokeWidth());

		return elem;
	}

	public String getTextStrokeColor() {
		return textStrokeColor;
	}

	public void setTextStrokeColor(String color) {
		this.textStrokeColor = color;
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

	public void setTextFillColor(String color) {
		textFillColor = color;
	}

	public String getTextFillColor() {
		return textFillColor;
	}

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public abstract String getName();

	public Integer getTextStrokeWidth() {
		return textStrokeWidth;
	}

	public void setTextStrokeWidth(Integer textStrokeWidth) {
		this.textStrokeWidth = textStrokeWidth;
	}

}
