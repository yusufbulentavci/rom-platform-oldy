package com.bilgidoku.rom.gwt.client.widgets.colorpicker;


public class SitePaletteDefinition implements ColorPaletteDefinition {
	private final int KEY_HUE = 1;
	private final int KEY_SAT = 2;
	private final int KEY_VAL = 3;
	private int keyOn = KEY_HUE;
	private double staticHue = 1.0;
	private double staticSaturation;
	private double staticValue;

	public int getHeight() {
		return 8;
	}

	public int getWidth() {
		return 8;
	}

	public String[] getHexColorValues() {
		return null;
	}

	public RGB[] getRGBVlues() {
		RGB[] tmp = new RGB[256];

		int c = 0;
		for (double y = 0; y <= 255; y += 17) {
			for (double x = 0; x <= 255; x += 17) {
				int[] rgb = null;
				switch (keyOn) {
				case KEY_HUE:
					rgb = ColorUtils.HSVtoRGB(staticHue, x / 255, 1.0 - y / 255);
					break;
				case KEY_SAT:
					rgb = ColorUtils.HSVtoRGB(x / 255, staticSaturation, 1.0 - y / 255);
					break;
				case KEY_VAL:
					rgb = ColorUtils.HSVtoRGB(x / 255, 1.0 - y / 255, staticValue);
					break;
				}

				tmp[c] = new RGB(rgb[0], rgb[1], rgb[2]);
				c++;
			}
		}

		RGB[] result = new RGB[64];
		int j = 0;
		for (int i = 0; i < tmp.length; i++) {
			if (i % 2 == 0) {
				result[j] = tmp[i];
				j++;
			}
			if ((i + 1)%16 == 0)
				i = i + 16;
			if (i==255) 
				result[63] = new RGB(0, 0, 0);
			
		}
		return result;

	}

	public void setStaticHue(double staticHue) {
		keyOn = KEY_HUE;
		this.staticHue = staticHue;
	}

	public void setStaticSaturation(double staticSaturation) {
		keyOn = KEY_SAT;
		this.staticSaturation = staticSaturation;
	}

	public void setStaticValue(double staticValue) {
		keyOn = KEY_VAL;
		this.staticValue = staticValue;
	}

	public int getDefaultSelected() {
		return 7;
	}

}