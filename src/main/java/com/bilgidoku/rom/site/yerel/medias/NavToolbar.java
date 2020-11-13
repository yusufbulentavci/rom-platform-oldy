package com.bilgidoku.rom.site.yerel.medias;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesResponse;
import com.bilgidoku.rom.gwt.client.util.browse.image.FileUploadDialog;
import com.bilgidoku.rom.gwt.client.util.browse.image.UploadFile;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.NewItemDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavToolbar extends NavToolbarBase implements UploadFile {

	private SiteToolbarButton btnFileUpload = new SiteToolbarButton("/_local/images/common/upload.png", "",
			Ctrl.trans.fileUpload(), "dosya-yukle.mp4");
	private final SiteToolbarButton btnImageSearch = new SiteToolbarButton("/_local/images/common/search_.png", "",
			Ctrl.trans.image_search(), "arama.mp4");

	private NavFiles nav;

	public NavToolbar(NavFiles wa) {
		super();

		this.btnNewContainer.setHelpy("dosya-klasorolusturma.mp4");
		this.btnRename.setHelpy("dosya-isimdegistirme.mp4");
		this.btnEdit.setHelpy("edit.mp4");

		this.nav = wa;
		forFileUpload();
		forSearch();
		Widget[] btns = { btnNewContainer, btnReload, btnFileUpload, ClientUtil.getSeperator(), btnEdit, btnDelete, btnRename,
				ClientUtil.getSeperator(), btnImageSearch, btnCopyLink };
		this.add(ClientUtil.getToolbar(btns, 11));
	}

	private void forFileUpload() {
		btnFileUpload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				newItem();
			}
		});
	}

	private void forSearch() {
		btnImageSearch.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Ctrl.focusSearch();
				// TabImageSearch tabSearch = new TabImageSearch();
				// Ctrl.closeAndOpenTab("imagesearch",
				// Ctrl.trans.image_search(), tabSearch, Data.CONTENT_COLOR);

			}
		});

	}

	@Override
	public void deleteItem() {
		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null)
			return;

		final SiteTreeItemData data = (SiteTreeItemData) toDel.getUserObject();

		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;

		if (data.isContainer())
			FilesDao.extinct(data.getUri(), new StringResponse() {
				public void ready(String value) {
					toDel.remove();
				}
			});
		else
			FilesDao.destroy(data.getUri(), new StringResponse() {
				public void ready(String value) {
					toDel.remove();
					String title = nav.getTree().getSelectedItemText();
					Ctrl.closeTab(title);
				}
			});

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
		// if (!ClientUtil.isImage(uri)) {
		// Window.open(ClientUtil.getAddress(uri, "media"), "_blank", "");
		// return;
		// }

		String title = uri.substring(uri.lastIndexOf("/") + 1);

		TabMedia tw = new TabMedia(uri);
		Ctrl.openTab(uri, title, tw, Data.CONTENT_COLOR);
	}

	@Override
	public void newItem() {
		final TreeItem toGo = nav.getSelectedItem();
		if (toGo == null)
			return;

		SiteTreeItemData cont = (SiteTreeItemData) toGo.getUserObject();
		if (!cont.isContainer()) {
			cont = (SiteTreeItemData) toGo.getParentItem().getUserObject();
		}
		FileUploadDialog dlg = new FileUploadDialog(this, cont.getUri());
		dlg.show();
	}

	@Override
	public void renameItem() {

		final TreeItem sel = nav.getSelectedItem();

		final SiteTreeItemData data = (SiteTreeItemData) sel.getUserObject();
		if (data.isContainer()) {
			Window.alert(Ctrl.trans.selectAnItem());
			return;
		}

		final String uri = data.getUri();
		String initial = "";
		if (uri.indexOf(".") <= 0)
			return;

		initial = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
		final String ext = uri.substring(uri.lastIndexOf(".") + 1);

		String path = uri.substring(0, uri.lastIndexOf("/"));

		final NewItemDlg dlg = new NewItemDlg(path, Ctrl.trans.rename(), initial, ext);
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {

			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				// clicked ok
				if (dlg.getUri() == null)
					return;

				String title = dlg.getNamed();
				String fixedTitle = dlg.getFixedTitle();

				if (title != null && !title.isEmpty() && fixedTitle != null && !fixedTitle.isEmpty()) {

					String uri = ((SiteTreeItemData) sel.getUserObject()).getUri();

					String newUri = uri.substring(0, uri.lastIndexOf("/") + 1) + fixedTitle + "." + ext;

					FilesDao.rename(newUri, uri, new StringResponse() {
						public void ready(String value) {
							reloadContainer(null);
						};
					});
				}
			}

		});

	}

	public void fileUploaded(String dbfs, String fileName) {
		// reload
		TreeItem toGo = nav.getSelectedItem();
		toGo.setState(false);
		toGo.setState(true);
	}

	@Override
	public void newContainer() {

		final NewItemDlg dlg = new NewItemDlg(nav.getHolderUriPrefix(), Ctrl.trans.newContainer(), true,
				Data.FILES_ROOT + "/", nav.getHolderUri());
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (dlg.getUri() != null && !dlg.getUri().isEmpty()) {
					FilesDao.breed(dlg.getUri(), Data.WRITING_PUBLIC_MASK, null, null, dlg.getDelegated(),
							new ContainersResponse() {
						public void ready(Containers value) {
							TreeItem parent = nav.getHolderContainer();
							if (dlg.getIsOnTop())
								parent = null;
							nav.addContainer(parent, value, true, false);
						}
					});

				}
			}

		});

	}

	@Override
	public void reloadContainer(TreeItem item) {
		boolean reload = false;
		if (item == null) {
			reload = true;
			item = nav.getHolderContainer();
		}

		// clear selection and set selected item as the "item"
		nav.getTree().setSelectedItem(null, false);
		nav.getTree().setSelectedItem(item);

		// if (!item.getState()) { item.setState(false, false); } else {
		if ((item.getChildCount() == 1 && item.getChild(0).getText().equals("!")) || reload) {
			// do not reload every time it expands, reload every time when
			// "reload"
			item.removeItems();
			getData(item);
		} else {
			item.setState(true, false);
		}

	}

	private List<Containers> cons = null;
	private List<Files> files = null;

	private void getData(final TreeItem parent) {
		SiteTreeItemData pd = (SiteTreeItemData) parent.getUserObject();

		ContainersDao.listsub(pd.getUri(), "/_/c", new ContainersResponse() {
			@Override
			public void array(List<Containers> value) {
				cons = value;
				dataReady(parent);
			}
		});

		FilesDao.list(Ctrl.infoLang(), 0, 1000, pd.getUri(), new FilesResponse() {
			public void array(List<Files> value) {
				files = value;
				dataReady(parent);
			}
		});

	}

	protected void dataReady(TreeItem parent) {
		if (cons == null || files == null)
			return;

		for (int i = 0; i < cons.size(); i++) {
			Containers con = cons.get(i);
			nav.addContainer(parent, con, true, false);
		}

		if (nav.listItems)
			for (int i = 0; i < files.size(); i++) {
				Files file = files.get(i);
				nav.addLeaf(parent, file);
			}

		cons = null;
		files = null;

		parent.setState(true, false);

	}

	@Override
	public void containerSelected() {
		btnFileUpload.setEnabled(true);
		super.containerSelected();
	}

	@Override
	public void itemSelected() {
		btnFileUpload.setEnabled(false);
		super.itemSelected();
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
