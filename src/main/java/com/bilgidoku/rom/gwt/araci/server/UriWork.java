package com.bilgidoku.rom.gwt.araci.server;
//uriwork
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.pg.dict.*;
import com.bilgidoku.rom.type.UriWorkBase;

public final class UriWork implements UriWorkBase{
	private static final Map<String,Net> nets=new HashMap<String,Net>();
	private static final Map<String,UriHierarychy> hiers=new HashMap<String,UriHierarychy>();
	private static final Set<String> services=new HashSet<String>();

	static {
		//uriworkitem
		nets.put("/_/tariffmodel",Net.INTRA);
		hiers.put("/_/tariffmodel",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_comments",Net.INTRA);
		hiers.put("/_/_comments",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/waiting",Net.INTRA);
		hiers.put("/_/waiting",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_org",Net.INTRA);
		hiers.put("/_/_org",UriHierarychy.ONE);
		//uriworkitem
		nets.put("/_/_resources",Net.INTRA);
		hiers.put("/_/_resources",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/widgets",Net.ALL);
		hiers.put("/_/widgets",UriHierarychy.ONE);
		//uriworkitem
		nets.put("/_/issues",Net.INTRA);
		hiers.put("/_/issues",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_dialogs",Net.INTRA);
		hiers.put("/_/_dialogs",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_stocks",Net.INTRA);
		hiers.put("/_/_stocks",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/tags",Net.INTRA);
		hiers.put("/_/tags",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/mails",Net.INTRA);
		hiers.put("/_/mails",UriHierarychy.HSC);
		//uriworkitem
		nets.put("/_/measure",Net.INTRA);
		hiers.put("/_/measure",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/c",Net.ALL);
		hiers.put("/_/c",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/styles",Net.ALL);
		hiers.put("/_/styles",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_tokens",Net.INTRA);
		hiers.put("/_/_tokens",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/co",Net.INTRA);
		hiers.put("/_/co",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/apps",Net.ALL);
		hiers.put("/_/apps",UriHierarychy.HSC);
		//uriworkitem
		nets.put("/_/_trans",Net.ALL);
		hiers.put("/_/_trans",UriHierarychy.ONE);
		//uriworkitem
		nets.put("/_/menu",Net.INTRA);
		hiers.put("/_/menu",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_hits",Net.INTRA);
		hiers.put("/_/_hits",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/exams",Net.INTRA);
		hiers.put("/_/exams",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/writings",Net.ALL);
		hiers.put("/_/writings",UriHierarychy.HSC);
		//uriworkitem
		nets.put("/_/_contents",Net.INTRA);
		hiers.put("/_/_contents",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/lists",Net.ALL);
		hiers.put("/_/lists",UriHierarychy.HSC);
		//uriworkitem
		nets.put("/_/questions",Net.INTRA);
		hiers.put("/_/questions",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/f",Net.ALL);
		hiers.put("/f",UriHierarychy.HSC);
		//uriworkitem
		nets.put("/_/links",Net.ALL);
		hiers.put("/_/links",UriHierarychy.HSC);
		//uriworkitem
		nets.put("/_/households",Net.ALL);
		hiers.put("/_/households",UriHierarychy.HSC);
		//uriworkitem
		nets.put("/_/_cart",Net.INTRA);
		hiers.put("/_/_cart",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/siteinfo",Net.ALL);
		hiers.put("/_/siteinfo",UriHierarychy.ONE);
		//uriworkitem
		nets.put("/_/usersites",Net.INTRA);
		hiers.put("/_/usersites",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_medias",Net.INTRA);
		hiers.put("/_/_medias",UriHierarychy.SINGLE);
		//uriworkitem
		nets.put("/_/_initials",Net.INTRA);
		hiers.put("/_/_initials",UriHierarychy.ONE);
		//uriworkitem
		nets.put("/_/assets",Net.INTRA);
		hiers.put("/_/assets",UriHierarychy.SINGLE);

		//uriworkservice
		services.add("/_analytics");
		//uriworkservice
		services.add("/_auth");
		//uriworkservice
		services.add("/f");
		//uriworkservice
		services.add("/_local");
		//uriworkservice
		services.add("/_public");
		//uriworkservice
		services.add("/_rm");
		//uriworkservice
		services.add("/_richweb");
		//uriworkservice
		services.add("/_static");
		//uriworkservice
		services.add("/_sesfuncs");
		//uriworkservice
		services.add("/_account");
		//uriworkservice
		services.add("/_admin");
		//uriworkservice
		services.add("/_audit");
		//uriworkservice
		services.add("/_guest");
		//uriworkservice
		services.add("/_info/");
		//uriworkservice
		services.add("/_dlgsrv");
		//uriworkservice
		services.add("/_welcome");

	}

	public Net getNetByPrefix(String pref){
		return nets.get(pref);
	}
	public UriHierarychy getHierByPrefix(String pref){
		return hiers.get(pref);
	}

	public boolean isService(String pref){
		return services.contains(pref);
	}
}
