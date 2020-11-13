package com.bilgidoku.rom.web.uri;

import com.bilgidoku.rom.pg.dict.UriHierarychy;
import com.bilgidoku.rom.type.UriWorkBase;

import io.netty.handler.codec.http.HttpMethod;

public class UriAnalyses {
	private static final String WRITING_PREFIX = "/_/writings";
	public String prefix;
	public String resource;
	public String method;
	int count = 0;
	int[] ind = new int[4];
	boolean hscItem = false;
	boolean service = false;
	boolean file = false;
	public boolean iscontainer = false;
	// public String container=null;
	public boolean writing = false;

	/**
	 * @param emailDomain 
	 * @param args
	 */
	public UriAnalyses(String path, HttpMethod httpMethod, UriWorkBase uriWork, String emailDomain) {

		byte[] pb = path.getBytes();
		if (pb.length < 2) {
			prefix = WRITING_PREFIX;
			resource = "/";
			hscItem = true;
			iscontainer = false;
			writing = true;
			setupMethod(httpMethod);
			return;
		}

		if (path.equals("/sitemap.xml")) {
			this.prefix = "/_info";
			this.resource = "/";
			this.method = "sitemap";
			this.hscItem = false;
			this.service = true;
			return;
		} else if (path.equals("/robots.txt")) {
			this.prefix = "/_info";
			this.resource = "/";
			this.method = "robots";
			this.hscItem = false;
			this.service = true;
			return;
		} else if(path.equals("/"+emailDomain+".html")){
			this.prefix = "/_info";
			this.resource = "/";
			this.method = "startssl";
			this.hscItem = false;
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

		if (pb[1] == 'f' && pb.length > 2 && pb[2] == '/') {
			file = true;
		} else if (pb[0] != '/' || pb[1] != '_') {
			prefix = WRITING_PREFIX;
			resource = substring(path, endOfResource);
			if (resource.equals(""))
				resource = "/";
			hscItem = true;
			writing = true;
			if (method == null)
				setupMethod(httpMethod);
			return;
		}

		if (pb[endOfResource - 1] == '/') {
			endOfResource--;
		}

		int i;
		for (i = 1; i < endOfResource; i++) {
			byte b = pb[i];
			if (b == '/') {
				ind[count++] = i;
			}
		}

		if (count < 4 && pb[endOfResource - 1] != '/') {
			ind[count++] = endOfResource;
		}

		if (!file && uriWork.isService(substring(path, ind[0]))) {
			prefix = substring(path, ind[0]);
			service = true;
			if (method == null)
				method = "get";
			resource = ind[0] == endOfResource ? "/" : path.substring(ind[0], endOfResource);
			file = false;
			return;
		}

		// if(cantknowcontainer){
		// if(count==1)
		// return;
		// cantknowcontainer=false;
		// return;
		// }

		if (!file && count == 2 && pb.length > 2 && pb[2] != '/') {
			prefix = substring(path, ind[0]);
			resource = substring(path, ind[1]);
			method = substring(path, ind[0] + 1, ind[1]);
			service = true;
			return;
		}

		this.resource = substring(path, endOfResource);

		if (file) {
			iscontainer = resource.indexOf('.') < 0;
			hscItem = !iscontainer;

			// if (count == 3) {
			// hscItem = true;
			// } else {
			// iscontainer = true;
			// // container=resource;
			// }
		} else {
			if (count == 4) {
				hscItem = true;
			}
		}

		// if(count==1){
		// // Writing
		//
		// return;
		// }

		if (file) {
			prefix = substring(path, ind[0]);
		} else {
			prefix = substring(path, ind[1]);
		}

		if (!service)
			setHier(uriWork.getHierByPrefix(prefix), httpMethod);
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

	private void setHier(UriHierarychy hier, HttpMethod hm) {
		if (file) {
			if (method == null) {

				if (iscontainer) {
					if (hm == HttpMethod.GET || hm == HttpMethod.HEAD)
						method = "list";
					else if (hm == HttpMethod.DELETE)
						method = "extinct";
					else if (hm.equals(HttpMethod.POST))
						method = "new";
				} else {

					if (hm == HttpMethod.GET || hm == HttpMethod.HEAD)
						method = "get";
					else if (hm == HttpMethod.DELETE)
						method = "destroy";
					else if (hm.equals(HttpMethod.POST))
						method = "change";

				}
			}

		}
		switch (hier) {
		case HSC:
			isHsc(hm);
			break;
		case SINGLE:
			isSingleContainer(hm);
			break;
		case ONE:
			isOne(hm);
			break;
		}
	}

	public void isOne(HttpMethod hm) {
		if (method == null) {
			setupMethod(hm);
		}
	}

	public void isHsc(HttpMethod hm) {
		if (!file && !writing) {
			if (count == 3) {
				iscontainer = true;
			}
		}

		if (method == null) {
			if (hscItem) {
				setupMethod(hm);
			} else {
				makeContainer(hm);
			}
		}

	}

	public void isSingleContainer(HttpMethod hm) {
		if (!file) {
			if (count == 2) {
				iscontainer = true;
			}
		}
		if (method == null) {
			if (count == 2) {
				makeContainer(hm);
			} else
				setupMethod(hm);
		}
	}

	private void setupMethod(HttpMethod hm) {
		if (hm == HttpMethod.GET || hm == HttpMethod.HEAD)
			method = "get";
		else if (hm == HttpMethod.DELETE)
			method = "destroy";
		else if (hm.equals(HttpMethod.POST))
			method = "change";
	}

	private void makeContainer(HttpMethod hm) {
		if (hm == HttpMethod.GET || hm == HttpMethod.HEAD)
			method = "list";
		else if (hm == HttpMethod.DELETE)
			method = "extinct";
		else if (hm.equals(HttpMethod.POST))
			method = "convulse";
	}

}
