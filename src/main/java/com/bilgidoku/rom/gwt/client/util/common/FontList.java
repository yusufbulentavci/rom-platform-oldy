package com.bilgidoku.rom.gwt.client.util.common;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.shared.render.Css;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class FontList extends ScrollPanel {

	private final static ApplicationConstants cons = GWT.create(ApplicationConstants.class);

	private final FontCell fontCell = new FontCell();
	private final CellList<String> fontList = new CellList<String>(fontCell);
	private final SingleSelectionModel<String> fontListSelModel = new SingleSelectionModel<String>();
	private List<String> fontData;
	private FontChanged fontChanged = null;

	public FontList(String from, JSONArray infoExtraFonts) {
		
		fontList.setSelectionModel(fontListSelModel);

		fontData = new ArrayList<String>();

		for (int i = 0; i < Css.SAFE_FONTS.length; i++) {
			fontData.add(Css.SAFE_FONTS[i]);
		}

//		if (from.equals("one")) {
//			for (int i = 0; i < Css.GOOGLE_FONTS.length; i++) {
//				fontData.add(Css.GOOGLE_FONTS[i]);
//			}
//		} else if (from.equals("editor")) {
			if (infoExtraFonts != null && infoExtraFonts.size() > 0) {
				for (int i = 0; i < infoExtraFonts.size(); i++) {
					String font = infoExtraFonts.get(i).isString().stringValue();
					fontData.add(font);					
				}
			}
//		}

		fontList.setVisibleRange(0, fontData.size());
		fontList.setRowCount(fontData.size(), true);
		fontList.setRowData(0, fontData);

		forSelect();

		this.add(fontList);
		this.setHeight("240px");
		this.setWidth("270px");
		this.setStyleName("site-innerform");

	}

//	public FontList(FontChanged fontChanged, String from) {
//		this(from, null);
//		this.fontChanged = fontChanged;
//	}

	public void setFontChanged(FontChanged fontChanged2) {
		this.fontChanged = fontChanged2;
	}

	private void forSelect() {
		fontListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String arr = fontListSelModel.getSelectedObject();
				FontList.this.fontChanged.fontChanged(arr);
			}
		});
	}

	public String getSelectedFont() {
		if (fontListSelModel.getSelectedObject() == null)
			return null;
		String arr = fontListSelModel.getSelectedObject();
		return arr;

	}

	public void selectFont(String siteFont) {
		String found = null;
		int i = 0;
		for (i = 0; i < fontList.getRowCount(); i++) {
			String arr = fontList.getVisibleItem(i);
			if (siteFont.equals(arr)) {
				found = arr;
				break;
			}
		}
		if (found != null) {
			fontListSelModel.setSelected(found, true);
			final int j = i;
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					if (j + 1 != fontList.getRowCount())
						fontList.getRowElement(j + 1).scrollIntoView();
					else
						fontList.getRowElement(j).scrollIntoView();

				}
			});
		}

	}

	private class FontCell extends AbstractCell<String> {

		@Override
		public void render(Context context, String row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}

			String fontName = row.replaceAll("'", "");
			String fontFamily = row;

			if (row.indexOf(",") > 0) {
				fontName = fontName.substring(0, row.indexOf(","));
			} else {
				fontFamily = "'" + fontFamily + "'";
			}

			sb.appendHtmlConstant("<div style=\"display:block; border-bottom: 1px solid #E5E5E5; "
					+ "border-left: 1px solid #E5E5E5; overflow: hidden; padding-left: 10px; width:210px; "
					+ "display:inline-block; font-family: " + fontFamily + ";\">"
					+ "<span style='font-size:21px;line-height:36px;'>" + fontName + "</span><br/><br/>"
					+ "<span style='font-size:13px;'>" + cons.sampleText(fontName) + "<br/><br/></span></div>");

		}
	}

}
