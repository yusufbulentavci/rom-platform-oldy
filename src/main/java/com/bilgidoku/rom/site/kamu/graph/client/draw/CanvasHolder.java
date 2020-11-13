package com.bilgidoku.rom.site.kamu.graph.client.draw;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootPanel;

public class CanvasHolder extends AbsolutePanel {

	
	
	
	
	protected int scrollX = 0;
	protected int scrollY = 0;

	private final Canvas canvas = Canvas.createIfSupported();

	private int holderLeft = -1;
	private int holderTop;

	// final EditState editState;

	boolean possibleDrag = false;
	// Image backImg;
	private int lastX;
	private int lastY;
	private boolean panning;

//	native void disableScroll() /*-{
//		document.addEventListener('ontouchstart', function(e) {e.preventDefault()}, false);
//		document.addEventListener('ontouchmove', function(e) {e.preventDefault()}, false);
//	}-*/;

	public CanvasHolder(ButtonBase[] buttonBases, Button[] selectors) {
		super();

		for (Button b : selectors) {
			b.setVisible(false);
			b.getElement().getStyle().setZIndex(1000);
			add(b, 0, 0);
		}

		for (ButtonBase b : buttonBases) {
			b.setVisible(false);
			b.getElement().getStyle().setZIndex(1000);
			add(b, 0, 0);
		}

		GraphicEditor.viewing.initCanvas(canvas);

		canvas.getElement().getStyle().setZIndex(100);
		canvas.setStyleName("site-canvas");
		canvas.getElement().setDraggable("false");
		add(canvas, 0, 0);

		setStyleName("canvas-holder");
		setSize("700px", "800px");

		windUp();
//		disableScroll();
		// RootPanel.get().addDomHandler(new TouchStartHandler() {
		//
		// @Override
		// public void onTouchStart(TouchStartEvent event) {
		// Sistem.outln("GGGGGGGGGGGGGGGGGGGG");
		// event.preventDefault();
		// }
		// }, TouchStartEvent.getType());
		// DOM.setCapture(RootPanel.getBodyElement());
//		DOM.setCapture(canvas.getElement());
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void guiReady() {
		this.holderLeft = getAbsoluteLeft();
		this.holderTop = getAbsoluteTop();
	}

	private void windUp() {
		canvas.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				event.preventDefault();
				// Sistem.outln("MOVE move");
				mouseEvent(new MouseCanvasAdapter(event), 'm');
			}
		});

		canvas.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				// Sistem.outln("MOVE DOWN");
				mouseEvent(new MouseCanvasAdapter(event), 'd');
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				// Sistem.outln("MOVE UP");
				mouseEvent(new MouseCanvasAdapter(event), 'u');
			}
		});

		canvas.addTouchStartHandler(new TouchStartHandler() {

			@Override
			public void onTouchStart(TouchStartEvent event) {
//				event.preventDefault();
				TouchCanvasAdapter ev = new TouchCanvasAdapter(event);

//				Sistem.outln("TOUCH down" + ev.toString());

				mouseEvent(ev, 'd');
			}
		});

		canvas.addTouchMoveHandler(new TouchMoveHandler() {

			@Override
			public void onTouchMove(TouchMoveEvent event) {
				event.preventDefault();
				TouchCanvasAdapter ev = new TouchCanvasAdapter(event);
//				Sistem.outln("TOUCH move" + ev.toString());
				mouseEvent(ev, 'm');
			}
		});

		canvas.addTouchEndHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				event.preventDefault();
				TouchCanvasAdapter ev = new TouchCanvasAdapter(event);
//				Sistem.outln("TOUCH UP" + ev.toString());
				mouseEvent(ev, 'u');
			}
		});

	}

	int xx = 0;
	private int lastPanX;
	private int lastPanY;

	private void mouseEvent(CanvasUserActionEvent event, char type) {
		if (holderLeft == -1) {
			guiReady();
		}
		int realX = (int) GraphicEditor.viewing
				.mouseToRealX(event.getClientX() + Window.getScrollLeft() - canvas.getAbsoluteLeft());

		int realY = (int) GraphicEditor.viewing.mouseToRealY(event.getClientY()
				// + getVerticalScrollPosition()
				+ Window.getScrollTop() - canvas.getAbsoluteTop());

		if (type == 'm') {
			try {
				if (possibleDrag) {
					if (!GraphicEditor.editState.tryDrag(realX, realY)) {
						this.panning = true;
					}
					possibleDrag = false;
				}
				if (panning) {
					// Sistem.outln(realX-xx+" "+event.getClientX()+"
					// "+Window.getScrollLeft()+" "+canvas.getAbsoluteLeft()+"
					// "+GraphicEditor.viewing.scrollX);
					// xx=realX;

					if (GraphicEditor.drawer.pan(event.getClientX() - lastPanX, event.getClientY() - lastPanY)) {

						// Sistem.outln("R" + realX + "," + realY);
						// Sistem.outln("L" + lastX + "," + lastY);
						// Sistem.outln((realX-lastX)+","+(realY-lastY));

						lastPanX = event.getClientX();
						lastPanY = event.getClientY();
					}
					return;
				}
				GraphicEditor.editState.canvasTrip(realX, realY);
			} finally {
				this.lastX = realX;
				this.lastY = realY;
			}
		} else if (type == 'd') {
			GraphicEditor.editState.canvasSelect(realX, realY);
			lastPanX = event.getClientX();
			lastPanY = event.getClientY();
			possibleDrag = true;
			panning = false;
		} else if (type == 'o') {
			GraphicEditor.editState.canvasOut(realX, realY);
			panning = false;
		} else if (type == 'u') {
			possibleDrag = false;
			panning = false;
			GraphicEditor.editState.tryDrop(realX, realY);
			GraphicEditor.editState.showGetInput();
			// editState.canvasShowSelector(realX, realY);

		}
	}

	public void setPointer(Cursor cursor) {
		if (cursor == null)
			cursor = cursor.POINTER;
		canvas.getElement().getStyle().setCursor(cursor);
	}

	public void setBackgroundColor(String color) {
		canvas.getElement().getStyle().setBackgroundColor(color);

	}

	// public Image getBackImg() {
	// return backImg;
	// }
}

interface CanvasUserActionEvent {

	int getClientX();

	int getClientY();

}

class MouseCanvasAdapter implements CanvasUserActionEvent {

	private MouseEvent e;

	MouseCanvasAdapter(MouseEvent e) {
		this.e = e;
	}

	@Override
	public int getClientX() {
		return e.getClientX();
	}

	@Override
	public int getClientY() {
		return e.getClientY();
	}

}

class TouchCanvasAdapter implements CanvasUserActionEvent {

	private Touch e;

	TouchCanvasAdapter(TouchEvent e) {
		if (e.getTouches().length() == 0) {
			this.e = (Touch) (e.getChangedTouches().get(0));
		} else {
			this.e = (Touch) (e.getTouches().get(0));
		}
	}

	@Override
	public int getClientX() {
		return e.getClientX();
	}

	@Override
	public int getClientY() {
		return e.getClientY();
	}

	public String toString() {
		return "x:" + getClientX() + ", y:" + getClientY();
	}

}