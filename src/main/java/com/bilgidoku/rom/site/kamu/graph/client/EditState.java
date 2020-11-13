package com.bilgidoku.rom.site.kamu.graph.client;

import java.util.Iterator;

import com.bilgidoku.rom.site.kamu.graph.client.change.Change;
import com.bilgidoku.rom.site.kamu.graph.client.change.DragDrop;
import com.bilgidoku.rom.site.kamu.graph.client.change.MoveDp;
import com.bilgidoku.rom.site.kamu.graph.client.change.Rotate;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.min.geo.Point;
import com.google.gwt.dom.client.Style.Cursor;

public class EditState {

	private static final int WAIT = 0;
	private static final int GET_CHANGE = 1;
	private static final int CHANGING = 2;

	private Change waitToCreate;

	private int state = WAIT;
	private Elem dbRelated;

	private Elem selElem;

	private boolean dragging;
	private final GraphicFeedback feedback;

	public EditState(GraphicFeedback feedback) {
		this.feedback = feedback;

	}

	// //////////////////////////////////////////////////////
	// Db
	public void elemRemoved(Elem elem) {
		if (feedback != null)
			feedback.modified();
	}

	public void elemAdded(Elem elem) {
		dbRelated = elem;
		if (feedback != null)
			feedback.modified();
	}

	public void elemUpdate(Elem elem) {
		dbRelated = elem;
		if (feedback != null)
			feedback.modified();
	}

	// ///////////////////////////////////////////////////
	// Canvas handler
	public void canvasSelect(int realX, int realY) {
		// Sistem.outln("CanvasSelect:"+realX+","+realY);
		if (WAIT == state) {
			Iterator<Elem> es = GraphicEditor.db.getElems();
			while (es.hasNext()) {
				Elem e = es.next();
				// Sistem.outln("Border:"+e.getBorder().toString());
				if (e.getBorder().isIn(realX, realY)) {
					setSelElem(e);
					toGet(e, true);
				}
			}
			return;
		}

		if (state == GET_CHANGE) {
			toWait();
			canvasSelect(realX, realY);
		} else {
			changingToGet(realX, realY);
		}
	}

	public void canvasTrip(int realX, int realY) {
		// Sistem.outln("CanvasTrip:"+realX+","+realY);
		if (state == GET_CHANGE) {
			return;
		}

		if (state == CHANGING) {
			if (waitToCreate == null) {
				toWait();
				return;
			}

			// ge.canvas.setPointer(waitToCreate.cursor);
			waitToCreate = waitToCreate.clone();
			// Sistem.outln("Changing "+waitToCreate.getName());
			waitToCreate.update(realX, realY);
			GraphicEditor.db.change(waitToCreate);
			GraphicEditor.drawer.scheduleRedraw();
			return;
		}
	}

	public void canvasOut(int realX, int realY) {
		// Sistem.outln("CanvasOut:"+realX+","+realY);
		if (GET_CHANGE == state) {
			return;
		}
		// ge.selHandler.hideSelectors();
	}

	public void change(Change change) {
		GraphicEditor.db.change(change);
		GraphicEditor.db.commit();
		GraphicEditor.drawer.scheduleRedraw();
	}

	// //////////////////////////////////////////////////////////
	// View control
	public void zoom() {
		// ge.canvas.resize();
		toWait();
		GraphicEditor.drawer.scheduleRedraw();
	}

	////////////////////////////////////////////////

	public void redraw() {
		GraphicEditor.drawer.scheduleRedraw();
	}

	public void unselect() {
		toWait();
	}

	private void toWait() {
		if (state == WAIT)
			return;
		setSelElem(null);
		GraphicEditor.change.hide();
		GraphicEditor.db.rollback();
		state = WAIT;
		GraphicEditor.drawer.scheduleRedraw();
	}

	private void toGet(Elem it, boolean already) {
		if (!already) {
			// mustSelect(it);
		}
		// ge.canvas.setPointer(null);

		state = GET_CHANGE;
		GraphicEditor.drawer.scheduleRedraw();
	}

	public void showGetInput() {
		if (selElem == null || state != GET_CHANGE) {
			return;
		}
		GraphicEditor.change.show(selElem);
	}

	public void toChanging(Change elem) {
		if (elem.cursor != null)
			ClientUtil.setCursor(elem.cursor);
		else {
			ClientUtil.setCursor(Cursor.MOVE);
		}
		GraphicEditor.db.rollback();
		GraphicEditor.change.hide();
		this.waitToCreate = elem;
		this.state = CHANGING;
	}

	/**
	 * 
	 * @param realX
	 * @param realY
	 */
	private void changingToGet(int realX, int realY) {
		if (waitToCreate == null) {
			toWait();
			return;
		}
		ClientUtil.setCursor(Cursor.DEFAULT);

		waitToCreate.update(realX, realY);
		GraphicEditor.db.change(waitToCreate);
		GraphicEditor.db.commit();

		if (waitToCreate.isCreator()) {
			toGet(dbRelated, false);
		} else {
			toWait();
		}
	}
	/////////////////////////////////////////////////

	public boolean tryDrag(int realX, int realY) {
		if (selElem == null) {
			return false;
		}

		double dist = selElem.distance(new Point(realX, realY));
		double radius = selElem.radius();
		
//		Sistem.outln(dist / radius);

		if (radius == 0) {
			toChanging(new DragDrop(this.selElem, 0, 0, selElem.getOrigin().getX(), selElem.getOrigin().getY(), realX,
					realY));
		} else {
			double dr= dist/radius;
			if( dr < 0.25f){
				toChanging(new Rotate(this.selElem, 0));
			} else if(dr < 0.75f){
				toChanging(new DragDrop(this.selElem, 0, 0, selElem.getOrigin().getX(), selElem.getOrigin().getY(), realX,
						realY));
			} else{
				toChanging(new MoveDp(this.selElem, 0, 0));
			}
			
		}

		//
		this.dragging = true;
		return true;
	}

	public void tryDrop(int realX, int realY) {
		this.dragging = false;
		if (selElem == null || state != CHANGING)
			return;
		changingToGet(realX, realY);
	}

	public void setSelElem(Elem el) {
		if (selElem == el)
			return;
		if (selElem != null) {
			this.selElem.setSelected(false);
		}

		if (el != null) {
			el.setSelected(true);
		}
		this.selElem = el;
	}

	// /////////////////////////////////////////////////////////////
	// Create handler, transform handler

	// public void createCancel() {
	// changeStateToWait();
	// }
	// public void cancelGet() {
	// ge.db.rollback();
	// redraw();

}
