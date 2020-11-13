package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ColorBox extends Composite implements HasChangeHandlers {
	private Images images = GWT.create(Images.class);
	private final ColorDlg colorPopup = new ColorDlg();
	private String activeColor = "#FFFFFF";
	private HTML box = new HTML(getBox(activeColor));

	public ColorBox() {
		box.setStyleName("site-box");

		box.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				colorPopup.show();
				colorPopup.setPopupPosition(box.getAbsoluteLeft(), event.getClientY() + 20);
			}
		});

		box.setWidth("42px");
		HorizontalPanel panel = new HorizontalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.add(box);
		this.initWidget(panel);
	}

	private String getBox(String hex) {
		if (hex.equals(""))
			hex = "#ffffff";

		String html = "<span style='background-color:" + hex
				+ "; width:20px; height:20px;float:left;margin-right:4px;'></span>"
				+ "<span style='width: 16px; display: block;  float: right; height: 100%; position:relative; '>"
				+ AbstractImagePrototype.create(images.arrow_down()).getHTML() + "</span>";
		return html;
	}

	public void setColor(String val1) {
		String val = val1.trim().replaceAll("\\n", "").replaceAll("\\t", "");
		activeColor = val;
		box.setHTML(getBox(val));
	}

	public String getValue() {
		return activeColor;
	}

	public int getIntValue() {
		if (activeColor.startsWith("#")) {
			String s = activeColor.replace("#", "");
			return hexToInt(s);
		} else if (activeColor.startsWith("rgb")) {
			try {

				// TODO later
				// String[] vals = activeColor.split(",");
				// String r = vals[0].replace("rgba(", "");
				// String g = vals[1];
				// String b = vals[2];
				// Color color = new
				// Color(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b));
				// String hex = Integer.toHexString(color.getRGB() & 0xffffff);
				// if (hex.length() < 6) {
				// hex = "0" + hex;
				// }
				// hex = "#" + hex;
				// Window.alert(hex);

				return hexToInt("000000");

			} catch (Exception e) {
			}
		}

		return 0;
	}

	private int hexToInt(String hex) {
		return Integer.valueOf(hex.replace("#", ""), 16).intValue();
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	private class ColorDlg extends ActionBarDlg {
		private final SiteColorPicker colors = new SiteColorPicker();

		public ColorDlg() {
			super("Renkler", null, null);
			run();
		}

		@Override
		public Widget ui() {
			colors.addHandler(new ColorPickedEventHandler() {
				public void colorPicked(ColorPickedEvent event) {
					if (event.getHexOrRgbaValue() == null) {
						colorPopup.hide();
						return;
					}

					if (event.getHexOrRgbaValue().equals("")) {
						setColor("");
						lastThing();
						return;
					}

					setColor(event.getHexOrRgbaValue());
					lastThing();

				}

				private void lastThing() {
					ColorBox.this.fireEvent(new PasteEvent());
					colorPopup.hide();
				}
			}, ColorPickedEvent.TYPE);
			colors.init();
			colors.setStyleName("site-chatdlgin");

			return colors;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
		}

	}

}
