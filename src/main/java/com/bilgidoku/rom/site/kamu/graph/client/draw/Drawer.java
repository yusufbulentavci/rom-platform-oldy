package com.bilgidoku.rom.site.kamu.graph.client.draw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ImgUtil;
import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.db.GraphicWaiting;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

public class Drawer implements GraphicWaiting , RepeatingCommand {

	private static final int CONTROL_PERIOD = 50;

	public static final CssColor CLEAN_COLOR = CssColor.make("rgba(0,255,255,1)"); // cyan
	public static final CssColor REDRAW_COLOR = CssColor.make("rgba(0,0,0,1)"); // black
	public static final FillStrokeStyle TRANPARENT_COLOR = CssColor.make("rgba(0,0,0,0)"); // #B3FFB3

	private final Db db;
	private final Canvas canvas;
	private final Viewing viewing;
	private final CanvasContext canvasContext;

	private int drawIndex = 0;

	private Context2d contextPrivate;

	private int waitCount;
	private Image backImg;
	private boolean dirty = true;

	private Runnable done;

	private boolean reReDraw = false;

	private GraphicEditor ge;

	public Drawer(Db db, Canvas cnv, final Viewing vw, final GraphicEditor ge) {
		this.db = db;
		this.canvas = cnv;
		this.viewing = vw;
		this.canvasContext = vw;
		this.ge = ge;

		if (db.getBackImage() != null) {
			ge.setStatus("Tasarım Yükleniyor...");
			this.backImg = ImgUtil.load(db.getBackImage(), new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean result) {
					ge.setStatus("Arka plan yüklendi");
					loadImages(new Runnable() {
						@Override
						public void run() {
							
							contextPrivate = canvas.getContext2d();
							contextPrivate.save();
							viewing.setMode(contextPrivate, false);
							// fontlar için fixti
							Scheduler.get().scheduleFixedDelay(Drawer.this, CONTROL_PERIOD);
							
						}
					});
				}

				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
	}

	private int loadCount = 0;

	private void loadImages(final Runnable runnable) {
		List<String> images = null;
		Iterator<Elem> a = db.getElems();
		while (a.hasNext()) {
			Elem elem = a.next();
			if (elem.getImg() != null) {
				if (images == null)
					images = new ArrayList<>();
				images.add(elem.getImg());
			}
		}

		if (images == null || images.size() == 0) {
			runnable.run();
			return;
		}

		loadCount = images.size();

		for (String uri : images) {
			ImgUtil.load(uri, new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean result) {
					loadCount--;
					checkFinished(runnable);
				}

				@Override
				public void onFailure(Throwable caught) {
					loadCount--;
					checkFinished(runnable);
				}
			});
		}

		checkFinished(runnable);
	}

	protected void checkFinished(Runnable runnable) {

		if (loadCount > 0)
			return;
		
		ge.setStatus("Resimler yüklendi");
		runnable.run();

	}

	@Override
	public boolean execute() {
		if (dirty) {
			dirty = false;
			draw(null);

			if (reReDraw) {
				reReDraw = false;
				scheduleRedraw();
			}
		}
		return true;
	}
	
	public void scheduleReredraw() {
		this.reReDraw = true;
		scheduleRedraw();
	}

	public void scheduleRedraw() {
		this.dirty = true;
	}

	public void draw(Runnable done) {
		this.done = done;
		graphicStart();
		Iterator<Elem> a = db.getElems();
		while (a.hasNext()) {
			Elem elem = a.next();
			elem.future(this);
		}
		this.graphicReady();
	}

	private void drawing() {

		// contextPrivate=canvas.getContext2d();
		// viewing.setContext(contextPrivate);
		viewing.clear();

		if (backImg != null)
			viewing.drawBackImage((ImageElement) backImg.getElement().cast());

		viewing.saveStyle();
		viewing.clip();

		Iterator<Elem> a = db.getElems();
		++drawIndex;
		while (a.hasNext()) {
			Elem cur = a.next();
			Elem first = null;

			if (drawIndex == cur.getDrawIndex())
				break;

			if (cur.hasSibling()) {
				cur = cur.getHead();
				Elem head = cur;
				cur.drawLinkedStart(head, viewing);
				Elem lastCur = cur;
				while (cur != null) {
					drawElem(cur);
					lastCur = cur;
					cur = cur.getNext();
				}
				lastCur.drawLinkedEnd(head, viewing);

			} else {
				drawElem(cur);
				if (cur.isSelected()) {
					cur.drawBorder(canvasContext);
				}
			}

		}

		viewing.restoreStyle();

	}

	private void drawElem(Elem cur) {
		cur.setDrawIndex(drawIndex);
		Point o = cur.getOrigin();
		cur.transformMatrix(viewing);

		cur.prepareDraw(viewing);
		cur.draw(viewing);

		canvasContext.resetTransform();
	}

	public static FillStrokeStyle getColor(String colorHex) {
		// CssColor c = colorCache.get(colorHex);
		// if(c==null){
		// c=CssColor.make(colorHex);
		// colorCache.put(colorHex, c);
		// }
		return CssColor.make(colorHex);
	}

	@Override
	public void graphicStart() {
		this.waitCount = 1;
	}

	@Override
	public void graphicWait() {
		this.waitCount++;
	}

	@Override
	public void graphicReady() {
		this.waitCount--;
		if (this.waitCount != 0) {
			return;
		}
		drawing();
		if (done != null) {
			done.run();
			done = null;
		}
	}

	// private void redraw(Runnable done) {
	// draw(done);
	// lastDraw = System.currentTimeMillis();
	// redrawPassed = null;
	// }
	//
	// private void redraw() {
	// redraw(null);
	// }

	public void drawImg(final AsyncCallback<String> ret) {

		final Canvas canvas = Canvas.createIfSupported();

		// Do nothing if canvas is not supported.
		if (canvas == null)
			return;

		// Size the canvas to fit the image.
		canvas.setCoordinateSpaceHeight(GraphicEditor.db.getBackImageSize().getY());
		canvas.setCoordinateSpaceWidth(GraphicEditor.db.getBackImageSize().getX());

		Context2d drawContext = canvas.getContext2d();
		drawContext.save();
		viewing.setMode(drawContext, true);
		draw(new Runnable() {
			@Override
			public void run() {
				viewing.setMode(contextPrivate, false);
				ret.onSuccess(canvas.toDataUrl("image/png"));
			}
		});

	}

	public boolean pan(int difX, int difY) {
		if (this.dirty)
			return false;
		GraphicEditor.viewing.pan(difX, difY);
		scheduleRedraw();
		return true;
	}


}
