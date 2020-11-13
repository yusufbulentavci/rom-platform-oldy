package com.bilgidoku.rom.site.kamu.graph.client.ui.forms;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.change.Change;
import com.bilgidoku.rom.site.kamu.graph.client.change.ChangeProperty;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.gwt.client.widgets.RangeBox;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorBox;
import com.bilgidoku.rom.gwt.client.widgets.fonts.FontFamilyBox;
import com.bilgidoku.rom.min.geo.Point;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class TextEditor extends ActionBarDlg {

	private final ColorBox elemFillColor = new ColorBox();
	private final ColorBox elemStrokeColor = new ColorBox();
	private final RangeBox elemStrokeWidth = new RangeBox(0, 8, 1);

	private final FontFamilyBox elemFonts = new FontFamilyBox();
	private final TextArea elemText = new TextArea();

	private final ColorBox textStrokeColor = new ColorBox();
	private final RangeBox textStrokeWidth = new RangeBox(0, 8, 1);
	private final ColorBox textFillColor = new ColorBox();


	private ChangeCallback caller;

	private Elem cur;

	public TextEditor(final ChangeCallback caller, Elem cur) {
		super("Text Editor", null, null);
		
		this.cur = cur;
		this.caller = caller;

		run();
		this.show();
		//TODO check after scaling
		Point p = GraphicEditor.viewing.getTextPosition(cur);
		int x = GraphicEditor.canvasHolder.getCanvas().getAbsoluteLeft() + p.getX();
		int y = GraphicEditor.canvasHolder.getCanvas().getAbsoluteTop() + p.getY();
		this.setPopupPosition(x, y);

	}

	private void forChangeTextFillColor() {
		textFillColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				Change change = new ChangeProperty(cur);
				change.setTextFillColor(textFillColor.getValue());
				caller.textChanged(change);

			}
		}, PasteEvent.TYPE);

	}

	private void forChangeTextStrokeColor() {
		textStrokeColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {

				Change change = new ChangeProperty(cur);
				change.setTextStrokeColor(textStrokeColor.getValue());
				caller.textChanged(change);

			}
		}, PasteEvent.TYPE);
	}

	private void forChangeTextStrokeWidth() {
		textStrokeWidth.addHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				String newValue = event.newValue;
				int i = Integer.parseInt(newValue);
				Change change = new ChangeProperty(cur);
				change.setTextStrokeWidth(i);
				caller.textChanged(change);
			}
		}, InputChanged.TYPE);

	}

	private void forChangeFont() {
		elemFonts.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Change change = new ChangeProperty(cur);
				change.setFont(elemFonts.getValue());
				caller.textChanged(change);
			}
		});
	}

	private void forChangeStrokeWidth() {
		elemStrokeWidth.addHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				String newValue = event.newValue;
				int i = Integer.parseInt(newValue);
				Change change = new ChangeProperty(cur);
				change.setStrokeWidth(i);
				caller.textChanged(change);				
			}
		}, InputChanged.TYPE);

	}

	private void forChangeStrokeColor() {
		elemStrokeColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				Change change = new ChangeProperty(cur);
				change.setStrokeColor(elemStrokeColor.getValue());
				caller.textChanged(change);

			}
		}, PasteEvent.TYPE);
	}

	private void forChangeFillColor() {
		elemFillColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {

				Change change = new ChangeProperty(cur);
				change.setFillColor(elemFillColor.getValue());
				caller.textChanged(change);

			}
		}, PasteEvent.TYPE);
	}

	private void forChangeText() {
		elemText.addKeyUpHandler(new KeyUpHandler() {			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				elemFonts.setText(elemText.getValue());				
				Change change = new ChangeProperty(cur);
				change.setText(elemText.getValue());
				caller.textChanged(change);				
				GraphicEditor.editState.showGetInput();
			}
		});
	}

	private void loadValues(Elem cur) {
		this.elemText.setFocus(true);
		if (cur.getTextStrokeWidth() != null) {
			this.textStrokeWidth.setValue(cur.getTextStrokeWidth());
		}
		if (cur.getTextFillColor() != null) {
			this.textFillColor.setColor(cur.getTextFillColor());
		}
		if (cur.getTextStrokeColor() != null) {
			this.textStrokeColor.setColor(cur.getTextStrokeColor());
		}

		if (cur.getFillColor() != null) {
			this.elemFillColor.setColor(cur.getFillColor());
		}
		if (cur.getStrokeColor() != null) {
			this.elemStrokeColor.setColor(cur.getStrokeColor());
		}
		if (cur.getStrokeWidth() != null) {
			this.elemStrokeWidth.setValue(cur.getStrokeWidth());
		}
		if (cur.getText() != null) {
			this.elemText.setText(cur.getText());
			this.elemFonts.setText(cur.getText());
		}
		if (cur.getFont() != null) {
			elemFonts.setFont(cur.getFont());
		}

	}

	@Override
	public Widget ui() {
		elemText.setStyleName("site-box");
		elemText.setSize("180px", "36px");
		elemFonts.setWidth("180px");

		forChangeText();
		forChangeFont();
		forChangeFillColor();
		forChangeStrokeColor();
		forChangeStrokeWidth();
		forChangeTextStrokeWidth();
		forChangeTextStrokeColor();
		forChangeTextFillColor();

		loadValues(cur);

		FlexTable p = new FlexTable();

		p.setStyleName("site-padding");
		
		p.setWidget(0, 0, new Label(GraphicEditor.trans.text()));
		p.setWidget(0, 1, new Label(GraphicEditor.trans.textColor()));
		p.setWidget(0, 2, new Label(GraphicEditor.trans.lineColor()));
		p.setWidget(0, 3, new Label(GraphicEditor.trans.lineWidth()));
		
		
		p.setWidget(1, 0, elemText);
		p.setWidget(1, 1, textFillColor);
		p.setWidget(1, 2, textStrokeColor);		
		p.setWidget(1, 3, textStrokeWidth);

		p.setWidget(2, 0, new Label(GraphicEditor.trans.font()));
		p.setWidget(2, 1, new Label(GraphicEditor.trans.boxColor()));
		p.setWidget(2, 2, new Label(GraphicEditor.trans.boxlineColor()));
		p.setWidget(2, 3, new Label(GraphicEditor.trans.boxLineSize()));

		p.setWidget(3, 0, elemFonts);
		p.setWidget(3, 1, elemFillColor);
		p.setWidget(3, 2, elemStrokeColor);
		p.setWidget(3, 3, elemStrokeWidth);


		return p;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
		// TODO Auto-generated method stub
		
	}

}
