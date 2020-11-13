daorender(schema,table,modules,isclient)::=
<<package com.bilgidoku.rom.gwt.araci.%if(isclient)%client%else%server%endif%.%schema%;
// daorender
import com.bilgidoku.rom.gwt.%if(isclient)%client%else%server%endif%.common.*;
import com.bilgidoku.rom.gwt.%if(isclient)%client%else%server%endif%.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

%if(isclient)%
import com.google.gwt.json.client.JSONString;
%else%
	
%endif%

%modules:moduleimport(isclient);wrap%

public class %table.nameFirstUpper%Dao extends DaoBase{
	%if(isclient)%
	%table.clientDaomethods:dbmethodrender(); wrap%
	%else%
	%table.daomethods:srvmethodrender(); wrap%
	%endif%
}

>>