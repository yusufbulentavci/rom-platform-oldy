package com.bilgidoku.rom.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Cache;
import org.xbill.DNS.Credibility;
import org.xbill.DNS.DClass;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.PTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ResolverConfig;
import org.xbill.DNS.ReverseMap;
import org.xbill.DNS.SPFRecord;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.ErrEffect;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.RunException;

public class DnsGorevlisi extends GorevliDir {
public static final int NO=0;
	
	public static DnsGorevlisi tek(){
		if(tek==null) {
			synchronized (DnsGorevlisi.class) {
				if(tek==null) {
					tek=new DnsGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static DnsGorevlisi tek;
	private DnsGorevlisi() {
		super("Dns", NO);
	}

	
	private boolean autodiscover;
	private String[] servers;
	private String[] searchPaths;
	private boolean authoritative;
	private int maxcachesize;

	// The record limit for lookups
	protected int recordLimit;
	/**
	 * A resolver instance used to retrieve DNS records. This is a reference to
	 * a third party library object.
	 */
	protected Resolver resolver;
	/**
	 * A TTL cache of results received from the DNS server. This is a reference
	 * to a third party library object.
	 */
	protected Cache cache;
	/**
	 * Whether the DNS response is required to be authoritative
	 */
	private int dnsCredibility;

	/**
	 * The DNS servers to be used by this service
	 */
	private List<String> dnsServers = new ArrayList<String>();

	/**
	 * The search paths to be used
	 */
	private Name[] searchNames = null;

	/**
	 * The MX Comparator used in the MX sort.
	 */
	private Comparator<MXRecord> mxComparator = new MXRecordComparator();
	private String localHostName;
	private String localCanonicalHostName;
	private String localAddress;
	
	@Override
	protected void kur() throws KnownError {
		autodiscover = true;
		servers = new String[0];
		searchPaths = new String[0];
		authoritative = false;
		maxcachesize = 5000;

		List<Name> sPaths = new ArrayList<Name>();
		if (autodiscover) {
			String[] serversArray = ResolverConfig.getCurrentConfig().servers();
			if (serversArray != null) {
				for (int i = 0; i < serversArray.length; i++) {
					dnsServers.add(serversArray[i]);
				}
			}
			Name[] systemSearchPath = ResolverConfig.getCurrentConfig().searchPath();
			if (systemSearchPath != null && systemSearchPath.length > 0) {
				sPaths.addAll(Arrays.asList(systemSearchPath));
			}

			for (Iterator<Name> i = sPaths.iterator(); i.hasNext();) {
				Name searchPath = i.next();
			}
		}
		

		for (int i = 0; i < servers.length; i++) {
			dnsServers.add(servers[i]);
		}

		// Get the DNS servers that this service will use for lookudddps

		for (int i = 0; i < searchPaths.length; i++) {
			try {
				sPaths.add(Name.fromString(searchPaths[i]));
			} catch (TextParseException e) {
				throw new RunException(ErrEffect.dns(), "Search path parsing failed:" + searchPaths[i]);
			}
		}

		searchNames = (Name[]) sPaths.toArray(new Name[0]);

		if (dnsServers.isEmpty()) {
			throw new RunException(ErrEffect.dns(), "no dns server");
		}

		// TODO: Check to see if the credibility field is being used correctly.
		// From the
		// docs I don't think so
		dnsCredibility = authoritative ? Credibility.AUTH_ANSWER : Credibility.NONAUTH_ANSWER;

		// Create the extended resolver...
		final String[] serversArray = (String[]) dnsServers.toArray(new String[0]);

		try {
			resolver = new ExtendedResolver(serversArray);
		} catch (UnknownHostException uhe) {
			throw new RunException(ErrEffect.dns(), "Unknown host");
		}

		cache = new Cache(DClass.IN);
		cache.setMaxEntries(maxcachesize);

		Lookup.setDefaultResolver(resolver);
		Lookup.setDefaultCache(cache, DClass.IN);
		try {
			Lookup.setDefaultSearchPath(searchPaths);
		} catch (TextParseException e) {
			throw new RunException(ErrEffect.dns(), "Search path parsing failed:" + searchPaths);
		}

		// Cache the local hostname and local address. This is needed because
		// the following issues:
		// JAMES-787
		// JAMES-302
		InetAddress addr = null;
		try {
			addr = getLocalHost();
			localCanonicalHostName = addr.getCanonicalHostName();
			localHostName = addr.getHostName();
			localAddress = addr.getHostAddress();
		} catch (UnknownHostException e) {
			throw new RunException(ErrEffect.dns(), "Unknown host" + addr == null ? "" : addr.getHostName());
		}

	}

	@Override
	protected void bitir(boolean dostca) {
	}

	// @Override
	// public void selfDescribe(JSONObject jo) {
	// jo.safePut("cacheSize", cache.getSize());
	// }
	//
	//

	public Collection<String> findMXRecords(String hostname) throws TemporaryResolutionException {
		List<String> servers = new ArrayList<String>();
		try {
			servers = findMXRecordsRaw(hostname);
			return Collections.unmodifiableCollection(servers);
		} finally {
			// If we found no results, we'll add the original domain name if
			// it's a valid DNS entry
			if (servers.size() == 0) {
//				failMxRecords.more();
				try {
					getByName(hostname);
					servers.add(hostname);
				} catch (UnknownHostException uhe) {
					Sistem.printStackTrace(uhe);
					// The original domain name is not a valid host,
					// so we can't add it to the server list. In this
					// case we return an empty list of servers
//					failOrigHost.more();
				}
			}
		}
	}

//	private static final Astate findTXTRecords = mc.c("dns-find-txt-records");


	public Collection<String> findTXTRecords(String hostname) {
//		findTXTRecords.more();
		List<String> txtR = new ArrayList<String>();
		Record[] records = lookupNoException(hostname, Type.TXT, "TXT");
		if (records != null) {
			for (int i = 0; i < records.length; i++) {
				TXTRecord txt = (TXTRecord) records[i];
				txtR.add(txt.rdataToString());
			}

		}
		return txtR;
	}

//	private static final Astate getAllByName = mc.c("dns-get-all-by-name");
//	private static final Astate getAllByNameRE = mc.c("dns-get-all-by-name-unknown-host-recovable-error");
//	private static final Astate getAllByNameError = mc.c("dns-get-all-by-name-unknown-host-error");


	public InetAddress[] getAllByName(String host) throws UnknownHostException {
//		getAllByName.more();
		String name = allowIPLiteral(host);
		try {
			// Check if its local
			if (name.equalsIgnoreCase(localHostName) || name.equalsIgnoreCase(localCanonicalHostName)
					|| name.equals(localAddress)) {
				return new InetAddress[] { getLocalHost() };
			}

			InetAddress addr = org.xbill.DNS.Address.getByAddress(name);
			return new InetAddress[] { addr };
		} catch (UnknownHostException e) {
			Record[] records = lookupNoException(name, Type.A, "A");

			if (records != null && records.length >= 1) {
				InetAddress[] addrs = new InetAddress[records.length];
				for (int i = 0; i < records.length; i++) {
					ARecord a = (ARecord) records[i];
					addrs[i] = InetAddress.getByAddress(name, a.getAddress().getAddress());
				}
//				getAllByNameRE.more();
				return addrs;
			} else {
//				getAllByNameError.more();
				throw e;
			}

		}
	}

//	private static final Astate getByName = mc.c("dns-get-by-name");
//	private static final Astate getByNameRE = mc.c("dns-get-by-name-recorable-error");
//	private static final Astate getByNameE = mc.c("dns-get-by-name-error");


	public InetAddress getByName(String host) throws UnknownHostException {
//		getByName.more();
		String name = allowIPLiteral(host);

		try {
			// Check if its local
			if (name.equalsIgnoreCase(localHostName) || name.equalsIgnoreCase(localCanonicalHostName)
					|| name.equals(localAddress)) {
				return getLocalHost();
			}

			return org.xbill.DNS.Address.getByAddress(name);
		} catch (UnknownHostException e) {
			Record[] records = lookupNoException(name, Type.A, "A");

			if (records != null && records.length >= 1) {
				ARecord a = (ARecord) records[0];
//				getByNameRE.more();
				return InetAddress.getByAddress(name, a.getAddress().getAddress());
			} else {
//				getByNameE.more();
				throw e;
			}
		}
	}


	public InetAddress getLocalHost() throws UnknownHostException {
		return InetAddress.getLocalHost();
	}

//	private static final Astate getHostName = mc.c("dns-host-name");


	public String getHostName(InetAddress addr) {
//		getHostName.more();
		String result = null;
		Name name = ReverseMap.fromAddress(addr);
		Record[] records = lookupNoException(name.toString(), Type.PTR, "PTR");

		if (records == null) {
			result = addr.getHostAddress();
		} else {
			PTRRecord ptr = (PTRRecord) records[0];
			result = ptr.getTarget().toString();
		}
		return result;
	}

	/**
	 * Return the list of DNS servers in use by this service
	 * 
	 * @return an array of DNS server names
	 */
	public String[] getDNSServers() {
		return (String[]) dnsServers.toArray(new String[0]);
	}

	/**
	 * Return the list of DNS servers in use by this service
	 * 
	 * @return an array of DNS server names
	 */
	public Name[] getSearchPaths() {
		return searchNames;
	}

	/**
	 * Return a prioritized unmodifiable list of MX records obtained from the
	 * server.
	 * 
	 * @param hostname
	 *            domain name to look up
	 * 
	 * @return a list of MX records corresponding to this mail domain
	 * @throws TemporaryResolutionException
	 *             get thrown on temporary problems
	 */
	private List<String> findMXRecordsRaw(String hostname) throws TemporaryResolutionException {
		Record answers[] = lookup(hostname, Type.MX, "MX");
		List<String> servers = new ArrayList<String>();
		if (answers == null) {
			return servers;
		}

		MXRecord[] mxAnswers = new MXRecord[answers.length];

		for (int i = 0; i < answers.length; i++) {
			mxAnswers[i] = (MXRecord) answers[i];
		}
		// just sort for now.. This will ensure that mx records with same prio
		// are in sequence
		Arrays.sort(mxAnswers, mxComparator);

		// now add the mx records to the right list and take care of shuffle
		// mx records with the same priority
		int currentPrio = -1;
		List<String> samePrio = new ArrayList<String>();
		for (int i = 0; i < mxAnswers.length; i++) {
			boolean same = false;
			boolean lastItem = i + 1 == mxAnswers.length;
			MXRecord mx = mxAnswers[i];
			if (i == 0) {
				currentPrio = mx.getPriority();
			} else {
				if (currentPrio == mx.getPriority()) {
					same = true;
				} else {
					same = false;
				}
			}

			String mxRecord = mx.getTarget().toString();
			if (same) {
				samePrio.add(mxRecord);
			} else {
				// shuffle entries with same prio
				// JAMES-913
				Collections.shuffle(samePrio);
				servers.addAll(samePrio);

				samePrio.clear();
				samePrio.add(mxRecord);

			}

			if (lastItem) {
				// shuffle entries with same prio
				// JAMES-913
				Collections.shuffle(samePrio);
				servers.addAll(samePrio);
			}
		}
		return servers;
	}

//	private static final Astate lookuperror = mc.c("lookup-error");
//	private static final Astate lookuperroris = mc.c("lookup-illegal-state-error");
//	private static final Astate lookuperrortp = mc.c("lookup-error-text-parse");

	/**
	 * Looks up DNS records of the specified type for the specified name.
	 * 
	 * This method is a public wrapper for the private implementation method
	 * 
	 * @param namestr
	 *            the name of the host to be looked up
	 * @param type
	 *            the type of record desired
	 * @param typeDesc
	 *            the description of the record type, for debugging purpose
	 */
	protected Record[] lookup(String namestr, int type, String typeDesc) throws TemporaryResolutionException {
		// Name name = null;
		try {
			// name = Name.fromString(namestr, Name.root);
			Lookup l = new Lookup(namestr, type);

			l.setCache(cache);
			l.setResolver(resolver);
			l.setCredibility(dnsCredibility);
			l.setSearchPath(searchPaths);
			Record[] r = l.run();

			try {
				if (l.getResult() == Lookup.TRY_AGAIN) {
//					lookuperror.more();
					throw new TemporaryResolutionException("DNSService is temporary not reachable");
				} else {
					return r;
				}
			} catch (IllegalStateException ise) {
				// This is okay, because it mimics the original behaviour
				// TODO find out if it's a bug in DNSJava
//				lookuperroris.more();
				throw new TemporaryResolutionException("DNSService is temporary not reachable");
			}

			// return rawDNSLookup(name, false, type, typeDesc);
		} catch (TextParseException tpe) {
			// TODO: Figure out how to handle this correctly.
//			lookuperrortp.more();
			Sistem.printStackTrace(tpe);
			return null;
		}
	}

	protected Record[] lookupNoException(String namestr, int type, String typeDesc) {
		try {
			return lookup(namestr, type, typeDesc);
		} catch (TemporaryResolutionException e) {
			return null;
		}
	}

	/*
	 * RFC 2821 section 5 requires that we sort the MX records by their
	 * preference. Reminder for maintainers: the return value on a Comparator
	 * can be counter-intuitive for those who aren't used to the old C strcmp
	 * function:
	 * 
	 * < 0 ==> a < b = 0 ==> a = b > 0 ==> a > b
	 */
	private static class MXRecordComparator implements Comparator<MXRecord> {
		public int compare(MXRecord a, MXRecord b) {
			int pa = a.getPriority();
			int pb = b.getPriority();
			return pa - pb;
		}
	}

	/*
	 * java.net.InetAddress.get[All]ByName(String) allows an IP literal to be
	 * passed, and will recognize it even with a trailing '.'. However,
	 * org.xbill.DNS.Address does not recognize an IP literal with a trailing
	 * '.' character. The problem is that when we lookup an MX record for some
	 * domains, we may find an IP address, which will have had the trailing '.'
	 * appended by the time we get it back from dnsjava. An MX record is not
	 * allowed to have an IP address as the right-hand-side, but there are still
	 * plenty of such records on the Internet. Since java.net.InetAddress can
	 * handle them, for the time being we've decided to support them.
	 * 
	 * These methods are NOT intended for use outside of James, and are NOT
	 * declared by the org.apache.james.services.DNSServer. This is currently a
	 * stopgap measure to be revisited for the next release.
	 */

	private static String allowIPLiteral(String host) {
		if ((host.charAt(host.length() - 1) == '.')) {
			String possible_ip_literal = host.substring(0, host.length() - 1);
			if (org.xbill.DNS.Address.isDottedQuad(possible_ip_literal)) {
				host = possible_ip_literal;
			}
		}
		return host;
	}

	/**
	 * @see org.apache.james.dnsservice.api.DNSServiceMBean#getMaximumCacheSize()
	 */
	public int getMaximumCacheSize() {
		return maxcachesize;
	}

	/**
	 * @see org.apache.james.dnsservice.api.DNSServiceMBean#getCurrentCacheSize()
	 */
	public int getCurrentCacheSize() {
		return cache.getSize();
	}

	/**
	 * @see org.apache.james.dnsservice.api.DNSServiceMBean#clearCache()
	 */
	public void clearCache() {
		cache.clearCache();
	}

	/**
	 * NOTE if this class is created with the default constructor it will use
	 * the static DefaultResolver from DNSJava and this method will change it's
	 * timeout. Other tools using DNSJava in the same JVM could be affected by
	 * this timeout change.
	 * 
	 * @see org.apache.james.jspf.core.DNSService#setTimeOut(int)
	 */
	public synchronized void setTimeOut(int timeOut) {
		this.resolver.setTimeout(timeOut);
	}

	/**
	 * @see org.apache.james.jspf.core.DNSService#getLocalDomainNames()
	 */
	public List<String> getLocalDomainNames() {
		List<String> names = new ArrayList<String>();

		// log.debug("Start Local ipaddress lookup");
		try {
			InetAddress ia[] = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());

			for (int i = 0; i < ia.length; i++) {
				String host = ia[i].getHostName();
				names.add(host);

				// log.debug("Add hostname " + host + " to list");
			}
		} catch (UnknownHostException e) {
			// just ignore this..
		}
		return names;

	}

	/**
	 * @return the current record limit
	 */
	public int getRecordLimit() {
		return recordLimit;
	}

	/**
	 * Set a new limit for the number of records for MX and PTR lookups.
	 * 
	 * @param recordLimit
	 */
	public void setRecordLimit(int recordLimit) {
		this.recordLimit = recordLimit;
	}

	/**
	 * @see org.apache.james.jspf.core.DNSService#getRecords(com.coreks.rom.dns.james.jspf.core.DNSRequest)
	 */
	public List<String> getRecords(DNSRequest request) throws TimeoutException {
		String recordTypeDescription;
		int dnsJavaType;
		switch (request.getRecordType()) {
		case DNSRequest.A:
			recordTypeDescription = "A";
			dnsJavaType = Type.A;
			break;
		case DNSRequest.AAAA:
			recordTypeDescription = "AAAA";
			dnsJavaType = Type.AAAA;
			break;
		case DNSRequest.MX:
			recordTypeDescription = "MX";
			dnsJavaType = Type.MX;
			break;
		case DNSRequest.PTR:
			recordTypeDescription = "PTR";
			dnsJavaType = Type.PTR;
			break;
		case DNSRequest.TXT:
			recordTypeDescription = "TXT";
			dnsJavaType = Type.TXT;
			break;
		case DNSRequest.SPF:
			recordTypeDescription = "SPF";
			dnsJavaType = Type.SPF;
			break;
		default: // TODO fail!
			return null;
		}
		try {

			// log.debug("Start "+recordTypeDescription+"-Record lookup for : "
			// + request.getHostname());

			Lookup query = new Lookup(request.getHostname(), dnsJavaType);
			query.setResolver(resolver);

			Record[] rr = query.run();
			int queryResult = query.getResult();

			if (queryResult == Lookup.TRY_AGAIN) {
				throw new TimeoutException(query.getErrorString());
			}

			List<String> records = convertRecordsToList(rr);

			// log.debug("Found " + (rr != null ? rr.length : 0) +
			// " "+recordTypeDescription+"-Records");
			return records;
		} catch (TextParseException e) {
			// i think this is the best we could do
			// log.debug("No "+recordTypeDescription+" Record found for host: "
			// + request.getHostname());
			return null;
		}
	}

	/**
	 * Convert the given Record array to a List
	 * 
	 * @param rr
	 *            Record array
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static List<String> convertRecordsToList(Record[] rr) {
		List<String> records;
		if (rr != null && rr.length > 0) {
			records = new ArrayList<String>();
			for (int i = 0; i < rr.length; i++) {
				switch (rr[i].getType()) {
				case Type.A:
					ARecord a = (ARecord) rr[i];
					records.add(a.getAddress().getHostAddress());
					break;
				case Type.AAAA:
					AAAARecord aaaa = (AAAARecord) rr[i];
					records.add(aaaa.getAddress().getHostAddress());
					break;
				case Type.MX:
					MXRecord mx = (MXRecord) rr[i];
					records.add(mx.getTarget().toString());
					break;
				case Type.PTR:
					PTRRecord ptr = (PTRRecord) rr[i];
					records.add(IPAddr.stripDot(ptr.getTarget().toString()));
					break;
				case Type.TXT:
					TXTRecord txt = (TXTRecord) rr[i];
					if (txt.getStrings().size() == 1) {
						records.add((String) txt.getStrings().get(0));
					} else {
						StringBuffer sb = new StringBuffer();
						for (Iterator<String> it = txt.getStrings().iterator(); it.hasNext();) {
							String k = (String) it.next();
							sb.append(k);
						}
						records.add(sb.toString());
					}
					break;
				case Type.SPF:
					SPFRecord spf = (SPFRecord) rr[i];
					if (spf.getStrings().size() == 1) {
						records.add((String) spf.getStrings().get(0));
					} else {
						StringBuffer sb = new StringBuffer();
						for (Iterator<String> it = spf.getStrings().iterator(); it.hasNext();) {
							String k = (String) it.next();
							sb.append(k);
						}
						records.add(sb.toString());
					}
					break;
				default:
					return null;
				}
			}
		} else {
			records = null;
		}
		return records;
	}


	public String getTXTRecord(String key) {
		Collection<String> cs=findTXTRecords(key);
		if(cs==null || cs.size()==0)
			return null;
		
		for (String string : cs) {
			return string;
		}
		return null;
	}


	public String[] getAppServices(String name) {
		String s=getTXTRecord("_app_"+name+".rock-server.net");
		if(s==null)
			return new String[0];
		return s.split(",");
	}
	

}
