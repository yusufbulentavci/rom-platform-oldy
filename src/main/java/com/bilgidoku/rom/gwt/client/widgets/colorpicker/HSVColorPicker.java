package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HSVColorPicker extends Composite implements ColorSelectedHandler {
	private FlexTable container;
	private ColorPanel colorPallete;
	private ColorPanel colorBar;
	private HSVPaletteDefinition colorPalleteGen = new HSVPaletteDefinition();
	private HSVBarPaletteDefinition colorBarGen = new HSVBarPaletteDefinition();
	private TextBox txtHex;
	private TextBox txtHue;
	private TextBox txtSaturation;
	private TextBox txtValue;
	private TextBox txtRed;
	private TextBox txtGreen;
	private TextBox txtBlue;
	private FlowPanel colorSwatch = new FlowPanel();

	public HSVColorPicker() {
		ButtonStyleCommand barButtonStyle = new ButtonStyleCommand();
		barButtonStyle.setBorder("none");
		barButtonStyle.setHeight("4px");
		barButtonStyle.setMargin("0");
		barButtonStyle.setPadding("0");
		barButtonStyle.setWidth("20px");

		colorPallete = new ColorPanel(colorPalleteGen);
		colorPallete.addColorSelectedHandler(this);

		colorBar = new ColorPanel(colorBarGen, barButtonStyle);
		colorBar.addColorSelectedHandler(this);
		colorBar.setHeight("50px");

		colorSwatch.setWidth("58px");
		colorSwatch.setHeight("40px");
		DOM.setStyleAttribute(colorSwatch.getElement(), "border", "1px solid black");
		DOM.setStyleAttribute(colorSwatch.getElement(), "marginBottom", "4px");

		txtHex = new DataBox();
		txtHue = new DataBox();
		txtSaturation = new DataBox();
		txtValue = new DataBox();
		txtRed = new DataBox();
		txtGreen = new DataBox();
		txtBlue = new DataBox();

		VerticalPanel dataPanel = new VerticalPanel();
		dataPanel.add(colorSwatch);
		{
			HorizontalPanel f = new HorizontalPanel();
			f.add(new HTML("<span style='font-family:courier'>H&nbsp;</span>"));
			f.add(txtHue);
			dataPanel.add(f);
		}

		{
			HorizontalPanel f = new HorizontalPanel();
			f.add(new HTML("<span style='font-family:courier'>S&nbsp;</span>"));
			f.add(txtSaturation);
			dataPanel.add(f);
		}

		{
			HorizontalPanel f = new HorizontalPanel();
			f.add(new HTML("<span style='font-family:courier'>V&nbsp;</span>"));
			f.add(txtValue);
			dataPanel.add(f);
		}

		dataPanel.add(new HTML("<hr />"));

		{
			HorizontalPanel f = new HorizontalPanel();
			f.add(new HTML("<span style='font-family:courier'>R&nbsp;</span>"));
			f.add(txtRed);
			dataPanel.add(f);
		}

		{
			HorizontalPanel f = new HorizontalPanel();
			f.add(new HTML("<span style='font-family:courier'>G&nbsp;</span>"));
			f.add(txtGreen);
			dataPanel.add(f);
		}

		{
			HorizontalPanel f = new HorizontalPanel();
			f.add(new HTML("<span style='font-family:courier'>B&nbsp;</span>"));
			f.add(txtBlue);
			dataPanel.add(f);
		}

		Button pick = new Button("OK");
		Button cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopupPanel pp = (PopupPanel) HSVColorPicker.this.getParent();
				pp.hide();
			}
		});
		pick.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				HSVColorPicker.this.fireEvent(new ColorPickedEvent(txtHex.getValue().replace("#", "")));
				PopupPanel pp = (PopupPanel) HSVColorPicker.this.getParent();
				pp.hide();				
			}
		});
		pick.setHeight("22px");
		cancel.setHeight("22px");
		txtHex.setWidth("50px");
		HorizontalPanel f = new HorizontalPanel();
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		f.setSpacing(5);
		f.add(new HTML("<span style='font-family:courier'>Hex</span>"));
		f.add(txtHex);
		f.add(pick);
		f.add(cancel);

		container = new FlexTable();
		container.addStyleName("site-innerform");
		container.setCellSpacing(5);
		container.setWidget(0, 0, colorPallete);
		container.setWidget(0, 1, colorBar);
		container.setWidget(0, 2, dataPanel);
		container.setWidget(1, 0, f);
		container.getFlexCellFormatter().setColSpan(1, 0, 2);
		container.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		container.getFlexCellFormatter().setRowSpan(0, 2, 2);

		initWidget(container);

	}

	public void onColorSelected(ColorSelectedEvent e) {
		if (e.getSource() == colorPallete) {
			txtRed.setText(Integer.toString(e.getRed()));
			txtGreen.setText(Integer.toString(e.getGreen()));
			txtBlue.setText(Integer.toString(e.getBlue()));

			double[] HSV = ColorUtils.RGBtoHSV(e.getRed(), e.getGreen(), e.getBlue());

			txtHue.setText(Integer.toString((int) (HSV[0] * 255)));
			txtSaturation.setText(Integer.toString((int) (HSV[1] * 255)));
			txtValue.setText(Integer.toString((int) (HSV[2] * 255)));

			colorSwatch.getElement().getStyle().setBackgroundColor("#" + e.getHexValue());
			txtHex.setValue("#" + e.getHexValue());

		} else if (e.getSource() == colorBar) {
			double newHue = 1.0 - (e.getPosition() / (double) e.getMaxPositionY());
			colorPalleteGen.setStaticHue(newHue);
			colorPallete.redrawPalette();
		}
	}

}