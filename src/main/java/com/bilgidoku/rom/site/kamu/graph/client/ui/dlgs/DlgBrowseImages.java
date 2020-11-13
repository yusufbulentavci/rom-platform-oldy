package com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowserInterface;
import com.bilgidoku.rom.gwt.client.util.browse.image.PnlImages;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgBrowseImages extends ActionBarDlg implements BrowserInterface {
//	private final SiteButton btnOk = new SiteButton("", GraphicEditor.trans.useIt(), GraphicEditor.trans.useIt(),
//			"sml");
//
//	private final SiteButton btnChange = new SiteButton("", GraphicEditor.trans.changeImage(),
//			GraphicEditor.trans.changeImage(), "sml");
//
//	private final SiteButton btnDel = new SiteButton("", GraphicEditor.trans.delImage(), GraphicEditor.trans.delImage(),
//			"sml");

	private final SiteButton btnListUsers = new SiteButton("", GraphicEditor.trans.yours(), GraphicEditor.trans.yours(),
			"sml");

	private final ListBox lbOthers = new ListBox();

	private ChangeCallback caller;

	private final PnlImages bList = new PnlImages(new BrowseCallback() {
		@Override
		public void selected(String selectedImg) {
			if (selectedImg == null)
				showWarning();
			else {
				caller.newImage(ClientUtil.getLastUri(selectedImg));
				DlgBrowseImages.this.hide();
			}
		}
	});
	
	TabPanel tp = new TabPanel();

	private final HTML warning = new HTML(
			"\"Resim Yükle\" ile yüklediğiniz ya da \"Internette Ara\"ma yaparak kullandığınız resimler burada listelenecek.");

	HorizontalPanel buttons = new HorizontalPanel();

	private String userDir;

	public DlgBrowseImages(final ChangeCallback caller, String initialParent, String userDir) {
		super("Görseller", null, null);
		this.userDir = userDir;
		this.caller = caller;
		
		warning.setVisible(false);
		
		loadContainers(initialParent);
		bList.listImages(initialParent, null);

		forYours();
		forSelectCategory();
		forFocusCategory();

		run();
		this.setGlassEnabled(true);
		this.show();
		this.center();

	}

	protected void showWarning() {
		warning.setVisible(true);		
	}

	protected void populate(String uri, boolean btnState) {
		ClientUtil.startWaiting();
		warning.setVisible(false);
		bList.listImages(uri, null);
	}

	private void forFocusCategory() {
		lbOthers.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				populate(lbOthers.getSelectedValue(), false);				
			}
		});
	}

	private void forYours() {
		btnListUsers.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				populate(userDir, true);				
			}
		});
	}

	protected void buttonsState(boolean b) {
	}

	private void forSelectCategory() {
	}

	private void loadContainers(final String parent) {		
		lbOthers.addItem(GraphicEditor.trans.popularItems(), parent);
		
		ContainersDao.listsub(parent, "/_/c", new ContainersResponse() {
			@Override
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					String text = con.uri.substring(con.uri.lastIndexOf("/") + 1);
					lbOthers.addItem(text, con.uri);
				}
			}
		});
	}

	@Override
	public void itemSelected(String parentUri, String fileUri) {
		// TODO Auto-generated method stub

	}

	@Override
	public Widget ui() {
		
		warning.getElement().getStyle().setPadding(20, Unit.PX);
		warning.setWidth("500px");
		bList.setImageSize("89px", "75px");
		bList.setSize("514px", "265px");
		lbOthers.setStyleName("site-box");

		lbOthers.setHeight("28px");
		btnListUsers.setHeight("28px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(4);
		hp.add(lbOthers);
		hp.add(btnListUsers);

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(3);
		vp.add(hp);
		vp.add(warning);
		vp.add(bList);		
//		vp.add(buttons);
		return vp;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ok() {
		String img = bList.getSelectedObject();
		if (img != null) {
			caller.newImage(img);
		}

	}

}
