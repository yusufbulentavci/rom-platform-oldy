package com.bilgidoku.rom.gwt.client.util.akil;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AkilDlg extends DialogBox {

	private final AkilCb cb;

	public AkilDlg(AkilCb cb) {
		this.cb = cb;
		ui();
	}

	List<String> soylenenSatirlar = new ArrayList<>();
	TextArea soylenen = new TextArea();
	VerticalPanel soruPanel = new VerticalPanel();
	ListBox menu = new ListBox();
	private Soru soru;

	private void ui() {
		VerticalPanel anaPanel = new VerticalPanel();

//		HorizontalPanel istekPanel = new HorizontalPanel();
		anaPanel.add(new Label("Istek:"));
		menu.setMultipleSelect(false);
		menu.setVisibleItemCount(5);
		menu.addItem("");
		menu.addItem("merhaba");
		menu.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				int selectedIndex = menu.getSelectedIndex();

				if (selectedIndex < 0) {
					return; // Not sure why this happens during addValue
				}
				String cmd = menu.getSelectedValue();
				if (cmd.length() == 0)
					return;
//				sorular.clear();
				menu.setEnabled(false);
				OturumIciCagriDao.akil(cmd, new Json(new JSONObject()), new BooleanResponse());
			}
		});

//		istekPanel.add(menu);
		anaPanel.add(menu);

		anaPanel.add(soruPanel);

		soylenen.setVisibleLines(10);
		anaPanel.add(soylenen);

		Button btnClose = new Button("Close");
		btnClose.setStyleName("site-closebutton");
		forClose(btnClose);
		anaPanel.add(btnClose);

		this.setTitle("akil");
		this.setText("Akillim");
		this.setWidget(anaPanel);
		this.setAutoHideEnabled(true);
		this.setStyleName("site-helpdlg");
		this.setModal(true);
		this.show();
		this.center();
	}

	private void forClose(Button btnClose) {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AkilDlg.this.hide();
			}
		});

	}

	public void soylendi(String eylem) {
		soyle(eylem);
		menu.setSelectedIndex(0);
		menu.setEnabled(true);
	}

	private void soyle(String eylem) {
		soylenenSatirlar.add(0, eylem);
		if(soylenenSatirlar.size()>5) {
			soylenenSatirlar.remove(5);
		}
		StringBuilder sb=new StringBuilder();
		for (String s : soylenenSatirlar) {
			sb.append(s);
			sb.append("/n");
		}
		soylenen.setText(sb.toString());
	}

	public void updateMenu(JSONArray olasi) throws RunException {
		menu.clear();
		menu.addItem("");
		for(int i=0; i< olasi.size(); i++) {
			menu.addItem(olasi.get(i).isString().stringValue());
		}
		menu.setEnabled(true);
	}

	private void setSoru(final Soru soru) {
		this.soru=soru;
		this.soruPanel.add(new Label(soru.yazi));
		TextBox yanit = new TextBox();
		this.soruPanel.add(yanit);
		if(soru.olasi!=null && soru.olasi.size()>0) {
			ListBox lb=new ListBox();
			lb.setMultipleSelect(false);
			lb.setVisibleItemCount(5);
			for(int i=0; i<soru.olasi.size(); i++) {
				try {
					lb.addItem(soru.olasi.get(i).isString().stringValue());
				} catch (RunException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lb.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					yanit.setText(lb.getSelectedValue());
				}
			});
			soruPanel.add(lb);
		}
		Button b=new Button("yanitla");
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JSONObject jo = new JSONObject();
				jo.put("soru", new JSONString(soru.soru));
				jo.put("yazi_", new JSONString(yanit.getValue()));
				OturumIciCagriDao.akil("yanit", new Json(jo), new BooleanResponse());
				AkilDlg.this.soru=null;
				soruPanel.clear();
				AkilDlg.this.cb.yanitlandi();
			}
		});
		soruPanel.add(b);
	}

	public boolean soruVarmi(Soru soru2) {
		if(soru!=null)
			return true;
		setSoru(soru2);
		return false;
	}
	
	

}
