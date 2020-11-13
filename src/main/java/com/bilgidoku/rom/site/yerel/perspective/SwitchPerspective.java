package com.bilgidoku.rom.site.yerel.perspective;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.Role;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.common.TopButton;
import com.bilgidoku.rom.gwt.client.util.panels.TabOrders;
import com.bilgidoku.rom.shared.CRoleMask;
import com.bilgidoku.rom.site.yerel.ActionBarOne;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.SummaryActions;
import com.bilgidoku.rom.site.yerel.admin.DataReady;
import com.bilgidoku.rom.site.yerel.admin.TabHits;
import com.bilgidoku.rom.site.yerel.admin.TabOrg;
import com.bilgidoku.rom.site.yerel.admin.TabTranslations;
import com.bilgidoku.rom.site.yerel.comment.TabComments;
import com.bilgidoku.rom.site.yerel.exam.PnlExams;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;

public class SwitchPerspective extends Composite implements DataReady {

	private final TopButton btnPublish = new TopButton("/_local/images/podcast.png", "", Ctrl.trans.publishDesc(), "");

	private final TopButton btnRestore = new TopButton("/_local/images/undo.png", "", "Undo last publish", "");
	
	private final TopButton btnExams = new TopButton("/_local/images/question_white.png", "", "exams", "");

	private final TopButton btnComments = new TopButton("/_local/images/speech_bubble.png", "",
			Ctrl.trans.commentsWaiting() + " " + Ctrl.trans.comments(), "yorumlar_Onaybekleyenekrani.mp4");

	private final TopButton btnTran = new TopButton("/_local/images/dunya.png", "", Ctrl.trans.translations(), "");

	private final TopButton btnOrg = new TopButton("/_local/images/home2.png", "", Ctrl.trans.organization(), "");

	private final TopButton btnSummary = new TopButton("/_local/images/chart_.png", "", Ctrl.trans.summary(), "");

	private final TopButton btnOrders = new TopButton("/_local/images/sepet.png", "", Ctrl.trans.orders(), "");

	private final HTML pipe = new HTML(
			"<span style='font-weight:bolder; color:white;padding: 0 2px; font-size:16px;'>|</span>");
	private final TopButton[] adminButtons = { btnRestore, btnPublish, btnComments, btnTran, btnSummary, btnOrders,
			btnOrg, btnExams };
	private final TopButton[] designButtons = { btnTran, btnPublish };
	private final TopButton[] contentButtons = { btnTran, btnPublish, btnComments, btnExams };
	private final TopButton[] commonButtons = {};
	private final TopButton[] houseButtons = { };
	

	private final Widget[] allButtons = { btnOrg, btnPublish, btnComments, btnTran, btnSummary, btnOrders, btnRestore, btnExams, 
			pipe };

	SiteButton btnPerspAdmin = new SiteButton("/_local/images/pers_key.png", "", Ctrl.trans.administrationDesc(), "");
	SiteButton btnPerspCommon = new SiteButton("/_local/images/pers_user.png", "", Ctrl.trans.common(), "");
	SiteButton btnPerspContent = new SiteButton("/_local/images/pers_document.png", "", Ctrl.trans.contentDesc(), "");
	SiteButton btnPerspDesign = new SiteButton("/_local/images/pers_design.png", "", Ctrl.trans.designDesc(), "");

	
	
	private final Widget[] persButtons = { btnPerspCommon, btnPerspContent, btnPerspDesign, btnPerspAdmin};
	
	final HorizontalPanel vp = new HorizontalPanel();
	
	public SwitchPerspective() {

		forPublish();
		forComments();
		forOrders();
		forRestore();
		forTranslations();
		forOrg();
		forSummary();
		forExams();

		initWidget(ui());

	}

	private void forExams() {
		btnExams.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				Ctrl.openTab(Ctrl.trans.exams(), Ctrl.trans.exams(), new PnlExams(), Data.CONTENT_COLOR);
				
			}
		});

		
	}

	private HorizontalPanel ui() {

		btnPerspAdmin.setStyleName("site-toolbarbutton site-btn" + Data.HOME_COLOR);
		btnPerspCommon.setStyleName("site-toolbarbutton site-btn" + Data.MAIL_COLOR);
		btnPerspContent.setStyleName("site-toolbarbutton site-btn" + Data.CONTENT_COLOR);
		btnPerspDesign.setStyleName("site-toolbarbutton site-btn" + Data.DESIGN_COLOR);

		btnPerspAdmin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectButton(btnPerspAdmin);
				selectAdminPerspective();
			}
		});

		btnPerspCommon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectMailPerspective();
			}
		});

		btnPerspContent.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectContentPerspective();
			}
		});

		btnPerspDesign.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectDesignPerspective();
			}
		});

		
		vp.add(btnPerspCommon);
		vp.add(btnPerspContent);
		vp.add(btnPerspDesign);
		
//		int roles = Integer.parseInt(RomEntryPoint.com().get("roles"));
//		if (CRoleMask.hasRole(roles, CRoleMask.admin) ) {
//			vp.add(btnPerspAdmin);
//		}

		
		vp.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		return vp;

	}

	private void removePerspectiveButtons() {
		for (int i = 0; i < allButtons.length; i++) {
			removeFromTop(allButtons[i]);
		}
	}

	private void forPublish() {
		btnPublish.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Ctrl.startWaiting();
				InfoDao.publish(Ctrl.info().uri, new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(Ctrl.trans.published());
					}
				});
			}
		});
	}

	@Override
	public void hitsDataReady(DataTable lineData, DataTable mapData, DataTable pieData, boolean hasNextWeek) {
		TabHits tabHits = new TabHits(lineData, mapData, pieData, hasNextWeek);
		Ctrl.openTab("dailyhits", Ctrl.trans.statistics(), tabHits, Data.DEFAULT_COLOR);

	}

	private void forOrg() {
		btnOrg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabOrg org = new TabOrg();
				Ctrl.openTab("org", Ctrl.trans.organization(), org, Data.DEFAULT_COLOR);

			}
		});

	}

	private void forTranslations() {
		btnTran.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabTranslations tw = new TabTranslations();
				Ctrl.openTab("Translations", Ctrl.trans.translations(), tw, Data.DEFAULT_COLOR);
			}
		});

	}

	private void forComments() {
		btnComments.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabComments tw = new TabComments(null, Ctrl.trans.approvalWaiting(), "waitingapproval");
				Ctrl.openTab("waitingapproval", Ctrl.trans.approvalWaiting(), tw, Data.DEFAULT_COLOR);
			}
		});
	}

	private void forRestore() {
		btnRestore.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm(
						"Are you sure you want to restore? All files and all writings will go back to the last published state! You will lose last changes!!!")) {
					InfoDao.restore(Ctrl.info().uri, new StringResponse() {
						@Override
						public void ready(String value) {
							Ctrl.setStatus("restored");
						}
					});
				}

			}
		});

	}

	private void forOrders() {
		btnOrders.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabOrders orders = new TabOrders("one");

				Ctrl.openTab("orders", Ctrl.trans.orders(), orders, Data.DEFAULT_COLOR);

			}
		});

	}

	private void forSummary() {
		btnSummary.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Ctrl.startWaiting();
				SummaryActions sa = new SummaryActions(true);
				sa.openSummary();
			}
		});
	}

	protected void selectButton(SiteButton text2) {
		for (int i = 0; i < persButtons.length; i++) {
			Widget b = persButtons[i];
			b.removeStyleName("selected-btn");
		}
		if (text2 != null)
			text2.addStyleName("selected-btn");
	}

	public void selectMailPerspective() {

		removePerspectiveButtons();
		for (int i = 0; i < commonButtons.length; i++) {
			insertToTop(commonButtons[i]);
		}
		Ctrl.gotoMail();

	}
	
	private void selectDesignPerspective() {
		removePerspectiveButtons();
		for (int i = 0; i < designButtons.length; i++) {
			insertToTop(designButtons[i]);
		}
		Ctrl.gotoDesign();

	}
	
	private void selectHousePerspective() {
		removePerspectiveButtons();
		for (int i = 0; i < houseButtons.length; i++) {
			insertToTop(houseButtons[i]);
		}
		Ctrl.gotoHouse();

	}

	public void selectContentPerspective() {
		removePerspectiveButtons();
		for (int i = 0; i < contentButtons.length; i++) {
			insertToTop(contentButtons[i]);
		}
		Ctrl.gotoContent();
	}

	private void selectAdminPerspective() {
		removePerspectiveButtons();
		for (int i = 0; i < adminButtons.length; i++) {
			insertToTop(adminButtons[i]);
		}
		Ctrl.gotoAdmin();
	}

	void insertToTop(Widget wdt) {
		ActionBarOne top = (ActionBarOne) RomEntryPoint.cm().comp("+actionbar", null);
		if (top == null)
			return;

		top.addToButtons(wdt);
	}

	private void removeFromTop(Widget widget) {
		ActionBarOne top = (ActionBarOne) RomEntryPoint.cm().comp("+actionbar", null);
		if (top == null)
			return;
		top.removeButton(widget);
	}

	public void update() {
		
		
		 if (Role.isDesigner()) {
		 vp.add(btnPerspDesign);
		 }


		vp.remove(btnPerspAdmin);
		
		
		int roles = Integer.parseInt(RomEntryPoint.com().get("roles"));
		if (CRoleMask.hasRole(roles, CRoleMask.admin) ) {
			vp.add(btnPerspAdmin);
		}

		if (CRoleMask.hasRole(roles, CRoleMask.admin) ) {
			vp.add(btnPerspAdmin);
		}

	}

}
