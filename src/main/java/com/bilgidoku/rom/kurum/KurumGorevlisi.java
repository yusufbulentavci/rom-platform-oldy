package com.bilgidoku.rom.kurum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.shared.HostStat;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class KurumGorevlisi extends GorevliDir {
	public static final int NO = 12;

	public static KurumGorevlisi tek() {
		if (tek == null) {
			synchronized (KurumGorevlisi.class) {
				if (tek == null) {
					tek = new KurumGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static KurumGorevlisi tek;

	private KurumGorevlisi() {
		super("Kurum", NO);
	}

	// private static final CheckSumService check =
	// ServiceDiscovery.getService(CheckSumService.class);

	// private static final String contactStr = "{\"first_name\":\"Yusuf Bulent\","
	// + "\"last_name\":\"Avci\","
	// + "\"organization\":\"Bilgidoku Technology\","
	// + "\"email\":\"avci.yusuf@gmail.com\","
	// + "\"address\":\"Mimarsinan mahallesi, Eylem Sokak, No:6, Silivri\","
	// + "\"postal_code\":\"34570\","
	// + "\"country_code\":\"TR\","
	// + "\"city\":\"Istanbul\","
	// + "\"phone\":\"902127290677\","
	// + "\"fax\":\"\","
	// + "\"state\":\"\"}";

	// private final JSONObject contact;
	// private final String contactEmail;
	private final List<HostListener> hostListener = new ArrayList<HostListener>();

	// RunTime
	// .getService(YerelGorevlisi.tek().class);

	private static final MC mc = new MC(KurumGorevlisi.class);

	// DigestUtils;

	// private Map<Integer, RomHost> hosts;
	private boolean domainsInitialized = false;
	private Map<String, HostStat> hostsByAlias;
	private Map<Integer, HostStat> hostById;
	// private boolean central;

	@Override
	protected void kur() throws KnownError {
		loadHosts();
	}

	@Override
	protected void bitir(boolean dostca) {
	}

	public void selfDescribe(JSONObject jo) {
		jo.safePut("domainsinit", domainsInitialized).safePut("hostcount", hostsByAlias.size());
	}

	private void loadHosts() throws KnownError {
		HostStat[] h = hosts();
		Map<String, HostStat> ha = new HashMap<String, HostStat>();
		Map<Integer, HostStat> hi = new HashMap<Integer, HostStat>();
		for (HostStat hs : h) {
			hi.put(hs.hostid, hs);
			if (hs.hostname != null)
				ha.put(hs.hostname, hs);
			if (hs.domainalias != null) {
				for (String alias : hs.domainalias) {
					ha.put(alias, hs);
				}
			}
		}
		hostsByAlias = ha;
		hostById = hi;
		for (HostListener l : hostListener) {
			l.hosts(h);
		}
	}

	// private void synch(Map<String, RomDomain> original, Map<String, RomDomain>
	// change) {
	// List<String> tor = new ArrayList<String>();
	// for (Entry<String, RomDomain> it : original.entrySet()) {
	// RomDomain changed = change.get(it.getKey());
	// if (changed == null) {
	// tor.add(it.getKey());
	// } else {
	// it.getValue().synch(changed);
	// }
	// }
	// for (String key : tor) {
	// RomDomain rmd = original.remove(key);
	// rmd.disable();
	// }
	// for (String key : change.keySet()) {
	// if (original.containsKey(key))
	// continue;
	// original.put(key, change.get(key));
	// }
	// }

	public RomDomain getDomain(int intraHostId) throws KnownError {
		HostStat hs = host(intraHostId);
		if (hs == null)
			throw new KnownError("Unknown hostId:" + intraHostId);

		if (hs.domainalias == null || hs.domainalias.length == 0)
			return null;

		return createDomain(hs.domainalias[0]);
	}

	private static final Astate _getDomain = mc.c("getDomain");

	public RomDomain getDomain(String domainName) throws KnownError {
		destur();
		_getDomain.more();
		if (domainName.indexOf(':') > 0) {
			domainName = domainName.substring(0, domainName.indexOf(':'));
		}

		if (!checkRomDomain(domainName)) {
			_getDomain.failed();
			throw new KnownError().notFound("domain:" + domainName).security();
		}

		return createDomain(domainName);
	}

	private boolean checkRomDomain(String domainName) throws KnownError {
		String stripped = DomainName.strip(domainName);
		if (stripped == null) {
			throw new KnownError("Stripped domainName is null/domain:" + (domainName == null ? "null" : domainName));
		}

		if (stripped.equals(DomainName.freeDomain)) {
			return true;
		}

		HostStat hs = hostsByAlias.get(stripped);

		if (hs == null) {
			return false;
		}
		return true;
	}

	public RomDomain optDomain(String domainName) {
		if (domainName.indexOf(':') > 0) {
			domainName = domainName.substring(0, domainName.indexOf(':'));
		}
		try {
			if (!checkRomDomain(domainName)) {
				return null;
			}
		} catch (Exception e) {
		}

		return createDomain(domainName);

	}

	private RomDomain createDomain(String domainName) {

		DomainName dn;
		try {
			dn = new DomainName(domainName);
			if (dn.getTopLevelName() == null)
				return null;

			if (dn.getHostId() == -1) {
				HostStat host = this.hostsByAlias.get(dn.getTopLevelName());
				if (host == null) {
					return null;
				}

				dn.romDomain(host.hostid, emalDomain(host), host.forceHttps);
			} else {
				HostStat host = this.hostById.get(dn.getHostId());
				if (host == null) {
					return null;
				}
				dn.romDomain(host.hostid, emalDomain(host), host.forceHttps);
			}

			return dn.getRomDomain();
		} catch (KnownError e) {
			Sistem.errln("DomainName not valid:" + domainName);
			return null;
		}

	}

	private String emalDomain(HostStat host) {
		return (host.domainalias != null && host.domainalias.length > 0) ? host.domainalias[0] : null;
	}

	public boolean emailEnabledForHost(int hostId) throws KnownError {
		destur();
		try (DbThree db3 = new DbThree("select * from dict.domains_emailon(?)")) {
			db3.setInt(hostId);
			db3.executeQuery();
			db3.checkedNext();
			return db3.getBoolean();
		}
	}

	public Media getMedia(int hostId, Integer pr, String pid) throws KnownError {
		destur();
		try (DbThree db3 = new DbThree("select uri,previewuri " + "from tepeweb.medias_getbyprovider(?, ?, ?)")) {
			db3.setInt(hostId);
			db3.setInt(pr);
			db3.setString(pid);
			db3.executeQuery();
			if (!db3.next()) {
				return null;
			}
			String uri = db3.getString();
			String previewUri = db3.getString();
			Media m = new Media(hostId, uri, pr, pid, previewUri);
			return m;
		}
	}

	public void updateHosts() throws KnownError {
		destur();
		loadHosts();
	}

	public void servableHost(Integer hostId, boolean on) throws KnownError {
		destur();
		try (DbThree db3 = new DbThree("update dict.hosts set servable=? where host_id=?")) {
			db3.setBoolean(on);
			db3.setInt(hostId);
			db3.executeQuery();
		}
	}

	public HostStat[] hosts() throws KnownError {
		destur();
		return new LoadHostsDbOp().doit();
	}

	public String getFavicon(int p_hostid) throws KnownError {
		destur();
		return new GetFavIconDbOp().doit(p_hostid);
	}

	public boolean containsDomain(String domain) {
		HostStat h = this.hostsByAlias.get(domain);
		return h != null && h.servable;
	}

	public String getEmailDomain(int host_id) throws KnownError {
		HostStat hs = this.hostById.get(host_id);
		if (hs != null)
			return hs.hostname;
		return null;
	}

	public HostStat host(int id) {
		return this.hostById.get(RomDomain.intra(id));
	}

	public String getDeskEmail(int hostId) throws KnownError {
		return new GetDeskDbOp().doit(hostId);
	}

	public void addHostListener(HostListener hl) {
		hostListener.add(hl);
	}

	// @Override
	// public JSONObject getTlosTechnicContact() {
	// return contact;
	// }
	//
	// @Override
	// public String getTlosTechnicEmail() {
	// return contactEmail;
	// }

	public void addAlias(int hostId, String alias) throws KnownError {
		new AddAliasDbOp().doit(hostId, alias);
		loadHosts();
	}

	// private boolean gettingRemoteDbHosts=false;
	// private int pass=0;

	// @Override
	// public void everyMinute(int year, int month, int day, int hour, int minute) {
	// if(gettingRemoteDbHosts)
	// return;
	// pass++;
	// if(pass==5){
	// pass=0;
	// KosuGorevlisi.tek().exec(this);
	// }
	// }

	private static final Astate updateRemoteHosts = mc.c("update-remote-hosts");
	// @Override
	// public void run() {
	// try {
	// updateRemoteHosts.more();
	// loadHosts();
	// } catch (KnownError e) {
	// updateRemoteHosts.failed(e);
	// } catch(Throwable t){
	// Sistem.printStackTrace(t, "HostService update remote hosts");
	// }
	// }

	public Organization getOrganization(int hostId) throws KnownError {
		return new GetOrganizationDbOp().doit(RomDomain.intra(hostId));
	}

}
