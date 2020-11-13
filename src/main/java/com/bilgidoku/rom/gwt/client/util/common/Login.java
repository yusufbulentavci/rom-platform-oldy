package com.bilgidoku.rom.gwt.client.util.common;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Authenticator;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;

public class Login extends CompBase {
	public static final CompInfo info = new CompInfo("+login", 500, new String[] { "*userneed", "isauth" },
			new String[] { "_wndtop", "+topwindow" }, new String[] {});
	public static final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return new Login();
		}
	};
	private String baseNeed;

	@Override
	public CompInfo compInfo() {
		return info;
	}

	@Override
	public void initial() {
		List<String> ret = RomEntryPoint.cm().askFeature("userneed");
		for(int i=0; i<ret.size(); i=i+2){
			String un = ret.get(i+1);
			switch (un) {
			case "local":
				baseNeed=un;
				break;
			case "contact":
				if(baseNeed!=null)
					baseNeed=un;
				break;
			case "cid":
				break;
			case "logout":
				break;

			default:
				break;
			}
		}
	}
	
	@Override
	public void resolve(){
		if(baseNeed!=null)
			doit(baseNeed);
	}
	
	
	DlgLogin dlg;

	@Override
	public boolean handle(String cmd, JSONObject cjo) throws RunException {
		if (cmd.equals("*userneed")) {
			String mod = cjo.getString("mode");
			doit(mod);
		}
		return true;
	}

	private void doit(String mod) {
		switch (mod) {
		case "local":
			String local = RomEntryPoint.com().get("user");
			if (local == null){
				if(dlg==null)
					dlg = new DlgLogin();
				else
					dlg.show();
			}

			break;
		case "contact":
			boolean contact = RomEntryPoint.com().getBool("isauth");
			if (!contact)
				if(dlg==null)
					dlg = new DlgLogin();
				else
					dlg.show();
			break;
		case "cid":
			Authenticator.needCid(null);
			break;
		case "logout":
			Authenticator.logout();
			break;

		default:
			break;
		}
	}

	@Override
	public void dataChanged(String key, String val) {
		if(key.equals("isauth") && val!=null && dlg!=null){
			dlg.hide();
			
		}
	}

}
