package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorTextBox;
import com.bilgidoku.rom.gwt.client.widgets.fonts.FontFamilyBox;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BrowseFont extends ActionBarDlg {

	private FontFamilyBox listFontFamily;
	private ListBox listFontWeight = new ListBox();
	private ListBox listFontSize = new ListBox();
	private TextBox txtLineHeight = new TextBox();
	private ListBox listFontStyle = new ListBox();
	private ColorTextBox txtFontColor = new ColorTextBox();

	public JSONObject selectedObject = null;

	public BrowseFont(JSONObject objFont) {
		super(Ctrl.trans.font(), null, Ctrl.trans.ok());
		listFontFamily = new FontFamilyBox();
		
		txtLineHeight.setWidth("64px");

		ChangeHandler handleChange = new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				BrowseFont.this.fontChanged(null);
			}
		};
		listFontWeight.addChangeHandler(handleChange);
		listFontSize.addChangeHandler(handleChange);
		listFontStyle.addChangeHandler(handleChange);
		txtFontColor.addChangeHandler(handleChange);
		listFontFamily.addChangeHandler(handleChange);
		txtFontColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				fontChanged(null);
			}
		}, PasteEvent.TYPE);

		txtFontColor.setHexValue("#3F4041");
		loadFontStyles();
		loadFontWeights();
		loadFontSizes();

		loadData(objFont);		
		run();
//		this.show();
//		this.center();

	}

	protected void fontChanged(Object object) {
		
		
	}
	
	private void loadFontSizes() {

		for (int i = 8; i < 81; i++) {
			listFontSize.addItem(i + "px");
		}

		listFontSize.addItem("xx-small");
		listFontSize.addItem("smaller");
		listFontSize.addItem("small");
		listFontSize.addItem("medium");
		listFontSize.addItem("large");
		listFontSize.addItem("larger");
		listFontSize.addItem("xx-large");

		listFontSize.setSelectedIndex(6);

	}

	private void loadFontWeights() {
		listFontWeight.addItem("normal");
		listFontWeight.addItem("bold");
		listFontWeight.addItem("bolder");
		listFontWeight.addItem("lighter");
	}

	private void loadFontStyles() {
		listFontStyle.addItem("normal");
		listFontStyle.addItem("italic");
		listFontStyle.addItem("oblique");
	}

	public void loadData(JSONObject font) {
		if (font == null)
			return;

		String fontFamily = ClientUtil.getString(font.get("fontfamily"));
		String fontSize = ClientUtil.getString(font.get("fontsize"));
		String fontWeight = ClientUtil.getString(font.get("fontweight"));
		String fontStyle = ClientUtil.getString(font.get("fontstyle"));
		String fontColor = ClientUtil.getString(font.get("color"));
		String lineHeight = ClientUtil.getString(font.get("lineheight"));

		txtFontColor.setHexValue(fontColor);
		txtLineHeight.setValue(lineHeight);
		ClientUtil.findAndSelect(listFontStyle, fontStyle);
		ClientUtil.findAndSelect(listFontWeight, fontWeight);
		ClientUtil.findAndSelect(listFontSize, fontSize);

		listFontFamily.setFont(fontFamily);
		

	}

	public JSONObject getFont() {
		JSONObject jo = new JSONObject();
		jo.put("fontfamily", new JSONString(listFontFamily.getValue()));
		jo.put("fontsize", new JSONString(listFontSize.getValue(listFontSize.getSelectedIndex())));
		jo.put("fontweight", new JSONString(listFontWeight.getValue(listFontWeight.getSelectedIndex())));
		jo.put("fontstyle", new JSONString(listFontStyle.getValue(listFontStyle.getSelectedIndex())));
		jo.put("color", new JSONString(txtFontColor.getValue()));
		jo.put("lineheight", new JSONString(txtLineHeight.getValue()));
		return jo;
	}

	@Override
	public Widget ui() {
		FlexTable ft = new FlexTable();

		// ft.setWidget(0, 0, fontPreview);

		ft.setHTML(1, 0, Ctrl.trans.font());
		ft.setWidget(1, 1, listFontFamily);

		ft.setHTML(2, 0, Ctrl.trans.fontSize());
		ft.setWidget(2, 1, listFontSize);

		ft.setHTML(2, 2, Ctrl.trans.fontWeigth());
		ft.setWidget(2, 3, listFontWeight);

		ft.setHTML(3, 0, Ctrl.trans.fontStyle());
		ft.setWidget(3, 1, listFontStyle);

		ft.setHTML(3, 2, Ctrl.trans.color());
		ft.setWidget(3, 3, txtFontColor);

		ft.setHTML(4, 0, Ctrl.trans.lineHeight());
		ft.setWidget(4, 1, txtLineHeight);

		ft.setStyleName("site-padding");
		ft.getFlexCellFormatter().setColSpan(0, 0, 4);
		ft.getFlexCellFormatter().setColSpan(1, 1, 3);

		return ft;
	}

	@Override
	public void cancel() {
		selectedObject = null;
	}

	@Override
	public void ok() {
		selectedObject = getFont();
		
	}

}
