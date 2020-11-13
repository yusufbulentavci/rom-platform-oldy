package com.bilgidoku.rom.site.yerel.common.widgets.colorpicker.colorpicker;

import com.bilgidoku.rom.gwt.client.util.common.DialogBase;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgColorPicker extends DialogBase {
	private SaturationLightnessPicker slPicker;
	private HuePicker huePicker;
	public String color;
	
	public DlgColorPicker() {
		super(Ctrl.trans.select(Ctrl.trans.color()), Ctrl.trans.ok());
		run();
	}

	@Override
	public Widget ui() {
		
		HorizontalPanel panel = new HorizontalPanel();
		panel.setHeight("180px");
		// the pickers
		slPicker = new SaturationLightnessPicker();
		panel.add(slPicker);
		huePicker = new HuePicker();
		panel.add(huePicker);

		// bind saturation/lightness picker and hue picker together
		huePicker.addHueChangedHandler(new IHueChangedHandler() {
			public void hueChanged(HueChangedEvent event) {
				slPicker.setHue(event.getHue());
			}
		});

		return panel;
	}

	public void setColor(String color) {
		int[] rgb = ColorUtils.getRGB(color);
		int[] hsl = ColorUtils.rgb2hsl(rgb);
		huePicker.setHue(hsl[0]);
		slPicker.setColor(color);
	}
	
	public String getColor() {
		return color;
	}

	@Override
	public void ok() {
		color = slPicker.getColor();
	}

	@Override
	public void cancel() {
	}
}
