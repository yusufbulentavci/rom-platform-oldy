package com.bilgidoku.rom.site.kamu.graph.client.ui.forms;

import com.bilgidoku.rom.site.kamu.graph.client.change.Change;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ImageSelector;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorTextBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RectForm extends Composite {

	private final ImageSelector<Integer> strokeWidth = new ImageSelector<Integer>(1);
	private final ColorTextBox fillColor = new ColorTextBox();
	private final ColorTextBox strokeColor = new ColorTextBox();
	private final Button line = new Button("New line");
	private final Button bezier = new Button("New curve");

	private final Change change;

	public RectForm(final ChangeCallback caller, Elem cur, Change change2) {
		this.change = change2;

		fillColor.setMixValue("rgba(0,0,0,0)");
		strokeColor.setMixValue("#994747");

		loadValues(cur);

		FlexTable p = new FlexTable();
		p.setWidget(0, 0, line);
		p.setWidget(0, 1, bezier);
		p.setWidget(1, 0, new Label("Fill"));
		p.setWidget(1, 1, fillColor);
		p.setWidget(1, 2, new Label("Line"));
		p.setWidget(1, 3, strokeColor);
		p.setWidget(1, 4, new Label("Line width"));
		p.setWidget(1, 5, strokeWidth);

		Button ok = new Button("Change Text");
		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				change.setStrokeWidth(strokeWidth.getValue());
				change.setFillColor(fillColor.getValue());
				caller.textChanged(change);
			}
		});

		HTML header = new HTML("Rectangular");
		header.setWidth("300px");

		VerticalPanel vp = new VerticalPanel();
		vp.setBorderWidth(1);
		vp.add(header);
		vp.add(p);
		vp.add(ok);
		initWidget(vp);

	}

	protected void loadValues(Elem cur) {
		if (cur.getFillColor() != null) {
			fillColor.setMixValue(cur.getFillColor());
		}

		if (cur.getStrokeColor() != null) {
			this.strokeColor.setMixValue(cur.getStrokeColor());
		}

		if (cur.getStrokeWidth() != null) {
			this.strokeWidth.setValue(cur.getStrokeWidth());
		}
	}

}
