package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.dragdrop.DroppableTextBox;
import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ColorTextBox extends Composite implements HasName, HasChangeHandlers {

	private Images images = GWT.create(Images.class);
	private DroppableTextBox textBox = new DroppableTextBox();
	private Hidden hidden = new Hidden();
	private Image btnOpenPopup = new Image(images.color());
	private SiteColorPicker colors = new SiteColorPicker();
	private final DlgForColors colorPopup = new DlgForColors();
	private String textboxWidth = "65px";
	private String textboxHeight = "20px";

	public ColorTextBox() {
		colors.addHandler(new ColorPickedEventHandler() {
			public void colorPicked(ColorPickedEvent event) {
				if (event.getHexOrRgbaValue() != null) {
					setHexValue(event.getHexOrRgbaValue());
					fireEvent(new PasteEvent());
				}
				colorPopup.hide();
			}
		}, ColorPickedEvent.TYPE);

//		colorPopup.addStyleName("site-dlg site-layer2");
//		colorPopup.setHTML(getDlgCaption(null, "Colors"));
//		colorPopup.setWidget(colors);
//		colorPopup.setSize("250px", "200px");

		btnOpenPopup.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		btnOpenPopup.getElement().getStyle().setBorderColor("#999999");
		btnOpenPopup.getElement().getStyle().setBorderWidth(1, Unit.PX);

		// forClose();
		forTextChange();
		forClick();
		
		textBox.setSize(textboxWidth, textboxHeight);
		
		HorizontalPanel panel = new HorizontalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.add(textBox);
		panel.add(btnOpenPopup);
		panel.add(hidden);
		this.initWidget(panel);
	}

	public void setBoxHeight(String value) {
		this.textboxHeight = value;
	}

	/* a hex value must be like #FFFFFFF */
	public ColorTextBox(String hexValue) {
		this();
		if (checkValidFormat(hexValue))
			this.setHexValue(hexValue);
		else
			this.setMixValue(hexValue);
	}

	private boolean checkValidFormat(String value) {
		// TODO check first char
		if (value.length() == 7 && value.startsWith("#")
				&& (value.replace("#", "").compareTo("000000") >= 0 && value.replace("#", "").compareTo("FFFFFF") <= 0))
			return true;
		else
			return false;
	}

	public void setTextBoxWidth(String width) {
		this.textboxWidth = width;
	}

	private void forClick() {
		btnOpenPopup.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Widget source = (Widget) event.getSource();
				// int left = source.getAbsoluteLeft() + 10 - 250;
				// int top = source.getAbsoluteTop() + 10 - 230;
				//
				// if (left < 0)
				// left = 0;
				// if (top < 0)
				// top = 0;

				// simplePopup.setPopupPosition(left, top);
				colors.init();
				colorPopup.show();
				colorPopup.center();
			}
		});

	}

	private void forTextChange() {
		textBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setMixValue(textBox.getValue());
			}
		});

	}

	public void setMixValue(String mixValue) {
		String value = mixValue.trim().replaceAll("\\n", "").replaceAll("\\t", "");
		if (checkValidFormat(value)) {
			setHexValue(value);
		} else {
			this.hidden.setValue(value);
			this.textBox.setText(value);
			this.textBox.getElement().setAttribute("style", "background-color: #FFFFFF; color: #000000;width:"
					+ textboxWidth + ";height:" + textboxHeight + ";");
		}
	}

	public void setHexValue(String hexValue) {
		String hex = hexValue.trim().replaceAll("\\n", "").replaceAll("\\t", "");
		this.hidden.setValue(hex);
		this.textBox.setText(hex);
		this.textBox.getElement().setAttribute("style", "background-color: " + hex + "; color: " + getFontColor(hex)
				+ ";width:" + textboxWidth + ";height:" + textboxHeight + ";");
	}

	public String getValue() {
		return this.textBox.getValue();
	}

	private String getFontColor(String hex) {
		hex = hex.replace("#", "");
		if (hex.isEmpty())
			return "#FFFFFF";

		String r = hex.substring(0, 2);
		String g = hex.substring(2, 4);
		String b = hex.substring(4);

		int t = 0;

		if (r.compareTo("99") <= 0 && r.compareTo("00") >= 0)
			t = t + 1;

		if (g.compareTo("99") <= 0 && g.compareTo("00") >= 0)
			t = t + 1;

		if (b.compareTo("99") <= 0 && b.compareTo("00") >= 0)
			t = t + 1;

		if (t <= 2) {
			return "#000000"; // black
		} else {
			return "#FFFFFF"; // white
		}
	}

	@Override
	public void setName(String name) {
		this.hidden.setName(name);
	}

	@Override
	public String getName() {
		return this.hidden.getName();
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public int getIntValue() {
		String s = this.getValue().replace("#", "");
		return ClientUtil.hexToInt(s);
	}

	private class DlgForColors extends ActionBarDlg {

		public DlgForColors() {
			super("Colors", null, null);
			run();
		}

		@Override
		public Widget ui() {
			return colors;
		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ok() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
