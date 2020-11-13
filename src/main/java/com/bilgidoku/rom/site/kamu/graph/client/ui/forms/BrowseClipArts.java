package com.bilgidoku.rom.site.kamu.graph.client.ui.forms;

import java.util.List;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowserInterface;
import com.bilgidoku.rom.gwt.client.util.browse.image.PnlImages;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BrowseClipArts extends Composite implements BrowserInterface {
	private final FlexTable holder = new FlexTable();
	private final ListBox lbOthers = new ListBox();
	
	private final SiteButton btnOk = new SiteButton("", GraphicEditor.trans.useIt(), "", "sml");
	private ChangeCallback caller;
	// private final String parent;
	private final PnlImages bList = new PnlImages(new BrowseCallback() {		
		@Override
		public void selected(String selectedImg) {
			buttonsState(true);
		}
	});
	
	public BrowseClipArts(final ChangeCallback caller, String initialParent) {

		this.caller = caller;
		addContainers(initialParent);
		bList.setImageSize("89px", "75px");
		bList.listImages(initialParent, null);
		
		bList.setSize("810px", "105px");
		forSelectCategory();
		forOK();

		lbOthers.setStyleName("site-box");
		lbOthers.getElement().getStyle().setPadding(2, Unit.PX);
		lbOthers.getElement().getStyle().setMarginRight(6, Unit.PX);
		
		HTML title = new HTML("<div style='padding: 2px 10px; color:white;font-weight:bold;'>" + GraphicEditor.trans.clipArts() + "</div>");
		
		FlexTable header= new FlexTable();		
		header.setStyleName("form-header");
		header.setWidth("100%");
		header.setWidget(0, 0, title);
		header.setWidget(0, 1, new HTML("<span style='color:white; font-weight:bold;'>"+ GraphicEditor.trans.otherCategories() +":</span>"));
		header.setWidget(0, 2, lbOthers);
		header.getFlexCellFormatter().setWidth(0, 2, "180px");
		header.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		btnOk.setWidth("150px");

		holder.setWidget(1, 0, bList);
		holder.setWidget(2, 0, btnOk);

		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.setStyleName("site-form");
		vp.add(header);
		vp.add(holder);
		
		btnOk.setEnabled(false);
		
		initWidget(vp);

	}

	protected void buttonsState(boolean b) {
		btnOk.setEnabled(true);
		
	}

	private void forOK() {
		btnOk.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (bList.getSelectedObject() == null)
					return;
				
				caller.newImage(bList.getSelectedObject());
			}
		});

	}

	private void forSelectCategory() {
		lbOthers.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				bList.listImages(lbOthers.getSelectedValue(), null);
			}
		});
	}

	private void addContainers(final String parent) {
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

	// protected Widget getWidget(String text, final String uri) {
	// HTML item = new HTML(text);
	// item.setStyleName("site-box site-left");
	// item.getElement().getStyle().setPadding(10, Unit.PX);
	// item.getElement().getStyle().setProperty("textAlign", "center");
	// item.setSize("100px", "23px");
	// item.addClickHandler(new ClickHandler() {
	// @Override
	// public void onClick(ClickEvent event) {
	// bList.listImages(uri, null);
	//// new DlgImages();
	// }
	// });
	// return item;
	// }

	// private class DlgImages extends ActionBarDlg {
	//
	// public DlgImages() {
	// super("Images", null, "OK");
	// run();
	// this.show();
	// this.center();
	// this.setAutoHideEnabled(false);
	// }
	//
	// @Override
	// public Widget ui() {
	// bList.setSize("670px", "400px");
	// return bList;
	// }
	//
	// @Override
	// public void cancel() {
	// }
	//
	// @Override
	// public void ok() {
	// Files img = bList.getSelectedObject();
	// if (img != null) {
	// caller.newImage(img.uri);
	// hide();
	// }
	// }
	//
	// }

	@Override
	public void itemSelected(String parentUri, String fileUri) {
		// TODO Auto-generated method stub

	}

}
