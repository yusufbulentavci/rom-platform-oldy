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


public class InfoDao extends DaoBase{
	// dbmethodrender
	public static void address(Json address,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("address",new JsonCoder().encode(address));


			methodResp.postNow(self+"/address.rom");
		}
			// dbmethodrender
	public static void restore(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/restore.rom");
		}
			// dbmethodrender
	public static void change(String lng,String style,Json headertext,String default_app,Json palette,Json text_font,Json site_footer,Json address,String browser_title,String banner_img,Json logo_img,String browser_icon,String text1,String text2,String menu1,String menu2,String list1,String list2,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("style",new StringCoder().encode(style));
					methodResp.addParam("headertext",new JsonCoder().encode(headertext));
					methodResp.addParam("default_app",new StringCoder().encode(default_app));
					methodResp.addParam("palette",new JsonCoder().encode(palette));
					methodResp.addParam("text_font",new JsonCoder().encode(text_font));
					methodResp.addParam("site_footer",new JsonCoder().encode(site_footer));
					methodResp.addParam("address",new JsonCoder().encode(address));
					methodResp.addParam("browser_title",new StringCoder().encode(browser_title));
					methodResp.addParam("banner_img",new StringCoder().encode(banner_img));
					methodResp.addParam("logo_img",new JsonCoder().encode(logo_img));
					methodResp.addParam("browser_icon",new StringCoder().encode(browser_icon));
					methodResp.addParam("text1",new StringCoder().encode(text1));
					methodResp.addParam("text2",new StringCoder().encode(text2));
					methodResp.addParam("menu1",new StringCoder().encode(menu1));
					methodResp.addParam("menu2",new StringCoder().encode(menu2));
					methodResp.addParam("list1",new StringCoder().encode(list1));
					methodResp.addParam("list2",new StringCoder().encode(list2));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void newlang(String lng,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.postNow(self+"/newlang.rom");
		}
			// dbmethodrender
	public static void browsertitle(String lng,String browsertitle,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("browsertitle",new StringCoder().encode(browsertitle));


			methodResp.postNow(self+"/browsertitle.rom");
		}
			// dbmethodrender
	public static void textfont(Json textfont,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("textfont",new JsonCoder().encode(textfont));


			methodResp.postNow(self+"/textfont.rom");
		}
			// dbmethodrender
	public static void sitefooter(String lng,Json sitefooter,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("sitefooter",new JsonCoder().encode(sitefooter));


			methodResp.postNow(self+"/sitefooter.rom");
		}
			// dbmethodrender
	public static void setlogin(Boolean login,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("login",new BooleanCoder().encode(login));


			methodResp.postNow(self+"/setlogin.rom");
		}
			// dbmethodrender
	public static void dellang(String dellang,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("dellang",new StringCoder().encode(dellang));


			methodResp.postNow(self+"/dellang.rom");
		}
			// dbmethodrender
	public static void browsericon(String browsericon,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("browsericon",new StringCoder().encode(browsericon));


			methodResp.postNow(self+"/browsericon.rom");
		}
			// dbmethodrender
	public static void headertext(String lng,Json headertext,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("headertext",new JsonCoder().encode(headertext));


			methodResp.postNow(self+"/headertext.rom");
		}
			// dbmethodrender
	public static void get(String lng,String self  , 
	InfoResponse methodResp
	) {

			methodResp.setCoder(
			new InfoCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void publish(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/publish.rom");
		}
			// dbmethodrender
	public static void setecommerce(Boolean ecommerce,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("ecommerce",new BooleanCoder().encode(ecommerce));


			methodResp.postNow(self+"/setecommerce.rom");
		}
			// dbmethodrender
	public static void logo(Json logoimg,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("logoimg",new JsonCoder().encode(logoimg));


			methodResp.postNow(self+"/logo.rom");
		}
			// dbmethodrender
	public static void style(String style,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("style",new StringCoder().encode(style));


			methodResp.postNow(self+"/style.rom");
		}
			// dbmethodrender
	public static void palette(Json palette,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("palette",new JsonCoder().encode(palette));


			methodResp.postNow(self+"/palette.rom");
		}
			// dbmethodrender
	public static void bannerimg(String bannerimg,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("bannerimg",new StringCoder().encode(bannerimg));


			methodResp.postNow(self+"/bannerimg.rom");
		}
			
}
