package com.bilgidoku.rom.site.yerel.issues;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Waiting;
import com.bilgidoku.rom.gwt.araci.client.rom.WaitingDao;
import com.bilgidoku.rom.gwt.araci.client.rom.WaitingResponse;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavNotifs extends Composite implements HasContainer {

	private Map<String, Waiting> waitings = new HashMap<String, Waiting>();
	VerticalPanel holder = new VerticalPanel();

	public NavNotifs() {
		initWidget(holder);
	}

	@Override
	public void addContainers() {
		// get and gotit
		// btnIssues.gotIt();
		holder.clear();
		WaitingDao.list("/_/waiting", new WaitingResponse() {
			@Override
			public void array(List<Waiting> myarr) {
				for (int i = 0; i < myarr.size(); i++) {
					Waiting w = myarr.get(i);
					if (w.app.equals("issue")) {
						waitings.put(w.code, w);
					}
				}
				
				ui();
				
				for (int i = 0; i < myarr.size(); i++) {
					WaitingDao.gotit(myarr.get(i).uri, new StringResponse());
				}

			}
		});

	}

	void ui() {
		HTML title = new HTML(Ctrl.trans.issues());
		title.setStyleName("site-panelheader");
		title.setWidth("97%");
		
		holder.add(title);
		holder.setSpacing(6);
		holder.setWidth("100%");
		int j = 0;
		Set<String> keySet = waitings.keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Waiting w = waitings.get(key);
			final String[] inref = w.inref;
			for (int i = 0; i < inref.length; i++) {
				final String ref = inref[i];

				Anchor a = new Anchor(ref);
				String text = "";
				if (w.title != null && w.title[i] != null)
					text = w.title[i];

				if (w.username != null && w.username[i] != null)
					text = text + ">>" + w.username[i];

				a.setText(text);
				a.setTitle(ref);
				a.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RomEntryPoint.one.getTopCb().gotoIssue(ref);
					}
				});
				j++;
				a.setStyleName((j%2 == 0) ? "row-odd" : "row-even");
				holder.add(a);
			}
		}
	}
}
