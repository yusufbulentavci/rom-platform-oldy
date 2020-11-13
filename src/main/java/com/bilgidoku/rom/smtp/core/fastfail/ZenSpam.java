package com.bilgidoku.rom.smtp.core.fastfail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class ZenSpam {
	
	private static InetAddress spam=safeAddress("127.0.0.2");
	private static InetAddress snowShow=safeAddress("127.0.0.3");
	private static InetAddress proxy4=safeAddress("127.0.0.4");
	private static InetAddress proxy5=safeAddress("127.0.0.5");
	private static InetAddress proxy6=safeAddress("127.0.0.6");
	private static InetAddress proxy7=safeAddress("127.0.0.7");
	private static InetAddress pb10=safeAddress("127.0.0.10");
	private static InetAddress pb11=safeAddress("127.0.0.11");

	private static final InetAddress[] addr={spam,snowShow,proxy4,proxy5,proxy6,proxy7,pb10, pb11};
	private static final ServerAttitude[] ats={ServerAttitude.SPAM,ServerAttitude.SNOWSHOE,ServerAttitude.PROXY,ServerAttitude.PROXY,ServerAttitude.PROXY,ServerAttitude.PROXY,ServerAttitude.POLICYBLOCK, ServerAttitude.POLICYBLOCK};
	
	
	
	public static ServerAttitude learn(String ip){
		
		try {
			ip=reverse(ip);
			InetAddress ret = InetAddress.getByName(ip+".zen.spamhaus.org");
			for(int i=0; i<addr.length; i++){
				if(addr[i].equals(ret)){
					return ats[i];
				}
			}
			System.err.println("Zen spam unknown result: "+ip+" result:"+ret.toString());
			return ServerAttitude.UNKNOWN;
		} catch (UnknownHostException e) {
			return ServerAttitude.NOTLISTED;
		}
	}

	
	private static String reverse(String ipAddress) {
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(ipAddress, " .", false);
		while (st.hasMoreTokens()) {
			sb.insert(0, st.nextToken() + ".");
		}
		return sb.toString();
	}


	private static InetAddress safeAddress(String safeIp){
		try {
			return InetAddress.getByName(safeIp);
		} catch (UnknownHostException e) {
			throw new RuntimeException(safeIp, e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("REt:"+ZenSpam.learn("192.168.2.2"));
	}
}
