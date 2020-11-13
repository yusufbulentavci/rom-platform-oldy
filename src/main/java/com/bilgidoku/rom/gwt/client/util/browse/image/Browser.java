package com.bilgidoku.rom.gwt.client.util.browse.image;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TreeItem;

public class Browser extends Composite implements BrowserInterface {

	private final PnlImages bList;
	private final BrowseTree bTree = new BrowseTree(this);

	public Browser(boolean fileUpload, BrowseCallback cb1) {
		bList = new PnlImages(cb1);
		
		bTree.setIsFileUploadable(fileUpload);
//		bTree.setResource(resource);

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		hp.setStyleName("site-panel");
		hp.setSpacing(10);
		hp.add(bTree);
		hp.add(bList);

		initWidget(hp);

		bTree.run();
	}

	public String getSelected() {
		String uri = bList.getSelectedObject();
		if (uri != null) {
			return uri;
		}

		TreeItem item = (TreeItem) bTree.getSelectedItem();
		if (item == null) {
			return null;
		}

		SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
		if (data.isContainer()) {
			return null;
		}

		return data.getUri();
	}

	
	
	@Override
	public void itemSelected(String parentUri, String fileUri) {
		bList.listImages(parentUri, fileUri);
	}

	public void setResource(String path) {
		if (path == null)
			return;
		bTree.setResource(path);		
	}

}
