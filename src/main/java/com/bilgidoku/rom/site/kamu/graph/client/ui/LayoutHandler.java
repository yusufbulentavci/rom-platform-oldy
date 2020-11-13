package com.bilgidoku.rom.site.kamu.graph.client.ui;

import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasHolder;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;

public class LayoutHandler extends FlowPanel {

	public LayoutHandler(Button[] createButtons, CanvasHolder canvasHolder,
			SimplePanel form, int widgetSize) {

		form.setWidth(widgetSize + "px");
		form.setVisible(false);
		
		HorizontalPanel top = new HorizontalPanel();
//		top.add(zoom);

		for (Button b : createButtons) {
			b.setSize(widgetSize / createButtons.length + "px", "50px");
			top.add(b);
		}
		
		top.getElement().getStyle().setMarginBottom(11, Unit.PX);
		canvasHolder.getElement().getStyle().setFloat(Float.LEFT);

		add(top);
		add(form);
		add(canvasHolder);

	}

//	private Widget getCenteredCanvas(CanvasHolder canvasHolder) {
//		HorizontalPanel sp = new HorizontalPanel();
//		sp.getElement().getStyle().setFloat(Float.LEFT);
//		sp.getElement().getStyle().setPosition(Position.RELATIVE);
//		sp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		sp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		sp.add(canvasHolder);
//		sp.setSize("800px", "700px");
//		return sp;
//	}

}
