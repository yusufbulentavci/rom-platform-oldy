package com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.site.kamu.graph.client.ui.SimpleChangeListener;
import com.bilgidoku.rom.site.kamu.graph.client.ui.forms.ImageEditor;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.fileupload.FileUploadCallback;
import com.bilgidoku.rom.gwt.client.util.fileupload.FileUploadPanel;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgUploadForm extends ActionBarDlg {

	private final ImageForm frm = new ImageForm();
	private ChangeCallback caller;
	private String userDir;

	public DlgUploadForm(final ChangeCallback caller, String userDir) {
		super(GraphicEditor.trans.imageUpload(), null, null);
		this.caller = caller;
		this.userDir = userDir;

		frm.setVisible(false);
		run();
		this.setGlassEnabled(true);
		this.show();
		this.center();
	}

	@Override
	public Widget ui() {
		FileUploadPanel pnl = new FileUploadPanel(new FileUploadCallback() {
			@Override
			public void uploaded(String name, String uri) {
				ClientUtil.setCursor(Cursor.DEFAULT);
				frm.setImage(uri);
				frm.setVisible(true);
			}

			@Override
			public void setStatus(String text) {
				caller.setStatus(text);
			}
		}, userDir, true);

		VerticalPanel holder = new VerticalPanel();
		holder.setSpacing(12);
		holder.add(pnl);
		holder.add(new HTML(GraphicEditor.trans.uploadNote2()));
		holder.add(new HTML(GraphicEditor.trans.uploadNote1()));

		VerticalPanel hp = new VerticalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		hp.add(holder);
		hp.add(frm);

		return hp;
	}

	private class ImageForm extends Composite {
		private Image image = new Image();
		private SiteButton useIt = new SiteButton("/_public/images/check.png", GraphicEditor.trans.useIt(), GraphicEditor.trans.useIt());
		private SiteButton changeIt = new SiteButton("/_public/images/bar/pencil.png", GraphicEditor.trans.changeImage(),
				GraphicEditor.trans.changeImage());
		private String uri;

		public ImageForm() {
			image.setStyleName("site-box");

			VerticalPanel vp = new VerticalPanel();
			vp.setSpacing(4);
			vp.add(changeIt);
			vp.add(useIt);

			HorizontalPanel hp = new HorizontalPanel();
			hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
			hp.setStyleName("site-padding");
			hp.add(image);
			hp.add(vp);

			forClickImage();
			forUse();
			forChange();

			initWidget(hp);
		}

		private void forClickImage() {
			image.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					caller.newImage(uri);
					DlgUploadForm.this.hide();
				}
			});
		}

		public void setImage(String uri) {
			this.uri = uri;
			image.setUrl(uri + "?romthumb=s");
			image.setSize("150px", "120px");
		}

		private void forUse() {
			useIt.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					caller.newImage(uri);
					DlgUploadForm.this.hide();
				}
			});

		}

		private void forChange() {
			changeIt.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new ImageEditor(new SimpleChangeListener() {

						@Override
						public void changed(String source) {
//							 setImage(ClientUtil.getLastUri(uri));
							caller.newImage(ClientUtil.getLastUri(uri));
							DlgUploadForm.this.hide();
						}

						@Override
						public void changed(Composite source, Integer oldone, Integer newone) {
							// TODO Auto-generated method stub

						}
					}, uri);

				}
			});

		}

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
