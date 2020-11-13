package com.bilgidoku.rom.shared;

import java.util.HashMap;
import java.util.Map;



public class TagImpl implements Tag{
	private final boolean isRoot;
	private final String group;
	private final String named;
	private final Att[] ats;

	private final String[] children;
	private final char[] childrenOpt;

	private final String summary;


	public TagImpl(final String named, final boolean isRoot, final String group, final Att[] ats,
			final String[] children, final char[] childrenOpt, final String summary) {
		this.named = named;
		this.isRoot = isRoot;
		this.group = group;
		this.ats = ats;
		this.children = children;
		this.childrenOpt = childrenOpt;
		this.summary = summary;
	}


	public boolean isRoot() {
		return isRoot;
	}


	public String getGroup() {
		return group;
	}

	public String getNamed() {
		return named;
	}


	public Att[] getAts() {
		return ats;
	}


	public String[] getChildren() {
		return children;
	}


	public char[] getChildrenOpt() {
		return childrenOpt;
	}

	public String getSummary() {
		return summary;
	}


	@Override
	public Att[] getParams() {
		return ats;
	}
	
	@Override
	public Map<String,Att> getAtDefs(){
		if(named.startsWith("c:"))
			return null;
		
		return defs();
	}


	private Map<String, Att> defs() {
		if(ats==null)
			return null;
		Map<String,Att> ret=new HashMap<String,Att>();
		for (Att it : ats) {
			ret.put(it.getNamed(), it);
		}
		return ret;
	}


	@Override
	public Map<String, Att> getParamDefs() {
		if(named.startsWith("c:"))
			return defs();
		return null;
	}


}