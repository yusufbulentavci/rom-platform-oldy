package com.bilgidoku.rom.site.yerel.boxing;

public interface BoxerGuiCb {

	void save();

	void pickWidgetFromWidgetList(String key);

	void infoChanged();

	void preview();
	
	void addImageWidget(String uri);

	void selectAllBody();

}
