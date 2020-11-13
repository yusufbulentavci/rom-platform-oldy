package com.bilgidoku.rom.web.db;

//dbcoder
import com.bilgidoku.rom.gwt.server.common.Json;
import com.bilgidoku.rom.gwt.server.common.coders.ArrayCoder;
import com.bilgidoku.rom.gwt.server.common.coders.BooleanCoder;
import com.bilgidoku.rom.gwt.server.common.coders.IntegerCoder;
import com.bilgidoku.rom.gwt.server.common.coders.JsonCoder;
import com.bilgidoku.rom.gwt.server.common.coders.LongCoder;
import com.bilgidoku.rom.gwt.server.common.coders.MapCoder;
import com.bilgidoku.rom.gwt.server.common.coders.StringCoder;
import com.bilgidoku.rom.gwt.server.common.coders.TypeCoder;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.dbop.Info;




public class InfoCoder extends
		TypeCoder<Info> {

	public InfoCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","title","summary","tip","icon","medium_icon","large_icon","multilang_icon","sound","langcodes","viewy","style","headertext","default_app","palette","text_font","site_footer","address","browser_title","banner_img","logo_img","browser_icon","text1","text2","menu1","menu2","list1","list2","ecommerce","login"});
	}

	@Override
	public  Info decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Info c=new Info();

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
		c.title=new ArrayCoder<String>(new StringCoder()).decode(json.get("title"));
		c.summary=new ArrayCoder<String>(new StringCoder()).decode(json.get("summary"));
		c.tip=new ArrayCoder<String>(new StringCoder()).decode(json.get("tip"));
		c.icon=new StringCoder().decode(json.get("icon"));
		c.medium_icon=new StringCoder().decode(json.get("medium_icon"));
		c.large_icon=new StringCoder().decode(json.get("large_icon"));
		c.multilang_icon=new ArrayCoder<String>(new StringCoder()).decode(json.get("multilang_icon"));
		c.sound=new StringCoder().decode(json.get("sound"));
		c.langcodes=new ArrayCoder<String>(new StringCoder()).decode(json.get("langcodes"));
		c.viewy=new JsonCoder().decode(json.get("viewy"));
		c.style=new StringCoder().decode(json.get("style"));
		c.headertext=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("headertext"));
		c.default_app=new StringCoder().decode(json.get("default_app"));
		c.palette=new JsonCoder().decode(json.get("palette"));
		c.text_font=new JsonCoder().decode(json.get("text_font"));
		c.site_footer=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("site_footer"));
		c.address=new JsonCoder().decode(json.get("address"));
		c.browser_title=new ArrayCoder<String>(new StringCoder()).decode(json.get("browser_title"));
		c.banner_img=new StringCoder().decode(json.get("banner_img"));
		c.logo_img=new JsonCoder().decode(json.get("logo_img"));
		c.browser_icon=new StringCoder().decode(json.get("browser_icon"));
		c.text1=new ArrayCoder<String>(new StringCoder()).decode(json.get("text1"));
		c.text2=new ArrayCoder<String>(new StringCoder()).decode(json.get("text2"));
		c.menu1=new StringCoder().decode(json.get("menu1"));
		c.menu2=new StringCoder().decode(json.get("menu2"));
		c.list1=new StringCoder().decode(json.get("list1"));
		c.list2=new StringCoder().decode(json.get("list2"));
		c.ecommerce=new BooleanCoder().decode(json.get("ecommerce"));
		c.login=new BooleanCoder().decode(json.get("login"));


		return c;
	}

	@Override
	public Object encode(Info obj) throws JSONException {
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
		js.put("title",new ArrayCoder<String>(new StringCoder()).encode(obj.title));
		js.put("summary",new ArrayCoder<String>(new StringCoder()).encode(obj.summary));
		js.put("tip",new ArrayCoder<String>(new StringCoder()).encode(obj.tip));
		js.put("icon",new StringCoder().encode(obj.icon));
		js.put("medium_icon",new StringCoder().encode(obj.medium_icon));
		js.put("large_icon",new StringCoder().encode(obj.large_icon));
		js.put("multilang_icon",new ArrayCoder<String>(new StringCoder()).encode(obj.multilang_icon));
		js.put("sound",new StringCoder().encode(obj.sound));
		js.put("langcodes",new ArrayCoder<String>(new StringCoder()).encode(obj.langcodes));
		js.put("viewy",new JsonCoder().encode(obj.viewy));
		js.put("style",new StringCoder().encode(obj.style));
		js.put("headertext",new ArrayCoder<Json>(new JsonCoder()).encode(obj.headertext));
		js.put("default_app",new StringCoder().encode(obj.default_app));
		js.put("palette",new JsonCoder().encode(obj.palette));
		js.put("text_font",new JsonCoder().encode(obj.text_font));
		js.put("site_footer",new ArrayCoder<Json>(new JsonCoder()).encode(obj.site_footer));
		js.put("address",new JsonCoder().encode(obj.address));
		js.put("browser_title",new ArrayCoder<String>(new StringCoder()).encode(obj.browser_title));
		js.put("banner_img",new StringCoder().encode(obj.banner_img));
		js.put("logo_img",new JsonCoder().encode(obj.logo_img));
		js.put("browser_icon",new StringCoder().encode(obj.browser_icon));
		js.put("text1",new ArrayCoder<String>(new StringCoder()).encode(obj.text1));
		js.put("text2",new ArrayCoder<String>(new StringCoder()).encode(obj.text2));
		js.put("menu1",new StringCoder().encode(obj.menu1));
		js.put("menu2",new StringCoder().encode(obj.menu2));
		js.put("list1",new StringCoder().encode(obj.list1));
		js.put("list2",new StringCoder().encode(obj.list2));
		js.put("ecommerce",new BooleanCoder().encode(obj.ecommerce));
		js.put("login",new BooleanCoder().encode(obj.login));

		return js;
	}

	@Override
	public  Info[] createArray(int size) {
		return new  Info[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Info val) throws KnownError {
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
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.title);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.summary);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.tip);
		new StringCoder().setDbValue(db3,val.icon);
		new StringCoder().setDbValue(db3,val.medium_icon);
		new StringCoder().setDbValue(db3,val.large_icon);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.multilang_icon);
		new StringCoder().setDbValue(db3,val.sound);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.langcodes);
		new JsonCoder().setDbValue(db3,val.viewy);
		new StringCoder().setDbValue(db3,val.style);
		new ArrayCoder<Json>(new JsonCoder()).setDbValue(db3,val.headertext);
		new StringCoder().setDbValue(db3,val.default_app);
		new JsonCoder().setDbValue(db3,val.palette);
		new JsonCoder().setDbValue(db3,val.text_font);
		new ArrayCoder<Json>(new JsonCoder()).setDbValue(db3,val.site_footer);
		new JsonCoder().setDbValue(db3,val.address);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.browser_title);
		new StringCoder().setDbValue(db3,val.banner_img);
		new JsonCoder().setDbValue(db3,val.logo_img);
		new StringCoder().setDbValue(db3,val.browser_icon);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.text1);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.text2);
		new StringCoder().setDbValue(db3,val.menu1);
		new StringCoder().setDbValue(db3,val.menu2);
		new StringCoder().setDbValue(db3,val.list1);
		new StringCoder().setDbValue(db3,val.list2);
		new BooleanCoder().setDbValue(db3,val.ecommerce);
		new BooleanCoder().setDbValue(db3,val.login);

	}

	@Override
	public String toString(Info val) throws KnownError {

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
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.title));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.summary));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.tip));
		sb.append(',');sb.append(new StringCoder().quoted(val.icon));
		sb.append(',');sb.append(new StringCoder().quoted(val.medium_icon));
		sb.append(',');sb.append(new StringCoder().quoted(val.large_icon));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.multilang_icon));
		sb.append(',');sb.append(new StringCoder().quoted(val.sound));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.langcodes));
		sb.append(',');sb.append(new JsonCoder().quoted(val.viewy));
		sb.append(',');sb.append(new StringCoder().quoted(val.style));
		sb.append(',');sb.append(new ArrayCoder<Json>(new JsonCoder()).quoted(val.headertext));
		sb.append(',');sb.append(new StringCoder().quoted(val.default_app));
		sb.append(',');sb.append(new JsonCoder().quoted(val.palette));
		sb.append(',');sb.append(new JsonCoder().quoted(val.text_font));
		sb.append(',');sb.append(new ArrayCoder<Json>(new JsonCoder()).quoted(val.site_footer));
		sb.append(',');sb.append(new JsonCoder().quoted(val.address));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.browser_title));
		sb.append(',');sb.append(new StringCoder().quoted(val.banner_img));
		sb.append(',');sb.append(new JsonCoder().quoted(val.logo_img));
		sb.append(',');sb.append(new StringCoder().quoted(val.browser_icon));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.text1));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.text2));
		sb.append(',');sb.append(new StringCoder().quoted(val.menu1));
		sb.append(',');sb.append(new StringCoder().quoted(val.menu2));
		sb.append(',');sb.append(new StringCoder().quoted(val.list1));
		sb.append(',');sb.append(new StringCoder().quoted(val.list2));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.ecommerce));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.login));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Info inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Info val=new Info();
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
		val.title=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.summary=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.tip=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.icon=new StringCoder().getDbValue(db3);
		val.medium_icon=new StringCoder().getDbValue(db3);
		val.large_icon=new StringCoder().getDbValue(db3);
		val.multilang_icon=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.sound=new StringCoder().getDbValue(db3);
		val.langcodes=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.viewy=new JsonCoder().getDbValue(db3);
		val.style=new StringCoder().getDbValue(db3);
		val.headertext=new ArrayCoder<Json>(new JsonCoder()).getDbValue(db3);
		val.default_app=new StringCoder().getDbValue(db3);
		val.palette=new JsonCoder().getDbValue(db3);
		val.text_font=new JsonCoder().getDbValue(db3);
		val.site_footer=new ArrayCoder<Json>(new JsonCoder()).getDbValue(db3);
		val.address=new JsonCoder().getDbValue(db3);
		val.browser_title=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.banner_img=new StringCoder().getDbValue(db3);
		val.logo_img=new JsonCoder().getDbValue(db3);
		val.browser_icon=new StringCoder().getDbValue(db3);
		val.text1=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.text2=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.menu1=new StringCoder().getDbValue(db3);
		val.menu2=new StringCoder().getDbValue(db3);
		val.list1=new StringCoder().getDbValue(db3);
		val.list2=new StringCoder().getDbValue(db3);
		val.ecommerce=new BooleanCoder().getDbValue(db3);
		val.login=new BooleanCoder().getDbValue(db3);

		return val;
	}

	public Info fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Info val=new Info();
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
		val.title=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.summary=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.tip=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.icon=new StringCoder().fromString(ms[i++]);
		val.medium_icon=new StringCoder().fromString(ms[i++]);
		val.large_icon=new StringCoder().fromString(ms[i++]);
		val.multilang_icon=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.sound=new StringCoder().fromString(ms[i++]);
		val.langcodes=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.viewy=new JsonCoder().fromString(ms[i++]);
		val.style=new StringCoder().fromString(ms[i++]);
		val.headertext=new ArrayCoder<Json>(new JsonCoder()).fromString(ms[i++]);
		val.default_app=new StringCoder().fromString(ms[i++]);
		val.palette=new JsonCoder().fromString(ms[i++]);
		val.text_font=new JsonCoder().fromString(ms[i++]);
		val.site_footer=new ArrayCoder<Json>(new JsonCoder()).fromString(ms[i++]);
		val.address=new JsonCoder().fromString(ms[i++]);
		val.browser_title=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.banner_img=new StringCoder().fromString(ms[i++]);
		val.logo_img=new JsonCoder().fromString(ms[i++]);
		val.browser_icon=new StringCoder().fromString(ms[i++]);
		val.text1=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.text2=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.menu1=new StringCoder().fromString(ms[i++]);
		val.menu2=new StringCoder().fromString(ms[i++]);
		val.list1=new StringCoder().fromString(ms[i++]);
		val.list2=new StringCoder().fromString(ms[i++]);
		val.ecommerce=new BooleanCoder().fromString(ms[i++]);
		val.login=new BooleanCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
