package com.bilgidoku.rom.dmarc;

import java.util.Collection;

import com.bilgidoku.rom.dns.DnsImplement;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;
import com.google.common.net.InternetDomainName;

public class DmarcHandler {

	public DmarcHandler() {
	}

	public DmarcResponse auth(String host, Boolean spf, Boolean dkim) {
		try {
			DmarcResource dr = get(host);
			if (dr == null)
				return null;
			return dr.auth(spf, dkim);

		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Dmarc auth failed:" + host + " spf:" + spf + " dkim:" + dkim);
			return null;
		}
	}

	private DmarcResource get(String host) throws KnownError {

		InternetDomainName h = InternetDomainName.from(host);
		boolean sd = false;
		do {

			Collection<String> c = DnsImplement.one().findTXTRecords("_dmarc." + h.toString());
			if (c != null && c.size() > 0) {
				return res(sd, c.iterator().next());
			}
			sd = true;
			if (!h.hasParent())
				return null;
			h = h.parent();

		} while (true);

	}

	private DmarcResource res(boolean isSubResource, String resource) throws KnownError {
		return new DmarcResource(isSubResource, resource);
	}

}
