package com.bilgidoku.rom.gwt.client.widgets;

import java.awt.List;
import java.util.ArrayList;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class ColorList extends Composite implements HasChangeHandlers {
	private Images images = GWT.create(Images.class);
	private final CellList<String[]> clColors = new CellList<String[]>(new CellTemplate());
	private final SingleSelectionModel<String[]> listSelModel = new SingleSelectionModel<String[]>();

	private final ColorDlg colorPopup = new ColorDlg();
	private String selectedValue = "#FFFFFF";
	private HTML box = new HTML(getBox(selectedValue));
	private String value  = null;

	public ColorList(final boolean popupAtTop) {
		clColors.setSelectionModel(listSelModel);
		clColors.setWidth("70px");
		listSelModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedValue = listSelModel.getSelectedObject()[1];
				setColor(listSelModel.getSelectedObject()[0]);
				setValue(listSelModel.getSelectedObject()[1]);
				colorPopup.hide();
				ColorList.this.fireEvent(new PasteEvent());
			}
		});

		// if (popupAtTop)
		// icon = images.arrow_up();

		box.setStyleName("site-box");
		box.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				colorPopup.show();
				if (popupAtTop)
					colorPopup.setPopupPosition(box.getAbsoluteLeft() -80, box.getAbsoluteTop() - 80);
				else
					colorPopup.setPopupPosition(box.getAbsoluteLeft(), box.getAbsoluteTop() + 30);
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

		String html = "<div style='background-color:" + hex
				+ "; width:20px; height:20px;margin-right:4px;display:inline-block;'></div>"
				+ "<span style='float:right;'>"
				+ AbstractImagePrototype.create(images.arrow_down()).getHTML() + "</span>";
		return html;
	}

	public void setColor(String val1) {
		if (val1 == null)
			return;
		String val = val1.trim().replaceAll("\\n", "").replaceAll("\\t", "");
		selectedValue = val;
		box.setHTML(getBox(val));
	}

	public void setValue(String val1) {
		value = val1;
	}

	public String getValue() {
		return value;
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public void populate(ArrayList<String[]> colors) {
		clColors.setRowCount(colors.size(), true);
		clColors.setRowData(0, colors);
	}
	
	
	public void empty() {
		clColors.setRowCount(0, true);
		clColors.setRowData(0, new ArrayList<String[]>());
	}

	private class ColorDlg extends ActionBarDlg {
		public ColorDlg() {
			super("Colors", null, null);
			run();
		}

		@Override
		public Widget ui() {
			clColors.setStyleName("site-chatdlgin");
			return clColors;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
		}

	}

	private class CellTemplate extends AbstractCell<String[]> {
		@Override
		public void render(Context context, String[] row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<div class='site-box' style='background:" + row[0]
					+ ";width:60px;height:20px;border-bottom:1px solid #888888;' title='"+row[2]+"'></div>");
		}
	}

}
