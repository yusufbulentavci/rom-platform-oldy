package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.ScrollPanel;

public class PageItems extends Composite implements PageReady{

	final FlexTable pnlAddedItems = new FlexTable();
	final FlexTable pnlAddItems = new FlexTable();
	final Button btnNext = new Button(Ctrl.trans.next());
	private boolean rmvSelf = false;
	private int row = -1;
	
	private final FlexTable holder = new FlexTable();
	private PnlCreatePage addForm = null;
	
	public PageItems(String pagetemplate, String header, String[][] suggestList, PageWizard caller, String parentUri, String parentName) {
		
		forRmvFromAdded();
		forRowSelectFromAdd();
		
		forNext(caller);
		
		btnNext.setStyleName("site-nextbutton");
		pnlAddedItems.setStyleName("site-innerform");
		pnlAddedItems.setWidth("400px");
		pnlAddedItems.setCellPadding(3);
		
		ScrollPanel sp = new ScrollPanel();
		sp.setStyleName("site-innerform");
		sp.setSize("400px", "190px");
		sp.add(pnlAddItems);
		
		holder.setWidget(0, 0, new HTML("<h3>" + Ctrl.trans.pwAddedItems() + "</h3>"));
		holder.setWidget(1, 0, pnlAddedItems);
		holder.setWidget(2, 0, new HTML("<h3>" + header + "</h3>"));
		holder.setWidget(3, 0, sp);
		holder.setWidget(4, 0, btnNext);

		holder.getFlexCellFormatter().setVisible(0, 0, false);
		holder.getFlexCellFormatter().setVisible(1, 0, false);
		
		if (suggestList != null) {
			//after adding page hide yourself
			rmvSelf = true; 
			for (int i = 0; i < suggestList.length; i++) {
				pnlAddItems.setWidget(i, 1, new PnlCreatePage(suggestList[i][0], suggestList[i][1], null, this, parentUri, parentName));
			}
		} else {
			addForm = new PnlCreatePage(null, pagetemplate, null, this, parentUri, parentName);
			pnlAddItems.setWidget(0, 0, addForm);
		}
		

		initWidget(holder);

	}

	
	private void forRowSelectFromAdd() {
		pnlAddItems.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Cell src = pnlAddItems.getCellForEvent(event);
				int rc = src.getRowIndex();
				row = rc;
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
		pnlAddedItems.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell src = pnlAddedItems.getCellForEvent(event);
				int colIndex = src.getCellIndex();
				int rowIndex = src.getRowIndex();
				if (colIndex == 2) {
					// delete
					String uri = pnlAddedItems.getHTML(rowIndex, 1);					
					deletePage(uri);
					pnlAddedItems.removeRow(rowIndex);
				}

			}
		});

	}


	protected void deletePage(String uri) {
		WritingsDao.destroy(uri, new StringResponse() {
			
		});	
	}



	@Override
	public void pageReady(String title, String uri, String lang) {
		//added items a ekle, add items dan cıkar, reset addForm
		if (addForm != null)
			addForm.reset();
		holder.getFlexCellFormatter().setVisible(0, 0, true);
		holder.getFlexCellFormatter().setVisible(1, 0, true);

		int rc = pnlAddedItems.getRowCount();
		pnlAddedItems.setHTML(rc, 0, title);
		pnlAddedItems.setHTML(rc, 1, uri);
		pnlAddedItems.setWidget(rc, 2, new Button("Del"));
		if (rmvSelf) {
			pnlAddItems.removeRow(this.row);
		}		
	}


}
