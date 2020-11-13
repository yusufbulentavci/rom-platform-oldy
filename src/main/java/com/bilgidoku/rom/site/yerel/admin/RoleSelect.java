package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class RoleSelect extends HorizontalPanel {

	private final CheckBox[] checkBoxs;

	public RoleSelect(int roles) {
		checkBoxs = new CheckBox[Data.ROLECOUNT - 6];
		for (int i = 6; i < Data.ROLECOUNT; i++) {
			CheckBox cb = new CheckBox(Data.ROLENAMES[i]);
			checkBoxs[i - 6] = cb;
			cb.setValue((roles & (1 << i)) != 0);
			this.add(cb);
		}
	}

	public void updateRoles(int roles) {
		for (int i = 6; i < Data.ROLECOUNT; i++) {
			checkBoxs[i - 6].setValue(((roles & (1 << i)) != 0));
		}
	}

	public int getRoles() {
		int ttl = 0;
		for (int i = 6; i < Data.ROLECOUNT; i++) {
			if (!checkBoxs[i - 6].getValue())
				continue;
			ttl = ttl | (1 << i);
		}
		return ttl;
	}
}
