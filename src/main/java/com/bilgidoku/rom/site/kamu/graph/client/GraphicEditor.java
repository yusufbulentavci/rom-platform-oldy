package com.bilgidoku.rom.site.kamu.graph.client;

import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.site.kamu.graph.client.constants.GraphConstants;
import com.bilgidoku.rom.site.kamu.graph.client.db.AreaFb;
import com.bilgidoku.rom.site.kamu.graph.client.db.Db;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasHolder;
import com.bilgidoku.rom.site.kamu.graph.client.draw.Drawer;
import com.bilgidoku.rom.site.kamu.graph.client.draw.Viewing;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeHandling;
import com.bilgidoku.rom.site.kamu.graph.client.ui.LayoutHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class GraphicEditor {
	
//	public static String A3 = "A3";
//	public static String CUP = "CUP";
	
	public final static GraphConstants trans = GWT.create(GraphConstants.class);
	public final static String clipArtDir = "/f/images/ca";

	public static EditState editState;
	public static Db db;
	public static Viewing viewing;
	public static CanvasHolder canvasHolder;

	public static Drawer drawer;
	public static ChangeHandling change;

	public static LayoutHandler layout;
	public static String userDir;

	final public GraphicFeedback feedback;
//	public static String type = A3;

	public GraphicEditor(final GraphicFeedback feedback, String backImage, Point backImageSize, Point editLocation,
			Point editSize, String userDir1) {		
		db = new Db(new AreaFb() {

			@Override
			public void areaChanged(final Point area, final int paperSize) {
				if (feedback != null)
					feedback.areaChanged(area, paperSize);

			}
		}, backImage, backImageSize, editLocation, editSize);
		this.feedback = feedback;
		editState = new EditState(feedback);
		cons();
		userDir = userDir1;
//		type = (editSize.getX() > 400 ? A3 : CUP);
	}

	public GraphicEditor(final GraphicFeedback feedback, JSONObject jo, String userDir1) throws RunException {
		db = new Db(new AreaFb() {

			@Override
			public void areaChanged(final Point area, final int paperSize) {
				if (feedback != null)
					feedback.areaChanged(area, paperSize);

			}
		}, jo);
		userDir = userDir1;
		this.feedback = feedback;
		cons();
	}

	private void cons() {
		viewing = new Viewing(db.getBackImageSize().getX(), db.getBackImageSize().getY(), db.getEditLocation().getX(),
				db.getEditLocation().getY(), db.getEditSize().getX(), db.getEditSize().getY(), 700, 800);

		change = new ChangeHandling(this);

		canvasHolder = new CanvasHolder(change.getButtons(), new Button[0]);

		drawer = new Drawer(db, canvasHolder.getCanvas(), viewing, this);

		layout = new LayoutHandler(change.getCreateButtons(), canvasHolder, change.getFormPanel(), 820);
	}

	public Widget getPanel() {
		return layout;
	}

	public EditState getEditState() {
		return editState;
	}

	public JSONObject toJson() {
		return db.toJson();
	}

	public void loadJson(JSONObject jo) throws RunException {
		db.loadFromJson(jo);
	}

	public void redraw() {
		drawer.scheduleRedraw();
	}

	public void reredraw() {
		drawer.scheduleReredraw();
	}

	public void setStatus(String text) {
		feedback.setStatus(text);

	}

}
