package com.bilgidoku.rom.gwt.client.util.common;

public class TimesButton extends TopButton {

	int times = 0;
	private String img;

	public TimesButton(String img) {
		super(img, "", "", null);
		this.img = img;
	}

	public void setTimes(int times) {
		if (times == 0) {
			this.setVisible(false);
			return;
		} else {
			changeHTML(img, times + "");
		}
		this.setVisible(true);
	}

	public void reset() {
		times = 0;
		this.setVisible(false);
	}

}
