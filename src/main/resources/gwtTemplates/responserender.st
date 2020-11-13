responserender(schema,type,isclient,direct)::=
<<
%if(direct)%
package com.bilgidoku.rom.gwt.araci.direct.%if(isclient)%client%else%direct%endif%.%schema%;
%else%
package com.bilgidoku.rom.gwt.araci.%if(isclient)%client%else%server%endif%.%schema%;
%endif%

import com.bilgidoku.rom.gwt.shared.*;

import com.bilgidoku.rom.gwt.%if(isclient)%client%else%server%endif%.common.DataResponse;

public class %type.nameFirstUpper%Response extends DataResponse<%type.nameFirstUpper%>{

}


>>