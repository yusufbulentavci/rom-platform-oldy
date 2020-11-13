package com.bilgidoku.rom.site.yerel.mail;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

public class ContactSelectWindow extends ActionBarDlg {

	private final CellTable<Contacts> cellTable = new CellTable<Contacts>();
	protected final MultiSelectionModel<Contacts> selectionModel = new MultiSelectionModel<Contacts>();
	private final TextBoxMailAddr form;

	public ContactSelectWindow(List<Contacts> contacts, TextBoxMailAddr contactList) {
		super(Ctrl.trans.selectContacts(), null, Ctrl.trans.ok());
		this.form = contactList;
		cellTable.setRowData(contacts);

		run();
		center();
	}

	private ScrollPanel uiTable() {
		cellTable.setFocus(false);
		cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Contacts>createCheckboxManager());
		Column<Contacts, Boolean> checkColumn = new Column<Contacts, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Contacts object) {
				return selectionModel.isSelected(object);
			}
		};
		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);

		TextColumn<Contacts> colFirstName = new TextColumn<Contacts>() {
			public String getValue(Contacts object) {
				return object.first_name;
			}
		};
		cellTable.addColumn(colFirstName, Ctrl.trans.firstName());

		TextColumn<Contacts> colLastName = new TextColumn<Contacts>() {
			public String getValue(Contacts object) {
				return object.last_name;
			}
		};
		cellTable.addColumn(colLastName, Ctrl.trans.lastName());

		TextColumn<Contacts> colEMail = new TextColumn<Contacts>() {
			public String getValue(Contacts object) {
				if (object.email == null || object.email.isEmpty())
					return Ctrl.trans.eMailEmpty();
				else
					return object.email;
			}
		};
		cellTable.addColumn(colEMail, Ctrl.trans.eMail());

		ScrollPanel sp = new ScrollPanel();
		sp.add(cellTable);
		sp.setHeight("350px");
		return sp;
	}

	@Override
	public Widget ui() {
		return uiTable();
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
		Set<Contacts> selected = selectionModel.getSelectedSet();
		for (Iterator<Contacts> iterator = selected.iterator(); iterator.hasNext();) {
			Contacts contact = (Contacts) iterator.next();
			// form.addItem(MailUtil.getAddr(contact));
			form.addItem(contact.email);
		}

	}

}
