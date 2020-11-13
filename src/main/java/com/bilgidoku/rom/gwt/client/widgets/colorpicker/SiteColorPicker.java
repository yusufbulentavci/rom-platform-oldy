package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.gwt.client.widgets.RangeBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class SiteColorPicker extends FlexTable implements ColorSelectedHandler {
	private ColorPanel colorPallete;
	private ColorPanel colorBar;
	private SitePaletteDefinition colorPalleteDefinition;
	private HSVBarPaletteDefinition colorBarDefinition;
	private TextBox txtHex  = new DataBox();
//	private TextBox txtHue = new DataBox();
//	private TextBox txtSaturation = new DataBox();
	private TextBox txtValue = new DataBox();
	private TextBox txtRed = new DataBox();
	private final TextBox txtGreen = new DataBox();
	private final TextBox txtBlue = new DataBox();
	private final DataBox txtAlpha = new DataBox();
	private final RangeBox opacity = new RangeBox(0, 1, 0.1);
	
	private SimplePanel colorSwatch = new SimplePanel();

	public SiteColorPicker() {

	}

	public void init() {
		txtAlpha.setEnabled(false);
		setInitialValue("red");
		colorPalleteDefinition = new SitePaletteDefinition();
		colorBarDefinition = new HSVBarPaletteDefinition();
		ButtonStyleCommand barButtonStyle = new ButtonStyleCommand();
		barButtonStyle.setBorder("none");
		barButtonStyle.setHeight("5px");
		barButtonStyle.setMargin("0");
		barButtonStyle.setPadding("0");
		barButtonStyle.setWidth("20px");

		ButtonStyleCommand palletteButtonStyle = new ButtonStyleCommand();
		palletteButtonStyle.setBorder("none");
		palletteButtonStyle.setHeight("24px");
		palletteButtonStyle.setMargin("0");
		palletteButtonStyle.setPadding("0");
		palletteButtonStyle.setWidth("24px");

		colorPallete = new ColorPanel(colorPalleteDefinition, palletteButtonStyle);
		colorPallete.addColorSelectedHandler(this);

		colorBar = new ColorPanel(colorBarDefinition, barButtonStyle);
		colorBar.addColorSelectedHandler(this);
		colorBar.setHeight("50px");

		
		Button pick = new Button("OK");
		pick.setStyleName("site-btn");
//		Button cancel = new Button("Close");
		
		opacity.setValue(1);
		opacity.addHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				String newValue = event.newValue;
				txtAlpha.setValue(newValue);
			}
		}, InputChanged.TYPE);

		
//		cancel.addStyleName("site-closebutton");
//		cancel.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				SiteColorPicker.this.fireEvent(new ColorPickedEvent(null));
//			}
//		});		
//		
		pick.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				SiteColorPicker.this.fireEvent(new ColorPickedEvent(getReturnValue()));
			}
		});
		
		txtHex.setWidth("50px");
		
		HorizontalPanel f = new HorizontalPanel();
		f.setWidth("100%");
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		f.setSpacing(5);		
		f.add(new HTML("Saydamlık:"));
		f.add(opacity);
		f.add(pick);
//		f.add(cancel);

		
		this.addStyleName("site-innerform");
		this.setCellSpacing(2);
		this.setWidget(0, 0, colorPallete);
		this.setWidget(0, 1, colorBar);
		this.setWidget(0, 2, getShowDataPanel());
		this.setWidget(1, 0, f);
		this.getFlexCellFormatter().setColSpan(1, 0, 3);
		this.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		this.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		this.getFlexCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);

	}

	private void setInitialValue(String string) {
		txtAlpha.setValue("1");
		opacity.setValue(1);		
		txtRed.setText("255");
		txtGreen.setText("16");
		txtBlue.setText("16");
		txtValue.setText("#FF1010");
		txtHex.setValue("#FF1010");

	}

	public String getReturnValue() {
		if (txtAlpha.getValue().equals("1")) //no alpha selected 
			return txtHex.getValue();	
		
		return getRGBA();	
		
	}

	private String getRGBA() {
		return "rgba(" + txtRed.getValue() + "," + txtGreen.getValue() + "," + txtBlue.getValue() + "," + txtAlpha.getValue() + ")";
	}

	private FlexTable getShowDataPanel() {
		FlexTable dp = new FlexTable();
		dp.setWidget(0, 0, colorSwatch);
		dp.setHTML(1, 0, "R");
		dp.setWidget(1, 1, txtRed);
		dp.setHTML(2, 0, "G");
		dp.setWidget(2, 1, txtGreen);
		dp.setHTML(3, 0, "B");
		dp.setWidget(3, 1, txtBlue);
		dp.setHTML(4, 0, "A");
		dp.setWidget(4, 1, txtAlpha);
		dp.setWidget(5, 0, txtHex);
		dp.getFlexCellFormatter().setColSpan(0, 0, 2);
		dp.getFlexCellFormatter().setColSpan(5, 0, 2);

		return dp;
	}

	public void onColorSelected(ColorSelectedEvent e) {
		if (e.getSource() == colorPallete) {
			
			txtAlpha.setValue("1");
			opacity.setValue(1);
			
			txtRed.setText(Integer.toString(e.getRed()));
			txtGreen.setText(Integer.toString(e.getGreen()));
			txtBlue.setText(Integer.toString(e.getBlue()));

			double[] HSV = ColorUtils.RGBtoHSV(e.getRed(), e.getGreen(), e.getBlue());

//			txtHue.setText(Integer.toString((int) (HSV[0] * 255)));
//			txtSaturation.setText(Integer.toString((int) (HSV[1] * 255)));
			txtValue.setText(Integer.toString((int) (HSV[2] * 255)));
			txtHex.setValue("#" + e.getHexValue());
			
			DOM.setStyleAttribute(colorSwatch.getElement(), "backgroundColor", "#" + e.getHexValue());

		} else if (e.getSource() == colorBar) {
			
			txtAlpha.setValue("1");
			opacity.setValue(1);
			double newHue = 1.0 - (e.getPosition() / (double) e.getMaxPositionY());
			colorPalleteDefinition.setStaticHue(newHue);
			colorPallete.redrawPalette();

			txtRed.setText(Integer.toString(e.getRed()));
			txtGreen.setText(Integer.toString(e.getGreen()));
			txtBlue.setText(Integer.toString(e.getBlue()));

			double[] HSV = ColorUtils.RGBtoHSV(e.getRed(), e.getGreen(), e.getBlue());

//			txtHue.setText(Integer.toString((int) (HSV[0] * 255)));
//			txtSaturation.setText(Integer.toString((int) (HSV[1] * 255)));
			txtValue.setText(Integer.toString((int) (HSV[2] * 255)));
			txtHex.setValue("#" + e.getHexValue());
			DOM.setStyleAttribute(colorSwatch.getElement(), "backgroundColor", "#" + e.getHexValue());

			
			
		}
	}

}