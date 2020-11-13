package com.bilgidoku.rom.gwt.client.widgets;


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class RangeSelector extends Composite {

	private final static float pxCount = 250f; 
	private final TouchSplitLayoutPanel selector = new TouchSplitLayoutPanel(40) {
		public void onResize() {
			updateBoxes();
		};
	};
	private final SimplePanel leftSelect = new SimplePanel();
	private final SimplePanel rangeline = new SimplePanel();
	private final TextBox val1 = new TextBox();
	private final Label lblUnit = new Label();
	private int max;
	private final String unitLabel;

	public String viewValue;
	public Integer dbValue = 0;

	public RangeSelector(final int max, final String unitLabel, final int start) {
		
		this.max = max;
		this.unitLabel = unitLabel;


		val1.setStyleName("rangebox");

		val1.setValue(start + "");
		viewValue = start + " " + unitLabel;
		dbValue = start;

		lblUnit.setText(" " + unitLabel);

		forVal1Change();

		buildSelector();

		FlowPanel hp = new FlowPanel();
		hp.setStyleName("rangeinfo");
		hp.add(val1);
		hp.add(lblUnit);

		FlowPanel fp = new FlowPanel();
		fp.setStyleName("rangeholder");
		fp.getElement().getStyle().setPosition(Position.RELATIVE);
		fp.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		fp.getElement().getStyle().setMarginBottom(10, Unit.PX);
		fp.add(rangeline);
		fp.add(selector);
		fp.add(hp);

		initWidget(fp);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				if (start > 0)
					updateDragger();

			}
		});

	}

	protected void updateBoxes() {	

		float unit = max / pxCount;
		int iRealVal = Math.round(unit * leftSelect.getOffsetWidth());

		if (unitLabel.equals("MB")) {
			if (iRealVal >= 1024) {
				int iRoundedTam = (iRealVal / 1024);
				double iRoundedValue = iRoundedTam;
				int kalan = iRealVal % 1024;
				if (kalan >= 512)
					iRoundedValue = iRoundedTam + .5;

				Double dbl = iRoundedValue * 1024;
				dbValue = dbl.intValue();

				val1.setValue(iRoundedValue + "");
				lblUnit.setText(" GB");

			} else {

				dbValue = (iRealVal / 100) * 100;
				val1.setValue(dbValue + "");
				lblUnit.setText(" MB");
			}

		} else if (unitLabel.equals("SMS")) {
			int divTo = 100;
			if (iRealVal > 2000) {
				divTo = 500;
			}

			int iRoundedValue = (iRealVal / divTo) * divTo;
			val1.setValue(iRoundedValue + "");
			dbValue = iRoundedValue;

		} else {
			dbValue = (iRealVal / 10) * 10;
			val1.setValue(dbValue + "");
		}

		viewValue = val1.getValue() + " " + lblUnit.getText();

	}

	private void forVal1Change() {

		val1.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				lblUnit.setText(" " + unitLabel);

			}
		});

		val1.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				updateDragger();
			}
		});

	}

	protected void updateDragger() {
		int i = 0;
		try {
			String s = val1.getValue();
			i = Integer.parseInt(s);
			if (i > max) {
				Window.alert("En fazla " + max + " girilebilir.");
				i = max;
				val1.setValue(i + "");

			}
		} catch (Exception e) {
			Window.alert("Lütfen geçerli bir değer giriniz");
			i = 0;
			val1.setValue("0");
		}

		float unit = max / pxCount;
		float width = i / unit;
		viewValue = i + " " + unitLabel;

		selector.setWidgetSize(leftSelect, Math.round(width));

	}

	private void buildSelector() {

		rangeline.setSize("270px", "16px");
		rangeline.setStyleName("rangeline");

		selector.setSize("290px", "50px");

		leftSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		leftSelect.getElement().getStyle().setOpacity(0);

		selector.getElement().getStyle().setPosition(Position.ABSOLUTE);
		selector.getElement().getStyle().setTop(20, Unit.PX);
		selector.getElement().getStyle().setLeft(0, Unit.PX);
		selector.getElement().getStyle().setZIndex(5);

		selector.addWest(leftSelect, 0);

	}

}
