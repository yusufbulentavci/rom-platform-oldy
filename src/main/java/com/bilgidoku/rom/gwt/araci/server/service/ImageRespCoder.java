package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class ImageRespCoder extends
		TypeCoder<ImageResp> {


	public ImageRespCoder(){
		super(false,null);
	}


	@Override
	public  ImageResp decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 ImageResp c=new ImageResp();

		c.id=new StringCoder().decode(json.get("id"));
		c.title=new StringCoder().decode(json.get("title"));
		c.ispaid=new BooleanCoder().decode(json.get("ispaid"));
		c.cost=new StringCoder().decode(json.get("cost"));
		c.source=new StringCoder().decode(json.get("source"));
		c.uri=new StringCoder().decode(json.get("uri"));
		c.width=new IntegerCoder().decode(json.get("width"));
		c.height=new IntegerCoder().decode(json.get("height"));
		c.thumbpath=new StringCoder().decode(json.get("thumbpath"));
		c.thumbwidth=new IntegerCoder().decode(json.get("thumbwidth"));
		c.thumbheight=new IntegerCoder().decode(json.get("thumbheight"));
		c.previewpath=new StringCoder().decode(json.get("previewpath"));
		c.filesize=new LongCoder().decode(json.get("filesize"));


		return c;
	}

	@Override
	public Object encode( ImageResp obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		js.put("id",new StringCoder().encode(obj.id));
		js.put("title",new StringCoder().encode(obj.title));
		js.put("ispaid",new BooleanCoder().encode(obj.ispaid));
		js.put("cost",new StringCoder().encode(obj.cost));
		js.put("source",new StringCoder().encode(obj.source));
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("width",new IntegerCoder().encode(obj.width));
		js.put("height",new IntegerCoder().encode(obj.height));
		js.put("thumbpath",new StringCoder().encode(obj.thumbpath));
		js.put("thumbwidth",new IntegerCoder().encode(obj.thumbwidth));
		js.put("thumbheight",new IntegerCoder().encode(obj.thumbheight));
		js.put("previewpath",new StringCoder().encode(obj.previewpath));
		js.put("filesize",new LongCoder().encode(obj.filesize));

		return js;
	}

	@Override
	public  ImageResp[] createArray(int size) {
		return new  ImageResp[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, ImageResp val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public ImageResp inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public ImageResp fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(ImageResp t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
