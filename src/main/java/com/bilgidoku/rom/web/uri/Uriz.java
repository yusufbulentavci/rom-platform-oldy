package com.bilgidoku.rom.web.uri;

import io.netty.handler.codec.http.HttpMethod;

public class Uriz {
	private static final String WRITING_PREFIX = "/_/writings";
	public String prefix;
	public String resource;
	public String method;
	int count = 0;
	public boolean service = false;
	public boolean file = false;
	public boolean iscontainer = false;
	public boolean writing = false;
	private HttpMethod httpMethod;
	
	
	@Override
	public String toString() {
		return "pre:"+prefix+" res:"+resource+" meth:"+method+" s:"+service+" f:"+file+" c:"+iscontainer+" w:"+writing;
	}
	
	/**
	 * @param args
	 */
	public Uriz(String path, HttpMethod httpMethod, String emailDomain) {
		this.httpMethod=httpMethod;
		byte[] pb = path.getBytes();
		
		if (pb.length < 2) {
			prefix = WRITING_PREFIX;
			resource = "/";
			writing = true;
			return;
		}

		if (path.equals("/sitemap.xml")) {
			this.prefix = "/_info";
			this.resource = "/";
			this.method = "sitemap";
			this.service = true;
			return;
		} else if (path.equals("/robots.txt")) {
			this.prefix = "/_info";
			this.resource = "/";
			this.method = "robots";
			this.service = true;
			return;
		}else if(path.equals("/"+emailDomain+".html")){
			this.prefix = "/_info";
			this.resource = "/";
			this.method = "startssl";
			this.service = true;
			return;
		}
		
		

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
		
		if (endOfResource==0) {
			prefix = WRITING_PREFIX;
			resource = "/";
			writing = true;
			return;
		}

		if (pb[endOfResource - 1] == '/') {
			endOfResource--;
		}
		
		int[] ind = new int[20];
		
		int i;
		for (i = 1; i < endOfResource; i++) {
			byte b = pb[i];
			if (b == '/') {
				ind[count++] = i;
			}
		}

		if (pb[endOfResource - 1] != '/') {
			ind[count++] = endOfResource;
		}
		
		
//		String first=substring(path, ind[0]);
		String pre = substring(path, ind[0]);
		
		if (pb[1] == 'f' && pb.length > 2 && pb[2] == '/') {
			prefix="/f";
			file = true;
		}else if(UriGorevlisi.tek().isService(pre)){
			service=true;
			prefix=pre;
			if (method == null)
				method = "get";
			
			resource = ind[0] == endOfResource ? "/" : path.substring(ind[0], endOfResource);
			return;
		}else if(count>1 && pre.equals("/_")){
			prefix=substring(path, ind[1]);
		}else{
			writing=true;
			prefix="/_/writings";
		}
		
		this.resource = substring(path, endOfResource);

	}

	private String substring(String path, int ind) {
		if (ind >= path.length()) {
			return path;
		}
		return path.substring(0, ind);
	}

	public void isContainer(boolean isContainer) {
		this.iscontainer=isContainer;
		
		if(method!=null)
			return;
		
		if(!iscontainer){
			if (httpMethod == HttpMethod.GET)
				method = "get";
			else if (httpMethod.equals(HttpMethod.POST))
				method = "change";
			else if (httpMethod == HttpMethod.DELETE)
				method = "destroy";
			return;
		}
		
		if (httpMethod == HttpMethod.GET || httpMethod == HttpMethod.HEAD)
			method = "list";
		else if (httpMethod == HttpMethod.DELETE)
			method = "extinct";
		else if (httpMethod.equals(HttpMethod.POST))
			method = "new";
	}

	public boolean hasResource() {
		return !service || file;
	}
}
