package com.bilgidoku.rom.ilk.util;


public final class HostingUtils {
	
//	public static final String SITE_SUFFIX = "www";
//
//	public static String contentPath(String directoryPath, int hostId,
//			String hostSuffix, String uri) {
//		return directoryPath+"hosts/"+hostId+"/"+hostSuffix+uri;
//	}
//	
//	public static String contentDir(String directoryPath, int hostId,
//			String hostSuffix) {
//		return directoryPath+"hosts/"+hostId+"/"+hostSuffix;
//	}
	
	public static int hostIdIntra(int hostId){
		if ( hostId % 2 == 0 ){
			return hostId-1;
		}
		return hostId;
	}

	public static int hostIdInter(int hostId){
		if ( hostId % 2 == 0 ){
			return hostId;
		}
		return hostId+1;
	}
	
	public static boolean isIntra(int hostId){
		return (hostId % 2) != 0;
	}

}
