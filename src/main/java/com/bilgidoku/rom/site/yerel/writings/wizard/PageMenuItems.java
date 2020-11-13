package com.bilgidoku.rom.site.yerel.writings.wizard;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.ListsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageMenuItems extends Composite {

	final Button btnNext = new Button(Ctrl.trans.next());
	FlexTable holder = new FlexTable();
	VerticalPanel items = new VerticalPanel();
	private final PageWizard caller;
	private boolean containerSelected = false;

	public PageMenuItems(String listUri, PageWizard calr) {
		this.caller = calr;
		if (listUri.indexOf("main") > 0 || listUri.indexOf("footer") > 0)
			containerSelected = true;

		getContainers(containerSelected);
		forSave(listUri);

		btnNext.setStyleName("site-nextbutton");

		items.setHeight("50px");

		holder.setWidget(1, 0, new ScrollPanel(items));
		holder.setWidget(2, 0, btnNext);
		initWidget(new ScrollPanel(holder));

	}

	private void forSave(final String listUri) {
		btnNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				VerticalPanel ft = items;
				if (ft.getWidgetCount() > 0) {
					List<String> checked = new ArrayList<String>();

					for (int i = 0; i < ft.getWidgetCount(); i++) {

						HorizontalPanel hp = (HorizontalPanel) ft.getWidget(i);

						CheckBox cb = (CheckBox) hp.getWidget(1);
						if (cb.getValue()) {
							Label l = (Label) hp.getWidget(3);
							checked.add(l.getText());
						}

					}
					String[] sb = new String[checked.size()];
					sb = checked.toArray(sb);

					if (sb == null || sb.length <= 0) {
						Window.alert(Ctrl.trans.selectAnItem());
						return;
					}

					if (sb.length > 6 && listUri.indexOf("main") > 0) {
						Window.alert(Ctrl.trans.pwTooMuchMenuItems());
						return;
					}

					ListsDao.change(sb, listUri, new StringResponse() {
						@Override
						public void ready(String value) {
							Ctrl.setStatus(Ctrl.trans.saved());
						}

					});

				}
				caller.pageAdded(null, null);

			}
		});

	}

	private void getContainers(final boolean selected) {
		items.add(getRow("", "/", Ctrl.trans.main(), true));
		ContainersDao.listsub("/_/writings", "/_/c", new ContainersResponse() {
			@Override
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					getWritings(con.uri, con.uri_prefix, selected);

				}
			}
		});

	}

	protected void getWritings(final String conuri, final String uri_prefix, final boolean selected) {
		WritingsDao.list(Ctrl.infoLang(), "", conuri, new ContentsResponse() {
			public void array(List<Contents> value) {
				
				String conWriting = uri_prefix.substring(0, uri_prefix.length() - 1);
				if (conWriting.isEmpty())
					conWriting = "/";
				items.add(getRow("", conWriting, ClientUtil.getTitleFromUri(conuri), selected));

				for (int i = 0; i < value.size(); i++) {
					Contents s = value.get(i);
					items.add(getRow("&nbsp;&nbsp;&nbsp;&nbsp;", s.uri, s.title[0], false));
				}
			}
		});

	}

	protected HorizontalPanel getRow(String space, String uri, String title, boolean selected) {
		Label lblUri = new Label(uri);
		lblUri.setVisible(false);

		Anchor a = new Anchor(title);
		a.setHref(uri);

		CheckBox cb = new CheckBox();
		cb.setValue(selected);

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(new HTML(space));
		hp.add(cb);
		hp.add(a);
		hp.add(lblUri);
		return hp;
	}

}
