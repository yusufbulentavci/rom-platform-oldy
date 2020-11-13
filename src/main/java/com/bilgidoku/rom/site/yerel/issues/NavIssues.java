package com.bilgidoku.rom.site.yerel.issues;

import java.util.Iterator;

import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.user.client.ui.Widget;

public class NavIssues extends NavBase implements HasContainer {
	// private boolean lastClosed;
	// private boolean lastResolved;
	// private boolean lastMine;
	// private boolean lastNotAssigned;
	// private boolean lastWaiting;

	private boolean added = false;

	public NavIssues() {
		super("/_local/images/common/iss_grey.png", "/_local/images/common/iss_grey.png",
				"/_local/images/common/iss_grey.png", true, 1);
	}

	@Override
	public void addContainers() {
		if (added)
			return;

		added = true;
		getToolbar().reloadContainer(null);
	}


	@Override
	public NavToolbarBase createToolbar() {
		NavToolbar tb = new NavToolbar(this);
		return tb;
	}

	@Override
	public void add(Widget w) {
		getToolbar().add(w);

	}

	@Override
	public void clear() {
		getToolbar().clear();

	}

	@Override
	public Iterator<Widget> iterator() {
		return getToolbar().iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return getToolbar().remove(w);
	}

	public void filterAgain() {
		getToolbar().reloadContainer(null);

	}

}
