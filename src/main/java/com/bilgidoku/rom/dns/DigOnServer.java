package com.bilgidoku.rom.dns;

import java.net.UnknownHostException;

import org.xbill.DNS.Cache;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.bilgidoku.rom.min.Sistem;

public class DigOnServer {

	final private SimpleResolver resolver;
	private String host;
	final Cache cache;

	public DigOnServer(String host, int cacheSize) throws UnknownHostException {
		this.resolver = new SimpleResolver(host);
		this.host=host;
		cache = new Cache(DClass.IN);
		cache.setMaxEntries(cacheSize);	
	}

	final static int type = Type.A, dclass = DClass.IN;

	public synchronized String dig(String nameString) {
		
		Lookup l;
		try {
			l = new Lookup(nameString, type, dclass);
			l.setCache(cache);
			l.setResolver(resolver);
			l.run();
			if (l.getResult() == Lookup.SUCCESSFUL){
//				System.out.println(l.getAnswers()[0].rdataToString());
				return l.getAnswers()[0].rdataToString();
			}
		} catch (TextParseException e) {
			Sistem.printStackTrace(e, "DigOnServer for host:"+host);
		}
		return null;
	}
	
	public int digAndGetLastByte(String nameString){
		String ret=dig(nameString);
		System.out.println(nameString);
		if(ret==null)
			return -1;
		int li=ret.lastIndexOf('.');
		if(li<0 || li>=(ret.length()-1)){
			Sistem.errln("digAndGetLastByte "+nameString+" "+ret);
			return -2;
		}
		String last=ret.substring(li+1);
		try{
			return Integer.parseInt(last);
		}catch(Exception e){
			Sistem.errln("digAndGetLastByte "+nameString+" "+ret);
			return -3;
		}
	}
	
	/**
	 * 
	 * @param ip
	 * @return true if found in a db
	 */
	public boolean dnsbl(String ip, String dnsbl){
		String[] all=ip.split("\\.");
		int last = digAndGetLastByte(all[3]+"."+all[2]+"."+all[1]+"."+all[0]+"."+dnsbl);
		if(last<0)
			return false;
		return true;
		
	}
	

}
