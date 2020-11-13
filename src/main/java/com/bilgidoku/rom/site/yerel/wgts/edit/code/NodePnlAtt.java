package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import java.util.HashSet;
import java.util.Set;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.NullableMap;
import com.bilgidoku.rom.site.yerel.tags.HtmlTagMap;
import com.bilgidoku.rom.site.yerel.wgts.edit.FlxGrid;
import com.bilgidoku.rom.site.yerel.wgts.edit.Seen;
import com.google.gwt.user.client.ui.Composite;

public class NodePnlAtt extends Composite implements Seen {

	private Code code;
	private final FlxGrid flx = new FlxGrid(this, false);

	public NodePnlAtt() {
		initWidget(flx);
	}

	public void update(Code code) {
		this.code = code;
	}

	@Override
	public void showData() {
		// build data
		// widget ve tag için çalışır
		if (code.tag.startsWith("c:"))
			return;

		if (code == null)
			return;

		Tag curTag = (code.tag.startsWith("w:")) ? HtmlTagMap.one().getTagDef("span")
				: HtmlTagMap.one().getTagDef(code.tag);
		if (curTag == null)
			return;

		Att[] allAtts = curTag.getAts();

		NullableMap<String, String> tagAtts = code.getAttributes();

		Set<String> hm = new HashSet<String>();

		flx.resetTable();

		if (allAtts != null) {
			for (Att at : allAtts) {
				if (!at.isMethod()) {

					String name = at.getNamed();
					hm.add(name);
					String value = "";
					if (!at.isDeclare()) {
						value = tagAtts != null ? tagAtts.get(name) : "";
					} else {
						value = at.getDefvalue();
					}
					flx.addOneRow(at, value);
				}

			}
		}

		if (tagAtts != null && tagAtts.keySet() != null)
			for (String key : tagAtts.keySet()) {
				if (!hm.contains(key)) {
					String value = tagAtts != null ? tagAtts.get(key) : "";

					MyAtt myatt = new MyAtt(key);

					flx.addOneRow(myatt, value);
				}
			}

	}

	@Override
	public void dataChanged(String name, String value) {
		if (value == null || value.isEmpty()) {
			this.code.removeAtt(name);
			return;
		}
		code.setAttribute(name, value);
	}

	@Override
	public void dataChanged(String type, String name, String value) {
	}

}
