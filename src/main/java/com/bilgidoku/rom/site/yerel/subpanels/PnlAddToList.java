package com.bilgidoku.rom.site.yerel.subpanels;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.ListsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;

public class PnlAddToList extends ScrollPanel {
	FlexTable ft = new FlexTable();
	private List<Contents> lists = new ArrayList<Contents>();
	private List<Contents> checkedLists = new ArrayList<Contents>();
	
	public PnlAddToList() {
		ft.setWidth("100%");
		this.add(ft);

	}

	public void load(String writingUri) {
						
		ft.removeAllRows();
		ft.clear();

		loadLists(writingUri);


	}

	private void loadLists(final String id) {		
		ListsDao.list(Ctrl.infoLang(), null, "/_/lists/public", new ContentsResponse() {
			@Override
			public void array(List<Contents> value) {
				lists = value;
				
				ListsDao.list(Ctrl.infoLang(), id, "/_/lists/public", new ContentsResponse() {
					@Override
					public void array(List<Contents> myarr) {
						
						checkedLists = myarr;
						
						ui(id);
					}
				});
				
			}
			
		});

	}
	
	private void ui(String writingUri) {
		for (int i = 0; i < lists.size(); i++) {
			Contents con = lists.get(i);
			
			MyCheckBox cb = new MyCheckBox(con, writingUri);
			
			ft.setWidget(i, 0, cb);
			ft.setHTML(i, 1, con.title[0] == null ? "" : con.title[0]);
			
			for (int j = 0; j < checkedLists.size(); j++) {
				Contents mon = checkedLists.get(j);
				
				if (mon.uri.equals(con.uri))
					cb.setValue(true);
			}
		}

		
	}


}

class MyCheckBox extends CheckBox {
	Contents myContent = null;

	public MyCheckBox(Contents con, final String writingUri) {
		this.myContent = con;
		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MyCheckBox mcb = (MyCheckBox) event.getSource();
				String uri = mcb.getContent().uri;
				if (mcb.getValue()) {
					ListsDao.addtolist(Ctrl.infoLang(), writingUri, uri, new StringResponse() {
						@Override
						public void ready(String value) {
						}
					});
				} else {
					ListsDao.removefromlist(Ctrl.infoLang(), writingUri, uri, new StringResponse() {
						@Override
						public void ready(String value) {
						}
					});
				}
			}
		});
	}

	public Contents getContent() {
		return this.myContent;
	}

}
