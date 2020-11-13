package com.bilgidoku.rom.cokluortam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Img {

	public String parent;

	public int pr;
	public String pid;
	public String url;

	public String title;
	public String note;

	public String author;
	public String authoremail;

	public List<String> tags=new ArrayList<String>();
	public int uses;
	public Integer weight;

	public Long cost;

	public Integer width;
	public Integer height;

	public Long usecount;

	public Boolean passive;

	public String license;
	public String format;

	public Img(){
		
	}
	public Img(DbThree db3) throws KnownError {
		this.pr=db3.getInt();
		this.pid=db3.getString();
		String[] ts = db3.getStringArray();
		for (String string : ts) {
			tags.add(string);
		}
		this.uses=db3.getInt();
		this.weight=db3.getInt();
		this.format=db3.getString();
	}
	
	public void set(DbThree db3) throws KnownError {
//		p_pack text, p_id text, p_provider text, p_pid text, p_tags text
		db3.setInt(pr);
		db3.setString(pid);
		String[] ts=tags.toArray(new String[tags.size()]);
		db3.setStringArray(ts);
		db3.setInt(uses);
		db3.setInt(weight);
		db3.setString(format);
	}
	
	public boolean valid() {
		return this.weight!=null;
	}
	
	public String toString(){
		Gson gson = new GsonBuilder().create();
		String p = gson.toJson(this, Img.class);
		return p;
	}

	public void formatFromUrl() {
		this.format=url.substring(url.lastIndexOf('.')+1).toLowerCase();
		if(format.equals("jpg")){
			format="jpeg";
		}
	}

	public static Img fromJsonFile(File f) throws FileNotFoundException, IOException{
		Gson gson = new GsonBuilder().create();
		try(JsonReader fr=new JsonReader(new FileReader(f))){
			return gson.fromJson(fr, Img.class);
		}
	}
	

	public static void main(String[] args) {

		Img i = new Img();
		i.usecount = 5L;
		i.url = "url";
		Gson gson = new GsonBuilder().create();
		String p = gson.toJson(i, Img.class);
		
//		syso(p);

		Img ii=gson.fromJson(p, Img.class);
//		syso(ii.usecount);
	}
	public void addTag(String text) {
		if(text==null)
			return;
		text=text.trim().toLowerCase();
		if(text.length()==0)
			return;
		tags.add(text);
	}
	public ImageResp toResult() {
		ImageResp ir=new ImageResp();
		ir.id=this.pid;
		ir.title=title;
		ir.ispaid=false;
		ir.cost="0";
		ir.source=url;
		ir.uri=toUri();
		ir.thumbpath = toThumbUri();
//		public String uri;
//		public Integer width;
//		public Integer height;
//		
//		public String thumbpath;
//		public Integer thumbwidth;
//		public Integer thumbheight;
//		public String previewpath;
//		public Long filesize;
		return ir;
	}
	private String toUri() {
		return "/_rm/"+pr+"_"+pid+"." + format;
	}

	private String toThumbUri() {
		return "/_rm/"+pr+"_"+pid+"_t." + format;
	}

}
