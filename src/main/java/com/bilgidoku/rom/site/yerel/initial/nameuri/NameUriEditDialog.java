package com.bilgidoku.rom.site.yerel.initial.nameuri;

import com.bilgidoku.rom.site.yerel.constants.InitialConstants;
import com.bilgidoku.rom.site.yerel.initial.Details;
import com.bilgidoku.rom.site.yerel.lang.UriGenerator;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class NameUriEditDialog extends DialogBox {
	private final InitialConstants con = GWT.create(InitialConstants.class);
	private static final String[] APP_NAMES = { "standart"};

	private NameUriEditCallback callback;
	private TextBox name = new TextBox();
	private TextBox uri = new TextBox();

	private Button ok = new Button(con.ok());
	private Button cancel = new Button(con.cancel());

	private final CheckBox toMenu = new CheckBox("Put in menu");
	private final CheckBox toFooter = new CheckBox("Put in footer menu");
	private final CheckBox toVitrine = new CheckBox("Put in vitrine");
	private final FlexTable holder = new FlexTable();
	protected final ListBox apps = new ListBox(false);

	public NameUriEditDialog(NameUriEditCallback callback, final boolean isEdit, String named, boolean isGroup, String preferredGroup) {
		this(callback, isEdit, named, isGroup, null, preferredGroup);
	}

	public NameUriEditDialog(NameUriEditCallback callback, final boolean isEdit, String named, boolean isGroup, Details details, String preferredGroup) {
		this.callback = callback;
		initValues(details, isEdit, preferredGroup);
		ui(isGroup);
		name.setFocus(true);
		forOk(isEdit, preferredGroup);
		forCancel();
		forKeyUpName(isGroup, details);
		this.setText(named);
		this.add(holder);
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(true);
		this.center();
	}

	private void forKeyUpName(boolean isGroup, Details initialDetails) {
		if (!isGroup || initialDetails.getUri().length() > 0) {
			name.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					uri.setValue(UriGenerator.generateUri(callback.getContentLang(), name.getValue(), false));
				}
			});
		}
	}

	private void forCancel() {
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				NameUriEditDialog.this.hide(true);
			}
		});

	}

	private void forOk(final boolean isEdit, final String prefferedGroup) {
		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (name.getValue() == null || name.getValue().length() == 0 || uri.getValue() == null || uri.getValue().length() == 0) {
					Window.alert("Name should be valid");
					return;
				}
				if (isEdit) {
					callback.nameChanged(name.getValue(), uri.getValue(), toMenu.getValue(), toFooter.getValue(), toVitrine.getValue(),
							apps.getItemText(apps.getSelectedIndex()));
				} else {
					if (prefferedGroup == null)
						callback.addGetPageGroup(name.getValue(), uri.getValue(), toMenu.getValue(), toFooter.getValue(), toVitrine.getValue(),
								apps.getItemText(apps.getSelectedIndex()));
					else
						callback.addNewPage(name.getValue(), uri.getValue(), toMenu.getValue(), toFooter.getValue(), toVitrine.getValue(),
								apps.getItemText(apps.getSelectedIndex()), prefferedGroup);

				}
				NameUriEditDialog.this.hide(true);
			}
		});
	}

	private void initValues(Details details, boolean isEdit, String preferredGroup) {
		int index = 0;
		for (String it : APP_NAMES) {
			apps.addItem(it);
			if (details != null) {
				if (it.equals(details.app)) {
					index = apps.getItemCount() - 1;
				}
			}
		}
		if (isEdit)
			apps.setSelectedIndex(index);
		else {
			if (preferredGroup == null) // this is a group
				selectFromList("listing");
			else
				selectFromList("writing");
		}

		if (details != null) {
			toMenu.setValue(details.menu);
			toFooter.setValue(details.footer);
			toVitrine.setValue(details.vitrine);
			name.setValue(details.name);
			uri.setValue(details.getUri());
		}
		name.setFocus(true);
	}

	private void ui(boolean isGroup) {
		Label nameLbl = new Label(con.name() + ":");
		Label uriLbl = new Label(con.uriText() + ":");
		Label appLbl = new Label(con.layout() + ":");
		holder.setCellSpacing(5);
		holder.setStyleName("site-padding");
		holder.setWidget(0, 0, nameLbl);
		holder.setWidget(0, 1, name);

		holder.setWidget(1, 0, uriLbl);
		holder.setWidget(1, 1, uri);

		// TODO checkif (isGroup && details.id.length() == 0)
		// uri.setEnabled(false);
		if (isGroup)
			uri.setEnabled(false);

		holder.setWidget(2, 0, appLbl);
		holder.setWidget(2, 1, apps);

		/*
		 * grid.setWidget(3, 0, toMenu);
		 * grid.getFlexCellFormatter().setColSpan(3, 0, 2);
		 * 
		 * grid.setWidget(4, 0, toFooter);
		 * grid.getFlexCellFormatter().setColSpan(4, 0, 2);
		 * 
		 * grid.setWidget(5, 0, toVitrine);
		 * grid.getFlexCellFormatter().setColSpan(5, 0, 2);
		 */
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(ok);
		buttons.add(cancel);
		buttons.setSpacing(5);
		holder.setWidget(3, 0, buttons);
		holder.getFlexCellFormatter().setColSpan(3, 0, 2);
		holder.getFlexCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);

	}

	private void selectFromList(String string) {
		for (int i = 0; i < apps.getItemCount(); i++) {
			String text = apps.getItemText(i);
			if (string.equals(text)) {
				apps.setSelectedIndex(i);
				break;
			}
		}
	}
	public TextBox getName() {
		return name;
	}

	public void setName(TextBox name) {
		this.name = name;
	}

}
