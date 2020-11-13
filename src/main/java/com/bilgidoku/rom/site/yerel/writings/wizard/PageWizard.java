package com.bilgidoku.rom.site.yerel.writings.wizard;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.araci.client.site.LinksDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

public class PageWizard extends DialogBox {

	private List<String> waiting = null;

	final Button btnNext = new Button(Ctrl.trans.next());
	final Button btnCancel = new Button("Cancel");
	final FlexTable holder = new FlexTable();
	final HTML title = new HTML(
			"<h1>" + Ctrl.trans.pwPageWizard() + "</h1><h2>" + Ctrl.trans.pwSelectMainMenuItems() + "</h2>");
	private boolean showVitrine = false;
	private boolean showFooter = false;
	private boolean showMain = false;
	private boolean nameAsked = false;

	public interface MyTemplate extends SafeHtmlTemplates {
		@Template("<span style=\"font-size:18px\">{0}</span>")
		SafeHtml header(String str);
	}

	private static final MyTemplate TEMPLATE = GWT.create(MyTemplate.class);

	public PageWizard(boolean getReadyImages) {

		forNext();
		forClose();

		makeDbReady(getReadyImages);

		btnCancel.setStyleName("site-closebutton");
		btnNext.setStyleName("site-nextbutton");
		title.setStyleName("site-title");
		holder.setStyleName("site-padding");
		holder.setSize("400px", "400px");
		holder.getFlexCellFormatter().setHeight(0, 0, "40px");
		holder.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		holder.getFlexCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
		holder.setWidget(0, 0, title);
		holder.setWidget(1, 0, pnlOptions());
		holder.setWidget(2, 0, btnNext);
		holder.setWidget(3, 0, btnCancel);

		this.setWidget(holder);
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(false);
		this.setText(Ctrl.trans.pwPageWizard());
		this.show();
		this.center();
	}

	private void makeDbReady(final boolean getReadyImages) {
		new ApplySiteInfo();

		createHome();

		ContainersDao.listsub("/f/images", "/_/c", new ContainersResponse() {
			@Override
			public void array(List<Containers> myarr) {
				if (!exists("/f/images/readymedia", myarr))
					createReadyMedia();
				else if (getReadyImages)
					new ApplyReadyImages();

			}

		});

	}

	protected void createReadyMedia() {
		FilesDao.breed("/f/images/readymedia", Data.WRITING_PUBLIC_MASK, null, "w:files", "/f/images",
				new ContainersResponse() {
					@Override
					public void ready(Containers value) {
						new ApplyReadyImages();
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						new ApplyReadyImages();
					}
				});

	}

	protected void createHome() {
		ResourcesDao.exists("/_/writings", new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				if (!value) {
					breedHome();
				} else {
					createHomePage();
				}
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				breedHome();
			}
		});

	}

	protected void createHomePage() {
		WritingsDao.neww(Ctrl.infoLang(), "Ana Sayfa", "/", "/_/writings", new StringResponse() {
			@Override
			public void ready(String value) {

			}
		});
		new ApplySearchPage();
		new ApplyKendinTasarla();

	}

	protected void breedHome() {
		WritingsDao.breed("/_/writings", Ctrl.infoLang(), Data.WRITING_PUBLIC_MASK, "/", "w:standart", "Home Page",
				null, new ContainersResponse() {
					@Override
					public void ready(Containers value) {
						createHomePage();
					}
				});

	}

	protected boolean exists(String string, List<Containers> myarr) {
		boolean exists = false;
		for (int i = 0; i < myarr.size(); i++) {
			Containers con = myarr.get(i);
			if (con.uri.equals(string)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	private Widget pnlOptions() {
		final FlexTable ft = new FlexTable();

		CheckBox main = new CheckBox();
		main.setValue(true);
		main.setEnabled(false);

		ft.setWidget(0, 0, main);
		ft.setHTML(0, 1, TEMPLATE.header(Ctrl.trans.main()));

		ft.setWidget(1, 0, new CheckBox());
		ft.setHTML(1, 1, TEMPLATE.header(Ctrl.trans.aboutus()));

		ft.setWidget(2, 0, new CheckBox());
		ft.setHTML(2, 1, TEMPLATE.header(Ctrl.trans.products()));

		ft.setWidget(3, 0, new CheckBox());
		ft.setHTML(3, 1, TEMPLATE.header(Ctrl.trans.projects()));

		ft.setWidget(4, 0, new CheckBox());
		ft.setHTML(4, 1, TEMPLATE.header(Ctrl.trans.references()));

		ft.setWidget(5, 0, new CheckBox());
		ft.setHTML(5, 1, TEMPLATE.header(Ctrl.trans.contactUs()));
		return ft;
	}

	private void forClose() {
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide(true);
			}
		});

	}

	private void forNext() {
		btnNext.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FlexTable ft = (FlexTable) holder.getWidget(1, 0);
				waiting = new ArrayList<String>();
				for (int i = 0; i < ft.getRowCount(); i++) {
					CheckBox cb = (CheckBox) ft.getWidget(i, 0);
					if (cb.getValue()) {
						waiting.add("" + i);

					}
				}
				ft.setVisible(false);
				goNextStep(null, null);
				btnNext.setVisible(false);
			}
		});

	}

	private void showMain() {
		showMain = true;
		title.setHTML("<h3>" + Ctrl.trans.mainMenu() + "</h3>");
		holder.getFlexCellFormatter().setVisible(1, 0, true);
		holder.setWidget(1, 0, new PageMenuItems("/_/lists/public/main", this));
	}

	private void showVitrine() {
		showVitrine = true;
		title.setHTML("<h3>" + Ctrl.trans.pwVitrine() + "</h3>");
		holder.getFlexCellFormatter().setVisible(1, 0, true);
		holder.setWidget(1, 0, new PageMenuItems("/_/lists/public/vitrine", this));
	}

	private void showFooter() {
		showFooter = true;
		title.setHTML("<h3>" + Ctrl.trans.siteFooter() + "</h3>");
		holder.getFlexCellFormatter().setVisible(1, 0, true);
		holder.setWidget(1, 0, new PageMenuItems("/_/lists/public/footer", this));
	}

	public void goNextStep(String parentName, String parentUri) {

		if (waiting.size() == 0 && showVitrine && showFooter && showMain) {
			Window.alert(Ctrl.trans.pwFinished());
			Ctrl.gotoContent();
			Ctrl.editHome();
			this.hide();
			return;
		}

		if (waiting.size() == 0) {

			if (!showMain)
				showMain();

			else if (!showVitrine)
				showVitrine();

			else if (!showFooter)
				showFooter();

			return;
		}

		String item = waiting.get(0);
		int i = Integer.parseInt(item);
		String pageName = "";
		switch (i) {
		case 0:
			pageName = Ctrl.trans.main();
			title.setHTML("<h3>" + pageName + "</h3>");
			holder.setWidget(1, 0, new CreateContainer(Ctrl.trans.main(), "homepage_1", null, this));
			waiting.remove(0);
			break;
		case 1:
			pageName = Ctrl.trans.aboutus();
			title.setHTML("<h3>" + pageName + "</h3>");
			if (!nameAsked) {
				// String[] altNames = { Ctrl.trans.pwCorporate() };
				holder.setWidget(1, 0, new CreateContainer(Ctrl.trans.aboutus(), "listing_2", null, this));
				nameAsked = true;
			} else {
				String[][] suggestions = { { Ctrl.trans.pwCorporate(), "detail_1" },
						{ Ctrl.trans.pwOurMission(), "detail_2" }, { Ctrl.trans.pwOurVission(), "detail_3" },
						{ Ctrl.trans.pwOurMissionAndVission(), "detail_1" },
						{ Ctrl.trans.pwOurHistory(), "detail_2" } };
				holder.setWidget(1, 0, new PageItems("detail_3", Ctrl.trans.pwSuggestions(), suggestions, this,
						parentUri, parentName));
				waiting.remove(0);
				nameAsked = false;

			}
			break;
		case 2:
			pageName = Ctrl.trans.products();
			if (parentName != null)
				pageName = parentName;

			title.setHTML("<h3>" + pageName + "</h3>");

			if (!nameAsked) {
				holder.setWidget(1, 0, new CreateContainer(Ctrl.trans.products(), "listing_1", null, this));

				nameAsked = true;
			} else {
				holder.setWidget(1, 0,
						new PageItems("detail_2", Ctrl.trans.pwAddPagesUnder(), null, this, parentUri, pageName));
				nameAsked = false;
				waiting.remove(0);
			}
			break;
		case 3:
			pageName = Ctrl.trans.projects();
			if (parentName != null)
				pageName = parentName;

			title.setHTML("<h3>" + pageName + "</h3>");

			String[] alts = { Ctrl.trans.services(), Ctrl.trans.solutions() };

			if (!nameAsked) {
				holder.setWidget(1, 0, new CreateContainer(Ctrl.trans.projects(), "listing_1", alts, this));
				nameAsked = true;
			} else {

				holder.setWidget(1, 0,
						new PageItems("detail_3", Ctrl.trans.pwAddPagesUnder(), null, this, parentUri, pageName));
				nameAsked = false;
				waiting.remove(0);
			}
			break;
		case 4:
			pageName = Ctrl.trans.references();
			if (parentName != null)
				pageName = parentName;

			title.setHTML("<h3>" + pageName + "</h3>");

			if (!nameAsked) {
				holder.setWidget(1, 0, new CreateContainer(Ctrl.trans.references(), "linklisting_2", null, this));
				nameAsked = true;
			} else {
				createLinkContainer(parentUri.substring(parentUri.lastIndexOf("/") + 1));
				holder.setWidget(1, 0, new PageLinks(Ctrl.trans.pwAddLinksUnder(), this, parentUri));
				nameAsked = false;
				waiting.remove(0);
			}
			break;

		case 5:
			title.setHTML("<h3>" + Ctrl.trans.contactUs() + "</h3>");
			holder.setWidget(1, 0, new CreateContainer(Ctrl.trans.contactUs(), "contactus", null, this));
			waiting.remove(0);
			break;

		default:
			break;
		}

	}

	public void pageAdded(String name, String uri) {
		goNextStep(name, uri);
	}

	private void createLinkContainer(String uri) {
		LinksDao.breed("/_/links/public/" + uri, Data.WRITING_PUBLIC_MASK, "/_/links/public", new ContainersResponse() {
			@Override
			public void ready(Containers value) {
				// Window.alert("Link container created");
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				if (statusCode == 500) {
					// Window.alert("Link exists");
					return;
				}
				Window.alert(statusText);
			}
		});

	}

}