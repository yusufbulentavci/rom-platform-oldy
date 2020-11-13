typerender(schema,type,isclient,modules) ::=
<<package com.bilgidoku.rom.gwt.araci.%if(isclient)%client%else%server%endif%.%schema%;
//typerender

import com.bilgidoku.rom.gwt.%if(isclient)%client%else%server%endif%.common.*;

%modules:moduleimport(isclient);wrap%


public class %type.nameFirstUpper%  {
	
	%type.atts:typefieldrender()%

}

>>
