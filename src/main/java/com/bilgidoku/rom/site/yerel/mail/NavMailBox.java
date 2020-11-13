package com.bilgidoku.rom.site.yerel.mail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Mails;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsResponse;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.yerel.ClientKnownError;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.mail.core.EmailMbMeta;
import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.MultiSelectionModel;

public class NavMailBox extends Composite implements HasContainer {
	// private final Images img = GWT.create(Images.class);
	private final MailCell mailCell = new MailCell();
	private final CellList<MailWrap> table = new CellList<MailWrap>(mailCell);

	// private List<MailWrap> pageMails;
	private static int PAGESIZE = 40;
	public String mailboxUri;

	// private HTML pageInfo = new HTML();

	// private NavMail nav;
	private final NavMailBoxToolbar toolbar;
	private int offset = 0;
	private int totalMailCount = 0;

	private final MultiSelectionModel<MailWrap> selectModel = new MultiSelectionModel<MailWrap>();
	protected MailWrap lastSelectedMail;

	public NavMailBox(String type) {
		this.mailboxUri = getUriFromType(type);
		this.toolbar = new NavMailBoxToolbar(this);

		table.setSelectionModel(selectModel);

		forReadMail();

		DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		dock.addNorth(toolbar, 55);
		dock.add(new ScrollPanel(table));
		dock.setStyleName("site-panels");

		getMailBoxData(mailboxUri);
		initWidget(dock);

	}

	private void getMailBoxData(final String mbUri) {
		ContainersDao.listing("rom", "mails", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (Iterator<Containers> iterator = value.iterator(); iterator.hasNext();) {
					Containers containers = (Containers) iterator.next();
					if (containers.uri.equals(mbUri)) {
						EmailMbMeta mm = new EmailMbMeta(containers.nesting);
						totalMailCount = mm.getCount();
						loadPage();
						break;
					}
				}
			}
		});

	}

	protected void loadPreviousPage() {
		if (offset - PAGESIZE >= 0) {
			offset = offset - PAGESIZE;
		}
		loadPage();

	}

	protected void loadNextPage() {
		if (offset < totalMailCount) {
			offset = offset + PAGESIZE;
		}
		loadPage();
	}

	private void forReadMail() {
		table.addCellPreviewHandler(new Handler<MailWrap>() {
			@Override
			public void onCellPreview(CellPreviewEvent<MailWrap> event) {
				if (event.getNativeEvent().getType().contains("click")) {
					lastSelectedMail = event.getValue();
					if (!event.getNativeEvent().getCtrlKey()) {
						readMail(lastSelectedMail);
					}
				}

			}
		});

	}

	private void loadPage() {
		MailsDao.list(offset, PAGESIZE, mailboxUri, new MailsResponse() {
			@Override
			public void array(List<Mails> myarr) {

				List<MailWrap> emails = new ArrayList<MailWrap>();
				// pageMails = emails;
				for (int i = 0; i < myarr.size(); i++) {
					Mails emailData = myarr.get(i);
					try {
						emails.add(new MailWrap(emailData));
					} catch (ClientKnownError e) {
						Sistem.printStackTrace(e);
					}
				}
				table.setRowCount(emails.size(), true);
				table.setRowData(emails);
				toolbar.updateUI(offset, offset + PAGESIZE, totalMailCount);

			}
		});
	}

	protected void readMail(MailWrap email) {
		if (email == null)
			email = lastSelectedMail;

		if (email == null)
			return;

		if (mailboxUri.indexOf("draft") > 0) {
			TabNewMail newMail = new TabNewMail(email.getSubject(), email.getTo(), email.getCc(), email);
			Ctrl.openTab(email.getUri(), Ctrl.trans.mail() + ":" + email.getSubject(), newMail, Data.MAIL_COLOR);
		} else {
			TabReadMail mail = new TabReadMail(email, mailboxUri, this);
			Ctrl.openTab(email.getUri(), Ctrl.trans.mail() + ":" + email.getSubject(), mail, Data.MAIL_COLOR);
		}

	}

	private String getUriFromType(String type) {
		String user=RomEntryPoint.com().get("user");
		return "/_/mails/" + user + "/" + type;
	}

	public Set<MailWrap> getMailItems() {
		@SuppressWarnings("unchecked")
		MultiSelectionModel<MailWrap> mu = ((MultiSelectionModel<MailWrap>) table.getSelectionModel());
		Set<MailWrap> set = mu.getSelectedSet();
		return set;
	}

	@SuppressWarnings("unchecked")
	private void clearSelection() {
		((MultiSelectionModel<MailWrap>) table.getSelectionModel()).clear();
	}

	private class MailCell extends AbstractCell<MailWrap> {
		@Override
		public void render(Context context, MailWrap row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant(
					"<div class='mailrow' style='padding:3px 1px; border-bottom: 1px solid #dedede; display:table;");

			if (!row.isRead()) {
				sb.appendHtmlConstant("font-weight:bold;");
			}

			sb.appendHtmlConstant("'>");

			if (row.hasAttachment()) {
				sb.appendHtmlConstant(
						"<img src='/_local/images/common/bullet_attach.png' style='padding: 0 1px;display:inline-block;'/>");
			} else {
				sb.appendHtmlConstant("<span style='width:15px;display:inline-block;'>&nbsp;</span>");
			}

			if (row.isImportant()) {
				sb.appendHtmlConstant(
						"<img src='/_local/images/common/star.gif' style='display:inline-block;padding:0 1px;'/>");
			} else {
				sb.appendHtmlConstant("<span style='width:12px;display:inline-block;'>&nbsp;</span>");
			}
			String type = "draft";
			if (type.equals("draft")) {
				String to = "";
				InternetAddress[] internetAddresses = row.getTo();
				if (internetAddresses == null || internetAddresses.length < 1) {
					to = "";
				}
				for (int i = 0; i < internetAddresses.length; i++) {
					InternetAddress addr = internetAddresses[i];
					to = ", " + MailUtil.mailShortFormat(addr.getPersonal(), addr.getAddress());
				}
				sb.appendHtmlConstant("<span style='width:170px;display:inline-block;'>" + to.substring(2) + "</span>");
			} else {
				String from = "";
				InternetAddress[] internetAddresses = row.getFrom();
				if (internetAddresses == null || internetAddresses.length < 1) {
					from = "";
				}

				for (int i = 0; i < internetAddresses.length; i++) {
					InternetAddress addr = internetAddresses[i];
					from = ", " + MailUtil.mailShortFormat(addr.getPersonal(), addr.getAddress());
				}
				sb.appendHtmlConstant(
						"<span style='width:170px;display:inline-block;'>" + from.substring(2) + "</span>");
			}

			sb.appendHtmlConstant(
					"<span style='width:190px;display:inline-block; padding: 2px 0 0 28px; font-size:smaller;'>"
							+ getShortComment(row.getSubject()) + "</span>");

			sb.appendHtmlConstant("</div>");

		}

		private String getShortComment(String comment) {
			if (comment == null || comment.isEmpty())
				return "";
			else if (comment.length() > 45)
				return comment.substring(0, 42) + "...";
			else
				return comment;
		}

	}

	public void mailsDeleted() {
		Ctrl.setStatus("mails deleted");
		reload();
	}

	public void reload() {
		loadPage();
		toolbar.loadMailBoxes();
	}

	public void mailsMoved() {
		Ctrl.setStatus("mails moved");
		reload();
	}

	public void markAsReaded(boolean isRead) {
		Ctrl.setStatus("mark as readed " + isRead);
		reload();
	}

	public void markAsImportant(boolean isImportant) {
		Ctrl.setStatus("mark as important " + isImportant);
		loadPage();
	}

	public MailWrap getMailItem() {
		return getMailItems().iterator().next();
	}

	public TreeItem getSelectedItem() {
		// old code
		return null;
	}

	public void markAsRead(MailWrap email) {
		toolbar.markAsRead(email, true);
		reload();
	}

	public void mailBoxChanged(String mailBoxUri) {
		this.mailboxUri = mailBoxUri;
		this.offset = 0;
		reload();

	}

	@Override
	public void addContainers() {
		loadPage();
	}

}
