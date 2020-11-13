package com.bilgidoku.rom.site.yerel.writings.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.service.InternetDao;
import com.bilgidoku.rom.gwt.araci.client.site.LinksDao;
import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class PageLinks extends Composite {
	
	final FlexTable pnlAddedLinks = new FlexTable();

	final Button btnNext = new Button(Ctrl.trans.next());
	private int imgIndex = 0;
	private final FlexTable holder = new FlexTable();
	List<ImageResp> linkImage = new ArrayList<ImageResp>();
	
	public PageLinks(String header, PageWizard caller, String parentUri) {
		//parent uri is writing uri so change it to link container
		parentUri = "/_/links/public" + parentUri;
		
		getReadyIconPictures();		
		
		forRmvFromAdded();
		
		forNext(caller);
		
		btnNext.setStyleName("site-nextbutton");
		pnlAddedLinks.setStyleName("site-innerform");
		pnlAddedLinks.setWidth("400px");
		pnlAddedLinks.setCellPadding(3);
		
		
		holder.setWidget(0, 0, new HTML("<h3>" + Ctrl.trans.pwAddedItems() + "</h3>"));
		holder.setWidget(1, 0, pnlAddedLinks);
		holder.setWidget(2, 0, new HTML("<h3>" + header + "</h3>"));
		holder.setWidget(3, 0, new CreateLink(this, parentUri));
		holder.setWidget(4, 0, btnNext);

		holder.getFlexCellFormatter().setVisible(0, 0, false);
		holder.getFlexCellFormatter().setVisible(1, 0, false);
		

		initWidget(holder);

	}


	private void getReadyIconPictures() { 
		InternetDao.searchimg(3, null, null, "1", null, null, null, null, null,
				new ArrayResponse<ImageResp>() {

					@Override
					public void ready(ImageResp[] value) {
						List<ImageResp> data3 = new ArrayList<ImageResp>(Arrays.asList(value));						
						linkImage.addAll(data3);
					}
				});

		InternetDao.searchimg(4, null, null, "1", null, null, null, null, null,
				new ArrayResponse<ImageResp>() {

					@Override
					public void ready(ImageResp[] value) {
						List<ImageResp> data4 = new ArrayList<ImageResp>(Arrays.asList(value));						
						linkImage.addAll(data4);
					}
				});
	}
	

	private void forNext(final PageWizard caller) {
		btnNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				caller.goNextStep(null, null);
			}
		});

	}

	private void forRmvFromAdded() {
		pnlAddedLinks.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell src = pnlAddedLinks.getCellForEvent(event);
				int colIndex = src.getCellIndex();
				int rowIndex = src.getRowIndex();
				if (colIndex == 2) {
					// delete
					String uri = pnlAddedLinks.getHTML(rowIndex, 1);					
					deletePage(uri);
					pnlAddedLinks.removeRow(rowIndex);
				}

			}
		});

	}


	protected void deletePage(String uri) {
		LinksDao.destroy(uri, new StringResponse());	
	}

	public void linkAdded(String pageName, String pageUri) {

		holder.getFlexCellFormatter().setVisible(0, 0, true);
		holder.getFlexCellFormatter().setVisible(1, 0, true);

		int rc = pnlAddedLinks.getRowCount();
		pnlAddedLinks.setHTML(rc, 0, pageName);
		pnlAddedLinks.setHTML(rc, 1, pageUri);
		pnlAddedLinks.setWidget(rc, 2, new Button("Del"));
			
	}

	public String getThumbImage() {		
		if (linkImage.size() > 0) {
			ImageResp img = linkImage.get(imgIndex);
			imgIndex++;
			
			if (imgIndex == linkImage.size()) 
				imgIndex = 0;
			return img.thumbpath;			
		}
		return null;
	}


}
