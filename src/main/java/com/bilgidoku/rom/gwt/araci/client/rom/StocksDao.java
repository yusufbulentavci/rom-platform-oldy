package com.bilgidoku.rom.gwt.araci.client.rom;
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


public class StocksDao extends DaoBase{
	// dbmethodrender
	public static void neww(String writing,Json tariff,Json physical,Integer amount,Integer alertonleft,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("writing",new StringCoder().encode(writing));
					methodResp.addParam("tariff",new JsonCoder().encode(tariff));
					methodResp.addParam("physical",new JsonCoder().encode(physical));
					methodResp.addParam("amount",new IntegerCoder().encode(amount));
					methodResp.addParam("alertonleft",new IntegerCoder().encode(alertonleft));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void seticon(String icon,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("icon",new StringCoder().encode(icon));


			methodResp.postNow(self+"/seticon.rom");
		}
			// dbmethodrender
	public static void settariff(Json tariff,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("tariff",new JsonCoder().encode(tariff));


			methodResp.postNow(self+"/settariff.rom");
		}
			// dbmethodrender
	public static void setvirtualsparent(String virtualsparent,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("virtualsparent",new StringCoder().encode(virtualsparent));


			methodResp.postNow(self+"/setvirtualsparent.rom");
		}
			// dbmethodrender
	public static void setsummary(String summary,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("summary",new StringCoder().encode(summary));


			methodResp.postNow(self+"/setsummary.rom");
		}
			// dbmethodrender
	public static void virtualstock(String stock,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("stock",new StringCoder().encode(stock));


			methodResp.getNow(self+"/virtualstock.rom");
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
	public static void list(String self  , 
	StocksResponse methodResp
	) {

			methodResp.setCoder(
			new StocksCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void listalternatives(String first,String self  , 
	StocksResponse methodResp
	) {

			methodResp.setCoder(
			new StocksCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("first",new StringCoder().encode(first));


			methodResp.getNow(self+"/listalternatives.rom");
		}
			// dbmethodrender
	public static void setoptions(Json options,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("options",new JsonCoder().encode(options));


			methodResp.postNow(self+"/setoptions.rom");
		}
			// dbmethodrender
	public static void addamount(Integer diff,String self  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);



					methodResp.addParam("diff",new IntegerCoder().encode(diff));


			methodResp.getNow(self+"/addamount.rom");
		}
			// dbmethodrender
	public static void settitle(String title,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("title",new StringCoder().encode(title));


			methodResp.postNow(self+"/settitle.rom");
		}
			// dbmethodrender
	public static void get(String self  , 
	StocksResponse methodResp
	) {

			methodResp.setCoder(
			new StocksCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void virtualslist(String self  , 
	StocksResponse methodResp
	) {

			methodResp.setCoder(
			new StocksCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"/virtualslist.rom");
		}
			// dbmethodrender
	public static void setalertonleft(Integer alertonleft,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("alertonleft",new IntegerCoder().encode(alertonleft));


			methodResp.postNow(self+"/setalertonleft.rom");
		}
			// dbmethodrender
	public static void setalternatives(String[] alternatives,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("alternatives",new ArrayCoder(new StringCoder()).encode(alternatives));


			methodResp.postNow(self+"/setalternatives.rom");
		}
			// dbmethodrender
	public static void setamount(Integer amount,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("amount",new IntegerCoder().encode(amount));


			methodResp.getNow(self+"/setamount.rom");
		}
			// dbmethodrender
	public static void setphysical(Json physical,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("physical",new JsonCoder().encode(physical));


			methodResp.postNow(self+"/setphysical.rom");
		}
			// dbmethodrender
	public static void setonsale(Boolean onsale,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("onsale",new BooleanCoder().encode(onsale));


			methodResp.postNow(self+"/setonsale.rom");
		}
			// dbmethodrender
	public static void settm(String tm,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("tm",new StringCoder().encode(tm));


			methodResp.getNow(self+"/settm.rom");
		}
			// dbmethodrender
	public static void setfirststock(String firststock,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("firststock",new StringCoder().encode(firststock));


			methodResp.postNow(self+"/setfirststock.rom");
		}
			
}
