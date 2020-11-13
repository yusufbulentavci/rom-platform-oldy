appdispatch(allmethods,customserv,modules,direct)::=
<<
%if(direct)%
package com.bilgidoku.rom.gwt.araci.direct;

import com.bilgidoku.rom.gwt.araci.direct.service.*;
%else%
package com.bilgidoku.rom.gwt.araci.server;

import com.bilgidoku.rom.gwt.araci.server.service.*;
%endif%
//appdistpatch

import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;

import com.bilgidoku.rom.type.DispatchBase;

import com.bilgidoku.rom.gwt.shared.*;

import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.*;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;



%modules:moduleimport(false);wrap%

public class DispatchData extends DispatchBase{

	public DispatchData(){
		%allmethods:appadddispatch()%
		%customserv:appaddcustom()%
		
		resolveInheritance();
	}
}

>>