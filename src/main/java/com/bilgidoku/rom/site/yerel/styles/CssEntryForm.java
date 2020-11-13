package com.bilgidoku.rom.site.yerel.styles;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.tags.HtmlTagMap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;

public class CssEntryForm extends Composite{
	
	private final TextBox tbcValue = new TextBox();
	
	private final MultiWordSuggestOracle oracleWidget = new MultiWordSuggestOracle();
	private final SuggestBox txtWidget = new SuggestBox(oracleWidget);

//	private final TextBox txtWidget = new TextBox();

	private final ListBox lbPseClass = new ListBox();
	private final Button btnSaveStyle = new Button(Ctrl.trans.updateStyle());
	
	private final MultiWordSuggestOracle oracleProperty = new MultiWordSuggestOracle();
	private final SuggestBox sbProperties = new SuggestBox(oracleProperty);

	private final MultiWordSuggestOracle oracleForTags = new MultiWordSuggestOracle();
	private final SuggestBox sbTags = new SuggestBox(oracleForTags);

	private final Button btnClearForm = new Button(Ctrl.trans.resetForm());
	private final TabStyles tab;
	
	public CssEntryForm(TabStyles tabStyles) {
		this.tab = tabStyles;
		forClearForm();
		forSaveStyle(tabStyles);
		forWidgetChanged(tabStyles);
		
		//load initial data
		for (String s : Data.STYLE_TYPES) {
			lbPseClass.addItem(s);
		}

		List<String> ltag = new ArrayList<String>(HtmlTagMap.one().getTagDefs().keySet());
		for (String s : ltag) {
			oracleForTags.add(s);
		}

		for (String key : HtmlTagMap.one().getStyleMap().keySet()) {
			oracleProperty.add(key);
		}

		for (String key : tabStyles.allCss.keySet() ) {
			oracleWidget.add(key);
		}
		initWidget(getForm());
	}
	
	private void forWidgetChanged(final TabStyles tabStyles) {
		txtWidget.addValueChangeHandler(new ValueChangeHandler<String>() {			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				tabStyles.loadCellTable(txtWidget.getValue());
				emptyForm();				
			}
		});
		
		txtWidget.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				tabStyles.loadCellTable(txtWidget.getValue());
				emptyForm();				
			}
		});
		
		
	}

	private void forClearForm() {
		btnClearForm.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				tab.buttonsState(true);
				emptyForm();
			}
		});
	}	

	private void forSaveStyle(final TabStyles tabStyles) {
		btnSaveStyle.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String t = sbTags.getValue();
				String pse = lbPseClass.getValue(lbPseClass.getSelectedIndex());
				tab.changeData(txtWidget.getValue().trim(), t, pse, sbProperties.getValue(), tbcValue.getValue());
				tabStyles.loadCellTable(txtWidget.getValue());
			}
		});
	}

	private FlexTable getForm() {
		tbcValue.setWidth("200px");
		
		lbPseClass.setWidth("150px");
		
		HorizontalPanel btns = new HorizontalPanel();
		btns.add(btnSaveStyle);
		btns.add(btnClearForm);
		btns.setSpacing(2);
		
		FlexTable form = new FlexTable();
		form.setWidth("100%");
		form.setHeight("50px");
		form.setHTML(0, 0, Ctrl.trans.widget());
		form.setWidget(0, 1, txtWidget);
		form.setHTML(1, 0, Ctrl.trans.pseClass());
		form.setWidget(1, 1, lbPseClass);
		form.setHTML(2, 0, Ctrl.trans.tag());
		form.setWidget(2, 1, sbTags);
		form.setHTML(3, 0, Ctrl.trans.property());
		form.setWidget(3, 1, sbProperties);
		form.setHTML(4, 0, Ctrl.trans.value());
		form.setWidget(4, 1, tbcValue);
		form.setWidget(5, 0, btns);

		form.getFlexCellFormatter().setColSpan(5, 0, 2);

		return form;
		
	}
	
	protected void setForm(String widget, String tag, String pse, String property, String value) {
		txtWidget.setValue(widget);
		//form.setHTML(0, 1, widget);
		sbTags.setValue(tag);
		setListBoxValue(lbPseClass, pse);
		sbProperties.setValue(property);
		tbcValue.setValue(value);
	}
	
	protected void emptyForm() {
		sbTags.setValue("");
		lbPseClass.setSelectedIndex(0);
		sbProperties.setValue("");
		tbcValue.setValue("");
		
	}
	
	private void setListBoxValue(ListBox lb, String value) {
		if (value.isEmpty()) {
			lb.setSelectedIndex(0);
			return;
		}
		for (int i = 0; i < lb.getItemCount(); i++) {
			if (lb.getValue(i).equals(value)) {
				lb.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public String getWidgetValue() {
		return txtWidget.getValue();
	}


}
