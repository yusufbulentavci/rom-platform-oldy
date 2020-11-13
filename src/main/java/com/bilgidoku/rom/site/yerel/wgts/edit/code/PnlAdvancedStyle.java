package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorTextBox;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.tags.HtmlTagMap;
import com.bilgidoku.rom.site.yerel.wgts.edit.FlxGrid;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PnlAdvancedStyle extends Composite {
	private final MultiWordSuggestOracle oracleStyle = new MultiWordSuggestOracle();
	private final SuggestBox styleSuggest = new SuggestBox(oracleStyle);
	private final ColorTextBox styleText = new ColorTextBox();
	private final Button btnAddStyle = new Button("Add");
	private NodePnlStyle parent;
	private ListBox styleTypeList = new ListBox();
	private Widget advancedForm;
	private FlxGrid addedStyles;
	private VerticalPanel vp = new VerticalPanel();

	public PnlAdvancedStyle(NodePnlStyle stylePanel) {
		this.parent = stylePanel;

		loadOracle();
		loadStylesTypes();

		forAdd();
		forFocus();

		addedStyles = new FlxGrid(parent, true);
		advancedForm = getAdvancedForm();

		vp.add(advancedForm);
		vp.add(addedStyles);

		initWidget(vp);

	}

	private void loadOracle() {
		for (String key : HtmlTagMap.one().getStyleMap().keySet()) {
			oracleStyle.add(key);
		}
	}

	private void loadStylesTypes() {
		for (int i = 0; i < Data.STYLE_TYPES.length; i++) {
			String s = Data.STYLE_TYPES[i];
			styleTypeList.addItem(s);
		}
	}

	private Widget getAdvancedForm() {

		styleText.setTextBoxWidth("175px");

		final FlexTable frm = new FlexTable();
		frm.setSize("100%", "50px");
		frm.setHTML(0, 0, Ctrl.trans.style() + ":");
		frm.setWidget(0, 1, styleSuggest);
		frm.setHTML(0, 2, "");
		frm.setHTML(1, 0, Ctrl.trans.value());
		frm.setWidget(1, 1, styleText);
		frm.setWidget(1, 2, btnAddStyle);

		VerticalPanel advancedForm = new VerticalPanel();
		advancedForm.add(styleTypeList);
		advancedForm.add(frm);

		return advancedForm;
	}

	private void forFocus() {
		styleSuggest.getTextBox().addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				styleText.setMixValue("");
			}
		});
	}

	private void forAdd() {
		btnAddStyle.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				addToGrid(styleSuggest.getValue(), styleText.getValue());
				parent.dataChanged(getSelectedType(), styleSuggest.getValue(), styleText.getValue());
				parent.getCode().setStyleByType(getSelectedType(), styleSuggest.getValue(), styleText.getValue());

			}
		});
	}

	protected void addToGrid(String name, String value) {
		String sType = getSelectedType();
		Map<String, String> styles = parent.getCode().getStyleByType(sType);

		if (value == null || value.isEmpty()) {
			parent.dataChanged(getSelectedType(), name, null);
			// parent.code.delStyleByType(getSelectedType(), name);
			addedStyles.deleteRow(name);
			return;
		}

		if (styles != null && styles.get(name) != null) {
			addedStyles.updateStyleRow(name, value);
		} else {
			MyAtt myatt = new MyAtt(name);
			addedStyles.addOneRow(myatt, value);
		}
		parent.getCode().setStyleByType(sType, name, value);

	}

	public void resetForm() {
		styleTypeList.setSelectedIndex(0);
		styleSuggest.setValue("");
		styleText.setMixValue("");
		addedStyles.resetTable();
	}

	public String getSelectedType() {
		return styleTypeList.getValue(styleTypeList.getSelectedIndex());
	}

	public void showAdvancedData() {

		String type = getSelectedType();
		Map<String, String> styles = parent.getStyleByType(type);

		if (styles == null)
			return;

		for (String key : styles.keySet()) {
			MyAtt myatt = new MyAtt(key);
			addedStyles.addOneRow(myatt, styles.get(key));
		}
	}

}
