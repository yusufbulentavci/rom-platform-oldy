package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class MaskSelect extends HorizontalPanel {
	private static String[] rights = new String[] { "none", "execute only", "write only", "write and execute", "read only", "read and execute",
			"read and write", "full" };
	private final ListBox[] listBoxes;

	public MaskSelect(long mask) {
		listBoxes = new ListBox[Data.ROLECOUNT];
		for (int i = 0; i < Data.ROLECOUNT; i++) {
			int stat = (int) ((mask >> (i * 3)) & 07);
			ListBox lb = new ListBox();
			for (int j = 0; j < rights.length; j++) {
				lb.addItem(rights[j]);
			}
			lb.setSelectedIndex(stat);
			listBoxes[i] = lb;
		}
	}

	public void updateMask(long mask) {
		for (int i = 0; i < Data.ROLECOUNT; i++) {
			int stat = (int) ((mask >> (i * 3)) & 07);
			listBoxes[i].setSelectedIndex(stat);
		}
	}
	
	public long getMask(){
		long ret=0;
		for (int i = 0; i < Data.ROLECOUNT; i++) {
			int stat=listBoxes[i].getSelectedIndex();
			ret=ret|(stat << (i * 3));
		}
		return ret;
	}
}
