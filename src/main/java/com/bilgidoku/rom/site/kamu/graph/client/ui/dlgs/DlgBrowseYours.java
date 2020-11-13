package com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.site.kamu.graph.client.ui.SimpleChangeListener;
import com.bilgidoku.rom.site.kamu.graph.client.ui.forms.ImageEditor;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowserInterface;
import com.bilgidoku.rom.gwt.client.util.browse.image.PnlImages;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgBrowseYours extends ActionBarDlg implements BrowserInterface {
	
	private ChangeCallback caller;
	private final SiteButton btnOk = new SiteButton("", GraphicEditor.trans.useIt(), GraphicEditor.trans.useIt(), "sml");

	private final SiteButton btnChange = new SiteButton("", GraphicEditor.trans.changeImage(), GraphicEditor.trans.changeImage(), "sml");

	private final SiteButton btnDel = new SiteButton("", GraphicEditor.trans.delImage(), GraphicEditor.trans.delImage(), "sml");

	private String userDir = null;
	
	private final PnlImages bList = new PnlImages(new BrowseCallback() {		
		@Override
		public void selected(String selectedImg) {
			imageSelected(selectedImg);
		}
	});
	
	private final HTML warning = new HTML("\"Resim Yükle\" ile yüklediğiniz ya da \"Internette Ara\"ma yaparak kullandığınız resimler burada listelenecek.");
	
	HorizontalPanel buttons = new HorizontalPanel();

	public DlgBrowseYours(final ChangeCallback caller, String selectedImg, String userDir) {
		super("Resimleriniz", null, null);
		this.caller = caller;

		this.userDir = userDir;
		
		bList.setImageSize("89px", "75px");
		bList.listImages(userDir, selectedImg);
		warning.getElement().getStyle().setPaddingLeft(20, Unit.PX);

		bList.setSize("514px", "265px");
		
//		forOK();
		forChange();
		forDel();

		HTML header = new HTML("<div style='padding: 2px 10px;'>" + GraphicEditor.trans.yours() + "</div>");
		header.setStyleName("form-header");
		header.setWidth("100%");

		btnOk.setWidth("150px");

		buttons.setSpacing(4);
		buttons.add(btnOk);
		buttons.add(btnChange);
		buttons.add(btnDel);
		
		run();
		this.setGlassEnabled(true);
		this.show();
		this.center();

		
	}
	
	

	protected void imageSelected(String selectedImg) {
//		if (selectedImg == null) {
//			holder.remove(buttons);
//			holder.add(warning);
//			return;
//		}
	}

	private void forDel() {
		btnDel.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				String uri = bList.getSelectedObject();
				if (uri != null) {
					FilesDao.destroy(uri, new StringResponse() {
						@Override
						public void ready(String value) {
							bList.listImages(userDir, null);
						}
					});
				}				
			}
		});
		
	}

	private void forChange() {
		btnChange.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				String img = bList.getSelectedObject();
				if (img != null) {
					new ImageEditor(new SimpleChangeListener() {
						
						@Override
						public void changed(Composite source, Integer oldone, Integer newone) {
						}

						@Override
						public void changed(String source) {
							bList.listImages(userDir, null);							
						}
					}, img);
				}
			}
		});
	}




	@Override
	public Widget ui() {
		VerticalPanel holder = new VerticalPanel();
		holder.add(bList);
		holder.add(buttons);
		return holder;
	}



	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void ok() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void itemSelected(String parentUri, String fileUri) {
		// TODO Auto-generated method stub
		
	}

}
