package com.bilgidoku.rom.site.yerel;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Issues;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesResponse;
import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.gwt.araci.client.site.Info;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.araci.client.site.InfoResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsResponse;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.TopCb;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.yerel.contacts.TabContact;
import com.bilgidoku.rom.site.yerel.issues.TabIssue;
import com.bilgidoku.rom.site.yerel.writings.wizard.PageWizard;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class one extends RomEntryPoint {

	public final CompInfo info = new CompInfo("+one", 50, new String[] {},
			new String[] { "_wndtop", "+topwindow", "user", "+actionbar" }, new String[] { "userneed", "local" });

	public final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return comp;
		}
	};

	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.remove(ActionBar.factory);
		f.add(ActionBarOne.factory);
		f.add(factory);
		return f;
	}

	private CompBase comp = new CompBase() {
		public void initial() {
			start();
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}
	};

	public static int userRole;
	public static String userContactId;
	public static String userMail;
	public static List<String[]> users;

	private com.google.gwt.json.client.JSONObject userAgent;

	public one() {
		super("Rom Server One Application", true, null, true, true);

	}

	protected void start() {

		InfoDao.get("en", "/_/siteinfo", new InfoResponse() {
			@Override
			public void rawReady(JSONValue val) {
				Ctrl.setInfoObj(val.isObject());
				super.rawReady(val);
			}

			@Override
			public void ready(Info value) {
				// List<String> pers =
				// AuthorizationManager.getPerspectivesForUserRole();

				Ctrl ctrl = Ctrl.two();
				Ctrl.setInfo(value);
				RomEntryPoint.one.clear();
				RomEntryPoint.one.addToRootPanel(ctrl.ui());

				ActionBarOne top = (ActionBarOne) RomEntryPoint.cm().comp("+actionbar", null);
				top.gotoFirstPerspective();

				if (Window.getClientWidth() <= 800) {
					Ctrl.toggleMobile();
				}

				WritingsDao.get("en", "/", new WritingsResponse() {
					@Override
					public void ready(Writings value) {
						// ana sayfa var
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						new PageWizard(false);
					}

				});

			}

		});

		InitialsDao.getusers("/_/_initials", new ArrayResponse<String>() {
			@Override
			public void array(List<String[]> myarr) {
				users = myarr;
				String user = RomEntryPoint.com().get("user");
				for (int i = 0; i < myarr.size(); i++) {
					String[] u = myarr.get(i);
					// u[0] => userName treeitem.getText()
					// u[1] => contactId data.getUri()
					// role => data.getTitle()
					if (user.equals(u[0])) {
						int role = 0;
						try {
							role = Integer.parseInt(u[2]);
						} catch (Exception e) {
						}
						userRole = role;
						userContactId = u[1];

						userMail = RomEntryPoint.com().getMail();
					}

				}
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				Sistem.outln("no user");
				// Window.Location.replace("/");
			}
		});

		OturumIciCagriDao.userAgent(new JsonResponse() {
			@Override
			public void ready(Json value) {
				userAgent = value.getValue().isObject();
			}
		});

	}

	public static boolean isManager() {
		return ((userRole & (1 << 11)) != 0) ? true : false;
	}

	public static boolean isEditor() {
		return ((userRole & (1 << 10)) != 0) ? true : false;
	}

	public static boolean isDeskHelper() {
		return ((userRole & (1 << 9)) != 0) ? true : false;
	}

	public static boolean isDesk() {
		return ((userRole & (1 << 8)) != 0) ? true : false;
	}

	public static boolean isDesigner() {
		return ((userRole & (1 << 7)) != 0) ? true : false;
	}

	public static boolean isAdmin() {
		return ((userRole & (1 << 6)) != 0) ? true : false;
	}

	@Override
	protected TopCb createTopCb() {
		return new TopCb() {

			@Override
			public void gotoMessaging() {
				// chatPnl.show();
			}

			@Override
			public void gotoMails() {
				Ctrl.reloadMailNav();
				Ctrl.gotoMail();

			}

			@Override
			public void gotoIssue(String uri) {
				// Ctrl.focusIssues();
				IssuesDao.get(uri, new IssuesResponse() {
					@Override
					public void ready(Issues iss) {
						TabIssue tw = new TabIssue(iss, users);
						Ctrl.openTab(iss.uri, iss.title, tw, Data.MAIL_COLOR);

					}
				});
			}

			@Override
			public void gotoMyInfos(String[] mine) {
				TabContact tw = new TabContact(mine[1], true);
				String user = RomEntryPoint.com().get("user");
				Ctrl.openTab(mine[1], user, tw, Data.MAIL_COLOR);

			}

			@Override
			public void gotoNotifications() {
				Ctrl.gotoNotifications();
			}

		};

	}

}
