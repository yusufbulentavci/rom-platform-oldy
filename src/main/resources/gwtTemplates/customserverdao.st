customserverdao(table,modules,roles,direct)::=
<<
%if(direct)%	
package com.bilgidoku.rom.gwt.araci.direct.service;
%else%
package com.bilgidoku.rom.gwt.araci.server.service;
%endif%

//customserverdao

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.bilgidoku.rom.pg.dict.*;

import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.web.http.RomHttpResponse;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;
import com.bilgidoku.rom.gwt.server.middle.CustomDaoBase;


%modules:moduleimport(false);wrap%

public class %table.nameFirstUpper% extends CustomDaoBase{
	

	
	%if(method.roles)%
	private static final int rls=%method.roles:rolecheck();separator="|"%;
	%endif%
	
	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException {
		
		%if(method.roles)%
		request.checkRole(rls);
		%endif%
		%table.pack%.%table.name%Gorevlisi.tek().romHandle((RomHttpResponse)request);
		return null;
	}
	
}

>>
