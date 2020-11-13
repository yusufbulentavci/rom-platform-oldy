package com.bilgidoku.rom.site.yerel.admin;

import java.util.HashMap;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class AskForContact extends ActionBarDlg {
	
	ListBox lb = new ListBox();
	public Contacts contact;
	private HashMap<String, Contacts> conList;
	 

	public AskForContact(final HashMap<String, Contacts> conList) {
		super(Ctrl.trans.select(Ctrl.trans.contact()), null, Ctrl.trans.ok());
		this.conList = conList;		
		loadList(conList);

		run();
		this.show();
		this.center();
	}

	private void loadList(HashMap<String, Contacts> conList) {
		Set<String> keySet = conList.keySet();
		for (String key : keySet) {
			lb.addItem(conList.get(key).email, key);
		}
		
	}

	@Override
	public Widget ui() {
		return lb;
	}

	@Override
	public void cancel() {
		contact = null;		
	}

	@Override
	public void ok() {
		contact = null;
		if (lb.getSelectedIndex() >= 0) {
			contact = conList.get(lb.getValue(lb.getSelectedIndex()));
		}
		
	}
}