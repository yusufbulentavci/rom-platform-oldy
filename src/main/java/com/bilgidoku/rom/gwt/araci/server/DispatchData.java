package com.bilgidoku.rom.gwt.araci.server;

import com.bilgidoku.rom.gwt.araci.server.service.*;
//appdistpatch

import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;

import com.bilgidoku.rom.type.DispatchBase;

import com.bilgidoku.rom.gwt.shared.*;

import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.*;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;



import com.bilgidoku.rom.gwt.araci.server.rom.*;
import com.bilgidoku.rom.gwt.araci.server.bilgi.*;
import com.bilgidoku.rom.gwt.araci.server.site.*;
import com.bilgidoku.rom.gwt.araci.server.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.server.asset.*;


public class DispatchData extends DispatchBase{

	public DispatchData(){
		//appadddispatch
		add(false,false,"/_/tariffmodel","new",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","tariffmodel","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tariffmodel_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/tariffmodel","setcode",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","tariffmodel","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tariffmodel_setcode(),100,null); 
		//appadddispatch
		add(false,false,"/_/tariffmodel","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"rom","tariffmodel","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tariffmodel_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/tariffmodel","setvatpercentage",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","tariffmodel","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tariffmodel_setvatpercentage(),100,null); 
		//appadddispatch
		add(false,false,"/_/tariffmodel","setcoefficient",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","tariffmodel","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tariffmodel_setcoefficient(),100,null); 
		//appadddispatch
		add(false,false,"/_/tariffmodel","list",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"rom","tariffmodel","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tariffmodel_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/tariffmodel","setbaseprice",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","tariffmodel","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tariffmodel_setbaseprice(),100,null); 
		//appadddispatch
		add(false,false,"/_/_comments","approve",RoleMask.read,new String[]{"contact","admin","user"},
			HttpMethod.POST,false,"rom","comments","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Comments_approve(),100,null); 
		//appadddispatch
		add(false,false,"/_/_comments","change",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"rom","comments","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Comments_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/_comments","get",RoleMask.read,new String[]{"contact"},
			HttpMethod.GET,false,"rom","comments","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Comments_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_comments","destroy",RoleMask.read,new String[]{"contact"},
			HttpMethod.DELETE,false,"rom","comments","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Comments_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/_comments","listwaitingapproval",RoleMask.read,new String[]{"author","contact","admin"},
			HttpMethod.POST,false,"rom","comments","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Comments_listwaitingapproval(),100,null); 
		//appadddispatch
		add(false,false,"/_/waiting","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","waiting","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Waiting_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/waiting","setvalidperiod",RoleMask.read,new String[]{"owner"},
			HttpMethod.POST,false,"rom","waiting","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Waiting_setvalidperiod(),100,null); 
		//appadddispatch
		add(false,false,"/_/waiting","gotit",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","waiting","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Waiting_gotit(),100,null); 
		//appadddispatch
		add(false,false,"/_/waiting","destroy",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.DELETE,false,"rom","waiting","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Waiting_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/waiting","list",RoleMask.read,new String[]{"contact"},
			HttpMethod.GET,false,"rom","waiting","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Waiting_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getforcehttps",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getforcehttps(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getcrs",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getcrs(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","changelanged",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_changelanged(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getorderpref",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getorderpref(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getga",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getga(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","setshipstyle",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_setshipstyle(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getnfs",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getnfs(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getcpic",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getcpic(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getshipstyle",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getshipstyle(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","dellang",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_dellang(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","setpaystyle",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_setpaystyle(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","setga",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_setga(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","setforcehttps",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_setforcehttps(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","setfbappid",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_setfbappid(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","setstartssl",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_setstartssl(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getpaystyle",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getpaystyle(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","setnfs",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_setnfs(),100,null); 
		//appadddispatch
		add(false,false,"/_/_org","getstartssl",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","org","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Org_getstartssl(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","removefile",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_removefile(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","measure_del",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_measure_del(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setrelatedcids",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setrelatedcids(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setcontainer",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setcontainer(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","addfile",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_addfile(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","getnestingvalue",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_getnestingvalue(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setownercid",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setownercid(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setrtag",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setrtag(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setmask",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setmask(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","leave",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_leave(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","join",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_join(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","getfile",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_getfile(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","effectivetags",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_effectivetags(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setgid",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setgid(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setweight",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setweight(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","getaccess",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_getaccess(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","downloadfile",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_downloadfile(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","getrtags",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_getrtags(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setdelegated",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setdelegated(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","measure_add",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_measure_add(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","sethtmlfile",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_sethtmlfile(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","reuri",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_reuri(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","setnestingvalue",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_setnestingvalue(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","parentaltags",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_parentaltags(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","exists",RoleMask.read,new String[]{"owner","author","contact","admin"},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_exists(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","measure_list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_measure_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/_resources","changecontainer",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"rom","resources",null,
			UriHierarychy.SINGLE,Net.INTRA,new Resources_changecontainer(),100,null); 
		//appadddispatch
		add(false,false,"/_/widgets","change",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","widgets","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Widgets_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/widgets","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","widgets","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Widgets_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/widgets","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"rom","widgets","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Widgets_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/widgets","list",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"rom","widgets","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Widgets_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","new",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","deletedialog",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_deletedialog(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","set_ozne",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_set_ozne(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","resolve",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_resolve(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","start",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_start(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","destroy",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.DELETE,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","setcls",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_setcls(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","list",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","changetitle",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_changetitle(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","listmine",RoleMask.read,new String[]{"contact"},
			HttpMethod.GET,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_listmine(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","set_oznetags",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_set_oznetags(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","changetags",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_changetags(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","stop",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_stop(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","changedesc",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_changedesc(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","reopen",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_reopen(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","set_nesnetags",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_set_nesnetags(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","set_nesne",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_set_nesne(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","setduedate",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_setduedate(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","setduestart",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_setduestart(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","close",RoleMask.read,new String[]{"manager","admin"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_close(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","assignto",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_assignto(),100,null); 
		//appadddispatch
		add(false,false,"/_/issues","setcreator",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","issues","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Issues_setcreator(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","comments",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_comments(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","leave",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_leave(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","change",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","setcafe",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_setcafe(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","destroy",RoleMask.read,new String[]{"user"},
			HttpMethod.DELETE,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","comment",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_comment(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","join",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_join(),100,null); 
		//appadddispatch
		add(false,false,"/_/_dialogs","cmd",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"rom","dialogs","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Dialogs_cmd(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","new",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","seticon",RoleMask.read,new String[]{"contact","admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_seticon(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","settariff",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_settariff(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setvirtualsparent",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setvirtualsparent(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setsummary",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setsummary(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","virtualstock",RoleMask.read,new String[]{"contact"},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_virtualstock(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","listalternatives",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_listalternatives(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setoptions",RoleMask.read,new String[]{"contact","admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setoptions(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","addamount",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_addamount(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","settitle",RoleMask.read,new String[]{"contact","admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_settitle(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","virtualslist",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_virtualslist(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setalertonleft",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setalertonleft(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setalternatives",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setalternatives(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setamount",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setamount(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setphysical",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setphysical(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setonsale",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setonsale(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","settm",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_settm(),100,null); 
		//appadddispatch
		add(false,false,"/_/_stocks","setfirststock",RoleMask.read,new String[]{"contact","admin"},
			HttpMethod.POST,false,"rom","stocks","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Stocks_setfirststock(),100,null); 
		//appadddispatch
		add(false,false,"/_/tags","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","tags","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tags_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/tags","change",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","tags","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tags_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/tags","get",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","tags","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tags_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/tags","destroy",RoleMask.read,new String[]{"user"},
			HttpMethod.DELETE,false,"rom","tags","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tags_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/tags","list",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","tags","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tags_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","important",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_important(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","answered",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_answered(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","change",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","changemailbox",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_changemailbox(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","destroy",RoleMask.read,new String[]{},
			HttpMethod.DELETE,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","changestate",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_changestate(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","send",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_send(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,true,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_breed(),100,null); 
		//appadddispatch
		add(false,false,"/_/mails","seen",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","mails","rom.resources",
			UriHierarychy.HSC,Net.INTRA,new Mails_seen(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","listsub",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","containers","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Containers_listsub(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","listing",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","containers","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Containers_listing(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","reuri",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","containers","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Containers_reuri(),100,null); 
		//appadddispatch
		add(false,false,"/_/styles","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","styles","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Styles_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/styles","change",RoleMask.read,new String[]{"admin","designer"},
			HttpMethod.POST,false,"rom","styles","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Styles_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/styles","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","styles","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Styles_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/styles","destroy",RoleMask.read,new String[]{"admin","designer"},
			HttpMethod.DELETE,false,"rom","styles","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Styles_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/styles","list",RoleMask.read,new String[]{"admin","designer"},
			HttpMethod.GET,false,"rom","styles","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Styles_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{"admin","designer"},
			HttpMethod.POST,true,"rom","styles","rom.resources",
			UriHierarychy.SINGLE,Net.ALL,new Styles_breed(),100,null); 
		//appadddispatch
		add(false,false,"/_/_tokens","ready",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","tokens","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tokens_ready(),100,null); 
		//appadddispatch
		add(false,false,"/_/_tokens","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","tokens","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tokens_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_tokens","change",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"rom","tokens","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Tokens_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","relset",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_relset(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","change",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","destroy",RoleMask.read,new String[]{"user"},
			HttpMethod.DELETE,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","adminchangepwd",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_adminchangepwd(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","list",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","getworks",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_getworks(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","setworks",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_setworks(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","changepwd",RoleMask.read,new String[]{"owner","admin"},
			HttpMethod.POST,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_changepwd(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","setnestingvalue",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.POST,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_setnestingvalue(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","getnestingvalue",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_getnestingvalue(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","get",RoleMask.read,new String[]{"owner","user"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","relget",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_relget(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","useremail",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_useremail(),100,null); 
		//appadddispatch
		add(false,false,"/_/co","username",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"rom","contacts","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contacts_username(),100,null); 
		//appadddispatch
		add(false,false,"/_/apps","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"rom","apps","rom.resources",
			UriHierarychy.HSC,Net.ALL,new Apps_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/apps","change",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"rom","apps","rom.resources",
			UriHierarychy.HSC,Net.ALL,new Apps_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/apps","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","apps","rom.resources",
			UriHierarychy.HSC,Net.ALL,new Apps_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/apps","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"rom","apps","rom.resources",
			UriHierarychy.HSC,Net.ALL,new Apps_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/apps","list",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"rom","apps","rom.resources",
			UriHierarychy.HSC,Net.ALL,new Apps_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,true,"rom","apps","rom.resources",
			UriHierarychy.HSC,Net.ALL,new Apps_breed(),100,null); 
		//appadddispatch
		add(false,false,"/_/_trans","getall",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.GET,false,"rom","trans","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Trans_getall(),100,null); 
		//appadddispatch
		add(false,false,"/_/_trans","change",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"rom","trans","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Trans_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/_trans","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"rom","trans","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Trans_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_trans","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"rom","trans","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Trans_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/_trans","changeall",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"rom","trans","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Trans_changeall(),100,null); 
		//appadddispatch
		add(false,false,"/_/_trans","changeconstants",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"rom","trans","rom.resources",
			UriHierarychy.ONE,Net.ALL,new Trans_changeconstants(),100,null); 
		//appadddispatch
		add(false,false,"/_/_hits","get",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"site","hits","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Hits_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_hits","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"site","hits","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Hits_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/_hits","list",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"site","hits","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Hits_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","start",RoleMask.read,new String[]{"contact"},
			HttpMethod.GET,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_start(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","destroy",RoleMask.read,new String[]{"user"},
			HttpMethod.DELETE,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","setelimination",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_setelimination(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","setduration",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_setduration(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","finished",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_finished(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","list",RoleMask.read,new String[]{"contact"},
			HttpMethod.GET,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","setpage",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_setpage(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","setrequirements",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_setrequirements(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","listresults",RoleMask.read,new String[]{"admin"},
			HttpMethod.GET,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_listresults(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","getexambypage",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_getexambypage(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","myresults",RoleMask.read,new String[]{"contact"},
			HttpMethod.GET,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_myresults(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","get",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","open",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.GET,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_open(),100,null); 
		//appadddispatch
		add(false,false,"/_/exams","setquestions",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","exams","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Exams_setquestions(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","newlang",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_newlang(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","body",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_body(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","setstock",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_setstock(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","dellang",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_dellang(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","setmask",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_setmask(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","nostock",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_nostock(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","extinct",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_extinct(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","setcarray",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_setcarray(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","new",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","deletedialog",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_deletedialog(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","copylangcontent",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_copylangcontent(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","change",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","destroy",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.DELETE,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","getbydialog",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_getbydialog(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","menu",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_menu(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","getbystockuri",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_getbystockuri(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","createdialog",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_createdialog(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,true,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_breed(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","tags",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_tags(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","textsearch",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_textsearch(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","spot",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_spot(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","containerreuri",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_containerreuri(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","breadcrumbs",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_breadcrumbs(),100,null); 
		//appadddispatch
		add(false,false,"/_/writings","setmaskrecursive",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","writings","site.contents",
			UriHierarychy.HSC,Net.ALL,new Writings_setmaskrecursive(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","summary",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_summary(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","dellang",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_dellang(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","changelang",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_changelang(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","newlang",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_newlang(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","icon",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_icon(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","tip",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_tip(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","multilangicon",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_multilangicon(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","title",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_title(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","content",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_content(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","largeicon",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_largeicon(),100,null); 
		//appadddispatch
		add(false,false,"/_/_contents","mediumicon",RoleMask.read,new String[]{"owner","author","admin"},
			HttpMethod.POST,false,"site","contents","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Contents_mediumicon(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","contains",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_contains(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","resource",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_resource(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","content_list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_content_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","change",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","extinct",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_extinct(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","addtolist",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_addtolist(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","destroy",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.DELETE,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","removefromlist",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_removefromlist(),100,null); 
		//appadddispatch
		add(false,false,"/_/lists","list",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.GET,false,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,true,"site","lists","site.contents",
			UriHierarychy.HSC,Net.ALL,new Lists_breed(),100,null); 
		//appadddispatch
		add(false,false,"/_/questions","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","questions","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Questions_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/questions","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","questions","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Questions_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/questions","change",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","questions","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Questions_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/questions","destroy",RoleMask.read,new String[]{"user"},
			HttpMethod.DELETE,false,"site","questions","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Questions_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/questions","list",RoleMask.read,new String[]{"user"},
			HttpMethod.GET,false,"site","questions","site.contents",
			UriHierarychy.SINGLE,Net.INTRA,new Questions_list(),100,null); 
		//appadddispatch
		add(false,false,"/f","new",RoleMask.read,new String[]{"author","contact","admin","designer"},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_neww(),100,null); 
		//appadddispatch
		add(false,false,"/f","changetext",RoleMask.read,new String[]{"contact","user"},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_changetext(),100,null); 
		//appadddispatch
		add(false,false,"/f","setdetail",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_setdetail(),100,null); 
		//appadddispatch
		add(false,false,"/f","imagecrop",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imagecrop(),100,null); 
		//appadddispatch
		add(false,false,"/f","destroy",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.DELETE,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/f","list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_list(),100,null); 
		//appadddispatch
		add(false,false,"/f","imageblur",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imageblur(),100,null); 
		//appadddispatch
		add(false,false,"/f","imagemaketransparent",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imagemaketransparent(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{},
			HttpMethod.POST,true,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_breed(),100,null); 
		//appadddispatch
		add(false,false,"/f","imagemakepng",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imagemakepng(),100,null); 
		//appadddispatch
		add(false,false,"/f","undo",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_undo(),100,null); 
		//appadddispatch
		add(false,false,"/f","imageresize",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imageresize(),100,null); 
		//appadddispatch
		add(false,false,"/f","rename",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_rename(),100,null); 
		//appadddispatch
		add(false,false,"/f","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_get(),100,null); 
		//appadddispatch
		add(false,false,"/f","extinct",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_extinct(),100,null); 
		//appadddispatch
		add(false,false,"/f","userdir",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_userdir(),100,null); 
		//appadddispatch
		add(false,false,"/f","imagesharpen",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imagesharpen(),100,null); 
		//appadddispatch
		add(false,false,"/f","getdetail",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_getdetail(),100,null); 
		//appadddispatch
		add(false,false,"/f","imageborder",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imageborder(),100,null); 
		//appadddispatch
		add(false,false,"/f","containerreuri",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_containerreuri(),100,null); 
		//appadddispatch
		add(false,false,"/f","pngtojpeg",RoleMask.read,new String[]{"contact","user"},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_pngtojpeg(),100,null); 
		//appadddispatch
		add(false,false,"/f","changecoded",RoleMask.read,new String[]{"contact","user"},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_changecoded(),100,null); 
		//appadddispatch
		add(false,false,"/f","setmaskrecursive",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_setmaskrecursive(),100,null); 
		//appadddispatch
		add(false,false,"/f","imagerotate",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","files","site.contents",
			UriHierarychy.HSC,Net.ALL,new Files_imagerotate(),100,null); 
		//appadddispatch
		add(false,false,"/_/links","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/links","content_list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_content_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/links","change",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/links","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/links","extinct",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_extinct(),100,null); 
		//appadddispatch
		add(false,false,"/_/links","destroy",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.DELETE,false,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/links","list",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.GET,false,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,true,"site","links","site.contents",
			UriHierarychy.HSC,Net.ALL,new Links_breed(),100,null); 
		//appadddispatch
		add(false,false,"/_/households","new",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"site","households","site.contents",
			UriHierarychy.HSC,Net.ALL,new Households_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/households","change",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.POST,false,"site","households","site.contents",
			UriHierarychy.HSC,Net.ALL,new Households_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/households","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","households","site.contents",
			UriHierarychy.HSC,Net.ALL,new Households_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/households","extinct",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","households","site.contents",
			UriHierarychy.HSC,Net.ALL,new Households_extinct(),100,null); 
		//appadddispatch
		add(false,false,"/_/households","destroy",RoleMask.read,new String[]{"author","admin"},
			HttpMethod.DELETE,false,"site","households","site.contents",
			UriHierarychy.HSC,Net.ALL,new Households_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/households","list",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.GET,false,"site","households","site.contents",
			UriHierarychy.HSC,Net.ALL,new Households_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/c","",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,true,"site","households","site.contents",
			UriHierarychy.HSC,Net.ALL,new Households_breed(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setpayconfirmed",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setpayconfirmed(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setitemsprice",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setitemsprice(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","getencrypted",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_getencrypted(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setcalcdetails",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setcalcdetails(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","listmycarts",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_listmycarts(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setinvoiceaddr",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setinvoiceaddr(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setshipstyle",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setshipstyle(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","settotalprice",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_settotalprice(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setshipref",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setshipref(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setnotice",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setnotice(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setpaystyle",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setpaystyle(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setcancelled",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setcancelled(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setshipprice",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setshipprice(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setshipdays",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setshipdays(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setissue",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setissue(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setconfirmed",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setconfirmed(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setdiscountprice",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setdiscountprice(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setinvoicesent",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setinvoicesent(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","activeget",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_activeget(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","add",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_add(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setshipdate",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setshipdate(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setshipaddr",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setshipaddr(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setpayments",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setpayments(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setdesign",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setdesign(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setitems",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setitems(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setvatprice",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setvatprice(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setpaysatisfied",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setpaysatisfied(),100,null); 
		//appadddispatch
		add(false,false,"/_/_cart","setactive",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"site","cart","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Cart_setactive(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","address",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_address(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","restore",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_restore(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","change",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_change(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","newlang",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_newlang(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","browsertitle",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_browsertitle(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","textfont",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_textfont(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","sitefooter",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_sitefooter(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","setlogin",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_setlogin(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","dellang",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_dellang(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","browsericon",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_browsericon(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","headertext",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_headertext(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","publish",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_publish(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","setecommerce",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_setecommerce(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","logo",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_logo(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","style",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_style(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","palette",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_palette(),100,null); 
		//appadddispatch
		add(false,false,"/_/siteinfo","bannerimg",RoleMask.read,new String[]{"author","admin","designer"},
			HttpMethod.POST,false,"site","info","site.contents",
			UriHierarychy.ONE,Net.ALL,new Info_bannerimg(),100,null); 
		//appadddispatch
		add(false,false,"/_/usersites","new",RoleMask.read,new String[]{"contact"},
			HttpMethod.POST,false,"tepeweb","usersites","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Usersites_neww(),100,null); 
		//appadddispatch
		add(false,false,"/_/usersites","list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"tepeweb","usersites","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Usersites_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/_medias","getbyprovider",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"tepeweb","medias","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Medias_getbyprovider(),100,null); 
		//appadddispatch
		add(false,false,"/_/_medias","get",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"tepeweb","medias","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Medias_get(),100,null); 
		//appadddispatch
		add(false,false,"/_/_medias","destroy",RoleMask.read,new String[]{"admin"},
			HttpMethod.DELETE,false,"tepeweb","medias","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Medias_destroy(),100,null); 
		//appadddispatch
		add(false,false,"/_/_medias","list",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"tepeweb","medias","rom.resources",
			UriHierarychy.SINGLE,Net.INTRA,new Medias_list(),100,null); 
		//appadddispatch
		add(false,false,"/_/_initials","changeroles",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"tepeweb","initials","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Initials_changeroles(),100,null); 
		//appadddispatch
		add(false,false,"/_/_initials","getcontact",RoleMask.read,new String[]{"contact","user"},
			HttpMethod.GET,false,"tepeweb","initials","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Initials_getcontact(),100,null); 
		//appadddispatch
		add(false,false,"/_/_initials","adduser",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"tepeweb","initials","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Initials_adduser(),100,null); 
		//appadddispatch
		add(false,false,"/_/_initials","removeuser",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"tepeweb","initials","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Initials_removeuser(),100,null); 
		//appadddispatch
		add(false,false,"/_/_initials","getusers",RoleMask.read,new String[]{"user"},
			HttpMethod.POST,false,"tepeweb","initials","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Initials_getusers(),100,null); 
		//appadddispatch
		add(false,false,"/_/_initials","changepass",RoleMask.read,new String[]{"admin"},
			HttpMethod.POST,false,"tepeweb","initials","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Initials_changepass(),100,null); 
		//appadddispatch
		add(false,false,"/_/_initials","getfbappid",RoleMask.read,new String[]{},
			HttpMethod.GET,false,"tepeweb","initials","rom.resources",
			UriHierarychy.ONE,Net.INTRA,new Initials_getfbappid(),100,null); 
		//appadddispatch
		add(false,false,"/_/assets","set_simple_id",RoleMask.read,new String[]{},
			HttpMethod.POST,false,"asset","assets","rom.stocks",
			UriHierarychy.SINGLE,Net.INTRA,new Assets_set_simple_id(),100,null); 
		//appadddispatch
		add(true,false,"/_auth","register",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_register(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","check",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_check(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","login",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_login(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","letmecontact",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_letmecontact(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","logout",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_logout(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","forgetcontactpass",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_forgetcontactpass(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","forgetpass",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_forgetpass(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","extplay",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_extplay(),1000,null); 
		//appadddispatch
		add(true,false,"/_auth","trustedplatformlogin",0,new String[]{},
			HttpMethod.POST,false,"service","YetkilendirmeGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yetkilendirme_trustedplatformlogin(),1000,null); 
		//appadddispatch
		add(true,false,"/_richweb","buyMedia",0,new String[]{},
			HttpMethod.POST,false,"service","InternetGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Internet_buyMedia(),1000,null); 
		//appadddispatch
		add(true,false,"/_richweb","searchimg",0,new String[]{},
			HttpMethod.POST,false,"service","InternetGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Internet_searchimg(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","hostName",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_hostName(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","userAgent",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_userAgent(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","rtpresence",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_rtpresence(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","rtonlines",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_rtonlines(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","rtexchange",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_rtexchange(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","rtsay",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_rtsay(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","akil",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_akil(),1000,null); 
		//appadddispatch
		add(true,false,"/_sesfuncs","hasDomain",0,new String[]{},
			HttpMethod.POST,false,"service","OturumIciCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new OturumIciCagri_hasDomain(),1000,null); 
		//appadddispatch
		add(true,false,"/_account","hostfeatures",0,new String[]{},
			HttpMethod.POST,false,"service","HesapGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Hesap_hostfeatures(),1000,null); 
		//appadddispatch
		add(true,false,"/_account","tariffs",0,new String[]{},
			HttpMethod.POST,false,"service","HesapGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Hesap_tariffs(),1000,null); 
		//appadddispatch
		add(true,false,"/_account","account",0,new String[]{},
			HttpMethod.POST,false,"service","HesapGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Hesap_account(),1000,null); 
		//appadddispatch
		add(true,false,"/_account","features",0,new String[]{},
			HttpMethod.POST,false,"service","HesapGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Hesap_features(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","keys",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_keys(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","getPrivate",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_getPrivate(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","getPublic",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_getPublic(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","getHostCerts",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_getHostCerts(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","addCert",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_addCert(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","removeCert",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_removeCert(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","updateKeys",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_updateKeys(),1000,null); 
		//appadddispatch
		add(true,false,"/_admin","getCsr",0,new String[]{},
			HttpMethod.POST,false,"service","YonetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Yonetim_getCsr(),1000,null); 
		//appadddispatch
		add(true,false,"/_audit","list",0,new String[]{},
			HttpMethod.POST,false,"service","DenetimGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Denetim_list(),1000,null); 
		//appadddispatch
		add(true,false,"/_guest","contactform",0,new String[]{},
			HttpMethod.POST,false,"service","MisafirGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Misafir_contactform(),1000,null); 
		//appadddispatch
		add(true,false,"/_info/","sitemap",0,new String[]{},
			HttpMethod.GET,false,"service","SiteInfoGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new SiteInfo_sitemap(),1000,"text/xml; charset=UTF-8"); 
		//appadddispatch
		add(true,false,"/_info/","robots",0,new String[]{},
			HttpMethod.GET,false,"service","SiteInfoGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new SiteInfo_robots(),1000,"text/plain; charset=UTF-8"); 
		//appadddispatch
		add(true,false,"/_info/","startssl",0,new String[]{},
			HttpMethod.GET,false,"service","SiteInfoGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new SiteInfo_startssl(),1000,"text/plain; charset=UTF-8"); 
		//appadddispatch
		add(true,false,"/_welcome","create",0,new String[]{},
			HttpMethod.POST,false,"service","HosgeldinGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Hosgeldin_create(),1000,null); 
		//appadddispatch
		add(true,false,"/_welcome","sitesof",0,new String[]{},
			HttpMethod.POST,false,"service","HosgeldinGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new Hosgeldin_sitesof(),1000,null); 

		// appaddcustom
		add(true,true,"/_local","YerelDosya",0,new String[]{},
			null,false,"","",
			null,null,Net.INTRA,new YerelDosya(),100,null); 
			// appaddcustom
		add(true,true,"/_public","KamusalDosya",0,new String[]{},
			null,false,"","",
			null,null,Net.INTRA,new KamusalDosya(),100,null); 
			// appaddcustom
		add(true,true,"/_rm","HazirIcerik",0,new String[]{},
			null,false,"","",
			null,null,Net.INTRA,new HazirIcerik(),100,null); 
			// appaddcustom
		add(true,true,"/_static","DuraganDosya",0,new String[]{},
			null,false,"","",
			null,null,Net.INTRA,new DuraganDosya(),100,null); 
			

		resolveInheritance();
	}
}
