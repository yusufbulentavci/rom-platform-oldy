package com.bilgidoku.rom.site.yerel.admin;

import com.google.gwt.visualization.client.DataTable;

public interface DataReady {
	public void hitsDataReady(DataTable lineData, DataTable mapData, DataTable pieData, boolean hasNextWeek);
}
