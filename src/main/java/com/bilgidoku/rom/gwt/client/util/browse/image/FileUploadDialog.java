package com.bilgidoku.rom.gwt.client.util.browse.image;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;

public final class FileUploadDialog extends ActionBarDlg {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private final UploadFile caller;
	private String container;

	public FileUploadDialog(UploadFile caller1, final String container) {
		super(trans.fileUpload() + ": " + container.replaceFirst("/f/", ""), null, null);
		this.caller = caller1;
		this.container = container;

		run();
		show();
		center();

	}

	private static native String getFilesSelected() /*-{
		var count = $wnd.$('input:file')[0].files.length;

		var out = "";

		for (i = 0; i < count; i++) {
			var file = $wnd.$('input:file')[0].files[i];
			out += file.name + ';' + file.size + ";";
		}
		return out;
	}-*/;

	@Override
	public Widget ui() {
		final FileUpload fileUpload = new FileUpload();
		fileUpload.getElement().setPropertyString("multiple", "multiple");
		final FormPanel form = new FormPanel();
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				JSONObject jo = JSONParser.parseStrict(event.getResults()).isObject();
				caller.fileUploaded(jo.get("def").isString().stringValue(), fileUpload.getFilename());
				FileUploadDialog.this.hide();

			}
		});

		form.setAction(container + "?outform=json");
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);

		Button btnUploadButton = new Button(trans.upload());
		btnUploadButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (fileUpload.getFilename() == null || fileUpload.getFilename().isEmpty()) {
					Window.alert(trans.emptyValue(trans.fileName()));
					return;
				}
				form.submit();
			}
		});

		Hidden hidden = new Hidden("outform");
		hidden.setValue("json");

		FlexTable f = new FlexTable();
		fileUpload.setName("fn");
		// f.setHTML(0, 0,
		// Ctrl.trans.uploadFileTo(container.replace("/f/","")));
		f.setHTML(0, 0, trans.chooseFile());
		f.setWidget(0, 1, fileUpload);
		f.setWidget(1, 0, btnUploadButton);
		f.setWidget(2, 0, hidden);
		f.getFlexCellFormatter().setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT,
				HasVerticalAlignment.ALIGN_BOTTOM);
		f.getFlexCellFormatter().setAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT,
				HasVerticalAlignment.ALIGN_BOTTOM);

		form.add(f);

		return form;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ok() {
		// TODO Auto-generated method stub

	}

}