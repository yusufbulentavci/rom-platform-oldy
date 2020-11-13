package com.bilgidoku.rom.gwt.shared;

public class Tariff implements Transfer{
	public String feature;
	public Integer limitto;
	public Long credits;
	public Boolean denied;

	public Tariff(String feature2, Integer limitto2, Boolean denied2, Long credits2) {
		this.feature=feature2;
		this.limitto=limitto2;
		this.denied=denied2;
		this.credits=credits2;
	}
	
	public Tariff(){
	}
}
