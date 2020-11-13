package com.bilgidoku.rom.web.info;

import com.bilgidoku.rom.gwt.shared.HostStat;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.http.session.AppSession;
import com.bilgidoku.rom.web.info.dbop.OrgGetStartssl;

@HttpCallServiceDeclare(uri = "/_info/", name = "SiteInfo", paket="com.bilgidoku.rom.web.info")
public class SiteInfoGorevlisi extends GorevliDir {
	public static final int NO = 46;

	public static SiteInfoGorevlisi tek() {
		if (tek == null) {
			synchronized (SiteInfoGorevlisi.class) {
				if (tek == null) {
					tek = new SiteInfoGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static SiteInfoGorevlisi tek;

	private SiteInfoGorevlisi() {
		super("SiteInfo", NO);
	}

	final static private MC mc = new MC(SiteInfoGorevlisi.class);


	final static private String sitemapHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
			+ "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" "
			+ "xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\"> ";

	final static private String sitemapFooter = "</urlset>";

	@HttpCallMethod(http = "get", contenttype = "text/xml; charset=UTF-8")
	public String sitemap(@hcp(n = "a_session") AppSession session) throws KnownError {
		HostStat host = KurumGorevlisi.tek().host(session.getInterHostId());

		// For publics
		//
//		String tld = "http://" + session.getDomain().getEmailDomain();

		StringBuilder sb = new StringBuilder(sitemapHeader);
		try (DbThree db3 = new DbThree(
				"select langcodes,uri,medium_icon,large_icon " + "from site.writings where host_id=? and mask&4=4")) {
			db3.setInt(session.getInterHostId());
			db3.executeQuery();
			while (db3.next()) {
				String[] langCodes = db3.getStringArray();
				String uri = db3.getString();
				String mIcon = db3.getString();
				String lIcon = db3.getString();

				for (String lang : langCodes) {
					String domain = "http://" + (host.mainlang.equals(lang) ? "www" : lang) + "." + host.hostname;
					sb.append("<url>");
					sb.append("<loc>");
					sb.append(domain);
					sb.append(uri);
					sb.append("</loc>");

					writeImage(sb, domain, mIcon);
					writeImage(sb, domain, lIcon);
					sb.append("</url>");
				}

			}
		}
		sb.append(sitemapFooter);
		return sb.toString();
	}

	private void writeImage(StringBuilder sb, String domainName, String image2) {
		if (image2 == null)
			return;
		sb.append("<image:image>");
		sb.append("<image:loc>");
		sb.append(domainName);
		sb.append(image2);
		sb.append("</image:loc>");
		sb.append("</image:image>");
	}


	private static final String robotsHeader = "User-agent: * \r\nDisallow:  \r\nDisallow: /_local \r\nSitemap: ";

	@HttpCallMethod(http = "get", contenttype = "text/plain; charset=UTF-8")
	public String robots(@hcp(n = "a_domain") RomDomain domain) throws KnownError {

		String domainName = "http://" + domain.getDomainName();
		StringBuilder sb = new StringBuilder(robotsHeader);
		sb.append(domainName);
		sb.append("/sitemap.xml");
		return sb.toString();
	}

	@HttpCallMethod(http = "get", contenttype = "text/plain; charset=UTF-8")
	public String startssl(@hcp(n = "a_domain") RomDomain domain) throws KnownError {

		String startssl = new OrgGetStartssl().doit(domain.getIntra());

		if (startssl == null)
			return "Empty value";
		return startssl.toString();
	}

	@Override
	public void selfDescribe(JSONObject jo) {

	}

}
