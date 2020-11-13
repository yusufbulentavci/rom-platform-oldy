package com.bilgidoku.rom.site.yerel.issues;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.client.widgets.DateTimeBox;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.one;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class NewEvntDlg extends DialogBox {

	private final TextArea named = new TextArea();
	private final TextArea desc = new TextArea();
	private final Hidden hd = new Hidden();
	// private final ListBox lbUsers = new ListBox();
	// private final ListBox lbWeights = new ListBox();
	DateTimeBox dpStart = new DateTimeBox();
	DateTimeBox dpEnd = new DateTimeBox();

	public String id = "0";

	public NewEvntDlg(List<String[]> users) {
		// loadUsers(users);

		dpStart.addChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				dpEnd.setValue(dpStart.getValue());

			}
		});

		// lbWeights.addItem("low", "10");
		// lbWeights.addItem("normal", "100");
		// lbWeights.addItem("high", "1000");
		//
		// lbWeights.setSelectedIndex(1);

		desc.setSize("250px", "100px");
		named.setSize("250px", "40px");
		this.setWidget(getUi());
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(true);
		this.setText(Ctrl.trans.add(Ctrl.trans.event()));
		this.center();
	}

	// private void loadUsers(List<String[]> myarr) {
	// lbUsers.addItem("---" + Ctrl.trans.assignToUser() + "---", "");
	// for (int i = 0; i < myarr.size(); i++) {
	// String[] u = myarr.get(i);
	// lbUsers.addItem(u[0], u[1]);
	// }
	// }

	private boolean stSaved = false;
	private boolean st1Saved = false;
	private boolean assSaved = false;

	private Widget getUi() {
		final FormPanel form = new FormPanel();
		form.setAction("/_/issues/new.rom?outform=json");
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		named.setName("title");
		hd.setName("desc");

		Button btnSave = new Button(Ctrl.trans.create());
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (NewEvntDlg.this.getNamed() == null || NewEvntDlg.this.getNamed().isEmpty())
					return;
				String str = desc.getValue();

				hd.setValue(str.replaceAll("(\r\n|\n)", "<br />"));
				form.submit();
			}
		});
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String resp = event.getResults();
				if (resp != null && !resp.isEmpty()) {
					JSONObject jo = JSONParser.parseStrict(resp).isObject();
					id = jo.get("def").isString().stringValue();

					IssuesDao.assignto(one.userContactId, id, new StringResponse() {
						@Override
						public void ready(String value) {
							assSaved = true;
							check();
						}
					});

					
					IssuesDao.setduestart(dpStart.getValue() + "", id, new StringResponse() {
						@Override
						public void ready(String value) {
							stSaved = true;
							check();
						}
					});
					IssuesDao.setduedate(dpEnd.getValue() + "", id, new StringResponse() {
						
						@Override
						public void ready(String value) {
							st1Saved = true;
							check();
						}
					});
					check();
				}

			}
		});
		Button btnCancel = new Button("Cancel");
		btnCancel.setStyleName("site-closebutton");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				NewEvntDlg.this.setNamed("");
				NewEvntDlg.this.setDesc("");
				NewEvntDlg.this.hide(true);
			}
		});

		Hidden outform = new Hidden("outform");
		outform.setValue("json");

		Hidden lang = new Hidden("lng");
		lang.setValue(Ctrl.infoLang());

		Hidden cls = new Hidden("cls");
		cls.setValue("event");

		FileUpload attFile = new FileUpload();
		attFile.setName("attach");

		FlexTable f = new FlexTable();
		f.setHTML(0, 0, Ctrl.trans.title());
		f.setWidget(0, 1, named);
		f.setHTML(1, 0, Ctrl.trans.desc());
		f.setWidget(1, 1, desc);

		f.setHTML(2, 0, Ctrl.trans.attachments());
		f.setWidget(2, 1, attFile);

		// f.setHTML(3, 0, Ctrl.trans.assign());
		// f.setWidget(3, 1, lbUsers);
		//
		// f.setHTML(4, 0, Ctrl.trans.weight());
		// f.setWidget(4, 1, lbWeights);

		f.setHTML(3, 0, Ctrl.trans.startDate());
		f.setWidget(3, 1, dpStart);

		f.setHTML(4, 0, Ctrl.trans.endDate());
		f.setWidget(4, 1, dpEnd);

		f.setWidget(5, 0, btnSave);
		f.setWidget(5, 1, btnCancel);
		f.setWidget(6, 0, outform);
		f.setWidget(6, 1, lang);
		f.setWidget(7, 0, hd);
		f.setWidget(7, 1, cls);

		form.add(f);
		return form;
	}

	protected void check() {
		if (!(stSaved && st1Saved && assSaved))
			return;
		NewEvntDlg.this.hide(true);
		
	}

	public String getNamed() {
		return named.getValue();
	}

	public void setNamed(String val) {
		named.setValue(val);
	}

	public void setDesc(String val) {
		desc.setValue(val);
	}

	public String getDesc() {
		return desc.getValue();
	}
}