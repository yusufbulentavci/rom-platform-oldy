package com.bilgidoku.rom.site.yerel.initial;

import java.util.Collection;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.constants.InitialConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class SaveHandler {
	private final InitialConstants con = GWT.create(InitialConstants.class);
//	private final SiteButton save = new SiteButton(images.disk(), con.save(), con.saveDesc(), "");
//	private final SiteButton finish = new SiteButton(images.house(), con.finish(), con.finishDesc(), "");

	private final SiteButton save = null;
	private final SiteButton finish = null;

	private final InitialPage initial;

	public SaveHandler(InitialPage initializationPage) {
		save.setWidth("100%");
		finish.setWidth("100%");
		this.initial = initializationPage;
		// setState(false);
		windUp(initializationPage);
	}

	private void windUp(final InitialPage initializationPage) {
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save(initializationPage);
			}
		});

		finish.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				finish(initializationPage);
			}
		});
	}

	protected void finish(InitialPage initializationPage) {
		save(initializationPage);
		Window.alert(con.msgSettingUpSystem());
//		InitialsDao.ready("/_/_initials", new StringResponse() {
//			@Override
//			public void ready(String value) {
//				Window.alert(con.msgDone());
//				redirectMain();
//			}
//
//			@Override
//			public void err(int statusCode, String statusText, Throwable exception) {
//				super.err(statusCode, statusText, exception);
//				redirectMain();
//			}
//			
//			private void redirectMain() {
//				String queryParam = LocaleInfo.getLocaleQueryParam();
//				String localeVal = Location.getParameter(queryParam);
//				String newLoc = "";
//				if (localeVal != null)
//					newLoc = "/_local/main.rom?" + queryParam + "=" + localeVal; 
//				else
//					newLoc = "/_local/main.rom";
//
//				String gwtCodeSrv = "gwt.codesvr";
//				String gwtCodeSrvVal = Location.getParameter(gwtCodeSrv);
//				if (gwtCodeSrvVal != null) {
//					if (newLoc.indexOf("?") > 0)
//						newLoc = newLoc + "&" +  gwtCodeSrv + "=" + gwtCodeSrvVal;
//					else
//						newLoc = newLoc + "?" +  gwtCodeSrv + "=" + gwtCodeSrvVal;
//				}
//				
//				Window.Location.replace(newLoc);
//			}
//			
//		});
	}

	protected void save(InitialPage init) {
//		String lang = init.getLang();
//		String domain = init.getDomainName();
//		String sector = init.getSector();
//		String xml = toXml();
//		//isCreate ve transfer için auth bilgisi de gondeirliecek.
//		InitialsDao.change(lang, domain, initial.getIsTransfer(), initial.getAuthInfo(), 
//				sector, xml, new Json(init.getContact()), "/_/_initials", new StringResponse() {
//			@Override
//			public void ready(String value) {
//				Window.alert(con.msgSaved());
//			}
//		});
	}

	public void load(final InitialPage init) {
//		InitialsDao.get("/_/_initials", new InitialsResponse() {
//			@Override
//			public void ready(Initials value) {
//				
//				if (value.site_lang != null) {
//					init.isfirstRun = false;
//					init.setLang(value.site_lang);					
//				}
//
//				if (value.domain_name != null) {
//					init.setDomainName(value.domain_name);
//				}
//
//				if (value.pages != null) {
//					loadPages(value.pages);
//				}
//			
//				if (value.sector != null) {
//					init.setSector(value.sector);
//				}
//				
//				if(value.contact!=null){
//					init.setContact(value.contact);
//				}
//				
//				if (init.isfirstRun)
//					init.step1();
//
//
//			}
//		});
	}

	public void setState(boolean b) {
		save.setEnabled(b);
		finish.setEnabled(b);
	}

	public Button getSave() {
		return save;
	}

	public Button getFinish() {
		return finish;
	}

	private String toXml() {
		Document document = XMLParser.createDocument();
		Element initial = document.createElement("initial");
		document.appendChild(initial);
		Collection<Details> ps = this.initial.getPages();
		writePages(document, initial, ps);
		return document.toString();
	}

	private void writePages(Document document, Element initial2, Collection<Details> ps) {

		for (Details det : ps) {
			Element page = document.createElement("page");
			initial2.appendChild(page);
			page.setAttribute("realuri", det.getRealUri());
			page.setAttribute("uri", det.getUri());
			page.setAttribute("name", det.name);
			page.setAttribute("app", det.app);
			page.setAttribute("main", det.menu ? "1" : "0");
			page.setAttribute("footer", det.footer ? "1" : "0");
			page.setAttribute("vitrine", det.vitrine ? "1" : "0");
			page.setAttribute("group", det.group);
			page.setAttribute("container", det.isContainer() ? "1" : "0" );
			/*
			if (det.group.isEmpty()) {
				page.setAttribute("container", "1");
			} else {
				page.setAttribute("container", "0");
			}
			*/
		}
	}

	private void loadPages(String xml) {
		Document doc = XMLParser.parse(xml);
		NodeList inXml = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < inXml.getLength(); i++) {
			Node p = inXml.item(i);
			// String
			// realuri=p.getAttributes().getNamedItem("realuri").getNodeValue();
			String uri = p.getAttributes().getNamedItem("uri").getNodeValue();
			String name = p.getAttributes().getNamedItem("name").getNodeValue();
			String app = p.getAttributes().getNamedItem("app").getNodeValue();
			String group = p.getAttributes().getNamedItem("group").getNodeValue();
			boolean main = p.getAttributes().getNamedItem("main").getNodeValue().equals("1");
			boolean footer = p.getAttributes().getNamedItem("footer").getNodeValue().equals("1");
			boolean vitrine = p.getAttributes().getNamedItem("vitrine").getNodeValue().equals("1");
			boolean container = p.getAttributes().getNamedItem("container").getNodeValue().equals("1");
			if (container) {
				initial.addGroup(name, uri, main, footer, vitrine, app);
			} else {
				initial.addPage(name, uri, main, footer, vitrine, app, group);
			}
		}
		initial.selectFirstPage();
		
	}
}
