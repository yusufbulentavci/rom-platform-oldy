package com.bilgidoku.rom.site.yerel.wgts.edit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.tags.HtmlTagMap;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class DragPnlTags extends Composite {

	public DragPnlTags() {
		ScrollPanel tagScroll = this.buildTags();
		initWidget(tagScroll);
	}

	private ScrollPanel buildTags() {
		final FlexTable tagTable = new FlexTable();
		tagTable.setCellSpacing(1);
		tagTable.setCellPadding(1);

		List<String> listTagNames = new ArrayList<String>();
		for (Entry<String, Tag> entry : HtmlTagMap.one().getTagDefs().entrySet()) {
			if (!entry.getKey().startsWith("c:"))
				listTagNames.add(entry.getKey());
		}

		Collections.sort(listTagNames);

		for (int i = 0; i < listTagNames.size(); i++) {
			String tagName = listTagNames.get(i);

			Label lbl = new DraggableLabel(tagName, Code.TAG);
			lbl.setStyleName("site-tag");

			tagTable.setWidget(i / 3, i % 3, lbl);

		}

		ScrollPanel tagPanel = new ScrollPanel(tagTable);
		return tagPanel;
	}

}
