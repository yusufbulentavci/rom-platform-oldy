package com.bilgidoku.rom.gwt.client.util.fileupload;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public final class FileUploadPanel extends Composite {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private final FileUploadCallback caller;
	private final HTML info = new HTML(); 
	
	FileUpload fileUpload = new FileUpload();
	CheckBox cb = new CheckBox();

	public FileUploadPanel(FileUploadCallback caller1, final String container, final boolean isImage) {
		this.caller = caller1;
		
		fileUpload.getElement().setPropertyString("multiple", "multiple");

		final FormPanel form = new FormPanel();
		SiteButton btnUploadButton = new SiteButton(trans.upload(), trans.upload());
		btnUploadButton.addStyleName("site-smlbtn");
		btnUploadButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (fileUpload.getFilename() == null || fileUpload.getFilename().isEmpty()) {
					Window.alert(trans.emptyValue(trans.fileName()));
					return;
				}
				
				if (isImage && !ClientUtil.isImage(fileUpload.getFilename())) {
					Window.alert(trans.onlyImagesAllowed());
					return;
				}
				
				if (!cb.getValue()) {
					Window.alert("Lütfen \"Resim Ekleme Kurallarını\" okuyup ilgili alanı işaretleyin.");
					return;
				}
				caller.setStatus("Dosya Yükleniyor....");
				ClientUtil.setCursor(Cursor.WAIT);
				form.submit();
			}
		});
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				ClientUtil.setCursor(Cursor.DEFAULT);
				caller.setStatus("Tamamlandı");
				JSONObject jo = JSONParser.parseStrict(event.getResults()).isObject();
				caller.uploaded(fileUpload.getFilename(), jo.get("def").isString().stringValue());
				
				info.setHTML("<span style='font-size:12px'>"+trans.uploadCompleted()+"</span>");				
				Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {					
					@Override
					public boolean execute() {
						info.setHTML("");
						return false;
					}
				}, 3000);				
			}		
			
		});
		

		
		form.setAction(container + "?outform=json");
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);


		Hidden hidden = new Hidden("outform");
		hidden.setValue("json");
		fileUpload.setName("fn");
		fileUpload.addStyleName("site-btn site-smlbtn");
		
		VerticalPanel f = new VerticalPanel();
		f.add(fileUpload);
		f.add(getConfirm());
		f.add(btnUploadButton);
		f.add(info);
		
		form.add(f);
		
		initWidget(form);

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
	
	private Widget getConfirm() {
		Anchor aRs = new Anchor("Resim Ekleme Kurallarını");
		aRs.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		aRs.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		aRs.getElement().getStyle().setPadding(6, Unit.PX);
		aRs.addClickHandler(new ClickHandler() {				
			@Override
			public void onClick(ClickEvent event) {
				new DlgInfo();				
			}
		});
		
		cb.getElement().getStyle().setVerticalAlign(VerticalAlign.SUB);
		
		HTML n = new HTML(" okudum, kabul ediyorum.");
		n.getElement().getStyle().setDisplay(Display.INLINE);
		
		FlowPanel cbPnl = new FlowPanel();
		cbPnl.add(cb);
		cbPnl.add(aRs);
		cbPnl.add(n);
		return cbPnl;

	}
	
	private class DlgInfo extends ActionBarDlg {
		public DlgInfo() {
			super("Resim Ekleme Kuralları:", "", "Tamam");
			run();
			show();
			center();
		}

		@Override
		public Widget ui() {
			HTML text = new HTML("<ul><li>Gönderdiğim resim, tasarım, yazının ticari lisansı ve üretme hakkı bana aittir.</li>" +
					"<li>Eğer, herhangi bir sebeple, kullandığım tasarımın lisans sahibi Bilgidoku Teknoloji ile bağlantı kurarsa, hukiki sorumluluğu bana aittir.</li>" +
					"<li>Üçüncü şahıslara ait telif hakkı ile korunan bir dizayn kullanmak ciddi bir suçtur ve ağır cezaları vardır.</li>" +
					"<li>Şahsımdan kaynaklanan bu tür sorunlarla ilgili Bilgidoku Teknoloji her türlü hukuki yola başvurabilir.</li>");
			text.setSize("500px", "120px");
			return text;
		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ok() {
			cb.setValue(true);
			
		}
	}

	

}