package com.bilgidoku.rom.site.kamu.graph.client.ui.forms;

import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MoreForm extends Composite {
	private ChangeCallback caller;
	// private final Images images = GWT.create(Images.class);
	private final Button rect = new Button("Rectangular");
	private final Button moveto = new Button("Line");

	// private final Button line = new
	// Button(ClientUtil.imageItemHTML(images.new_line(), "New line"));
	// private final Button bezier = new
	// Button(ClientUtil.imageItemHTML(images.new_bezier(), "New curve"));

	public MoreForm(final ChangeCallback caller) {

		this.caller = caller;

		forRect();
		forLine();

		VerticalPanel vp = new VerticalPanel();
		vp.add(rect);
		vp.add(moveto);

		initWidget(vp);

	}

	private void forLine() {
		moveto.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// caller.newLine();
			}
		});
	}

	private void forRect() {
		rect.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// caller.newRect();
			}
		});

	}

}
