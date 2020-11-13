package com.bilgidoku.rom.web.uri;

import io.netty.handler.codec.http.HttpMethod;

public class UriAnalysesDirect {
	public String prefix;
	public String resource;
	public String method;
	int count = 0;
	int[] ind = new int[4];
	boolean service = false;
	
//	private final String mime;
//	private final static ContentMatcherService matcher = ServiceDiscovery.getService(ContentMatcherService.class);

	/**
	 * @param args
	 */
	public UriAnalysesDirect(String path, HttpMethod httpMethod) {
		
//		this.mime=matcher.getMimeOfFile(path);
//		if(mime!=null){
//			resource=path;
//			return;
//		}

		
		// Find method
		// /_admun/hostlist.rom
		byte[] pb = path.getBytes();
		int endOfResource = pb.length;
		if (pb.length > 7) {
			if (pb[pb.length - 4] == '.' && pb[pb.length - 3] == 'r' && pb[pb.length - 2] == 'o'
					&& pb[pb.length - 1] == 'm') {
				for (int i = pb.length - 5; i >= 0; i--) {
					if (pb[i] == '/') {
						method = path.substring(i + 1, pb.length - 4);
						endOfResource = i;
						break;
					}
				}
			}
		}
		// method: hostlist
		// endofresouce: /_admun/
		// 

		// Resource
		if (pb[endOfResource - 1] == '/') {
			endOfResource--;
		}
		
		// endofresouce: /_admun

		int i;
		for (i = 1; i < endOfResource; i++) {
			byte b = pb[i];
			if (b == '/') {
				ind[count++] = i;
				if (count == 4) {
					break;
				}
			}
		}
		// count: 0
		
		if (count < 4 && pb[endOfResource - 1] != '/') {
			ind[count++] = endOfResource;
		}
		
//		if (count == 2 && pb.length > 2 && pb[2] != '/') {
			prefix = substring(path, ind[0]);
//			resource = substring(path, ind[1]);
			resource="/";
//			method = substring(path, ind[0] + 1, ind[1]);
			service = true;
//			return;
//		}
//
//		this.resource = substring(path, endOfResource);
//
//		prefix = substring(path, ind[1]);

	}

	private String substring(String path, int ind) {
		if (ind >= path.length()) {
			return path;
		}
		return path.substring(0, ind);
	}

	private String substring(String path, int from, int ind) {
		if (ind >= path.length()) {
			return path.substring(from);
		}
		return path.substring(from, ind);
	}

}
