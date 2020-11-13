package com.bilgidoku.rom.web.db.usersites;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.web.grow.GenislemeGorevlisi;

public class createhost extends BeforeHook{

	
	
	@Override
	public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, KnownError, ParameterError {
		
		String email=scope.getParam("email", 4, 50, true);
		String country=scope.getParam("country", 2, 2, true).toUpperCase();
		String lang=scope.getParam("lang", 2, 2, true).toLowerCase();
		String adminName=scope.getParam("adminname", 2, 50, true);
		String password=scope.getParam("password", 5, 30, true);
		String ip=scope.getRequest().getSession().getIpAddress();
		String ipCountry=scope.getRequest().getSession().getCountry();
		
		scope.paramOverride("ipcountry", ipCountry);
		Integer remoteHostId = newHost(email, adminName, password, country, lang);
		
		scope.paramOverride("remotehost", remoteHostId.toString());
		
		return false;
	}

	@Override
	public void undo(HookScope scope) throws KnownError, ParameterError {
	}
	
	private Integer newHost(String email, String adminName, String password, String cc, String lang) throws KnownError {
		try {
			JSONObject dataClient = new JSONObject();
			dataClient.put("lang", lang);
			dataClient.put("admin", adminName);
			dataClient.put("credential", password);
			dataClient.put("manual", true);
			JSONObject contact = techcontact(email, cc);
			dataClient.put("contact", contact);

			dataClient.put("business", "Business");
			dataClient.put("cc", cc);

			JSONObject dataServer = new JSONObject();
			dataServer.put("email", contact.getString("email"));
			// dataServer.put("money", 100000);

			JSONObject jo = new JSONObject();
			jo.put("s", dataServer);
			jo.put("c", dataClient);

			JSONObject m = new JSONObject();
			m.put("d", jo);

			Integer ret = GenislemeGorevlisi.tek().growInternal(m);
			return ret;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	public JSONObject techcontact(String email, String cc) throws KnownError {
		JSONObject ret = new JSONObject();
		try {
			ret.put("firstName", "Firstname").put("lastName", "Lastname").put("organization", "Organization")
					.put("email", email).put("address", "Address").put("countryCode", cc).put("city", "City");
			return ret;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

}
