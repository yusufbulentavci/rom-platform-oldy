package com.bilgidoku.rom.site.yerel.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.bilgidoku.rom.gwt.client.util.help.NeedHelp;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabMailToolbar extends SimplePanel implements NeedHelp {

	// public final SiteToolbarButton btnReload = new
	// SiteToolbarButton("/_local/images/common/refresh.png",
	// Ctrl.trans.reloadDesc(), "", "");
	public final SiteToolbarButton btnDelete = new SiteToolbarButton(Ctrl.trans.delete(), Ctrl.trans.deleteDesc(), "");
	public final SiteToolbarButton btnforward = new SiteToolbarButton(Ctrl.trans.forward(), Ctrl.trans.forwardDesc(),
			"");
	public final SiteToolbarButton btnShowOrg = new SiteToolbarButton(Ctrl.trans.showOriginal(), "", "");
	public final SiteToolbarButton btnSpam = new SiteToolbarButton("Spam", "", "");
	public final SiteToolbarButton btnNotSpam = new SiteToolbarButton(Ctrl.trans.notSpam(), "", "");

	public final MenuBar menuMark = new MenuBar(true);
	public final MenuBar menuMove = new MenuBar(true);
	public final MenuBar menuReply = new MenuBar(true);

	// public final SiteToolbarButton moreActions = new
	// SiteToolbarButton("More", "default");

	private TabMailView mailView;
	private final boolean isReadingMail;
	private Widget[] btns;

	public TabMailToolbar(TabMailView mv, String type, boolean isOneItem) {

		this.mailView = mv;
		this.isReadingMail = isOneItem;

		loadMenus();
		menuMark.setAutoOpen(false);
		menuReply.setAutoOpen(false);
		menuMove.setAutoOpen(false);

		forDelete();
		forForward();
		// forReload();
		forShowOrg();
		forSpam();
		forNotSpam();

		btns = getBtns(type);
		this.add(ClientUtil.getToolbar(btns, 20));

	}

	@Override
	public Helpy[] helpies() {
		// return new Helpy[] { btnReload.getHelpy(), btnDelete.getHelpy(),
		// btnforward.getHelpy(), btnShowOrg.getHelpy(),
		// btnSpam.getHelpy(), btnNotSpam.getHelpy() };
		return new Helpy[] {};

	}

	private Widget[] getBtns(String type) {

		List<Widget> btns = new ArrayList<Widget>();
		// btns.add(btnReload);
		btns.add(btnforward);
		btns.add(btnDelete);
		btns.add(menuReply);
		btns.add(menuMove);
		btns.add(menuMark);
		btns.add(btnShowOrg);
		// btns.add(btnSpam);
		// btns.add(btnNotSpam);
		// btns.add(btnMarkAsRead);
		// btns.add(btnMarkAsUnRead);

		if (type.equals("draft") || type.equals("sent")) {
			btns.remove(btnNotSpam);
			btns.remove(btnSpam);
		}

		if (type.equals("spam")) {
			btns.remove(btnSpam);
		}

		if (isReadingMail) {
			// btns.remove(btnReload);
		}

		return btns.toArray(new Widget[btns.size()]);
	}

	private void forNotSpam() {
		btnNotSpam.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void forSpam() {
		btnSpam.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			}
		});

	}

	private void forShowOrg() {
		btnShowOrg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;

				// MailWrap mail = list.iterator().next();
				// new ShowOrgDialog(mail.getBody());

				for (MailWrap mailWrap : list) {
					// replyToAll(mailWrap);
					new ShowOrgDialog(mailWrap.getBody());
					break;
				}

			}
		});

	}

	private void loadMenus() {

		loadMenuMove();

		MenuBar replyMenu = new MenuBar(true);
		replyMenu.addItem(new MenuItem(Ctrl.trans.replyToSender(), forReply()));
		replyMenu.addItem(new MenuItem(Ctrl.trans.replyToAll(), forReplyToAll()));
		menuReply.addItem(new MenuItem(Ctrl.trans.reply(), replyMenu));

		MenuBar markMenu = new MenuBar(true);
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsRead(), forMarkAsRead()));
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsUnRead(), forMarkAsUnRead()));
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsImportant(), forMarkAsImportant()));
		markMenu.addItem(new MenuItem(Ctrl.trans.markAsUnImportant(), forMarkAsUnImportant()));
		menuMark.addItem(new MenuItem(Ctrl.trans.markAs(), markMenu));

	}

	private void loadMenuMove() {
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
		});

	}

	private ScheduledCommand forMove(final String target) {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				for (MailWrap mailWrap : list) {
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
				mailView.moved(email, target);
			}
		});

	}

	private ScheduledCommand forReplyToAll() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				for (MailWrap mailWrap : list) {
					replyToAll(mailWrap);
				}
			}
		};
		return cmd;
	}

	private ScheduledCommand forReply() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				for (MailWrap mailWrap : list) {
					replyTo(mailWrap);
				}

			}

		};
		return cmd;
	}

	private void replyTo(MailWrap mail) {
		TabNewMail tw = new TabNewMail("Re:" + mail.getSubject(), mail.getFrom(), null, mail, false, true);
		Ctrl.openTab("Re:" + mail.getUri(), "Re:" + mail.getSubject(), tw, Data.MAIL_COLOR);

	}

	private void replyToAll(MailWrap mail) {
		TabNewMail tw = new TabNewMail("Re:" + mail.getSubject(), mail.getFrom(), mail.getCc(), mail, false, true);
		Ctrl.openTab("Re:" + mail.getUri(), "Re:" + mail.getSubject(), tw, Data.MAIL_COLOR);
	}

	private void forward(MailWrap mail) {
		TabNewMail tw = new TabNewMail("Fwd:" + mail.getSubject(), null, null, mail, true, false);
		Ctrl.openTab("Fwd:" + mail.getUri(), "Fwd:" + mail.getSubject(), tw, Data.MAIL_COLOR);
	}

	private ScheduledCommand forMarkAsImportant() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				for (MailWrap mailWrap : list) {
					markAsImportant(mailWrap);
				}

			}

		};
		return cmd;
	}

	private ScheduledCommand forMarkAsUnImportant() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				for (MailWrap mailWrap : list) {
					markAsUnImportant(mailWrap);
				}

			}

		};
		return cmd;
	}

	protected void markAsUnImportant(final MailWrap email) {
		if (!email.isRead())
			return;
		MailsDao.important(false, email.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {
				mailView.markAsImportant(email);
			}
		});
	}

	private void markAsImportant(final MailWrap email) {
		if (!email.isRead())
			return;
		MailsDao.important(true, email.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {
				mailView.markAsImportant(email);
			}
		});
	}

	private ScheduledCommand forMarkAsUnRead() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				for (MailWrap mailWrap : list) {
					markAsUnRead(mailWrap);
				}

			}

		};
		return cmd;
	}

	private void markAsUnRead(final MailWrap email) {
		if (!email.isRead())
			return;
		MailsDao.seen(false, email.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {
				mailView.markAsUnReaded(email);
			}
		});
	}

	private void forForward() {
		btnforward.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				if (list.size() > 0) {
					forward(list.iterator().next());
				}
			}
		});
	}

	private ScheduledCommand forMarkAsRead() {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Set<MailWrap> list = mailView.getMailItems();
				if (list == null)
					return;
				for (MailWrap mailWrap : list) {
					markAsRead(mailWrap);
				}
			}

		};
		return cmd;
	}

	public void markAsRead(final MailWrap email) {
		if (email.isRead())
			return;

		MailsDao.seen(true, email.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {
				email.read();
				// update navigation
				mailView.markAsReaded(email);
			}
		});
	}

	private void forDelete() {
		btnDelete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<MailWrap> list = mailView.getMailItems();
				for (MailWrap mailWrap : list) {
					deleteMail(mailWrap);
				}

			}
		});

	}

	protected void deleteMail(final MailWrap mailWrap) {
		MailsDao.destroy(mailWrap.getUri(), new StringResponse() {
			@Override
			public void ready(String value) {
				mailView.mailDeleted(mailWrap);
			}
		});
	}

	protected void hideButtons() {
		buttons(false);
	}

	protected void showButtons() {
		buttons(true);
	}

	protected void buttons(boolean show) {
		// always show reload button (first button)
		if (btns.length >= 1)
			for (int i = 1; i < btns.length; i++) {
				Widget btn = btns[i];
				btn.setVisible(show);
			}
	}

	private class ShowOrgDialog extends DialogBox {
		private HTML body = new HTML();

		public ShowOrgDialog(String text) {
			Button btnClose = new Button(Ctrl.trans.close());
			btnClose.setStyleName("site-closebutton");
			btnClose.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ShowOrgDialog.this.hide();
				}
			});

			body.setHTML(text);
			ScrollPanel sp = new ScrollPanel();
			sp.add(body);
			sp.setSize("400px", "300px");

			VerticalPanel vp = new VerticalPanel();
			vp.add(sp);
			vp.add(btnClose);

			this.setWidget(vp);
			this.setText(Ctrl.trans.originalMail());
			this.getElement().getStyle().setProperty("zIndex", "100");
			this.setAutoHideEnabled(true);
			this.setModal(false);
			this.show();
			this.center();

		}

	}

}
