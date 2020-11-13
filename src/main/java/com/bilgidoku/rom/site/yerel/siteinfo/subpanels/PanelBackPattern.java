package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class PanelBackPattern extends ScrollPanel {
	private final BackPatternCell patternCell = new BackPatternCell();
	private final CellList<String> patternList = new CellList<String>(patternCell);
	private final SingleSelectionModel<String> patterListSelModel = new SingleSelectionModel<String>();
	protected boolean initialLoad = false;;

	public PanelBackPattern(PanelBackground parentPanel) {
		forSelectList(parentPanel);
		patternList.setSelectionModel(patterListSelModel);
		List<String> patternData = new ArrayList<String>();
		for (String is : Data.BACK_PATTERNS) {
			patternData.add(is);
		}
		patternList.setRowCount(Data.BACK_PATTERNS.length, true);
		patternList.setRowData(0, patternData);
		this.setSize("155px", "145px");
		this.add(patternList);
	}

	private void forSelectList(final PanelBackground parentPanel) {
		patterListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (initialLoad) {
					initialLoad = false;
					return;
				}
				if (patterListSelModel.getSelectedObject() == null)
					return;
				parentPanel.backPatternChanged(patterListSelModel.getSelectedObject());
			}
		});
	}

	public void removeSelection() {
		initialLoad = true;
		patterListSelModel.clear();
	}

	public void setSelection(String backPtn) {
		if (backPtn == null || backPtn.isEmpty())
			return;
		initialLoad = true;
		patterListSelModel.setSelected(backPtn, true);

	}

	public String getSelected() {
		if (patterListSelModel.getSelectedObject() == null)
			return null;

		return patterListSelModel.getSelectedObject();
	}

	private class BackPatternCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<div style='background-color: #3F4041; display: inline-block; "
					+ "height: 60px; line-height: 16px; margin: 4px; padding: 0; "
					+ "width: 60px; background-image: url("
					+ row + ")'></div>");
		}
	}

}
