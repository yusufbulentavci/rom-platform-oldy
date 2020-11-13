package com.bilgidoku.rom.web.analytics;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryHour;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.analytics.useragent.UserAgent;
import com.bilgidoku.rom.web.http.RomHttpHandler;

@HttpCallServiceDeclare(uri = "/_analytics", name = "Analitik", paket="com.bilgidoku.rom.web.analytics")
public class AnalitikGorevlisi extends GorevliDir implements EveryHour {

	public static final int NO = 14;

	public static AnalitikGorevlisi tek(){
		if (tek == null) {
			synchronized (AnalitikGorevlisi.class) {
				if (tek == null) {
					tek = new AnalitikGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static AnalitikGorevlisi tek;

	private AnalitikGorevlisi(){
		super("Analitik", NO);

	}

	@Override
	protected void kur() throws KnownError {
		String[] searchEngineKeys = new String[0];
		if (searchEngineKeys.length % 4 != 0) {
			throw new KnownError(
					"searchEngines property error, couldnot be initialized:" + searchEngineKeys.toString());
		}
		searchEngines = new SearchEngine[searchEngineKeys.length / 4];
		for (int i = 0; i < searchEngineKeys.length; i += 4) {
			SearchEngine se = new SearchEngine(searchEngineKeys[i], searchEngineKeys[i + 1], searchEngineKeys[i + 2],
					searchEngineKeys[i + 3]);
			this.searchEngines[i / 4] = se;
		}

		KosuGorevlisi.tek().waitHour(this);

	}

	@Override
	protected void bitir(boolean dostca) {
	}

	private static final MC mc = new MC(AnalitikGorevlisi.class);

	private SearchEngine[] searchEngines;

	private final Map<Integer, AnalyticsOfHost> hosts = new HashMap<Integer, AnalyticsOfHost>();
	private Long lastDbWrite = null;

	public void selfDescribe(JSONObject jo) {
		jo.safePut("hostcount", hosts.size());
		if (lastDbWrite != null)
			jo.safePut("lastdb", new Date(lastDbWrite).toString());
	}

	public void addSessionCounters(int hostId, String hostName, long startTime, String country, String lang,
			String referrer, UserAgent userAgent, Map<String, PageVisit> pageVisits, long cpu, long netRead,
			long netWrite) {
		AnalyticsOfHost an = hosts.get(hostId);
		if (an == null) {
			an = new AnalyticsOfHost(hostId, startTime);
			hosts.put(hostId, an);
		}

		if (referrer != null && referrer.length() > 0) {
			URL url;
			try {
				url = new URL(referrer);
				if (!url.getHost().equals(hostName)) {
					checkSearchEngine(url, an);
					an.addReferrer(url.getHost());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Sistem.printStackTrace(e);
			}
		}

		an.appendCounters(country, lang, userAgent, pageVisits, cpu, netRead, netWrite);
	}

	private void checkSearchEngine(URL url, AnalyticsOfHost an) {
		for (SearchEngine se : searchEngines) {
			if (se.domainName.equals(url.getHost())) {
				String[] q = se.extractQuery(url.getQuery());
				if (q != null)
					an.mergeBringingWords(q);
				return;
			}
		}
	}

	// a_host integer,
	// p_timebegin bigint,
	// p_timeend bigint,
	// p_ops json,
	// p_browsers json,
	// p_referrers json,
	// p_inhits json,
	// p_countries json,
	// p_langs json,
	// p_pageperiods json,
	// p_bringingwords json,
	// p_cpu bigint,
	// p_netread bigint,
	// p_netwrite bigint
	private final String callProto = "select * from site.hits_new(?,?,?,?::json,?::json,?::json,?::json,?::json,?::json,?::json,?::json,?,?,?)";

	@Override
	public void everyHour(int year, int month, int day, int hour) {
		if (!mayi())
			return;

		long nowMins = System.currentTimeMillis();
		try (DbThree db3 = new DbThree(callProto)) {
			for (Entry<Integer, AnalyticsOfHost> it : this.hosts.entrySet()) {
				AnalyticsOfHost an = it.getValue();
				db3.setInt(it.getKey());
				db3.setLong(an.startTime);
				db3.setLong(nowMins);

				db3.setNull(java.sql.Types.VARCHAR);
				// val=request.getParam("ops", null, null, false);

				db3.setString(new JSONObject(an.browsers).toString());

				db3.setString(new JSONObject(an.referrers).toString());

				db3.setString(new JSONObject(an.pageVisitsCount).toString());

				db3.setString(new JSONObject(an.countries).toString());

				db3.setString(new JSONObject(an.langs).toString());

				db3.setString(new JSONObject(an.pageVisitsTime).toString());

				db3.setString(new JSONObject(an.bringingWords).toString());

				db3.setLong(an.cpuUsage);

				db3.setLong(an.netRead);

				db3.setLong(an.netWrite);

				db3.execute();

				it.getValue().reset();
			}

		} catch (KnownError e) {
			err(e);
		}
		lastDbWrite = Sistem.millis();
	}

	public RomHttpHandler getCustomService() {
		return null;
	}

}
