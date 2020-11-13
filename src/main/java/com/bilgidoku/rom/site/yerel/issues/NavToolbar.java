package com.bilgidoku.rom.site.yerel.issues;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Issues;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesResponse;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.site.yerel.common.NavTreeItem;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class NavToolbar extends NavToolbarBase {

	private final ListBox lbStates = new ListBox();

	private final SiteToolbarButton btnNewEvent = new SiteToolbarButton("/_local/images/common/calendar.png", "",
			Ctrl.trans.add(Ctrl.trans.event()), "");

	private NavIssues nav;

	public NavToolbar(NavIssues nav) {
		super();
		this.nav = nav;
		loadStates();
		Widget[] btns = { btnNew, btnNewEvent, btnEdit, btnDelete, btnReload, lbStates };
		this.add(ClientUtil.getToolbar(btns, 6));

		forStateChange();
		forNewEvent();
	}

	private void forNewEvent() {
		btnNewEvent.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final NewEvntDlg dlg = new NewEvntDlg(null);
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (!dlg.id.equals("0")) {
							reloadContainer(null);
						}
					}
				});
				dlg.show();
				dlg.center();

			}
		});
	}

	// private void filter() {
	// String val = lbStates.getSelectedValue();
	// populate(val.equals("closed"), val.equals("resolved"),
	// val.equals("mine"), val.equals("notassigned"),
	// !val.equals("notassigned"));
	// }

	private void forStateChange() {
		lbStates.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				populate(false);
			}
		});

	}

	private void loadStates() {
		lbStates.addItem(Ctrl.trans.assignedToMe(), "mine");
		lbStates.addItem(Ctrl.trans.waitingIssues(), "waiting");
		lbStates.addItem(Ctrl.trans.closedIssues(), "closed");
		lbStates.addItem(Ctrl.trans.resolvedIssues(), "resolved");		
		lbStates.addItem(Ctrl.trans.notYetAssigned(), "notassigned");
		lbStates.addItem(Ctrl.trans.tomorrow(), "tomorrow");
		lbStates.addItem(Ctrl.trans.thisWeek(), "thisweek");
	}

	private List<String[]> users = null;
	private List<Issues> waitingToEdit = new ArrayList<Issues>();

	private void getUsers() {
		InitialsDao.getusers("/_/_initials", new ArrayResponse<String>() {
			@Override
			public void array(List<String[]> myarr) {
				users = myarr;
				open();
			}
		});
	}

	private void getIssue(final String uri) {
		IssuesDao.get(uri, new IssuesResponse() {
			@Override
			public void ready(Issues value) {
				waitingToEdit.add(value);
				open();
			}
		});
	}

	protected void open() {
		if (users == null)
			return;
		if (waitingToEdit.isEmpty())
			return;
		Issues iss = waitingToEdit.remove(waitingToEdit.size() - 1);
		TabIssue tw = new TabIssue(iss, users);
		Ctrl.openTab(iss.uri, iss.title, tw, Data.MAIL_COLOR);
	}

	@Override
	public void deleteItem() {
		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null)
			return;
		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;
		final SiteTreeItemData issue = (SiteTreeItemData) toDel.getUserObject();
		IssuesDao.destroy(issue.getUri(), new StringResponse() {
			@Override
			public void ready(String arg0) {
				toDel.remove();
				Ctrl.closeTab(issue.getUri());
			}
		});
	}

	@Override
	public void newItem() {
		InitialsDao.getusers("/_/_initials", new ArrayResponse<String>() {
			@Override
			public void array(List<String[]> myarr) {
				final NewIssueDlg dlg = new NewIssueDlg(myarr);
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (!dlg.id.equals("0")) {
							reloadContainer(null);
							// IssuesDao.get(dlg.id, new IssuesResponse() {
							// public void ready(Issues value) {
							// nav.addLe(value);
							// };
							// });
						}
					}
				});
			}
		});
	}

	@Override
	public void renameItem() {
		// TODO Auto-generated method stub

	}

	@Override
	public void newContainer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reloadContainer(TreeItem item) {
		lbStates.setSelectedIndex(0);
		populate(true);
	}

	public void openIssue() {
		TreeItem selItem = nav.getSelectedItem();
		if (selItem == null)
			return;

		SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
		getIssue(selNode.getUri());
		getUsers();
	}

	@Override
	public void editSelectedItem() {
		TreeItem selItem = nav.getSelectedItem();
		if (selItem == null)
			return;

		SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
		editItem(selNode.getUri());
	}

	@Override
	public void editItem(String uri) {
		getIssue(uri);
		getUsers();
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

	private void populate(final boolean mine) {
		getTree().clear();
		getTree().removeItems();
		final String selected = lbStates.getSelectedValue();

		final boolean mineSel = mine || selected.equals("mine");
		final boolean tomorrow = selected.equals("tomorrow");
		final boolean thisweek = selected.equals("thisweek");

		if (mineSel || tomorrow || thisweek) {
			String s = null;
			String e = null;
			if (tomorrow || thisweek) {
				// bugün
				Date start = new Date();
				CalendarUtil.resetTime(start);

				Date end = CalendarUtil.copyDate(start);
				CalendarUtil.addDaysToDate(end, 1);

				if (tomorrow) {
					CalendarUtil.addDaysToDate(start, 1);
					CalendarUtil.addDaysToDate(end, 1);
				} else if (thisweek) {
					CalendarUtil.addDaysToDate(end, 7);
				}
				s = ClientUtil.fmtSqlDate(start);
				e = ClientUtil.fmtSqlDate(end);
				
			}
			IssuesDao.listmine(true, true, s, e, "/_/issues",
					new IssuesResponse() {
						@Override
						public void array(List<Issues> value) {
							for (int i = 0; i < value.size(); i++) {
								Issues iss = value.get(i);
								addLe(iss);
							}
						}
					});
			return;
		}

		// on going
		Boolean closed = null;				
		Boolean resolved = null;
		//final Boolean notAssigned = selected.equals("notassigned");
		
		if (selected.equals("closed")) 
			closed = false;
		
		if (selected.equals("resolved"))
			resolved = false;
		
		if (selected.equals("waiting")) {
			closed = true;
			resolved = true;
		}			

		IssuesDao.list(closed, resolved, "/_/issues", new IssuesResponse() {
			public void array(List<Issues> value) {
				for (int i = 0; i < value.size(); i++) {
					Issues iss = value.get(i);
					if (!selected.equals("notassigned")) {
						addLe(iss);
						continue;
					}

					if (iss.assigned_to == null || iss.assign_date.isEmpty())
						addLe(iss);

				}
			}
		});

	}

	private Tree getTree() {
		return nav.getTree();
	}

	public void addLe(Issues iss) {

		String itemImg = "/_local/images/common/iss_grey.png";
		String title = iss.title;

		if (iss.cls.equals("task")) {
			if (iss.resolve_date != null && !iss.resolve_date.isEmpty()) {
				itemImg = "/_local/images/common/iss_green.png";
			} else if (iss.close_date != null && !iss.close_date.isEmpty()) {
				itemImg = "/_local/images/common/iss_blue.png";
			} else if (iss.assign_date == null || iss.assign_date.isEmpty()) {
				itemImg = "/_local/images/common/iss_pink.png";
			} else {
				if (iss.weight <= 10)
					itemImg = "/_local/images/common/iss_grey.png";
				else if (iss.weight <= 100)
					itemImg = "/_local/images/common/iss_yellow.png";
				else if (iss.weight <= 1000)
					itemImg = "/_local/images/common/iss_red.png";
			}
		} else if (iss.cls.equals("event")) {
			itemImg = "/_local/images/common/event.png";			
			title = ClientUtil.fmtTime(iss.due_start) + " ";
			
			if (!ClientUtil.isToday(iss.due_start)) {
				title = title + ClientUtil.fmtShortDate(iss.due_start) + " ";	
			}
			
			title = title + iss.title;
		}

		SiteTreeItem node = new NavTreeItem(title, itemImg, null, iss.uri, false, true, true);
		node.setUserObject(new SiteTreeItemData(title, iss.uri, false, iss.uri));
		getTree().addItem(node);
		nav.deletable(node);
		nav.editable(node);

	}

}
