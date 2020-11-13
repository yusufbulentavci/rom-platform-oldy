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


public class CartDao extends DaoBase{
	// dbmethodrender
	public static void setpayconfirmed(Boolean payconfirmed,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("payconfirmed",new BooleanCoder().encode(payconfirmed));


			methodResp.postNow(self+"/setpayconfirmed.rom");
		}
			// dbmethodrender
	public static void setitemsprice(Json itemsprice,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("itemsprice",new JsonCoder().encode(itemsprice));


			methodResp.postNow(self+"/setitemsprice.rom");
		}
			// dbmethodrender
	public static void getencrypted(String forwhat,String self  , 
	CartResponse methodResp
	) {

			methodResp.setCoder(
			new CartCoder()
			);



					methodResp.addParam("forwhat",new StringCoder().encode(forwhat));


			methodResp.postNow(self+"/getencrypted.rom");
		}
			// dbmethodrender
	public static void setcalcdetails(Json calcdetails,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("calcdetails",new JsonCoder().encode(calcdetails));


			methodResp.postNow(self+"/setcalcdetails.rom");
		}
			// dbmethodrender
	public static void listmycarts(String self  , 
	CartResponse methodResp
	) {

			methodResp.setCoder(
			new CartCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"/listmycarts.rom");
		}
			// dbmethodrender
	public static void setinvoiceaddr(Json invoiceaddr,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("invoiceaddr",new JsonCoder().encode(invoiceaddr));


			methodResp.postNow(self+"/setinvoiceaddr.rom");
		}
			// dbmethodrender
	public static void setshipstyle(String shipstyle,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("shipstyle",new StringCoder().encode(shipstyle));


			methodResp.postNow(self+"/setshipstyle.rom");
		}
			// dbmethodrender
	public static void settotalprice(Json totalprice,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("totalprice",new JsonCoder().encode(totalprice));


			methodResp.postNow(self+"/settotalprice.rom");
		}
			// dbmethodrender
	public static void setshipref(String shipref,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("shipref",new StringCoder().encode(shipref));


			methodResp.postNow(self+"/setshipref.rom");
		}
			// dbmethodrender
	public static void setnotice(String notice,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("notice",new StringCoder().encode(notice));


			methodResp.postNow(self+"/setnotice.rom");
		}
			// dbmethodrender
	public static void setpaystyle(String paystyle,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("paystyle",new StringCoder().encode(paystyle));


			methodResp.postNow(self+"/setpaystyle.rom");
		}
			// dbmethodrender
	public static void get(String self  , 
	CartResponse methodResp
	) {

			methodResp.setCoder(
			new CartCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void setcancelled(Boolean cancelled,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("cancelled",new BooleanCoder().encode(cancelled));


			methodResp.postNow(self+"/setcancelled.rom");
		}
			// dbmethodrender
	public static void setshipprice(Json shipprice,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("shipprice",new JsonCoder().encode(shipprice));


			methodResp.postNow(self+"/setshipprice.rom");
		}
			// dbmethodrender
	public static void setshipdays(Integer shipdays,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("shipdays",new IntegerCoder().encode(shipdays));


			methodResp.postNow(self+"/setshipdays.rom");
		}
			// dbmethodrender
	public static void setissue(String issue,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("issue",new StringCoder().encode(issue));


			methodResp.postNow(self+"/setissue.rom");
		}
			// dbmethodrender
	public static void setconfirmed(Boolean confirmed,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("confirmed",new BooleanCoder().encode(confirmed));


			methodResp.postNow(self+"/setconfirmed.rom");
		}
			// dbmethodrender
	public static void setdiscountprice(Json discountprice,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("discountprice",new JsonCoder().encode(discountprice));


			methodResp.postNow(self+"/setdiscountprice.rom");
		}
			// dbmethodrender
	public static void setinvoicesent(Boolean invoicesent,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("invoicesent",new BooleanCoder().encode(invoicesent));


			methodResp.postNow(self+"/setinvoicesent.rom");
		}
			// dbmethodrender
	public static void activeget(String self  , 
	CartResponse methodResp
	) {

			methodResp.setCoder(
			new CartCoder()
			);




			methodResp.getNow(self+"/activeget.rom");
		}
			// dbmethodrender
	public static void add(String stock,Integer diff,String lng,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("stock",new StringCoder().encode(stock));
					methodResp.addParam("diff",new IntegerCoder().encode(diff));
					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.postNow(self+"/add.rom");
		}
			// dbmethodrender
	public static void setshipdate(Long shipdate,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("shipdate",new LongCoder().encode(shipdate));


			methodResp.postNow(self+"/setshipdate.rom");
		}
			// dbmethodrender
	public static void setshipaddr(Json shipaddr,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("shipaddr",new JsonCoder().encode(shipaddr));


			methodResp.postNow(self+"/setshipaddr.rom");
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
	CartResponse methodResp
	) {

			methodResp.setCoder(
			new CartCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void setpayments(Json[] payments,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("payments",new ArrayCoder(new JsonCoder()).encode(payments));


			methodResp.postNow(self+"/setpayments.rom");
		}
			// dbmethodrender
	public static void setdesign(Boolean design,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("design",new BooleanCoder().encode(design));


			methodResp.postNow(self+"/setdesign.rom");
		}
			// dbmethodrender
	public static void setitems(Json items,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("items",new JsonCoder().encode(items));


			methodResp.postNow(self+"/setitems.rom");
		}
			// dbmethodrender
	public static void setvatprice(Json vatprice,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("vatprice",new JsonCoder().encode(vatprice));


			methodResp.postNow(self+"/setvatprice.rom");
		}
			// dbmethodrender
	public static void setpaysatisfied(Boolean paysatisfied,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("paysatisfied",new BooleanCoder().encode(paysatisfied));


			methodResp.postNow(self+"/setpaysatisfied.rom");
		}
			// dbmethodrender
	public static void setactive(Boolean active,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("active",new BooleanCoder().encode(active));


			methodResp.postNow(self+"/setactive.rom");
		}
			
}
