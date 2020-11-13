package com.bilgidoku.rom.gwt.shared;

public class Account implements Transfer {
	public Long credits;
	public String[] limitfeatures;
	public Long reserved;
	public String model;
	public Long modelExpire;
	public String modelNext;

	public Account(Long credits2, Long reserved, String model, Long modelExpire, String modelNext,
			String[] limitFeatures2) {
		this.credits = credits2;
		this.reserved = reserved;
		this.model = model;
		this.modelNext = modelNext;
		this.modelExpire = modelExpire;
		this.limitfeatures = limitFeatures2;
	}

	public Account() {
		// TODO Auto-generated constructor stub
	}
}
