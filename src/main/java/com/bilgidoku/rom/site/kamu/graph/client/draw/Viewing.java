package com.bilgidoku.rom.site.kamu.graph.client.draw;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.db.elems.RomRGBAImageFilter;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.min.geo.Point;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;

public class Viewing implements CanvasContext {
	private double scale = 2f;

	int scrollX = 150;
	private int scrollY = 50;

	private final int realW;
	private final int realH;

	private final int imgX;
	private final int imgY;
	private final int imgW;
	private final int imgH;

	private boolean modeImgAlone = false;

	private Context2d context;

	private int canvasH;

	private int canvasW;

	private double fitWidthScale;

	public Viewing(int realSizeX, int realSizeY, int imgX, int imgY, int imgW, int imgH, int canvasW, int canvasH) {
		this.realW = realSizeX;
		this.realH = realSizeY;
		this.imgX = imgX;
		this.imgY = imgY;
		this.imgW = imgW;
		this.imgH = imgH;
		this.canvasW = canvasW;
		this.canvasH = canvasH;

		this.fitWidthScale = (double) canvasW / realW;

		setViewScale(1);

	}

	public void setMode(Context2d context, boolean mode) {
		this.context = context;
		this.modeImgAlone = mode;
	}

	public void resetScroll() {
		this.scrollX = 0;
		this.scrollY = 0;
	}

	// public void diffScrollX(int dx) {
	// scrollX += unscale(dx);
	// }
	//
	// public void diffScrollY(int dy) {
	// scrollY += unscale(dy);
	// }

	// public void zoom(){
	// if(canvasW<realW){
	// setScale(1);
	// }else{
	// setScale(2);
	// }
	// }
	//
	// public void fitWidth() {
	// scrollX = 0;
	// scrollY = 0;
	// scale = (double)canvasW / realW;
	// }

	public void setViewScale(double scale) {
		setScale(fitWidthScale * scale);
	}

	private void setScale(double scale) {
		this.scale = scale;

		scrollX = Math.abs((int) (unscale(canvasW) - realW) / 2);
		scrollY = Math.abs((int) (unscale(canvasH) - realH) / 2);

		// Sistem.outln(scale + "-" + scrollX + "," + scrollY);

		// scrollX=(scale(realW)-canvasW)/2;
		//
		// unscale((scale(canvasW)-realW)/2);
		//
		//
		// if(scale<=1){
		// scrollX=0;
		// scrollY=0;
		// }else{
		// scrollX=(int) unscale((scale(canvasW)-realW)/2);
		// scrollY=(int) unscale((scale(canvasH)-realH)/2);
		//// Sistem.outln(scrollX+","+scrollY+"-"+realW+"-"+realH+";"+canvasW);
		// }
	}

	public double scale(double l) {
		if (modeImgAlone)
			return l;
		return l * scale;
	}

	private double h(int h) {
		if (modeImgAlone)
			return h;

		return scale(h);
	}

	private double w(int w) {
		if (modeImgAlone)
			return w;
		return scale(w);
	}

	private double y(double d) {
		if (modeImgAlone)
			return d;

		return windowY(scale(d));
	}

	public double mouseToRealX(int vx) {
		// return unscale(vx - scale(imgX) + scale(scrollX));
		return unscale(vx) + scrollX;
	}

	public double mouseToRealY(int vy) {
		// return unscale(vy - scale(imgY) + scale(scrollY));
		return unscale(vy) + scrollY;
	}

	private double unscale(double d) {
		return d / scale;
	}

	private double x(double d) {
		if (modeImgAlone)
			return d;

		return windowX(scale(d));
	}

	private double windowX(double scaledX) {
		// return scaledX - scale(scrollX) + scale(imgX);
		return scaledX - scale(scrollX);
	}

	private double windowY(double scaledY) {
		// return scaledY - scale(scrollY) + scale(imgY);
		return scaledY - scale(scrollY);
	}

	@Override
	public void setStrokeStyle(FillStrokeStyle selectColor) {
		context.setStrokeStyle(selectColor);
	}

	@Override
	public void setLineWidth(int i) {
		context.setLineWidth(scale(i));

	}

	@Override
	public void beginPath() {
		context.beginPath();
	}

	@Override
	public void setTransform(double a, double x, double y) {
		// Sistem.outln("TrR:"+x+":"+y);
		// Sistem.outln("Tr:"+x(x)+":"+y(y));
		context.setTransform(Math.cos(a), -Math.sin(a), Math.sin(a), Math.cos(a), x(x), y(y));
	}

	@Override
	public void resetTransform() {
		context.setTransform(1, 0, 0, 1, 0, 0);
	}

	@Override
	public void rect(int w, int h) {
		context.rect(0, 0, w(w), h(h));
	}

	@Override
	public void closePath() {
		context.closePath();

	}

	@Override
	public void stroke() {
		context.stroke();
	}

	@Override
	public void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x, int y) {
		context.bezierCurveTo(x(cp1x), y(cp1y), x(cp2x), y(cp2y), x(x), y(y));
	}

	@Override
	public void drawImage(ImageElement imageElement, int w, int h) {
		context.drawImage(imageElement, 0, 0, w(w), h(h));
	}

	@Override
	public void lineTo(int x, int y) {
		context.lineTo(x(x), y(y));
	}

	@Override
	public void moveTo(int x, int y) {
//		Sistem.outln(x(x) + "-" + y(y));
		context.moveTo(x(x), y(y));
	}

	@Override
	public void setFont(String string) {
		context.setFont(string);
	}

	@Override
	public int measureText(String selText) {
		return (int) context.measureText(selText).getWidth();
	}

	@Override
	public void setTextAlign(String string) {
		context.setTextAlign(string);
	}

	@Override
	public void setTextBaseline(String string) {
		context.setTextBaseline(string);
	}

	@Override
	public void fillText(String string, int x, int y) {
		context.fillText(string, w(x), h(y));
	}

	@Override
	public void strokeText(String string, int x, int y) {
		context.strokeText(string, w(x), h(y));
	}

	public void showButtons(EditElemUi ui, Elem cur) {
		if (modeImgAlone)
			return;

		int cx = cur.getOrigin().getX();
		int cy = cur.getOrigin().getY();

		double x = x(cx);
		double y = y(cy);

		showButton(ui.del, x, y);
		if(!GraphicEditor.db.atDeepBack(cur)){
			showButton(ui.back, x - 18, y);
		}

		if (cur.getEdit()) {

//			Point t = new Point(0, cur.getDp().getY() / 2);
//			t.rotate(-cur.getRot());
//
//			double dx = x(cx + t.getX() / 2);
//			double dy = y(cy + t.getY() / 2);

			showButton(ui.edit, x + 18, y);

		} else {
			hideButton(ui.edit);
		}
	}

	private void hideButton(Button but) {
		but.setVisible(false);
	}

	private void showButton(Button but, double x, double y) {
		if (x < 0 || y < 0) {
			but.setVisible(false);
			return;
		}
		but.getElement().getStyle().setLeft(x - 8, Unit.PX);
		but.getElement().getStyle().setTop(y - 8, Unit.PX);
		but.setVisible(true);
	}

	public static final FillStrokeStyle CLIP_COLOR = CssColor.make("rgba(133, 78, 70, 0.2)"); // #B3FFB3

	public void clip() {
		if (this.modeImgAlone)
			return;
		// Sistem.outln(x(imgX)+","+y(imgY)+","+w(imgW)+","+h(imgH));
		context.beginPath();
		context.rect(x(imgX), y(imgY), w(imgW), h(imgH));
		// context.stroke();
		context.clip();
		context.closePath();

	}

	public void drawBackImage(ImageElement imageElement) {
		if (modeImgAlone)
			return;
		// context.drawImage(imageElement, x(-imgX), y(-imgY), w(realW),
		// h(realH));
		context.beginPath();
		context.drawImage(imageElement, x(0), y(0), w(realW), h(realH));
		context.closePath();
	}

	public void clear() {
		context.restore();
		context.beginPath();
		context.clearRect(0, 0, w(realW), h(realH));
		context.fill();
		context.closePath();
	}

	@Override
	public void setFillStyle(FillStrokeStyle color) {
		context.setFillStyle(color);
	}

	@Override
	public void fill() {
		context.fill();
	}

	public void initCanvas(Canvas canvas) {
		canvas.setSize(canvasW + "px", canvasH + "px");
		canvas.setCoordinateSpaceWidth(canvasW);
		canvas.setCoordinateSpaceHeight(canvasH);
	}

	public void pan(int difX, int difY) {
		difX = (int) scale(difX);
		difY = (int) scale(difY);

		if (scrollX - difX < 0 || (scale(scrollX - difX) + canvasW) > scale(realW)) {
			difX = 0;
		}

		if (scrollY - difY < 0 || (scale(scrollY - difY) + canvasH) > scale(realH)) {
			difY = 0;
		}

		scrollX -= difX;
		scrollY -= difY;
	}

	public Point getTextPosition(Elem cur) {

		int cx = cur.getOrigin().getX();
		int cy = cur.getOrigin().getY();

		double x = x(cx);
		double y = y(cy);
		return new Point((int) Math.round(x) - 50, (int) Math.round(y) - 200);

	}

	@Override
	public void saveStyle() {
		context.save();
	}

	@Override
	public void restoreStyle() {
		context.restore();
	}

	public void setContext(Context2d contextPrivate) {
		this.context = contextPrivate;
	}

	@Override
	public void resetTransformMatrix() {
		context.setTransform(1, 0, 0, 1, 0, 0);
	}

	@Override
	public void resetFont() {
		context.setFont("10px sans-serif");
	}

	@Override
	public void applyRGBFilter(RomRGBAImageFilter romRGBAImageFilter) {
		// ImageData imageData = context.getImageData(0, 0, c..getWidth(),
		// image.getHeight());

		// context.
		//
		//
		// int[] ret = new int[4];
		// ret[0] = imageData.getRedAt(x, y);
		// ret[1] = imageData.getGreenAt(x, y);
		// ret[2] = imageData.getBlueAt(x, y);
		// ret[3] = imageData.getAlphaAt(x, y);
	}

	@Override
	public void arcTo(int x1, int y1, int x2, int y2, double radius) {
//		Sistem.outln(x(x1) + "-" + y(y1) + "-" + x(x2) + "-" + y(y2) + "-" + w((int) radius));
		context.arcTo(x(x1), y(y1), x(x2), y(y2), w((int) radius));

	}

}
