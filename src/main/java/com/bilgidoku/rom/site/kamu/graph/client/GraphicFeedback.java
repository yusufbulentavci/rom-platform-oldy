package com.bilgidoku.rom.site.kamu.graph.client;

import com.bilgidoku.rom.min.geo.Point;

public interface GraphicFeedback {

	void modified();
	void setStatus(String text);
	void areaChanged(Point area, int paperSize);

}
