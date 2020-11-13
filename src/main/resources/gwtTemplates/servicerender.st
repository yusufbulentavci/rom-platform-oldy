servicerender(service,direct)::=
<<
%if(direct)%	
package com.bilgidoku.rom.gwt.araci.direct.client.service;
%else%
package com.bilgidoku.rom.gwt.araci.client.service;
%endif%

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class %service.nameAsClass%Dao extends DaoBase{
	%service.clientDaomethods:srvmethodrender(); wrap%
}

>>