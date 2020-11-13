package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.shared.Utils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NewItemDlg extends ActionBarDlg {

	private final TextBox txtTitle = new TextBox();
	private final TextBox txtUri = new TextBox();
	private final HTML ext = new HTML();
	
	public boolean isOk = false;
	
	private final CheckBox isOnTop = new CheckBox();
	private final HTML htmlPath = new HTML();
	private String delegated;
	private boolean isContainer;
	private String rootPath;
	private String delegated1;
	private String path;
	
	public NewItemDlg(String path, String dlgTitle, String initial, String extension) {
		// for rename item
		this(path, dlgTitle, false, null, null);
		txtTitle.setValue(initial);
		txtUri.setValue(Utils.nameFix(initial));
		ext.setHTML("." + extension);
	}

	public NewItemDlg(final String path, String dlgTitle, boolean isContainer, final String rootPath, final String delegated1) {
		super(dlgTitle, null, "OK");
		this.path = path;
		this.isContainer = isContainer;
		this.rootPath = rootPath;
		this.delegated1 = delegated1;
		this.delegated = delegated1;
		isOk = false;
		
		run();
		show();
		center();
	}

	public String getFixedTitle() {
		return txtUri.getValue();
	}

	public String getNamed() {
		return txtTitle.getValue();
	}

	/**
	 * @return the isOnTop
	 */
	public boolean getIsOnTop() {
		return isOnTop.getValue();
	}
	
	public String getUri() {
		if (txtTitle.getValue() == null || txtTitle.getValue().isEmpty() || txtUri.getValue() == null || txtUri.getValue().isEmpty())
			return null;
		return htmlPath.getText() + txtUri.getValue().trim(); 
	}
	
	public String getDelegated() {
		return delegated;
	}

	@Override
	public Widget ui() {
		txtTitle.setWidth("450px");
		txtUri.setWidth("450px");

		HTML warn = new HTML("uriWarning()");
		warn.setStyleName("site-warning");

		txtTitle.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String u = Utils.nameFix(txtTitle.getValue());
				txtUri.setValue(u);

				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					isOk = true;
					hide(true);
				}
			}
		});

		txtUri.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					isOk = true;
					hide(true);
				}
			}
		});

		HorizontalPanel adr = new HorizontalPanel();
		adr.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		adr.add(txtUri);
		adr.add(ext);
		
		htmlPath.setHTML(path);

		VerticalPanel hp = new VerticalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(htmlPath);
		hp.add(adr);

		FlexTable ft = new FlexTable();
		ft.setCellPadding(2);

		if (isContainer) {
			ft.setHTML(1, 0, "En Üste Ekle");
			ft.setWidget(1, 1, isOnTop);
			ft.getFlexCellFormatter().setWidth(1, 0, "75px");
			isOnTop.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {					
					if (isOnTop.getValue()) {
						htmlPath.setHTML(rootPath);
						delegated = "";
					} else {
						htmlPath.setHTML(path);
						delegated = delegated1;						
					}					
				}
			});
		}

		ft.setHTML(2, 0, "Başlık");
		ft.setWidget(2, 1, txtTitle);
		// ft.setWidget(1, 2, warn);

		ft.setHTML(3, 0, "Uri");
		ft.setWidget(3, 1, hp);

		ft.setHeight("50px");
		ft.getFlexCellFormatter().setColSpan(0, 0, 2);

		return ft;
	}

	@Override
	public void cancel() {
		txtTitle.setValue(null);
		txtUri.setValue(null);

		
	}

	@Override
	public void ok() {
		isOk = true;
	}


}