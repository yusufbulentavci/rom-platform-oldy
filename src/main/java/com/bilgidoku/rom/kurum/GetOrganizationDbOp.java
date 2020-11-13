package com.bilgidoku.rom.kurum;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetOrganizationDbOp extends DbOp {
	private static final MC mc = new MC(GetOrganizationDbOp.class);

	protected static final String query = "select issue_year,issue_nextid,shipstyle,paystyle"
			+ " from rom.org"
			+ " where host_id=?";

	public Organization doit(Integer hostId) throws KnownError {

		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.executeQuery();
			hasNext(db3);
			Integer issueYear = db3.getInt();
			Integer issueNext = db3.getInt();
			JSONObject shipStyle=(JSONObject) getDbValue(db3);
			JSONObject payStyle=(JSONObject) getDbValue(db3);
			return new Organization(hostId, issueYear, issueNext, shipStyle, payStyle);
		} catch (JSONException e) {
			throw new KnownError("While getting organization for id:"+hostId, e);
		}

	}

	public Object getDbValue(DbSetGet db3) throws KnownError, JSONException {
		String val=db3.getString();
		if(val==null)
			return null;
		return decodeCode(val);
	}

	protected Object decodeCode(String val) throws JSONException {
		if(val.equalsIgnoreCase("NULL")){
			return null;
		}
		
		if(val.startsWith("[")){
			return new JSONArray(val);
		}
		
		return new JSONObject(val);
	}
}
