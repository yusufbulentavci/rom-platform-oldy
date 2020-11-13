package com.bilgidoku.rom.kurum;

import com.bilgidoku.rom.ilk.json.JSONObject;

public class Organization {

	public final Integer hostId;
	public final Integer issueYear;
	public final Integer issueNext;
	public final JSONObject shipStyle;
	public final JSONObject payStyle;

	public Organization(Integer hostId, Integer issueYear, Integer issueNext, JSONObject shipStyle, JSONObject payStyle) {
		this.hostId=hostId;
		this.issueYear=issueYear;
		this.issueNext=issueNext;
		this.shipStyle=shipStyle;
		this.payStyle=payStyle;
	}

}
