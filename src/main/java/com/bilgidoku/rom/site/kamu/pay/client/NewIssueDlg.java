package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NewIssueDlg extends ActionBarDlg {

	private final TextBox named = new TextBox();
	private final TextArea desc = new TextArea();
	public String id = "0";

	public NewIssueDlg() {
		super(pay.trans.newIssue(), null, null);
		desc.setSize("250px", "100px");
		run();
		center();		
	}

	@Override
	public Widget ui() {
		final FormPanel form = new FormPanel();
		form.setStyleName("site-padding");
		form.setAction("/_/issues/new.rom?outform=json");
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);

		SiteButton btnSave = new SiteButton(pay.trans.save(), "");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (NewIssueDlg.this.getNamed() == null || NewIssueDlg.this.getNamed().isEmpty())
					return;
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
				}
				NewIssueDlg.this.hide(true);
			}
		});

		Hidden outform = new Hidden("outform");
		outform.setValue("json");

		Hidden lang = new Hidden("lng");
		lang.setValue(null);

		FileUpload attFile = new FileUpload();
		attFile.setName("attach");

		named.setName("title");
		desc.setName("desc");

		FlexTable f = new FlexTable();
		f.setHTML(0, 0, pay.trans.title());
		f.setWidget(0, 1, named);
		f.setHTML(1, 0, pay.trans.desc());
		f.setWidget(1, 1, desc);
		f.setHTML(2, 0, pay.trans.attachments());
		f.setWidget(2, 1, attFile);
		f.setWidget(3, 0, btnSave);
		f.setWidget(4, 0, outform);
		f.setWidget(4, 1, lang);

		form.add(f);
		return form;
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

	@Override
	public void cancel() {
		setNamed("");
		setDesc("");
	}

	@Override
	public void ok() {
	}
}