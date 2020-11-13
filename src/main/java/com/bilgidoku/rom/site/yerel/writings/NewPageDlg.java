package com.bilgidoku.rom.site.yerel.writings;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
//import com.bilgidoku.site.client.wgts.edit.input.ImageBox;
import com.bilgidoku.rom.gwt.client.widgets.ImageBox;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.shared.Utils;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteLabel;
import com.bilgidoku.rom.site.yerel.writings.wizard.PnlTemplates;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NewPageDlg extends DialogBox {

	private final TextBox txtTitle = new TextBox();
	private final HTML warnTitle = new HTML();

	private final TextBox txtUri = new TextBox();
	private final HTML warnUri = new HTML();

	private PnlTemplates templates = new PnlTemplates();
	private TextArea txtSummary = new TextArea();
	private ImageBox imgIcon = new ImageBox();
	private final HTML txtPath;
	private final LangList langList = new LangList();

	public NewPageDlg(String initial, String extension, String path, final String rootPath, boolean isContainer,
			String dlgTitle) {
		if (!isContainer && !path.equals("/")) {
			path = path + "/";
		}

		final String myPath = path;

		txtPath = new HTML(myPath);
		txtPath.getElement().getStyle().setFontSize(10, Unit.PX);
		txtTitle.setValue(initial);
		txtUri.setValue(null);

		txtTitle.setWidth("260px");
		txtSummary.setSize("260px", "40px");
		txtUri.setWidth("260px");
		imgIcon.setImage(null);

		warnUri.setStyleName("site-warning");
		warnTitle.setStyleName("site-warning");

		txtTitle.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				warnUri.setHTML("");
				warnTitle.setHTML("");

				String title = txtTitle.getValue();

				if (title.isEmpty()) {
					warnTitle.setHTML(Ctrl.trans.emptyValue(""));
					return;
				}

				// if (!ClientUtil.checkTitle(title)) {
				// warnTitle.setHTML(Ctrl.trans.notValid(""));
				// return;
				// }

				fixTitle(myPath);
			}
		});
		
		txtTitle.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					ok();
				}
			}
		});

		
		txtTitle.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				fixTitle(myPath);
			}
		}, PasteEvent.TYPE);

		txtUri.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				warnUri.setHTML("");
				String uri = txtUri.getValue();

				if (uri.isEmpty()) {
					warnUri.setHTML(Ctrl.trans.emptyValue(""));
					return;
				}

				if (!ClientUtil.checkUri(uri)) {
					warnUri.setHTML(Ctrl.trans.notValid(""));
					return;
				}

			}
		});

		Button btnOK = new Button(Ctrl.trans.ok());
		btnOK.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				forOk();
			}
		});

		Button btnCancel = new Button("Cancel");
		btnCancel.setStyleName("site-closebutton");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				txtTitle.setValue(null);
				txtUri.setValue(null);
				hide(true);
			}
		});

		HorizontalPanel hpTitle = new HorizontalPanel();
		hpTitle.add(txtTitle);
		hpTitle.add(warnTitle);

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(txtUri);
		hp.add(new HTML(extension));
		hp.add(warnUri);

		VerticalPanel vpUri = new VerticalPanel();
		vpUri.add(txtPath);
		vpUri.add(hp);

		FlexTable ft = new FlexTable();
		ft.setCellPadding(2);

		templates.setSize("372px", "260px");

		ft.setHTML(0, 0, Ctrl.trans.pageLang());
		ft.setWidget(0, 1, langList);

		ft.setHTML(1, 0, Ctrl.trans.title());
		ft.setWidget(1, 1, hpTitle);

		ft.setHTML(2, 0, Ctrl.trans.internetUri());
		ft.setWidget(2, 1, vpUri);

		ft.setWidget(3, 0, new SiteLabel(Ctrl.trans.summary(), Ctrl.trans.summaryDesc()));
		ft.setWidget(3, 1, txtSummary);

		ft.setHTML(4, 0, Ctrl.trans.image());
		ft.setWidget(4, 1, imgIcon);

		ft.setHTML(5, 0, Ctrl.trans.templates());
		ft.setWidget(5, 1, templates);

		ft.setWidget(6, 0, btnOK);
		ft.setWidget(6, 1, btnCancel);

		ft.getFlexCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_TOP);
		ft.getFlexCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_LEFT);

		this.setWidget(ft);
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(true);
		this.setText(dlgTitle);
		this.center();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				txtTitle.setFocus(true);
			}
		});
	}

	protected void forOk() {
		ok();
	}

	private void ok() {
		String title = txtTitle.getValue();
		String uri = txtUri.getValue();

		if (title.isEmpty()) {
			warnTitle.setHTML(Ctrl.trans.emptyValue(""));
			txtTitle.setFocus(true);
			return;
		}

		// if (!ClientUtil.checkTitle(title)) {
		// warnTitle.setHTML(Ctrl.trans.notValid(""));
		// txtTitle.setFocus(true);
		// return;
		// }

		if (uri.isEmpty()) {
			warnUri.setHTML(Ctrl.trans.emptyValue(""));
			txtUri.setFocus(true);
			return;
		}

		if (!ClientUtil.checkUri(uri)) {
			warnUri.setHTML(Ctrl.trans.validUri());
			txtUri.setFocus(true);

			return;
		}
		hide(true);

		
	}

	protected void fixUri(String myPath) {
		String u = Utils.nameFix(txtTitle.getValue());
		txtUri.setValue(u);
		txtPath.setHTML(myPath + u);

	}

	protected void fixTitle(String myPath) {
		// : // vs char olamaz
		String u = Utils.nameFix(txtTitle.getValue());
		txtUri.setValue(u);
		txtPath.setHTML(myPath + u);

	}

	public String getFixedTitle() {
		return txtUri.getValue().trim();
	}

	public String getNamed() {
		return txtTitle.getValue();
	}

	public String getSummary() {
		return txtSummary.getValue();
	}

	public String getImage() {
		return imgIcon.getImgPath();
	}

	public String getTemplate() {
		return templates.getSelected();
	}

	public boolean isPrivate() {
		return true;
	}

	// public boolean toTop() {
	// // return chkTopLevel.getValue();
	// return false;
	// }

	public String getPageLang() {
		return langList.getSelectedLang();
	}
}