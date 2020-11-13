package com.bilgidoku.rom.site.kamu.graph.client.ui;

import com.google.gwt.user.client.ui.Composite;

public interface SimpleChangeListener {
	public void changed(Composite source, Integer oldone, Integer newone);
	public void changed(String source);
}
