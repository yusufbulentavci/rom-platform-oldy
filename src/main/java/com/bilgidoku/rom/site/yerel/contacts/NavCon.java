package com.bilgidoku.rom.site.yerel.contacts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.client.util.com.RtReceiver;
import com.bilgidoku.rom.gwt.client.util.com.WebSocketClient;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.shared.events.RtEvent;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.google.gwt.user.client.ui.Widget;

public class NavCon extends NavBase implements RtReceiver, HasContainer {
	
	public NavCon() {
		super("/_local/images/common/user.png", "/_local/images/common/users.png", "/_local/images/common/user.png", true, 2);		
//		beOnline();
	}

	public void addContainers() {
		getTree().removeItems();

		ContactsDao.list("", Data.CONTACT_CONTAINER, new ContactsResponse() {
			@Override
			public void array(List<Contacts> value) {
				for (int i = 0; i < value.size(); i++) {
					
					Contacts s = value.get(i);
					addContact(s);
					
				}
				
				if (getTree().getItemCount() > 0)
					selectFirstItem();

				
			}
		});

	}

	public void addContact(Contacts con) {
		String seen = null;
		if (con.first_name != null && !con.first_name.equals("")){
			seen = con.first_name;
		}
			
		if (con.last_name != null && !con.last_name.equals("")){
			if(seen==null){
				seen=con.last_name;
			}else{
				seen  += " "+con.last_name;
			}
		}
		if(seen==null){
			seen="noname";
		}
		addLeaf(null, con.uri, MailUtil.getAddr(con), seen, null, true);
	}

	@Override
	public NavToolbarBase createToolbar() {
		NavConToolbar tb = new NavConToolbar(this);
		return tb;
	}

	private WebSocketClient webSocket;

	private final Map<String, List<RtEvent>> dialogHistory = new HashMap<String, List<RtEvent>>();

	public void beOnline() {
//		webSocket = new WebSocketClient("0", "im", null, this);
//		webSocket.connect();
	}

	@Override
	public void onMsg(String rte) {
		// Window.alert(rte.msg());
//		Ctrl.incomingMessage(rte);
	}
	
	@Override
	public void add(Widget w) {
		getToolbar().add(w);
		
	}

	@Override
	public void clear() {
		getToolbar().clear();
		
	}

	@Override
	public Iterator<Widget> iterator() {
		return getToolbar().iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return getToolbar().remove(w);
	}


}
