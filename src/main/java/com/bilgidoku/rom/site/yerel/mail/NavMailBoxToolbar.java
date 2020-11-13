package com.bilgidoku.rom.site.yerel.mail;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.mail.core.EmailMbMeta;
import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class NavMailBoxToolbar extends Composite {
	private final SiteToolbarButton btnNew = new SiteToolbarButton("/_local/images/common/send_mail.png", "",
			Ctrl.trans.newItem(), "");

	private final SiteToolbarButton btnReload = new SiteToolbarButton("/_local/images/common/refresh.png", "",
			Ctrl.trans.reloadGroup(), "");

	public SiteToolbarButton btnDelete = new SiteToolbarButton("/_local/images/common/bin.png", "",
			Ctrl.trans.deleteItem(), "");

	public final SiteToolbarButton btnSpam = new SiteToolbarButton("/_local/images/common/exclamation.png", "", "Spam",
			"");

	private SiteToolbarButton btnPrev = new SiteToolbarButton("/_local/images/common/go_left.png", "",
			Ctrl.trans.previousPage(), "");
	private SiteToolbarButton btnNext = new SiteToolbarButton("/_local/images/common/go_right.png", "",
			Ctrl.trans.nextPage(), "");

	public SiteToolbarButton btnEdit = new SiteToolbarButton("/_local/images/common/pencil.png", "",
			Ctrl.trans.editItem(), "");

	private final ListBox lbMailBoxes = new ListBox();

	public final MenuBar menuMark = new MenuBar(true);
	public final MenuBar menuMove = new MenuBar(true);

	private NavMailBox navMailBox;

	public NavMailBoxToolbar(NavMailBox navMailBox) {

		this.navMailBox = navMailBox;
		loadMenus();
		loadMailBoxes();

		forNext();
		forPrevious();

		forSelectMailBox();
		forNewMail();
		forReload();
		forDelete();
		forSpam();
		forEdit();

		Widget[] btns = { btnNew, btnReload, btnEdit, btnDelete, lbMailBoxes, menuMark, menuMove, btnPrev, btnNext };
		initWidget(ClientUtil.getToolbar(btns, 5));

	}

	private void forSelectMailBox() {
		lbMailBoxes.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				navMailBox.mailBoxChanged(lbMailBoxes.getSelectedValue());				
			}
		});		
	}

	private void forEdit() {
		btnEdit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				navMailBox.readMail(null);
			}
		});

	}

	private void forPrevious() {
		btnPrev.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				navMailBox.loadPreviousPage();

			}
		});

	}

	private void forNext() {
		btnNext.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				navMailBox.loadNextPage();
			}
		});

	}

	public void loadMailBoxes() {
		lbMailBoxes.clear();
		ContainersDao.listing("rom", "mails", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					EmailMbMeta mm = new EmailMbMeta(con.nesting);
					String text = getTitle(con.uri, mm.getUnseen());
					lbMailBoxes.addItem(text, con.uri);
					if (con.uri.equals(navMailBox.mailboxUri))
						lbMailBoxes.setSelectedIndex(i);
					
				}
			}

		});

	}

	private String getTitle(String mailboxUri, int unseen) {
		String text = mailboxUri.substring(mailboxUri.lastIndexOf("/") + 1);
		// if (unseen > 0)
		text = text + " (" + unseen + ")";

		return text;
	}

	protected void updateUI(int from, int to, int totalMailCount) {
		// pageInfo.setHTML(" " + from + " to " + to + " ");

		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);

		if (from <= 1) {
			btnPrev.setEnabled(false);
		}

		if (to >= totalMailCount) {
			btnNext.setEnabled(false);
		}

	}

	private void loadMenus() {

		MenuBar markMenu = new MenuBar(true);
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsRead(), forMarkAsRead()));
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsUnRead(), forMarkAsUnRead()));
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsImportant(), forMarkAsImportant()));
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsUnImportant(), forMarkAsUnImportant()));
		menuMark.addItem(new MenuItem(Ctrl.trans.markAs(), markMenu));

		ContainersDao.listing("rom", "mails", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				MenuBar moveto = new MenuBar(true);
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					String text = con.uri.substring(con.uri.lastIndexOf("/") + 1);
					moveto.addItem(new MenuItem(text, forMove(con.uri)));
				}
				menuMove.addItem(new MenuItem("Move", moveto));
			}

			private ScheduledCommand forMove(final String target) {
				Command cmd = new Command() {
					@Override
					public void execute() {
						Set<MailWrap> list = navMailBox.getMailItems();
						if (list == null || list.size() < 1)
							return;

						counter = 0;
						totalCount = list.size();
						for (Iterator<MailWrap> iterator = list.iterator(); iterator.hasNext();) {
							MailWrap mailWrap = (MailWrap) iterator.next();
							moveTo(mailWrap, target);							
						}

					}
				};
				return cmd;
			}
			
			private void moveTo(final MailWrap email, final String target) {
				MailsDao.changemailbox(target, email.getUri(), new StringResponse() {
					@Override
					public void ready(String value) {
						counter++;
						if (counter >= totalCount) {
							navMailBox.mailsMoved();
						}
					}
				});

			}


		});
	}

	private ScheduledCommand forMarkAsUnImportant() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				
				Set<MailWrap> list = navMailBox.getMailItems();
				if (list == null || list.size() < 1)
					return;

				counter = 0;
				totalCount = list.size();
				for (Iterator<MailWrap> iterator = list.iterator(); iterator.hasNext();) {
					MailWrap mailWrap = (MailWrap) iterator.next();
					markAsImportant(mailWrap, false);							
				}

			}

		};
		return cmd;
	}

	private ScheduledCommand forMarkAsImportant() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				
				Set<MailWrap> list = navMailBox.getMailItems();
				if (list == null || list.size() < 1)
					return;

				counter = 0;
				totalCount = list.size();
				for (Iterator<MailWrap> iterator = list.iterator(); iterator.hasNext();) {
					MailWrap mailWrap = (MailWrap) iterator.next();
					markAsImportant(mailWrap, true);							
				}

			}

		};
		return cmd;
	}

	public void markAsImportant(final MailWrap email, final boolean isImportant) {
		MailsDao.important(isImportant, email.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {				
				counter++;
				if (counter >= totalCount) {
					navMailBox.markAsImportant(isImportant);
				}

			}
		});
	}

	private ScheduledCommand forMarkAsUnRead() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				
				Set<MailWrap> list = navMailBox.getMailItems();
				if (list == null || list.size() < 1)
					return;

				counter = 0;
				totalCount = list.size();
				for (Iterator<MailWrap> iterator = list.iterator(); iterator.hasNext();) {
					MailWrap mailWrap = (MailWrap) iterator.next();
					markAsRead(mailWrap, false);							
				}

			}

		};
		return cmd;
	}

	private ScheduledCommand forMarkAsRead() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				
				Set<MailWrap> list = navMailBox.getMailItems();
				if (list == null || list.size() < 1)
					return;

				counter = 0;
				totalCount = list.size();
				for (Iterator<MailWrap> iterator = list.iterator(); iterator.hasNext();) {
					MailWrap mailWrap = (MailWrap) iterator.next();
					markAsRead(mailWrap, true);							
				}

			}

		};
		return cmd;
	}

	public void markAsRead(final MailWrap email, final boolean isRead) {
		MailsDao.seen(isRead, email.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {				
				counter++;
				if (counter >= totalCount) {
					navMailBox.markAsReaded(isRead);
				}

			}
		});
	}

	private void forSpam() {
		// TODO Auto-generated method stub

	}

	private int counter = 0;
	private int totalCount = 0;

	private void forDelete() {
		btnDelete.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if (navMailBox.getMailItems() == null || navMailBox.getMailItems().size() < 1) {
					return;
				}
				counter = 0;
				totalCount = navMailBox.getMailItems().size();
				if (Window.confirm(Ctrl.trans.confirmDelete())) {
					Set<MailWrap> selectedItes = navMailBox.getMailItems();
					for (Iterator<MailWrap> iterator = selectedItes.iterator(); iterator.hasNext();) {
						MailWrap mailWrap = (MailWrap) iterator.next();
						deleteMail(mailWrap);
					}
				}				
			}
		});
		
	}

	protected void deleteMail(final MailWrap mailWrap) {
		MailsDao.destroy(mailWrap.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {
				counter++;
				if (counter >= totalCount) {
					navMailBox.mailsDeleted();
				}
			}
		});
	}

	private void forReload() {
		btnReload.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				navMailBox.reload();				
			}
		});	

	}

	private void forNewMail() {
		btnNew.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				TabNewMail tw = new TabNewMail(null, null, null, null);
				Ctrl.openTab("New Mail", Ctrl.trans.newMail(), tw, Data.MAIL_COLOR);				
			}
		});
	}
}
