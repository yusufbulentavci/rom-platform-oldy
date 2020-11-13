package com.bilgidoku.rom.gwt.shared;


public class AuditItem implements Transfer{
	public Integer hostId;
	public String cid;
	public String method;
	public String uri;
	public String[] fieldNames;
	public String[] fieldValues;
	public Integer aid;
	public Long time;
	
	public AuditItem(){
	}

	public AuditItem(int hostId, String cid, String method, String uri,
			String[] fieldNames, String[] fieldValues) {
		this.hostId = hostId;
		this.cid = cid;
		this.method = method;
		this.uri = uri;
		this.fieldNames = fieldNames;
		this.fieldValues = fieldValues;
	}

	public AuditItem(int hostId, int aid, long timestamp,
			String cid, String method, String uri,
			String[] fieldNames, String[] fieldValues) {
		this(hostId, cid, method, uri,
			fieldNames, fieldValues);
		this.aid=aid;
		this.time=timestamp;
	}
	
}
