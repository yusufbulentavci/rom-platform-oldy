package com.bilgidoku.rom.shared.query;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.shared.query.values.ValueCollector;

public class QueryContext {
	private StringBuilder sb=new StringBuilder();
	
	private List<String> froms=new ArrayList<>();
	
	public ValueCollector vc;
	public ReqKnowns	rk;
	public SuchCriteria currentCriteria;
	
	

	public void append(String string) {
		sb.append(string);
	}

	public void addFrom(String turAdiToTableName) {
		this.froms.add(turAdiToTableName);
	}

	public void appendEscaped(String tag) {
		sb.append("'");
		sb.append(tag);
		sb.append("'");
	}

}
