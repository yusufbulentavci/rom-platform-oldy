package com.bilgidoku.rom.shared.gorevli;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.cokluortam.CokluOrtamGorevlisi;
import com.bilgidoku.rom.cokluortam.twod.ResimGorevlisi;
import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.haber.HaberGorevlisi;
import com.bilgidoku.rom.internetapi.InternetApiGorevlisi;
import com.bilgidoku.rom.izle.IzlemeGorevlisi;
import com.bilgidoku.rom.js.JsGorevlisi;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.mime.MimeGorevlisi;
import com.bilgidoku.rom.os.KabukGorevlisi;
import com.bilgidoku.rom.pg.PgGorevlisi;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dosya.DosyaGorevlisi;
import com.bilgidoku.rom.pg.sembol.SembolGorevlisi;
import com.bilgidoku.rom.pg.sozluk.SozlukGorevlisi;
import com.bilgidoku.rom.pg.veritabani.VeritabaniGorevlisi;
import com.bilgidoku.rom.pop3.Pop3Gorevlisi;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.secure.GuvenlikGorevlisi;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.smtp.SmtpGorevlisi;
import com.bilgidoku.rom.web.account.HesapGorevlisi;
import com.bilgidoku.rom.web.admin.YonetimGorevlisi;
import com.bilgidoku.rom.web.analytics.AnalitikGorevlisi;
import com.bilgidoku.rom.web.audit.DenetimGorevlisi;
import com.bilgidoku.rom.web.authorize.YetkilendirmeGorevlisi;
import com.bilgidoku.rom.web.cagridagitma.CagriDagitmaGorevlisi;
import com.bilgidoku.rom.web.cagridagitma.IcCagriDagitmaGorevlisi;
import com.bilgidoku.rom.web.charge.UcretlendirmeGorevlisi;
import com.bilgidoku.rom.web.cookie.CookieGorevlisi;
import com.bilgidoku.rom.web.directweb.UygulamaYonetimGorevlisi;
import com.bilgidoku.rom.web.directweb.UygulamaYonetimiCagriGorevlisi;
import com.bilgidoku.rom.web.filecontent.DuraganDosyaGorevlisi;
import com.bilgidoku.rom.web.filecontent.HazirIcerikGorevlisi;
import com.bilgidoku.rom.web.filecontent.KamusalDosyaGorevlisi;
import com.bilgidoku.rom.web.filecontent.SiteDosyaGorevlisi;
import com.bilgidoku.rom.web.filecontent.YerelDosyaGorevlisi;
import com.bilgidoku.rom.web.grow.GenislemeGorevlisi;
import com.bilgidoku.rom.web.guest.MisafirGorevlisi;
import com.bilgidoku.rom.web.html.HtmlGorevlisi;
import com.bilgidoku.rom.web.http.HttpGorevlisi;
import com.bilgidoku.rom.web.info.SiteInfoGorevlisi;
import com.bilgidoku.rom.web.maintain.BakimGorevlisi;
import com.bilgidoku.rom.web.online.OnlineGorevlisi;
import com.bilgidoku.rom.web.richweb.InternetGorevlisi;
import com.bilgidoku.rom.web.sessionfuncs.OturumIciCagriGorevlisi;
import com.bilgidoku.rom.web.uri.UriGorevlisi;
import com.bilgidoku.rom.web.welcome.HosgeldinGorevlisi;
import com.bilgidoku.rom.yerel.YerelGorevlisi;

import net.sf.clipsrules.jni.AkilGorevlisi;



public class GorevliSozlugu {
	private static GorevliSozlugu tek;
	
	public static GorevliSozlugu tek() {
		if(tek==null)
			tek=new GorevliSozlugu();
		return tek;
	}
	
	BilinenGorevli[] tumu=new BilinenGorevli[100];
	private GorevliSozlugu() {
		ekle(0, GunlukGorevlisi.class);
		ekle(1, IzlemeGorevlisi.class);
		ekle(3, KosuGorevlisi.class);
		ekle(4, VeritabaniGorevlisi.class);
		ekle(5, PgGorevlisi.class);
		ekle(6, DosyaGorevlisi.class);
		ekle(7, YerelGorevlisi.class);
		ekle(10, GuvenlikGorevlisi.class);
		ekle(11, OturumGorevlisi.class);
		ekle(12, KurumGorevlisi.class);
		ekle(13, CookieGorevlisi.class);
		ekle(14, AnalitikGorevlisi.class);
		ekle(15, MimeGorevlisi.class);
		ekle(16, HttpGorevlisi.class);
		ekle(18, SozlukGorevlisi.class);
		ekle(19, DbfsGorevlisi.class);
		ekle(20, InternetApiGorevlisi.class);
		ekle(21, CokluOrtamGorevlisi.class);
		ekle(22, ResimGorevlisi.class);
		ekle(23, HaberGorevlisi.class);
		ekle(24, EpostaGorevlisi.class);
		ekle(26, YetkilendirmeGorevlisi.class);
		ekle(27, CagriDagitmaGorevlisi.class);
		ekle(28, SiteDosyaGorevlisi.class);
		ekle(29, IcCagriDagitmaGorevlisi.class);
		ekle(30, UriGorevlisi.class);
		ekle(31, HtmlGorevlisi.class);
		ekle(32, UcretlendirmeGorevlisi.class);
		ekle(33, GenislemeGorevlisi.class);
		ekle(34, YerelDosyaGorevlisi.class);
		ekle(35, KamusalDosyaGorevlisi.class);
		ekle(36, HazirIcerikGorevlisi.class);
		ekle(37, InternetGorevlisi.class);
		ekle(38, DuraganDosyaGorevlisi.class);
		ekle(39, OturumIciCagriGorevlisi.class);
		ekle(40, UygulamaYonetimGorevlisi.class);
		ekle(41, UygulamaYonetimiCagriGorevlisi.class);
		ekle(42, HesapGorevlisi.class);
		ekle(43, YonetimGorevlisi.class);
		ekle(44, DenetimGorevlisi.class);
		ekle(45, MisafirGorevlisi.class);
		ekle(46, SiteInfoGorevlisi.class);
		ekle(47, BakimGorevlisi.class);
		ekle(48, OnlineGorevlisi.class);
		ekle(49, HosgeldinGorevlisi.class);
		ekle(50, AkilGorevlisi.class);
		ekle(51, JsGorevlisi.class);
		ekle(52, KabukGorevlisi.class);
		ekle(53, SembolGorevlisi.class);
		ekle(54, SmtpGorevlisi.class);
		ekle(55, Pop3Gorevlisi.class);
	}

	private void ekle(int i, Class c) {
		tumu[i]=new BilinenGorevli(i, c);
	}

	public List<BilinenGorevli> anotasyonlariGetir(Class c) {
		List<BilinenGorevli> ret=new ArrayList<>();
		
		for (int i=0; i< tumu.length; i++) {
			BilinenGorevli g=tumu[i];
			if(g==null)
				continue;
			if(g.anotasyon(c)!=null)
				ret.add(g);
		}
		return ret;
	}

}

