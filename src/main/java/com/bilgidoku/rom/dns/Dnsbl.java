package com.bilgidoku.rom.dns;

import java.net.UnknownHostException;

public class Dnsbl extends DigOnServer{

	public Dnsbl(int cacheSize) throws UnknownHostException {
		super(null, cacheSize);
	}
	
	public boolean dnsbl(String ip){
//		return dnsbl(ip, "spam.dnsbl.sorbs.net");
		return dnsbl(ip, "zen.spamhaus.org");
	}

}
