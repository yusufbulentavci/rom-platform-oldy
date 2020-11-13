package com.bilgidoku.rom.site.yerel.writings.wizard;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class PnlTemplates extends VerticalPanel {
	private final TemplateCell templCell = new TemplateCell();
	private final CellList<String[]> templList = new CellList<String[]>(templCell);
	private final SingleSelectionModel<String[]> templSelModel = new SingleSelectionModel<String[]>();
	private final ListBox typeOfPage = new ListBox();

	public PnlTemplates() {
		typeOfPage.addItem(Ctrl.trans.selectPageType(), "");
		typeOfPage.addItem(Ctrl.trans.homePage(), "homepage");
		typeOfPage.addItem(Ctrl.trans.detail(), "detail");
		typeOfPage.addItem(Ctrl.trans.listing(), "listing");
		typeOfPage.addItem(Ctrl.trans.contactUs(), "contactus");
		typeOfPage.addItem(Ctrl.trans.search(), "search");

		forSelectType();
		templList.setSelectionModel(templSelModel);

		ScrollPanel sp = new ScrollPanel();
		// sp.setHeight("200px");
		sp.setSize("372px", "240px");
		sp.add(templList);
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.add(typeOfPage);
		this.add(sp);
	}

	private void forSelectType() {
		typeOfPage.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {

				String type = typeOfPage.getValue(typeOfPage.getSelectedIndex());
				if (type.isEmpty()) {
					templList.setRowCount(0, true);
					// templList.setRowData(0, null);
					return;
				}

				List<String[]> data = getData(type);
				templList.setRowCount(data.size(), true);
				templList.setRowData(0, data);

			}
		});

	}

	private List<String[]> getData(String type) {
		List<String[]> data = new ArrayList<String[]>();
		if (type.equals("homepage")) {

			data.add("homepage_1,Homepage Big Slider".split(","));
			// data.add("homepage_2,Homepage 2".split(","));
			data.add("homepage_3,Homepage Small Slider".split(","));

		}

		else if (type.equals("listing")) {
			data.add("listing_1,Listing On the Left".split(","));
			data.add("listing_2,Listing Full".split(","));
			data.add("listing_3,Listing On the Right".split(","));
			data.add("listing_4,Price List".split(","));

		}

		else if (type.equals("detail")) {
			data.add("detail_1,Detail Carousel".split(","));
			data.add("detail_2,Detail Slider".split(","));
			data.add("detail_3,Detail Gallery".split(","));
			data.add("detail_4,Detail Sale".split(","));
			// data.add("detail_5,Detail Image".split(","));
		}

		else if (type.equals("contactus")) {
			data.add("contactus,Contact Us".split(","));

		}

		else if (type.equals("search")) {
			data.add("search,Search".split(","));

		}

		return data;
	}

	public String getSelected() {
		String[] img = templSelModel.getSelectedObject();
		if (img == null)
			return null;
		else
			return img[0];
	}

	private class TemplateCell extends AbstractCell<String[]> {
		@Override
		public void render(Context context, String[] row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant(
					"<div style='display:inline-block;padding: 4px; margin:4px; border:1px solid #CCCCCC;'>"
							+ "<img src='/_local/images/templates/" + row[0]
							+ ".png' width='160px' height='214px'></img><br>" +
							// + row[1] +
							"</div>");
		}
	}

}
