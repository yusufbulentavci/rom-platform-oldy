package com.bilgidoku.rom.gwt.client.util.browse.image;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.NewItemDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.shared.CRoleMask;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BrowseTree extends Composite implements UploadFile {

	private final Tree bTree = new Tree();
	private final SiteButton btnCancel = new SiteButton("cancel", null, "cancel", null);

	private final SiteButton btnOk = new SiteButton("/_public/images/bar/check.png", null, "ok", null);
	private final SiteButton btnSearchImage = new SiteButton("/_public/images/bar/search_.png", null,
			"search image on internet", null);
	private final SiteButton btnFileUpload = new SiteButton("/_public/images/bar/upload.png", null, "upload image",
			null);
	public final SiteButton btnNewContainer = new SiteButton("/_local/images/common/folder_add.png", "", "Yeni klasör",
			"");

	private BrowserInterface browser;

	public BrowseTree(BrowserInterface browser) {
		this.browser = browser;
		forSelectOnTree();
		forExpand();
		forSearch();
		// forOk();
		// forCancel();
		forUpload();
		forNewCont();

		btnFileUpload.setVisible(false);

		btnFileUpload.setHeight("28px");
		btnSearchImage.setHeight("28px");
		btnNewContainer.setHeight("28px");
		btnOk.setHeight("28px");
		// btnReload.setHeight("28px");
		btnCancel.setHeight("28px");

		FlowPanel fp = new FlowPanel();
		fp.add(btnSearchImage);
		fp.add(btnFileUpload);
		 fp.add(btnNewContainer);
		// fp.add(btnOk);
		// fp.add(btnCancel);

		bTree.setSize("250px", "460px");
		ScrollPanel wTree = new ScrollPanel(bTree);

		VerticalPanel vp = new VerticalPanel();
		vp.getElement().getStyle().setBackgroundColor("white");
		vp.add(wTree);
		vp.add(fp);

		initWidget(vp);
	}

	public void run() {
		addContainerNodes();
	}

	private void forNewCont() {
		btnNewContainer.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TreeItem parent = getHolderContainer();
				if (parent == null) {
					Window.alert("Bir klasör seçin");
					return;
				}
				
				SiteTreeItemData data = (SiteTreeItemData) parent.getUserObject();

				final NewItemDlg dlg = new NewItemDlg(data.getUriPrefix(), "Yeni Klasör", true,
						Data.FILES_ROOT + "/", data.getUri());
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (dlg.getUri() != null && !dlg.getUri().isEmpty()) {
							FilesDao.breed(dlg.getUri(), Data.WRITING_PUBLIC_MASK, null, null, dlg.getDelegated(),
									new ContainersResponse() {
								public void ready(Containers value) {
									TreeItem parent = getHolderContainer();
									if (dlg.getIsOnTop())
										parent = null;
									
									
									addContainer(getSelectedItem(), value.mask, value.uri, value.uri_prefix);

								}
							});

						}
					}

				});


			}
		});
	}

	private void forUpload() {
		btnFileUpload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TreeItem item = bTree.getSelectedItem();
				if (item == null) {
					Window.alert("Select an item");
					return;
				}
				SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
				if (!data.isContainer()) {
					item = item.getParentItem();
					data = (SiteTreeItemData) item.getUserObject();
				}

				String target = data.getUri();

				if (data.getUriPrefix().startsWith(":r:")) {
					target = target + "/addfile.rom";
				}

				FileUploadDialog dlg = new FileUploadDialog(BrowseTree.this, target);
				dlg.show();
			}
		});

	}

	private void forSearch() {
		btnSearchImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SearchDlg dlg = new SearchDlg();
				dlg.addHandler(new DownloadCompletedHandler() {
					@Override
					public void done(DownloadCompleted event) {
						fileUploaded(null, null);
					}
				}, DownloadCompleted.TYPE);
			}
		});
	}

	private void forSelectOnTree() {
		bTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = (TreeItem) event.getSelectedItem();
				SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
				String uri = data.getUri();
				showImages(item, uri);
			}
		});

	}

	public void addContainerNodes() {
		bTree.removeItems();

		ContainersDao.listing("site", "files", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					// if (con.uri.indexOf("image") > 0)
					addContainer(null, con.mask, con.uri, con.uri_prefix);
				}
			}
		});
	}

	public void addContainer(TreeItem parent, Long mask, final String uri, String uri_prefix) {
		String conImg = "/_public/images/bar/folder.png";

		if (uri_prefix.startsWith(":r:")) {
			conImg = "/_public/images/bar/blue_folder.png";
		} else if (!CRoleMask.maskIsPublic(mask)) {
			conImg = "/_public/images/bar/folder_key.png";
		}

		String text = uri.substring(uri.lastIndexOf("/") + 1);

		final TreeItem node = new TreeItem(new HTML("<img src='" + conImg + "'/>" + text));
		node.setUserObject(new SiteTreeItemData(text, uri, true, uri_prefix));
		node.addTextItem("");

		if (parent == null)
			bTree.addItem(node);
		else
			parent.addItem(node);

	}

	private void forExpand() {
		bTree.addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem item = event.getTarget();
				reloadContainer(item);
			}
		});
	}

	protected void reloadContainer(final TreeItem item) {

		SiteTreeItemData pd = (SiteTreeItemData) item.getUserObject();
		if (!pd.isContainer())
			return;

		if (pd.getUriPrefix().startsWith(":r:"))
			return;

		if (item.getChildCount() == 1) {
			ContainersDao.listsub(pd.getUri(), "/_/c", new ContainersResponse() {
				@Override
				public void array(List<Containers> value) {

					if (value == null) {
						item.getChild(0).remove();
						return;
					}

					if (value.size() <= 0) {
						item.getChild(0).remove();
						return;
					}

					item.setState(false, false);

					for (int i = 0; i < value.size(); i++) {
						Containers con = value.get(i);
						addContainer(item, con.mask, con.uri, con.uri_prefix);
					}

					item.getChild(0).remove();

					item.setState(true, false);

					SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();

					String parentUri = data.getUri();

					browser.itemSelected(parentUri, null);

				}
			});
		}

	}

	protected void addLeaf(TreeItem item, Files file) {
		if (file.uri == null || !ClientUtil.isImage(file.uri))
			return;

		Image img = new Image(file.uri);
		addLeaf(item, file.uri, getItemName(file.uri), img);
	}

	private void addLeaf(TreeItem item, String uri, String itemName, Image img) {
		TreeItem node = new TreeItem(new HTML("<img src='/_public/images/bar/image.png'/>" + itemName));
		node.setUserObject(new SiteTreeItemData(itemName, uri, false, uri));
		item.addItem(node);

	}

	protected void showImages(TreeItem node, final String fileUri) {
		if (node == null) {
			return;
		}

		SiteTreeItemData data = (SiteTreeItemData) node.getUserObject();

		String parentUri = data.getUri();

		if (!data.isContainer())
			parentUri = getParentUri(data);

		if (fileUri == null)
			return;

		browser.itemSelected(parentUri, fileUri);

	}

	public TreeItem getSelectedItem() {
		return bTree.getSelectedItem();
	}

	private String getParentUri(SiteTreeItemData data) {
		String uri = data.getUri();
		return uri.substring(0, uri.lastIndexOf("/"));
	}

	private String getItemName(String uri) {
		String[] sp = uri.split("/");
		if (sp.length < 2)
			return null;
		return sp[sp.length - 1];
	}

	@Override
	public void fileUploaded(String dbfs, String fileUri) {
		showImages(bTree.getSelectedItem(), fileUri);
		// TreeItem parent = item;
		// SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
		// if (!data.isContainer()) {
		// parent = item.getParentItem();
		// data = (SiteTreeItemData) parent.getUserObject();
		// }
		//
		// parent.setState(false);
		// parent.setState(true);
		//
		// showImages(parent, fileUri);

	}

	public void isMobile(boolean onlyFolders) {
		// this.onlyFolders = onlyFolders;
		btnOk.setVisible(false);
		// btnReload.setVisible(false);
		btnFileUpload.setVisible(false);

	}

	public void setIsFileUploadable(boolean fileUpload) {
		btnFileUpload.setVisible(true);
	}

	private boolean added = false;
	
	public void setResource(String resource) {
		if (resource == null)
			return;
		
		if (added) 
			return;
		
		addContainer(null, 0L, resource, ":r:" + resource);
		added = true;
	}
	
	public TreeItem getHolderContainer() {
		TreeItem item = getSelectedItem();
		if (item == null)
			return null; 
		
		SiteTreeItemData pd = (SiteTreeItemData) item.getUserObject();
		if (pd.isContainer()) {
			return item;
		} else {
			return item.getParentItem();
		} 
	}


}
