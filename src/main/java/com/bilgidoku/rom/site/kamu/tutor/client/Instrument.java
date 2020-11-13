package com.bilgidoku.rom.site.kamu.tutor.client;

import java.util.List;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.kamu.tutor.client.constants.tutortrans;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Instrument extends AbsolutePanel {
	// private static final int CANVAS_Z = 100;
	// private static final int BUTTON_Z = 110;

	private static final int WIDTH = 500;
	final TuneBook showTune;
	final Label showSummary;

	public static tutortrans trans = (tutortrans) GWT.create(tutortrans.class);

	final String name;
	final Img[] images;
	final Option[] options;
	final Button[] selects;
	final Image[] imgItems;
	final Canvas canvas = Canvas.createIfSupported();
	final Selected selected;
	private final Vector2d size;
	private LineStyle lineStyle;
	private PtrStyle ptrStyle;
	private Button dontKnow = new Button(trans.dontknow());
	private CellPanel buttonContainer;

	private boolean editMode = false;

	private boolean btnsVertical;
	private int btnsx=510,btnsy=0;

	private static final int TEXT_WIDTH = 100;

	public Instrument(final Selected selected, PtrStyle ptrStyle, LineStyle lineStyle, final String name,
			final Vector2d size, final Img[] images, final Option[] options, final Button[] select,
			String btnsParams, String codeParams, String summaryParams) {
		this.ptrStyle = ptrStyle;
		this.lineStyle = lineStyle;
		this.selected = selected;
		this.name = name;
		this.images = images;
		this.options = options;
		this.selects = new Button[select.length];
		this.size = size;
		
		this.btnsVertical = true;
		
		
		if(btnsParams!=null){
			String bs[]=btnsParams.split("-");
			btnsVertical=!bs[0].equals("h");
			if(bs.length>0){
				btnsx=Integer.parseInt(bs[1]);
				btnsy=Integer.parseInt(bs[2]);
			}else{
				if(!btnsVertical){
					btnsx=0;
				}
			}
		}
		
		
		
		setSize(size.x + TEXT_WIDTH + "px", size.y + "px");
		if (btnsVertical) {
			buttonContainer = new VerticalPanel();
		} else {
			buttonContainer = new HorizontalPanel();
		}
		
		buttonContainer.getElement().getStyle().setOpacity(0.7);

		this.imgItems = new Image[images.length];
		for (int i = 0; i < images.length; i++) {
			Img ci = images[i];
			Image img = new Image(ci.url);
			img.setVisible(false);
			add(img, ci.pos.x, ci.pos.y);
			imgItems[i] = img;
		}

		canvas.setWidth(size.x + TEXT_WIDTH + "px");
		canvas.setCoordinateSpaceWidth(size.x + TEXT_WIDTH);

		canvas.setHeight(size.y + "px");
		canvas.setCoordinateSpaceHeight(size.y);

		// canvas.getElement().getStyle().setZIndex(CANVAS_Z);

		add(canvas, 0, 0);

		dontKnow.getElement().getStyle().setWidth(100, Unit.PCT);
		buttonContainer.add(dontKnow);
		// dontKnow.getElement().getStyle().setZIndex(BUTTON_Z);
		dontKnow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				selected.dontKnow();
			}
		});

		for (short i = 0; i < select.length; i++) {
			final short x = i;
			Button b = new Button(select[i].getText());
			b.getElement().getStyle().setWidth(100, Unit.PCT);
			// b.getElement().getStyle().setZIndex(BUTTON_Z);
			selects[i] = b;
			b.setVisible(true);

			buttonContainer.add(b);
			b.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					selected.noteSelected(x);
				}
			});
		}

		add(buttonContainer, btnsx, btnsy);

		if (codeParams != null) {
			String[] prms = codeParams.split("-");
			showTune = new TuneBook("showtune", 50);
			// showTune.getElement().getStyle().setLeft(Integer.parseInt(),
			// Unit.PX);
			// showTune.getElement().getStyle().setTop(Integer.parseInt(prms[1]),
			// Unit.PX);
			showTune.setVisible(false);
			showTune.getElement().getStyle().setZIndex(5000);
			Sistem.outln("SHOWTUNE-" + Integer.parseInt(prms[0]));
			add(showTune, Integer.parseInt(prms[0]), Integer.parseInt(prms[1]));
		} else {
			showTune = null;
		}
		
		if (summaryParams != null) {
			String[] prms = summaryParams.split("-");
			showSummary = new Label();
			// showTune.getElement().getStyle().setLeft(Integer.parseInt(),
			// Unit.PX);
			// showTune.getElement().getStyle().setTop(Integer.parseInt(prms[1]),
			// Unit.PX);
			showSummary.setVisible(false);
			Style style = showSummary.getElement().getStyle();
			style.setFontSize(Double.parseDouble(prms[4]), Unit.EM);
			style.setColor(prms[5]);
			
			style.setBackgroundColor(prms[6]);
			style.setOpacity(Double.parseDouble(prms[7]));
			
			
			showSummary.getElement().getStyle().setZIndex(6000);
			add(showSummary, Integer.parseInt(prms[0]), Integer.parseInt(prms[1]));
			showSummary.setWidth(Integer.parseInt(prms[2])+"px");
			showSummary.setHeight(Integer.parseInt(prms[3])+"px");
			
			
		} else {
			showSummary = null;
		}


	}

	private void renderEdit() {
		hideImages();
		// hideButtons();
		for (int i = 0; i < options.length; i++) {

		}
	}

	public String getName() {
		return name;
	}

	public int getOptionCount() {
		return options.length;
	}

	public void render(int which) {
		hideImages();
		hideButtons();
		showOption(which);
	}

	private void showOption(int which) {
		Sistem.outln("Showing: " + which);
		Option o = options[which];
		imgItems[o.imgInd].setVisible(true);
		showOption(o);

		showResult(o);
		// selects[o.selectIndex].setEnabled(false);
	}

	private void showResult(Option o) {
		Button s = selects[o.selectIndex];
		s.getElement().getStyle().setColor("red");

		if (o.imgloc == null)
			return;
		Context2d context1 = canvas.getContext2d();
		context1.beginPath();
		context1.moveTo(o.imgloc.x, o.imgloc.y);

		if (btnsVertical) {
			context1.lineTo(btnsx, s.getAbsoluteTop()-canvas.getAbsoluteTop() + s.getOffsetHeight()/2);
		} else {
			context1.lineTo(s.getAbsoluteLeft() + s.getOffsetWidth() / 2 - canvas.getAbsoluteLeft(),
					s.getAbsoluteTop() + s.getOffsetHeight() - canvas.getAbsoluteTop());
		}

		context1.setStrokeStyle(cssColor(lineStyle.color));
		context1.setLineWidth(lineStyle.width);
		context1.stroke();
		context1.closePath();

		showOption(o);



	}

	private CssColor cssColor(Vector3d ls) {
		return CssColor.make(ls.x, ls.y, ls.z);
	}

	public void render(List<Integer> known) {
		// hideButtons();
		hideImages();
		for (Integer i : known) {
			showOption(i);
		}
	}

	public void ask(int which) {
		clean();
		Option o = options[which];

		showOption(o);

		imgItems[o.imgInd].setVisible(true);
		showAllButtons();

	}

	private void showOption(Option o) {

		
		if (showTune != null) {
			String note = o.note();
			if (note != null) {
				showTune.showNote(o.note());
				showTune.setVisible(true);
			}
		}

		if (showSummary != null && o.summary != null) {
			showSummary.setText(o.summary);
			showSummary.setVisible(true);
		}
		
		if (o.imgloc == null)
			return;

		Context2d context1 = canvas.getContext2d();
		context1.beginPath();
		context1.arc(o.imgloc.x, o.imgloc.y, ptrStyle.radius, 0, 2 * Math.PI, false);
		context1.setStrokeStyle(cssColor(ptrStyle.color));
		context1.setLineWidth(ptrStyle.width);
		context1.stroke();
		context1.closePath();
	}

	private void showAllButtons() {
		dontKnow.getElement().getStyle().setColor("black");
		dontKnow.setEnabled(true);
		for (Button b : this.selects) {
			b.setEnabled(true);
			b.getElement().getStyle().setColor("black");
		}
	}

	private void hideImages() {
		for (Image img : imgItems) {
			img.setVisible(false);
		}
	}

	private void hideButtons() {
		dontKnow.getElement().getStyle().setColor("gray");
		dontKnow.setEnabled(false);
		for (Button img : selects) {
			img.getElement().getStyle().setColor("gray");
			img.setEnabled(false);
		}
	}

	private void clearCanvas() {
		Context2d context1 = canvas.getContext2d();
		context1.clearRect(0, 0, size.x + TEXT_WIDTH, size.y);
	}

	public Option[] getOptions() {
		return options;
	}

	public void clean() {
		hideImages();
		hideButtons();
		clearCanvas();
	}

}
