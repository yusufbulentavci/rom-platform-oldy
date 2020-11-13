package com.bilgidoku.rom.shared;

import java.util.Map;


public interface Tag {
	public boolean isRoot();
	public String getGroup();
	public String getNamed();
	public Att[] getAts();
	public Att[] getParams();
	public String[] getChildren();
	public char[] getChildrenOpt();
	public String getSummary();
	public Map<String,Att> getAtDefs();
	public Map<String,Att> getParamDefs();
}