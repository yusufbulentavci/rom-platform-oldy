package com.bilgidoku.rom.site.kamu.pay.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Hidden;

public final class FileUploadDialog extends DialogBox {
	private final UploadFile caller;
	FileUpload fileUpload = new FileUpload();

	public FileUploadDialog(UploadFile caller1, final String container) {
		this.caller = caller1;
		setText(pay.trans.fileUpload());

		final FormPanel form = new FormPanel();
		form.setAction(container + "?outform=json");
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);

		Button cancel = new Button(pay.trans.cancel());
		cancel.setStyleName("site-closebutton");
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FileUploadDialog.this.hide();
			}
		});

		Button btnUploadButton = new Button(pay.trans.upload());
		btnUploadButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {				
				if (fileUpload.getFilename() == null || fileUpload.getFilename().isEmpty()) {
					Window.alert(pay.trans.emptyFileName());
					return;
				}
				form.submit();
//				Ctrl.startWaiting();
			}
		});

		Hidden hidden = new Hidden("outform");
		hidden.setValue("json");
		
		FlexTable f = new FlexTable();
		f.setStyleName("site-padding");
		
		fileUpload.setName("fn");

//		f.setHTML(0, 0, pay.trans.uploadFileTo() + "\n" + container.replace("/f/", ""));
		
		f.setWidget(1, 0, fileUpload);
		f.setWidget(1, 1, btnUploadButton);		
		f.setWidget(2, 0, hidden);
		f.setWidget(2, 1, cancel);
//		f.getFlexCellFormatter().setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_BOTTOM);
//		f.getFlexCellFormatter().setAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_BOTTOM);

		form.add(f);

		setModal(false);
		setWidget(form);
		setSize("100%", "100%");
		setGlassEnabled(true);
		center();

		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				JSONObject jo=JSONParser.parseStrict(event.getResults()).isObject();
				
				caller.fileAdded(jo.get("def").isString().stringValue(),fileUpload.getFilename());
				FileUploadDialog.this.hide();
			}
		});

	}
}