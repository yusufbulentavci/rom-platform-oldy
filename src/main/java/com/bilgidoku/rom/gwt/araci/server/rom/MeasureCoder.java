package com.bilgidoku.rom.gwt.araci.server.rom;

//dbcoder
import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;

import com.bilgidoku.rom.shared.err.*;
import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.pg.dict.*;



import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;


import com.bilgidoku.rom.gwt.araci.server.rom.*;
import com.bilgidoku.rom.gwt.araci.server.bilgi.*;
import com.bilgidoku.rom.gwt.araci.server.site.*;
import com.bilgidoku.rom.gwt.araci.server.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.server.asset.*;



public class MeasureCoder extends
		TypeCoder<Measure> {

	public MeasureCoder(){
		super(true,new String[]{"id","host_id","uri","code","reading_at","amount"});
	}

	@Override
	public  Measure decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Measure c=new Measure();

		c.id=new IntegerCoder().decode(json.get("id"));
		c.host_id=new IntegerCoder().decode(json.get("host_id"));
		c.uri=new StringCoder().decode(json.get("uri"));
		c.code=new StringCoder().decode(json.get("code"));
		c.reading_at=new StringCoder().decode(json.get("reading_at"));
		c.amount=new IntegerCoder().decode(json.get("amount"));


		return c;
	}

	@Override
	public Object encode(Measure obj) throws JSONException {
		JSONObject js=new JSONObject();
		js.put("id",new IntegerCoder().encode(obj.id));
		js.put("host_id",new IntegerCoder().encode(obj.host_id));
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("code",new StringCoder().encode(obj.code));
		js.put("reading_at",new StringCoder().encode(obj.reading_at));
		js.put("amount",new IntegerCoder().encode(obj.amount));

		return js;
	}

	@Override
	public  Measure[] createArray(int size) {
		return new  Measure[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Measure val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		new IntegerCoder().setDbValue(db3,val.id);
		new IntegerCoder().setDbValue(db3,val.host_id);
		new StringCoder().setDbValue(db3,val.uri);
		new StringCoder().setDbValue(db3,val.code);
		new StringCoder().setDbValue(db3,val.reading_at);
		new IntegerCoder().setDbValue(db3,val.amount);

	}

	@Override
	public String toString(Measure val) throws KnownError {

		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		sb.append(new IntegerCoder().quoted(val.id));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.host_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.uri));
		sb.append(',');sb.append(new StringCoder().quoted(val.code));
		sb.append(',');sb.append(new StringCoder().quoted(val.reading_at));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.amount));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Measure inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Measure val=new Measure();
		val.id=new IntegerCoder().getDbValue(db3);
		val.host_id=new IntegerCoder().getDbValue(db3);
		val.uri=new StringCoder().getDbValue(db3);
		val.code=new StringCoder().getDbValue(db3);
		val.reading_at=new StringCoder().getDbValue(db3);
		val.amount=new IntegerCoder().getDbValue(db3);

		return val;
	}

	public Measure fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Measure val=new Measure();
		int i=0;
		val.id=new IntegerCoder().fromString(ms[i++]);
		val.host_id=new IntegerCoder().fromString(ms[i++]);
		val.uri=new StringCoder().fromString(ms[i++]);
		val.code=new StringCoder().fromString(ms[i++]);
		val.reading_at=new StringCoder().fromString(ms[i++]);
		val.amount=new IntegerCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
