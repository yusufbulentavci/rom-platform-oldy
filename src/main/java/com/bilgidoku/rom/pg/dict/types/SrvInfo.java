package com.bilgidoku.rom.pg.dict.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bilgidoku.rom.pg.dict.CGAtt;
import com.bilgidoku.rom.pg.dict.CGMethod;
import com.bilgidoku.rom.pg.dict.CGType;
import com.bilgidoku.rom.pg.dict.Net;

public class SrvInfo implements CGType {
	public String uri;
	public String name;
	public List<SrvMethod> methods = new ArrayList<SrvMethod>();
	public boolean custom;
	public boolean norom;
	public Net net;
	public String[] roles;
	public int cpu;
	public String pack;

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getNameAsClass() {
		return capitalize(name);
	}

	public String getUri() {
		return uri;
	}

	public String getName() {
		return name;
	}

	public List<SrvMethod> getMethods() {
		return methods;
	}

	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public void addMethod(SrvMethod md) {
		md.servuri = uri;
		methods.add(md);
	}

	public Net getNet() {
		return this.net;
	}

	public Collection<CGMethod> getClientDaomethods() {
		List<CGMethod> daomethods = new ArrayList<CGMethod>();
		for (SrvMethod method : methods) {
			if (!method.hasFileArg)
				daomethods.add(method);
		}
		return daomethods;
	}

	public Collection<CGMethod> getDaomethods() {
		List<CGMethod> daomethods = new ArrayList<CGMethod>();
		for (SrvMethod method : methods) {
			daomethods.add(method);
		}
		return daomethods;
	}

	@Override
	public String getNameFirstUpper() {
		return capitalize(name);
	}

	@Override
	public boolean getNoUri() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CGAtt> getAtts() {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

}
