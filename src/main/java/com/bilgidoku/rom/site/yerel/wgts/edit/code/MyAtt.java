package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONValue;

public class MyAtt implements Att {
	private String key = null;

	public MyAtt(String name) {
		key = name;
	}

	@Override
	public String getNamed() {
		return key;
	}

	@Override
	public boolean isMethod() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getEnumeration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeclare() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getContext() {
		return 1;
	}

	@Override
	public Att cloneObj() throws RunException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefvalue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReq() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONValue store() throws RunException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFixed() {
		// TODO Auto-generated method stub
		return false;
	}

}