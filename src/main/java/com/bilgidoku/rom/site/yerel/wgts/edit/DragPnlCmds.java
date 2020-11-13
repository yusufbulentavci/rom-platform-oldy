package com.bilgidoku.rom.site.yerel.wgts.edit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.shared.cmds.CommandRepo;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class DragPnlCmds extends Composite {

	public DragPnlCmds() {
		ScrollPanel tagScroll = this.buildTags();
		initWidget(tagScroll);
	}

	private ScrollPanel buildTags() {

		final FlexTable tagTable = new FlexTable();
		tagTable.setCellSpacing(1);
		tagTable.setCellPadding(1);

		Set<String> setKeys = CommandRepo.one().getCommandNames();

		List<String> keys = new ArrayList<String>();
		keys.addAll(setKeys);
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			Label lbl = new DraggableLabel(keys.get(i), "command");
			lbl.setStyleName("site-tag");
			tagTable.setWidget(i / 2, i % 2, lbl);

		}

		ScrollPanel tagPanel = new ScrollPanel(tagTable);
		return tagPanel;
	}

}
