package com.bilgidoku.rom.gwt.client.widgets;

import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class RangeBox extends Composite {

	private final HTML leftSelect = new HTML();
	private final SplitLayoutPanel sp = new SplitLayoutPanel(18) {
		public void onResize() {
			resized();
		};
	};
	private int rangeStart = 0;
	private int rangeEnd = 10;

	private long val = 0;
	private double eachStep;

	public RangeBox(int rs, int re, double step) {
		this.rangeStart = rs;
		this.rangeEnd = re;
		this.eachStep = step;

		double vw = ((rangeEnd - rangeStart) / step) * 10;
		long viewWidth = Math.round(vw);

		sp.setWidth(viewWidth + "px");
		sp.getElement().getStyle().setPadding(9, Unit.PX);
		sp.setStyleName("site-rangebox site-box");
		sp.addStyleName("rounded");
		sp.addWest(leftSelect, 0);
		leftSelect.getElement().getStyle().setProperty("padding", "3px 0 ");

		initWidget(sp);

	}

	protected void resized() {
		int sel = leftSelect.getOffsetWidth();
		// int step = (viewWidth / (rangeEnd - rangeStart));
		// step = 10;
		double d = ((double) sel) / 10;
		val = Math.round(d);

		double dval = ((double) val * eachStep);
		String sval = String.valueOf(dval);
		if (eachStep < 1) {
			NumberFormat df = NumberFormat.getFormat("##0.0");
			sval = df.format(dval).replace(",", ".");
		}
		leftSelect.setHTML("&nbsp;" + sval);

		if ((d - val) == 0) {
			Sistem.outln("val" + val);
			RangeBox.this.fireEvent(new InputChanged("range", sval));
		}
	}

	public void setValue(double val) {
		leftSelect.setHTML("&nbsp;" + val);

		double d = (val / eachStep) * 10;
		long i = Math.round(d);
		sp.remove(leftSelect);
		sp.addWest(leftSelect, i);
	}

	public HandlerRegistration addChangedHandler(InputChangedHandler handler) {
		return this.addHandler(handler, InputChanged.TYPE);
	}

}
