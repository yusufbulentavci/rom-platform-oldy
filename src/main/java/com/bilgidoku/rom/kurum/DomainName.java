package com.bilgidoku.rom.kurum;

import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.shared.err.KnownError;
import com.google.common.net.InternetDomainName;

public class DomainName {
	public static final String freeDomain = "enjoy-tlos.net";
	public static final String freeDomainDot = ".enjoy-tlos.net";

	private final String fullDomainName;
	private final String topLevelName;

	private boolean home = false;
	private boolean www = false;
	private String unknownSub = null;

	private RomDomain romDomain;
	private int hostId = -1;
	private boolean emailBanned;

	public DomainName(String domainName) throws KnownError {
		this.fullDomainName = domainName;
		InternetDomainName i = InternetDomainName.from(domainName);
		Assert.beTrue(i.hasPublicSuffix());

		// int topSize = 0;
		if (domainName.endsWith(freeDomainDot) && hasHostId()) {
			topLevelName = "host" + hostId + freeDomainDot;
			// topSize = 3;
			this.home = true;
			this.emailBanned = true;
		} else {
			InternetDomainName top = i.topPrivateDomain();
			topLevelName = top.toString();
			this.emailBanned = false;
		}

		int ignoreTopLevel = countDots(this.getTopLevelName()) + 1;

		for (int j = 0; j < i.parts().size() - ignoreTopLevel; j++) {
			String par = i.parts().get(j);
			if (par.equals("www")) {
				www = true;
			} else if (par.equals("home")) {
				this.home = true;
			} else {
				this.unknownSub = par;
			}
		}

	}

	private int countDots(String topLevelName2) {
		int ret = 0;
		for (int i = 0; i < topLevelName2.length(); i++) {
			if (topLevelName2.charAt(i) == '.')
				ret++;
		}
		return ret;
	}

	private boolean hasHostId() {
		String last = fullDomainName.substring(0, fullDomainName.length() - freeDomainDot.length());

		int host = last.indexOf("host");
		if (host < 0)
			return false;

		String num = last.substring(host + "host".length());

		Integer n = Integer.parseInt(num);
		hostId = n;

		return true;
	}

	public RomDomain getRomDomain() {
		return romDomain;
	}

	public final String getTopLevelName() {
		return topLevelName;
	}

	public final boolean isHome() {
		return home;
	}

	public final boolean isWww() {
		return www;
	}

	public final String getUnknownSub() {
		return unknownSub;
	}

	public void romDomain(int hid, String exactEmailDomain, boolean forceHttps) {
		if (!home)
			hid++;

		this.romDomain = new RomDomain(home, fullDomainName, topLevelName, emailBanned, !isHome(), exactEmailDomain,
				hid, forceHttps);
	}

	public boolean error() {
		return unknownSub != null;
	}

	public String getDomainName() {
		return fullDomainName;
	}

	public int getHostId() {
		return hostId;
	}

	public static String strip(String domainName) {
		try {
			InternetDomainName i = InternetDomainName.from(domainName);
			i.hasPublicSuffix();
			return i.topPrivateDomain().toString();
		} catch (Exception e) {
			return null;
		}
	}

}
