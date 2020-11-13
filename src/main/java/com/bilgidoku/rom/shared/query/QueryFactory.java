package com.bilgidoku.rom.shared.query;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONValue;

public class QueryFactory {

	public static SuchCriteria create(String k, JSONValue jvx) throws RunException {
		SuchCriteria sc=null;
		switch(k) {
		case LogicalAnd.NAME:
			sc=new LogicalAnd();
			break;
		case LogicalOr.NAME:
			sc=new LogicalOr();
			break;
		case ClassSelector.NAME:
			sc=new ClassSelector();
			break;
		case ContainerSelector.NAME:
			sc=new ContainerSelector();
			break;
		case TagSelector.NAME:
			sc=new TagSelector();
			break;
		default:
			throw new RuntimeException("Criteria type not found:"+k);
		}
		sc.loadFromJson(jvx);
		
		return sc;
	}

}
