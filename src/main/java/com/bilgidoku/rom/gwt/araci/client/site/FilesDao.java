package com.bilgidoku.rom.gwt.araci.client.site;
// daorender
import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.google.gwt.json.client.JSONString;
import com.bilgidoku.rom.gwt.araci.client.rom.*;
import com.bilgidoku.rom.gwt.araci.client.bilgi.*;
import com.bilgidoku.rom.gwt.araci.client.site.*;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.client.asset.*;


public class FilesDao extends DaoBase{
	// dbmethodrender
	public static void neww(String lng,String uri,String[] metas,String download_uri,String[] mulfn,String filename,String textcontent,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("metas",new ArrayCoder(new StringCoder()).encode(metas));
					methodResp.addParam("download_uri",new StringCoder().encode(download_uri));
					methodResp.addParam("mulfn",new ArrayCoder(new StringCoder()).encode(mulfn));
					methodResp.addParam("filename",new StringCoder().encode(filename));
					methodResp.addParam("textcontent",new StringCoder().encode(textcontent));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void changetext(String text,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("text",new StringCoder().encode(text));


			methodResp.postNow(self+"/changetext.rom");
		}
			// dbmethodrender
	public static void setdetail(String lng,String title,String summary,String tip,String icon,String medium_icon,String large_icon,String multilang_icon,String sound,Json viewy,String[] metas,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("summary",new StringCoder().encode(summary));
					methodResp.addParam("tip",new StringCoder().encode(tip));
					methodResp.addParam("icon",new StringCoder().encode(icon));
					methodResp.addParam("medium_icon",new StringCoder().encode(medium_icon));
					methodResp.addParam("large_icon",new StringCoder().encode(large_icon));
					methodResp.addParam("multilang_icon",new StringCoder().encode(multilang_icon));
					methodResp.addParam("sound",new StringCoder().encode(sound));
					methodResp.addParam("viewy",new JsonCoder().encode(viewy));
					methodResp.addParam("metas",new ArrayCoder(new StringCoder()).encode(metas));


			methodResp.postNow(self+"/setdetail.rom");
		}
			// dbmethodrender
	public static void imagecrop(Integer left,Integer top,Integer right,Integer bottom,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("left",new IntegerCoder().encode(left));
					methodResp.addParam("top",new IntegerCoder().encode(top));
					methodResp.addParam("right",new IntegerCoder().encode(right));
					methodResp.addParam("bottom",new IntegerCoder().encode(bottom));


			methodResp.getNow(self+"/imagecrop.rom");
		}
			// dbmethodrender
	public static void destroy(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.deleteNow(self);
		}
			// dbmethodrender
	public static void list(String lng,Integer offset,Integer limit,String self  , 
	FilesResponse methodResp
	) {

			methodResp.setCoder(
			new FilesCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("offset",new IntegerCoder().encode(offset));
					methodResp.addParam("limit",new IntegerCoder().encode(limit));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void imageblur(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.getNow(self+"/imageblur.rom");
		}
			// dbmethodrender
	public static void imagemaketransparent(Integer left,Integer top,Integer closeind,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("left",new IntegerCoder().encode(left));
					methodResp.addParam("top",new IntegerCoder().encode(top));
					methodResp.addParam("closeind",new IntegerCoder().encode(closeind));


			methodResp.getNow(self+"/imagemaketransparent.rom");
		}
			// dbmethodrender
	public static void breed(String uri,Long mask,String[] tags,String containerapp,String parent  , 
	ContainersResponse methodResp
	) {

			methodResp.setCoder(
			new ContainersCoder()
			);


				final String self="/_/c";
				final String schema="site";
				final String table="files";
				methodResp.addParam("table",new JSONString("files"));

					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("mask",new LongCoder().encode(mask));
					methodResp.addParam("tags",new ArrayCoder(new StringCoder()).encode(tags));
					methodResp.addParam("containerapp",new StringCoder().encode(containerapp));
					methodResp.addParam("parent",new StringCoder().encode(parent));
					methodResp.addParam("schema",new StringCoder().encode(schema));
					methodResp.addParam("table",new StringCoder().encode(table));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void imagemakepng(String uri,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.getNow(self+"/imagemakepng.rom");
		}
			// dbmethodrender
	public static void undo(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/undo.rom");
		}
			// dbmethodrender
	public static void imageresize(Integer x,Integer y,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("x",new IntegerCoder().encode(x));
					methodResp.addParam("y",new IntegerCoder().encode(y));


			methodResp.getNow(self+"/imageresize.rom");
		}
			// dbmethodrender
	public static void rename(String uri,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.postNow(self+"/rename.rom");
		}
			// dbmethodrender
	public static void get(String lng,String self  , 
	FilesResponse methodResp
	) {

			methodResp.setCoder(
			new FilesCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void extinct(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/extinct.rom");
		}
			// dbmethodrender
	public static void userdir(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.getNow(self+"/userdir.rom");
		}
			// dbmethodrender
	public static void imagesharpen(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.getNow(self+"/imagesharpen.rom");
		}
			// dbmethodrender
	public static void getdetail(String lng,String self  , 
	FilesResponse methodResp
	) {

			methodResp.setCoder(
			new FilesCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"/getdetail.rom");
		}
			// dbmethodrender
	public static void imageborder(Integer width,Integer height,Integer color,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("width",new IntegerCoder().encode(width));
					methodResp.addParam("height",new IntegerCoder().encode(height));
					methodResp.addParam("color",new IntegerCoder().encode(color));


			methodResp.getNow(self+"/imageborder.rom");
		}
			// dbmethodrender
	public static void containerreuri(String uri,String uriprefix,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("uriprefix",new StringCoder().encode(uriprefix));


			methodResp.getNow(self+"/containerreuri.rom");
		}
			// dbmethodrender
	public static void pngtojpeg(String text,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("text",new StringCoder().encode(text));


			methodResp.postNow(self+"/pngtojpeg.rom");
		}
			// dbmethodrender
	public static void changecoded(String text,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("text",new StringCoder().encode(text));


			methodResp.postNow(self+"/changecoded.rom");
		}
			// dbmethodrender
	public static void setmaskrecursive(Long mask,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("mask",new LongCoder().encode(mask));


			methodResp.postNow(self+"/setmaskrecursive.rom");
		}
			// dbmethodrender
	public static void imagerotate(Integer angle,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("angle",new IntegerCoder().encode(angle));


			methodResp.getNow(self+"/imagerotate.rom");
		}
			
}
