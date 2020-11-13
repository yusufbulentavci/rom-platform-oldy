package com.bilgidoku.rom.site.yerel.mail;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEdit;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEditHandler;
import com.bilgidoku.rom.shared.CRoleMask;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.MailBoxTreeItem;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.site.yerel.mail.core.EmailMbMeta;
import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavMail extends NavBase implements HasContainer {

	public NavMail() {
		super("/_local/images/common/folder.png", "/_local/images/common/folder.png", "/_local/images/common/folder_key.png", true, 1);
	}

	@Override
	public void addContainers() {

		getTree().removeItems();
		getTree().clear();

		getToolbar().buttonsStates(false);

		ContainersDao.listing("rom", "mails", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {

				String[] sortList = new String[value.size()];
				for (int i = 0; i < value.size(); i++) {
					sortList[i] = new String(value.get(i).uri);
				}

				java.util.Arrays.sort(sortList);

				for (int j = 0; j < sortList.length; j++) {
					Containers con = findInList(value, sortList[j]);
					addContainer(null, con, false, false);
				}

			}

			private Containers findInList(List<Containers> value, String string) {
				if (value == null)
					return null;

				Containers found = null;
				for (int i = 0; i < value.size(); i++) {
					if (value.get(i).uri.equals(string)) {
						found = value.get(i);
						break;
					}
				}
				return found;
			}
		});
	}

	@Override
	public void addContainer(TreeItem parent, Containers con, boolean isDeletable, boolean isEditable) {
		String conImg = containerImage;
		if (!CRoleMask.maskIsPublic(con.mask))
			conImg = containerPrivateImage;

		EmailMbMeta mm = new EmailMbMeta(con.nesting);
		String text = getTitle(con.uri, mm.getUnseen());

		MailBoxTreeItem node = new MailBoxTreeItem(text + "", conImg);
		node.setUserObject(new MailTreeItemData(text, con.uri, true, con.uri_prefix, mm));
		node.addItem(new Label(""));
		node.setState(false);

		if (parent == null)
			getTree().addItem(node);
		else
			parent.addItem(node);

		node.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				getToolbar().editSelectedItem();
			}
		});

		node.addTreeItemEditHandler(new TreeItemEditHandler() {
			@Override
			public void editTreeItem(TreeItemEdit event) {
				getToolbar().editSelectedItem();
			}
		});

	}

	@Override
	public NavToolbarBase createToolbar() {
//		NavMailToolbar tb = new NavMailToolbar(this);
//		return tb;
		return null;
	}

	public void mailRead(String mailboxUri) {
		SiteTreeItem item = findTreeItem(mailboxUri);
		if (item == null)
			return;
		MailTreeItemData data = (MailTreeItemData) item.getUserObject();
		data.meta.readMail();
		item.setText(getTitle(mailboxUri, data.meta.getUnseen()));

	}

	private String getTitle(String mailboxUri, int unseen) {
		String text = mailboxUri.substring(mailboxUri.lastIndexOf("/") + 1);
		// if (unseen > 0)
		text = text + " (" + unseen + ")";

		return text;
	}

	public void mailUnRead(String mailboxUri) {
		TreeItem item = findTreeItem(mailboxUri);
		if (item == null)
			return;
		MailTreeItemData data = (MailTreeItemData) item.getUserObject();
		data.meta.unreadMail();
		item.setText(getTitle(mailboxUri, data.meta.getUnseen()));

	}

	private SiteTreeItem findTreeItem(String mailboxUri) {
		SiteTreeItem item = null;
		for (int i = 0; i < getTree().getItemCount(); i++) {
			MailTreeItemData data = (MailTreeItemData) getTree().getItem(i).getUserObject();
			if (data.getUri().equals(mailboxUri)) {
				item = (SiteTreeItem) getTree().getItem(i);
				break;
			}
		}
		return item;
	}

	public void mailDeleted(String mailboxUri, MailWrap email) {
		TreeItem item = findTreeItem(mailboxUri);
		if (item == null)
			return;
		MailTreeItemData data = (MailTreeItemData) item.getUserObject();
		data.meta.mailDeleted();
		if (email.isRead()) {
			data.meta.readMail();
		}

		item.setText(getTitle(mailboxUri, data.meta.getUnseen()));

	}

	public void mailAdded(String mailboxUri, MailWrap email) {
		TreeItem item = findTreeItem(mailboxUri);
		if (item == null)
			return;
		
		MailTreeItemData data = (MailTreeItemData) item.getUserObject();
		data.meta.mailAdded();
		
		if (!email.isRead()) {
			data.meta.unreadMail();
		}
		
		item.setText(getTitle(mailboxUri, data.meta.getUnseen()));

	}

	@Override
	public void add(Widget w) {
		getToolbar().add(w);
		
	}

	@Override
	public void clear() {
		getToolbar().clear();
		
	}

	@Override
	public Iterator<Widget> iterator() {
		return getToolbar().iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return getToolbar().remove(w);
	}

}
