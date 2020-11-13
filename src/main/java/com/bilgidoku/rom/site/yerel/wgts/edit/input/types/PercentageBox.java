package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

class PercentageBoxRegistry extends InputBoxRegistry {


	@Override
	public boolean iCanInput(Att att) {
		return att.getType() == 2;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new PercentageBox(att, value);
	}

}

public class PercentageBox extends InputBox {

	private final Widget textBox;

	protected PercentageBox(Att att, String value) {
		textBox = getBox(att, value);
		initWidget(textBox);
	}

	public static PercentageBoxRegistry getRegistry() {
		return new PercentageBoxRegistry();
	}

	private Widget getBox(final Att att, String value) {
		final ListBox lb = new ListBox();
		lb.setTitle(att.getNamed());
		lb.setStyleName("gwt-TextBox");
		lb.setWidth("50px");
		
		loadList(lb);
		selectValue(lb, value);
		
		lb.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				if (lb.getSelectedIndex() == 0) {
					fireEvent(new InputChanged(att.getNamed(), "0"));
					return;
				}
				fireEvent(new InputChanged(att.getNamed(), getDoubleValue(lb)));
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(lb);
		hp.add(new Label("%"));
		return hp;
	}

	private void selectValue(ListBox lb, String value) {
		if (value == null || value.isEmpty())
			return;
		
		int index = getPercentValue(value);
		lb.setSelectedIndex(index + 1);
	}

	private void loadList(ListBox tf) {
		tf.addItem("-----");
		for (int i = 0; i <= 100; i++) {
			tf.addItem(i + "");
		}
		
	}

	private int getPercentValue(String value) {
		Double dbl = Double.parseDouble(value);
		long iVal = Math.round(dbl * 100);
		return (int) iVal;

	}

	protected String getDoubleValue(ListBox lb) {
		String val = lb.getValue(lb.getSelectedIndex());
		int intVal = Integer.parseInt(val);
		Double dVal = intVal / (double) 100;
		return Double.toString(dVal);
	}

}
