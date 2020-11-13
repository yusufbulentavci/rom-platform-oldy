package com.bilgidoku.rom.site.yerel.admin;

import java.util.Date;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.OrgDao;
import com.bilgidoku.rom.gwt.araci.client.service.YonetimDao;
import com.bilgidoku.rom.gwt.client.util.browse.image.UploadFile;
import com.bilgidoku.rom.gwt.shared.RomCert;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PnlCertificate extends Composite {

	public final HTML forceLbl = new HTML();
	public final Button btnForceHttp = new Button();

	// private final HTML hasKeyLbl = new HTML("key");
	protected Boolean forcehttps;

	CertLine home = new CertLine("home", "odd");
	CertLine www = new CertLine("www", "even");

	public PnlCertificate() {
		loadOrgForceData();
		loadCertificates();
		loadKeys();

		forForceHttp();

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(forceLbl);
		hp.add(btnForceHttp);

		VerticalPanel vp = new VerticalPanel();
		vp.add(hp);
		vp.add(getHeader());
		vp.add(home);
		vp.add(www);

		initWidget(vp);
	}

	private void loadKeys() {
		YonetimDao.keys(new ArrayResponse<String>() {
			@Override
			public void ready(String[] value) {
				for (int i = 0; i < value.length; i++) {
					Sistem.outln(value[i]);
				}
			}
		});
	}

	private Widget getHeader() {
		HTML i = new HTML("Issuer");
		HTML a = new HTML("Alias");
		HTML t = new HTML("Test");
		HTML k = new HTML("Has Key?");
		HTML s = new HTML("Serial");

		HTML nb = new HTML("Not Before");
		HTML na = new HTML("Not After");

		k.setWidth("40px");
		a.setWidth("150px");
		i.setWidth("135px");
		t.setWidth("40px");
		s.setWidth("100px");
		nb.setWidth("100px");
		na.setWidth("100px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("cell-header");
		// hp.setBorderWidth(1);
		// hp.add(h);
		hp.add(a);
		hp.add(i);
		hp.add(t);
		hp.add(k);
		hp.add(s);
		hp.add(nb);
		hp.add(na);

		return hp;
	}

	private void loadCertificates() {
		YonetimDao.getHostCerts(new ArrayResponse<RomCert>() {
			@Override
			public void ready(RomCert[] value) {
				Sistem.outln("Certs loaded");
				for (int i = 0; i < value.length; i++) {
					if (value[i].alias.indexOf("home") >= 0)
						home.setCertificate(value[i]);
					else
						www.setCertificate(value[i]);
				}
			}
		});

	}

	private void loadOrgForceData() {
		OrgDao.getforcehttps("/_/_org", new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				Sistem.outln("Force data loaded");
				forcehttps = value;
				updateForceHttp();
			}
		});

	}

	private void forForceHttp() {
		btnForceHttp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				OrgDao.setforcehttps(!forcehttps, "/_/_org", new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						forcehttps = !forcehttps;
						updateForceHttp();
					}
				});
			}
		});
	}

	private void updateForceHttp() {
		if (forcehttps.booleanValue()) {
			forceLbl.setHTML("Https Forced");
			btnForceHttp.setText("Do not Force Https");
		} else {
			forceLbl.setHTML("Https is not Forced");
			btnForceHttp.setText("Force Https");
		}
	}

	private class CertLine extends Composite implements UploadFile {
		private RomCert cert;

		private final HorizontalPanel mid = new HorizontalPanel();
		private final Button btnUpd = new Button("Refresh Keys");
		private final Button btnUpload = new Button("Upload Cert");
		private final Button btnDel = new Button("Del Cert");
		private final Anchor hAlias = new Anchor();

		public CertLine(final String alias, String style) {
			hAlias.setTarget("_blank");
			hAlias.setHref("/_admin/getCsr.rom?alias=" + alias);
			btnUpload.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgNewCertificate fd = new DlgNewCertificate(cert.alias);
					fd.show();
					fd.center();
				}
			});
			btnDel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (cert == null)
						return;
					YonetimDao.removeCert(cert.alias, new BooleanResponse() {
						@Override
						public void ready(Boolean value) {
							// setCertificate(null, "odd");
						}
					});
				}
			});
			btnUpd.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (cert == null)
						return;
					YonetimDao.updateKeys(cert.alias, new BooleanResponse() {
						@Override
						public void ready(Boolean value) {

						}
					});
				}
			});

			mid.setWidth("480px");

			HorizontalPanel hp = new HorizontalPanel();
			hp.add(btnDel);
			hp.add(btnUpload);
			hp.add(btnUpd);

			hAlias.setHTML(alias);
			hAlias.setWidth("150px");
			hAlias.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

			// hasKeyLbl.setWidth("30px");

			HorizontalPanel line = new HorizontalPanel();
			line.setStyleName("row-" + style);

			line.add(hAlias);
			line.add(mid);
			line.add(hp);
			initWidget(line);
		}

		public void setCertificate(RomCert c) {
			this.cert = c;
			mid.clear();

			if (cert != null) {
				hAlias.setHTML(cert.alias);
				mid.add(getIssuer(cert.issuer));
				mid.add(getTest(cert.test));
				mid.add(getHasKey(cert.alias));
				mid.add(getSerial(cert.serial));
				mid.add(getDates(cert.notBefore, cert.notAfter));
				btnDel.setVisible(true);
			} else {
				btnDel.setVisible(false);
			}
		}

		private Widget getHasKey(String alias) {
			HTML a = new HTML("false" + "");
			a.setWidth("40px");
			return a;
		}

		@Override
		public void fileUploaded(String dbfs, String fileName) {

		}

		private HTML getIssuer(String iss) {
			HTML a = new HTML(iss);
			a.setWidth("135px");
			return a;
		}

		private HTML getTest(Boolean test) {
			HTML a = new HTML(test + "");
			a.setWidth("40px");
			return a;
		}

		private HTML getSerial(String s) {
			HTML a = new HTML(s);
			a.setWidth("120px");
			return a;
		}

		private HTML getDates(Long b, Long a) {
			HTML nb = new HTML(fmtDate(new Date(b)) + " - " + fmtDate(new Date(a)));
			nb.setWidth("200px");
			return nb;
		}

		private String fmtDate(Date date) {
			// 2013-09-04 15:31:12.958632
			DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
			return fmt.format(date);
		}

	}

}
