package com.bilgidoku.rom.dns;

public class DnsRecord {

	public final String name;
	public final String value;
	public final String type;
	final int ttl;

	public DnsRecord(String name, String value, String type, int ttl) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		this.ttl = ttl;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other instanceof DnsRecord)) {
			return false;
		}
		DnsRecord dr = (DnsRecord) other;
		return (name.equals(dr.name) && value.equals(dr.name) && type.equals(dr.type));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DnsRecord name:");
		sb.append(name);
		sb.append(" type:");
		sb.append(type);
		sb.append(" value:");
		sb.append(value);
		sb.append(" ttl:");
		sb.append(ttl);
		return sb.toString();
	}
}
