package com.bilgidoku.rom.site.kamu.graph.client.draw;

import com.bilgidoku.rom.site.kamu.graph.client.db.elems.RomRGBAImageFilter;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.dom.client.ImageElement;

public interface CanvasContext {

	void setFillStyle(FillStrokeStyle color);
	void setStrokeStyle(FillStrokeStyle selectColor);

	void setLineWidth(int i);

	void beginPath();


	void rect(int viewX, int viewY);

	void closePath();

	void stroke();

	void drawImage(ImageElement imageElement, int viewX, int viewY);

	void lineTo(int viewX, int viewY);

	void moveTo(int viewX, int viewY);

	void setFont(String string);
	void resetFont();

	int measureText(String selText);

	void setTextAlign(String string);

	void setTextBaseline(String string);

	void fillText(String string, int sx, int sy);
	void strokeText(String string, int sx, int sy);

	void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x, int y);

	void setTransform(double rot, double x, double y);

	void resetTransform();

	void fill();
	double scale(double fontWidth);
	void saveStyle();
	void restoreStyle();
	void resetTransformMatrix();
	void applyRGBFilter(RomRGBAImageFilter romRGBAImageFilter);
	void arcTo(int i, int j, int k, int l, double a);


}
