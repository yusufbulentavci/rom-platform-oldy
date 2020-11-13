package com.bilgidoku.rom.site.kamu.activate.client;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.kamu.activate.client.constants.activatetext;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelLang extends Composite {

	private final ListBox listLangs = new ListBox();
	private final activatetext trans = GWT.create(activatetext.class);
	
	private final SiteButton next = new SiteButton(trans.next(), trans.nextDesc(), "next");
	
	public PanelLang(Steps steps) {
		next.setEnabled(true);
		forNext(steps);
		loadLangs();
		initWidget(getUi());
	}

	private void forNext(final Steps steps) {
		next.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				steps.state2OK(getLang());				
			}
		});		
	}
	
	private String getLang() {
		return listLangs.getValue(listLangs.getSelectedIndex());
	}


	private Widget getUi() {
		listLangs.setWidth("100px");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(10);
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(new HTML("<h3>" + trans.lang() + "</h3>"));
		hp.add(listLangs);

		HTML title = new HTML("<h2>" + trans.selectSiteLang() + "</h2>");
		
		VerticalPanel vp = new VerticalPanel();		
		vp.add(title);
		vp.add(hp);
		vp.add(next);
		vp.setStyleName("site-panel");
//		vp.setWidth("250px");
		return vp;
	}

	private void loadLangs() {
		for (int i = 0; i < LANG_CODES.length; i++) {
			String[] arr = LANG_CODES[i];
			listLangs.addItem(arr[1], arr[0]);
		}
	}

	private final String[][] LANG_CODES = { { "tr", trans.turkish() }, { "en", trans.english() } };

	public void setValues(String lang) {		
		listLangs.setSelectedIndex(-1);
		for (int i = 0; i < listLangs.getItemCount(); i++) {
			String val = listLangs.getValue(i);
			if (val.equals(lang)) {
				listLangs.setSelectedIndex(i);
				break;
			}
		}
		
	}

	public void setJson(JSONObject jo) throws NotReadyException {
		jo.put("lang", new JSONString(Steps.notNull("language",getLang())));
	}

}
