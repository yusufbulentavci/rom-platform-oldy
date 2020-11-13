package com.bilgidoku.rom.gwt.client.util.com;

public abstract class CompFactory{
	protected Comp one;
	public Comp get(){
		if(this.one==null){
			this.one=create();
			this.one.initial();
		}
		return this.one;
	}
	public abstract Comp create();
	public abstract CompInfo info();
	
}
