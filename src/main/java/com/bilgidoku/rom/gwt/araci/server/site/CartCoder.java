package com.bilgidoku.rom.gwt.araci.server.site;

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



public class CartCoder extends
		TypeCoder<Cart> {

	public CartCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","sid","active","design","confirmed","paysatisfied","payconfirmed","cancelled","shipdate","invoicesent","items","itemsprice","paystyle","payments","calcdetails","shipstyle","shipaddr","shipref","shipdays","shipprice","vatprice","discountprice","totalprice","invoiceaddr","notice","issue","lang_id","validity"});
	}

	@Override
	public  Cart decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Cart c=new Cart();

		c.ri=new LongCoder().decode(json.get("ri"));
		c.host_id=new IntegerCoder().decode(json.get("host_id"));
		c.uri=new StringCoder().decode(json.get("uri"));
		c.container=new StringCoder().decode(json.get("container"));
		c.html_file=new StringCoder().decode(json.get("html_file"));
		c.modified_date=new StringCoder().decode(json.get("modified_date"));
		c.creation_date=new StringCoder().decode(json.get("creation_date"));
		c.delegated=new StringCoder().decode(json.get("delegated"));
		c.ownercid=new StringCoder().decode(json.get("ownercid"));
		c.gid=new StringCoder().decode(json.get("gid"));
		c.relatedcids=new ArrayCoder<String>(new StringCoder()).decode(json.get("relatedcids"));
		c.mask=new LongCoder().decode(json.get("mask"));
		c.nesting=new MapCoder().decode(json.get("nesting"));
		c.dbfs=new ArrayCoder<String>(new StringCoder()).decode(json.get("dbfs"));
		c.weight=new IntegerCoder().decode(json.get("weight"));
		c.lexemes=new StringCoder().decode(json.get("lexemes"));
		c.rtags=new ArrayCoder<String>(new StringCoder()).decode(json.get("rtags"));
		c.aa=new StringCoder().decode(json.get("aa"));
		c.sid=new StringCoder().decode(json.get("sid"));
		c.active=new BooleanCoder().decode(json.get("active"));
		c.design=new BooleanCoder().decode(json.get("design"));
		c.confirmed=new BooleanCoder().decode(json.get("confirmed"));
		c.paysatisfied=new BooleanCoder().decode(json.get("paysatisfied"));
		c.payconfirmed=new BooleanCoder().decode(json.get("payconfirmed"));
		c.cancelled=new BooleanCoder().decode(json.get("cancelled"));
		c.shipdate=new LongCoder().decode(json.get("shipdate"));
		c.invoicesent=new BooleanCoder().decode(json.get("invoicesent"));
		c.items=new JsonCoder().decode(json.get("items"));
		c.itemsprice=new JsonCoder().decode(json.get("itemsprice"));
		c.paystyle=new StringCoder().decode(json.get("paystyle"));
		c.payments=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("payments"));
		c.calcdetails=new JsonCoder().decode(json.get("calcdetails"));
		c.shipstyle=new StringCoder().decode(json.get("shipstyle"));
		c.shipaddr=new JsonCoder().decode(json.get("shipaddr"));
		c.shipref=new StringCoder().decode(json.get("shipref"));
		c.shipdays=new IntegerCoder().decode(json.get("shipdays"));
		c.shipprice=new JsonCoder().decode(json.get("shipprice"));
		c.vatprice=new JsonCoder().decode(json.get("vatprice"));
		c.discountprice=new JsonCoder().decode(json.get("discountprice"));
		c.totalprice=new JsonCoder().decode(json.get("totalprice"));
		c.invoiceaddr=new JsonCoder().decode(json.get("invoiceaddr"));
		c.notice=new StringCoder().decode(json.get("notice"));
		c.issue=new StringCoder().decode(json.get("issue"));
		c.lang_id=new StringCoder().decode(json.get("lang_id"));
		c.validity=new IntegerCoder().decode(json.get("validity"));


		return c;
	}

	@Override
	public Object encode(Cart obj) throws JSONException {
		JSONObject js=new JSONObject();
		js.put("ri",new LongCoder().encode(obj.ri));
		js.put("host_id",new IntegerCoder().encode(obj.host_id));
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("container",new StringCoder().encode(obj.container));
		js.put("html_file",new StringCoder().encode(obj.html_file));
		js.put("modified_date",new StringCoder().encode(obj.modified_date));
		js.put("creation_date",new StringCoder().encode(obj.creation_date));
		js.put("delegated",new StringCoder().encode(obj.delegated));
		js.put("ownercid",new StringCoder().encode(obj.ownercid));
		js.put("gid",new StringCoder().encode(obj.gid));
		js.put("relatedcids",new ArrayCoder<String>(new StringCoder()).encode(obj.relatedcids));
		js.put("mask",new LongCoder().encode(obj.mask));
		js.put("nesting",new MapCoder().encode(obj.nesting));
		js.put("dbfs",new ArrayCoder<String>(new StringCoder()).encode(obj.dbfs));
		js.put("weight",new IntegerCoder().encode(obj.weight));
		js.put("lexemes",new StringCoder().encode(obj.lexemes));
		js.put("rtags",new ArrayCoder<String>(new StringCoder()).encode(obj.rtags));
		js.put("aa",new StringCoder().encode(obj.aa));
		js.put("sid",new StringCoder().encode(obj.sid));
		js.put("active",new BooleanCoder().encode(obj.active));
		js.put("design",new BooleanCoder().encode(obj.design));
		js.put("confirmed",new BooleanCoder().encode(obj.confirmed));
		js.put("paysatisfied",new BooleanCoder().encode(obj.paysatisfied));
		js.put("payconfirmed",new BooleanCoder().encode(obj.payconfirmed));
		js.put("cancelled",new BooleanCoder().encode(obj.cancelled));
		js.put("shipdate",new LongCoder().encode(obj.shipdate));
		js.put("invoicesent",new BooleanCoder().encode(obj.invoicesent));
		js.put("items",new JsonCoder().encode(obj.items));
		js.put("itemsprice",new JsonCoder().encode(obj.itemsprice));
		js.put("paystyle",new StringCoder().encode(obj.paystyle));
		js.put("payments",new ArrayCoder<Json>(new JsonCoder()).encode(obj.payments));
		js.put("calcdetails",new JsonCoder().encode(obj.calcdetails));
		js.put("shipstyle",new StringCoder().encode(obj.shipstyle));
		js.put("shipaddr",new JsonCoder().encode(obj.shipaddr));
		js.put("shipref",new StringCoder().encode(obj.shipref));
		js.put("shipdays",new IntegerCoder().encode(obj.shipdays));
		js.put("shipprice",new JsonCoder().encode(obj.shipprice));
		js.put("vatprice",new JsonCoder().encode(obj.vatprice));
		js.put("discountprice",new JsonCoder().encode(obj.discountprice));
		js.put("totalprice",new JsonCoder().encode(obj.totalprice));
		js.put("invoiceaddr",new JsonCoder().encode(obj.invoiceaddr));
		js.put("notice",new StringCoder().encode(obj.notice));
		js.put("issue",new StringCoder().encode(obj.issue));
		js.put("lang_id",new StringCoder().encode(obj.lang_id));
		js.put("validity",new IntegerCoder().encode(obj.validity));

		return js;
	}

	@Override
	public  Cart[] createArray(int size) {
		return new  Cart[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Cart val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		new LongCoder().setDbValue(db3,val.ri);
		new IntegerCoder().setDbValue(db3,val.host_id);
		new StringCoder().setDbValue(db3,val.uri);
		new StringCoder().setDbValue(db3,val.container);
		new StringCoder().setDbValue(db3,val.html_file);
		new StringCoder().setDbValue(db3,val.modified_date);
		new StringCoder().setDbValue(db3,val.creation_date);
		new StringCoder().setDbValue(db3,val.delegated);
		new StringCoder().setDbValue(db3,val.ownercid);
		new StringCoder().setDbValue(db3,val.gid);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.relatedcids);
		new LongCoder().setDbValue(db3,val.mask);
		new MapCoder().setDbValue(db3,val.nesting);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.dbfs);
		new IntegerCoder().setDbValue(db3,val.weight);
		new StringCoder().setDbValue(db3,val.lexemes);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.rtags);
		new StringCoder().setDbValue(db3,val.aa);
		new StringCoder().setDbValue(db3,val.sid);
		new BooleanCoder().setDbValue(db3,val.active);
		new BooleanCoder().setDbValue(db3,val.design);
		new BooleanCoder().setDbValue(db3,val.confirmed);
		new BooleanCoder().setDbValue(db3,val.paysatisfied);
		new BooleanCoder().setDbValue(db3,val.payconfirmed);
		new BooleanCoder().setDbValue(db3,val.cancelled);
		new LongCoder().setDbValue(db3,val.shipdate);
		new BooleanCoder().setDbValue(db3,val.invoicesent);
		new JsonCoder().setDbValue(db3,val.items);
		new JsonCoder().setDbValue(db3,val.itemsprice);
		new StringCoder().setDbValue(db3,val.paystyle);
		new ArrayCoder<Json>(new JsonCoder()).setDbValue(db3,val.payments);
		new JsonCoder().setDbValue(db3,val.calcdetails);
		new StringCoder().setDbValue(db3,val.shipstyle);
		new JsonCoder().setDbValue(db3,val.shipaddr);
		new StringCoder().setDbValue(db3,val.shipref);
		new IntegerCoder().setDbValue(db3,val.shipdays);
		new JsonCoder().setDbValue(db3,val.shipprice);
		new JsonCoder().setDbValue(db3,val.vatprice);
		new JsonCoder().setDbValue(db3,val.discountprice);
		new JsonCoder().setDbValue(db3,val.totalprice);
		new JsonCoder().setDbValue(db3,val.invoiceaddr);
		new StringCoder().setDbValue(db3,val.notice);
		new StringCoder().setDbValue(db3,val.issue);
		new StringCoder().setDbValue(db3,val.lang_id);
		new IntegerCoder().setDbValue(db3,val.validity);

	}

	@Override
	public String toString(Cart val) throws KnownError {

		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		sb.append(new LongCoder().quoted(val.ri));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.host_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.uri));
		sb.append(',');sb.append(new StringCoder().quoted(val.container));
		sb.append(',');sb.append(new StringCoder().quoted(val.html_file));
		sb.append(',');sb.append(new StringCoder().quoted(val.modified_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.creation_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.delegated));
		sb.append(',');sb.append(new StringCoder().quoted(val.ownercid));
		sb.append(',');sb.append(new StringCoder().quoted(val.gid));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.relatedcids));
		sb.append(',');sb.append(new LongCoder().quoted(val.mask));
		sb.append(',');if(true)throw new RuntimeException("dont call");
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.dbfs));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.weight));
		sb.append(',');sb.append(new StringCoder().quoted(val.lexemes));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.rtags));
		sb.append(',');sb.append(new StringCoder().quoted(val.aa));
		sb.append(',');sb.append(new StringCoder().quoted(val.sid));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.active));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.design));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.confirmed));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.paysatisfied));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.payconfirmed));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.cancelled));
		sb.append(',');sb.append(new LongCoder().quoted(val.shipdate));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.invoicesent));
		sb.append(',');sb.append(new JsonCoder().quoted(val.items));
		sb.append(',');sb.append(new JsonCoder().quoted(val.itemsprice));
		sb.append(',');sb.append(new StringCoder().quoted(val.paystyle));
		sb.append(',');sb.append(new ArrayCoder<Json>(new JsonCoder()).quoted(val.payments));
		sb.append(',');sb.append(new JsonCoder().quoted(val.calcdetails));
		sb.append(',');sb.append(new StringCoder().quoted(val.shipstyle));
		sb.append(',');sb.append(new JsonCoder().quoted(val.shipaddr));
		sb.append(',');sb.append(new StringCoder().quoted(val.shipref));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.shipdays));
		sb.append(',');sb.append(new JsonCoder().quoted(val.shipprice));
		sb.append(',');sb.append(new JsonCoder().quoted(val.vatprice));
		sb.append(',');sb.append(new JsonCoder().quoted(val.discountprice));
		sb.append(',');sb.append(new JsonCoder().quoted(val.totalprice));
		sb.append(',');sb.append(new JsonCoder().quoted(val.invoiceaddr));
		sb.append(',');sb.append(new StringCoder().quoted(val.notice));
		sb.append(',');sb.append(new StringCoder().quoted(val.issue));
		sb.append(',');sb.append(new StringCoder().quoted(val.lang_id));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.validity));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Cart inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Cart val=new Cart();
		val.ri=new LongCoder().getDbValue(db3);
		val.host_id=new IntegerCoder().getDbValue(db3);
		val.uri=new StringCoder().getDbValue(db3);
		val.container=new StringCoder().getDbValue(db3);
		val.html_file=new StringCoder().getDbValue(db3);
		val.modified_date=new StringCoder().getDbValue(db3);
		val.creation_date=new StringCoder().getDbValue(db3);
		val.delegated=new StringCoder().getDbValue(db3);
		val.ownercid=new StringCoder().getDbValue(db3);
		val.gid=new StringCoder().getDbValue(db3);
		val.relatedcids=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.mask=new LongCoder().getDbValue(db3);
		val.nesting=new MapCoder().getDbValue(db3);
		val.dbfs=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.weight=new IntegerCoder().getDbValue(db3);
		val.lexemes=new StringCoder().getDbValue(db3);
		val.rtags=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.aa=new StringCoder().getDbValue(db3);
		val.sid=new StringCoder().getDbValue(db3);
		val.active=new BooleanCoder().getDbValue(db3);
		val.design=new BooleanCoder().getDbValue(db3);
		val.confirmed=new BooleanCoder().getDbValue(db3);
		val.paysatisfied=new BooleanCoder().getDbValue(db3);
		val.payconfirmed=new BooleanCoder().getDbValue(db3);
		val.cancelled=new BooleanCoder().getDbValue(db3);
		val.shipdate=new LongCoder().getDbValue(db3);
		val.invoicesent=new BooleanCoder().getDbValue(db3);
		val.items=new JsonCoder().getDbValue(db3);
		val.itemsprice=new JsonCoder().getDbValue(db3);
		val.paystyle=new StringCoder().getDbValue(db3);
		val.payments=new ArrayCoder<Json>(new JsonCoder()).getDbValue(db3);
		val.calcdetails=new JsonCoder().getDbValue(db3);
		val.shipstyle=new StringCoder().getDbValue(db3);
		val.shipaddr=new JsonCoder().getDbValue(db3);
		val.shipref=new StringCoder().getDbValue(db3);
		val.shipdays=new IntegerCoder().getDbValue(db3);
		val.shipprice=new JsonCoder().getDbValue(db3);
		val.vatprice=new JsonCoder().getDbValue(db3);
		val.discountprice=new JsonCoder().getDbValue(db3);
		val.totalprice=new JsonCoder().getDbValue(db3);
		val.invoiceaddr=new JsonCoder().getDbValue(db3);
		val.notice=new StringCoder().getDbValue(db3);
		val.issue=new StringCoder().getDbValue(db3);
		val.lang_id=new StringCoder().getDbValue(db3);
		val.validity=new IntegerCoder().getDbValue(db3);

		return val;
	}

	public Cart fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Cart val=new Cart();
		int i=0;
		val.ri=new LongCoder().fromString(ms[i++]);
		val.host_id=new IntegerCoder().fromString(ms[i++]);
		val.uri=new StringCoder().fromString(ms[i++]);
		val.container=new StringCoder().fromString(ms[i++]);
		val.html_file=new StringCoder().fromString(ms[i++]);
		val.modified_date=new StringCoder().fromString(ms[i++]);
		val.creation_date=new StringCoder().fromString(ms[i++]);
		val.delegated=new StringCoder().fromString(ms[i++]);
		val.ownercid=new StringCoder().fromString(ms[i++]);
		val.gid=new StringCoder().fromString(ms[i++]);
		val.relatedcids=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.mask=new LongCoder().fromString(ms[i++]);
		if(true)throw new RuntimeException("dont call");
		val.dbfs=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.weight=new IntegerCoder().fromString(ms[i++]);
		val.lexemes=new StringCoder().fromString(ms[i++]);
		val.rtags=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.aa=new StringCoder().fromString(ms[i++]);
		val.sid=new StringCoder().fromString(ms[i++]);
		val.active=new BooleanCoder().fromString(ms[i++]);
		val.design=new BooleanCoder().fromString(ms[i++]);
		val.confirmed=new BooleanCoder().fromString(ms[i++]);
		val.paysatisfied=new BooleanCoder().fromString(ms[i++]);
		val.payconfirmed=new BooleanCoder().fromString(ms[i++]);
		val.cancelled=new BooleanCoder().fromString(ms[i++]);
		val.shipdate=new LongCoder().fromString(ms[i++]);
		val.invoicesent=new BooleanCoder().fromString(ms[i++]);
		val.items=new JsonCoder().fromString(ms[i++]);
		val.itemsprice=new JsonCoder().fromString(ms[i++]);
		val.paystyle=new StringCoder().fromString(ms[i++]);
		val.payments=new ArrayCoder<Json>(new JsonCoder()).fromString(ms[i++]);
		val.calcdetails=new JsonCoder().fromString(ms[i++]);
		val.shipstyle=new StringCoder().fromString(ms[i++]);
		val.shipaddr=new JsonCoder().fromString(ms[i++]);
		val.shipref=new StringCoder().fromString(ms[i++]);
		val.shipdays=new IntegerCoder().fromString(ms[i++]);
		val.shipprice=new JsonCoder().fromString(ms[i++]);
		val.vatprice=new JsonCoder().fromString(ms[i++]);
		val.discountprice=new JsonCoder().fromString(ms[i++]);
		val.totalprice=new JsonCoder().fromString(ms[i++]);
		val.invoiceaddr=new JsonCoder().fromString(ms[i++]);
		val.notice=new StringCoder().fromString(ms[i++]);
		val.issue=new StringCoder().fromString(ms[i++]);
		val.lang_id=new StringCoder().fromString(ms[i++]);
		val.validity=new IntegerCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
