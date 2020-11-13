package com.bilgidoku.rom.site.yerel.issues;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.IntegerResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class NewIssueDlg extends DialogBox {

	private final TextArea named = new TextArea();
	private final TextArea desc = new TextArea();
	private final Hidden hd = new Hidden();
	private final ListBox lbUsers = new ListBox();
	private final ListBox lbWeights = new ListBox();

	public String id = "0";

	public NewIssueDlg(List<String[]> users) {
		loadUsers(users);
		
		lbWeights.addItem("low", "10");
		lbWeights.addItem("normal", "100");
		lbWeights.addItem("high", "1000");
		
		lbWeights.setSelectedIndex(1);

		desc.setSize("250px", "100px");
		named.setSize("250px", "40px");
		this.setWidget(getUi());
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(true);
		this.setText(Ctrl.trans.newIssue());
		this.center();
	}

	private void loadUsers(List<String[]> myarr) {
		lbUsers.addItem("---" + Ctrl.trans.assignToUser() + "---", "");
		for (int i = 0; i < myarr.size(); i++) {
			String[] u = myarr.get(i);
			lbUsers.addItem(u[0], u[1]);
		}
	}
	
	private boolean assigned = false;
	private boolean weighted = false;

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
				if (NewIssueDlg.this.getNamed() == null || NewIssueDlg.this.getNamed().isEmpty())
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

					if (lbUsers.getSelectedIndex() > 0) {
						IssuesDao.assignto(lbUsers.getSelectedValue(), id, new StringResponse() {
							@Override
							public void ready(String value) {
								assigned = true;
								closeDlg();
								
							}

						});
					} else {
						assigned = true;
						closeDlg();						
					}
					
					if (lbWeights.getSelectedIndex() != 1) {
						int val = Integer.parseInt(lbWeights.getSelectedValue());
						ResourcesDao.setweight(val, id, new IntegerResponse(){
							public void ready(Integer value) {
								weighted = true;
								closeDlg();
							};
						});
					} else {
						weighted = true;
						closeDlg();
					}
					
				}
				closeDlg();
			}
		});
		Button btnCancel = new Button("Cancel");
		btnCancel.setStyleName("site-closebutton");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				NewIssueDlg.this.setNamed("");
				NewIssueDlg.this.setDesc("");
				NewIssueDlg.this.hide(true);
			}
		});

		Hidden outform = new Hidden("outform");
		outform.setValue("json");

		Hidden lang = new Hidden("lng");
		lang.setValue(Ctrl.infoLang());

		Hidden cls = new Hidden("cls");
		cls.setValue("task");

		
		FileUpload attFile = new FileUpload();
		attFile.setName("attach");

		FlexTable f = new FlexTable();
		f.setHTML(0, 0, Ctrl.trans.title());
		f.setWidget(0, 1, named);
		f.setHTML(1, 0, Ctrl.trans.desc());
		f.setWidget(1, 1, desc);

		f.setHTML(2, 0, Ctrl.trans.attachments());
		f.setWidget(2, 1, attFile);

		f.setHTML(3, 0, Ctrl.trans.assign());
		f.setWidget(3, 1, lbUsers);

		f.setHTML(4, 0, Ctrl.trans.weight());
		f.setWidget(4, 1, lbWeights);

		f.setWidget(5, 0, btnSave);
		f.setWidget(5, 1, btnCancel);
		f.setWidget(6, 0, outform);
		f.setWidget(6, 1, lang);
		f.setWidget(7, 0, hd);
		f.setWidget(7, 1, cls);

		form.add(f);
		return form;
	}

	private void closeDlg() {
		if (assigned && weighted)
			NewIssueDlg.this.hide(true);		
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