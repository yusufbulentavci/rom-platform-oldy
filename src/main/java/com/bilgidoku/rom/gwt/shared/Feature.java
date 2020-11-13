package com.bilgidoku.rom.gwt.shared;

public class Feature implements Transfer{
	public String item;
	public Integer importance;
	public Integer tperiod;
	public Integer invoicetperiod;
	public Integer invoicetperiodamount;
	public Integer[] autorenewoptions;
	public String usageunit;
	public String description;
	
	public Feature(String item, Integer importance, Integer tperiod, Integer invoicetperiod,
			Integer invoicetperiodamount, Integer[] autorenewoptions, String usageunit, String description) {
		super();
		this.item = item;
		this.importance = importance;
		this.tperiod = tperiod;
		this.invoicetperiod = invoicetperiod;
		this.invoicetperiodamount = invoicetperiodamount;
		this.autorenewoptions = autorenewoptions;
		this.usageunit = usageunit;
		this.description = description;
	}

	public Feature() {
		// TODO Auto-generated constructor stub
	}
}
